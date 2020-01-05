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
	public Handler(Camera camera, World world, Player player) {
		//this.camera = camera;
		world1 = world;	
		objects = world1.getObjects();
		projectiles =  new ArrayList<Projectile>();
		this.player = player;
		
	}
	public void tick() {
		boolean tempVar = false;
		boolean tempVar2 = false;
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
		player.tick();
		for (int index = 0; index < projectiles.size(); index ++) {
			projectiles.get(index).tick();
			if (projectiles.get(index).getCollide() == true) {
				projectiles.remove(index);
			}
		}
		//System.out.println(projectiles.size());
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].getId() == 0 && Math.abs(objects[i].getX() - player.getX()) <= objects[i].getWidth() && tempVar == false) {
				if (player.getY() > objects[i].getY()) {
					tempVar = player.freefall(objects[i], 1);
				}
				else {
					tempVar = player.freefall(objects[i], 2);
				}
				if (tempVar == false) {
					player.setFalling();
				}
			}
			else if (objects[i].getId() == 1 && Math.abs(objects[i].getX() - player.getX()) <= 500 && tempVar2 == false) {
				tempVar2 = player.hitWall(objects[i]);
			}
			if (tempVar == true && tempVar2 == true) {
				break;
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
