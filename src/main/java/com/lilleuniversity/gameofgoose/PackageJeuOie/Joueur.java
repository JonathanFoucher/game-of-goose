package PackageJeuOie;
import java.util.Random;
import java.awt.Color;

public class Joueur extends Object
{
	/*
		nom : le nom du joueur
	    nomColor : chaîne de caractère contenant le nom du joueur mais encadré par des balises HTML pour mettre son nom en couleur 
	    dans l’interface du jeu
	    passeTour : booléen qui indique si le joueur passe son tour ou si il peut jouer
		position : le numéro de la case sur laquelle se trouve le joueur
		xInit : l'abscisse initiale du pion du joueur sur le plateau
		yInit : l’ordonné initiale du pion du joueur sur le plateau
		derniereCase : le numéro de la dernière case du jeu (case de victoire)
		messageTour : chaîne de caractères contenant le message à afficher pour le joueur à la fin du tour
	*/
	private String nom, nomColor;
	private boolean passeTour;
	private int position, xInit, yInit, derniereCase;
	private String messageTour = new String("");

	/* Constructeur de la classe Joueur */
	public Joueur(String n, int nbCases, int absInit, int ordInit, Color couleur)
	{
		nom = n;
		nomColor = "<span style=\"color:rgb(" + couleur.getRed() + "," + couleur.getGreen() + "," + couleur.getBlue() + ")\">" + n + "</span>";
		derniereCase = nbCases - 1;
		passeTour = false;
		position = 0;
		xInit = absInit;
		yInit = ordInit;
	}
	
	/* Méthode qui simule l'action lorsque le joueur joue */
	public int joue()
	{
		int res = 0;
		
		/* Si le joueur ne passe pas son tour, il lance le dé sinon on remet à false la variable passeTour pour le prochain tour */
		if(passeTour == false)
		{
			avancer(tirageDe());
			res = position;
		}
		else
		{
			passeTour = false;
			messageTour = nomColor + " passe son tour";
			System.out.println(nom + " passe son tour");
		}
		
		return res;
	}
	
	/* Méthode qui simule le lancé de dé */
	public int tirageDe()
	{
		Random rnd = new Random();
		int nombreAleatoire = 1 + rnd.nextInt(6);
		messageTour = nomColor + " est sur la case " + position + " et lance le d\u00e9 : " + nombreAleatoire;
		System.out.println(nom + " lance le d\u00e9 : " + nombreAleatoire);
		return nombreAleatoire;
	}
	
	/* Méthode qui fait avancer le joueur de n cases */
	public void avancer(int n)
	{
		/* On vérifie que le joueur n'avance pas plus loin que la dernière case, si c'est le cas il recule alors du nombre de pas restants */
		if((position + n) < derniereCase + 1)
		{
			position += n;
			messageTour = messageTour + "<br>" + nomColor + " avance de " + n + " case(s) et arrive sur la case " + position;
			System.out.println(nom + " avance de " + n + " case(s) et arrive sur la case " + position);
		}
		else
		{
			position = derniereCase - (position + n - derniereCase);
			messageTour = messageTour + "<br>" + nomColor + " avance de " + (n - (derniereCase - position)) + " case(s) mais recule de " + (derniereCase - position) + " case(s) et arrive sur la case " + position;
			System.out.println(nom + " avance de " + (n - (derniereCase - position)) + " case(s) mais recule de " + (derniereCase - position) + " case(s) et arrive sur la case " + position);
		}
	}
	
	/* Méthode qui fait reculer le joueur de n cases */
	public void reculer(int n)
	{
		position -= n;
		messageTour = messageTour + "<br>" + nomColor + " recule de " + n + " case(s) et arrive sur la case " + position;
		System.out.println(nom + " recule de " + n + " case(s) et arrive sur la case " + position);
	}
	
	/* Méthode qui change la variable passeTour pour faire passer son tour au joueur au tour suivant */
	public void passeSonTour()
	{
		passeTour = true;
	}
	
	/* Méthode qui téléporte le joueur sur la case envoyée en paramètre */
	public void teleporter(int nouvellePosition)
	{
		position = nouvellePosition;
		messageTour = messageTour + "<br>" + nomColor + " se t\u00e9l\u00e9porte \u00e0 la case " + nouvellePosition;
		System.out.println(nom + " se t\u00e9l\u00e9porte \u00e0 la case " + nouvellePosition);
	}
	
	/* Méthode test la victoire du joueur */
	public boolean testVictoire(boolean fini)
	{
		if(position == derniereCase)
		{
			messageTour = messageTour + "<br>" + nomColor + " a gagn\u00e9 !";
			System.out.println(nom + " a gagn\u00e9 !");
			fini = true;
		}
		return fini;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getPos()
	{
		return position;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getX()
	{
		return xInit;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getY()
	{
		return yInit;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public String getMsg()
	{
		return messageTour;
	}
	
	/* Méthode qui téléporte le joueur sur la case envoyée en paramètre */
	public boolean getPasseTour()
	{
		return passeTour;
	}
}