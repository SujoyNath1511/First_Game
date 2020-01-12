/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class is used to make the background lights and walls to make buildings
 */
package entities_and_objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main_game_code.Camera;
import main_game_code.ImageLoader;

public class Background_Object extends Entities{
	private Camera camera;
	//the static array underneath is static because all instances of this class will use the same images, seperated by their ids
	private static BufferedImage[] textures = {ImageLoader.loadImage("resources/textures/wall_2.png"),ImageLoader.loadImage("resources/textures/lights.png")};
	public Background_Object(int x, int y, int width, int height, Camera camera,int id) {
		super(false, x, y, width, height, id);		//running Entitites constructor
		this.camera = camera;
		/* ids:
		 * Background walls = 0
		 * lights = 1
		 */
		
	}

	/*
	 * pre: background_object has been instantiated
	 * post: the background_object instance has been drawn unto the canvas
	 * Description: Draws the image onto the screen
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(textures[id], x + camera.getXOffset(), y, width, height, null);
	}

}
