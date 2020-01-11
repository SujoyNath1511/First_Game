package main_game_code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sprites.SpriteSheet;

public class Boss  {
	private int bossXValue;
	private int bossYValue;
	private int bossHitPoints;
	private int bossDamagePoints;
	private int bossXVelocity;
	private Player player;
	private Camera camera;
	private int freezeDistance;
	private BufferedImage tempImg;
	private int frameCounter;
	private BufferedImage [] sprites;
	private int bossWidth;
	private int bossHeight;
	private Boss_Gun bossGun1;
	private Boss_Gun bossGun2;
	private Boss_Gun bossGun3;
	private int ticks;
	private MenuState menu;

	public Boss(int newHitPoints, int newDamagePoints, int x, int y, Camera camera, Player player) {
		//super(newHitPoints, newDamagePoints, 4, x, y, camera, player, sheet, direction);
		this.bossXValue = x;
		this.bossYValue = y;
		bossWidth = 339;
		bossHeight = 145;
		freezeDistance = 600;
		this.camera = camera;
		this.player = player;
		bossHitPoints = newHitPoints;
		this.tempImg = ImageLoader.loadImage("/animationSheets/boss.png");
		bossGun1 = new Boss_Gun(bossXValue + 155, bossYValue, camera,ImageLoader.loadImage("/textures/tank_gun_1.png") , 208, 22, this, player, newDamagePoints, 1);
		bossGun2 = new Boss_Gun(bossXValue + 100, bossYValue - 10, camera, null, 50,30,this,player,newDamagePoints, 2);
		bossGun3 = new Boss_Gun(bossXValue + 150, bossYValue + 45, camera,ImageLoader.loadImage("/textures/tank_gun_2.png") , 60,30, this, player, newDamagePoints, 3);
		frameCounter = 0;
		sprites = new BufferedImage[12];
		for (int i = 0; i < 12; i++) {
			sprites[i] = tempImg.getSubimage(i * bossWidth * 2, 0,bossWidth * 2, bossHeight * 2);
		}
		ticks = 0;
	}
	public MenuState getMenu() {
		return menu;
	}
	public void setMenu(MenuState menu) {
		this.menu = menu;
	}
	public void setBossHitPoints(int bossHitPoints) {
		this.bossHitPoints = bossHitPoints;
	}
	public void bossMovement() {
		if(bossXValue  <  player.getX()) {//if statement to check if the player is to the right of the enemy
			bossXVelocity = 3;
			if (bossXValue - player.getX() > -freezeDistance) {
				bossXVelocity = 0;
				bossGun1.attack();
				bossGun2.attack();
				bossGun3.attack();
			}

		}
		else
			if(bossXValue  >  player.getX()) {
				bossXVelocity = -3;
				if(bossXValue - player.getX() < freezeDistance) {
					bossXVelocity = 0;
					bossGun1.attack();
					bossGun2.attack();
					bossGun3.attack();
				}
			}
		bossXValue += bossXVelocity;
		if (bossXValue + bossWidth > 7911) {
			bossXValue = 7911;
		}
		else if (bossXValue < 6588){
			bossXValue = 6890;
		}
	}
	public int getBossXValue() {
		return bossXValue;
	}
	public int getBossYValue() {
		return bossYValue;
	}
	public int getBossDamagePoints() {
		return bossDamagePoints;
	}
	public int getBossHitPoints() {
		return bossHitPoints;
	}
	public void render (Graphics g) {
		if (player.isBossFight() == true) {
			if (bossHitPoints > 0) {
				bossGun1.render((Graphics2D) g);
				bossGun2.render((Graphics2D) g);
				if (bossXVelocity == 0) {
					g.drawImage(sprites[0], bossXValue + camera.getXOffset(), bossYValue,bossWidth,bossHeight, null);//if walking is 2 (moving right)

				}
				else
					g.drawImage(sprites[frameCounter], bossXValue + camera.getXOffset(),bossYValue,bossWidth,bossHeight, null);	//draw the sprite on the screen, based on which part of the walking animation is
				frameCounter++;
				if (frameCounter > 8) { 
					frameCounter = 0;
				}
				bossGun3.render((Graphics2D) g);
			}
			else {
				if (ticks <= 180)
					g.drawImage(sprites[ticks/60 + 8], bossXValue + camera.getXOffset(),bossYValue, bossWidth, bossHeight, null);
				else {
					g.setColor(Color.white);
					g.setFont(new Font("arial", Font.BOLD, 16));
					g.drawString("You Win", Frame.WINDOW_WIDTH/2, Frame.WINDOW_HEIGHT/2);
				}
			}
		}
	}
	public void tick (ArrayList<Projectile> projectiles) {
		if (player.isBossFight() == true) {
			if (bossHitPoints > 0) {
				bossGun1.tick();
				bossGun2.tick();
				bossGun3.tick();
				bossMovement();
			}
			else {
				ticks++;
				if (ticks >= 360) {
					menu.setState(0);
					bossHitPoints = 1000;
					bossXValue = 7572;
					bossYValue = 424;
					player.setDeathCounter(3);
					player.setBossFight(false);
					ticks = 0;
				}
			}
		}
		//boss_bullet_collision(projectiles);
	}
	public void boss_bullet_collision(Projectile projectile) {
		bossHitPoints -= projectile.getDamage();
	}
	public Rectangle getBounds() {
		return new Rectangle(bossXValue,bossYValue,bossWidth,bossHeight);
	}
	public int getBossWidth() {
		return bossWidth;
	}
	public int getBossHeight() {
		return bossHeight;
	}

}
