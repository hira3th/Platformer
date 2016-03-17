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
		tex = quickLoad("basic/enemy64x128");
		this.spriteHeight = 128;
		this.spriteWidth = 64;
		this.health = 100;
		this.speed = 0.01f;
		for(int i = 0; i < cx.length; i++){
			for (int j = 0; j < cy.length; j++){
				System.out.println(cx[i] + ", " + cy[j]);
			}
		}
	}

	@Override
	public void draw() {
		drawQuadTex(tex, Artist.getMidScreenWidth() + offsetToPlayerX-(Artist.TILE_SIZE/2) * (xTileCount-1),
				Artist.getMidScreenHeight() + offsetToPlayerY-(Artist.TILE_SIZE/2) * (yTileCount-1), spriteWidth, spriteHeight);
	}
}
