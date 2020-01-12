package main_game_code;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy_Gun extends Gun {
	private int ticks;
	boolean shoot;
	private Player player;
	private Enemy_Stats enemy;
	public Enemy_Gun(Camera cam, BufferedImage image, Enemy_Stats enemy, int width, int height, Player player, int direction) {
		
		super(enemy.getEnemyXValue() , enemy.getEnemyYValue() ,cam, image, width, height);
		this.enemy = enemy;
		this.player = player;
		ticks = 0;
	}

	

	@Override
	public void render(Graphics2D g2d) {
		if (enemy.getHitpoints() > 0) {
			if (enemy.getEnemyXValue()  <  player.getX() ){
				g2d.rotate(-angle, x + camera.getXOffset() + 17, y);
				g2d.drawImage(img,x + camera.getXOffset() + 17, y, width, height, null);
				g2d.rotate(angle, x + camera.getXOffset() + 17 , y);
			}
			else {
				if(enemy.getEnemyXValue() >  player.getX() ) {
					g2d.rotate(angle, x + camera.getXOffset() + 17, y);
					g2d.drawImage(img,x + camera.getXOffset()+ 17, y, -width, height, null);
					g2d.rotate(-angle, x + camera.getXOffset() + 17, y);
				}
			}
		}
	}

	@Override
	public void tick() {
		if (enemy != null) {
			if(enemy.getEnemyType()==1) {
				x = enemy.getEnemyXValue() + enemy.getEnemy_width()[0]/2;
				y = enemy.getEnemyYValue() + enemy.getEnemy_height()[0]/2;
			}
			else {
				//if(enemy.getEnemyType()==2) {
				x = enemy.getEnemyXValue();
				y = enemy.getEnemyYValue() + 24;
			}
			/*else
				if(enemy.getEnemyType()==3) {
					x = enemy.getEnemyXValue() + 24;
					y = enemy.getEnemyYValue() + 24;
				}*/
			if (Math.abs(enemy.getEnemyXValue() - player.getX()) <= enemy.getFreezeDistance())
				findAngle();
			ticks ++;
		}
		
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
		if (ticks >= 110 && shoot == false) {
			shoot = true;	
			ticks = 0;
		}
		else
			shoot = false;

		if (enemy.getEnemyXValue()  <  player.getX() && enemy.getEnemyXValue() - player.getX() > -(enemy.getFreezeDistance())) {
			if (shoot == true) {
				bullets.add(new Projectile(x, y, 5, angle, 25,10,camera,enemy.getDamagePoints(),2));
				shoot = false;
			}	
		}
		else
			if(enemy.getEnemyXValue() >  player.getX()  && enemy.getEnemyXValue() - player.getX() < enemy.getFreezeDistance()) {
				if (shoot == true) {
					bullets.add(new Projectile(x, y, -5, -angle, -25,10,camera,enemy.getDamagePoints(),2));
					shoot = false;
			}
				
		}
	}

		
	

}
