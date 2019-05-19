package com.lilleuniversity.gameofgoose.gamewindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/* La classe plateau permet de gérer les déplacements des pions sur le plateau
Un pion est représenté par un carré de la couleur du joueur, entouré par un encadré noir */
public class Board extends JLabel {
    private static final long serialVersionUID = 1L;
    private GameWindow gameWindow;

    public Board(ImageIcon img, GameWindow gameWindow) {
        super(img);
        this.gameWindow = gameWindow;
    }

    public void paintComponent(Graphics g) {
    	
        if(gameWindow.isGameStarted) {
            super.paintComponent(g);
            for(int i = 0; i < gameWindow.playersNumber; i++) {
                g.setColor(Color.BLACK);
                g.fillRect(gameWindow.xPiece[i], gameWindow.yPiece[i], 15, 15);
                g.setColor(gameWindow.playersColors[i]);
                g.fillRect(gameWindow.xPiece[i] + 2, gameWindow.yPiece[i] + 2, 11, 11);
            }
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(1050, 600);
    }
}