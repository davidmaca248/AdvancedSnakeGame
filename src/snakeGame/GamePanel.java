package snakeGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	private static int screenWidth = 700;
	private static int screenHeight = 700;
	private static int unitSize;  /* pixels */
	private static int gameUnits;
	private static int delay = 100;
	
	private int SMALL = 50, MED = 25, LARGE = 10;
	
	/* snake body coordinates (index 0 is head) */
	private int x[];
	private int y[];
	
	int bodyParts = 6;
	int applesEaten;
	
	/* apple coordinates */
	int appleX;
	int appleY;
	
	/* list of wall coordinates */
	int wallX[];
	int wallY[];
	/* new wall coordinate */
	int newWallX;
	int newWallY;
	int numWalls;
	/* more walls spawn the bigger the map */
	int wallsNeeded;
	
	char direction = 'R';
	private boolean running = false;
	Timer timer;
	Random random;
	int mode = 0;  /* 0 = normal, 1 = wall, 2 = speed */
	
	GamePanel(){
		
	}
	
	GamePanel(int size, int gameMode){
		mode = gameMode;
		unitSize = size;  
		
		/* set map size*/
		if(unitSize == SMALL) {      /* small map */
			screenWidth = 750;
			screenHeight = 725;
		}
		else if(unitSize == MED) {  /* medium map */
			screenWidth = 780;
			screenHeight = 725;
		}
		else {                     /* large map */
			screenWidth = 790;
			screenHeight = 740;
		}
		
		gameUnits = (screenWidth*screenHeight)/(unitSize * unitSize);
		x = new int[gameUnits];
		y = new int[gameUnits];
		
		wallX = new int[gameUnits];
		wallY = new int[gameUnits];
		
		/* if wall mode */
		if(mode == 1) {
			/* Set walls needed */
			if(unitSize == SMALL) {
				wallsNeeded = 1;
			}
			else if(unitSize == MED){
				wallsNeeded = 5;
			}
			else { /* large */
				wallsNeeded = 20;
			}
		}
		
		random = new Random();
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		this.setEnabled(true);
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		
		/* different map sizes have different delays */
		if(unitSize == 50) { // small
			delay = 130;
		}
		else if(unitSize == 25) { // med
			delay = 100;
		}
		else{ // large
			delay = 50;
		}
		
		/* set the delay for each actionPerformed() call */
		timer = new Timer(delay,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	/* draw the apple, snake, score text on the panel */
	public void draw(Graphics g) {
		
		if(running) {

			/* Draw apple */
			/* on coordinates appleX and appleY with unitSize by unitSize pixels */
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, unitSize, unitSize);
		
			/* Draw snake */
			/* For every body part coordinate (x[] and y[]) with unitSize by unitSize pixels  */
			for(int i = 0; i< bodyParts;i++) {
				/* head */
				if(i == 0) {
					g.setColor(Color.yellow);
					
					/* Fill head based on the current direction */
					if(direction == 'U') {
						g.fillArc(x[i], y[i], unitSize, unitSize, 135, 270);
						g.fillRect(x[i], y[i]+ unitSize/2, unitSize, unitSize/2);

					} 
					else if(direction == 'D') {
						g.fillArc(x[i], y[i], unitSize, unitSize, 315, 270);
						g.fillRect(x[i], y[i], unitSize, unitSize/2);
					}
					else if(direction == 'L') {
						g.fillArc(x[i], y[i], unitSize, unitSize, 225, 270);
						g.fillRect(x[i] + unitSize/2, y[i], unitSize/2, unitSize);
					}	
					else {
						g.fillArc(x[i], y[i], unitSize, unitSize, 45, 270);
						g.fillRect(x[i], y[i], unitSize/2, unitSize);
					}
				}
				/* body */
				else {
					g.setColor(Color.decode("#34e8e2"));
					g.fillRect(x[i], y[i], unitSize, unitSize);
				}			
			}
			
			/* Draw walls */
			for(int i = 0; i< numWalls;i++) {
					g.setColor(Color.white);
					g.fillRect(wallX[i], wallY[i], unitSize, unitSize);
			}
						
			/* Score */
			g.setColor(Color.lightGray);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (screenWidth - metrics.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
	}
	
	/* Calculates the new coordinates for the apple */
	public void newApple(){
		boolean found = false;
		boolean collision;
		
		while(!found) {
			collision = false;
			appleX = random.nextInt((int)(screenWidth/unitSize))*unitSize;
			appleY = random.nextInt((int)(screenHeight/unitSize))*unitSize;
			
			/* Dont spawn apple on body parts */
			/* For every body part, check if newApple is in the same coordinate */
			for(int i = bodyParts;i>0;i--) {
				if((appleX == x[i]) && (appleY== y[i])) {
					collision = true;
					break;
				}
			}
			
			/* Dont spawn apple on walls */
			/* For every wall, check if newApple is in the same coordinate */
			for(int i = numWalls;i>0;i--) {
				if((appleX == wallX[i]) && (appleY == wallY[i])) {
					collision = true;
					break;
				}
			}
			
			if(!collision) {
				found = true;
			}
		}

	}
	
	/* Calculates the new coordinates for a wall */
	public void newWall(){
		boolean collision;

		int wallsGen = 0;
		
		while(wallsGen != wallsNeeded) {
			collision = false;
			newWallX = random.nextInt((int)(screenWidth/unitSize))*unitSize;
			newWallY = random.nextInt((int)(screenHeight/unitSize))*unitSize;
			
			/* Dont spawn walls on body parts */
			/* For every body part, check if newWall is in the same coordinate */
			for(int i = bodyParts;i>0;i--) {
				if((newWallX == x[i]) && (newWallY == y[i])) {
					collision = true;
					break;
				}
			}
			
			if(!collision) {
				wallsGen++;
				
				/* add new wall to walls array */
				wallX[numWalls] = newWallX;
				wallY[numWalls] = newWallY;
				numWalls++;
			}
		}
	}
	
	/* Shifts the body coordinates of the snake based on current direction */
	public void move(){
		
		/* for each body part, shift coordinates by 1 (coordinate beside) */
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		/* To change direction */
		/* index 0 is the head */
		switch(direction) {
			/* If the current direction is UP, go to next position (1 unitSize up)*/
			case 'U':
				y[0] = y[0] - unitSize;
				break;
			case 'D':
				y[0] = y[0] + unitSize;
				break;
			case 'L':
				x[0] = x[0] - unitSize;
				break;
			case 'R':
				x[0] = x[0] + unitSize;
				break;
		}
		
	}
	public void checkApple() {
		
		/* If head is on the same spot as the apple = eaten */
		if((x[0] == appleX) && (y[0] == appleY)) {
			
			/* if wall mode, add walls */
			if(mode == 1) {
				newWall();
			} 
			/* if speed mode */
			else if(mode == 2) {
				delay -= (delay/30);     // increase speed
				timer = new Timer(delay,this);
				timer.start();
			}
			else {}
			
			/* Add body parts */
			if(unitSize == SMALL) {
				bodyParts += 2;	
			} 
			else if (unitSize == MED) {
				bodyParts += 5;
			}else {
				bodyParts += 10;
			}
			
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		
		/* checks if head collides with wall */
		/* For every wall, check if head is in the same coordinate */
		for(int i = numWalls;i>0;i--) {
			if((x[0] == wallX[i]) && (y[0] == wallY[i])) {
				running = false;
			}
		}
		
		/* checks if head collides with body */
		/* For every body part, check if head is in the same coordinate */
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		
		/* if head touches borders */
		if( //if head touches left border
			x[0] < 0 ||
			//if head touches right border
			x[0] > screenWidth ||
			//if head touches top border
			y[0] < 0 ||
			//if head touches bottom border
			y[0] > screenHeight) {
			
			running = false;
		}
	
		if(!running) {
			/* Stop calling actionPerformed() */
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		
		//Score text (same spot)
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+ applesEaten, (screenWidth - metrics1.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());
		
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screenWidth - metrics2.stringWidth("Game Over"))/2, screenHeight/3);
		
		/* disable game panel*/
		this.setEnabled(false);
	}
	
	/* Constantly runs in a loop */
	@Override
	public void actionPerformed(ActionEvent e) {

		/* while running, move the snake, check if apple eaten, and check collisions */
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		
		/* Calls paintComponent()*/
		/* to draw based on the new coordinates */
		repaint();
	}
	
	/* Changes the direction */
	/* Listens to keyboard presses */
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
				}
		}
	}
}