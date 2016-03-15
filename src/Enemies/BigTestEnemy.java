package Enemies;

import static Helpers.Artist.drawQuadTex;
import static Helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

import Game.Enemy;
import Helpers.Artist;

public class BigTestEnemy extends Enemy{
	Texture tex; 
	public BigTestEnemy(int id, float x, float y, int xTileCount,int yTileCount) {
		super(id, x, y, xTileCount, yTileCount);
		tex = quickLoad("basic/enemy3x2");
		this.spriteHeight = 96;
		this.spriteWidth = 64;
		this.health = 100;
		System.out.println("HITBOX: " + this.hitboxCenterX + ", " + this.hitboxCenterY + " || POS: " + this.xx + ", "
				+ this.yy + "||" + offsetToPlayerX + ", " + offsetToPlayerY);
	}

	@Override
	public void draw() {
		drawQuadTex(tex, Artist.getMidScreenWidth() + offsetToPlayerX,
				Artist.getMidScreenHeight() + offsetToPlayerY, spriteWidth, spriteHeight);
	}
}
