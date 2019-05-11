package com.lilleuniversity.gameofgoose;

import com.lilleuniversity.gameofgoose.game.impl.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * 
 * @author JonathanFoucher
 * Represents the game application window
 *
 */
public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	/*
	 * grille : JPanel représentant le fond de la fenêtre
	 */
	private JPanel backgroundPanel = new JPanel();
	
	/*
		combo : la combobox qui permet de choisir le nombre de joueurs
		textBoxJoueurs : les textbox qui permettent d'entrer les noms des joueurs
		labelJoueurs : labels "Joueur 1" "Joueur 2"...
		btLancer : le bouton pour lancer la partie
		btQuitter : le bouton pour quitter l'application
	*/
	private JComboBox<Integer> playersComboBox = new JComboBox<>();
	private JTextField[] playersTextBoxes;
	private JLabel[] playersLabels;
	private JButton launchButton = new JButton("Launch"); 
	private JButton quitButton = new JButton("Quit"); 
	
	/* 
		nbJoueursMax : le nombre de joueurs maximum, ici fixé à 4 par choix
		couleurs : le tableau contenant les couleurs des joueurs
		nbJoueurs : le nombre de joueurs choisi par l'utilisateur (récupéré avec la combobox)
	*/
	private static int maxPlayers = 4;
	private Color[] playersColors = new Color[maxPlayers];
	private int playersNumber;
	
	/* 
		maPartie : l'instance de la classe JeuOie liée à l'interface
		plateauJeu : le panneau qui contient le plateau de jeu
		plateauLabel : ce label est utilisé pour charger l'image du plateau dans le panneau plateauJeu
		scores : tableau de panneaux pour former la bande sous le plateau de jeu (selecteur de joueur, score, messages, boutons)
		labelTour : le label qui indique le numéro de tour actuel
		labelJou : un tableau de labels affichera les noms des joueurs et leurs scores
		btJouer : bouton qui permet de lancer le dé
		message : le message affiché (lorsqu'un joueur avance, passe son tour ...)
		msg : la chaîne contenant le contenu du message à afficher
		actionN : le compteur d'actions réalisées, il permet de connaître par calcul quel joueur joue actuellement et à quel tour on se trouve
		jeu : booléan qui indique si le jeu a été lancé ou si on est encore en phase de configuration
		indicateurJoueur : le label contenant l'image du sélecteur, le sélecteur indique graphiquement quel joueur doit lancer le dé en se plaçant devant son nom
	*/
	private Game game;
	private JPanel gameBoard = new JPanel();
	private Board gameBoardLabel;
	private JPanel[] scoresPanels = new JPanel[6];
	private JLabel turnLabel = new JLabel("Turn 1");
	private JLabel[] playersLabel;
	private JButton playButton = new JButton("");
	private JLabel messageLabel = new JLabel("<html><center>The game is starting<br>Click on the dice to make a roll</center></html>", JLabel.CENTER);
	private String message = new String("");
	private int actionCounter = 1;
	private boolean isGameStarted = false;
	private JLabel playerSelectorLabel;
	
	/*
		listener : objet qui permet "d'écouter" le timer pour savoir quand il est écoulé
		timer : timer qui permet de gérer le déplacement d'un pion (on retrace le pion toutes les 5ms en décalant de 2 pixels)
		xJoueur : tableau qui contient les abscisses où se trouve les joueurs
		yJoueur : tableau qui contient les ordonnées où se trouve les joueurs
		xPion : tableau qui contient les abscisses où se trouvent les pions
		yPion : tableau qui contient les ordonnées où se trouvent les pions
		On sait que l'on doit déplacer un pion tant que son abscisse et/ou son ordonnée ne sont pas les mêmes que celles du joueur correspondant
		On peut donc imaginer xJoueur et yJoueur la position finale du mouvemant d'un pion
	*/
	private Timer timer;
	private int[] xPlayer;
	private int[] yPlayer;
	private int[] xPiece;
	private int[] yPiece;
	
	/* Constructeur de la classe Fenetre */
	public GameWindow() {
		/* Les paramètres de la fenêtre */
		setTitle("Game configuration");
		setSize(250, (110 + 55 * maxPlayers));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		/* Les paramètres du panneau de fond de la fenêtre */
		backgroundPanel.setBackground(Color.white);
		backgroundPanel.setLayout(new GridBagLayout());
		
		/* On définit les couleurs des quatre joueurs */
		playersColors[0] = new Color(0, 0, 255);
		playersColors[1] = new Color(255, 0, 0);
		playersColors[2] = new Color(0, 180, 0);
		playersColors[3] = new Color(130, 0, 250);
		
		/* On forme la combobox avec ses options */
		for(int i = 2; i < maxPlayers + 1; i++)
			playersComboBox.addItem(i);
		playersComboBox.setForeground(Color.blue);
		playersComboBox.setPreferredSize(new Dimension(50, 20));
		playersComboBox.addActionListener(new PlayersComboBoxListener());
		
		/* On créé les labels et textbox pour les noms des joueurs */
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
		
		
		/* On place les différents éléments dans le panneau de fond */
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
		
		
		/* On ajoute l'écoute des boutons et du focus de la fenêtre */
		launchButton.addActionListener(new LaunchButtonListener());
		playButton.addActionListener(new PlayButtonListener());
		quitButton.addActionListener(new QuitButtonListener());
		addWindowFocusListener(new AppWindowFocusListener());
		
		/* On appelle la méthode creeTimer() pour créer le timer */
		createTimer();
		
		/* On ajoute le panneau de fond à la fenêtre et on rend la fenêtre visible */
		setContentPane(backgroundPanel);
		setVisible(true);
	}
	
	/* Méthode qui gère le déplacement des pions (variation abscisse/ordonnée avec un rappel sur le timer pour donner l'illusion d'un mouvement constant) */
	public void createTimer() {
		AbstractAction listener = new AbstractAction() {
            private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				timer.stop();
				int n = (actionCounter - 1)%playersNumber;
				/* Si la position du pion n'est pas égale à la position du joueur, on déplace le pion vers la position joueur et on relance le timer
				   en boucle jusqu'à ce que la position soit la même */
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
	
	/* Action qui s'applique lorsque l'utilisateur change la valeur de la combobox du nombre de joueurs (fenêtre de configuration) */
	class PlayersComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/* Selon le nombre sélectionné dans la combobox, on grise/dégrise les labels et textbox des noms des joueurs */
			for(int i = 2; i < (Integer)playersComboBox.getSelectedItem(); i++) {
				playersTextBoxes[i].setEnabled(true);
				playersLabels[i].setForeground(Color.BLACK);
			}

			for(int i = (Integer)playersComboBox.getSelectedItem(); i < playersTextBoxes.length; i++) {
				playersTextBoxes[i].setEnabled(false);
				playersLabels[i].setForeground(Color.LIGHT_GRAY);
			}
		}
	}
	
	/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Lancer" */
	class LaunchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/* On commence par vérifier que des noms ont été entrés dans les textbox
			   Si ce n'est pas le cas, on affiche un message d'erreur sinon on forme la fenêtre de jeu */
			boolean namesTest = true;
			for(int i = 0; i < (Integer)playersComboBox.getSelectedItem(); i++) {
				if(playersTextBoxes[i].getText().length() == 0) namesTest = false;
			}
			
			if(!namesTest) {
				JOptionPane.showMessageDialog(null, "Error, a player's name cannot be empty !", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				/* Paramètres de la fenêtre et du panneau de fond */
				isGameStarted = true;
				int width = 1050;
				int height = 779;
				playersNumber = (Integer)playersComboBox.getSelectedItem();
				backgroundPanel.removeAll();
				setTitle("Game of goose");
				setSize(width, height);
				backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.PAGE_AXIS));
				backgroundPanel.setPreferredSize(new Dimension(width, height));
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				setLocation(dimension.width/2 - getSize().width/2, dimension.height/2 - getSize().height/2);
				
				/* Paramètres du panneau contenant le plateau de jeu */
				gameBoard.setLayout(new GridBagLayout());
				gameBoard.setPreferredSize(new Dimension(width, 600));
				
				/* On définit les paramètres des panneaux de scores */
				for(int i = 0; i < scoresPanels.length; i++) {
					scoresPanels[i] = new JPanel();
					if(i < 2) scoresPanels[i].setLayout(new FlowLayout());
					else if (i == 2) scoresPanels[i].setLayout(new BorderLayout());
					else scoresPanels[i].setLayout(new BoxLayout(scoresPanels[i], BoxLayout.PAGE_AXIS));
				}
				scoresPanels[0].setBackground(Color.blue);
				
				for(int i = 1; i < scoresPanels.length; i++)
					scoresPanels[i].setBackground(Color.black);
				
				scoresPanels[0].setPreferredSize(new Dimension(1050, 150));
				scoresPanels[1].setPreferredSize(new Dimension(255, 145));
				scoresPanels[2].setPreferredSize(new Dimension(518, 145));
				scoresPanels[3].setPreferredSize(new Dimension(255, 145));
				scoresPanels[4].setPreferredSize(new Dimension(95, 135));
				scoresPanels[5].setPreferredSize(new Dimension(129, 135));
				
				/* On ajoute les différents panneaux */
				backgroundPanel.add(gameBoard);
				backgroundPanel.add(scoresPanels[0]);
				for(int i = 1; i < scoresPanels.length; i++)
					scoresPanels[0].add(scoresPanels[i]);
				
				playersLabel = new JLabel[playersNumber];
				String[] playersNames = new String[playersNumber];
				scoresPanels[1].add(scoresPanels[4]);
				scoresPanels[1].add(scoresPanels[5]);
				
				/* On ajoute l'image du sélecteur */
				scoresPanels[4].add(Box.createRigidArea(new Dimension(20, 35)));
				try {
					Image img = ImageIO.read(getClass().getClassLoader().getResource("images/selecteur.png"));
					playerSelectorLabel = new JLabel(new ImageIcon(img));
					scoresPanels[4].add(playerSelectorLabel);
				} catch (Exception ex) {
					System.out.println(ex);
				}
				
				/* On paramètre et ajoute les labels de score des joueurs (noms + scores) */
				for(int i = 0; i < playersNumber; i++) {
					playersLabel[i] = new JLabel(playersTextBoxes[i].getText() + " : 0");
					playersLabel[i].setForeground(playersColors[i]);
					playersNames[i] = playersTextBoxes[i].getText();
				}
				
				scoresPanels[5].add(Box.createRigidArea(new Dimension(0,10)));
				scoresPanels[5].add(turnLabel);
				scoresPanels[5].add(Box.createRigidArea(new Dimension(0,10)));
				for(int i = 0; i < playersNumber; i++) {
					scoresPanels[5].add(playersLabel[i]);
					scoresPanels[5].add(Box.createRigidArea(new Dimension(0,5)));
				}
				
				scoresPanels[2].add(messageLabel, BorderLayout.CENTER);
				
				/* On ajoute une image de dé sur le bouton "Jouer" */
				try {
					Image img = ImageIO.read(getClass().getResource("images/dice.png"));
					playButton.setIcon(new ImageIcon(img));
				} catch (Exception ex) {
					System.out.println(ex);
				}
				
				/* On configure et ajoute les boutons */
				playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				playButton.setPreferredSize(new Dimension(80, 20));
				quitButton.setPreferredSize(new Dimension(80, 20));
				scoresPanels[3].add(Box.createRigidArea(new Dimension(0,30)));
				scoresPanels[3].add(playButton);
				scoresPanels[3].add(Box.createRigidArea(new Dimension(0,20)));
				scoresPanels[3].add(quitButton);
				
				/* On ajoute l'image du plateau */
				try {
					Image img = ImageIO.read(getClass().getResource("images/plateau.png"));
					gameBoardLabel = new Board(new ImageIcon(img));
					gameBoard.add(gameBoardLabel);
				} catch (Exception ex) {
					System.out.println(ex);
				}
				
				/* On définit les position initiales des pions et des joueurs */
				xPlayer = new int[4];
				yPlayer = new int[4];
				xPiece = new int[playersNumber];
				yPiece = new int[playersNumber];
				
				xPlayer[0] = 103; yPlayer[0] = 55;
				xPlayer[1] = 127; yPlayer[1] = 55;
				xPlayer[2] = 103; yPlayer[2] = 79;
				xPlayer[3] = 127; yPlayer[3] = 79;
				for(int i = 0; i < playersNumber; i++) {
					xPiece[i] = xPlayer[i];
					yPiece[i] = yPlayer[i];
				}
				
				/* On change les couleurs du texte des labels pour afficher le texte en blanc */
				turnLabel.setForeground(Color.WHITE);
				messageLabel.setForeground(Color.WHITE);
				
				/* On crée une instance de la classe JeuOie et on donne les paramètres choisis */
				game = new Game(playersNumber, playersNames, playersColors, xPlayer, yPlayer);
				
				/* On dessine les pions */
				gameBoardLabel.repaint();
			}
		}
	}
	
	/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Jouer" */
	class PlayButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			nextPlayer(true);
		}
	}
	
	/* Méthode qui gère le tour d'un joueur
	   Cette méthode peut être appelée par un appuie sur un joueur (paramètre à true) ou si un joueur doit passer son tour (paramètre à false) */
	public void nextPlayer(boolean nouvelleAction) {
		/* Si un joueur passe son tour on ne ré-initiale pas le message */
		if(nouvelleAction)
			message = "";
		
		int playerNumber = (actionCounter-1)%playersNumber;
		int m = (actionCounter)%playersNumber;
		int turn = ((actionCounter - actionCounter%playersNumber)/playersNumber + 1);
		
		playButton.setEnabled(false);
		game.getPlayerPosition(playerNumber);
		
		/* On appelle la méthode lancerDe de la classe JeuOie pour faire jouer l'action du joueur */
		game.rollDice(playerNumber, turn);
		
		/* On modifie le score du joueur, l'affichage du numéro de tour et le message affiché en bas de l'écran */
		playersLabel[playerNumber].setText(playersTextBoxes[playerNumber].getText() + " : " + game.getPlayerPosition(playerNumber));
		
		message = message + "<br>" + game.getPlayerMessage(playerNumber);
		messageLabel.setText("<html><center>" + message + "</center></html>");
		messageLabel.revalidate();
		messageLabel.repaint();
		
		turnLabel.setText("Turn " + turn);
		
		/* On récupère les nouvelles abscisse et ordonnée du joueur après avoir joué */
		xPlayer[playerNumber] = game.getPlayerX(playerNumber);
		yPlayer[playerNumber] = game.getPlayerY(playerNumber);
		
		/* On lance le timer pour initier le déplacement, il s'appelera autant que nécessaire jusqu'à l'arrivée du pion à la position du joueur */
		timer.start();
		
		/* Si la partie n'est pas finie, on fait avancer le sélecteur au joueur suivant pour indiquer que c'est à son tour de jouer */
		if(!game.isGameEnded()) {
			scoresPanels[4].removeAll();
			scoresPanels[4].add(Box.createRigidArea(new Dimension(20, (35 + m*21))));
			scoresPanels[4].add(playerSelectorLabel);
			scoresPanels[4].repaint();
		}
	}
	
	/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Quitter" */
	class QuitButtonListener implements ActionListener {
		/* Une messagebox apparaît pour demander la confirmation à l'utilisateur, si il confirme, l'application se ferme */
		public void actionPerformed(ActionEvent e) {
			int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the app ?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION)
				System.exit(0);
		}
	}
	
	/* On retrace les pions lorsque la fenêtre reprend le focus (pour corriger un bug où les pions disparaissent lorsque la fenêtre perd le focus) */
	class AppWindowFocusListener implements WindowFocusListener {
		public void windowGainedFocus(WindowEvent e) {
			if(isGameStarted) gameBoardLabel.repaint();
		}
		
		public void windowLostFocus(WindowEvent e) {}
	}
	
	/* La classe plateau permet de gérer les déplacements des pions sur le plateau
	   Un pion est représenté par un carré de la couleur du joueur, entouré par un encadré noir */
	class Board extends JLabel {
		private static final long serialVersionUID = 1L;

		public Board(ImageIcon img) {
			super(img);
		}
		
		public void paintComponent(Graphics g) {
			if(isGameStarted) {
				super.paintComponent(g);
				for(int i = 0; i < playersNumber; i++) {
					g.setColor(Color.BLACK);
					g.fillRect(xPiece[i], yPiece[i], 15, 15);
					g.setColor(playersColors[i]);
					g.fillRect(xPiece[i] + 2, yPiece[i] + 2, 11, 11);
				}
			}
		}
		
		public Dimension getPreferredSize() {
            return new Dimension(1050, 600);
        }
	}
}