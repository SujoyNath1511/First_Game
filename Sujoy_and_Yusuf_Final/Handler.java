/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This is the handler. It runs the ticks and render of all the objects within the game not including the menu. It runs the player's tick,
 * player collision, world rendering, bullet collision, boss render and tick, projectiles render and tick and enemies render and tick. It basically runs everything.
 */



import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Handler {
	//private Camera camera;
	private World world1;			//an instance of the world
	private Player player;				//instance of player
	private Foreground_Object[] objects;
	private ArrayList<Projectile> projectiles;		//all the projectiles
	private Enemy_Stats[] enemies;			//all the enemies
	private Boss boss;
	private Camera camera;
	public Handler(Camera camera, World world, Player player) {
		this.camera = camera;
		world1 = world;	
		objects = world1.getObjects();
		projectiles =  new ArrayList<Projectile>();
		this.player = player;
		enemies = world1.getEnemies();
		boss  = new Boss(1500, 30, 7572, 424, camera, player);	//creating the boss

	}
	/*
	 * pre: handler has been instantiated and menuState is 1
	 * post: all game objects have been updated
	 * Description: Updates all in game objects
	 */
	public void tick() {
		boolean tempVars[] = {false,false,false,false, false};		//this boolean array is used to check for collison with Foreground_Objects instances
		projectiles = player.getGun().getBullets();		//get the bullets arraylist (updates the list)
		for (int a = 0; a < projectiles.size(); a++) {	//run a for loop to check every object in the projectiles lise
			tempVars[4] = false;		//set tempVars[4] to false each iteration
			for (int b = 0; b < objects.length; b++) {	
				if (objects[b].getId() != 2 && Math.abs(projectiles.get(a).getX() - objects[b].getX()) <= objects[b].getWidth()
						&& projectiles.get(a).getType() != 3) {
					if (projectiles.get(a).collisionDetection(objects[b].getBounds()) == true) {
						tempVars[4] = true;		//if the bullet isnt type 3 (special boss weapon) and break the loop (no need to check with every 
						break; 					//other foreground_object
					}
				}
			}
			if (projectiles.get(a).getType() != 1 && projectiles.get(a) != null && tempVars[4] == false) {	//if the projectile type isn't 1 (shot from player)
				if (projectiles.get(a).collisionDetection(player.getBounds()) == true) {			//and tempVars[4] is false (the projectile didn't collide yet)
					player.bullet_collision(projectiles.get(a));				//check for collision with the player and subtract from the player's health
					continue;			//move on to the next iteration of this for loop as there is no reason to check for collision with any object for this
				}						// projectile as the projectile has already collided with the player.
			}
			else if (projectiles.get(a) != null && tempVars[4] == false) {		//if the projectile hasn't collided with any foregorund object or the player or 
				for (int c = 0; c < enemies.length ; c++) {						// the type is 1, then check for collision detection with the enemy and subtract health
					if (enemies[c].getHitpoints() > 0)
						if (projectiles.get(a).collisionDetection(enemies[c].getBounds()) == true) {
							enemies[c].setHitPoints(enemies[c].getHitpoints() - projectiles.get(a).getDamage());
							tempVars[4] = true;
							break;
						}
				}
				if (projectiles.get(a).collisionDetection(boss.getBounds()) == true) {		//if the projectile collides with the boss, subtract from the boss's
					tempVars[4] = true;														//health
					boss.boss_bullet_collision(projectiles.get(a));
				}
			}
		}
		for (int i = 0; i < enemies.length;i++ ) {		//run the tick method for all enemies if they are alive and on the screen
			if (enemies[i].getHitpoints() > 0) {
				if ((enemies[i].getEnemyXValue() + camera.getXOffset()) <= Frame.WINDOW_WIDTH && 
						enemies[i].getEnemyXValue() + camera.getXOffset() + enemies[i].getEnemy_width()[enemies[i].getEnemyType()-1]> 0) {
					enemies[i].tick();		
				}
			}

		}
		boss.tick();		//run boss's tick
		player.tick();			
		
		for (int i = 0; i < objects.length; i++) {			//this loop check for player collision with the foreground objects
			if (objects[i].getId() == 0 && tempVars[2] == false) {		//if the object id is 0 (platform) and the player is within range
				if (player.getX() + Player.PLAYER_WIDTH > objects[i].getX() || player.getX() < objects[i].getX() + objects[i].getWidth()) {
					if (player.getY() > objects[i].getY()) {			//check to see if the player is approaching the platform from the top or bottom
						tempVars[2] = player.freefall(objects[i], 1);		//type 1 is from above
					}
					else {
						tempVars[2] = player.freefall(objects[i], 2);		//type 2 is from below
					}
					if (tempVars[2] == false) {
						player.setFalling();			//if the player interacted with the block but not from above, than the player will continue falling
					}
				}
			}
			else if (objects[i].getId() == 1 && Math.abs(objects[i].getX() - player.getX()) <= 500 && tempVars[3] == false) {
				tempVars[3] = player.hitWall(objects[i]);		//if the object id is 1 (walls), than check to see if the player interacts with a wall
			}
			else if (objects[i].getId() == 2 && Math.abs(player.getX() - objects[i].getX()) <= 200) {
				if (objects[i].getBounds().intersects(player.getBounds()) == true) {
					player.setSpawn(2);				//if the player intersects/interacts with a check point (type 2), then reset the player's spawn point and
					deleteEnemies(0);				//give them more health. Also delete the enemies the player didn't kill
				}
			}
			if (tempVars[3] == true && tempVars[2] == true) {		//if the player has interacted with both a wall and a platform, break the loop
				break;												//(no need to check any further)
			}
		}
		for (int j = 0; j < enemies.length;j++) {		//run the hitwall and freefall method for all enemies as well
			
				tempVars[0] = false;
				tempVars[1] = false;
				for (int i = 0; i < objects.length; i++) {
					if (objects[i].getId() == 0 && Math.abs(objects[i].getX() - enemies[j].getEnemyXValue()) <= objects[i].getWidth() && tempVars[0] == false) {
						if (enemies[j].getEnemyYValue() > objects[i].getY()) {
							tempVars[0] = enemies[j].freefall(objects[i], 1);
						}
						else {
							tempVars[0] = enemies[j].freefall(objects[i], 2);
						}
					}
					else if (objects[i].getId() == 1 && Math.abs(objects[i].getX() - enemies[j].getEnemyXValue()) <= 500 && tempVars[1] == false) {
						tempVars[1] = enemies[j].hitWall(objects[i]);
					}
					if (tempVars[1] == true && tempVars[0] == true) {
						break;
					}
				}
			
		}
		for (int index = 0; index < projectiles.size(); index ++) {
			if (projectiles.get(index) != null) {
				projectiles.get(index).tick();		//run tick for every method and if a projectile has collided with an object or went offscreen, delete it
				if (projectiles.get(index).getCollide() == true) {
					projectiles.remove(index);
				}
			}

		}
	}
	/*
	 * pre: none
	 * post: returns boss
	 * Description: returns the current instance of boss
	 */
	public Boss getBoss() {
		return boss;
	}
	/*
	 * pre: none
	 * post: sets boss
	 * Description: sets the boss
	 */
	public void setBoss(Boss boss) {
		this.boss = boss;
	}
	/*
	 * pre: handler has been instantiated and menuState is 1
	 * post: all objects have been rendered onto the screen
	 * Description: This method renders all the objects on the screen
	 */
	public void render(Graphics g, Graphics2D g2d, Graphics2D g2d_2) {
		world1.render(g);		//render the world
		player.render(g);		//render the player
		boss.render(g);				///render the boss
		for (int i = 0; i < enemies.length; i++) {		//render all the enemies that are alive and on screen
			if (enemies[i].getHitpoints() > 0) {
				if ((enemies[i].getEnemyXValue() + camera.getXOffset()) <= Frame.WINDOW_WIDTH &&
						enemies[i].getEnemyXValue() + camera.getXOffset() + enemies[i].getEnemy_width()[enemies[i].getEnemyType()-1]> 0) {
					enemies[i].render(g2d_2);
				}
			}
		}
		for (int index = 0; index < projectiles.size(); index ++) {		//render all projectiles
			if (projectiles.get(index).getCollide() == false)
				projectiles.get(index).render(g2d);
		}
	}
	/*
	 * pre: player has reached a checkpoint
	 * post: deletes all enemies that are behind the player and off screen
	 * Description: Deletes all enemies that are behind the player and off screen because there is no way to go back, thus it is more efficient to delete them
	 * (not ticking and rendering)
	 */
	public void deleteEnemies(int i) {
		if (i < enemies.length) {		//as long as we are in the array
			if (enemies[i].getEnemyXValue() + camera.getXOffset() < 0) {//check if the x plus camera offset is less than 0 (off screen on the left)
				enemies[i].setHitPoints(0);		//set the enemy's hitpoints to 0
			}
			deleteEnemies(i + 1);		//move on to the next.
		}
	}
}
