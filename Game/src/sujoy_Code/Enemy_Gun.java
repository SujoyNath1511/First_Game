package sujoy_Code;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy_Gun extends Gun {
	private Player player;
	private Enemy_Stats[] enemies;
	public Enemy_Gun(int x, int y, Camera cam, BufferedImage image, int width, int height, Enemy_Stats[] enemies) {
		super(x, y, cam, image, width, height);
		this.enemies = enemies;
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void findAngle() {
		int tempX;
		int tempY;
		for (int i = 0; i < enemies.length; i++) {
			tempX = player.getX() - enemies[i].getEnemyXValue();
			tempY = -(player.getY() - enemies[i].getEnemyYValue());
			double tempHype = Math.sqrt((tempX * tempX) + (tempY * tempY));
			if (tempX != 0) {
				angle = Math.asin(tempY/tempHype);
			}
		}
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

}
