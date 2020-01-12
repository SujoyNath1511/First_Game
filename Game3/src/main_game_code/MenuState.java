package main_game_code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;


//import com.sun.xml.internal.ws.api.server.Container;

public class MenuState  {
	private BufferedImage laser_gun, laser, wasdKeys, mouseImg, spaceBar, keyA, keyD;
	private int state;
	private int mouseX,mouseY;
	private MouseManager mouse;
	private Game game;
	public MenuState(Frame frame, MouseManager mouse, Game game) {
		state = 0;
		laser_gun = ImageLoader.loadImage("resources/textures/laser_gun.png");
		laser = ImageLoader.loadImage("resources/textures/laser.png");
		//skull = ImageLoader.loadImage("/textures/evil_skull.png");
		wasdKeys = ImageLoader.loadImage("resources/textures/wasd_keys.png");
		mouseImg = ImageLoader.loadImage("resources/textures/mouse_leftclick.png");
		spaceBar = ImageLoader.loadImage("resources/textures/space_bar.png");
		keyA = ImageLoader.loadImage("resources/textures/key_a.png");
		keyD = ImageLoader.loadImage("resources/textures/key_d.png");
		this.mouse = mouse;
		this.game = game;
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
			g.setColor(Color.BLACK);
			g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+50, Frame.WINDOW_HEIGHT/2, 200,100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("START",Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+100, Frame.WINDOW_HEIGHT/2+60);
			g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+350, Frame.WINDOW_HEIGHT/2, 200,100);
			g.drawString("CONTROLS",710, Frame.WINDOW_HEIGHT/2+60);
			g.setFont(new Font("arial", Font.BOLD, 100));
			g.drawString("Welcome to No Land", 125, 100);
			g.drawRect(542, 582, 200,100);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("QUIT",605, 642);
			g.drawImage(laser_gun, 427, 200, 200, 100, null);
			g.drawImage(laser, 669, 225, 200, 50, null);
			g.drawImage(laser, 900, 225, 200, 50, null);
			//g.drawImage(skull, 275,200,125,150,null);


		}
		else
			if(state == 2) {
				g.setFont(new Font("arial", Font.BOLD, 30));
				g.drawRect(Frame.WINDOW_WIDTH/2-Frame.WINDOW_WIDTH/4+200, Frame.WINDOW_HEIGHT/2+100, 200,50);
				g.drawString("BACK",600, 520);
				g.drawImage(wasdKeys,180,116,180,116,null);
				g.drawString(" :MOVE", 381, 194);
				g.drawImage(mouseImg,826,110,180,150,null);
				g.drawString(":SHOOT", 1000, 194);
				g.drawImage(spaceBar,170,310,200,50,null);
				g.drawString(" + ", 250, 400);
				g.drawImage(keyA,240,415,50,50,null);
				g.drawString("TELEPORT LEFT", 150, 520);
				g.drawImage(spaceBar,880,310,200,50,null);
				g.drawString(" + ", 960, 400);
				g.drawImage(keyD,960,415,50,50,null);
				g.drawString("TELEPORT RIGHT", 850, 520);

			}

	}
	public void checkMouse() {
		if(state == 0) {
			if(mouseX > 400 && mouseX < 600) {
				if(mouseY > 415 && mouseY < 515)
					if(mouse.getLeftPressed()==true) {
						state = 1;
						mouse.setLeftPressed(false);
					}
			}
			else
				if(mouseX > 700 && mouseX < 900) {
					if(mouseY > 415 && mouseY < 515)
						if(mouse.getLeftPressed()==true) {
							state = 2;
							mouse.setLeftPressed(false);
						}
				}


		}
		else {
			if(mouseX > 550 && mouseX < 700) 
				if(mouseY > 515 && mouseY < 565)
					if(mouse.getLeftPressed()==true) {
						state = 0;
						mouse.setLeftPressed(false);
					}
		}
		if(mouseX > 550 && mouseX < 750)
			if(mouseY > 615 && mouseY < 715)
				if(mouse.getLeftPressed()==true) {
					game.setRunning(false);
					mouse.setLeftPressed(false);
				}
	}
}
