package worlds;

import java.awt.Graphics;

import entities_and_objects.Foreground_Object;
import sujoy_Code.Camera;
import utils.Utils;

public class World {
	private int worldWidth;
	private int worldHeight;
	private Camera camera;
	private Foreground_Object[][] objects;
	
	public World(Camera cam, String path) {
		camera = cam;
		loadWorld(path);
		
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
	public Foreground_Object[][] getObjects() {
		return objects;
	}
	public void setObjects(Foreground_Object[][] objects) {
		this.objects = objects;
	}
	
	private void loadWorld(String path) {
		String file = Utils.fileToString(path);
		String[] tempArray = file.split("\\s+");
		worldWidth = Integer.parseInt(tempArray[0]);
		worldHeight = Integer.parseInt(tempArray[1]);
		objects = new Foreground_Object[Integer.parseInt(tempArray[2])][Integer.parseInt(tempArray[3])];
		for (int row = 4; row < tempArray.length - 1; row += 6) {
			//System.out.println(row);
			objects[Integer.parseInt(tempArray[row + 5])][Integer.parseInt(tempArray[row])] = new Foreground_Object
					(true, Integer.parseInt(tempArray[row + 1]),Integer.parseInt(tempArray[row + 2]),Integer.parseInt(tempArray[row + 3]),Integer.parseInt(tempArray[row + 4]), camera, Integer.parseInt(tempArray[row + 5]));
			
		} 		
	}
	public int getWorldWidth() {
		return worldWidth;
	}
	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}
	public int getWorldHeight() {
		return worldHeight;
	}
	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

}
