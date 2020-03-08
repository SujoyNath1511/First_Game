/*
 * Name: Sujoy Deb Nath, Yusuf Ali
 * Date Last Editied: January 13, 2020
 * Description: This is the boss class that runs all of the boss's behaviours and functions. Basically the Boss AI
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss  {
	private int bossXValue;
	private int bossYValue;
	private int bossHitPoints;
	private int bossDamagePoints;		//how much damage the boss should do (arbitrary)
	private int bossXVelocity;
	private Player player;				//a player object to keep track of the player
	private Camera camera;				//a camera object to render
	private int freezeDistance;			//how far the boss should freeze away from the player before firing
	private BufferedImage tempImg;		//a temporary image that will contain the boss's sprite sheet
	private int frameCounter;			//frame counter for aniamtion
	private BufferedImage [] sprites;		//an array of buffered images for rendering the boss
	private int bossWidth;		
	private int bossHeight;
	private Boss_Gun bossGun1;				//the main big turret
	private Boss_Gun bossGun2;				//a non rendered special turret that runs an area of affect attack
	private Boss_Gun bossGun3;			//a machine gun
	private int ticks;			//a timer to run the boss's date animation using the game loop timer
	private MenuState menu;		//an object of menu to return to menu.

	public Boss(int newHitPoints, int newDamagePoints, int x, int y, Camera camera, Player player) {
		this.bossXValue = x;		
		this.bossYValue = y;
		bossWidth = 339;
		bossHeight = 145;
		freezeDistance = 600;
		this.camera = camera;		//getting our camera and player
		this.player = player;
		bossHitPoints = newHitPoints;
		this.tempImg = ImageLoader.loadImage("resources/animationSheets/boss.png");		//loading the boss's spritesheet
		//Underneath, I instantiate an instance of all three guns using the Boss_Gun class
		bossGun1 = new Boss_Gun(bossXValue + 155, bossYValue, camera,ImageLoader.loadImage("resources/textures/tank_gun_1.png") , 208, 22, this, player, newDamagePoints, 1);
		bossGun2 = new Boss_Gun(bossXValue + 100, bossYValue + 10, camera, null, 50,30,this,player,newDamagePoints, 2);
		bossGun3 = new Boss_Gun(bossXValue + 150, bossYValue + 45, camera,ImageLoader.loadImage("resources/textures/tank_gun_2.png") , 60,30, this, player, newDamagePoints, 3);
		frameCounter = 0;		//starting frame
		sprites = new BufferedImage[12];
		//underneath I crop out specific images from the boss sprite sheet (tempImg)
		for (int i = 0; i < 12; i++) {
			sprites[i] = tempImg.getSubimage(i * bossWidth * 2, 0,bossWidth * 2, bossHeight * 2);
		}
		ticks = 0;	//starting ticks to 0 
	}

	/*
	 * pre: menu exists and boss has been instantiated
	 * post: returns the menu
	 * Description: A method to return the menu object
	 */
	public MenuState getMenu() {
		return menu;
	}

	/*
	 * pre: none
	 * post: menu has been instantiated
	 * Description: Sets a MenuState object
	 */
	public void setMenu(MenuState menu) {
		this.menu = menu;
	}

	/*
	 * pre: none
	 * post: boss hitpoints have been set
	 * Description: Sets the boss hitpoints
	 */
	public void setBossHitPoints(int bossHitPoints) {
		this.bossHitPoints = bossHitPoints;
	}

	/*
	 * pre: boss has been instantiated and player.isBossFight() is true
	 * post: boss's position has been updated and the boss attacked
	 * Description: Updates the boss's position and the boss attacks
	 */
	public void bossMovement() {
		if(bossXValue  <  player.getX()) {//if statement to check if the player is to the right of the enemy
			bossXVelocity = 3;
			if (bossXValue - player.getX() > -freezeDistance) {
				bossXVelocity = 0;
				bossGun1.attack();
				bossGun2.attack();
				bossGun3.attack();
			}

		}
		else
			if(bossXValue  >  player.getX()) {
				bossXVelocity = -3;					//setting the velocity if the player is to the left of the boss
				if(bossXValue - player.getX() < freezeDistance) {		//stop the boss and fire all guns
					bossXVelocity = 0;
					bossGun1.attack();
					bossGun2.attack();
					bossGun3.attack();
				}
			}
		bossXValue += bossXVelocity;		//move the boss
		if (bossXValue + bossWidth > 7911) {
			bossXValue = 7911 - bossWidth;			//if the boss tries to go out of the world, stop him
		}
		else if (bossXValue < 6588){			//makes sure the boss stays within the bounds of the arena
			bossXValue = 6890;
		}
	}

	/*
	 * pre: bossGun1 has been instantiated
	 * post: returns bossGun1
	 * Description: Returns the main turret
	 */
	public Boss_Gun getBossGun1() {
		return bossGun1;
	}
	/*
	 * pre: bossGun2 has been instantiated
	 * post: returns bossGun2
	 * Description: returns the special turret
	 */
	public Boss_Gun getBossGun2() {
		return bossGun2;
	}
	/*
	 * pre: bossGun3 has been instantiated
	 * post: returns bossGun3
	 * Description: returns the machine gun
	 */
	public Boss_Gun getBossGun3() {
		return bossGun3;
	}
	/*
	 * pre: none
	 * post: sets bossGun1
	 * Description: Sets bossGun1
	 */
	public void setBossGun1(Boss_Gun bossGun1) {
		this.bossGun1 = bossGun1;
	}
	/*
	 * pre: none
	 * post: sets bossGun2
	 * Description: Sets bossGun2
	 */
	public void setBossGun2(Boss_Gun bossGun2) {
		this.bossGun2 = bossGun2;
	}
	/*
	 * pre: none
	 * post: sets bossGun3
	 * Description: Sets bossGun3
	 */
	public void setBossGun3(Boss_Gun bossGun3) {
		this.bossGun3 = bossGun3;
	}
	/*
	 * pre: none
	 * post: gets the boss's x position
	 * Description: Returns the boss's x position
	 */
	public int getBossXValue() {
		return bossXValue;
	}
	/*
	 * pre: none
	 * post: returns the boss's y position
	 * Description: Gets the boss's y position
	 */
	public int getBossYValue() {
		return bossYValue;
	}
	/*
	 * pre: none
	 * post: gets the boss's damage
	 * Description: Returns the boss's damage
	 */
	public int getBossDamagePoints() {
		return bossDamagePoints;
	}
	/*
	 * pre: none
	 * post: returns bossHitPoints
	 * Description: gets the boss's health
	 */
	public int getBossHitPoints() {
		return bossHitPoints;
	}
	/*
	 * pre: boss has been instantiated
	 * post: Boss has been drawn onto the screen if the player is in the arena
	 * Description: Renders the boss onto the screen
	 */
	public void render (Graphics g) {
		if (player.isBossFight() == true) {
			if (bossHitPoints > 0) {
				bossGun1.render((Graphics2D) g);
				bossGun2.render((Graphics2D) g);
				if (bossXVelocity == 0) {
					g.drawImage(sprites[0], bossXValue + camera.getXOffset(), bossYValue,bossWidth,bossHeight, null);//if walking is 2 (moving right)

				}
				else
					g.drawImage(sprites[frameCounter], bossXValue + camera.getXOffset(),bossYValue,bossWidth,bossHeight, null);	//draw the sprite on the screen, based on which part of the walking animation is
				frameCounter++;
				if (frameCounter > 8) { 
					frameCounter = 0;
				}
				bossGun3.render((Graphics2D) g);
			}
			else {	//print You win onto the screen if the player beats the boss, also run the death animation
				if (ticks <= 180)
					g.drawImage(sprites[ticks/60 + 8], bossXValue + camera.getXOffset(),bossYValue, bossWidth, bossHeight, null);	//run boss death animation
				else {
					g.setColor(Color.white);
					g.setFont(new Font("arial", Font.BOLD, 70));
					g.drawString("You Win", Frame.WINDOW_WIDTH/2, 450);
				}
			}
		}
	}
	/*
	 * pre: boss has been instantiated
	 * post: boss is either dead or boss's position has been updated
	 * Description: Updates the boss's positions and behaviours
	 */
	public void tick () {
		if (player.isBossFight() == true) {
			if (bossHitPoints > 0) {
				bossGun1.tick();
				bossGun2.tick();
				bossGun3.tick();
				bossMovement();
			}
			else {		//start adding to ticks when the boss dies
				ticks++;
				if (ticks >= 360) {		//if 4 to 6 seconds have passed (depends on the fps you are running at) since the boss died:
					menu.setState(0);			//go back to main menu
					bossHitPoints = 1000;		//reset the boss's hitpoints, and position
					bossXValue = 7572;
					bossYValue = 424;
					player.setDeathCounter(3);		//set the player deathcounter to 3 (resets the player back to the beginning
					player.setBossFight(false);			//set boss fight to false
					ticks = 0;		//reset ticks
				}
			}
		}
	}
	/*
	 * pre: boss has collided with a bullet from the player (type 1)
	 * post: boss has less hitPoints
	 * Description: Bullets do damage to the boss if they interact with the boss and they are from player
	 */
	public void boss_bullet_collision(Projectile projectile) {
		bossHitPoints -= projectile.getDamage();
	}
	/*
	 * pre: boss has been instantiated
	 * post: returns a rectangle with the same dimensions and position as the boss
	 * Description: Gets the boss's hit box
	 */
	public Rectangle getBounds() {
		return new Rectangle(bossXValue,bossYValue,bossWidth,bossHeight);
	}
	/*
	 * pre: none
	 * post: returns the boss's width
	 * Description: gets the boss's width
	 */
	public int getBossWidth() {
		return bossWidth;
	}
	/*
	 * pre: none
	 * post: returns the boss's height
	 * Description: returns the boss's height
	 */
	public int getBossHeight() {
		return bossHeight;
	}

}
