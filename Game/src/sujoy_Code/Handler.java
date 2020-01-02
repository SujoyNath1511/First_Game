package sujoy_Code;


import java.awt.Graphics;

import entities_and_objects.Foreground_Object;
import worlds.World;

public class Handler {
	private Camera camera;
	private World world1;
	private Player player;
	private Foreground_Object[][] objects;
	public Handler(Camera camera, World world, Player player) {
		this.camera = camera;
		world1 = world;	
		objects = world1.getObjects();
		this.player = player;
	}
	public void tick() {
		boolean tempVar;
		boolean tempVar2;
		player.tick();
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
			if (objects[1][j] != null) {
				tempVar2 = player.hitWall(objects[1][j]);
				if (tempVar2 == true) {
					break;
				}
			}
		}
	}
	public void render(Graphics g) {
		world1.render(g);
		player.render(g);
		
	}
}
