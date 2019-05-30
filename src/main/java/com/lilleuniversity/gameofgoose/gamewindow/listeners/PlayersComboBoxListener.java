package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lilleuniversity.gameofgoose.gamewindow.GameWindow;

/**
 * Listener for the combobox allowing to choose the number of players
 * @author Jonathan Foucher
 *
 */
public class PlayersComboBoxListener implements ActionListener {
	/**
	 * The game window
	 */
	private GameWindow gameWindow;
	
	/**
	 * The constructor
	 * @param gameWindow The game window
	 */
	public PlayersComboBoxListener(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}

	/**
	 * Mehtod called when the player chooses an element in the combobox
	 * @param e The ActionEvent
	 */
	@Override
    public void actionPerformed(ActionEvent e) {
		// in function on the number of players chosen, the player names textbox will be greyed or ungreyed
        for(int i = 2; i < (Integer)gameWindow.playersComboBox.getSelectedItem(); i++) {
        	gameWindow.playersTextBoxes[i].setEnabled(true);
        	gameWindow.playersLabels[i].setForeground(Color.BLACK);
        }

        for(int i = (Integer)gameWindow.playersComboBox.getSelectedItem(); i < gameWindow.playersTextBoxes.length; i++) {
        	gameWindow.playersTextBoxes[i].setEnabled(false);
        	gameWindow.playersLabels[i].setForeground(Color.LIGHT_GRAY);
        }
    }
}