package main_game_code;


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
	private Boss boss;
	private Camera camera;
	public Handler(Camera camera, World world, Player player) {
		this.camera = camera;
		world1 = world;	
		objects = world1.getObjects();
		projectiles =  new ArrayList<Projectile>();
		this.player = player;
		enemies = world1.getEnemies();
		boss  = new Boss(1500, 30, 7572, 424, camera, player);

	}
	public void tick() {
		boolean tempVars[] = {false,false,false,false, false};
		projectiles = player.getGun().getBullets();
		for (int a = 0; a < projectiles.size(); a++) {
			tempVars[4] = false;
			for (int b = 0; b < objects.length; b++) {
				if (objects[b].getId() != 2 && Math.abs(projectiles.get(a).getX() - objects[b].getX()) <= objects[b].getWidth()
						&& projectiles.get(a).getType() != 3) {
					if (projectiles.get(a).collisionDetection(objects[b].getBounds()) == true) {
						tempVars[4] = true;
						break;
					}
				}
			}
			if (projectiles.get(a).getType() != 1 && projectiles.get(a) != null && tempVars[4] == false) {
				if (projectiles.get(a).collisionDetection(player.getBounds()) == true) {
					player.bullet_collision(projectiles.get(a));
					continue;
				}
			}
			else if (projectiles.get(a) != null && tempVars[4] == false) {
				for (int c = 0; c < enemies.length ; c++) {
					if (enemies[c].getHitpoints() > 0)
						if (projectiles.get(a).collisionDetection(enemies[c].getBounds()) == true) {
							enemies[c].setHitPoints(enemies[c].getHitpoints() - projectiles.get(a).getDamage());
							tempVars[4] = true;
							break;
						}
				}
				if (projectiles.get(a).collisionDetection(boss.getBounds()) == true) {
					tempVars[4] = true;
					boss.boss_bullet_collision(projectiles.get(a));
				}
			}
		}
		for (int i = 0; i < enemies.length;i++ ) {
			if (enemies[i].getHitpoints() > 0) {
				if ((enemies[i].getEnemyXValue() + camera.getXOffset()) <= Frame.WINDOW_WIDTH && 
						enemies[i].getEnemyXValue() + camera.getXOffset() + enemies[i].getEnemy_width()[enemies[i].getEnemyType()-1]> 0) {
					enemies[i].tick();
				}
			}

		}
		boss.tick(projectiles);
		player.tick();
		//player.bullet_collision(projectiles);
		for (int index = 0; index < projectiles.size(); index ++) {
			if (projectiles.get(index) != null) {
				projectiles.get(index).tick();
				if (projectiles.get(index).getCollide() == true) {
					projectiles.remove(index);
				}
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
					deleteEnemies(0);
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
	public Boss getBoss() {
		return boss;
	}
	public void setBoss(Boss boss) {
		this.boss = boss;
	}
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		world1.render(g);
		for (int index = 0; index < projectiles.size(); index ++) {
			projectiles.get(index).render(g2d);
		}
		player.render(g);
		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i].getHitpoints() > 0) {
				if ((enemies[i].getEnemyXValue() + camera.getXOffset()) <= Frame.WINDOW_WIDTH &&
						enemies[i].getEnemyXValue() + camera.getXOffset() + enemies[i].getEnemy_width()[enemies[i].getEnemyType()-1]> 0) {
					enemies[i].render(g2d);
				}
			}
		}
		boss.render(g);
		//g2d.dispose();

	}
	public void deleteEnemies(int i) {
		if (i < enemies.length) {
			if (enemies[i].getEnemyXValue() + camera.getXOffset() < -10) {
				enemies[i].setHitPoints(0);
			}
			System.out.println("HELLO");
			deleteEnemies(i + 1);
		}
	}
}
