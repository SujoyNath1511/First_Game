package sujoy_Code;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entities_and_objects.Foreground_Object;
import worlds.World;

public class Handler {
	//private Camera camera;
	private World world1;
	private Player player;
	private Foreground_Object[] objects;
	private ArrayList<Projectile> projectiles;
	private Enemy_Stats[] enemies;
	public Handler(Camera camera, World world, Player player) {
		//this.camera = camera;
		world1 = world;	
		objects = world1.getObjects();
		projectiles =  new ArrayList<Projectile>();
		this.player = player;
		enemies = world1.getEnemies();
		
	}
	public void tick() {
		boolean tempVars[] = {false,false,false,false};
		projectiles = player.getGun().getBullets();
		for (int a = 0; a < projectiles.size(); a++) {
			for (int b = 0; b < objects.length; b++) {
				if (objects[b].getId() != 2 && Math.abs(projectiles.get(a).getX() - objects[b].getX()) <= Frame.WINDOW_WIDTH) {
					if (projectiles.get(a).collisionDetection(objects[b].getBounds()) == true) {
						break;
					}
				}
			}
		}
		world1.tick(projectiles);
		player.tick();
		for (int index = 0; index < projectiles.size(); index ++) {
			projectiles.get(index).tick();
			if (projectiles.get(index).getCollide() == true) {
				projectiles.remove(index);
			}
			
		}
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].getId() == 0 && tempVars[2] == false) {
				if (player.getX() + Player.PLAYER_WIDTH > objects[i].getX() || player.getX() < objects[i].getX() + objects[i].getWidth()) {
					if (player.getY() > objects[i].getY()) {
						tempVars[2] = player.freefall(objects[i], 1);
					}
					else {
						tempVars[2] = player.freefall(objects[i], 2);
					}
					if (tempVars[2] == false) {
						player.setFalling();
					}
				}
			}
			else if (objects[i].getId() == 1 && Math.abs(objects[i].getX() - player.getX()) <= 500 && tempVars[3] == false) {
				tempVars[3] = player.hitWall(objects[i]);
			}
			else if (objects[i].getId() == 2 && Math.abs(player.getX() - objects[i].getX()) <= 200) {
				if (objects[i].getBounds().intersects(player.getBounds()) == true) {
					player.setSpawn();
				}
			}
			if (tempVars[3] == true && tempVars[2] == true) {
				break;
			}
		}
		for (int j = 0; j < enemies.length;j++) {
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
		//System.out.println(projectiles.size());
	}
	public void render(Graphics g) {
		world1.render(g);
		for (int index = 0; index < projectiles.size(); index ++) {
			projectiles.get(index).render((Graphics2D) g);
		}
		player.render(g);
		
	}
}
