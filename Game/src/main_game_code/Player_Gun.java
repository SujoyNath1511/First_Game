package main_game_code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

public class Player_Gun extends Gun{
	private int cursorX;
	private int cursorY;
	private Player player;
	public Player_Gun(Camera cam, BufferedImage image, Player player, int width, int height) {
		super(player.getX() + Player.PLAYER_WIDTH/2, player.getY() + Player.PLAYER_HEIGHT/2, cam, image, width, height);
		this.player = player;
	}
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		if (x <= cursorX) {
			g2d.rotate(-angle, x + camera.getXOffset() + 8, y);
			g2d.drawImage(img,x + camera.getXOffset() + 8, y, width, height, null);
			g2d.rotate(angle, x + camera.getXOffset() + 8, y);
		}
		else {
			g2d.rotate(angle, x + camera.getXOffset() + 17, y);
			g2d.drawImage(img,x + camera.getXOffset() + 17, y, -width, height, null);
			g2d.rotate(-angle, x + camera.getXOffset() + 17, y);
		}
		//g2d.dispose();
		
	}
	public void tick() {
		cursorX = (int) (MouseInfo.getPointerInfo().getLocation().getX() - camera.getXOffset());
		cursorY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		x = player.getX();
		y = player.getY() + 24;
		findAngle();
		
	}
	public void findAngle() {
		int tempX = cursorX - x;
		int tempY = -(cursorY - y);
		double tempHype = Math.sqrt((tempX * tempX) + (tempY * tempY));
		if (tempX != 0) {
			angle = Math.asin(tempY/tempHype);
		}
	}
	public int getCursorX() {
		return cursorX;
	}
	public int getCursorY() {
		return cursorY;
	}
	public void fire() {
		if (player.getHealth() > 0) {
			if (x <= cursorX)
				bullets.add(new Projectile(x + 8, y, 28, angle, 36,14,camera, 30, 1));
			else
				bullets.add(new Projectile(x + 17, y, -28, -angle, -36,14,camera, 30, 1));
		}
	}

}
