package sujoy_Code;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Gun{
	protected int x;
	protected int y;
	
	protected int width;
	protected int height;
	
	protected double angle;
	protected BufferedImage img;
	protected Camera camera;
	protected ArrayList<Projectile> bullets;
	public Gun(int x, int y, Camera cam, BufferedImage image, int width, int height) {
		this.camera = cam;
		img = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		angle = 0;
		bullets = new ArrayList<Projectile>();
	}
	public abstract void render(Graphics2D g2d);
	public abstract void tick();
	public ArrayList<Projectile> getBullets() {
		return bullets;
	}
	public void setBullets(ArrayList<Projectile> bullets) {
		this.bullets = bullets;
	}
	protected abstract void findAngle();
	public double getAngle() {
		return angle;
	}
	public abstract void fire();
}
