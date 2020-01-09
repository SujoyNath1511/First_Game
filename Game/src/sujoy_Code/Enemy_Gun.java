package sujoy_Code;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

public class Enemy_Gun extends Gun {
	private long current;
	boolean shoot;
	private Player player;
	private Enemy_Stats enemy;
	public Enemy_Gun(Camera cam, BufferedImage image, Enemy_Stats enemy, int width, int height, Player player, int direction) {
		
		super(enemy.getEnemyXValue() , enemy.getEnemyYValue() ,cam, image, width, height);
		this.enemy = enemy;
		this.player = player;
		current = 0;
	}

	

	@Override
	public void render(Graphics2D g2d) {
			if (enemy.getEnemyXValue()  <  player.getX() ){
				g2d.rotate(-angle, x + camera.getXOffset(), y);
				g2d.drawImage(img,x + camera.getXOffset(), y, width, height, null);
				g2d.rotate(angle, x + camera.getXOffset(), y);
			}
			else {
				if(enemy.getEnemyXValue() >  player.getX() ) {
					g2d.rotate(angle, x + camera.getXOffset(), y);
					g2d.drawImage(img,x + camera.getXOffset(), y, -width, height, null);
					g2d.rotate(-angle, x + camera.getXOffset(), y);
				}
			}
		
		
	}

	@Override
	public void tick() {
		if(enemy.getEnemyType()==1) {
			x = enemy.getEnemyXValue() + enemy.getEnemy_width()[0]/2;
			y = enemy.getEnemyYValue() + enemy.getEnemy_height()[0]/2;
		}
		else
			if(enemy.getEnemyType()==2) {
				x = enemy.getEnemyXValue() + enemy.getEnemy_width()[1]/2;
				y = enemy.getEnemyYValue() + enemy.getEnemy_height()[1]/2;
			}
			else
				if(enemy.getEnemyType()==3) {
					x = enemy.getEnemyXValue() + enemy.getEnemy_width()[2]/2;
					y = enemy.getEnemyYValue() + enemy.getEnemy_height()[2]/2;
				}
		if (Math.abs(enemy.getEnemyXValue() - player.getX()) <= 250)
			findAngle();
		
	}

	@Override
	protected void findAngle() {
		int tempX;
		int tempY;
		tempX = player.getX() - enemy.getEnemyXValue();
		tempY = -(player.getY() - enemy.getEnemyYValue());
		double tempHype = Math.sqrt((tempX * tempX) + (tempY * tempY));
		if (tempX != 0) {
			angle = Math.asin(tempY/tempHype);
		}
	}

	@Override
	public void fire() {
		long timeShot = System.currentTimeMillis();
		if (timeShot - current >= 2000 && shoot == false) {
			current = timeShot;
			shoot = true;		
		}
		else
			shoot = false;

		if (enemy.getEnemyXValue()  <  player.getX() && enemy.getEnemyXValue() - player.getX() > -250) {
			if (shoot == true) {
				bullets.add(new Projectile(x, y, 5, angle, 25,10,camera,10,2));
				shoot = false;
			}	
		}
		else
			if(enemy.getEnemyXValue() >  player.getX()  && enemy.getEnemyXValue() - player.getX() < 250) {
				if (shoot == true) {
					bullets.add(new Projectile(x, y, -5, -angle, -25,10,camera,10,2));
					shoot = false;
			}
				
		}
	}

		
	

}
