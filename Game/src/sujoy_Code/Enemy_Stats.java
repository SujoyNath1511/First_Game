package sujoy_Code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities_and_objects.Foreground_Object;
import sprites.SpriteSheet;

/* Name:Yusuf Ali
 * FileName:Enemy_Stats
 * LastDateofEdit:2019-12-19
 * Description: This class is where you can set and change the enemies HP, DPS and damage type(officer, air, tank);
 */
public class Enemy_Stats {
	public static final int ENEMY_HEIGHT = 50;
	public static final int ENEMY_WIDTH = 50;
	private int hitPoints;
	private int damagePoints, enemyType;
	private int xVelocity;
	private double yVelocity;
	private int enemyXValue;
	private int enemyYValue;
	private int frameCounter;
	private int freezeDistance = 150 , detectDistance = 250;
	private Player player;
	private Camera camera;
	private BufferedImage img;
	private BufferedImage [] skeleton;
	private SpriteSheet tempImg;
	private int walking;
	//private int stand;
	
	/*Pre: four integer variables passed as an argument
	 *post: Sets the values for the enemies hitpoints, damage and enemy type  
	 */
	public Enemy_Stats(int newHitPoints, int newDamagePoints,  int newEnemyType, int x, int y, Camera camera, Player player, SpriteSheet sheet, int direction) {
		walking = direction;
		hitPoints = newHitPoints;
		damagePoints = newDamagePoints;
		enemyType = newEnemyType;
		this.enemyXValue = x;
		this.enemyYValue = y;
		this.camera = camera;
		this.player = player;
		this.tempImg = sheet;
		frameCounter = 0;
		skeleton = new BufferedImage[18];
		for (int i = 0; i < 9; i++) {
			skeleton[i] = tempImg.crop(i * 64, 11 * 64, 64, 64);
			skeleton[i + 9] = tempImg.crop(i * 64, 9 * 64, 64, 64);
		}
		img = ImageLoader.loadImage("/textures/skeleton_walk.png");
		
					
	}
	/*Pre: Integer value passed as an argument
	 * Post: Sets the enemies x velocity
	 */
	public void setxVelocity(int newxVelocity) {
		xVelocity = newxVelocity;
	}
	/*Pre: Integer value passed as an argument
	 * Post: Sets the enemies hitpoints
	 */
	public void setHitPoints(int newHitPoints) {
		hitPoints = newHitPoints;  
	}
	/*Pre: Integer value passed as an argument
	 * Post: Sets the enemies y velocity
	 */
	public void setyVelocity(int newyVelocity) {
		yVelocity = newyVelocity;
	}
	/*Pre: None
	 * Post: Gives access to the enemy type for other code access 
	 */
	public int getEnemyType() {
		return enemyType;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's x velocity for other code access 
	 */
	public int getxVelocity() {
		return xVelocity;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's hitpoints for other code access 
	 */
	public int getHitpoints() {
		return hitPoints;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's y velocity for other code access 
	 */
	public double getyVelocity() {	
		return yVelocity;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's damage points for other code access 
	 */
	public int getDamagePoints() {
		return damagePoints;
	}
	
	/*Pre: An integer value passed as an argument
	 * Post: Assigns the new xValue for the enemy AI
	 */
	public void setEnemyXValue(int newEnemyXValue) {
		enemyXValue = newEnemyXValue;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's x position for other code access 
	 */
	public int getEnemyXValue() {
		return enemyXValue;
	}
	/*Pre: An integer value pass as an argument
	 * Post: Assigned the new Y value to the enemy AI 
	 */
	public void setEnemyYValue(int newEnemyYValue) {
		enemyYValue = newEnemyYValue;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's y position for other code to access 
	 */
	public int getEnemyYValue() {
		return enemyYValue;
	}
	public int getEnemyWidth() {
		return ENEMY_WIDTH;
	}
	/*Pre: None
	 * Post: Gets the enemy AI to move towards the player for action
	 */
	public void aiMovement(){
		if(enemyXValue  <  player.getX() && enemyXValue - player.getX() > -detectDistance) {//if statement to check if the player is to the right of the enemy
			walking = 2;
			if (getEnemyType() == 1) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
				setxVelocity(5);
				if (enemyXValue - player.getX() > - freezeDistance)//if statement to check if the enemy is close enough to attack
					xVelocity = 0;//Anything with  in this class is just here to be set as a basis for now and will be changed
			}
			else
				if(getEnemyType() == 2) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
					setxVelocity(1);
					if (enemyXValue - player.getX() >-freezeDistance)//if statement to check if the enemy is close enough to attack
						xVelocity = 0;
				}
				else
					if(getEnemyType() == 3) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
						setxVelocity(1);
						if (enemyXValue - player.getX() >-freezeDistance)//if statement to check if the enemy is close enough to attack
							xVelocity = 0;
					}
		}
		else
			if(enemyXValue >  player.getX()  && enemyXValue - player.getX() < detectDistance) {//If statement to check if the player is to the left of the enemy 
				walking = 1;
				if (getEnemyType() == 1) {//These sets of if statements have the same purpose as the ones above
					setxVelocity(-5);
					if (enemyXValue - player.getX() < freezeDistance)
						xVelocity=0;
				}
				else
					if(getEnemyType() == 2) {
						setxVelocity(-1);
						if (enemyXValue - player.getX() <freezeDistance)
							xVelocity=0;
					}
					else
						if(getEnemyType() == 3) {
							setxVelocity(-4);
							if (enemyXValue - player.getX() < freezeDistance)
								xVelocity = 0;
						}
				
			}
			else
				setxVelocity(0);
				
				
		enemyXValue += xVelocity;
		enemyYValue += yVelocity;
		if(getEnemyType() != 1)
			yVelocity += Player.GRAVITY;
			
	}
	public Rectangle getBounds() {
		return new Rectangle (enemyXValue,enemyYValue,ENEMY_WIDTH,ENEMY_HEIGHT);
	}
	public boolean freefall(Foreground_Object tempObj, int type) {
		Rectangle tempRec = tempObj.getBounds();
		
		if (getBounds().intersects(tempRec) == true) {
			if (type == 1) {
				yVelocity = 2;
				return false;
			}
			else {
				yVelocity = 0;
				enemyYValue = tempRec.y - ENEMY_HEIGHT + 1;
				return true;
			}
		}
		return false;
	}
	public boolean hitWall(Foreground_Object tempObj) {
		Rectangle tempRec = tempObj.getBounds();
		if (getBounds().intersects(tempRec)){
			if (enemyXValue + ENEMY_WIDTH >= tempRec.x && enemyXValue < tempRec.x) {
				enemyXValue = tempRec.x - ENEMY_WIDTH;
			}
			else if (enemyXValue <= tempRec.x + tempRec.width){
				enemyXValue = tempRec.x + tempRec.width;
			}
			
			return true;
		}
		return false;
	}
	public void tick(ArrayList<Projectile> projectiles) {
		aiMovement();
		bullet_collision(projectiles);
	}
	/*Pre: None
	 * Post: Shows the enemy's current health points 
	 */
	public void render (Graphics g) {
		//g.fillRect(enemyXValue + camera.getXOffset(), enemyYValue, ENEMY_WIDTH, ENEMY_HEIGHT);
		//g.drawImage(img, enemyXValue + camera.getXOffset(), enemyYValue, ENEMY_WIDTH, ENEMY_HEIGHT, null);
		if (walking == 2) {	
			if (xVelocity == 0)
				g.drawImage(skeleton[0], enemyXValue + camera.getXOffset(), enemyYValue + camera.getYOffset(),ENEMY_WIDTH,ENEMY_HEIGHT, null);//if walking is 2 (moving right)
			else
				g.drawImage(skeleton[frameCounter], enemyXValue + camera.getXOffset(), enemyYValue + camera.getYOffset(),ENEMY_WIDTH,ENEMY_HEIGHT, null);	//draw the sprite on the screen, based on which part of the walking animation is
			
			//currently playing
			//frameCounter ++;
			/*//increment frameCounter by 1
			if (frameCounter > 8) { //if frameCounter is greater than 8, reset it to 0 because there are only 9 images for walking animation
				frameCounter = 0;
			}*/
		}
		else if(walking == 1) {		//same as the one above, only except make sure frameCounter2 increments by 1 and adds 9 to get the second
									//half of the BufferedImage array. The second half contains walking left images.
				if (xVelocity == 0)
					g.drawImage(skeleton[9], enemyXValue + camera.getXOffset(), enemyYValue + camera.getYOffset(),ENEMY_WIDTH,ENEMY_HEIGHT, null);
				else
					g.drawImage(skeleton[frameCounter + 9], enemyXValue + camera.getXOffset(), enemyYValue + camera.getYOffset(),ENEMY_WIDTH,ENEMY_HEIGHT, null);
		}
		g.drawRect(enemyXValue + camera.getXOffset(), enemyYValue, ENEMY_WIDTH, ENEMY_HEIGHT);
			frameCounter++;
		if (frameCounter > 8) { 
			frameCounter = 0;
		}
	}
	public void bullet_collision(ArrayList <Projectile> projectiles) {
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i) != null)
				if (projectiles.get(i).collisionDetection(getBounds()) == true) {
					hitPoints -= projectiles.get(i).getDamage();
				}
		}
	}
	
	public String toString() {
		return("Enemy: " + Integer.toString(hitPoints) + "HP");
	}

}

