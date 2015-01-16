/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
			(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** starting xcoord of column 1 */
	private double xcoordstart = 0;

	
	private static final int RED = 1;
	private static final int ORANGE = 3;
	private static final int YELLOW = 5;
	private static final int GREEN = 7;
	private static final int CYAN = 9;
	
	
	/* Method: run() */
	/** Runs the Breakout program. */
	
	public void run() {

		setup();

	}

	private void setup() {

		/* use to help locate x,y coords DELETE when done*/
		label = new GLabel("");
		label.setFont("Times New Roman-36");
		add(label, 50, 50);
		// Must call this method to be able to get mouse events
		addMouseListeners();
		addRows();

	}

	// This method is called everytime user moves mouse
	public void mouseMoved(MouseEvent e) {
		label.setLabel("Mouse: (" + e.getX() + ", " + e.
				getY() + ")");
	}

	/* method to start 2 rows of same color */ 
	public void addRows() {
			
			for (int rows = 0; rows < 10; rows++){

				for (double bricknum = 0; bricknum < 10; bricknum++) {
					
					brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
					brick.setFilled(true);
					
					switch (rows) {
						case 0: case 1:
							brick.setColor(Color.RED);
							break;
						case 2: case 3:
							brick.setColor(Color.ORANGE);
							break;
						case 4: case 5:
							brick.setColor(Color.YELLOW);
							break;
						case 6: case 7:
							brick.setColor(Color.GREEN);
							break;
						case 8: case 9:
							brick.setColor(Color.CYAN);
							break;
					}								
					add(brick, ((getWidth() - WIDTH) / 2) + (bricknum * (BRICK_WIDTH + BRICK_SEP)),(BRICK_Y_OFFSET+(rows * (BRICK_SEP+BRICK_HEIGHT))));
				}

			}
		}




	/* private instance variables */
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private GLabel label;
   






}
