/*
 * Name: Sujoy Deb Nath
 * Last Edited: December 19,2019
 * Description: This is the main method that runs the game. It creates instances of all other classes and runs the main game.
 */

package sujoy_Code;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import sprites.SpriteSheet;
import worlds.World;

public class Game implements Runnable{		//runnable lets me use threads
	private Frame window;		//the window/frame of the game
	private Player player;
	private Thread gameThread;		//a thread to run the game
	private boolean isRunning;
	private BufferStrategy buffer;		//buffers for drawing on and using
	private SpriteSheet sheet;
	private Graphics g;
	private Handler handler;
	private Camera camera;
	private World world1;
	private MouseManager mouseManager;
	public MenuState menuState;
	
	public Game() {
		isRunning = false;	//this boolean value is to check to see if the game is already running or not. At the beginning, we set it to false
	}
	/*
	 * pre: Game has been initialized
	 * post: all other classes has been instantiated
	 * Description: initializes and instantiates all classes in use
	 */
	private void init() {
		window = new Frame();
		camera = new Camera();
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/skeleton.png"));	//use image loader to get a sprite sheet and put it into sheet
		world1 = new World(camera, "resources/levels/world_1.txt");
		player = new Player(sheet, camera, world1);
		window.getFrame().addKeyListener(player);//add a keylistener to player
		world1.generateEnemies(player, sheet);
		handler = new Handler(camera, world1, player);
		mouseManager = new MouseManager(player.getGun());
		menuState = new MenuState(window, mouseManager);
		mouseManager.setMenu(menuState);
		window.getCanvas().addMouseListener(mouseManager);

		//State.setState(menuState);
		
	}
	
	/*
	 * pre: Game is instantiated, init() has already run once
	 * post: updated player location
	 * Description: This method is supposed to update all conditions (object location, player health etc.). Currently only updates player location
	 */
	public void tick() {//this method updates all the values of objects on the screen.
		if (menuState.isState() == 0 || menuState.isState() == 2) {
			menuState.tick();
			
		}
		else {
			if(menuState.isState() == 1)
				handler.tick();
		}
	}
	/*
	 * pre: tick has ran once
	 * post: all objects has been drawn onto the screen
	 * Description: This method draws images and objects onto the buffer to show on the screen. Runs about 60 times per second
	 */
	public void render() {			//this method renders and draws images onto the screen
		buffer = window.getCanvas().getBufferStrategy();		//get a buffer to use
		if (buffer == null) {
			window.getCanvas().createBufferStrategy(3);			//if this is the first time, make 3 buffers (have no clue why 3 is the limit yet)
			return;				//end the method
		}
		g = buffer.getDrawGraphics();
		
		g.clearRect(0, 0, Frame.WINDOW_WIDTH, Frame.WINDOW_HEIGHT);	//clears the screen (makes a clear rectangle the size of the screen)
		if (menuState.isState() == 0)
			menuState.render(g);
		else {
			if(menuState.isState() ==1)
				handler.render(g);
			
		}//close the graphics object (get reinitialized each loop so having the previous open would create problems.)
		buffer.show();			//show the object on the window (same as repaint)
		g.dispose();
	}
	/*
	 * pre: Thread has started
	 * post: game has ended(player closed the window)
	 * Description: Basically runs the game. It calls all the main methods like init(), tick() and then render() to run the game.
	 */
	public void run() {
		int fps = 120;	//the target frames per second
		long time = System.nanoTime();	//get the current time in nanoseconds
		double tickRate = 1000000000/ fps; //time to run the tick method once (1 second divided by 60)
		double timer = 0;	//keeps track of when 1/60 of a second has passed
		long now;			//current time that will be set in the future
		long timer_checker = 0;
		int ticks = 0;
		
		init();		//initialize/instantiate all objects
		while (isRunning == true){			//run this game loop of ticking(updating variables) and rendering (repainting the canvas/window) until game ends.
			now = System.nanoTime();
			timer += (now - time);	//subtract the time taken to reach this line of code(used to check when we reach 1/60 of a second)
			timer_checker += now - time;		//keep track of that time
			time = now;			//reset time to current time (this process is in nanoseconds so it doesn't matter too much)
			//tick();
			if (timer >= tickRate) {		//if we passed 1/60 of a second, run tick and render
				tick();
				ticks ++;	//add 1 to number of ticks
				timer -= tickRate;	//reset timer to 1 less than current (resetting it to 0 causes problems due to other code running)
			}
			render();
			if (timer_checker >= 1000000000) {	//print fps every second
				System.out.println("FPS: " + ticks); 
				timer_checker = 0;
				ticks = 0;
			}
			
		}
		
		end();		//end the thread
	}
	
	/*
	 * pre: Game has been instantiated
	 * post: thread has been instantiated and started
	 * Description: This method creates a thread to run the game on and starts it.
	 */
	public synchronized void start() {		//synchronizes all running threads so none interrupt each other and corrupt data. Also to make sure that 
											//only one thread has access to Game and all of its variables at a time.
		if (isRunning == true) {			//checks to see if the thread has already been instantiated, if so, return
			return;
		}
		isRunning = true;
		gameThread = new Thread(this);		//create a new thread to run Game class
		gameThread.start();		//start the thread and calls run()
	}
	/*
	 * pre: Thread has already been instantiated and started
	 * post: thread has joined the main thread and closed
	 * Description: Closes the thread that runs game.
	 */
	public synchronized void end() {			//synchronized is needed to make sure multiple threads don't interfere in each other's processes.
		if (isRunning == false) {				//checks to see if the thread has been closed already, if so, return
			return;
		}
		isRunning = false;
		try {
			gameThread.join();				//This makes the thread join the main thread, thus ending the thread safely.
		} catch (InterruptedException e) {	//must be in a try and catch to work
			e.printStackTrace();
		}
	}
	public  MouseManager getMouseManager() {
		return mouseManager;
	}

}
