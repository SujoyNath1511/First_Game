/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class is to create the walls and paltforms of the game
 */
package entities_and_objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main_game_code.Camera;
import main_game_code.ImageLoader;

public class Foreground_Object extends Entities {
	private Camera camera;
	//Since all the platforms will use the same images, the image array is static
	private static BufferedImage[] textures = {ImageLoader.loadImage("resources/textures/stone.png"),ImageLoader.loadImage("resources/textures/floor.png"), ImageLoader.loadImage("resources/textures/flag.png"), ImageLoader.loadImage("resources/textures/stone_1.png")};
	public Foreground_Object(int x, int y, int width, int height, Camera camera, int id) {
		super(true, x, y, width, height, id);
		this.camera = camera;
		/*
		 * Id:
		 * walls: 1
		 * platforms: 0
		 * checkpoints: 2
		 */
	}

	/*
	 * pre: this class has been instantiated
	 * post: draws a foreground object onto the screen
	 * Description: Renders platforms and walls
	 */
	@Override
	public void render(Graphics g) {
		if (id == 0) {
			if (width > 747 || height > 178)
				g.drawImage(textures[0].getSubimage(0, 0,width, height),x + camera.getXOffset(), y,null);
			else
				g.drawImage(textures[1].getSubimage(0, 0, width, height),x + camera.getXOffset(), y,null);
		}
		else if (id == 2) {
			g.drawImage(textures[2], x + camera.getXOffset(), y, width, height, null);
		}
		else
			g.drawImage(textures[3], x + camera.getXOffset(), y, width, height, null);
			//g.fillRect(x + camera.getXOffset(), y + camera.getYOffset(), width, height);
	}

	/*
	 * pre: an instance has been instantiated
	 * post: gets the object's bounds (position and dimensions)
	 * Description: returns the boject's hitbox
	 */
	public Rectangle getBounds() {
		return new Rectangle (x,y,width,height);
	}

	/*
	 * pre: none
	 * post: Returns the id as a string
	 * Description: returns the objects id as a string (was used for testing purposes)
	 */
	public String toString() {
		return Integer.toString(id);
	}

}
