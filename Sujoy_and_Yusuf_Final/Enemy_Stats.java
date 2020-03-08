
/* Name:Yusuf Ali
 * LastDateofEdit:2020-01-11
 * Description: This class is where you can set and change the enemies HP, DPS and damage type(officer, air, tank);
 */


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;



public class Enemy_Stats {
	private static BufferedImage[] enemy_sprites = {ImageLoader.loadImage("resources/animationSheets/drone_sprite_2.png"), ImageLoader.loadImage("resources/animationSheets/strong_defense_walk.png"),ImageLoader.loadImage("resources/animationSheets/officer_walk.png")};//Gets the sprites for the enemies
	private int enemy_width[] = {67,33,28};//Array to set the width of the enemy
	private int enemy_height[] = {40,50,50};//Array to set the height of the enemy
	private int hitPoints;//Variable to hold the enemy's hitpoints
	private int damagePoints;//Variable to hold the enemy's damagepoints
	private int enemyType;//Variable to hold what type of enemy it is
	private int xVelocity;//Variable to hold the enemy's x velocity
	private double yVelocity;//Variable to hold the enemy's y velocity
	private int enemyXValue;//Variable to hold the current enemy x value
	private int enemyYValue;//Variable to hold the current enemy y value
	private int frameCounter;//Variable frame counter to run through the sprites array and out puts the appropriate image
	private int freezeDistance, detectDistance;//variable for the enemies detect distance and freeze distance for the enemy AI
	private Player player;//Instance of the player class 
	private Camera camera;//Instance of the camera class
	private BufferedImage [] sprite;//An array instance of the buffered Image class to hold the sprite images in order
	private Enemy_Gun enemyGun;//An instance of the Enemy_Gun class
	private int walking;//Variable to determine the direction the enemy is walking
	private int original_hp;//Variable to hold the enemy's original health
	private int original_x;//Variable to hold the enemy's original x position
	private int original_y;//Variable to hold the enemy's original y position
	

	
	/*Pre: six integer variables passed as an argument
	 *post: Sets the values for the enemies hitpoints, damagepoints, enemy type. Also takes in a camera object, player object and the direction the enemy faces as an int value (1 = left) (2 = right)  
	 */
	public Enemy_Stats(int newHitPoints, int newDamagePoints,  int newEnemyType, int x, int y, Camera camera, Player player, int direction) {
		walking = direction;
		hitPoints = newHitPoints;
		original_hp = newHitPoints;
		damagePoints = newDamagePoints;
		enemyType = newEnemyType;
		detectDistance = 400;
		freezeDistance = (int)(Math.random() * ((300 - 200) + 1) + 200);
		this.enemyXValue = x;
		original_x = x;
		original_y = y;
		this.enemyYValue = y;
		this.camera = camera;
		this.player = player;
		frameCounter = 0;
		sprite = new BufferedImage[18];
		for (int i = 0; i < 9; i++) {//For loop to crop out the sprite images
			sprite[i] = enemy_sprites[enemyType - 1].getSubimage(i * enemy_width[enemyType - 1], 50, enemy_width[enemyType - 1], enemy_height[enemyType - 1]);
			sprite[i + 9] = enemy_sprites[enemyType - 1].getSubimage(i * enemy_width[enemyType - 1], 0, enemy_width[enemyType - 1], enemy_height[enemyType - 1]);
		}
		this.enemyGun = new Enemy_Gun(this.camera, ImageLoader.loadImage("resources/textures/laser_gun.png"),this,25, 12, player, direction);
		
		
					
	}
	/*Pre: none
	 * Post: Return the array of the enemies widths
	 */
	public int[] getEnemy_width() {
		return enemy_width;
	}
	/*Pre: none
	 * Post: Return the array of the enemies heights
	 */
	public int[] getEnemy_height() {
		
		return enemy_height;
	}
	/*Pre: none
	 * Post: Return the gun object of the enemy
	 */
	public Enemy_Gun getGun() {
		return enemyGun;
	}
	/*Pre: none
	 * Post: resets all the enemies x, y and health after the game ends
	 */
	public void resetEnemy() {
		hitPoints = original_hp;  
		enemyXValue = original_x;
		enemyYValue = original_y;
	}
	/*Pre: Must take in an object of the Enemy_Gun class 
	 * Post: assigns the new enemyGun object
	 */
	public void setGun(Enemy_Gun enemyGun) {
		this.enemyGun = enemyGun;
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
	/*Pre: None
	 * Post: Gets the enemy AI to move towards the player for action
	 */
	public void aiMovement(){
		if(enemyXValue  <  player.getX() && enemyXValue - player.getX() > -detectDistance) {//if statement to check if the player is to the right of the enemy
			walking = 2;
			if (enemyType == 1) //if statement to check what type of enemy therefore assigning it's appropriate velocity
				xVelocity = 5;
				if (enemyXValue - player.getX() > - freezeDistance) {//if statement to check if the enemy is close enough to attack
					xVelocity = 0;//Anything with  in this class is just here to be set as a basis for now and will be changed
					enemyGun.fire();
				}
			else
				if(enemyType == 2) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
					xVelocity = 1;
					if (enemyXValue - player.getX() >-freezeDistance) {//if statement to check if the enemy is close enough to attack
						xVelocity = 0;
						enemyGun.fire();
					}
				}
				else
					if(enemyType == 3) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
						xVelocity = 4;
						if (enemyXValue - player.getX() >-freezeDistance) {//if statement to check if the enemy is close enough to attack
							xVelocity = 0;
							enemyGun.fire();
						}
					}
			
		}
		else
			if(enemyXValue >  player.getX()  && enemyXValue - player.getX() < detectDistance) {//If statement to check if the player is to the left of the enemy 
				walking = 1;
				if (enemyType == 1) {//These sets of if statements have the same purpose as the ones above
					xVelocity = -5;
					if (enemyXValue - player.getX() < freezeDistance) {
						xVelocity=0;
						enemyGun.fire();
					}
				}
				else
					if(enemyType == 2) {
						xVelocity = -1;
						if (enemyXValue - player.getX() <freezeDistance) {
							xVelocity=0;
							enemyGun.fire();
						}
					}
					else
						if(enemyType == 3) {
							xVelocity = -4;
							if (enemyXValue - player.getX() < freezeDistance) {
								xVelocity = 0;
								enemyGun.fire();
							}
						}
				
			}
			else
				xVelocity = 0;
				
				
		enemyXValue += xVelocity;//Adding the appropriate X and Y velcoties
		enemyYValue += yVelocity;
		if(enemyType != 1)//Checks if the enemyType is not air since the air unites do not get affected by gravity
			yVelocity += Player.GRAVITY;//Adds gravity to the enemy if the walk off a platform
		
			
	}
	/*Pre: None
	 * Post: return the AI's freeze distance
	 */
	public int getFreezeDistance() {
		return freezeDistance;
	}
	/*Pre: An integer value passed as an argument
	 * Post: Assigned the new freeze distance value to the enemy AI 
	 */
	public void setFreezeDistance(int freezeDistance) {
		this.freezeDistance = freezeDistance;
	}
	/*Pre: None
	 * Post: returns the enemy hitbox 
	 */
	public Rectangle getBounds() {
		if (hitPoints > 0)//If statement to check if the enemy is not dead
			return new Rectangle (enemyXValue,enemyYValue,enemy_width[enemyType - 1],enemy_height[enemyType - 1]);//Returns the enemy's aapropriate hitbox
		else //Else meaning the enemy is dead
			return new Rectangle (0,0,0,0);//Return no hitbox
	}
	/*Pre: An object of the foreground class passed as an argument
	 * Post: return whether the enemy is in contact with a platform or not (True:yes False:no) 
	 */
	public boolean freefall(Foreground_Object tempObj, int type) {
		Rectangle tempRec = tempObj.getBounds();
		
		if (getBounds().intersects(tempRec) == true) {//Checks if an intersections has occurred
			if (type == 1) {//If there is no object in the way then the enemy falls
				yVelocity = 2;
				return false;//return false meaning no object
			}
			else {//Else meaning there is an object in the way while falling therefore reseting the enemy's y value to be on top of that platform
				yVelocity = 0;
				enemyYValue = tempRec.y - enemy_height[enemyType - 1] + 1;
				return true;//return true meaning there is an object 
			}
		}
		return false;
	}
	/*Pre: An object of the foreground class passed as an argument
	 * Post: return whether the enemy is in contact with a wall or not (True:yes False:no) 
	 */
	public boolean hitWall(Foreground_Object tempObj) {
		Rectangle tempRec = tempObj.getBounds();
		if (getBounds().intersects(tempRec)){
			if (enemyXValue + enemy_width[enemyType - 1] >= tempRec.x && enemyXValue < tempRec.x) {//Check if the enemy is on contact with the wall
				enemyXValue = tempRec.x - enemy_width[enemyType - 1];//If it is, it resets the enemy'ies x value so it wouldn't be able to walk through it
			}
			else if (enemyXValue <= tempRec.x + tempRec.width){//Same as the previous if statement but on the other side of the wall
				enemyXValue = tempRec.x + tempRec.width;
			}
			
			return true;//return true if an intersection occurred
		}
		return false;//return false if no intersection occurred
	}
	/*Pre: none
	 * Post: runs the aiMovement method and the enemyGun's tick method 
	 */
	public void tick() {
		aiMovement();
		enemyGun.tick();
	}
	/*Pre: None
	 * Post: Shows the enemy's current health points 
	 */
	public void render (Graphics g) {
		if (walking == 2) {	//Checks if the enemy is facing to the right
			if (xVelocity == 0) {//Checks if the enemy is currently not moving, which the renders the appropriate sprite
				g.drawImage(sprite[0], enemyXValue + camera.getXOffset(), enemyYValue,enemy_width[enemyType-1],enemy_height[enemyType-1], null);//if walking is 2 (moving right)
				
			}
				else
				g.drawImage(sprite[frameCounter], enemyXValue + camera.getXOffset(), enemyYValue,enemy_width[enemyType-1],enemy_height[enemyType-1], null);	//draw the sprite on the screen, based on which part of the walking animation is
			
		}
		else if(walking == 1) {		//same as the one above, only except make sure frameCounter increments by 1 and adds 9 to get the second
									//half of the BufferedImage array. The second half contains walking left images.
				if (xVelocity == 0)
					g.drawImage(sprite[9], enemyXValue + camera.getXOffset(), enemyYValue, enemy_width[enemyType-1],enemy_height[enemyType-1], null);
				else
					g.drawImage(sprite[frameCounter + 9], enemyXValue + camera.getXOffset(), enemyYValue ,enemy_width[enemyType-1], enemy_height[enemyType-1], null);
			}
		frameCounter++;
		if (frameCounter > 8) {  //if frameCounter is greater than 8, reset it to 0 because there are only 9 images for walking animation
			frameCounter = 0;
		}
		if (enemyType !=1)
			enemyGun.render((Graphics2D)g);
	}
	/*Pre: None
	 * Post: Overries the toString method an outputs the health of the enemy
	 */
	public String toString() {
		return("Enemy: " + Integer.toString(hitPoints) + "HP");
	}

}

