package main_game_code;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Boss_Gun extends Gun{
	private static int ticks;
	private static int ticks2;
	private static int ticks3;
	boolean shoot;
	boolean shoot2;
	private Player player;
	private Boss boss;
	private int damage;
	private int type;
	public Boss_Gun(int x, int y, Camera cam, BufferedImage image, int width, int height, Boss boss, Player player, int damage, int type) {
		super(x, y, cam, image, width, height);
		this.boss = boss;
		this.player = player;
		this.damage = damage;
		this.type = type;
		ticks = 0;
		ticks2 = 0;
		ticks3 = 0;
	}
	// TODO Auto-generated constructor stub
	public void tick() {
		ticks++;
		ticks2++;
		ticks3++;
		if (type == 1) {
			x = boss.getBossXValue() + 150;
			y = boss.getBossYValue();
		}
		else if (type == 2){
			x = boss.getBossXValue() + 100;
			y = boss.getBossYValue();
		}
		else {
			x = boss.getBossXValue() + 150;
			y = boss.getBossYValue() + 45;
		}
		if (Math.abs(boss.getBossXValue() - player.getX()) <= 1000)
			findAngle();
	}


	@Override
	public void render(Graphics2D g2d) {
		if (type != 2) {
			if (boss.getBossXValue()  <  player.getX() ){
				g2d.rotate(-angle, x + camera.getXOffset(), y);
				g2d.drawImage(img,x + camera.getXOffset(), y, width, height, null);
				g2d.rotate(angle, x + camera.getXOffset(), y);
			}
			else {
				if(boss.getBossXValue() >  player.getX()) {
					g2d.rotate(angle, x + camera.getXOffset(), y);
					g2d.drawImage(img,x + camera.getXOffset(), y, -width, height, null);
					g2d.rotate(-angle, x + camera.getXOffset(), y);
				}
			}
		}
		//g2d.dispose();
	}

	@Override
	public void findAngle() {
		int tempX;
		int tempY;
		tempX = player.getX() - x;
		tempY = -(player.getY() - y);
		double tempHype = Math.sqrt((tempX * tempX) + (tempY * tempY));
		if (tempX != 0) {
			angle = Math.asin(tempY/tempHype);
			if (angle < -Math.PI/6) {
				angle = -Math.PI/6;
			}
		}

	}

	
	public void attack() {
		if (ticks >= 180 && shoot == false && type ==1) {
			ticks = 0;
			shoot = true;		
		}
		else
			shoot = false;
		if (ticks2 >= 480 && shoot2 == false && type == 2) {
			ticks2 = 0;
			shoot2 = true;
		}
		else
			shoot2 = false;
		if (ticks3 >= 30 && type == 3) {
			ticks3 = 0;
			if (boss.getBossXValue()  <  player.getX())
				bullets.add(new Projectile(x + 2, y, 10, angle, 30,10,camera,2,2));
			else
				bullets.add(new Projectile(x + 2, y, -10, -angle, 30,10,camera,2,2));
		}
		if (type == 1) {
			if (boss.getBossXValue()  <  player.getX() && boss.getBossXValue() - player.getX() > -500) {
				if (shoot == true) {
					bullets.add(new Projectile(x + 2, y, 10, angle, 50,10,camera,damage * type,2));
					shoot = false;
				}	
			}
			else
				if(boss.getBossXValue() >  player.getX()  && boss.getBossXValue() - player.getX() < 500) {
					if (shoot == true) {
						bullets.add(new Projectile(x + 2, y, -10, -angle, -50,10,camera,damage * type,2));
						shoot = false;
					}

				}
		}
		else if (shoot2 == true) {
			shoot2 = false;
			recursiveAreaAttack(0);
		}
	}
	public void recursiveAreaAttack(int angleIndex) {
		if (angleIndex == 180) {
			bullets.add(new Projectile(x,y,-2, -Math.PI, -25,20,camera,damage * type, 3));
		}	
		else {
			if (angleIndex <= 90) {
				bullets.add(new Projectile(x,y,6, Math.toRadians(angleIndex), 25, 20, camera,damage/type,3));
			}
			else {
				bullets.add(new Projectile(x,y,-6, -Math.toRadians(angleIndex - 90), -25, 20, camera,damage/type,3));
			}
			recursiveAreaAttack(angleIndex + 5);
		}
	}
	@Override
	public void fire() {}
}
