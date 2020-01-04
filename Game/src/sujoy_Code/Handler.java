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
	private Foreground_Object[][] objects;
	private ArrayList<Projectile> projectiles;
	public Handler(Camera camera, World world, Player player) {
		//this.camera = camera;
		world1 = world;	
		objects = world1.getObjects();
		projectiles =  new ArrayList<Projectile>();
		this.player = player;
		
	}
	public void tick() {
		boolean tempVar;
		boolean tempVar2;
		projectiles = player.getGun().getBullets();
		for (int a = 0; a < projectiles.size(); a++) {
			for (int b = 0; b < objects[0].length; b++) {
				if (objects[0][b] != null && Math.abs(projectiles.get(a).getX() - objects[0][b].getX()) <= Frame.WINDOW_WIDTH) {
					if (projectiles.get(a).collisionDetection(objects[0][b].getBounds()) == true) {
						break;
					}
				}
				if (objects[1][b] != null && Math.abs(projectiles.get(a).getX() - objects[0][b].getX()) <= Frame.WINDOW_WIDTH) {
					if (projectiles.get(a).collisionDetection(objects[1][b].getBounds()) == true) {
						break;
					}
				}
			}
		}
		player.tick();
		for (int index = 0; index < projectiles.size(); index ++) {
			projectiles.get(index).tick();
			if (projectiles.get(index).getCollide() == true) {
				projectiles.remove(index);
			}
		}
		//System.out.println(projectiles.size());
		for (int i = 0; i < objects[0].length; i++) {
			if (objects[0][i] != null && Math.abs(objects[0][i].getX() - player.getX()) <= objects[0][i].getWidth()) {
				if (player.getY() > objects[0][i].getY()) {
					tempVar = player.freefall(objects[0][i], 1);
				}
				else {
					tempVar = player.freefall(objects[0][i], 2);
				}
				if (tempVar == false) {
					player.setFalling();
				}
				else {
					break;
				}
			}
		}
		
		for (int j = 0; j < objects[1].length; j++) {
			if (objects[1][j] != null && Math.abs(objects[1][j].getX() - player.getX()) <= 500) {
				tempVar2 = player.hitWall(objects[1][j]);
				if (tempVar2 == true) {
					break;
				}
			}
		}
	}
	public void render(Graphics g) {
		world1.render(g);
		for (int index = 0; index < projectiles.size(); index ++) {
			projectiles.get(index).render((Graphics2D) g);
		}
		player.render(g);
	}
}
