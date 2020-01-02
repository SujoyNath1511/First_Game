package sujoy_Code;

import java.awt.Graphics2D;

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
	public Projectile(int x, int y, int vel, double angle, int width, int height, Camera cam) {
		camera = cam;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.vel = vel;
		this.angle = angle;
	}
	public void tick() {
		xVel = (int) (vel * Math.cos(-angle));
		yVel = (int) (vel * Math.sin(-angle));
		x += xVel;
		y += yVel;
	}
	public void render(Graphics2D g2d) {
		g2d.fillRect(x + camera.getXOffset(), y, width, height);
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
	public void collisionDetection() {
		
	}
}
