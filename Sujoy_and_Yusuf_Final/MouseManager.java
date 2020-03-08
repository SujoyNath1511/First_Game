/* Name:Yusuf Ali
 * LastDateofEdit:2020-01-11
 * Description: This class controls mouse clicking
 */



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter{
	private boolean leftPressed;//Variable to hold if the left click has been pressed (True = yes, False = no)
	private Player_Gun gun;//Creates an instance of the Player_Gun class
	private MenuState menu;//Creates an instance of the MenuState class
	/*Pre: Takes in a Player_Gun object
	 * Post: Initializes the Player_Gun object and leftPressed varaible to false;
	 */
	public MouseManager(Player_Gun gun) {
		this.gun = gun;
		leftPressed = false;
	}
	@Override
	/*Pre: Takes in an object of MouseEvent
	 * Post: Checks the state runs the appropriate operation
	 */
	public void mousePressed(MouseEvent e) {
		if (menu.isState() == 0 || menu.isState() == 2)//Checks if the the player is currently on the main menu or controls menu
			leftPressed = true;//Sets leftPressed to true of the left click button has been pressed
		else
			if(menu.isState() == 1)//Checks if the payer is playing the game, if the player presses the left click the gun shoots
				gun.fire();
	}
	/*Pre: Takes in an object of MouseEvent
	 * Post: Checks if the left click button has been released an resetting the variable back to false
	 */
	public void mouseReleased(MouseEvent e) {
		leftPressed = false;
	}
	/*Pre: None
	 * Post: returns the leftPresses variable
	 */
	public boolean getLeftPressed() {
		return leftPressed;
	}
	/*
	 * Pre: none
	 * Post: sets leftPressed
	 */
	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}
	/*Pre: None
	 * Post: returns the menu variable
	 */
	public MenuState getMenu() {
		return menu;
	}
	/*Pre: Takes in an object of the MenuState class
	 * Post: sets the new menu state
	 */
	public void setMenu(MenuState menu) {
		this.menu = menu;
	}
	
}
