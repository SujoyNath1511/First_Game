package sujoy_Code;

public class Camera {
	private int xOffset, yOffset;
	
	public Camera() {
		this.xOffset = 0;
		this.yOffset = 0;
	}
	
	public void center(Player player) {
		xOffset = -(player.getX() - Frame.WINDOW_WIDTH/2 + 32);
	}
	
	public void move(int xAmount, int yAmount) {
		xOffset += xAmount;
		yOffset += yAmount;
	}

	public int getXOffset() {
		return xOffset;
	}

	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}
	
}
