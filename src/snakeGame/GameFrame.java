package snakeGame;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/* Initializes the GUI */
public class GameFrame extends JFrame implements ActionListener{
	
	Timer timer;
	private final int FRAME_SIZE = 800, SMALL = 50, MEDIUM = 25, LARGE = 10;
	private int mapSize = 25, gameMode = 0;
	
	/* 0 = startPanel, 1 = modePanel, 2 = sizePanel, 3 = gamePanel, 4 = overPanel*/
	private int curPanel = 0;  
	
	private JPanel startPanel, modePanel, modeBtnPanel, sizePanel, sizeBtnPanel, gamePanel, overPanel;
	private JLabel startLabel, sizeLabel, modeLabel;
	private JButton startBtn, smallBtn, medBtn, largeBtn, normalBtn, wallBtn, speedBtn, mapSizeBtn, gameModeBtn, restartBtn;
	
	public GameFrame(){
		
		/* set the delay for each actionPerformed() call */
		timer = new Timer(10,this);
		timer.start();
		
		gamePanel = new GamePanel();
		
		/* Start Panel */
		startPanel = new JPanel();
		startPanel.setLayout(new BorderLayout());
		
		startLabel = new JLabel();
		startLabel.setText("Snake Game");
		startLabel.setForeground(Color.cyan);
		startLabel.setBackground(Color.blue);
		startLabel.setOpaque(true);
		startLabel.setPreferredSize(new Dimension(FRAME_SIZE,550));
		startLabel.setHorizontalAlignment(JLabel.CENTER);
		startLabel.setFont(new Font("Arial",Font.BOLD,100));

		startBtn = new JButton();
		startBtn.setText("Click here to start");
		startBtn.setFocusable(false);
		startBtn.setPreferredSize(new Dimension(FRAME_SIZE,200));
		startBtn.setFont(new Font("Serif",Font.BOLD,30));
		startBtn.setForeground(Color.black);
		startBtn.setBackground(Color.cyan);
		startBtn.addActionListener(this);

		startPanel.add(startLabel,BorderLayout.NORTH);
		startPanel.add(startBtn,BorderLayout.SOUTH);
		
		/* Mode Panel */
		modePanel = new JPanel();
		modePanel.setLayout(new BorderLayout());
		
		modeLabel = new JLabel();
		modeLabel.setText("Choose Game Mode");
		modeLabel.setForeground(Color.red);
		modeLabel.setBackground(Color.darkGray);
		modeLabel.setOpaque(true);
		modeLabel.setPreferredSize(new Dimension(FRAME_SIZE,400));
		modeLabel.setHorizontalAlignment(JLabel.CENTER);
		modeLabel.setFont(new Font("Arial",Font.BOLD,50));
		
		modeBtnPanel = new JPanel();
		modeBtnPanel.setBackground(Color.orange);
		modeBtnPanel.setPreferredSize(new Dimension(FRAME_SIZE,350));
		modeBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER,45,75));
		
		normalBtn = new JButton();
		normalBtn.setText("Normal");
		normalBtn.setFocusable(false);
		normalBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		normalBtn.setFont(new Font("Serif",Font.BOLD,30));
		normalBtn.setForeground(Color.black);
		normalBtn.setBackground(Color.green);
		normalBtn.addActionListener(this);
		
		wallBtn = new JButton();
		wallBtn.setText("Wall");
		wallBtn.setFocusable(false);
		wallBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		wallBtn.setFont(new Font("Serif",Font.BOLD,30));
		wallBtn.setForeground(Color.black);
		wallBtn.setBackground(Color.green);
		wallBtn.addActionListener(this);
		
		speedBtn = new JButton();
		speedBtn.setText("Speed");
		speedBtn.setFocusable(false);
		speedBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		speedBtn.setFont(new Font("Serif",Font.BOLD,30));
		speedBtn.setForeground(Color.black);
		speedBtn.setBackground(Color.green);
		speedBtn.addActionListener(this);
		
		modeBtnPanel.add(normalBtn);
		modeBtnPanel.add(wallBtn);
		modeBtnPanel.add(speedBtn);
		
		modePanel.add(modeLabel,BorderLayout.NORTH);
		modePanel.add(modeBtnPanel,BorderLayout.SOUTH);
		
		
		/* Size Panel */
		sizePanel = new JPanel();
		sizePanel.setLayout(new BorderLayout());
		
		sizeLabel = new JLabel();
		sizeLabel.setText("Choose Map Size");
		sizeLabel.setForeground(Color.red);
		sizeLabel.setBackground(Color.orange);
		sizeLabel.setOpaque(true);
		sizeLabel.setPreferredSize(new Dimension(FRAME_SIZE,400));
		sizeLabel.setHorizontalAlignment(JLabel.CENTER);
		sizeLabel.setFont(new Font("Arial",Font.BOLD,50));
		
		sizeBtnPanel = new JPanel();
		sizeBtnPanel.setBackground(Color.blue);
		sizeBtnPanel.setPreferredSize(new Dimension(FRAME_SIZE,350));
		sizeBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER,45,75));
		
		smallBtn = new JButton();
		smallBtn.setText("Small");
		smallBtn.setFocusable(false);
		smallBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		smallBtn.setFont(new Font("Serif",Font.BOLD,30));
		smallBtn.setForeground(Color.black);
		smallBtn.setBackground(Color.cyan);
		smallBtn.addActionListener(this);
		
		medBtn = new JButton();
		medBtn.setText("Medium");
		medBtn.setFocusable(false);
		medBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		medBtn.setFont(new Font("Serif",Font.BOLD,30));
		medBtn.setForeground(Color.black);
		medBtn.setBackground(Color.cyan);
		medBtn.addActionListener(this);
		
		largeBtn = new JButton();
		largeBtn.setText("Large");
		largeBtn.setFocusable(false);
		largeBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		largeBtn.setFont(new Font("Serif",Font.BOLD,30));
		largeBtn.setForeground(Color.black);
		largeBtn.setBackground(Color.cyan);
		largeBtn.addActionListener(this);
		
		sizeBtnPanel.add(smallBtn);
		sizeBtnPanel.add(medBtn);
		sizeBtnPanel.add(largeBtn);
		
		sizePanel.add(sizeLabel,BorderLayout.NORTH);
		sizePanel.add(sizeBtnPanel,BorderLayout.SOUTH);
		
		
		/* overPanel */
		overPanel = new JPanel();
		overPanel.setBackground(Color.red);
		overPanel.setPreferredSize(new Dimension(FRAME_SIZE,350));
		overPanel.setLayout(new FlowLayout(FlowLayout.CENTER,45,75));
		
		restartBtn = new JButton();
		restartBtn.setText("Restart");
		restartBtn.setFocusable(false);
		restartBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		restartBtn.setFont(new Font("Serif",Font.BOLD,30));
		restartBtn.setForeground(Color.black);
		restartBtn.setBackground(Color.pink);
		restartBtn.addActionListener(this);
		
		mapSizeBtn = new JButton();
		mapSizeBtn.setText("Map Size");
		mapSizeBtn.setFocusable(false);
		mapSizeBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		mapSizeBtn.setFont(new Font("Serif",Font.BOLD,25));
		mapSizeBtn.setForeground(Color.black);
		mapSizeBtn.setBackground(Color.pink);
		mapSizeBtn.addActionListener(this);
		
		gameModeBtn = new JButton();
		gameModeBtn.setText("Game Mode");
		gameModeBtn.setFocusable(false);
		gameModeBtn.setPreferredSize(new Dimension(FRAME_SIZE/4,200));
		gameModeBtn.setFont(new Font("Serif",Font.BOLD,25));
		gameModeBtn.setForeground(Color.black);
		gameModeBtn.setBackground(Color.pink);
		gameModeBtn.addActionListener(this);
		
		overPanel.add(restartBtn);
		overPanel.add(gameModeBtn);
		overPanel.add(mapSizeBtn);

		
		/* Initialize frame with startPanel*/
		this.add(startPanel);
		
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();               /* will adjust frame size based on the panel */
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {

		final Object srcBtn = e.getSource();
		
		switch(curPanel) {
			case(0):{  /* startPanel */
				/* switch to modePanel */
				if(srcBtn == startBtn) {
					curPanel = 1; 
					this.remove(startPanel);
					this.add(modePanel);
				}
				break;
			}
			case(1):{  /* modePanel */
				
				if(srcBtn == normalBtn) {
					gameMode = 0;
				} 
				else if(srcBtn == wallBtn) {
					gameMode = 1;
				}
				else if (srcBtn == speedBtn){
					gameMode = 2;
				} 
				else {
					break;
				}
				
				/* switch to sizePanel */
				curPanel = 2;
				this.remove(modePanel);
				this.add(sizePanel);
				break;
			}
			case(2):{  /* sizePanel */
				if(srcBtn == smallBtn) {
					mapSize = SMALL;
				} 
				else if(srcBtn == medBtn) {
					mapSize = MEDIUM;
				}
				else if(srcBtn == largeBtn){
					mapSize = LARGE;
				} 
				else {  /* no button clicked */
					break;
				}
				
				/* switch to gamePanel */
				curPanel = 3;
				gamePanel = new GamePanel(mapSize,gameMode);
				this.remove(sizePanel);
				this.add(gamePanel);
				gamePanel.requestFocusInWindow();   /* use gp's keyAdapter */
				break;
			}
			case(3):{  /* gamePanel */
				if(!gamePanel.isEnabled()) {
					
					/* switch to overPanel */
					curPanel = 4;
					this.add(overPanel,BorderLayout.SOUTH);
				}
				break;
			}
			case(4):{  /* overPanel */
				if(srcBtn == restartBtn) {
					/* switch to gamePanel */
					curPanel = 3;
					this.remove(gamePanel);
					gamePanel = new GamePanel(mapSize,gameMode);
					this.remove(overPanel);
					this.add(gamePanel);
					gamePanel.requestFocusInWindow();
				}
				else if(srcBtn == gameModeBtn){
					/* switch to modePanel */
					curPanel = 1;
					this.remove(gamePanel);
					this.remove(overPanel);
					this.add(modePanel);
				} 
				else if(srcBtn == mapSizeBtn) {
					/* switch to sizePanel */
					curPanel = 2;
					this.remove(gamePanel);
					this.remove(overPanel);
					this.add(sizePanel);
				}
				else {  /* no button clicked */
					break;
				}
				break;
			}
		}
		
		this.revalidate();          /* Refreshes the frame */
		this.repaint();
	}
}