package Game;

import org.newdawn.slick.geom.Rectangle;
import Helpers.Artist;

public abstract class Entity {
	protected int cx, cy; // GridPosition
	protected float xr, yr; // Position inside a cell
	protected float xx, yy; // Resulting coordinates
	protected float frictX = 0.7f, //
			frictY = 0.94f, //
			gravity = 0.02f, //
			speed = 0.01f, //
			dx = 0, //
			dy = 0;

	private boolean alive = true;
	protected int spriteHeight, spriteWidth, id;
	protected double health;
	protected float hitboxCenterX, hitboxCenterY, offsetToPlayerX, offsetToPlayerY;
	protected Rectangle hitbox;

	public Entity(int id, float x, float y) {
		this.id = id;
		setCoordinates(x, y);
	}

	public void setCoordinates(float x, float y) {
		xx = x;
		yy = y;
		cx = (int) (xx / Artist.TILE_SIZE);
		cy = (int) (yy / Artist.TILE_SIZE);
		xr = (xx - cx * Artist.TILE_SIZE) / 16;
		yr = (yy - cy * Artist.TILE_SIZE) / 16;

	}

	public void update() {
		/*
		 * X-Axis Logic goes here
		 */

		xr += dx;
		dx *= frictX;

		if (Level.hasCollision(cx - 1, cy) && xr <= 0.5f) {
			dx = 0;
			xr = 0.5f;
		}

		if (Level.hasCollision(cx + 1, cy) && xr >= 0.5f) {
			dx = 0;
			xr = 0.5f;
		}

		while (xr < 0) {
			cx--;
			xr++;
		}

		while (xr > 1) {
			cx++;
			xr--;
		}

		/*
		 * Y-Axis Logic goes here
		 */

		dy += gravity;
		yr += dy;
		dy *= frictY;

		if (Level.hasCollision(cx, cy - 1) && yr <= 0.4f) {
			dy = 0;
			yr = 0.4f;
		}

		if (onGround()) {
			dy = 0;
			yr = 0.5f;
		}

		while (yr > 1) {
			cy++;
			yr--;
		}

		while (yr < 0) {
			cy--;
			yr++;
		}

		xx = (cx + xr) * Artist.TILE_SIZE - Artist.TILE_SIZE / 2;
		yy = (cy + yr) * Artist.TILE_SIZE - Artist.TILE_SIZE / 2;

		this.hitbox = new Rectangle(this.xx, this.yy, spriteHeight, spriteWidth);
		this.hitboxCenterX = hitbox.getCenterX();
		this.hitboxCenterY = hitbox.getCenterY();
		this.offsetToPlayerX = hitboxCenterX - Player.getHitboxCenterX();
		this.offsetToPlayerY = hitboxCenterY - Player.getHitboxCenterY();
	}

	public boolean onGround() {
		return Level.hasCollision(cx, cy + 1) && yr >= 0.5f;
	}

	public boolean isOnScreen() {
		float distanceX = offsetToPlayerX < 0 ? -offsetToPlayerX : offsetToPlayerX;
		float distanceY = offsetToPlayerY < 0 ? -offsetToPlayerY : offsetToPlayerY;

		boolean onScreenX = distanceX + spriteWidth / 2 <= Artist.getMidScreenWidth();
		boolean onScreenY = distanceY + spriteHeight / 2 <= Artist.getMidScreenHeight();

		return (onScreenX && onScreenY);
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

	public boolean isAlive() {
		return this.alive;
	}
}
