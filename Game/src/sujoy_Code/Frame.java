/*
 * Name: Sujoy Deb Nath
 * Last Edited: December 19,2019
 * Description: Frame extends the JFrame class and creates the window for the game. It also makes a canvas to draw.
 */

//imports and packages
package sujoy_Code;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")		//Blocks the serializable warning
public class Frame extends JFrame{
	public static final int WINDOW_HEIGHT = 1080;	
	public static final int WINDOW_WIDTH = 1920;
	private Canvas canvas;		//create a canvas object
	private Cursor cursor;
	private BufferedImage crosshair;
	public Frame() {
		super("Game Window");		//make a JFrame
		createFrame();			//call the createFrame method, made another method to keep the code organized.
	}
	
	/*
	 * Pre: none
	 * post: a canvas object is created and added to the Jframe
	 * Description: Sets the JFrame conditions and creates a canvas.
	 */
	public void createFrame() {
		this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		canvas = new Canvas();				//create and canvas object and set it's maximum and minimum size
		canvas.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		canvas.setMaximumSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		canvas.setMinimumSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		canvas.setFocusable(false);		//turns the focus away from canvas and onto the Jframe (required for keyListener)
		canvas.setBackground(Color.CYAN);
		this.add(canvas); //add canvas component to the window
		
		crosshair = ImageLoader.loadImage("/textures/cursor.png");
		
		cursor = Toolkit.getDefaultToolkit().createCustomCursor(crosshair, new Point(0,0), null);
		this.setCursor(cursor);
		
		this.pack();   //basically compiles or resizes the canvas to fit the window.
	}
	/*
	 * Pre: canvas has been instantiated
	 * post: returns canvas
	 * Description: Returns canvas for external use
	 */
	public Canvas getCanvas() {
		return canvas;
	}
	/*
	 * Pre: Frame has been instantiated
	 * post: returns the current instance of Frame
	 * Description: Returns Frame for external use
	 */
	public JFrame getFrame() {
		return this;	//return the current object
	}
}
