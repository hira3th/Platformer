package Game;

public abstract class Enemy extends Entity {
	public Enemy(int id, float x, float y, int xTileCount, int yTileCount){
		super(id, x, y, xTileCount, yTileCount);
	}
}
