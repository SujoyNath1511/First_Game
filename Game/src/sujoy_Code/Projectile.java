package sujoy_Code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Projectile {
	private int x;
	private int y;
	private int xVel;
	private int yVel;
	private int vel;
	private int width;
	private int height;
	private Camera camera;
	private double angle;
	private BufferedImage img;
	private boolean collide;
	public Projectile(int x, int y, int vel, double angle, int width, int height, Camera cam) {
		camera = cam;
		img = ImageLoader.loadImage("/textures/laser.png");
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.vel = vel;
		this.angle = angle;
		xVel = (int) (vel * Math.cos(-angle));
		yVel = (int) (vel * Math.sin(-angle));
		collide = false;
	}
	public void tick() {
		if (collide == false) {
			x += xVel;
			y += yVel;
			if (x + camera.getXOffset() > Frame.WINDOW_WIDTH || x + camera.getXOffset() < 0) {
				collide = true;
			}
		}
	}
	public void render(Graphics2D g2d) {
		g2d.drawString(x + "," + y, 200, 200);
		if (collide == false) {
			g2d.rotate(-angle, x + camera.getXOffset(), y);
			g2d.drawImage(img, x + camera.getXOffset(), y, width, height, null);
			g2d.rotate(angle, x + camera.getXOffset(), y);
		}
	}
	public Rectangle getBounds() {
		if (width > 0) {
			return new Rectangle(x,y,width,height);
		}
		else {
			return new Rectangle(x + width,y,-width,height);
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getxVel() {
		return xVel;
	}
	public void setxVel(int xVel) {
		this.xVel = xVel;
	}
	public int getyVel() {
		return yVel;
	}
	public void setyVel(int yVel) {
		this.yVel = yVel;
	}
	public int getVel() {
		return vel;
	}
	public void setVel(int vel) {
		this.vel = vel;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean collisionDetection(Rectangle tempRec) {
		if (getBounds().intersects(tempRec) == true) {
			collide = true;
			return true;
		}
		return false;
	}
	public boolean getCollide() {
		return collide;
	}
}
