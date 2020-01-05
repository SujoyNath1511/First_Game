package sujoy_Code;

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
		g2d.drawString(player.getX() + "," + player.getY(), 10, 100);
		if (x <= cursorX) {
			g2d.rotate(-angle, x + camera.getXOffset(), y);
			g2d.drawImage(img,x + camera.getXOffset(), y, width, height, null);
			g2d.rotate(angle, x + camera.getXOffset(), y);
		}
		else {
			g2d.rotate(angle, x + camera.getXOffset(), y);
			g2d.drawImage(img,x + camera.getXOffset(), y, -width, height, null);
			g2d.rotate(-angle, x + camera.getXOffset(), y);
		}
	}
	public void tick() {
		cursorX = (int) MouseInfo.getPointerInfo().getLocation().getX() - camera.getXOffset();
		cursorY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		x = player.getX() + Player.PLAYER_WIDTH/2;
		y = player.getY() + Player.PLAYER_HEIGHT/2;
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
		if (x <= cursorX)
			bullets.add(new Projectile(x, y, 2, angle, 50,20,camera));
		else
			bullets.add(new Projectile(x, y, -2, -angle, -50,20,camera));
	}

}
