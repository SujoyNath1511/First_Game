package sujoy_Code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

//import com.sun.xml.internal.ws.api.server.Container;

public class MenuState  {
	 private Frame frame;
	 private BufferedImage laser_gun, laser;
	 private boolean state;
	 private int mouseX,mouseY;
	 JButton b1 = new JButton();
	//private MouseManager mouseManager;
	public MenuState(Frame frame) {
		//super();
		this.frame = frame;
		state = true;
		laser_gun = ImageLoader.loadImage("/textures/laser_gun.png");
		laser = ImageLoader.loadImage("/textures/laser.png");
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public void tick() {
		mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
	    mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
		//System.out.println(mouseX + "    " + mouseY);
	}

	public void render(Graphics g) {
		g.drawString(mouseX+","+mouseY,50,100);
		g.setColor(Color.BLACK);
		g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+50, Frame.WINDOW_HEIGHT/2, 200,100);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("START",Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+100, Frame.WINDOW_HEIGHT/2+60);
		g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+350, Frame.WINDOW_HEIGHT/2, 200,100);
		g.drawString("Controls",730, Frame.WINDOW_HEIGHT/2+60);
		g.setFont(new Font("arial", Font.BOLD, 100));
		g.drawString("Welcome To NoName", 125, 100);
		g.drawImage(laser_gun, 427, 200, 200, 100, null);
		g.drawImage(laser, 0, 198, 200, 300, null);
		
		
		
	}


}
