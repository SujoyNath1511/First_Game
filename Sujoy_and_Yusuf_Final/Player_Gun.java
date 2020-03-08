/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class is the gun the player uses, which the follows the cursor.
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

public class Player_Gun extends Gun{
	private int cursorX;	//cursor's x position
	private int cursorY;  //cursor's y position
	private Player player;
	public Player_Gun(Camera cam, BufferedImage image, Player player, int width, int height) {
		super(player.getX() + Player.PLAYER_WIDTH/2, player.getY() + Player.PLAYER_HEIGHT/2, cam, image, width, height); //run gun constructor
		this.player = player;
	}
	/*
	 * pre: Player_Gun has been instantiated
	 * post: draws the player gun on the screen
	 * Description: Renders the player gun at the angles which follow the cursor
	 */
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		if (x <= cursorX) {
			g2d.rotate(-angle, x + camera.getXOffset() + 8, y);		//rotate the canvas to draw the image at an angle
			g2d.drawImage(img,x + camera.getXOffset() + 8, y, width, height, null);
			g2d.rotate(angle, x + camera.getXOffset() + 8, y);   //rotate the canvas back to be able to draw images normally
		}
		else {
			g2d.rotate(angle, x + camera.getXOffset() + 17, y);
			g2d.drawImage(img,x + camera.getXOffset() + 17, y, -width, height, null);		//use negative scaling when facing left
			g2d.rotate(-angle, x + camera.getXOffset() + 17, y);
		}
		
	}
	/*
	 * pre: Player_Gun has been instantiated
	 * post: player's gun's angle and postion have been updated
	 * Description: Updates the gun's position and angle according to the cursor.
	 */
	public void tick() {
		cursorX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - camera.getXOffset());  //update the cursor position
		cursorY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		x = player.getX();
		y = player.getY() + 24;		//update gun position
		findAngle();
		
	}
	/*
	 * pre: tick has run
	 * post: gun's angle has been determined
	 * Description: Determines the gun's angle (angle to draw the gun) using the cursor's location
	 */
	public void findAngle() {
		int tempX = cursorX - x;			//get the difference in the x values and negative of the y because in java, down is positive
		int tempY = -(cursorY - y);
		double tempHype = Math.sqrt((tempX * tempX) + (tempY * tempY));	//get the hypoteneuse
		if (tempX != 0) {
			angle = Math.asin(tempY/tempHype);		//if the hypoteneuse isn't 0, get the angle using sin.
		}
	}
	/*
	 * pre: none
	 * post: returns cursorX
	 * Description: Returns the cursor's x position
	 */
	public int getCursorX() {
		return cursorX;
	}
	/*
	 * pre: none
	 * post: returns cursorY
	 * Description: Returns the cursor's y position
	 */
	public int getCursorY() {
		return cursorY;
	}
	/*
	 * pre: left mouse button has been clicked
	 * post: a projectile has been created
	 * Description: Fires the gun (creates a new projectile with a certain velocity and damage);
	 */
	public void fire() {
		if (player.getHealth() > 0) {		//fire as long as the player is alive
			if (x <= cursorX)
				bullets.add(new Projectile(x + 8, y, 28, angle, 36,14,camera, 30, 1));
			else
				bullets.add(new Projectile(x + 17, y, -28, -angle, -36,14,camera, 30, 1));		//use negative angle and negative scaling for the bullets if they are going left
		}
	}

}
