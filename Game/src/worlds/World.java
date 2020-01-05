package worlds;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities_and_objects.Foreground_Object;
import sujoy_Code.Camera;
import sujoy_Code.Frame;
import sujoy_Code.ImageLoader;
import utils.Utils;

public class World {
	private int worldWidth;
	private int worldHeight;
	private Camera camera;
	private Foreground_Object[] objects;
	private BufferedImage background;
	
	public World(Camera cam, String path) {
		camera = cam;
		loadWorld(path);
		
	}
	public void tick() {
		
	}
	public void render(Graphics g) {
		g.drawImage(background,0, 0, null);
		for (int i = 0; i < objects.length; i++) {
			if ((objects[i].getX() + camera.getXOffset()) <= Frame.WINDOW_WIDTH || 
					objects[i].getX() + camera.getXOffset() + objects[i].getWidth() > 0) {
				objects[i].render(g);
			}
		}
		
	}
	public Foreground_Object[] getObjects() {
		return objects;
	}
	public void setObjects(Foreground_Object[] objects) {
		this.objects = objects;
	}
	
	private void loadWorld(String path) {
		String file = Utils.fileToString(path);
		String[] tempArray = file.split("\\s+");
		background = ImageLoader.loadImage(tempArray[0]);
		worldWidth = Integer.parseInt(tempArray[1]);
		worldHeight = Integer.parseInt(tempArray[2]);
		objects = new Foreground_Object[Integer.parseInt(tempArray[3])];
		for (int row = 4; row + 4 < tempArray.length - 1; row += 5) {
			System.out.println((int) ((row/4.0) - 1));
			objects[row/5] = new Foreground_Object 
					(true, Integer.parseInt(tempArray[row]),Integer.parseInt(tempArray[row + 1]),Integer.parseInt(tempArray[row + 2]),Integer.parseInt(tempArray[row + 3]), camera, Integer.parseInt(tempArray[row + 4]));
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
