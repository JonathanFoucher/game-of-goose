package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Listener for the quit button
 * @author Jonathan Foucher
 *
 */
public class QuitButtonListener implements ActionListener {
	
    /**
     * Method called when the player try to quit the application, a pop-up message asks a confirmation from the player
     * @param e The ActionEvent
     */
	@Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the app ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(choice == JOptionPane.YES_OPTION)
            System.exit(0);
    }
}