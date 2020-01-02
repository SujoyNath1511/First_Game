package sujoy_Code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Gun implements MouseListener{
	private int x;
	private int y;
	private int cursorX;
	private int cursorY;
	private int targetX;
	private int targetY;
	private int width;
	private int height;
	private Player player;
	private double angle;
	private BufferedImage img;
	private Camera camera;
	private ArrayList<Projectile> bullets = new ArrayList<Projectile>();
	public Gun(Player play, Camera cam) {
		this.player = play;
		this.camera = cam;
		img = ImageLoader.loadImage("/textures/laser_gun.png");
		x = player.getX() + Player.PLAYER_WIDTH/2;
		y = player.getY() + Player.PLAYER_HEIGHT/2;
		width = 50;
		height = 24;
		angle = 0;
	}
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.drawString(Integer.toString(width)+", " + Integer.toString(height), 500, 100);
		
		if (x <= cursorX) {
			g2d.rotate(-angle, x + camera.getXOffset(), y);
			g2d.drawImage(img,x + camera.getXOffset(), y, width, height, null);
		}
		else {
			g2d.rotate(angle, x + camera.getXOffset(), y);
			g2d.drawImage(img,x + camera.getXOffset(), y, -width, height, null);
		}
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i) != null)
				bullets.get(i).render(g2d);
		}	
	}
	
	public void tick() {
		cursorX = (int) MouseInfo.getPointerInfo().getLocation().getX() - camera.getXOffset();
		cursorY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		x = player.getX() + Player.PLAYER_WIDTH/2;
		y = player.getY() + Player.PLAYER_HEIGHT/2;
		findAngle();
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i) != null) {
				bullets.get(i).tick();
				if (bullets.get(i).getX() > x + 1080) {
					bullets.remove(i);
				}
			}
		}	
		
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
	public double getAngle() {
		return angle;
	}
	public void fire() {
		bullets.add(new Projectile(x, y + height/2, 10, angle, 20,10,camera));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		fire();
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
