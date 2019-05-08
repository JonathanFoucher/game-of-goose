package PackageJeuOie;
import java.awt.Color;

public class JeuOie extends Object
{
	/*
		fini : le booléen qui indique si la partie est finie ou non
		joueurs : le tableau contenant les joueurs
		cases : le tableau contenant les cases du jeu
		k : le nombre de joueurs
		nbCases : le nombre de cases du jeu
	*/
	private boolean fini;
	private Joueur[] joueurs;
	private Case[] cases;
	private int k, nbCases;

	/* Constructeur de la classe JeuOie */
	public JeuOie(int nbJoueurs, String[] noms, Color[] couleurs, int[] x, int[] y)
	{
		k = nbJoueurs;
		nbCases = 71;
		initialiserCases();
		initialiserJoueurs(noms, couleurs, x, y);
		fini = false;
	}
	
	/* Methode qui initialise les joueurs de la partie */
	public void initialiserJoueurs(String[] noms, Color[] couleurs, int[] x, int[] y)
	{
		joueurs = new Joueur[k];
		for(int i = 0; i < k; i++)
			joueurs[i] = new Joueur(noms[i], nbCases, x[i], y[i], couleurs[i]);
	}
	
	/* Methode qui initialise les cases de la partie 
	   Les cases du plateau sont de taille 50 pixels
	   La case 0 représente l'origine (0, 0) et on déduite la postion des cases suivantes en décalant soit en abscisse soit en ordonnée de 50
	   Par exemple la case 1 étant à sa droite, elle se situe en (50, 0) */
	public void initialiserCases()
	{
		cases = new Case[nbCases];
		
		cases[0] = new Case(0, 0);
		for(int i = 1; i < 17; i++)
		{
			int x = cases[i-1].getX() + 50;
			int y = cases[i-1].getY();
			if(i == 14) cases[i] = new CaseTeleporter(x, y, 27);
			else if(i == 5 || i == 10) cases[i] = new CaseAvancer(x, y, 2);
			else if(i == 11) cases[i] = new CaseReculer(x, y, 2);
			else if(i == 6) cases[i] = new CasePasseTour(x, y);
			else cases[i] = new Case(x, y);
		}
		for(int i = 17; i < 26; i++)
		{
			int x = cases[i-1].getX();
			int y = cases[i-1].getY() + 50;
			if(i == 18 || i == 24) cases[i] = new CaseAvancer(x, y, 2);
			else if(i == 19) cases[i] = new CaseReculer(x, y, 2);
			else if(i == 22) cases[i] = new CasePasseTour(x, y);
			else cases[i] = new Case(x, y);
		}
		for(int i = 26; i < 42; i++)
		{
			int x = cases[i-1].getX() - 50;
			int y = cases[i-1].getY();
			if(i == 39) cases[i] = new CaseTeleporter(x, y, 49);
			else if(i == 36) cases[i] = new CaseAvancer(x, y, 1);
			else if(i == 31) cases[i] = new CaseReculer(x, y, 1);
			else if(i == 26) cases[i] = new CaseAvancer(x, y, 2);
			else if(i == 40) cases[i] = new CaseReculer(x, y, 2);
			else if(i == 33) cases[i] = new CasePasseTour(x, y);
			else cases[i] = new Case(x, y);
		}
		for(int i = 42; i < 48; i++)
		{
			int x = cases[i-1].getX();
			int y = cases[i-1].getY() - 50;
			if(i == 47) cases[i] = new CaseTeleporter(x, y, 0);
			else if(i == 43) cases[i] = new CaseAvancer(x, y, 1);
			else if(i == 42 || i == 46) cases[i] = new CaseReculer(x, y, 1);
			else cases[i] = new Case(x, y);
		}
		for(int i = 48; i < 60; i++)
		{
			int x = cases[i-1].getX() + 50;
			int y = cases[i-1].getY();
			if(i == 57) cases[i] = new CaseTeleporter(x, y, 64);
			else if(i == 55) cases[i] = new CaseReculer(x, y, 1);
			else if(i == 53) cases[i] = new CasePasseTour(x, y);
			else cases[i] = new Case(x, y);
		}
		for(int i = 60; i < 63; i++)
		{
			int x = cases[i-1].getX();
			int y = cases[i-1].getY() + 50;
			cases[i] = new Case(x, y);
		}
		for(int i = 63; i < 71; i++)
		{
			int x = cases[i-1].getX() - 50;
			int y = cases[i-1].getY();
			if(i == 64) cases[i] = new CaseTeleporter(x, y, 57);
			else if(i == 65) cases[i] = new CaseTeleporter(x, y, 32);
			else if(i == 67) cases[i] = new CaseAvancer(x, y, 1);
			else if(i == 69) cases[i] = new CasePasseTour(x, y);
			else cases[i] = new Case(x, y);
		}
	}
	
	/* Methode qui appelle l'action du joueur actuel */
	public void lancerDe(int n, int tour)
	{
		if(n == 0) System.out.println("------------------------------------------------------\n\nTOUR " + tour + " :\n");
		cases[joueurs[n].joue()].action(joueurs[n]);
		fini = joueurs[n].testVictoire(fini);
		System.out.println("");
	}
	
	/* Methode qui permet de faire remonter la position du joueur dans l'interface */
	public int getPosJoueur(int n)
	{
		return (joueurs[n].getPos());
	}
	
	/* Methode qui permet de faire remonter l'abscisse du joueur dans l'interface */
	public int getXJoueur(int n)
	{
		/* Pour déterminer la nouvelle abscisse d'un joueur, il suffit d'additionner son abscisse initiale avec l'abscisse de la case */
		return (joueurs[n].getX() + cases[joueurs[n].getPos()].getX());
	}
	
	/* Methode qui permet de faire remonter l'ordonnée du joueur dans l'interface */
	public int getYJoueur(int n)
	{
		/* Pour déterminer la nouvelle ordonnée d'un joueur, il suffit d'additionner son ordonnée initiale avec l'ordonnée de la case */
		return (joueurs[n].getY() + cases[joueurs[n].getPos()].getY());
	}
	
	/* Methode qui permet de faire remonter le message du joueur dans l'interface */
	public String getMsgJoueur(int n)
	{
		return joueurs[n].getMsg();
	}
	
	/* Methode qui permet de faire remonter si le joueur passe son tour dans l'interface */
	public boolean getPasseTourJoueur(int n)
	{
		return joueurs[n].getPasseTour();
	}
	
	/* Methode qui indique à l'interface si la partie est finie */
	public boolean testFin()
	{
		return fini;
	}
}