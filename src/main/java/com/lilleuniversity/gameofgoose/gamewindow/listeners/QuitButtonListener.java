package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Quitter" */
public class QuitButtonListener implements ActionListener {
    /* Une messagebox apparaît pour demander la confirmation à l'utilisateur, si il confirme, l'application se ferme */
	@Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the app ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if(choice == JOptionPane.YES_OPTION)
            System.exit(0);
    }
}