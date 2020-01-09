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
	 private BufferedImage laser_gun, laser, skull;
	 private int state;
	 private int mouseX,mouseY;
	 private MouseManager mouse;
	 JButton b1 = new JButton();
	//private MouseManager mouseManager;
	public MenuState(Frame frame, MouseManager mouse) {
		//super();
		this.frame = frame;
		state = 0;
		laser_gun = ImageLoader.loadImage("/textures/laser_gun.png");
		laser = ImageLoader.loadImage("/textures/laser.png");
		//skull = ImageLoader.loadImage("/textures/evil_skull.png");
		this.mouse = mouse;
	}
	public int isState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public void tick() {
		mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
	    mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();
	    checkMouse();
		//System.out.println(mouseX + "    " + mouseY);
	}

	public void render(Graphics g) {
		if (state == 0) {
			g.drawString(mouseX+","+mouseY,50,100);
			g.setColor(Color.BLACK);
			g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+50, Frame.WINDOW_HEIGHT/2, 200,100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("START",Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+100, Frame.WINDOW_HEIGHT/2+60);
			g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+350, Frame.WINDOW_HEIGHT/2, 200,100);
			g.drawString("CONTROLS",710, Frame.WINDOW_HEIGHT/2+60);
			g.setFont(new Font("arial", Font.BOLD, 100));
			g.drawString("Welcome To NoName", 125, 100);
			g.drawImage(laser_gun, 427, 200, 200, 100, null);
			g.drawImage(laser, 669, 225, 200, 50, null);
			g.drawImage(laser, 900, 225, 200, 50, null);
			g.drawImage(skull, 275,200,125,150,null);
		
		}
		else
			if(state == 2) {
				g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+350, Frame.WINDOW_HEIGHT/2, 200,100);
				g.drawString("BACK",710, Frame.WINDOW_HEIGHT/2+60);
			}
		
	}
	public void checkMouse() {
		if(state == 0) {
			if(mouseX > 400 && mouseX < 600) {
				if(mouseY > 415 && mouseY < 515)
					if(mouse.getLeftPressed()==true)
						state = 1;
			}
			else
				if(mouseX > 700 && mouseX < 900) {
					if(mouseY > 415 && mouseY < 515)
						if(mouse.getLeftPressed()==true) 
							state = 2;
						
				}
		}
		else
			if(state == 2) {
					if(mouseX > 400 && mouseX < 600) {
						if(mouseY > 415 && mouseY < 515)
							if(mouse.getLeftPressed()==true)
								state = 0;
					}
			}
				
	}


}
