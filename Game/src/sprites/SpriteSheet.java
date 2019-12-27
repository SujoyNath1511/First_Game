/*
 * Name: Sujoy Deb Nath
 * Last Edited: December 19,2019
 * Description: This class stores the sprite sheet image and adds the ability to crop the image (get specific rows of animation for different tasks)
 */

package sprites;

import java.awt.image.BufferedImage;	//Import the BufferedImage class

public class SpriteSheet {
	private BufferedImage spriteSheet;	//variable to store the sprite sheet image
	public SpriteSheet(BufferedImage sheet) {
		spriteSheet = sheet;	//store the image
	}
	/*
	 * Pre: spriteSheet contains a BufferedImage (not null)
	 * post: returns part of the image based on the x and y coordinates of the top left corner, width and height of the cropped image
	 * Description: A method that returns a cropped image
	 */
	public BufferedImage crop(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x , y, width, height);		//get a cropped image
	}
}
