/*
 * Name: Sujoy Deb Nath
 * Last Edited: December 19, 2019
 * Description: This is a class that loads images from the resources library.
 */

//imports

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
	/*
	 * Pre:None
	 * Post: a image has been read from a file and returned
	 * Description: A method that simplifies the process of getting images from files
	 */
	public static BufferedImage loadImage(String path) {		
		try {
			return ImageIO.read(new File(path));	//Use ImageIO.read to get an image. Must be in a try and catch statement
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	//return null for all returns unless the image exists and can be returned
	}
}
