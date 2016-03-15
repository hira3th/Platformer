package Data;

import org.newdawn.slick.geom.Rectangle;

import Helpers.Artist;

public abstract class Projectile {
	protected float xx, yy; // Resulting coordinates

	protected float speed = 0, //
			gravity = 0, //
			dx = 0, //
			dy = 0;
	protected Rectangle hitbox;
	protected double damage = 0;
	protected int spriteHeight, spriteWidth, hitboxWidth, hitboxHeight, direction;
	protected float hitboxCenterX, hitboxCenterY, offsetToPlayerX, offsetToPlayerY;
	protected boolean alive;

	public Projectile(int direction, float x, float y) {
		this.direction = direction;
		setCoordinates(x, y);
		this.hitbox = new Rectangle(
				this.xx + (spriteWidth - hitboxWidth) / 2 /* Abbildung 1 */,
				this.yy + (spriteHeight - hitboxHeight) / 2, hitboxWidth, hitboxHeight);
		alive = true;
	}

	public void setCoordinates(float x, float y) {
		xx = x;
		yy = y;
	}

	public void update() {
		System.out.println("HITBOX: " + this.hitboxCenterX + ", " + this.hitboxCenterY + " || POS: " + this.xx + ", "
				+ this.yy + "||" + offsetToPlayerX + ", " + offsetToPlayerY);

		if (direction == 0)
			xx += speed;
		else if (direction == 1)
			xx -= speed;

		// yy += gravity;
		// yy *= 0.95f;

		this.hitbox = new Rectangle(
				this.xx + (spriteWidth - hitboxWidth) / 2 /* Abbildung 1 */,
				this.yy + (spriteHeight - hitboxHeight) / 2, hitboxWidth, hitboxHeight);
		this.hitboxCenterX = hitbox.getCenterX();
		this.hitboxCenterY = hitbox.getCenterY();
		this.offsetToPlayerX = hitboxCenterX - Player.getHitboxCenterX();
		this.offsetToPlayerY = hitboxCenterY - Player.getHitboxCenterY();

		if (Level.hasCollision(this))
			alive = false;
	}

	public boolean isOnScreen() {
		float distanceX = offsetToPlayerX < 0 ? -offsetToPlayerX : offsetToPlayerX;
		float distanceY = offsetToPlayerY < 0 ? -offsetToPlayerY : offsetToPlayerY;

		boolean onScreenX = distanceX + spriteWidth / 2 <= Artist.getMidScreenWidth();
		boolean onScreenY = distanceY + spriteHeight / 2 <= Artist.getMidScreenHeight();

		return (onScreenX && onScreenY);
	}

	public boolean isAlive() {
		return alive;
	}

	public void kill() {
		this.alive = false;
	}

	public void draw() {

	}

	public Rectangle getHitbox() {
		return this.hitbox;
	}
	
	public double getDamage(){
		return this.damage;
	}
}
