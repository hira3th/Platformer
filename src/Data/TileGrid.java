//package data;
//
//import static Helpers.Artist.drawQuadTex;
//import Helpers.Artist;
//
//public class TileGrid {
//	public Tile[][] map;
//
//	public TileGrid() {
//		map = new Tile[Artist.WIDTH / Artist.TILE_SIZE][Artist.HEIGHT / Artist.TILE_SIZE];
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				map[i][j] = new Tile(i * Artist.TILE_SIZE, j * Artist.TILE_SIZE, Artist.TILE_SIZE, Artist.TILE_SIZE,
//						TileType.Background);
//			}
//		}
//	}
//
//	public TileGrid(int[][] newMap) {
//		map = new Tile[Artist.WIDTH / Artist.TILE_SIZE][Artist.HEIGHT / Artist.TILE_SIZE];
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				switch (newMap[j][i]) {
//				case 0:
//					map[i][j] = new Tile(i * Artist.TILE_SIZE, j * Artist.TILE_SIZE, Artist.TILE_SIZE,
//							Artist.TILE_SIZE, TileType.Void);
//					break;
//				case 1:
//					map[i][j] = new Tile(i * Artist.TILE_SIZE, j * Artist.TILE_SIZE, Artist.TILE_SIZE,
//							Artist.TILE_SIZE, TileType.Ground);
//					break;
//				case 2:
//					map[i][j] = new Tile(i * Artist.TILE_SIZE, j * Artist.TILE_SIZE, Artist.TILE_SIZE,
//							Artist.TILE_SIZE, TileType.Background);
//					break;
//				}
//			}
//		}
//	}
//
//	public void setTile(int xCoord, int yCoord, TileType type) {
//		map[xCoord][yCoord] = new Tile(xCoord * Artist.TILE_SIZE, yCoord * Artist.TILE_SIZE, Artist.TILE_SIZE,
//				Artist.TILE_SIZE, type);
//	}
//	
//	public Tile getTile(int xCoord, int yCoord){
//		return map[xCoord][yCoord];
//	}
//
//	public void draw() {
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				Tile t = map[i][j];
//				drawQuadTex(t.getTexture(), t.getX(), t.getY(), t.getWidth(), t.getHeight());
//			}
//		}
//	}
//}