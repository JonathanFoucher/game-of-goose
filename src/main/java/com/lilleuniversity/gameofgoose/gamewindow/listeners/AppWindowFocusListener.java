package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import com.lilleuniversity.gameofgoose.gamewindow.Board;

/* On retrace les pions lorsque la fenêtre reprend le focus (pour corriger un bug où les pions disparaissent lorsque la fenêtre perd le focus) */
public class AppWindowFocusListener implements WindowFocusListener {
	private boolean isGameStarted;
	private Board gameBoardLabel;
	
	public AppWindowFocusListener(boolean isGameStarted, Board gameBoardLabel) {
		this.isGameStarted = isGameStarted;
		this.gameBoardLabel = gameBoardLabel;
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
        if(isGameStarted) gameBoardLabel.repaint();
    }

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}