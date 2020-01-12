/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class is generates the environment of the game. It reads from a text file and generates all the enemies, platforms, walls etc.
 */
package worlds;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities_and_objects.Background_Object;
import entities_and_objects.Foreground_Object;
import main_game_code.Camera;
import main_game_code.Enemy_Stats;
import main_game_code.Frame;
import main_game_code.ImageLoader;
import main_game_code.Player;
import utils.Utils;

public class World {
	private int worldWidth;
	private int worldHeight;
	private Camera camera;
	private Foreground_Object[] objects;			//list of foreground_objects
	private Background_Object[] background_objs;	//list of background_objects
	private BufferedImage background;
	private Enemy_Stats[] enemies;


	public World(Camera cam, String path) {
		camera = cam;
		loadWorld(path);		//load the world
		enemies = new Enemy_Stats[24];	//instantiate the enemies array

	}
	/*
	 * pre: World has been instantiated
	 * post: enemies have been created and placed around the level
	 * Description: This method constructs enemies within the world at specific locations.
	 */
	public void generateEnemies (Player player) {
		//underneath, Yusuf has hardcoded all the enemies and their locations and attributes. Look at the Enemy_Stats clss to understand what each
		//variable means.
		//---------------------Type 1 ------------------------------//
		enemies[23] = new Enemy_Stats(50,15,1,1940,61,camera,player,1);
		enemies[0] = new Enemy_Stats(50,15,1,1630,232,camera,player,1);
		enemies[22] = new Enemy_Stats(50,15,1,2195,68,camera,player,1);
		enemies[1] = new Enemy_Stats(50,15,1,2805,318,camera,player,1);
		enemies[2] = new Enemy_Stats(50,15,1,3065,169,camera,player,1);
		enemies[3] = new Enemy_Stats(50,15,1,3350,112,camera,player,1);
		enemies[4] = new Enemy_Stats(50,15,1,5465,53,camera,player,1);
		// ------------------------Type 2  ------------------------------//
		enemies[5] = new Enemy_Stats(200,5,2,1145,528,camera,player,1);
		enemies[6] = new Enemy_Stats(200,5,2,3775,535,camera,player,1);
		enemies[7] = new Enemy_Stats(200,5,2,4105,535,camera,player,1);
		enemies[8] = new Enemy_Stats(200,5,2,4990,314,camera,player,1);
		//-----------------------Type 3 -------------------------------//
		enemies[9] = new Enemy_Stats(100,30,3,1120,385,camera,player,1);
		enemies[10] = new Enemy_Stats(100,30,3,1185,385,camera,player,1);
		enemies[11] = new Enemy_Stats(100,30,3,835,257,camera,player,1);
		enemies[12] = new Enemy_Stats(100,30,3,930,257,camera,player,1);
		enemies[13] = new Enemy_Stats(100,30,3,1616,442,camera,player,1);
		enemies[14] = new Enemy_Stats(100,30,3,1796,350,camera,player,1);
		enemies[15] = new Enemy_Stats(100,30,3,2031,324,camera,player,1);
		enemies[16] = new Enemy_Stats(100,30,3,2181,520,camera,player,1);
		enemies[17] = new Enemy_Stats(100,30,3,2830,528,camera,player,1);
		enemies[18] = new Enemy_Stats(100,30,3,4255,300,camera,player,1);
		enemies[19] = new Enemy_Stats(100,30,3,4158,101,camera,player,1);
		enemies[20] = new Enemy_Stats(100,30,3,4543,301,camera,player,1);
		enemies[21] = new Enemy_Stats(100,30,3,5510,172,camera,player,1);
		

	}
	/*
	 * pre: enemy_stats array has been initialized (generate enemies have been called)
	 * post: returns enemies
	 * Description: Returns the arrray of enemies
	 */
	public Enemy_Stats[] getEnemies() {
		return enemies;
	}
	/*
	 * pre: World has been instantiated
	 * post: draws the platforms and background walls to the screen
	 * Description: This method renders the platforms and background of the game
	 */
	public void render(Graphics g) {
		g.drawImage(background,0, 0, null);		//draw the game background
		for (int j = 0; j < background_objs.length; j++) {	//run a for loop for the background objects array and if the object is on the screen
			if ((background_objs[j].getX() + camera.getXOffset()) <= Frame.WINDOW_WIDTH && 		//render it
					background_objs[j].getX() + camera.getXOffset() + background_objs[j].getWidth() > 0) {
				background_objs[j].render(g);			//render the object if the left side of the object is on the screen or the right side is
			}											//is on the screen
		}
		for (int i = 0; i < objects.length; i++) {			//do the same for all foreground objects
			if ((objects[i].getX() + camera.getXOffset()) <= Frame.WINDOW_WIDTH && 
					objects[i].getX() + camera.getXOffset() + objects[i].getWidth() > 0) {
				objects[i].render(g);
			}
		}

	}
	/*
	 * pre: loadWorld() has run
	 * post: returns the foreground_objects array
	 * Description: Returns the list of foreground_objects
	 */
	public Foreground_Object[] getObjects() {
		return objects;
	}
	/*
	 * pre: none
	 * post: Sets the foreground_Objects
	 * Description: Sets the fore ground objects array
	 */
	public void setObjects(Foreground_Object[] objects) {
		this.objects = objects;
	}

	/*
	 * pre: World is being instantiated
	 * post: All the foreground objects, background objects and the world background have been created
	 * Description: Reads the locations and attributes of all the foreground and background objects from a textfile and instantiates 
	 * those objects
	 */
	private void loadWorld(String path) {
		String file = Utils.fileToString(path);		//read the text file as a string
		String[] tempArray = file.split("\\s+");		//split up the string into a string array,split at every new line and empty space
		background = ImageLoader.loadImage(tempArray[0]);		//get the image address from the first index
		worldWidth = Integer.parseInt(tempArray[1]);	//get the world width
		worldHeight = Integer.parseInt(tempArray[2]);		//get the world height
		objects = new Foreground_Object[Integer.parseInt(tempArray[3])];		//initialize the objects array from the number in the text file
		background_objs = new Background_Object[Integer.parseInt(tempArray[4])];	//initialize the boackground objects array
		for (int row = 5; row + 5 < tempArray.length; row += 5) {		//run a for loop for the tempArray and create all the foreground objects
			if (row < 250) {
				objects[row/5 - 1] = new Foreground_Object 		//the reason all the values are getting multiplied by 683/960 is because my laptop's resolution is 1920 x 1080, so i programmed the object dimensions according to that. to make the game playable on most computers, we rescaled everything to 1366 x 768, thus we had to multiply by 683/960, the ratio between 1366/1920.
						((int) (Integer.parseInt(tempArray[row]) * 683/960.0),(int)(Integer.parseInt(tempArray[row + 1]) * 683/960.0),(int)(Integer.parseInt(tempArray[row + 2]) * 683/960.0),(int)(Integer.parseInt(tempArray[row + 3]) * 683/960.0), camera, Integer.parseInt(tempArray[row + 4]));
			}
			else if (row < 290){		//background objects were made later thus they don't need to be rescaled
				background_objs[row/5 - 50] = new Background_Object(Integer.parseInt(tempArray[row]),Integer.parseInt(tempArray[row + 1]),Integer.parseInt(tempArray[row + 2]),Integer.parseInt(tempArray[row + 3]), camera, Integer.parseInt(tempArray[row + 4]));
			}
		} 		
	}
	/*
	 * pre:	world has been instantiated
	 * post: returns the world width
	 * Description: Returns the width of the level
	 */
	public int getWorldWidth() {
		return worldWidth;
	}
	/*
	 * pre: none
	 * post: sets worldWidth
	 * Description: sets the width of the world
	 */
	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}
	/*
	 * pre: World has been instantiated
	 * post: returns the world height
	 * Description: Returns the height of the world
	 */
	public int getWorldHeight() {
		return worldHeight;
	}
	/*
	 * pre: none
	 * post: sets the world height
	 * Description: sets the height of the level
	 */
	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

}
