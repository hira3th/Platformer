package Enemies;

import static Helpers.Artist.drawQuadTex;
import static Helpers.Artist.quickLoad;

import org.newdawn.slick.opengl.Texture;

import Helpers.Artist;
import Game.Enemy;

public class TestEnemy extends Enemy {
	Texture tex;

	public TestEnemy(int id, float x, float y, int xTileCount, int yTileCount) {
		super(id, x, y, xTileCount, yTileCount);
		tex = quickLoad("basic/enemy1");
		this.spriteHeight = 32;
		this.spriteWidth = 32;
		this.health = 10;
		// this.speed = 0.01f;
	}

	@Override
	public void draw() {
		drawQuadTex(tex, Artist.getMidScreenWidth() + offsetToPlayerX - (Artist.TILE_SIZE / 2) * (xTileCount - 1),
				Artist.getMidScreenHeight() + offsetToPlayerY - (Artist.TILE_SIZE / 2) * (yTileCount - 1), spriteWidth,
				spriteHeight);
	}
}
