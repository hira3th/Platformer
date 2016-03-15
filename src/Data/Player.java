package Data;

import static Helpers.Artist.TILE_SIZE;
import static Helpers.Artist.drawQuadTex;
import static Helpers.Artist.quickLoad;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.opengl.Texture;

import Helpers.Artist;
import Projectiles.testProjectile;

public abstract class Player {
	static int cx, cy; // GridPosition
	static float xr, yr; // Position inside a cell
	static float xx, yy; // Position in Pixel-Precision
	static float hitboxCenterX, hitboxCenterY;
	private static float frictX = 0.7f, //
			frictY = 0.94f, //
			gravity = 0.02f, //
			speed = 0.05f, //
			dx = 0, //
			dy = 0;
	private static int direction = 0;
	private static Rectangle hitbox;
	private static Texture tex;
	
	public static void initiate() {
		setCoordinates(Level.getPlayerSpawnX(), Level.getPlayerSpawnY());
		tex = quickLoad("basic/player");
	}

	static int counter = 0;

	public static void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
			dx -= speed;
			direction = 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
			dx += speed;
			direction = 0;
		}

		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && onGround()) {
			dy = -0.4f;
		}
		
		if (counter > 0)
			counter--;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (counter == 0)
				if (!Level.hasMaxProjectiles()) {
					Level.addProjectile(new testProjectile(direction, xx, yy));
					counter = 5;
				}
		}

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
		hitbox = new Rectangle(xx, yy, TILE_SIZE, TILE_SIZE);
		hitboxCenterX = hitbox.getCenterX();
		hitboxCenterY = hitbox.getCenterY();
	}

	public static void setCoordinates(float x, float y) {
		xx = x;
		yy = y;
		cx = (int) (xx / Artist.TILE_SIZE);
		cy = (int) (yy / Artist.TILE_SIZE);
		xr = (xx - cx * Artist.TILE_SIZE) / 16;
		yr = (yy - cy * Artist.TILE_SIZE) / 16;
	}

	public static boolean onGround() {
		return Level.hasCollision(cx, cy + 1) && yr >= 0.5f;
	}

	public static void draw() {
		drawQuadTex(tex, Artist.getMidScreenWidth(), Artist.getMidScreenHeight(), TILE_SIZE,
				TILE_SIZE);
	}

	public static int getGridPositionX() {
		return cx;
	}

	static void setGridPositionX(int newCX) {
		cx = newCX;
	}

	public static int getGridPositionY() {
		return cy;
	}

	static void setGridPositionY(int newCY) {
		cy = newCY;
	}

	public static int getExactX() {
		return (int) xx;
	}

	public static int getExactY() {
		return (int) yy;
	}

	public static float getRelativeY() {
		return yr;
	}

	public static float getRelativeX() {
		return xr;
	}

	public static float getHitboxCenterX() {
		return hitboxCenterX;
	}

	public static float getHitboxCenterY() {
		return hitboxCenterY;
	}
}
