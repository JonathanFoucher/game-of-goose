package com.lilleuniversity.gameofgoose.gamewindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents the Board
 * @author Jonathan Foucher
 *
 */
public class Board extends JLabel {
	/**
	 * The serial version UID
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * The game window
     */
    private GameWindow gameWindow;

    /**
     * The constructor
     * @param img The board image
     * @param gameWindow The game window
     */
    public Board(ImageIcon img, GameWindow gameWindow) {
        super(img);
        this.gameWindow = gameWindow;
    }

    /**
     * The method that paints the board
     * @param g The Graphics to draw the board
     */
    public void paintComponent(Graphics g) {
        if(gameWindow.isGameStarted) {
            super.paintComponent(g);
            // paint the players pieces (represented by a square of their color in a black square)
            for(int i = 0; i < gameWindow.playersNumber; i++) {
                g.setColor(Color.BLACK);
                g.fillRect(gameWindow.xPiece[i], gameWindow.yPiece[i], 15, 15);
                g.setColor(gameWindow.playersColors[i]);
                g.fillRect(gameWindow.xPiece[i] + 2, gameWindow.yPiece[i] + 2, 11, 11);
            }
        }
    }

    /**
     * Get the board dimension
     * @param Returns the board dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(1050, 600);
    }
}