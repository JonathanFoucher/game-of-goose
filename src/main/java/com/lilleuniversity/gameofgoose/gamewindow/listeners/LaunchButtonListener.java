package com.lilleuniversity.gameofgoose.gamewindow.listeners;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lilleuniversity.gameofgoose.game.impl.Game;
import com.lilleuniversity.gameofgoose.gamewindow.Board;
import com.lilleuniversity.gameofgoose.gamewindow.GameWindow;

/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Lancer" */
public class LaunchButtonListener implements ActionListener {
	private GameWindow gameWindow;
	
	public static final String DICE_URL = "images/dice.png";
	public static final String SELECTOR_URL = "images/selector.png";
	public static final String BOARD_URL = "images/board.png";
	
	public LaunchButtonListener(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        /* On commence par vérifier que des noms ont été entrés dans les textbox
           Si ce n'est pas le cas, on affiche un message d'erreur sinon on forme la fenêtre de jeu */
        boolean namesTest = true;
        for(int i = 0; i < (Integer)gameWindow.playersComboBox.getSelectedItem(); i++) {
            if(gameWindow.playersTextBoxes[i].getText().length() == 0) namesTest = false;
        }
        
        if(!namesTest) {
            JOptionPane.showMessageDialog(null, "Error, a player's name cannot be empty !", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int width = 1050;
            int height = 779;
            gameWindow.playersNumber = (Integer)gameWindow.playersComboBox.getSelectedItem();
            gameWindow.backgroundPanel.removeAll();
            gameWindow.setTitle("Game of goose");
            gameWindow.setSize(width, height);
            gameWindow.backgroundPanel.setLayout(new BoxLayout(gameWindow.backgroundPanel, BoxLayout.PAGE_AXIS));
            gameWindow.backgroundPanel.setPreferredSize(new Dimension(width, height));
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            gameWindow.setLocation(dimension.width/2 - gameWindow.getSize().width/2, dimension.height/2 - gameWindow.getSize().height/2);
            
            /* Paramètres du panneau contenant le plateau de jeu */
            gameWindow.gameBoard.setLayout(new GridBagLayout());
            gameWindow.gameBoard.setPreferredSize(new Dimension(width, 600));
            
            /* On définit les paramètres des panneaux de scores */
            for(int i = 0; i < gameWindow.scoresPanels.length; i++) {
            	gameWindow.scoresPanels[i] = new JPanel();
                if(i < 2) gameWindow.scoresPanels[i].setLayout(new FlowLayout());
                else if (i == 2) gameWindow.scoresPanels[i].setLayout(new BorderLayout());
                else gameWindow.scoresPanels[i].setLayout(new BoxLayout(gameWindow.scoresPanels[i], BoxLayout.PAGE_AXIS));
            }
            gameWindow.scoresPanels[0].setBackground(Color.blue);
            
            for(int i = 1; i < gameWindow.scoresPanels.length; i++)
            	gameWindow.scoresPanels[i].setBackground(Color.black);
            
            gameWindow.scoresPanels[0].setPreferredSize(new Dimension(1050, 150));
            gameWindow.scoresPanels[1].setPreferredSize(new Dimension(255, 145));
            gameWindow.scoresPanels[2].setPreferredSize(new Dimension(518, 145));
            gameWindow.scoresPanels[3].setPreferredSize(new Dimension(255, 145));
            gameWindow.scoresPanels[4].setPreferredSize(new Dimension(95, 135));
            gameWindow.scoresPanels[5].setPreferredSize(new Dimension(129, 135));
            
            /* On ajoute les différents panneaux */
            gameWindow.backgroundPanel.add(gameWindow.gameBoard);
            gameWindow.backgroundPanel.add(gameWindow.scoresPanels[0]);
            for(int i = 1; i < gameWindow.scoresPanels.length; i++)
            	gameWindow.scoresPanels[0].add(gameWindow.scoresPanels[i]);
            
            gameWindow.playersLabel = new JLabel[gameWindow.playersNumber];
            String[] playersNames = new String[gameWindow.playersNumber];
            gameWindow.scoresPanels[1].add(gameWindow.scoresPanels[4]);
            gameWindow.scoresPanels[1].add(gameWindow.scoresPanels[5]);
            
            /* On ajoute l'image du sélecteur */
            gameWindow.scoresPanels[4].add(Box.createRigidArea(new Dimension(20, 35)));
            try {
                Image img = ImageIO.read(getClass().getClassLoader().getResource(SELECTOR_URL));
                gameWindow.playerSelectorLabel = new JLabel(new ImageIcon(img));
                gameWindow.scoresPanels[4].add(gameWindow.playerSelectorLabel);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            /* On paramètre et ajoute les labels de score des joueurs (noms + scores) */
            for(int i = 0; i < gameWindow.playersNumber; i++) {
            	gameWindow.playersLabel[i] = new JLabel(gameWindow.playersTextBoxes[i].getText() + " : 0");
            	gameWindow.playersLabel[i].setForeground(gameWindow.playersColors[i]);
                playersNames[i] = gameWindow.playersTextBoxes[i].getText();
            }
            
            gameWindow.scoresPanels[5].add(Box.createRigidArea(new Dimension(0,10)));
            gameWindow.scoresPanels[5].add(gameWindow.turnLabel);
            gameWindow.scoresPanels[5].add(Box.createRigidArea(new Dimension(0,10)));
            for(int i = 0; i < gameWindow.playersNumber; i++) {
            	gameWindow.scoresPanels[5].add(gameWindow.playersLabel[i]);
            	gameWindow.scoresPanels[5].add(Box.createRigidArea(new Dimension(0,5)));
            }
            
            gameWindow.scoresPanels[2].add(gameWindow.messageLabel, BorderLayout.CENTER);
            
            /* On ajoute une image de dé sur le bouton "Jouer" */
            try {
                Image img = ImageIO.read(getClass().getClassLoader().getResource(DICE_URL));
                gameWindow.playButton.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            /* On configure et ajoute les boutons */
            gameWindow.playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameWindow.quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameWindow.playButton.setPreferredSize(new Dimension(80, 20));
            gameWindow.quitButton.setPreferredSize(new Dimension(80, 20));
            gameWindow.scoresPanels[3].add(Box.createRigidArea(new Dimension(0,30)));
            gameWindow.scoresPanels[3].add(gameWindow.playButton);
            gameWindow.scoresPanels[3].add(Box.createRigidArea(new Dimension(0,20)));
            gameWindow.scoresPanels[3].add(gameWindow.quitButton);
            
            /* On ajoute l'image du plateau */
            try {
                Image img = ImageIO.read(getClass().getClassLoader().getResource(BOARD_URL));
                gameWindow.gameBoardLabel = new Board(new ImageIcon(img), gameWindow);
                gameWindow.gameBoard.add(gameWindow.gameBoardLabel);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
            /* On définit les position initiales des pions et des joueurs */
            gameWindow.xPlayer = new int[4];
            gameWindow.yPlayer = new int[4];
            gameWindow.xPiece = new int[gameWindow.playersNumber];
            gameWindow.yPiece = new int[gameWindow.playersNumber];
            
            gameWindow.xPlayer[0] = 103; gameWindow.yPlayer[0] = 55;
            gameWindow.xPlayer[1] = 127; gameWindow.yPlayer[1] = 55;
            gameWindow.xPlayer[2] = 103; gameWindow.yPlayer[2] = 79;
            gameWindow.xPlayer[3] = 127; gameWindow.yPlayer[3] = 79;
            for(int i = 0; i < gameWindow.playersNumber; i++) {
            	gameWindow.xPiece[i] = gameWindow.xPlayer[i];
            	gameWindow.yPiece[i] = gameWindow.yPlayer[i];
            }
            
            /* On change les couleurs du texte des labels pour afficher le texte en blanc */
            gameWindow.turnLabel.setForeground(Color.WHITE);
            gameWindow.messageLabel.setForeground(Color.WHITE);
            
            /* On crée une instance de la classe JeuOie et on donne les paramètres choisis */
            gameWindow.game = new Game(gameWindow.playersNumber, playersNames, gameWindow.playersColors, gameWindow.xPlayer, gameWindow.yPlayer);
            
            /* On dessine les pions */
            gameWindow.gameBoardLabel.repaint();
        }
    }
}