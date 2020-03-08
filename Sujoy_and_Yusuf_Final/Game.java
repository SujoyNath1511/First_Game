/*
 * Name: Sujoy Deb Nath
 * Last Edited: January 13, 2020
 * Description: This is the main method that runs the game and the menu. It creates instances of all other classes and runs the main game.
 */




import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;




public class Game implements Runnable{		//runnable lets me use threads
	private Frame window;		//the window/frame of the game
	private Player player;
	private Thread gameThread;		//a thread to run the game
	private boolean isRunning;
	private BufferStrategy buffer;		//buffers for drawing on and using
	private Graphics g;
	private Graphics2D g2d, g2d_2;
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
		world1 = new World(camera, "resources/levels/world_1.txt");
		player = new Player(camera, world1);
		window.getFrame().addKeyListener(player);//add a keylistener to player
		world1.generateEnemies(player);
		handler = new Handler(camera, world1, player);
		mouseManager = new MouseManager(player.getGun());
		menuState = new MenuState(window, mouseManager, this);
		mouseManager.setMenu(menuState);		//adds a menu to mouseManager
		window.getCanvas().addMouseListener(mouseManager);		//add a mouseListener object to mouseManager
		handler.getBoss().setMenu(menuState);		//set a menu object for the boss to use
		//State.setState(menuState);
		
	}
	
	/*
	 * pre: Game is instantiated, init() has already run once
	 * post: updated player location
	 * Description: This method is supposed to update all conditions (object location, player health etc.). Currently only updates player location
	 */
	public void tick() {//this method updates all the values of objects on the screen.
		if (menuState.isState() == 0 || menuState.isState() == 2) {	//run the tick for menuState if the state is 0 or 2 (main menu or help screen)
			menuState.tick();
			
		}
		else {
			handler.tick();		//run the game
		}
		
		if (player.getDeathCounter() >= 3) {	//if the player has died 3 or more times, set the state to 0 and reset all the enemies, boss and the player.
			menuState.setState(0);
			player.setDeathCounter(0);
			for (int i = 0; i < world1.getEnemies().length ; i++) {
				world1.getEnemies()[i].resetEnemy();
			}
		}
	}
	/*
	 * pre: Game has been instantiated
	 * post: get isRunning
	 * Description: Returns isRunning;
	 */
	public boolean isRunning() {
		return isRunning;
	}
	/*
	 * pre: Game has been instantiated
	 * post: set isRunning
	 * Description: set isRunning to determine if the game is running or not.
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	/*
	 * pre: tick has ran once
	 * post: all objects has been drawn onto the screen
	 * Description: This method draws images and objects onto the buffer to show on the screen. Runs about 60 times per second
	 */
	public void render() {			//this method renders and draws images onto the screen
		buffer = window.getCanvas().getBufferStrategy();		//get a buffer to use
		if (buffer == null) {
			window.getCanvas().createBufferStrategy(3);			//if this is the first time, make 3 buffers 
			return;				//end the method
		}
		
		//Underneath, the reason I make 3 graphics objects is because I was running into an error that would make the player or some of the enemies disappear,
		//thus after a lot of time debugging and trying to figure out solutions, I have decided to create 3 graphics objects, one for enemies, one for projectiles
		//and one for everything else.
		g = buffer.getDrawGraphics();
		g2d = (Graphics2D)(buffer.getDrawGraphics());
		g2d_2 = (Graphics2D)(buffer.getDrawGraphics());

		g.clearRect(0, 0, Frame.WINDOW_WIDTH, Frame.WINDOW_HEIGHT);	//clears the screen (makes a clear rectangle the size of the screen)
		if (menuState.isState() == 0 || menuState.isState() == 2)		//run menuState render if the state is 0 or 2
			menuState.render(g);
		else {
			handler.render(g, g2d, g2d_2);		//run handler
		}								
		buffer.show();			//show the object on the window (same as repaint)
		//Underneath I dispose of all graphics objects
		g.dispose();
		g2d.dispose();
		g2d_2.dispose();
	}
	/*
	 * pre: Thread has started
	 * post: game has ended(player closed the window)
	 * Description: Basically runs the game. It calls all the main methods like init(), tick() and then render() to run the game.
	 */
	public void run() {
		int fps = 120;	//the target frames per second
		long time = System.nanoTime();	//get the current time in nanoseconds
		double tickRate = 1000000000/ fps; //time to run the tick method once (1 second divided by 120)
		double timer = 0;	//keeps track of when 1/120 of a second has passed
		long now;			//current time that will be set in the future
		long timer_checker = 0;
		int ticks = 0;
		
		init();		//initialize/instantiate all objects
		while (isRunning == true){			//run this game loop of ticking(updating variables) and rendering (repainting the canvas/window) until game ends.
			now = System.nanoTime();
			timer += (now - time);	//subtract the time taken to reach this line of code(used to check when we reach 1/120 of a second)
			timer_checker += now - time;		//keep track of that time
			time = now;			//reset time to current time (this process is in nanoseconds so it doesn't matter too much)
			//tick();
			if (timer >= tickRate) {		//if we passed 1/120 of a second, run tick and render
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
		java.lang.System.exit(1);			//terminate the program
		end();		
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
		try {
			gameThread.join();				//This makes the thread join the main thread, thus ending the thread safely.
		} catch (InterruptedException e) {	//must be in a try and catch to work
			e.printStackTrace();
		}
	}
	/*
	 * pre: mouseManager has been instantiated
	 * post: gets mouseManager
	 * Description: returns the current instance of MouseManager
	 */
	public  MouseManager getMouseManager() {
		return mouseManager;
	}

}
