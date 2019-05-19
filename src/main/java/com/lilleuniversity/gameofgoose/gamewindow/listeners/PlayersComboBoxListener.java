package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lilleuniversity.gameofgoose.gamewindow.GameWindow;

/* Action qui s'applique lorsque l'utilisateur change la valeur de la combobox du nombre de joueurs (fenêtre de configuration) */
public class PlayersComboBoxListener implements ActionListener {
	private GameWindow gameWindow;
	
	public PlayersComboBoxListener(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        /* Selon le nombre sélectionné dans la combobox, on grise/dégrise les labels et textbox des noms des joueurs */
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