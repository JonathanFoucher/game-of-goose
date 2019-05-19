package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lilleuniversity.gameofgoose.gamewindow.GameWindow;

/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Jouer" */
public class PlayButtonListener implements ActionListener {
	private GameWindow gameWindow;
	
	public PlayButtonListener(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		gameWindow.nextPlayer(true);
    }
}