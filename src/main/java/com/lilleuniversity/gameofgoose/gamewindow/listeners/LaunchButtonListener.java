package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lilleuniversity.gameofgoose.game.impl.Game;
import com.lilleuniversity.gameofgoose.gamewindow.Board;
import com.lilleuniversity.gameofgoose.gamewindow.GameWindow;

/**
 * Listener for the game launcher button
 * @author Jonathan Foucher
 *
 */
public class LaunchButtonListener implements ActionListener {
	/**
	 * The game window
	 */
	private GameWindow gameWindow;
	
	/**
	 * The dice image path
	 */
	public static final String DICE_PATH = "images/dice.png";
	
	/**
	 * The selector image path
	 */
	public static final String SELECTOR_PATH = "images/selector.png";
	
	/**
	 * The board image path
	 */
	public static final String BOARD_PATH = "images/board.png";
	
	/**
	 * The constructor
	 * @param gameWindow The game window
	 */
	public LaunchButtonListener(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}

	/**
	 * Method called when the player clicks on the game launcher button
	 * @param e The ActionEvent
	 */
	@Override
    public void actionPerformed(ActionEvent e) {
        // verify that the names have been typed
        boolean isNamesOk = true;
        for(int i = 0; i < (Integer)gameWindow.playersComboBox.getSelectedItem(); i++) {
            if(gameWindow.playersTextBoxes[i].getText().length() == 0) isNamesOk = false;
        }
        
        // if everything is good, the game start, otherwise an error message shows up
        if(!isNamesOk) {
            JOptionPane.showMessageDialog(null, "Error, a player's name cannot be empty !", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
        	gameWindow.isGameStarted = true;
            int width = 1050;
            int height = 779;
            gameWindow.playersNumber = (Integer)gameWindow.playersComboBox.getSelectedItem();
            gameWindow.backgroundPanel.removeAll();
            gameWindow.setTitle("Game of goose");
            gameWindow.setSize(width, height);
            gameWindow.backgroundPanel.setLayout(new BoxLayout(gameWindow.backgroundPanel, BoxLayout.PAGE_AXIS));
            gameWindow.backgroundPanel.setPreferredSize(new Dimension(width, height));
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            gameWindow.setLocation(dimension.width/2 - gameWindow.getSize().width/2, dimension.height/2 - gameWindow.getSize().height/2);
            
            // board panel
            gameWindow.gameBoardPanel.setLayout(new GridBagLayout());
            gameWindow.gameBoardPanel.setPreferredSize(new Dimension(width, 600));
            
            // scores panels
            for(int i = 0; i < gameWindow.scoresPanels.length; i++) {
            	gameWindow.scoresPanels[i] = new JPanel();
                if(i < 2) gameWindow.scoresPanels[i].setLayout(new FlowLayout());
                else if (i == 2) gameWindow.scoresPanels[i].setLayout(new BorderLayout());
                else gameWindow.scoresPanels[i].setLayout(new BoxLayout(gameWindow.scoresPanels[i], BoxLayout.PAGE_AXIS));
            }
            gameWindow.scoresPanels[0].setBackground(Color.blue);
            
            for(int i = 1; i < gameWindow.scoresPanels.length; i++)
            	gameWindow.scoresPanels[i].setBackground(Color.black);
            
            gameWindow.scoresPanels[0].setPreferredSize(new Dimension(1050, 150));
            gameWindow.scoresPanels[1].setPreferredSize(new Dimension(255, 145));
            gameWindow.scoresPanels[2].setPreferredSize(new Dimension(518, 145));
            gameWindow.scoresPanels[3].setPreferredSize(new Dimension(255, 145));
            gameWindow.scoresPanels[4].setPreferredSize(new Dimension(95, 135));
            gameWindow.scoresPanels[5].setPreferredSize(new Dimension(129, 135));
            
            // add the panels
            gameWindow.backgroundPanel.add(gameWindow.gameBoardPanel);
            gameWindow.backgroundPanel.add(gameWindow.scoresPanels[0]);
            for(int i = 1; i < gameWindow.scoresPanels.length; i++)
            	gameWindow.scoresPanels[0].add(gameWindow.scoresPanels[i]);
            
            gameWindow.playersLabel = new JLabel[gameWindow.playersNumber];
            String[] playersNames = new String[gameWindow.playersNumber];
            gameWindow.scoresPanels[1].add(gameWindow.scoresPanels[4]);
            gameWindow.scoresPanels[1].add(gameWindow.scoresPanels[5]);
            
            // selector image
            gameWindow.scoresPanels[4].add(Box.createRigidArea(new Dimension(20, 35)));
            try {
                Image img = ImageIO.read(getClass().getClassLoader().getResource(SELECTOR_PATH));
                gameWindow.playerSelectorLabel = new JLabel(new ImageIcon(img));
                gameWindow.scoresPanels[4].add(gameWindow.playerSelectorLabel);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            // labels
            for(int i = 0; i < gameWindow.playersNumber; i++) {
            	gameWindow.playersLabel[i] = new JLabel(gameWindow.playersTextBoxes[i].getText() + " : 0");
            	gameWindow.playersLabel[i].setForeground(gameWindow.playersColors[i]);
                playersNames[i] = gameWindow.playersTextBoxes[i].getText();
            }
            
            gameWindow.scoresPanels[5].add(Box.createRigidArea(new Dimension(0,10)));
            gameWindow.scoresPanels[5].add(gameWindow.turnLabel);
            gameWindow.scoresPanels[5].add(Box.createRigidArea(new Dimension(0,10)));
            for(int i = 0; i < gameWindow.playersNumber; i++) {
            	gameWindow.scoresPanels[5].add(gameWindow.playersLabel[i]);
            	gameWindow.scoresPanels[5].add(Box.createRigidArea(new Dimension(0,5)));
            }
            
            gameWindow.scoresPanels[2].add(gameWindow.messageLabel, BorderLayout.CENTER);
            
            // dice image
            try {
                Image img = ImageIO.read(getClass().getClassLoader().getResource(DICE_PATH));
                gameWindow.playButton.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            // buttons
            gameWindow.playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameWindow.quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameWindow.playButton.setPreferredSize(new Dimension(80, 20));
            gameWindow.quitButton.setPreferredSize(new Dimension(80, 20));
            gameWindow.scoresPanels[3].add(Box.createRigidArea(new Dimension(0,30)));
            gameWindow.scoresPanels[3].add(gameWindow.playButton);
            gameWindow.scoresPanels[3].add(Box.createRigidArea(new Dimension(0,20)));
            gameWindow.scoresPanels[3].add(gameWindow.quitButton);
            
            // image of the board
            try {
                Image img = ImageIO.read(getClass().getClassLoader().getResource(BOARD_PATH));
                gameWindow.gameBoardLabel = new Board(new ImageIcon(img), gameWindow);
                gameWindow.gameBoardPanel.add(gameWindow.gameBoardLabel);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            // initial positions of the players pieces
            gameWindow.xPlayer = new int[4];
            gameWindow.yPlayer = new int[4];
            gameWindow.xPiece = new int[gameWindow.playersNumber];
            gameWindow.yPiece = new int[gameWindow.playersNumber];
            
            gameWindow.xPlayer[0] = 103; gameWindow.yPlayer[0] = 55;
            gameWindow.xPlayer[1] = 127; gameWindow.yPlayer[1] = 55;
            gameWindow.xPlayer[2] = 103; gameWindow.yPlayer[2] = 79;
            gameWindow.xPlayer[3] = 127; gameWindow.yPlayer[3] = 79;
            for(int i = 0; i < gameWindow.playersNumber; i++) {
            	gameWindow.xPiece[i] = gameWindow.xPlayer[i];
            	gameWindow.yPiece[i] = gameWindow.yPlayer[i];
            }
            
            // text in white
            gameWindow.turnLabel.setForeground(Color.WHITE);
            gameWindow.messageLabel.setForeground(Color.WHITE);
            
            // creating the game
            gameWindow.game = new Game(gameWindow.playersNumber, playersNames, gameWindow.playersColors, gameWindow.xPlayer, gameWindow.yPlayer);
            
            // draw the pieces
            gameWindow.gameBoardLabel.repaint();
        }
    }
}