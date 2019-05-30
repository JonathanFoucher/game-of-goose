package com.lilleuniversity.gameofgoose.gamewindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.lilleuniversity.gameofgoose.game.impl.Game;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.AppWindowFocusListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.LaunchButtonListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.PlayButtonListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.PlayersComboBoxListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.QuitButtonListener;

/**
 * Represents the game application window
 * @author JonathanFoucher
 *
 */
public class GameWindow extends JFrame {
	/**
	 * The serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The main panel
	 */
	public JPanel backgroundPanel = new JPanel();
	
	/**
	 * The combobox to indicates the number of players
	 */
	public JComboBox<Integer> playersComboBox = new JComboBox<>();
	
	/**
	 * The players textboxes
	 */
	public JTextField[] playersTextBoxes;
	
	/**
	 * The players labels
	 */
	public JLabel[] playersLabels;
	
	/**
	 * The button to start the game
	 */
	private JButton launchButton = new JButton("Launch"); 
	
	/**
	 * The quit button
	 */
	public JButton quitButton = new JButton("Quit"); 

	/**
	 * The maximum of players allowed
	 */
	private static int maxPlayers = 4;
	
	/**
	 * The array containing the players' colors
	 */
	public Color[] playersColors = new Color[maxPlayers];
	
	/**
	 * The number of players playing
	 */
	public int playersNumber;
	
	/**
	 * The game
	 */
	public Game game;
	
	/**
	 * The board
	 */
	public Board gameBoard;
	
	/**
	 * The scores panels
	 */
	public JPanel[] scoresPanels = new JPanel[6];
	
	/**
	 * The turn panel
	 */
	public JLabel turnLabel = new JLabel("Turn 1");
	
	/**
	 * The players labels
	 */
	public JLabel[] playersLabel;
	
	/**
	 * The play button, it allows the player to rolls the dices
	 */
	public JButton playButton = new JButton("");
	
	/**
	 * The turn message label
	 */
	public JLabel messageLabel = new JLabel("<html><center>The game is starting<br>Click on the dice to make a roll</center></html>", JLabel.CENTER);
	
	/**
	 * The turn message
	 */
	private String message = new String("");
	
	/**
	 * The action counter
	 */
	private int actionCounter = 1;
	
	/**
	 * The boolean that indicates if the game has started
	 */
	public boolean isGameStarted = false;
	
	/**
	 * The player selector, it indicates whose turn it is
	 */
	public JLabel playerSelectorLabel;
	
	/**
	 * The timer for the pieces motions
	 */
	private Timer timer;
	
	/**
	 * The players x positions
	 */
	public int[] xPlayer;
	
	/**
	 * The players y positions
	 */
	public int[] yPlayer;
	
	/**
	 * The players pieces x positions
	 */
	public int[] xPiece;
	
	/**
	 * The players pieces y positions
	 */
	public int[] yPiece;
	
	/**
	 * The constructor
	 */
	public GameWindow() {
		// window settings
		setTitle("Game configuration");
		setSize(250, (110 + 55 * maxPlayers));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		// main panel settings
		backgroundPanel.setBackground(Color.white);
		backgroundPanel.setLayout(new GridBagLayout());
		
		// players' colors
		playersColors[0] = new Color(0, 0, 255);
		playersColors[1] = new Color(255, 0, 0);
		playersColors[2] = new Color(0, 180, 0);
		playersColors[3] = new Color(130, 0, 250);
		
		// combobox for the number of players
		for(int i = 2; i < maxPlayers + 1; i++)
			playersComboBox.addItem(i);
		playersComboBox.setForeground(Color.blue);
		playersComboBox.setPreferredSize(new Dimension(50, 20));
		playersComboBox.addActionListener(new PlayersComboBoxListener(this));
		
		// labels and textboxes for the players' names
		playersTextBoxes = new JTextField[maxPlayers];
		playersLabels = new JLabel[maxPlayers];
		for(int i = 0; i < playersTextBoxes.length; i++) {
			playersLabels[i] = new JLabel("Player " + (i + 1) + " : ");
			playersTextBoxes[i] = new JTextField("");
			playersTextBoxes[i].setFont(new Font("Arial", Font.BOLD, 12));
			playersTextBoxes[i].setPreferredSize(new Dimension(80, 20));
			playersTextBoxes[i].setForeground(playersColors[i]);
			if(i > 1) {
				playersTextBoxes[i].setEnabled(false);
				playersLabels[i].setForeground(Color.LIGHT_GRAY);
			}
		}
		
		
		// adding the different elements on the main panel
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		
		backgroundPanel.add(new JLabel("Number of players :"), gbc);
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		backgroundPanel.add(playersComboBox, gbc);
		
		gbc.gridx=0;
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		
		gbc.gridy+=1;
		gbc.gridwidth = 2;
		for(int i = 1; i < maxPlayers + 1; i++) {
			gbc.gridx = 0;
			backgroundPanel.add(playersLabels[i-1], gbc);
			gbc.gridx = 2;
			backgroundPanel.add(playersTextBoxes[i-1], gbc);
			gbc.gridy++;
			backgroundPanel.add(new JLabel(" "), gbc);
			gbc.gridy++;
		}
		
		gbc.gridx = 0;
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		gbc.gridy++;
		backgroundPanel.add(launchButton, gbc);
		gbc.gridx = 3;
		backgroundPanel.add(quitButton, gbc);
		
		
		// listeners
		launchButton.addActionListener(new LaunchButtonListener(this));
		playButton.addActionListener(new PlayButtonListener(this));
		quitButton.addActionListener(new QuitButtonListener());
		addWindowFocusListener(new AppWindowFocusListener(isGameStarted, gameBoard));
		
		// create the timer
		createTimer();
		
		// setting the main panel
		setContentPane(backgroundPanel);
		setVisible(true);
	}
	
	/**
	 * Create and manage the timer for the pieces motions
	 */
	public void createTimer() {
		/**
		 * The timer listener
		 */
		AbstractAction listener = new AbstractAction() {
			/**
			 * The serial version UID
			 */
            private static final long serialVersionUID = 1L;

            /**
             * Method called at the end of the timer
             * @param e The ActionEvent
             */
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				int n = (actionCounter - 1)%playersNumber;
				// if the piece position is different from the player position, the piece makes a step to the players position and call the timer
				if(xPiece[n] < xPlayer[n]) {
					xPiece[n]+=2;
					gameBoard.repaint();
					timer.start();
				} else if(yPiece[n] < yPlayer[n]) {
					yPiece[n]+=2;
					gameBoard.repaint();
					timer.start();
				} else if(xPiece[n] > xPlayer[n]) {
					xPiece[n]-=2;
					gameBoard.repaint();
					timer.start();
				} else if(yPiece[n] > yPlayer[n]) {
					yPiece[n]-=2;
					gameBoard.repaint();
					timer.start();
				} else {
					if(!game.isGameEnded()) {
						actionCounter++;
						
						if(game.isPassTurn((actionCounter - 1)%playersNumber)) nextPlayer(false);
						else playButton.setEnabled(true);
					}
				}
            }
        };
		timer = new Timer(5, listener);
	}
	
	/**
	 * Play the next player turn
	 * @param hasPlayed The boolean that indicates if the player played (true) or passed his/her turn (false)
	 */
	public void nextPlayer(boolean hasPlayed) {
		// if the player didn't pass his/her turn, we erase the message
		if(hasPlayed)
			message = "";
		
		int playerNumber = (actionCounter-1)%playersNumber;
		int m = (actionCounter)%playersNumber;
		int turn = ((actionCounter - actionCounter%playersNumber)/playersNumber + 1);
		
		playButton.setEnabled(false);
		game.getPlayerPosition(playerNumber);
		
		// the player plays
		game.play(playerNumber, turn);
		
		// update the score, the turn number and the message
		playersLabel[playerNumber].setText(playersTextBoxes[playerNumber].getText() + " : " + game.getPlayerPosition(playerNumber));
		
		message = message + "<br>" + game.getPlayerMessage(playerNumber);
		messageLabel.setText("<html><center>" + message + "</center></html>");
		messageLabel.revalidate();
		messageLabel.repaint();
		
		turnLabel.setText("Turn " + turn);
		
		// get the new player position
		xPlayer[playerNumber] = game.getPlayerX(playerNumber);
		yPlayer[playerNumber] = game.getPlayerY(playerNumber);
		
		// call the timer to move the piece
		timer.start();
		
		// if the game is not ended, update the selector position to indicate whose turn it is
		if(!game.isGameEnded()) {
			scoresPanels[4].removeAll();
			scoresPanels[4].add(Box.createRigidArea(new Dimension(20, (35 + m*21))));
			scoresPanels[4].add(playerSelectorLabel);
			scoresPanels[4].repaint();
		}
	}
}