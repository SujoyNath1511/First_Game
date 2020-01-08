package entities_and_objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import sujoy_Code.Camera;
import sujoy_Code.ImageLoader;

public class Background_Object extends Entities{
	private Camera camera;
	private static BufferedImage[] textures = {ImageLoader.loadImage("/textures/wall_2.png"),ImageLoader.loadImage("/textures/pillar.png"), 
			ImageLoader.loadImage("/textures/lights.png")};
	public Background_Object(int x, int y, int width, int height, Camera camera,int id) {
		super(false, x, y, width, height, id);
		this.camera = camera;
		/* ids:
		 * Background walls = 0
		 * pillars = 1
		 */
		
	}

	@Override
	public void tick(int x, int y) {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(textures[id], x + camera.getXOffset(), y, width, height, null);
	}

}
