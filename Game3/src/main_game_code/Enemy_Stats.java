package main_game_code;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entities_and_objects.Foreground_Object;

/* Name:Yusuf Ali
 * FileName:Enemy_Stats
 * LastDateofEdit:2019-12-19
 * Description: This class is where you can set and change the enemies HP, DPS and damage type(officer, air, tank);
 */
public class Enemy_Stats {
	private static BufferedImage[] enemy_sprites = {ImageLoader.loadImage("resources/animationSheets/drone_sprite_2.png"), ImageLoader.loadImage("resources/animationSheets/strong_defense_walk.png"),ImageLoader.loadImage("resources/animationSheets/officer_walk.png")};
	private int enemy_width[] = {67,33,28};
	private int enemy_height[] = {40,50,50};
	private int hitPoints;
	private int damagePoints;
	private int enemyType;
	private int xVelocity;
	private double yVelocity;
	private int enemyXValue;
	private int enemyYValue;
	private int frameCounter;
	private int freezeDistance, detectDistance;
	private Player player;
	private Camera camera;
	private BufferedImage [] sprite;
	private Enemy_Gun enemyGun;
	private int walking;
	private int original_hp;
	private int original_x;
	private int original_y;


	//private int stand;

	/*Pre: four integer variables passed as an argument
	 *post: Sets the values for the enemies hitpoints, damage and enemy type  
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
		for (int i = 0; i < 9; i++) {
			sprite[i] = enemy_sprites[enemyType - 1].getSubimage(i * enemy_width[enemyType - 1], 50, enemy_width[enemyType - 1], enemy_height[enemyType - 1]);
			sprite[i + 9] = enemy_sprites[enemyType - 1].getSubimage(i * enemy_width[enemyType - 1], 0, enemy_width[enemyType - 1], enemy_height[enemyType - 1]);
		}
		this.enemyGun = new Enemy_Gun(this.camera, ImageLoader.loadImage("resources/textures/laser_gun.png"),this,25, 12, player, direction);



	}
	public int[] getEnemy_width() {
		return enemy_width;
	}
	public int[] getEnemy_height() {

		return enemy_height;
	}
	public Enemy_Gun getGun() {
		return enemyGun;
	}
	public void resetEnemy() {
		hitPoints = original_hp;  
		enemyXValue = original_x;
		enemyYValue = original_y;
	}

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
			if (getEnemyType() == 1) //if statement to check what type of enemy therefore assigning it's appropriate velocity
				setxVelocity(5);
			if (enemyXValue - player.getX() > - freezeDistance) {//if statement to check if the enemy is close enough to attack
				xVelocity = 0;//Anything with  in this class is just here to be set as a basis for now and will be changed
				enemyGun.fire();
			}
			else
				if(getEnemyType() == 2) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
					setxVelocity(1);
					if (enemyXValue - player.getX() >-freezeDistance) {//if statement to check if the enemy is close enough to attack
						xVelocity = 0;
						enemyGun.fire();
					}
				}
				else
					if(getEnemyType() == 3) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
						setxVelocity(4);
						if (enemyXValue - player.getX() >-freezeDistance) {//if statement to check if the enemy is close enough to attack
							xVelocity = 0;
							enemyGun.fire();
						}
					}

		}
		else
			if(enemyXValue >  player.getX()  && enemyXValue - player.getX() < detectDistance) {//If statement to check if the player is to the left of the enemy 
				walking = 1;
				if (getEnemyType() == 1) {//These sets of if statements have the same purpose as the ones above
					setxVelocity(-5);
					if (enemyXValue - player.getX() < freezeDistance) {
						xVelocity=0;
						enemyGun.fire();
					}
				}
				else
					if(getEnemyType() == 2) {
						setxVelocity(-1);
						if (enemyXValue - player.getX() <freezeDistance) {
							xVelocity=0;
							enemyGun.fire();
						}
					}
					else
						if(getEnemyType() == 3) {
							setxVelocity(-4);
							if (enemyXValue - player.getX() < freezeDistance) {
								xVelocity = 0;
								enemyGun.fire();
							}
						}

			}
			else
				setxVelocity(0);


		enemyXValue += xVelocity;
		enemyYValue += yVelocity;
		if(getEnemyType() != 1)
			yVelocity += Player.GRAVITY;


	}
	public int getFreezeDistance() {
		return freezeDistance;
	}
	public void setFreezeDistance(int freezeDistance) {
		this.freezeDistance = freezeDistance;
	}
	public Rectangle getBounds() {
		if (hitPoints > 0)
			return new Rectangle (enemyXValue,enemyYValue,enemy_width[enemyType - 1],enemy_height[enemyType - 1]);
		else
			return new Rectangle (0,0,0,0);
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
				enemyYValue = tempRec.y - enemy_height[enemyType - 1] + 1;
				return true;
			}
		}
		return false;
	}
	public boolean hitWall(Foreground_Object tempObj) {
		Rectangle tempRec = tempObj.getBounds();
		if (getBounds().intersects(tempRec)){
			if (enemyXValue + enemy_width[enemyType - 1] >= tempRec.x && enemyXValue < tempRec.x) {
				enemyXValue = tempRec.x - enemy_width[enemyType - 1];
			}
			else if (enemyXValue <= tempRec.x + tempRec.width){
				enemyXValue = tempRec.x + tempRec.width;
			}

			return true;
		}
		return false;
	}
	public void tick() {
		aiMovement();
		enemyGun.tick();
	}
	/*Pre: None
	 * Post: Shows the enemy's current health points 
	 */
	public void render (Graphics g) {
		//g.fillRect(enemyXValue + camera.getXOffset(), enemyYValue, enemy_width[enemyType - 1], enemy_height[enemyType - 1]);
		//g.drawImage(img, enemyXValue + camera.getXOffset(), enemyYValue, enemy_width[enemyType - 1], enemy_height[enemyType - 1], null);
		if (walking == 2) {	
			if (xVelocity == 0) {
				g.drawImage(sprite[0], enemyXValue + camera.getXOffset(), enemyYValue,enemy_width[enemyType-1],enemy_height[enemyType-1], null);//if walking is 2 (moving right)

			}
			else
				g.drawImage(sprite[frameCounter], enemyXValue + camera.getXOffset(), enemyYValue,enemy_width[enemyType-1],enemy_height[enemyType-1], null);	//draw the sprite on the screen, based on which part of the walking animation is
		}
		else if(walking == 1) {		//same as the one above, only except make sure frameCounter2 increments by 1 and adds 9 to get the second
			//half of the BufferedImage array. The second half contains walking left images.
			if (xVelocity == 0)
				g.drawImage(sprite[9], enemyXValue + camera.getXOffset(), enemyYValue, enemy_width[enemyType-1],enemy_height[enemyType-1], null);
			else
				g.drawImage(sprite[frameCounter + 9], enemyXValue + camera.getXOffset(), enemyYValue ,enemy_width[enemyType-1], enemy_height[enemyType-1], null);
		}
		frameCounter++;
		if (frameCounter > 8) { 
			frameCounter = 0;
		}
		if (enemyType !=1)
			enemyGun.render((Graphics2D)g);
	}
	public String toString() {
		return("Enemy: " + Integer.toString(hitPoints) + "HP");
	}

}

