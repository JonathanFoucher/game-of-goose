package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import com.lilleuniversity.gameofgoose.gamewindow.Board;

/**
 * Listener for the windows focus
 * @author Jonathan Foucher
 *
 */
public class AppWindowFocusListener implements WindowFocusListener {
	/**
	 * The boolean that indicates if the game has started
	 */
	private boolean isGameStarted;
	
	/**
	 * The game board
	 */
	private Board gameBoard;
	
	/**
	 * The constructor
	 * @param The boolean that indicates if the game has started
	 * @param gameBoard The game board
	 */
	public AppWindowFocusListener(boolean isGameStarted, Board gameBoard) {
		this.isGameStarted = isGameStarted;
		this.gameBoard = gameBoard;
	}

	/**
	 * Method called when the window gains the focus, we repaint the board
	 * @param e The WindowEvent
	 */
	@Override
	public void windowGainedFocus(WindowEvent e) {
        if(isGameStarted) gameBoard.repaint();
    }

	/**
	 * Method called when the window gains the focus, nothing is done
	 * @param e The WindowEvent
	 */
	@Override
	public void windowLostFocus(WindowEvent e) {}
}