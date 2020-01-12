/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This is an abstract class used to make all the guns within the game
 */
package main_game_code;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Gun{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected double angle;
	protected BufferedImage img;
	protected Camera camera;
	protected static ArrayList<Projectile> bullets = new ArrayList<Projectile>();	//this is a protected arraylist that is static because all the projectiles that are created can be accessed
													//from the same list rather than multiple different ones. makes things easier
	public Gun(int x, int y, Camera cam, BufferedImage image, int width, int height) {
		this.camera = cam;
		img = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		angle = 0;
	}
	/*
	 * pre: none
	 * post: angle has been set
	 * Description: Sets the angle of the gun
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public abstract void render(Graphics2D g2d);
	public abstract void tick();
	/*
	 * pre: none
	 * post: Returns bullets
	 * Description: returns the arraylist of projectiles
	 */
	public ArrayList<Projectile> getBullets() {
		return bullets;
	}
	protected abstract void findAngle();

	/*
	 * pre: none
	 * post: returns the angle
	 * Description: gets the angle
	 */
	public double getAngle() {
		return angle;
	}
	public abstract void fire();

	/*
	 * pre: none
	 * post: gets width
	 * Description: Returns the width
	 */
	public int getWidth() {
		return width;
	}
	/*
	 * pre: none
	 * post: sets width
	 * Description: Sets the width of the gun
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
