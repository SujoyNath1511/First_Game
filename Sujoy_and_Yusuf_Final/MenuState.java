/* Name:Yusuf Ali
 * FileName:MenuState
 * LastDateofEdit:2020-01-11
 * Description: This class is where is outputs the menu and also change the current state of the game to either the controls menu or the game
 */



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;


public class MenuState  {
	private BufferedImage laser_gun, laser, wasdKeys, mouseImg, spaceBar, keyA, keyD;
	private int state;
	private int mouseX,mouseY;
	private MouseManager mouse;
	private Game game;
	public MenuState(Frame frame, MouseManager mouse, Game game) {
		state = 0;//Initializes the state to the main menu
		laser_gun = ImageLoader.loadImage("resources/textures/laser_gun.png");//Gets the gun image
		laser = ImageLoader.loadImage("resources/textures/laser.png");//Gets the laser bullet image
		wasdKeys = ImageLoader.loadImage("resources/textures/wasd_keys.png");//Gets the wasd image
		mouseImg = ImageLoader.loadImage("resources/textures/mouse_leftclick.png");//Gets the mouse image
		spaceBar = ImageLoader.loadImage("resources/textures/space_bar.png");//Gets the spacebar image
		keyA = ImageLoader.loadImage("resources/textures/key_a.png");//Gets the A key image
		keyD = ImageLoader.loadImage("resources/textures/key_d.png");//Gets the D key image
		this.mouse = mouse;//Initializes the mouse object
		this.game = game;//Initializes the game object
	}
	/*Pre: none
	 * Post: Return the current state. Menu, controls or game
	 */
	public int isState() {
		return state;
	}
	/*Pre: An integer value pass as an argument
	 * Post: Assigns the new state the program should be on 
	 */
	public void setState(int state) {
		this.state = state;
	}
	/*Pre: none
	 * Post: Continuously checks where the players mouse is and runs the check mouse method
	 */
	public void tick() {
		mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
		mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		checkMouse();
	}

	public void render(Graphics g) {
		if (state == 0) {//Checks if the game is currently on the menu screen, which the renders the menu screen's features such as quit, start and controls
			g.setColor(Color.BLACK);
			g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+50, Frame.WINDOW_HEIGHT/2, 200,100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("START",Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+100, Frame.WINDOW_HEIGHT/2+60);
			g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+350, Frame.WINDOW_HEIGHT/2, 200,100);
			g.drawString("CONTROLS",710, Frame.WINDOW_HEIGHT/2+60);
			g.setFont(new Font("arial", Font.BOLD, 100));
			g.drawString("Welcome to No Land", 125, 100);
			g.drawRect(542, 582, 200,100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("QUIT",605, 642);
			g.drawImage(laser_gun, 427, 200, 200, 100, null);
			g.drawImage(laser, 669, 225, 200, 50, null);
			g.drawImage(laser, 900, 225, 200, 50, null);


		}
		else
			if(state == 2) {//Checks if the player is on the controls screen, which then outputs the controls to the game and also a back option
				g.setFont(new Font("arial", Font.BOLD, 30));
				g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+200, Frame.WINDOW_HEIGHT/2+100, 200,50);
				g.drawString("BACK",600, 520);
				g.drawImage(wasdKeys,180,116,180,116,null);
				g.drawString(" :MOVE", 381, 194);
				g.drawImage(mouseImg,826,110,180,150,null);
				g.drawString(":SHOOT", 1000, 194);
				g.drawImage(spaceBar,170,310,200,50,null);
				g.drawString(" + ", 250, 400);
				g.drawImage(keyA,240,415,50,50,null);
				g.drawString("TELEPORT LEFT", 150, 520);
				g.drawImage(spaceBar,880,310,200,50,null);
				g.drawString(" + ", 960, 400);
				g.drawImage(keyD,960,415,50,50,null);
				g.drawString("TELEPORT RIGHT", 850, 520);

			}

	}
	/*Pre: none
	 * Post: CHanges the state or quits according to what the player has selected 
	 */
	public void checkMouse() {
		if(state == 0) {//Checks if player is on the main menu 
			if(mouseX > 400 && mouseX < 600) {//Checks if the user hovering over the start button
				if(mouseY > 415 && mouseY < 515)
					if(mouse.getLeftPressed()==true) {//Checks if the left click has been pressed 
						state = 1;//Sets the state to game
						mouse.setLeftPressed(false);
					}
			}
			else
				if(mouseX > 700 && mouseX < 900) {//Checks if the user is hovering over the controls button
					if(mouseY > 415 && mouseY < 515)
						if(mouse.getLeftPressed()==true) {//Checks if the left click has been pressed
							state = 2;//Sets the state to controls
							mouse.setLeftPressed(false);
						}
				}
			if(mouseX > 550 && mouseX < 750)//Checks if the player is hovering over the quit button
				if(mouseY > 615 && mouseY < 715)
					if(mouse.getLeftPressed()==true) {//Checks if the left click has been pressed 
						game.setRunning(false);//sets setRunning to false in order to terminate the program
						mouse.setLeftPressed(false);
					}


		}
		else if (state == 2){//Else meaning if the player is not on menu means he has to be on controls 
			if(mouseX > 550 && mouseX < 700) //Checks if the mouse is hovering over the back button
				if(mouseY > 515 && mouseY < 565)
					if(mouse.getLeftPressed()==true) {//Checks if the left click has been pressed
						state = 0;//Sets the state back to main menu
						mouse.setLeftPressed(false);
					}
		}
		
	}
}
