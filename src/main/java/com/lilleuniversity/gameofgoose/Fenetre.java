import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import PackageJeuOie.*;

/* La classe Fenetre permet de créer l'interface du jeu */
public class Fenetre extends JFrame 
{
	private static final long serialVersionUID = 1L;

	/*
	 * grille : JPanel représentant le fond de la fenêtre
	 */
	private JPanel grille = new JPanel();
	
	/*
		combo : la combobox qui permet de choisir le nombre de joueurs
		textBoxJoueurs : les textbox qui permettent d'entrer les noms des joueurs
		labelJoueurs : labels "Joueur 1" "Joueur 2"...
		btLancer : le bouton pour lancer la partie
		btQuitter : le bouton pour quitter l'application
	*/
	private JComboBox<Integer> combo = new JComboBox<>();
	private JTextField[] textBoxJoueurs;
	private JLabel[] labelJoueurs;
	private JButton btLancer = new JButton("Lancer"); 
	private JButton btQuitter = new JButton("Quitter"); 
	
	/* 
		nbJoueursMax : le nombre de joueurs maximum, ici fixé à 4 par choix
		couleurs : le tableau contenant les couleurs des joueurs
		nbJoueurs : le nombre de joueurs choisi par l'utilisateur (récupéré avec la combobox)
	*/
	private static int nbJoueursMax = 4;
	private Color[] couleurs = new Color[4];
	private int nbJoueurs;
	
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
	private JeuOie maPartie;
	private JPanel plateauJeu = new JPanel();
	private Plateau plateauLabel;
	private JPanel[] scores = new JPanel[6];
	private JLabel labelTour = new JLabel("Tour 1");
	private JLabel[] labelJou;
	private JButton btJouer = new JButton("");
	private JLabel message = new JLabel("<html><center>D\u00e9but de la partie<br>Cliquer sur le d\u00e9 pour effectuer un lanc\u00e9</center></html>", JLabel.CENTER);
	private String msg = new String("");
	private int actionN = 1;
	private boolean jeu = false;
	private JLabel indicateurJoueur;
	
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
	private ActionListener listener;
	private Timer timer;
	private int[] xJoueur;
	private int[] yJoueur;
	private int[] xPion;
	private int[] yPion;
	
	/* Constructeur de la classe Fenetre */
	public Fenetre()
	{
		/* Les paramètres de la fenêtre */
		setTitle("Configuration de la partie");
		setSize(250, (110 + 55 * nbJoueursMax));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		/* Les paramètres du panneau de fond de la fenêtre */
		grille.setBackground(Color.white);
		grille.setLayout(new GridBagLayout());
		
		/* On définit les couleurs des quatre joueurs */
		couleurs[0] = new Color(0, 0, 255);
		couleurs[1] = new Color(255, 0, 0);
		couleurs[2] = new Color(0, 180, 0);
		couleurs[3] = new Color(130, 0, 250);
		
		/* On forme la combobox avec ses options */
		for(int i = 2; i < nbJoueursMax + 1; i++)
			combo.addItem(i);
		combo.setForeground(Color.blue);
		combo.setPreferredSize(new Dimension(50, 20));
		combo.addActionListener(new comboListener());
		
		/* On créé les labels et textbox pour les noms des joueurs */
		textBoxJoueurs = new JTextField[nbJoueursMax];
		labelJoueurs = new JLabel[nbJoueursMax];
		for(int i = 0; i < textBoxJoueurs.length; i++)
		{
			labelJoueurs[i] = new JLabel("Joueur " + (i + 1) + " : ");
			textBoxJoueurs[i] = new JTextField("");
			textBoxJoueurs[i].setFont(new Font("Arial", Font.BOLD, 12));
			textBoxJoueurs[i].setPreferredSize(new Dimension(80, 20));
			textBoxJoueurs[i].setForeground(couleurs[i]);
			if(i > 1) 
			{
				textBoxJoueurs[i].setEnabled(false);
				labelJoueurs[i].setForeground(Color.LIGHT_GRAY);
			}
		}
		
		
		/* On place les différents éléments dans le panneau de fond */
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		
		grille.add(new JLabel("Nbre de joueurs :"), gbc);
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		grille.add(combo, gbc);
		
		gbc.gridx=0;
		gbc.gridy++;
		grille.add(new JLabel(" "), gbc);
		gbc.gridy++;
		grille.add(new JLabel(" "), gbc);
		
		gbc.gridy+=1;
		gbc.gridwidth = 2;
		for(int i = 1; i < nbJoueursMax + 1; i++)
		{
			gbc.gridx = 0;
			grille.add(labelJoueurs[i-1], gbc);
			gbc.gridx = 2;
			grille.add(textBoxJoueurs[i-1], gbc);
			gbc.gridy++;
			grille.add(new JLabel(" "), gbc);
			gbc.gridy++;
		}
		
		gbc.gridx = 0;
		gbc.gridy++;
		grille.add(new JLabel(" "), gbc);
		gbc.gridy++;
		grille.add(new JLabel(" "), gbc);
		gbc.gridy++;
		grille.add(btLancer, gbc);
		gbc.gridx = 3;
		grille.add(btQuitter, gbc);
		
		
		/* On ajoute l'écoute des boutons et du focus de la fenêtre */
		btLancer.addActionListener(new btLancerListener());
		btJouer.addActionListener(new btJouerListener());
		btQuitter.addActionListener(new btQuitterListener());
		addWindowFocusListener(new focusFenetre());
		
		/* On appelle la méthode creeTimer() pour créer le timer */
		creeTimer();
		
		/* On ajoute le panneau de fond à la fenêtre et on rend la fenêtre visible */
		setContentPane(grille);
		setVisible(true);
	}
	
	/* Méthode qui gère le déplacement des pions (variation abscisse/ordonnée avec un rappel sur le timer pour donner l'illusion d'un mouvement constant) */
	public void creeTimer()
	{
		ActionListener listener = new AbstractAction()
		{
            private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
			{
				timer.stop();
				int n = (actionN - 1)%nbJoueurs;
				/* Si la position du pion n'est pas égale à la position du joueur, on déplace le pion vers la position joueur et on relance le timer
				   en boucle jusqu'à ce que la position soit la même */
				if(xPion[n] < xJoueur[n])
				{
					xPion[n]+=2;
					plateauLabel.repaint();
					timer.start();
				}
				else if(yPion[n] < yJoueur[n])
				{
					yPion[n]+=2;
					plateauLabel.repaint();
					timer.start();
				}
				else if(xPion[n] > xJoueur[n])
				{
					xPion[n]-=2;
					plateauLabel.repaint();
					timer.start();
				}
				else if(yPion[n] > yJoueur[n])
				{
					yPion[n]-=2;
					plateauLabel.repaint();
					timer.start();
				}
				else
				{
					if(!maPartie.testFin())
					{
						actionN++;
						
						if(maPartie.getPasseTourJoueur((actionN - 1)%nbJoueurs)) joueurSuivant(false);
						else btJouer.setEnabled(true);
					}
				}
            }
        };
		timer = new Timer(5, listener);
	}
	
	/* Action qui s'applique lorsque l'utilisateur change la valeur de la combobox du nombre de joueurs (fenêtre de configuration) */
	class comboListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			/* Selon le nombre sélectionné dans la combobox, on grise/dégrise les labels et textbox des noms des joueurs */
			for(int i = 2; i < (Integer)combo.getSelectedItem(); i++)
			{
				textBoxJoueurs[i].setEnabled(true);
				labelJoueurs[i].setForeground(Color.BLACK);
			}
			for(int i = (Integer)combo.getSelectedItem(); i < textBoxJoueurs.length; i++)
			{
				textBoxJoueurs[i].setEnabled(false);
				labelJoueurs[i].setForeground(Color.LIGHT_GRAY);
			}
		}
	}
	
	/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Lancer" */
	class btLancerListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e)
		{
			/* On commence par vérifier que des noms ont été entrés dans les textbox
			   Si ce n'est pas le cas, on affiche un message d'erreur sinon on forme la fenêtre de jeu */
			boolean testNoms = true;
			for(int i = 0; i < (Integer)combo.getSelectedItem(); i++)
			{
				if(textBoxJoueurs[i].getText().length() == 0) testNoms = false;
			}
			
			if(!testNoms)
			{
				JOptionPane.showMessageDialog(null, "Erreur, un nom de joueur ne peut \u00eatre vide !", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				/* Paramètres de la fenêtre et du panneau de fond */
				jeu = true;
				int largeur = 1050;
				int hauteur = 779;
				nbJoueurs = (Integer)combo.getSelectedItem();
				grille.removeAll();
				setTitle("Jeu de l'oie");
				setSize(largeur, hauteur);
				grille.setLayout(new BoxLayout(grille, BoxLayout.PAGE_AXIS));
				grille.setPreferredSize(new Dimension(largeur, hauteur));
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);
				
				/* Paramètres du panneau contenant le plateau de jeu */
				plateauJeu.setLayout(new GridBagLayout());
				plateauJeu.setPreferredSize(new Dimension(largeur, 600));
				
				/* On définit les paramètres des panneaux de scores */
				for(int i = 0; i < scores.length; i++)
				{
					scores[i] = new JPanel();
					if(i < 2) scores[i].setLayout(new FlowLayout());
					else if (i == 2) scores[i].setLayout(new BorderLayout());
					else scores[i].setLayout(new BoxLayout(scores[i], BoxLayout.PAGE_AXIS));
				}
				scores[0].setBackground(Color.blue);
				
				for(int i = 1; i < scores.length; i++)
					scores[i].setBackground(Color.black);
				
				scores[0].setPreferredSize(new Dimension(1050, 150));
				scores[1].setPreferredSize(new Dimension(255, 145));
				scores[2].setPreferredSize(new Dimension(518, 145));
				scores[3].setPreferredSize(new Dimension(255, 145));
				scores[4].setPreferredSize(new Dimension(95, 135));
				scores[5].setPreferredSize(new Dimension(129, 135));
				
				/* On ajoute les différents panneaux */
				grille.add(plateauJeu);
				grille.add(scores[0]);
				for(int i = 1; i < scores.length; i++)
					scores[0].add(scores[i]);
				
				labelJou = new JLabel[nbJoueurs];
				String[] nomsJoueurs = new String[nbJoueurs];
				scores[1].add(scores[4]);
				scores[1].add(scores[5]);
				
				/* On ajoute l'image du sélecteur */
				scores[4].add(Box.createRigidArea(new Dimension(20, 35)));
				try
				{
					Image img = ImageIO.read(getClass().getResource("resources/selecteur.png"));
					indicateurJoueur = new JLabel(new ImageIcon(img));
					scores[4].add(indicateurJoueur);
				}
				catch (Exception ex)
				{
					System.out.println(ex);
				}
				
				/* On paramètre et ajoute les labels de score des joueurs (noms + scores) */
				for(int i = 0; i < nbJoueurs; i++)
				{
					labelJou[i] = new JLabel(textBoxJoueurs[i].getText() + " : 0");
					labelJou[i].setForeground(couleurs[i]);
					nomsJoueurs[i] = textBoxJoueurs[i].getText();
				}
				
				scores[5].add(Box.createRigidArea(new Dimension(0,10)));
				scores[5].add(labelTour);
				scores[5].add(Box.createRigidArea(new Dimension(0,10)));
				for(int i = 0; i < nbJoueurs; i++)
				{
					scores[5].add(labelJou[i]);
					scores[5].add(Box.createRigidArea(new Dimension(0,5)));
				}
				
				scores[2].add(message, BorderLayout.CENTER);
				
				/* On ajoute une image de dé sur le bouton "Jouer" */
				try
				{
					Image img = ImageIO.read(getClass().getResource("resources/dice.png"));
					btJouer.setIcon(new ImageIcon(img));
				}
				catch (Exception ex)
				{
					System.out.println(ex);
				}
				
				/* On configure et ajoute les boutons */
				btJouer.setAlignmentX(Component.CENTER_ALIGNMENT);
				btQuitter.setAlignmentX(Component.CENTER_ALIGNMENT);
				btJouer.setPreferredSize(new Dimension(80, 20));
				btQuitter.setPreferredSize(new Dimension(80, 20));
				scores[3].add(Box.createRigidArea(new Dimension(0,30)));
				scores[3].add(btJouer);
				scores[3].add(Box.createRigidArea(new Dimension(0,20)));
				scores[3].add(btQuitter);
				
				/* On ajoute l'image du plateau */
				try
				{
					Image img = ImageIO.read(getClass().getResource("resources/plateau.png"));
					plateauLabel = new Plateau(new ImageIcon(img));
					plateauJeu.add(plateauLabel);
				}
				catch (Exception ex)
				{
					System.out.println(ex);
				}
				
				/* On définit les position initiales des pions et des joueurs */
				xJoueur = new int[4];
				yJoueur = new int[4];
				xPion = new int[nbJoueurs];
				yPion = new int[nbJoueurs];
				
				xJoueur[0] = 103; yJoueur[0] = 55;
				xJoueur[1] = 127; yJoueur[1] = 55;
				xJoueur[2] = 103; yJoueur[2] = 79;
				xJoueur[3] = 127; yJoueur[3] = 79;
				for(int i = 0; i < nbJoueurs; i++)
				{
					xPion[i] = xJoueur[i];
					yPion[i] = yJoueur[i];
				}
				
				/* On change les couleurs du texte des labels pour afficher le texte en blanc */
				labelTour.setForeground(Color.WHITE);
				message.setForeground(Color.WHITE);
				
				/* On crée une instance de la classe JeuOie et on donne les paramètres choisis */
				maPartie = new JeuOie(nbJoueurs, nomsJoueurs, couleurs, xJoueur, yJoueur);
				
				/* On dessine les pions */
				plateauLabel.repaint();
			}
		}
	}
	
	/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Jouer" */
	class btJouerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			joueurSuivant(true);
		}
	}
	
	/* Méthode qui gère le tour d'un joueur
	   Cette méthode peut être appelée par un appuie sur un joueur (paramètre à true) ou si un joueur doit passer son tour (paramètre à false) */
	public void joueurSuivant(boolean nouvelleAction)
	{
		/* Si un joueur passe son tour on ne ré-initiale pas le message */
		if(nouvelleAction) msg = "";
		
		int n = (actionN-1)%nbJoueurs;
		int m = (actionN)%nbJoueurs;
		int tour = ((actionN - actionN%nbJoueurs)/nbJoueurs + 1);
		
		btJouer.setEnabled(false);
		int temp = maPartie.getPosJoueur(n);
		
		/* On appelle la méthode lancerDe de la classe JeuOie pour faire jouer l'action du joueur */
		maPartie.lancerDe(n, tour);
		
		/* On modifie le score du joueur, l'affichage du numéro de tour et le message affiché en bas de l'écran */
		labelJou[n].setText(textBoxJoueurs[n].getText() + " : " + maPartie.getPosJoueur(n));
		
		msg = msg + "<br>" + maPartie.getMsgJoueur(n);
		message.setText("<html><center>" + msg + "</center></html>");
		message.revalidate();
		message.repaint();
		
		labelTour.setText("Tour " + tour);
		
		/* On récupère les nouvelles abscisse et ordonnée du joueur après avoir joué */
		xJoueur[n] = maPartie.getXJoueur(n);
		yJoueur[n] = maPartie.getYJoueur(n);
		
		/* On lance le timer pour initier le déplacement, il s'appelera autant que nécessaire jusqu'à l'arrivée du pion à la position du joueur */
		timer.start();
		
		/* Si la partie n'est pas finie, on fait avancer le sélecteur au joueur suivant pour indiquer que c'est à son tour de jouer */
		if(!maPartie.testFin())
		{
			scores[4].removeAll();
			scores[4].add(Box.createRigidArea(new Dimension(20, (35 + m*21))));
			scores[4].add(indicateurJoueur);
			scores[4].repaint();
		}
	}
	
	/* Action qui s'applique lorsque l'utilisateur appuie sur le bouton "Quitter" */
	class btQuitterListener implements ActionListener
	{
		/* Une messagebox apparaît pour demander la confirmation à l'utilisateur, si il confirme, l'application se ferme */
		public void actionPerformed(ActionEvent e)
		{
			int choix = JOptionPane.showConfirmDialog(null, "\u00cates-vous s\u00fbr(e) de vouloir quitter l'application ?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if(choix == JOptionPane.YES_OPTION)
				System.exit(0);
		}
	}
	
	/* On retrace les pions lorsque la fenêtre reprend le focus (pour corriger un bug où les pions disparaissent lorsque la fenêtre perd le focus) */
	class focusFenetre implements WindowFocusListener
	{
		public void windowGainedFocus(WindowEvent e)
		{
			if(jeu) plateauLabel.repaint();
		}
		
		public void windowLostFocus(WindowEvent e)
		{}
	}
	
	/* La classe plateau permet de gérer les déplacements des pions sur le plateau
	   Un pion est représenté par un carré de la couleur du joueur, entouré par un encadré noir */
	class Plateau extends JLabel
	{
		private static final long serialVersionUID = 1L;

		public Plateau(ImageIcon img)
		{
			super(img);
		}
		
		public void paintComponent(Graphics g )
		{
			if(jeu)
			{
				super.paintComponent(g);
				for(int i = 0; i < nbJoueurs; i++)
				{
					g.setColor(Color.BLACK);
					g.fillRect(xPion[i], yPion[i], 15, 15);
					g.setColor(couleurs[i]);
					g.fillRect(xPion[i] + 2, yPion[i] + 2, 11, 11);
				}
			}
		}
		
		public Dimension getPreferredSize() {
            return new Dimension(1050, 600);
        }
	}
}