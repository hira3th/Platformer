package Projectiles;

import Data.Projectile;
import static Helpers.Artist.*;

import org.newdawn.slick.opengl.Texture;

import Helpers.Artist;

public class testProjectile extends Projectile {
	
	Texture tex = quickLoad("/basic/testShot");
	public testProjectile(int direction, float x, float y) {
		super(direction, x, y);
		this.hitboxHeight = 4;
		this.hitboxWidth = 4;
		this.spriteHeight = 32;
		this.spriteWidth = 32;
		this.speed = 8f;
		this.gravity = 0.02f;
		this.damage = 5;
	}

	@Override
	public void draw() {
		drawQuadTex(tex, Artist.getMidScreenWidth() + this.offsetToPlayerX,
				Artist.getMidScreenHeight() + this.offsetToPlayerY, spriteHeight, spriteWidth);
	}
}
