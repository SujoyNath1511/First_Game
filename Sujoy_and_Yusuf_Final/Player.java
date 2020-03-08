/*
 * Name: Sujoy Deb Nath
 * Date Last Edited: December 19, 2019
 * Description: This is the Player class that stores all the player controls, sprites, and stats. 
 */


import java.awt.Color;
import java.awt.Font;
//All imports are below
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;



public class Player implements KeyListener{		//keyListener added in the Game Class
	public static final int PLAYER_HEIGHT = 50;
	public static final int PLAYER_WIDTH = 28;
	public static double GRAVITY = 683/960.0;		//standard gravitational constant for the game
	private int x;			//player x location
	private int y;			// player y location
	private int startX;		//player's spawn in the x 
	private int startY;		//player's spawn in the y 
	private int health;		//how much damage the player does
	private boolean falling;		//boolean value to determine if the player is falling or not
	private int speed;			//player's speed
	private int teleport_x;			//teleport distance in the x
	private int teleport_bar;			//A variable to determine how much of the teleport bar is filled	
	private BufferedImage[] walk;	//array for when the player moves (animation)
	private BufferedImage tempImg;		//player's sprite sheet
	private int walking;			//a int value to check to see if the player is heading left or right
	private int frameCounter;		//a frameCounter to run moving animation
	private boolean[] keys;		//an array to see which keys have been pressed
	private double yVel;		//the y velocity of the player
	private Camera camera;		//a camera object for movement
	private World world;		//world object for the world
	private int teleport_timer;		//a timer to run teleport cool down
	private Player_Gun gun;		//player's gun
	private int respawn_timer;			//a respawn timer that sets when the player respawns
	private int deathCounter;			//a counter to see how many times the player died (how many lives were spent)
	private boolean dead;			//variable to determine if the player is dead, prevents respawn timer from being reset mid respawn so the 
									//player never respawns
	private boolean bossFight;			//checks to see if the player is in the boss room thus the bossfight begins
	private int checkPointCounter;		//checks to see if the player has reached a checkpoint (health upgrade)


	/*
	 * pre:none
	 * post: Almost all variables have been initialized
	 * Description: Initializes all variables and objects
	 */
	public Player(Camera camera, World world) {
		this.world = world;
		this.camera = camera;
		this.gun = new Player_Gun(this.camera, ImageLoader.loadImage("resources/textures/laser_gun.png"),this,36, 17);
		falling = true;					//player didn't already jump
		frameCounter = 0;				//set to 0 (first frame)
		walking = 2;					//facing right
		walk = new BufferedImage[18];		//has a size of 18 because walking in each direction has 9 images each, so in total 18	
		tempImg = ImageLoader.loadImage("resources/animationSheets/player_walk.png");
		startX = 0;			//starting positions
		startY = 525;
		x = startX;		//player's starting x coordinate
		y = startY;		//player's starting y coordinate
		checkPointCounter = 1;			//set the first checkpoint to be spawn point
		health = 100 * checkPointCounter;	//health (subject to change)
		speed = 4;				//initial horizontal movement velocity
		keys = new boolean[5];		//a boolean array used to check to see when a key has been pressed
		yVel = 0;					//set the initial y velocity to 0
		teleport_x = 215;				//the teleport distance is 215 pixels in either direction
		for (int j = 0; j < keys.length; j++)			//initialize all the keys to false (no key has been pressed)
			keys[j] = false;

		//underneath, I get all the images I need for the walking animation using the subImage from BufferedImage
		for (int i = 0; i < 9; i++) {
			walk[i] = tempImg.getSubimage(i * PLAYER_WIDTH,50, PLAYER_WIDTH, PLAYER_HEIGHT);
			walk[i + 9] = tempImg.getSubimage(i * PLAYER_WIDTH,0, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		teleport_bar = 0;		//set the teleport to 0 at the beginning
		respawn_timer = 0;		//set the respawn timer to 0
		teleport_timer = 0;
		deathCounter = 0;
		dead = false;			//the player isn't dead when they spawn
		bossFight = false;			//the player isn't in the boss room
	}

	/*
	 * pre: player is instantiated
	 * post: returns the bossFight
	 * Description: returns wither the player is in the boss room or not
	 */
	public boolean isBossFight() {
		return bossFight;
	}

	/*
	 * pre: player is instantiated
	 * post: sets bossFight
	 * Description:sets whether the boss fight is happening or not
	 */
	public void setBossFight(boolean bossFight) {
		this.bossFight = bossFight;
	}

	/*
	 * pre: health has been initialized
	 * post: returns health
	 * Description: Returns player health
	 */
	public int getHealth() {
		return health;
	}
	/*
	 * pre: x has been initialized
	 * post: return's player's x coordinate
	 * Description: Gets the player's x coordinate
	 */
	public int getX() {
		return x;
	}
	/*
	 * pre: y has been initialized
	 * post: returns y coordinate
	 * Description: Gets the player's y coordinate
	 */
	public int getY() {
		return y;
	}
	/*
	 * pre: player has collided with a bullet of type 2 or 3
	 * post: player now has a lower health, player may have died
	 * Description: This method runs player's collison with projectiles and subtracts health accordingly
	 */
	public void bullet_collision(Projectile projectile) {
		health -= projectile.getDamage();		//subtract the damage from the player's health
		if (health <= 0 && dead == false) {		//if the player's health is below 0, add 1 to death counter and set the respawn timer
			dead = true;
			respawn_timer = 180;
			deathCounter += 1;
		}
	}
	/* pre: none
	 * post: none
	 * Description: Does nothing. Only here because its a required method to implement keyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	/*
	 * pre: none
	 * post: keys array at specific indexes are set to true (key has been pressed). Player also jumps
	 * Description: Sets the initial jump speed and keys array values based on key presses.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int	key = e.getKeyCode();		//gets which key is pressed
		switch (key) {				// a switch case to see which key is being pressed
		case KeyEvent.VK_W:
			//keys[0] = true;
			if (falling == false) {		// if W was pressed, set jump to -13 (go up) and set falling to true.
				yVel = -13;			
				falling = true;	// sets falling to true, so that the player can't jump while mid air
			}
			break;
		case KeyEvent.VK_A:		//if a is pressed, set the corespondent key in the keys array to true 
			keys[1] = true;
			break;
		case KeyEvent.VK_D:		//if D is pressed, set the coresponded keys in the keys array to true
			keys[3] = true;	
			break;
		case KeyEvent.VK_SPACE:
			if (teleport_timer <= 0) {		//if space bar is pressed, set that key to true and set a teleport timer (cooldown)
				keys[4] = true;
				teleport_timer = 180;		//a cooldown of 2 to 3 seconds (depends on the game tickRate/FPS)
				teleport_bar = 0;			//set the teleport bar to 0 to show that the player has ran out of teleportation juice
			}
			break;
		}
		

	}

	/*
	 * pre: A key has been pressed
	 * post: Sets speed to 0 after A or D have been released
	 * Description: Does an action when a key has been released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {			//if a or d or spacebar is released, set their corresponding indexes to false. makes for smoother
											//controls
			keys[1] = false;
		}
		else if (key == KeyEvent.VK_D) {
			keys[3] = false;
		}
		else if (key == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}

	}
	/*
	 * pre: gun has been instantiated
	 * post: returns player_gun
	 * Description: Returns the instance of Player_Gun
	 */
	public Player_Gun getGun() {
		return gun;
	}

	/*
	 * pre: none
	 * post: sets the instance of Player_Gun
	 * Description: Sets the instance of Player_Gun
	 */
	public void setGun(Player_Gun gun) {
		this.gun = gun;
	}

	/*
	 * pre: none
	 * post: player's x and y have been updated
	 * Description: Updates player's x and y location
	 */
	public void movement () {
		int tempX = x;		//temporary variable to store the last known x position
		y += yVel;				//add the yVel to the y position and add gravity to the yVel like in real physics
		yVel += GRAVITY;
		if (keys[1] == true) {		//if the player pressed a
			if (keys[4] == true) {			//if the player pressed spacebar as well, teleport the player left
				x -= teleport_x;
				if (tempX > (world.getWorldWidth() - Frame.WINDOW_WIDTH) && x <= 6588 ) {
					x = 6589;			//if the player is in the boss room, then the player cannot teleport back out
				}
				keys[4] = false;		//set the spacebar to false 
			}
			else {
				x -= speed;		//if the player only pressed a, then make the player move left
			}
		}
		else if (keys[3] == true) {		//do the same for when the player is moving right as for when the player is moving left
			if (keys[4] == true) {
				x += teleport_x;
				keys[4] = false;
			}
			else {
				x += speed;
			}
		}
		if (x < 0) {		//if the player is trying to go off the world on the left, then make the player's x position to 0
			x = 0;
		}
		else if (x + PLAYER_WIDTH > world.getWorldWidth()) {
			x = world.getWorldWidth() - PLAYER_WIDTH;		//if the player is trying to go off the world on the right, then make the player's
															//x position to the world width - player with (an invisible wall)
		}
		if (y > world.getWorldHeight()) {
			health = 0;			//if the player falls of the world, set a respawn timer and set the health to 0 (player is dead)
			respawn_timer = 180;
			dead = true;
			deathCounter ++;		//take out a life
		}
	}

	/*
	 * pre: deathCounter and player have been initialized and instantiated
	 * post: returns deathCounter
	 * Description: Returns how many times the player has died in the current run
	 */
	public int getDeathCounter() {
		return deathCounter;
	}

	/*
	 * pre: player has died more than 3 times
	 * post: deathCounter = 0, checkPointCounter = 1, enemies have been reset, boss has been reset, menuState is 0, player has been reset
	 * Description:
	 */
	public void setDeathCounter(int deathCounter) {
		this.deathCounter = deathCounter;
		startX = 0;			//starting positions
		startY = 525;
		respawn_timer = 0;		//sets respawn timer to 0 to respawn the player
		health = 0;	
		checkPointCounter = 1;		//set checkPointCounter to 1 (no checkpoints have been reached)
	}

	/*
	 * pre: Player has been instantiated
	 * post: Player's positions have been updated, player's health, teleport bar and gun have been updated
	 * Description: This method updates the player based on environmental and game eventsb(including controls)
	 */
	public void tick() {
		respawn_timer--;	//set the respawn timer to count down per tick, same for teleport timer
		teleport_timer --;
		if (teleport_timer <= 0) {
			teleport_bar = 180;		//if the teleport timer is 0, then set the bar to 180 (teleport juice is full)
		}
		else {
			teleport_bar++;		//add one to teleport bar to give the illusion of a refill animation
		}
		if (health > 0) {		//if the player's health is above 0, then render the player's movement
			movement();
		}
		else {
			if (respawn_timer <= 0) {	//else check to see if the timer has reached 0
				x = startX;		//spawn the player and give them the appropriate amount of health
				y = startY;
				health = 100 * checkPointCounter;
				camera.setXOffset(0);
				respawn_timer = 0;		//reset the timer
				dead = false;			//set dead to false

			}
		}
		if (x >= (world.getWorldWidth() - Frame.WINDOW_WIDTH)) {
			camera.setXOffset(-world.getWorldWidth() + Frame.WINDOW_WIDTH);		//if the player is in the boss room, then set boss fight to true
			bossFight = true;									//and set the camera offset to only cover the boss room	
		}
		else if (x >= Frame.WINDOW_WIDTH/2) {		//else, center the camera around the player
			camera.center(this);
		}
		if(keys[1] == false && keys[3] == false) {	//if the player didn't click either a or d, run the first image in the sprite sheet (standing)
			frameCounter = 0;
		}
		if (x + PLAYER_WIDTH/2 >= gun.getCursorX()) {	//set which direction the player is facing based on cursor position
			walking = 1;
		}
		else {
			walking = 2;
		}
		gun.tick();		//run Player_Gun tick
	}
	/* pre: none
	 * post: returns a rectangle the same size as the image
	 * Description: This method returns a rectangle the same size as the player image in order to run collision detection.
	 */
	public Rectangle getBounds() {
		return new Rectangle (x,y,PLAYER_WIDTH,PLAYER_HEIGHT);
	}
	/*
	 * pre: the player is mid air (not interacting with any block)
	 * post: falling = true
	 * Description: Sets the player's falling variable to let the game know that the player should be in freefall and cannot jump
	 */
	public void setFalling() {
		falling = true;
	}

	/*
	 * pre: Player has been instantiated
	 * post: yVel = 0|| yVel = 2, falling = true || falling = false, y has changed so that the player doesn't do through blocks
	 * Description: This method sets it so that the player doesn't fall through platforms and bounces of of platforms if they hit their head on them
	 */
	public boolean freefall(Foreground_Object tempObj, int type) {
		Rectangle tempRec = tempObj.getBounds();		//get the platform's hitbox

		if (getBounds().intersects(tempRec) == true) {		//if the player and the platform intersect
			if (type == 1) {		//if the player is approaching the platform from the bottom, make it so the player bounces off
				yVel = 2;
				y = tempRec.y + tempRec.height;		//set the height so that the player doesn't go into the block
				return false;		//player is still falling
			}
			else {

				falling = false;		//else the player is approaching from above, thus we set falling to false (the player can jump)
				yVel = 0;	//set the velocity to 0
				y = tempRec.y - PLAYER_HEIGHT + 1;	//set the y so that one pixel row is interacting with the block (so that falling is
													//continously false)
				return true;		//player is on solid ground
			}
		}
		return false;		//return false for interaction
	}
	/*
	 * pre: player has been instantiated
	 * post: sets the player's x to either side of a wall, depending on where the player's approaches from
	 * Description: makes it so the player can't walk through walls
	 */
	public boolean hitWall(Foreground_Object tempObj) {
		Rectangle tempRec = tempObj.getBounds();
		if (getBounds().intersects(tempRec)){		//check for collision
			if (x + PLAYER_WIDTH >= tempRec.x && x < tempRec.x) {
				x = tempRec.x - PLAYER_WIDTH;		//if the player's left side is outside the wall and right side is in the wall, make the
													//set the player's x on the left side so they don't pass through
			}
			else if (x <= tempRec.x + tempRec.width){
				x = tempRec.x + tempRec.width;			//else if the player is inside the block, make the player be on the wall's right side
			}
			return true;		//return true for hitting
		}
		return false;		//return false if the player didn't interact with a block
	}
	/*
	 * pre: player has interacted with a checkpoint block
	 * post: player's startX and startY have changed, health has been reset or has increased
	 * Description: this method resets the player's spawn point, health and if it's the first time the player has interacted with a checkpoint,
	 * then increases/doubles the player's health
	 */
	public void setSpawn(int num) {	
		startX = x;		//set new x spawn position
		startY = y;		//set new y spawn position
		checkPointCounter = num;		//set checkPointcounter to a new value (2)
		health = 100 * checkPointCounter;	//increase the player's health or reset it
	}
	/*
	 * pre: walking and frameCounter have been initialized
	 * post: the player has been drawn on the screen with walking animation; lives, health bar and teleport bar have been rendered on the screen
	 * Description: This method renders(draws the player) onto the buffer and uses animation
	 */
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(7, 36,teleport_bar,18);			//draws the teleport bar
		g.setColor(Color.RED);
		g.fillRect(7, 7,health * 2, 18);			//draws the health bar
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString("Health: " + Integer.toString(health), 15, 20);	//writes how much health you have left
		for (int i = 0; i < 3 - deathCounter; i++) {
			g.setColor(Color.red);
			g.fillOval(25 * (i + 1), 60, 20, 20);			//draws a red circle for the number of lives you have left
			
		}
		if (health <= 0) {	//if the player's health is below 0, then write game over on the screen
			g.setColor(Color.BLACK);
			g.setFont(new Font("arial", Font.BOLD, 72));
			g.drawString("GAME OVER", Frame.WINDOW_WIDTH/2, Frame.WINDOW_HEIGHT/2 - 100);
			frameCounter = 0;
		}
		else {	//else draw the player onto the screen
			if (walking == 2) {			//if walking is 2 (cursor is aimed right)
				g.drawImage(walk[frameCounter], x + camera.getXOffset(), y,PLAYER_WIDTH,PLAYER_HEIGHT, null);	//draw the sprite on the screen, based on which part of the walking animation is currently playing
			}
			else if(walking == 1) {		//same as the one above, only except make sure frameCounter2 increments by 1 and adds 9 to get the second
				//half of the BufferedImage array. The second half contains walking left images.
				g.drawImage(walk[frameCounter + 9], x + camera.getXOffset(), y,PLAYER_WIDTH,PLAYER_HEIGHT, null);
			}
			frameCounter++;	//increment the frameCounter
			if (frameCounter > 8) {
				frameCounter = 0;		//if the player has reached the last animation, reset frame counter
			}
			gun.render((Graphics2D) g);		//draw the player's gun
		}
	}
}
