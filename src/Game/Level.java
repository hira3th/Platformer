package Game;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import Enemies.BigTestEnemy;
import Enemies.TestEnemy;
import Helpers.Artist;

public abstract class Level {
	public static TiledMap map;

	private static int x = 0, y = 0, //
			solidLayer, enemyLayer, enemy2x4Layer;
	private static float playerSpawnX, playerSpawnY;
	private static boolean blocked[][];
	private static ArrayList<Rectangle> blocks;
	private static ArrayList<Enemy> enemies;
	private static ArrayList<Projectile> projectiles;

	public static void setMap(String mapName) {
		try {
			map = new TiledMap("res/maps/" + mapName + ".tmx");
			solidLayer = map.getLayerIndex("solid");
			enemyLayer = map.getLayerIndex("enemies");
			enemy2x4Layer = map.getLayerIndex("enemies2x4");

			blocked = new boolean[map.getWidth()][map.getHeight()];
			enemies = new ArrayList<Enemy>();
			blocks = new ArrayList<Rectangle>();
			projectiles = new ArrayList<Projectile>();

			for (int i = 0; i < blocked.length; i++) {
				for (int j = 0; j < blocked[i].length; j++) {
					if (map.getTileId(i, j, solidLayer) != 0) {
						blocked[i][j] = true;

						blocks.add(new Rectangle((float) i * Artist.TILE_SIZE, (float) j * Artist.TILE_SIZE,
								Artist.TILE_SIZE, Artist.TILE_SIZE));
					}
				}
			}

			for (int i = 0; i < blocked.length; i++) { // Hier nicht blocked
														// benutzen, geht
														// natuerlich, ist aber
														// haesslich
				for (int j = 0; j < blocked[i].length; j++) {
					if (map.getTileId(i, j, enemyLayer) != 0) {
						enemies.add(new TestEnemy(1, i * Artist.TILE_SIZE + Artist.TILE_SIZE / 4,
								j * Artist.TILE_SIZE + Artist.TILE_SIZE / 4, 1, 1));
					}
				}
			}

			for (int i = 0; i < blocked.length; i++) {
				for (int j = 0; j < blocked[i].length; j++) {
					if (map.getTileId(i, j, enemy2x4Layer) != 0) {
						enemies.add(new BigTestEnemy(1, i * Artist.TILE_SIZE,
								j * Artist.TILE_SIZE, 2, 4)); // Div by 4 wtf?
					}
				}
			}

			setPlayerSpawn();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void setPlayerSpawn() {
		int tileID, spawnPlayerLayerId = map.getLayerIndex("playerSpawn");
		for (int i = 0; i < getHeightTiles(); i++) {
			for (int j = 0; j < getWidthTiles(); j++) {
				tileID = map.getTileId(j, i, spawnPlayerLayerId);
				if (tileID != 0) {
					playerSpawnX = j * Artist.TILE_SIZE + Artist.TILE_SIZE / 4;
					playerSpawnY = i * Artist.TILE_SIZE + Artist.TILE_SIZE / 4;
				}
			}
		}
	}

	public static void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	public static boolean hasCollision(int x, int y) {
		return blocked[x][y];
	}

	public static boolean hasCollision(int[] x, int[] y, int direction) {
		/*
		 * Hier kann noch noch viel optimiert werden!
		 * 
		 * 0 for North, 1 for East, 2 for South, 3 for West
		 */
		boolean collision = false;

		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < y.length; j++) {
				if (direction == 0) {
					if (blocked[x[i]][y[j] - 1]) {
						collision = true;
						break;
					}
				} else if (direction == 1) {
					if (blocked[x[i] + 1][y[j]]) {
						collision = true;
						break;
					}
				} else if (direction == 2) {
					if (blocked[x[i]][y[j] + 1]) {
						collision = true;
						break;
					}
				} else if (direction == 3) {
					if (blocked[x[i] - 1][y[j]]) {
						collision = true;
						break;
					}
				}
			}
		}
		return collision;
	}

	public static boolean hasCollision(Projectile obj) {
		boolean collision = false;
		for (Rectangle block : blocks) {
			if (obj.getHitbox().intersects(block))
				collision = true;
		}
		return collision;
	}

	public static void updateEntities() {
		if (enemies.size() > 0)
			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				enemy.update();
				Rectangle hitbox = enemy.getHitbox();

				for (Projectile projectile : projectiles) {
					System.out.println("hitbox: " + hitbox.toString());
					System.out.println("proj: " + projectile.getHitbox().toString());
					if (hitbox.intersects(projectile.getHitbox())) {
						enemy.damage(projectile.getDamage());
						projectile.kill();
					}
				}
				if (!enemy.isAlive())
					enemies.remove(enemy);
			}
	}

	public static void updateProjectiles() {
		if (projectiles.size() > 0)
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile tempProjectile = projectiles.get(i);
				tempProjectile.update();
				if (!tempProjectile.isAlive())
					projectiles.remove(tempProjectile);
			}
	}

	public static void draw() {
		map.render((int) -(Player.xx + Artist.TILE_SIZE) + (10 * 32), (int) -(Player.yy + Artist.TILE_SIZE) + (6 * 32));
		Player.draw();
		for (Enemy enemy : enemies)
			if (enemy.isOnScreen())
				enemy.draw();

		if (projectiles.size() > 0) {
			for (Projectile projectile : projectiles)
				if (projectile.isOnScreen())
					projectile.draw();
		}
	}

	public static int getWidthTiles() {
		return map.getWidth();
	}

	public static int getHeightTiles() {
		return map.getHeight();
	}

	public static int getWidthPixels() {
		return getWidthTiles() * Artist.TILE_SIZE;
	}

	public static int getHeightPixels() {
		return getHeightTiles() * Artist.TILE_SIZE;
	}

	public static void setX(int newX) {
		x = newX;
	}

	public static int getX() {
		return x;
	}

	public static void setY(int newY) {
		y = newY;
	}

	public static int getY() {
		return y;
	}

	public static float getPlayerSpawnX() {
		return playerSpawnX;
	}

	public static float getPlayerSpawnY() {
		return playerSpawnY;
	}

	public static boolean hasMaxProjectiles() {
		return projectiles.size() > 50;
	}

	public static void clearProjectiles() {
		projectiles.clear();
	}
}
