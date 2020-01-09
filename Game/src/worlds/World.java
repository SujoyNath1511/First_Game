package worlds;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities_and_objects.Background_Object;
import entities_and_objects.Foreground_Object;
import sprites.SpriteSheet;
import sujoy_Code.Camera;
import sujoy_Code.Enemy_Stats;
import sujoy_Code.Frame;
import sujoy_Code.ImageLoader;
import sujoy_Code.Player;
import sujoy_Code.Projectile;
import utils.Utils;

public class World {
	private int worldWidth;
	private int worldHeight;
	private Camera camera;
	private Foreground_Object[] objects;
	private Background_Object[] background_objs;
	private BufferedImage background;
	private Enemy_Stats[] enemies;
	//private Player player;
	
	public World(Camera cam, String path) {
		camera = cam;
		loadWorld(path);
		enemies = new Enemy_Stats[39];
		//background_objs[1] = new Background_Object(811,0,455,569, camera, 0);
		
	}
	public void generateEnemies (Player player, SpriteSheet sheet) {
		
		//---------------------Type 1 ------------------------------//
				enemies[0] = new Enemy_Stats(50,15,1,1940,61,camera,player,sheet,1);
				enemies[1] = new Enemy_Stats(50,15,1,1805,141,camera,player,sheet,1);
				enemies[2] = new Enemy_Stats(50,15,1,1630,232,camera,player,sheet,1);
				enemies[3] = new Enemy_Stats(50,15,1,2195,68,camera,player,sheet,1);
				enemies[4] = new Enemy_Stats(50,15,1,2805,318,camera,player,sheet,1);
				enemies[5] = new Enemy_Stats(50,15,1,3065,169,camera,player,sheet,1);
				enemies[6] = new Enemy_Stats(50,15,1,3350,112,camera,player,sheet,1);
				enemies[7] = new Enemy_Stats(50,15,1,5080,107,camera,player,sheet,1);
				enemies[8] = new Enemy_Stats(50,15,1,5280,104,camera,player,sheet,1);
				enemies[9] = new Enemy_Stats(50,15,1,5465,53,camera,player,sheet,1);
				enemies[10] = new Enemy_Stats(50,15,1,5565,98,camera,player,sheet,1);
				enemies[11] = new Enemy_Stats(50,15,1,5705,115,camera,player,sheet,1);
				// ------------------------Type 2  ------------------------------//
				enemies[12] = new Enemy_Stats(200,5,2,1145,528,camera,player,sheet,1);
				enemies[21] = new Enemy_Stats(200,5,2,3775,535,camera,player,sheet,1);
				enemies[25] = new Enemy_Stats(200,5,2,4105,535,camera,player,sheet,1);
				enemies[33] = new Enemy_Stats(200,5,2,4990,314,camera,player,sheet,1);
				//-----------------------Type 3 -------------------------------//
				enemies[13] = new Enemy_Stats(100,30,3,1120,385,camera,player,sheet,1);
				enemies[14] = new Enemy_Stats(100,30,3,1185,385,camera,player,sheet,1);
				enemies[15] = new Enemy_Stats(100,30,3,835,257,camera,player,sheet,1);
				enemies[16] = new Enemy_Stats(100,30,3,930,257,camera,player,sheet,1);
				enemies[17] = new Enemy_Stats(100,30,3,1616,442,camera,player,sheet,1);
				enemies[18] = new Enemy_Stats(100,30,3,1796,350,camera,player,sheet,1);
				enemies[19] = new Enemy_Stats(100,30,3,2031,268,camera,player,sheet,1);
				enemies[20] = new Enemy_Stats(100,30,3,2181,392,camera,player,sheet,1);
				enemies[22] = new Enemy_Stats(100,30,3,2830,528,camera,player,sheet,1);
				enemies[23] = new Enemy_Stats(100,30,3,3035,378,camera,player,sheet,1);
				enemies[24] = new Enemy_Stats(100,30,3,3380,321,camera,player,sheet,1);
				enemies[26] = new Enemy_Stats(100,30,3,4330,421,camera,player,sheet,1);
				enemies[27] = new Enemy_Stats(100,30,3,4620,421,camera,player,sheet,1);
				enemies[28] = new Enemy_Stats(100,30,3,4680,300,camera,player,sheet,1);
				enemies[29] = new Enemy_Stats(100,30,3,4450,300,camera,player,sheet,1);
				enemies[30] = new Enemy_Stats(100,30,3,4255,300,camera,player,sheet,1);
				enemies[31] = new Enemy_Stats(100,30,3,4158,101,camera,player,sheet,1);
				enemies[32] = new Enemy_Stats(100,30,3,4543,301,camera,player,sheet,1);
				enemies[34] = new Enemy_Stats(100,30,3,5205,314,camera,player,sheet,1);
				enemies[35] = new Enemy_Stats(100,30,3,5510,172,camera,player,sheet,1);
				enemies[36] = new Enemy_Stats(100,30,3,5645,172,camera,player,sheet,1);
				enemies[37] = new Enemy_Stats(100,30,3,5810,172,camera,player,sheet,1);
				enemies[38] = new Enemy_Stats(100,30,3,6015,172,camera,player,sheet,1);

	}
	
	public void tick(ArrayList<Projectile> projectiles) {
		for (int i = 0; i < enemies.length;i++ ) {
			if (enemies[i].getHitpoints() > 0)
				enemies[i].tick(projectiles);
		}
		
	}
	public Enemy_Stats[] getEnemies() {
		return enemies;
	}
	public void render(Graphics g) {
		g.drawImage(background,0, 0, null);
		for (int j = 0; j < background_objs.length; j++) {
			if ((background_objs[j].getX() + camera.getXOffset()) <= Frame.WINDOW_WIDTH || 
					background_objs[j].getX() + camera.getXOffset() + background_objs[j].getWidth() > 0) {
				background_objs[j].render(g);
			}
		}
		for (int i = 0; i < objects.length; i++) {
			if ((objects[i].getX() + camera.getXOffset()) <= Frame.WINDOW_WIDTH || 
					objects[i].getX() + camera.getXOffset() + objects[i].getWidth() > 0) {
				objects[i].render(g);
			}
		}
		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i].getHitpoints() > 0) {
				if ((enemies[i].getEnemyXValue() + camera.getXOffset()) <= Frame.WINDOW_WIDTH || 
					enemies[i].getEnemyXValue() + camera.getXOffset() + enemies[i].getEnemy_width()[enemies[i].getEnemyType()-1]> 0) {
					enemies[i].render(g);
				}
			}
		}
		
	}
	public Foreground_Object[] getObjects() {
		return objects;
	}
	public void setObjects(Foreground_Object[] objects) {
		this.objects = objects;
	}
	
	private void loadWorld(String path) {
		String file = Utils.fileToString(path);
		String[] tempArray = file.split("\\s+");
		background = ImageLoader.loadImage(tempArray[0]);
		worldWidth = Integer.parseInt(tempArray[1]);
		worldHeight = Integer.parseInt(tempArray[2]);
		objects = new Foreground_Object[Integer.parseInt(tempArray[3])];
		background_objs = new Background_Object[Integer.parseInt(tempArray[4])];
		for (int row = 5; row + 5 < tempArray.length; row += 5) {
			if (row < 260) {
				objects[row/5 - 1] = new Foreground_Object 
						((int) (Integer.parseInt(tempArray[row]) * 683/960.0),(int)(Integer.parseInt(tempArray[row + 1]) * 683/960.0),(int)(Integer.parseInt(tempArray[row + 2]) * 683/960.0),(int)(Integer.parseInt(tempArray[row + 3]) * 683/960.0), camera, Integer.parseInt(tempArray[row + 4]));
			}
			else {
				background_objs[row/5 - 52] = new Background_Object(Integer.parseInt(tempArray[row]),Integer.parseInt(tempArray[row + 1]),Integer.parseInt(tempArray[row + 2]),Integer.parseInt(tempArray[row + 3]), camera, Integer.parseInt(tempArray[row + 4]));
			}
		} 		
	}
	public int getWorldWidth() {
		return worldWidth;
	}
	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}
	public int getWorldHeight() {
		return worldHeight;
	}
	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

}
