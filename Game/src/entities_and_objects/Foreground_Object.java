package entities_and_objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import sujoy_Code.Camera;
import sujoy_Code.ImageLoader;

public class Foreground_Object extends Entities {
	private Camera camera;
	private int id;
	public static final Color[] COLOR_ARRAY = {Color.GREEN, Color.BLACK, Color.YELLOW, Color.RED, Color.ORANGE};
	private static BufferedImage[] textures = {ImageLoader.loadImage("/textures/stone.png")};
	public Foreground_Object(boolean interactable, int x, int y, int width, int height, Camera camera, int id) {
		super(interactable, x, y, width, height);
		this.camera = camera;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(COLOR_ARRAY[id]);
		if (id == 0) {
			g.drawImage(textures[0].getSubimage(0, 0, width, height),x + camera.getXOffset(), y,null);
		}
		else
			g.fillRect(x + camera.getXOffset(), y + camera.getYOffset(), width, height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle (x,y,width,height);
	}

	@Override
	public void tick(int x, int y) {
		super.x = x;
		super.y = y;
		
	}
	public boolean getInteractable() {
		return interactable;
	}
	public String toString() {
		return Integer.toString(id);
	}

}
