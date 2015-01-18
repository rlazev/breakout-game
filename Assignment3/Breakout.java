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
	private static final int NTURNS = 5;


	/* Method: run() */
	/** Runs the Breakout program. */

	public void run() {
		
		//* Initializing starting variables ** //
		
		turns = NTURNS;
		totalbricks = (NBRICK_ROWS * NBRICKS_PER_ROW);
		System.out.println(totalbricks);
		startturn = true; 
		gameover = false;
				
		//* setup game board** //
		
		turns--;
		setupBricks();
		setupPaddle();
		setupScoreboard();

		
	
		//* play game** //
				
		
		while (turns >= 0 && checkWin() == false ) {
			if (startturn == true) {
				// starttext = new GLabel("Click mouse to start turn", WIDTH / 2, HEIGHT / 2);
				// add(starttext);
				updateScoreboard();
				playBall();
				startturn = false;
			}

			checkWalls();
			checkCollision();
			moveBall();
		}

	}

	private void setupBricks() {


		for (int rows = 0; rows < NBRICK_ROWS; rows++){

			for (double bricknum = 0; bricknum < NBRICKS_PER_ROW; bricknum++) {

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
				totalbricks ++;
			}

		}
	}

	private void setupPaddle() {

		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle, (getWidth()-PADDLE_WIDTH) / 2, (getHeight() - PADDLE_Y_OFFSET));
		paddlelastx = paddle.getX();
		addMouseListeners();

	}
	private void setupScoreboard() {
		turnboard = new GLabel("Turns Remaining: " + turns, 8,15);
		scoreboard= new GLabel("Bricks Remaining: " + totalbricks, WIDTH - 140,15);
		add(scoreboard);
		add(turnboard);
	}
	
	private void updateScoreboard() {
		remove(scoreboard);
		remove(turnboard);
		turnboard = new GLabel("Turns Remaining: " + turns, 8,15);
		scoreboard= new GLabel("Bricks Remaining: " + totalbricks, WIDTH - 140,15);
		add(scoreboard);
		add(turnboard);
	}
	
	// Called on mouse move to reposition the paddle
	public void mouseMoved(MouseEvent e) {
		if (e.getX()<=340){
			paddle.move(e.getX() - paddlelastx,0);
			paddlelastx = paddle.getX();	
		}

	}
	

	private void playBall() {
		ball = new GOval((WIDTH - BALL_RADIUS) / 2, (HEIGHT - BALL_RADIUS) / 2 , BALL_RADIUS,BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
		pause(700);
		vx = rgen.nextDouble(1.0 , 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		vy = 3.0;
	}


	private void checkWalls() {
		/* Checks if it hits Right Wall using x coord */
		checkSideWalls();
		checkTopWall();
		checkBottomWall();
	}
	
	private void checkSideWalls() {
		if ((ball.getX() + BALL_RADIUS) > APPLICATION_WIDTH || (ball.getX() < 0)) {
			vx = -vx; //reverse direction on x axis
		}	
	}
		
	private void checkTopWall() {
		if (ball.getY() < 0) {
			vy = -vy; //reverse direction on x axis
		}
	}
	
	private void checkBottomWall() {
		if ((ball.getY() + (BALL_RADIUS * 2)) > APPLICATION_HEIGHT) {
			// vy = -vy; //reverse direction on x axis
			turns-- ;
			startturn = true;
			remove(ball);

		}
	}	
	
	private void checkCollision() {
		GObject collider = getCollidingObject();
		if (collider != null) {
			if (collider == paddle) {
				vy = -vy; //reverse direction to go up after hitting paddle
			}
			else {
				
				vy = -vy; //reverse direction to go down after hitting brick
				remove(collider);
				totalbricks --;
				updateScoreboard();
			}
		}
	}
	
	/** Update and move ball */
	private void moveBall() {
		ball.move(vx,vy);
		pause(10);	
		
	}
	
	private boolean checkWin() {
		if (totalbricks == 0) {
			remove(scoreboard);
			scoreboard = new GLabel("You WIN!!!!" , WIDTH / 2, HEIGHT / 2);
			add(scoreboard);
			return true;
		}
		return false;
	}	
	
	
	private GObject getCollidingObject() {
		if (getElementAt(ball.getX(), ball.getY()) != null) {						//check top left of ball
			return getElementAt(ball.getX(), ball.getY());
		}
		if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY()) != null) {	//check top right of ball
			return getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY());
		}
		if (getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS)) != null) {	//check bottom left of ball
			return getElementAt(ball.getX(), ball.getY() + (2 * BALL_RADIUS));
		}
		if (getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS)) != null) {	//check bottom left of ball
			return getElementAt(ball.getX() + (2 * BALL_RADIUS), ball.getY() + (2 * BALL_RADIUS));
		}
		return null;
	}
		
	
		


	/* private instance variables */
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private GLabel turnboard;
	private GLabel scoreboard;
	private GLabel starttext;
	public GObject collider;
	public GObject hit;
	private boolean gameover;
	private double paddlelastx;
	private RandomGenerator rgen = RandomGenerator.getInstance();

	/* used to make sure paddle is clicked and not a brick */
	private double vx, vy;
	private int turns;
	private int totalbricks;
	private boolean startturn;




}
