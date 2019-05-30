package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lilleuniversity.gameofgoose.gamewindow.GameWindow;

/**
 * Listener for play button
 * @author Jonathan Foucher
 *
 */
public class PlayButtonListener implements ActionListener {
	/**
	 * The game window
	 */
	private GameWindow gameWindow;
	
	/**
	 * The constructor
	 * @param gameWindow The game window
	 */
	public PlayButtonListener(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}
	
	/**
	 * Method called when the player clicks on the button, the next player turn is called
	 * @param e The ActionEvent
	 */
	@Override
    public void actionPerformed(ActionEvent e) {
		gameWindow.nextPlayer(true);
    }
}