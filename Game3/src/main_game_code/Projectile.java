/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class is used to create and handle all the projectiles within the game, whether it be from the enemies, boss or player.
 */
package main_game_code;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Projectile {
	private int x;		//x position of the projectile
	private int y;			//y position of the projectile
	private int xVel;		//projectile's horizontal velocity
	private int yVel;		//projectile's vertical velocity
	private int vel;		//projectile's general velocity
	private int width;		//projectile's width
	private int height;			//projectile's height
	private Camera camera;		//camera for rendering
	private double angle;				//angle the projectile is being shot at (angle of the gun)
	private static BufferedImage img = ImageLoader.loadImage("resources/textures/laser.png");		//projectile image for all bullets
	private boolean collide;		//a variable to determine whether the projectile collided with an object or not
	private int damage;			//damage of the bullet
	private int type;			//bulet type (determines who shot the projectile and it's behaviours)

	public Projectile(int x, int y, int vel, double angle, int width, int height, Camera cam, int dmg, int type) {
		camera = cam;
		damage = dmg;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.vel = vel;
		this.angle = angle;
		xVel = (int) (vel * Math.cos(-angle));		//set the horizontal velocity with relation to the velocity of the bullet and angle of the gun
		yVel = (int) (vel * Math.sin(-angle));		//do the same for vertical velocity
		collide = false;		//initialize to false
		this.type = type;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns the projectile's damage
	 * Description: Returns the projetile's damage value
	 */
	public int getDamage() {
		return damage;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: x and y have been updated, collide has been determined to be true or false
	 * Description:	This method updates the projectile's location and checks to see if it went of screen, if so, set collide to true to not render
	 * or tick it anymore
	 */
	public void tick() {
		if (collide == false) {
			x += xVel;
			y += yVel;
			if (y < 0 || y > Frame.WINDOW_HEIGHT || x + camera.getXOffset() > Frame.WINDOW_WIDTH || x + camera.getXOffset() < 0) {
				collide = true;		//if the projectile is off screen, set collide to true to not tick and render anymore.
			}
		}
	}
	/*
	 * pre: projectile has been instantiated
	 * post: projectile has been drawn onto the screen
	 * Description: This method renders the projectiles
	 */
	public void render(Graphics2D g2d) {
		g2d.rotate(-angle, x + camera.getXOffset(), y);
		g2d.drawImage(img, x + camera.getXOffset(), y, width, height, null); //this is to make sure the projectile is at the same angle as the gun.
		g2d.rotate(angle, x + camera.getXOffset(), y);
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns the projectile's bounds
	 * Description: Gets the projectile's hitbox
	 */
	public Rectangle getBounds() {
		if (width > 0) {
			return new Rectangle(x,y,width,height);
		}
		else {
			return new Rectangle(x + width,y,-width,height);	//if the projectile is going left, then use negative scaling
		}
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns x
	 * Description:Returns the projectile's x position
	 */
	public int getX() {
		return x;			
	}
	/*
	 * pre: projectile has been instantiated
	 * post: sets x
	 * Description: Sets the x position
	 */
	public void setX(int x) {
		this.x = x;
	}
	/* 
	 * pre: projectile has been instantiated
	 * post: gets y
	 * Description: Returns the y position
	 */
	public int getY() {
		return y;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: sets y
	 * Description: sets the y position
	 */
	public void setY(int y) {
		this.y = y;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: gets xVel
	 * Description: Returns the x velocity of the bullet
	 */
	public int getxVel() {
		return xVel;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: sets xVel
	 * Description: sets the x velocity of the bullet
	 */
	public void setxVel(int xVel) {
		this.xVel = xVel;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns the y velocity
	 * Description: Returns the y velocity of the bullet
	 */
	public int getyVel() {
		return yVel;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: sets yVel
	 * Description: sets the y velocity of the projectile
	 */
	public void setyVel(int yVel) {
		this.yVel = yVel;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: gets the velocity of the projectile
	 * Description: Returns the velocity of the projectile
	 */
	public int getVel() {
		return vel;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: vel has a new value
	 * Description: sets the velocity of the projectile
	 */
	public void setVel(int vel) {
		this.vel = vel;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns width
	 * Description: Returns the width of the projectile
	 */
	public int getWidth() {
		return width;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: width has a new value
	 * Description: Sets the width of the projectile
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns height
	 * Description: returns the height of the projectile
	 */
	public int getHeight() {
		return height;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: height has a new value
	 * Description: Sets the projectile's height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: if the projectile's hitbox intersects with another object that is not a projectile, then collide is true, else false
	 * Description: This method returns a boolean value to check to see if a projectile has collided with an object or not
	 */
	public boolean collisionDetection(Rectangle tempRec) {
		if (getBounds().intersects(tempRec) == true) {		//checks for collision/intersection of hitboxes.
			collide = true;
			return true;
		}
		return false;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns collide
	 * Description: This method returns the value of collide
	 */
	public boolean getCollide() {
		return collide;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: returns type
	 * Description: returns which type of bullet this instance is (player, enemy or boss special)
	 */
	public int getType() {
		return type;
	}
	/*
	 * pre: projectile has been instantiated
	 * post: sets the type
	 * Description: Sets which type of projectile this instance is
	 */
	public void setType(int type) {
		this.type = type;
	}
}