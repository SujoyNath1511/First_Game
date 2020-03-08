/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class was made to be able to make a scrolling camera that follows the player and helps render the world
 */


public class Camera {
	private int xOffset;		//this is the x offset. The x offset is by how much do you subtract (or in this case add a negative) to an object's x postion
								//to render onto the little screen
	
	public Camera() {
		this.xOffset = 0;
	}

	/*
	 * pre: player has been instantiated, camera has been instantiated
	 * post: camera has been centered around the player
	 * Description: Centers the camera around the player
	 */
	public void center(Player player) {
		xOffset = -(player.getX() - Frame.WINDOW_WIDTH/2 + 32);
	}

	/*
	 * pre: none 
	 * post: returns xOffset
	 * Description: Gets the x offset of the camera
	 */
	public int getXOffset() {
		return xOffset;
	}

	/*
	 * pre:none
	 * post: sets xOffset
	 * Description: sets the xOffset of the camera
	 */
	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}
}
