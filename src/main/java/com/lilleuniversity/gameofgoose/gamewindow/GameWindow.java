package com.lilleuniversity.gameofgoose.gamewindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.lilleuniversity.gameofgoose.game.impl.Game;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.AppWindowFocusListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.LaunchButtonListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.PlayButtonListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.PlayersComboBoxListener;
import com.lilleuniversity.gameofgoose.gamewindow.listeners.QuitButtonListener;

/**
 * 
 * @author JonathanFoucher
 * Represents the game application window
 *
 */
public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	/*
	 * grille : JPanel repr�sentant le fond de la fen�tre
	 */
	public JPanel backgroundPanel = new JPanel();
	
	/*
		combo : la combobox qui permet de choisir le nombre de joueurs
		textBoxJoueurs : les textbox qui permettent d'entrer les noms des joueurs
		labelJoueurs : labels "Joueur 1" "Joueur 2"...
		btLancer : le bouton pour lancer la partie
		btQuitter : le bouton pour quitter l'application
	*/
	public JComboBox<Integer> playersComboBox = new JComboBox<>();
	public JTextField[] playersTextBoxes;
	public JLabel[] playersLabels;
	private JButton launchButton = new JButton("Launch"); 
	public JButton quitButton = new JButton("Quit"); 
	
	/* 
		nbJoueursMax : le nombre de joueurs maximum, ici fix� � 4 par choix
		couleurs : le tableau contenant les couleurs des joueurs
		nbJoueurs : le nombre de joueurs choisi par l'utilisateur (r�cup�r� avec la combobox)
	*/
	private static int maxPlayers = 4;
	public Color[] playersColors = new Color[maxPlayers];
	public int playersNumber;
	
	/* 
		maPartie : l'instance de la classe JeuOie li�e � l'interface
		plateauJeu : le panneau qui contient le plateau de jeu
		plateauLabel : ce label est utilis� pour charger l'image du plateau dans le panneau plateauJeu
		scores : tableau de panneaux pour former la bande sous le plateau de jeu (selecteur de joueur, score, messages, boutons)
		labelTour : le label qui indique le num�ro de tour actuel
		labelJou : un tableau de labels affichera les noms des joueurs et leurs scores
		btJouer : bouton qui permet de lancer le d�
		message : le message affich� (lorsqu'un joueur avance, passe son tour ...)
		msg : la cha�ne contenant le contenu du message � afficher
		actionN : le compteur d'actions r�alis�es, il permet de conna�tre par calcul quel joueur joue actuellement et � quel tour on se trouve
		jeu : bool�an qui indique si le jeu a �t� lanc� ou si on est encore en phase de configuration
		indicateurJoueur : le label contenant l'image du s�lecteur, le s�lecteur indique graphiquement quel joueur doit lancer le d� en se pla�ant devant son nom
	*/
	public Game game;
	public JPanel gameBoard = new JPanel();
	public Board gameBoardLabel;
	public JPanel[] scoresPanels = new JPanel[6];
	public JLabel turnLabel = new JLabel("Turn 1");
	public JLabel[] playersLabel;
	public JButton playButton = new JButton("");
	public JLabel messageLabel = new JLabel("<html><center>The game is starting<br>Click on the dice to make a roll</center></html>", JLabel.CENTER);
	private String message = new String("");
	private int actionCounter = 1;
	public boolean isGameStarted = false;
	public JLabel playerSelectorLabel;
	
	/*
		listener : objet qui permet "d'�couter" le timer pour savoir quand il est �coul�
		timer : timer qui permet de g�rer le d�placement d'un pion (on retrace le pion toutes les 5ms en d�calant de 2 pixels)
		xJoueur : tableau qui contient les abscisses o� se trouve les joueurs
		yJoueur : tableau qui contient les ordonn�es o� se trouve les joueurs
		xPion : tableau qui contient les abscisses o� se trouvent les pions
		yPion : tableau qui contient les ordonn�es o� se trouvent les pions
		On sait que l'on doit d�placer un pion tant que son abscisse et/ou son ordonn�e ne sont pas les m�mes que celles du joueur correspondant
		On peut donc imaginer xJoueur et yJoueur la position finale du mouvemant d'un pion
	*/
	private Timer timer;
	public int[] xPlayer;
	public int[] yPlayer;
	public int[] xPiece;
	public int[] yPiece;
	
	/* Constructeur de la classe Fenetre */
	public GameWindow() {
		/* Les param�tres de la fen�tre */
		setTitle("Game configuration");
		setSize(250, (110 + 55 * maxPlayers));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		/* Les param�tres du panneau de fond de la fen�tre */
		backgroundPanel.setBackground(Color.white);
		backgroundPanel.setLayout(new GridBagLayout());
		
		/* On d�finit les couleurs des quatre joueurs */
		playersColors[0] = new Color(0, 0, 255);
		playersColors[1] = new Color(255, 0, 0);
		playersColors[2] = new Color(0, 180, 0);
		playersColors[3] = new Color(130, 0, 250);
		
		/* On forme la combobox avec ses options */
		for(int i = 2; i < maxPlayers + 1; i++)
			playersComboBox.addItem(i);
		playersComboBox.setForeground(Color.blue);
		playersComboBox.setPreferredSize(new Dimension(50, 20));
		playersComboBox.addActionListener(new PlayersComboBoxListener(this));
		
		/* On cr�� les labels et textbox pour les noms des joueurs */
		playersTextBoxes = new JTextField[maxPlayers];
		playersLabels = new JLabel[maxPlayers];
		for(int i = 0; i < playersTextBoxes.length; i++) {
			playersLabels[i] = new JLabel("Player " + (i + 1) + " : ");
			playersTextBoxes[i] = new JTextField("");
			playersTextBoxes[i].setFont(new Font("Arial", Font.BOLD, 12));
			playersTextBoxes[i].setPreferredSize(new Dimension(80, 20));
			playersTextBoxes[i].setForeground(playersColors[i]);
			if(i > 1) {
				playersTextBoxes[i].setEnabled(false);
				playersLabels[i].setForeground(Color.LIGHT_GRAY);
			}
		}
		
		
		/* On place les diff�rents �l�ments dans le panneau de fond */
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		
		backgroundPanel.add(new JLabel("Number of players :"), gbc);
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		backgroundPanel.add(playersComboBox, gbc);
		
		gbc.gridx=0;
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		
		gbc.gridy+=1;
		gbc.gridwidth = 2;
		for(int i = 1; i < maxPlayers + 1; i++) {
			gbc.gridx = 0;
			backgroundPanel.add(playersLabels[i-1], gbc);
			gbc.gridx = 2;
			backgroundPanel.add(playersTextBoxes[i-1], gbc);
			gbc.gridy++;
			backgroundPanel.add(new JLabel(" "), gbc);
			gbc.gridy++;
		}
		
		gbc.gridx = 0;
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		gbc.gridy++;
		backgroundPanel.add(new JLabel(" "), gbc);
		gbc.gridy++;
		backgroundPanel.add(launchButton, gbc);
		gbc.gridx = 3;
		backgroundPanel.add(quitButton, gbc);
		
		
		/* On ajoute l'�coute des boutons et du focus de la fen�tre */
		launchButton.addActionListener(new LaunchButtonListener(this));
		playButton.addActionListener(new PlayButtonListener(this));
		quitButton.addActionListener(new QuitButtonListener());
		addWindowFocusListener(new AppWindowFocusListener(isGameStarted, gameBoardLabel));
		
		/* On appelle la m�thode creeTimer() pour cr�er le timer */
		createTimer();
		
		/* On ajoute le panneau de fond � la fen�tre et on rend la fen�tre visible */
		setContentPane(backgroundPanel);
		setVisible(true);
	}
	
	/* M�thode qui g�re le d�placement des pions (variation abscisse/ordonn�e avec un rappel sur le timer pour donner l'illusion d'un mouvement constant) */
	public void createTimer() {
		AbstractAction listener = new AbstractAction() {
            private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				timer.stop();
				int n = (actionCounter - 1)%playersNumber;
				/* Si la position du pion n'est pas �gale � la position du joueur, on d�place le pion vers la position joueur et on relance le timer
				   en boucle jusqu'� ce que la position soit la m�me */
				if(xPiece[n] < xPlayer[n]) {
					xPiece[n]+=2;
					gameBoardLabel.repaint();
					timer.start();
				} else if(yPiece[n] < yPlayer[n]) {
					yPiece[n]+=2;
					gameBoardLabel.repaint();
					timer.start();
				} else if(xPiece[n] > xPlayer[n]) {
					xPiece[n]-=2;
					gameBoardLabel.repaint();
					timer.start();
				} else if(yPiece[n] > yPlayer[n]) {
					yPiece[n]-=2;
					gameBoardLabel.repaint();
					timer.start();
				} else {
					if(!game.isGameEnded()) {
						actionCounter++;
						
						if(game.isPassTurn((actionCounter - 1)%playersNumber)) nextPlayer(false);
						else playButton.setEnabled(true);
					}
				}
            }
        };
		timer = new Timer(5, listener);
	}
	
	/* M�thode qui g�re le tour d'un joueur
	   Cette m�thode peut �tre appel�e par un appuie sur un joueur (param�tre � true) ou si un joueur doit passer son tour (param�tre � false) */
	public void nextPlayer(boolean nouvelleAction) {
		/* Si un joueur passe son tour on ne r�-initiale pas le message */
		if(nouvelleAction)
			message = "";
		
		int playerNumber = (actionCounter-1)%playersNumber;
		int m = (actionCounter)%playersNumber;
		int turn = ((actionCounter - actionCounter%playersNumber)/playersNumber + 1);
		
		playButton.setEnabled(false);
		game.getPlayerPosition(playerNumber);
		
		/* On appelle la m�thode lancerDe de la classe JeuOie pour faire jouer l'action du joueur */
		game.rollDice(playerNumber, turn);
		
		/* On modifie le score du joueur, l'affichage du num�ro de tour et le message affich� en bas de l'�cran */
		playersLabel[playerNumber].setText(playersTextBoxes[playerNumber].getText() + " : " + game.getPlayerPosition(playerNumber));
		
		message = message + "<br>" + game.getPlayerMessage(playerNumber);
		messageLabel.setText("<html><center>" + message + "</center></html>");
		messageLabel.revalidate();
		messageLabel.repaint();
		
		turnLabel.setText("Turn " + turn);
		
		/* On r�cup�re les nouvelles abscisse et ordonn�e du joueur apr�s avoir jou� */
		xPlayer[playerNumber] = game.getPlayerX(playerNumber);
		yPlayer[playerNumber] = game.getPlayerY(playerNumber);
		
		/* On lance le timer pour initier le d�placement, il s'appelera autant que n�cessaire jusqu'� l'arriv�e du pion � la position du joueur */
		timer.start();
		
		/* Si la partie n'est pas finie, on fait avancer le s�lecteur au joueur suivant pour indiquer que c'est � son tour de jouer */
		if(!game.isGameEnded()) {
			scoresPanels[4].removeAll();
			scoresPanels[4].add(Box.createRigidArea(new Dimension(20, (35 + m*21))));
			scoresPanels[4].add(playerSelectorLabel);
			scoresPanels[4].repaint();
		}
	}
}