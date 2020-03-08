/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This is a abstract class that I will use to create all of my platforms, walls and background images like background walls
 */


import java.awt.Graphics;

public abstract class Entities {
	protected boolean interactable;		// is a boolean value to check to see if the player can interact with this object
	protected int x,y,width,height,id;		//these values are protected because they are common for all child classes
	public Entities (boolean interactable, int x, int y, int width, int height, int id) {
		this.x = x;			//intializing the values
		this.y = y;
		this.height = height;
		this.width = width;
		this.interactable = interactable;
		this.id = id;
	}
	/*
	 * pre: none
	 * post: returns id
	 * Description: Returns's the object's id
	 */
	public int getId() {
		return id;
	}

	/*
	 * pre: none
	 * post: sets id
	 * Description: Sets the object's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	public abstract void render(Graphics g);

	/*
	 * pre: none
	 * post: gets interactable
	 * Description: Returns interactable
	 */
	public boolean isInteractable() {
		return interactable;
	}

	/*
	 * pre: none
	 * post: sets interactable
	 * Description: Sets interactable the boolean
	 */
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}

	/*
	 * pre: none
	 * post: returns x
	 * Description: gets the x position
	 */
	public int getX() {
		return x;
	}

	/*
	 * pre: none
	 * post: sets x
	 * Description: sets the x position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/*
	 * pre: none
	 * post: gets y
	 * Description: returns the y position
	 */
	public int getY() {
		return y;
	}

	/*
	 * pre: none
	 * post: sets the y positon
	 * Description: Sets the y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * pre: none
	 * post: gets width
	 * Description: Returns the width
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * pre: none
	 * post: sets width
	 * Description: sets the width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/*
	 * pre: none
	 * post: gets the height
	 * Description: returns the height
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * pre: none
	 * post: sets the height
	 * Description: sets the height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
