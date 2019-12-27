package worlds;

import java.awt.Graphics;

import entities_and_objects.Foreground_Object;
import sujoy_Code.Camera;

public class World1 {
	public static final int WORLD_WIDTH = 13440;
	public static final int WORLD_HEIGHT = 1080;
	private Camera camera;
	private Foreground_Object[][] objects;
	public Foreground_Object[][] getObjects() {
		return objects;
	}
	public void setObjects(Foreground_Object[][] objects) {
		this.objects = objects;
	}
	public World1(Camera cam) {
		camera = cam;
		objects = new Foreground_Object[5][30];
		objects[0][0] = new Foreground_Object(true, 313, 803, 149, 71, camera, 0);
		objects[0][1] = new Foreground_Object(true, 0, 1000, 1920, 41, camera, 0);
		objects[0][2] = new Foreground_Object(true, 500, 700, 125, 60, camera, 0);
		objects[0][3] = new Foreground_Object(true, 700, 700, 175, 60, camera, 0);
		
		objects[1][0] = new Foreground_Object(true, 1100, 0, 40, 1000, camera, 1);
	}
	public void tick() {
		
	}
	public void render(Graphics g) {
		for (int i = 0; i < objects.length; i++) {
			for (int j = 0; j < objects[0].length; j++) {
				if (objects[i][j] != null) {
					objects[i][j].render(g);
				}
			}
		}
		
	}

}
