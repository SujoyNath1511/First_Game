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
		
		//Platforms
		objects[0][0] = new Foreground_Object(true, 313, 603, 149, 71, camera, 0);
		objects[0][1] = new Foreground_Object(true, 0, 800, 1920, 300, camera, 0);
		objects[0][2] = new Foreground_Object(true, 500, 500, 125, 60, camera, 0);
		objects[0][3] = new Foreground_Object(true, 700, 500, 175, 60, camera, 0);
		objects[0][4] = new Foreground_Object(true, 1920, 800, 1620, 300, camera, 0);
		objects[0][5] = new Foreground_Object(true, 1140, 420, 300, 55,camera,0);
		objects[0][6] = new Foreground_Object(true, 1500, 600, 300, 55,camera,0);
		objects[0][7] = new Foreground_Object(true, 2200, 680, 220, 120,camera,0);
		objects[0][8] = new Foreground_Object(true, 2465, 550, 180, 270,camera,0);
		objects[0][9] = new Foreground_Object(true, 2760, 435, 300, 50,camera,0);	
		objects[0][10] = new Foreground_Object(true, 2905, 610, 400, 50,camera,0);
		objects[0][11] = new Foreground_Object(true, 3900, 800, 500, 300, camera, 0);
		objects[0][12] = new Foreground_Object(true, 4115, 590, 380, 70, camera, 0);
		objects[0][13] = new Foreground_Object(true, 4580, 510, 380, 70, camera, 0);
		objects[0][14] = new Foreground_Object(true, 5080, 810, 400, 70, camera, 0);
		objects[0][15] = new Foreground_Object(true, 5480, 810, 1200, 70, camera, 0);
		objects[0][16] = new Foreground_Object(true, 5900, 650, 780, 40, camera, 0);
		objects[0][17] = new Foreground_Object(true, 5900, 480, 500, 40, camera, 0);
		objects[0][18] = new Foreground_Object(true, 6525, 480, 150, 40, camera, 0);
		objects[0][19] = new Foreground_Object(true, 5480, 390, 225, 10, camera, 0);
		objects[0][20] = new Foreground_Object(true, 5652, 200, 1030, 60, camera, 0);
		objects[0][21] = new Foreground_Object(true, 5652, 80, 1030, 55, camera, 0);
		objects[0][22] = new Foreground_Object(true, 6750, 800, 3000, 280,camera,0);
		
		
		

		
		
		//Walls
		objects[1][0] = new Foreground_Object(true, 1100, 0, 40, 740, camera, 1);
		objects[1][1] = new Foreground_Object(true, 1780, 0, 40, 200, camera, 1);
		objects[1][2] = new Foreground_Object(true, 1780, 300, 40, 500, camera, 1);
		objects[1][3] = new Foreground_Object(true, 2710, 435, 50, 365, camera, 1);
		objects[1][4] = new Foreground_Object(true, 3305, 0, 50, 730, camera, 1);
		objects[1][5] = new Foreground_Object(true, 5430, 0, 50, 750, camera, 1);
		objects[1][6] = new Foreground_Object(true, 5430, 880, 50, 200, camera, 1);
		objects[1][7] = new Foreground_Object(true, 6650, 0, 30, 135, camera, 1);
		objects[1][8] = new Foreground_Object(true, 5480, 400, 225, 100, camera, 1);
		objects[1][9] = new Foreground_Object(true, 6650, 200, 30, 610, camera, 1);
		
		//Treasure
		objects[2][0] = new Foreground_Object(true,1200,370,75,50,camera,2);
		objects[2][1] = new Foreground_Object(true,3220,560,75,50,camera,2);
		
		
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
