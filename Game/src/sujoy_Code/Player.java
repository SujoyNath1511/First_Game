package sujoy_Code;
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

import entities_and_objects.Foreground_Object;
import sprites.SpriteSheet;
import worlds.World;

public class Player implements KeyListener{		//keyListener added in the Game Class
	public static final int PLAYER_HEIGHT = 50;
	public static final int PLAYER_WIDTH = 28;
	public static double GRAVITY = 683/960.0;
	private int x;			//player x location
	private int y;			// player y location
	private int startX;
	private int startY;
	private int health;		//player's health
	private int damage;			//how much damage the player does
	private boolean falling;		
	private int speed;			//player's speed
	private int teleport_x;			//teleport distance in the x (needs more work)
	private long teleport_bar;			//A variable that checks the current time with when the space bar has been last pressed.	
	private BufferedImage[] walk;	//array for when the skeleton moves (animation)
	//private SpriteSheet tempImg;	//temporary storage of the full spritesheet (a spritesheet object)
	private BufferedImage tempImg;
	private int walking;			//a int value to check to see if the player is moving left, right or not at all
	private int frameCounter;		//a frameCounter to run moving right animation
	private boolean[] keys;		//an array to see which keys have been pressed
	private double yVel;		//the y velocity of the player
	private Camera camera;		//a camera object for movement
	private World world;		//world object for the first world
	private long timePressed;		//a long variable to check to see when 
	private long now;
	private Player_Gun gun;
	private long timeDied;
	private long respawn_timer;


	/*
	 * pre:none
	 * post: Almost all variables have been initialized
	 * Description: Initializes all variables and objects
	 */
	public Player(SpriteSheet sheet, Camera camera, World world) {
		this.world = world;
		this.camera = camera;
		this.gun = new Player_Gun(this.camera, ImageLoader.loadImage("/textures/laser_gun.png"),this,36, 17);
		falling = true;					//player didn't already jump
		frameCounter = 0;				//set to 0 (first frame)
		walking = 2;
		walk = new BufferedImage[18];		//has a size of 18 because walking in each direction has 9 images each, so in total 18
		//this.tempImg = sheet;		
		tempImg = ImageLoader.loadImage("/animationSheets/player_walk.png");
		startX = 0;
		startY = 525;
		x = startX;		//player's starting x coordinate
		y = startY;		//player's starting y coordinate
		health = 100;	//health (subject to change)
		damage = 20;		//initial damage (subject to change)
		speed = 4;			//initaial horizontal movement velocity
		keys = new boolean[5];
		yVel = 0;
		teleport_x = 213;
		for (int j = 0; j < keys.length; j++)
			keys[j] = false;

		//underneath, I get all the images I need for the walking animation using the crop method from SpriteSheets
		for (int i = 0; i < 9; i++) {
			walk[i] = tempImg.getSubimage(i * PLAYER_WIDTH,50, PLAYER_WIDTH, PLAYER_HEIGHT);
			walk[i + 9] = tempImg.getSubimage(i * PLAYER_WIDTH,0, PLAYER_WIDTH, PLAYER_HEIGHT);
		}
		now = 0;
		teleport_bar = 0;
		respawn_timer = 0;
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
	 * pre: damage has been initialized
	 * post: returns damage
	 * Description: Gets how much damage the player does
	 */
	public int getDamage() {
		return damage;
	}
	/* pre: none
	 * post: none
	 * Description: Does nothing. Only here because its a required method to implement keyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	/*
	 * pre: none
	 * post: jump and speed change values based on key presses
	 * Description: Sets the initial jump speed and player x velocity based on key presses. Also sets the walking variable for the render method
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int	key = e.getKeyCode();		//gets which key is pressed
		switch (key) {				// a switch case to see which key is being pressed
		case KeyEvent.VK_W:
			//keys[0] = true;
			if (falling == false) {		// if W was pressed, set jump to -10 (go up) and set jumped to true. Also get the last known y coordinate
				yVel = -15;			//before jumping (temporary solution to jumping. Will be improved with collision detection later)
				falling = true;	//also it makes it so that the player doesn't keep going up while pressing w.
			}
			break;
		case KeyEvent.VK_A:		//if a is pressed, set walking to 1 and speed to -2
			keys[1] = true;
			//speed = -5;
			break;
		case KeyEvent.VK_D:		//if D is pressed, set walking to 2 and speed to 2
			keys[3] = true;
			//speed = 5;
			break;
		case KeyEvent.VK_SPACE:
			timePressed = System.currentTimeMillis();
			if (timePressed - now >= 3000) {
				keys[4] = true;
				now = timePressed;
				teleport_bar = 0;
			}
			break;
		case KeyEvent.VK_P:
			System.out.println("Position: " + x + ", " + y);
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
		if (key == KeyEvent.VK_A) {
			keys[1] = false;
		}
		else if (key == KeyEvent.VK_D) {
			keys[3] = false;
		}
		else if (key == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}

	}
	public Player_Gun getGun() {
		return gun;
	}

	public void setGun(Player_Gun gun) {
		this.gun = gun;
	}

	/*
	 * pre: none
	 * post: player's x and y have been updated
	 * Description: Updates player's x and y location
	 */
	public void movement () {
		int tempX = x;
		y += yVel;
		yVel += GRAVITY;
		if (keys[1] == true) {
			if (keys[4] == true) {
				x -= teleport_x;
				if (tempX > (world.getWorldWidth() - Frame.WINDOW_WIDTH) && x <= 6588 ) {
					x = 6589;
				}
				keys[4] = false;
			}
			else {
				x -= speed;
			}
		}
		else if (keys[3] == true) {
			if (keys[4] == true) {
				x += teleport_x;
				keys[4] = false;
			}
			else {
				x += speed;
			}
		}
		if (x < 0) {
			x = 0;
		}
		else if (x + PLAYER_WIDTH > world.getWorldWidth()) {
			x = world.getWorldWidth() - PLAYER_WIDTH;
		}
		if (y > world.getWorldHeight()) {
			health = 0;
			timeDied = System.currentTimeMillis();
		}
	}
	public void tick() {
		respawn_timer = System.currentTimeMillis();
		teleport_bar = System.currentTimeMillis() - now;
		if (teleport_bar > 3000) {
			teleport_bar = 3000;
		}
		if (health > 0) {
			movement();
		}
		else {
			if (respawn_timer - timeDied >= 3000) {
				x = startX;
				y = startY;
				health = 100;
				camera.setXOffset(0);
				respawn_timer = 0;

			}
		}
		if (x >= (world.getWorldWidth() - Frame.WINDOW_WIDTH)) {
			camera.setXOffset(-world.getWorldWidth() + Frame.WINDOW_WIDTH);
		}
		else if (x >= Frame.WINDOW_WIDTH/2) {
			camera.center(this);
		}
		if(keys[1] == false && keys[3] == false) {
			frameCounter = 0;
		}
		if (x + PLAYER_WIDTH/2 >= gun.getCursorX()) {
			walking = 1;
		}
		else {
			walking = 2;
		}
		gun.tick();
	}
	/* pre: none
	 * post: returns a rectangle the same size as the image
	 * Description: This method returns a rectangle the same size as the player image in order to run collision detection.
	 */
	public Rectangle getBounds() {
		return new Rectangle (x,y,PLAYER_WIDTH,PLAYER_HEIGHT);
	}
	public void setFalling() {
		falling = true;
	}

	public boolean freefall(Foreground_Object tempObj, int type) {
		Rectangle tempRec = tempObj.getBounds();

		if (getBounds().intersects(tempRec) == true) {
			if (type == 1) {
				yVel = 2;
				y = tempRec.y + tempRec.height;
				return false;
			}
			else {

				falling = false;
				yVel = 0;
				//keys[0] = false;
				y = tempRec.y - PLAYER_HEIGHT + 1;
				return true;
			}
		}
		return false;
	}
	public boolean hitWall(Foreground_Object tempObj) {
		Rectangle tempRec = tempObj.getBounds();
		if (getBounds().intersects(tempRec)){
			if (x + PLAYER_WIDTH >= tempRec.x && x < tempRec.x) {
				x = tempRec.x - PLAYER_WIDTH;
			}
			else if (x <= tempRec.x + tempRec.width){
				x = tempRec.x + tempRec.width;
			}
			return true;
		}
		return false;
	}
	public void setSpawn() {
		startX = x;
		startY = y;
	}
	/*
	 * pre: walking and frameCounter have been initialized
	 * post: the player has been drawn on the screen with walking animation
	 * Description: This method renders(draws the player) onto the buffer and uses animation
	 */
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(7, 36,(int)(teleport_bar/13),18);
		g.setColor(Color.RED);
		g.fillRect(7, 7,(int)(health * 4), 18);
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 16));
		g.drawString("Health: " + Integer.toString(health), 14, 20);
		if (health <= 0) {
			g.setFont(new Font("arial", Font.BOLD, 72));
			g.drawString("GAME OVER", Frame.WINDOW_WIDTH/2, Frame.WINDOW_HEIGHT/2 - 100);
			frameCounter = 0;
		}
		else {
			g.drawString(x + ", " + y, 10, 80);
			if (walking == 2) {			//if walking is 2 (moving right)
				g.drawImage(walk[frameCounter], x + camera.getXOffset(), y + camera.getYOffset(),PLAYER_WIDTH,PLAYER_HEIGHT, null);	//draw the sprite on the screen, based on which part of the walking animation is currently playing
			}
			else if(walking == 1) {		//same as the one above, only except make sure frameCounter2 increments by 1 and adds 9 to get the second
				//half of the BufferedImage array. The second half contains walking left images.
				g.drawImage(walk[frameCounter + 9], x + camera.getXOffset(), y + camera.getYOffset(),PLAYER_WIDTH,PLAYER_HEIGHT, null);
			}
			frameCounter++;
			if (frameCounter > 8) {
				frameCounter = 0;
			}
			gun.render((Graphics2D) g);
		}
	}
}
