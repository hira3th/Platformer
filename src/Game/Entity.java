package Game;

import org.newdawn.slick.geom.Rectangle;
import Helpers.Artist;

public abstract class Entity {
	protected int[] cx, cy; // GridPosition
	protected float xr, yr; // Position inside a cell
	protected float xx, yy; // Resulting coordinates
	protected float frictX = 0.7f, //
			frictY = 0.94f, //
			gravity = 0.02f, //
			speed = 0.0f, //
			dx = 0, //
			dy = 0;

	private boolean alive = true;
	protected int spriteHeight, spriteWidth, //
			id, //
			xTileCount, yTileCount;
	protected double health;
	protected float hitboxCenterX, hitboxCenterY, offsetToPlayerX, offsetToPlayerY;
	protected Rectangle hitbox;

	public Entity(int id, float x, float y, int xTileCount, int yTileCount) {
		this.id = id;
		this.xTileCount = xTileCount;
		this.yTileCount = yTileCount;
		this.cx = new int[xTileCount];
		this.cy = new int[yTileCount];
		setCoordinates(x, y);
		this.hitbox = new Rectangle(this.xx, this.yy, Artist.TILE_SIZE * xTileCount, Artist.TILE_SIZE * yTileCount);
		this.hitboxCenterX = hitbox.getCenterX();
		this.hitboxCenterY = hitbox.getCenterY();
	}

	public void setCoordinates(float x, float y) {
		xx = x;
		yy = y;

		for (int i = 0; i < cx.length; i++) {
			cx[i] = (int) (xx / Artist.TILE_SIZE) + i;
		}

		for (int i = 0; i < cy.length; i++) {
			cy[i] = (int) (yy / Artist.TILE_SIZE) + i;
		}

		xr = (xx - cx[0] * Artist.TILE_SIZE) / 16 + 0.5f;
		yr = (yy - cy[0] * Artist.TILE_SIZE) / 16 + 0.5f;
	}

	public void update() {
		/*
		 * X-Axis Logic goes here
		 */

		dx += speed;
		xr += dx;
		dx *= frictX;

		if (Level.hasCollision(cx, cy, 3) && xr <= 0.5f) {
			dx = 0;
			xr = 0.5f;
		}

		if (Level.hasCollision(cx, cy, 1) && xr >= 0.5f) {
			dx = 0;
			xr = 0.5f;
		}

		while (xr < 0) {
			for (int i = 0; i < cx.length; i++) {
				cx[i]--;
			}
			xr++;
		}

		while (xr > 1) {
			for (int i = 0; i < cx.length; i++) {
				cx[i]++;
			}
			xr--;
		}

		/*
		 * Y-Axis Logic goes here
		 */

		dy += gravity;
		yr += dy;
		dy *= frictY;

		if (Level.hasCollision(cx, cy, 0) && yr <= 0.4f) {
			dy = 0;
			yr = 0.4f;
		}

		if (onGround()) {
			dy = 0;
			yr = 0.5f;
		}

		while (yr > 1) {
			for (int i = 0; i < cy.length; i++) {
				cy[i]++;
			}
			yr--;
		}

		while (yr < 0) {
			for (int i = 0; i < cy.length; i++) {
				cy[i]--;
			}
			yr++;
		}

		xx = (cx[0] + xr) * Artist.TILE_SIZE - Artist.TILE_SIZE / 2;
		yy = (cy[0] + yr) * Artist.TILE_SIZE - Artist.TILE_SIZE / 2;

		this.hitbox = new Rectangle(this.xx, this.yy, Artist.TILE_SIZE * xTileCount, Artist.TILE_SIZE * yTileCount);
		this.hitboxCenterX = hitbox.getCenterX();
		this.hitboxCenterY = hitbox.getCenterY();
		this.offsetToPlayerX = hitboxCenterX - Player.getHitboxCenterX();
		this.offsetToPlayerY = hitboxCenterY - Player.getHitboxCenterY();
	}

	public boolean onGround() {
		return Level.hasCollision(cx, cy, 2) && yr >= 0.5f;
	}

	boolean first = true;

	public boolean isOnScreen() {
		float distanceX = offsetToPlayerX < 0 ? -offsetToPlayerX : offsetToPlayerX;
		float distanceY = offsetToPlayerY < 0 ? -offsetToPlayerY : offsetToPlayerY;

		boolean onScreenX = (distanceX - spriteWidth) <= Artist.getMidScreenWidth();
		boolean onScreenY = (distanceY - spriteHeight) <= Artist.getMidScreenHeight();
		if (first) {
			System.out.println(distanceX + spriteWidth / 2 + ", " + distanceY + spriteHeight / 2);
			first = !first;
		}
		return (onScreenX && onScreenY);
	}

	public boolean isCloseToPlayer() {
		boolean isCloseX = false;
		boolean isCloseY = false;

		for (int cx : cx) {
			int distance = Player.cx - cx > 0 ? Player.cx - cx : -(Player.cx - cx);
			if (distance < 2)
				return isCloseX = true;
		}
		
		for (int cy : cy) {
			int distance = Player.cy - cy > 0 ? Player.cy - cy : -(Player.cy - cy);
			if (distance < 2)
				return isCloseY = true;
		}
		
		return isCloseX && isCloseY;
	}

	public void draw() {

	}

	public void damage(double damage) {
		this.health -= damage;
		if (this.health <= 0)
			kill();
	}

	public void kill() {
		this.alive = false;
	}

	public Rectangle getHitbox() {
		return this.hitbox;
	}
	
	public float getHitboxCenterX(){
		return this.hitboxCenterX;
	}
	
	public float getHitboxCenterY(){
		return this.hitboxCenterY;
	}

	public boolean isAlive() {
		return this.alive;
	}
}
