package sujoy_Code;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter{
	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;
	private Player_Gun gun;
	public MouseManager(Player_Gun gun) {
		this.gun = gun;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		gun.fire();
	}
}
