package main_game_code;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter{
	private boolean leftPressed;
	private Player_Gun gun;
	private MenuState menu;
	public MouseManager(Player_Gun gun) {
		this.gun = gun;
		leftPressed = false;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if (menu.isState() == 0 || menu.isState() == 2)
			leftPressed = true;
		else
			if(menu.isState() == 1)
				gun.fire();
	}
	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}
	public boolean getLeftPressed() {
		return leftPressed;
	}
	public MenuState getMenu() {
		return menu;
	}
	public void setMenu(MenuState menu) {
		this.menu = menu;
	}
	
}
