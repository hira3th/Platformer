package Game;

import org.lwjgl.opengl.Display;

import Helpers.Artist;
import Helpers.Clock;

import static Helpers.Artist.beginSession;

public class Boot {

	public Boot() {

		beginSession();
		
		Level.setMap("testLevel");
		Player.initiate();
	
		while (!Display.isCloseRequested()) {
			Clock.update();
			Artist.updateFPS();
			Display.update();
			Display.sync(60);
			Player.update();
			Level.updateEntities();
			Level.updateProjectiles();
			Level.draw();
		}

		Display.destroy();
	}

	public static void main(String[] args) {
		new Boot();
	}
}
