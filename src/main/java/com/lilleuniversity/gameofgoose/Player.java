import java.util.Random;
import java.awt.Color;

public class Player extends Object
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
	private String name, coloredName;
	private boolean isPassTurn;
	private int position, xInit, yInit, lastSpace;
	private String turnMessage = new String("");

	/* Constructeur de la classe Joueur */
	public Player(String name, int nbCases, int xInit, int yInit, Color color)
	{
		this.name = name;
		this.coloredName = "<span style=\"color:rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")\">" + name + "</span>";
		this.lastSpace = nbCases - 1;
		this.isPassTurn = false;
		this.position = 0;
		this.xInit = xInit;
		this.yInit = yInit;
	}
	
	/* Méthode qui simule l'action lorsque le joueur joue */
	public int joue()
	{
		int result = 0;
		
		/* Si le joueur ne passe pas son tour, il lance le dé sinon on remet à false la variable passeTour pour le prochain tour */
		if(isPassTurn == false)
		{
			advance(rollDice());
			result = position;
		}
		else
		{
			isPassTurn = false;
			turnMessage = coloredName + " passes his/her turn";
			System.out.println(name + " passes his/her turn");
		}
		
		return result;
	}
	
	/* Méthode qui simule le lancé de dé */
	public int rollDice()
	{
		Random random = new Random();
		int randomResult = 1 + random.nextInt(6);
		turnMessage = coloredName + " is on the space " + position + " and rolls the dice : " + randomResult;
		System.out.println(name + " is on the space " + position + " and rolls the dice : " + randomResult);
		return randomResult;
	}
	
	/* Méthode qui fait avancer le joueur de n cases */
	public void advance(int numberSpaces)
	{
		/* On vérifie que le joueur n'avance pas plus loin que la dernière case, si c'est le cas il recule alors du nombre de pas restants */
		if((position + numberSpaces) < lastSpace + 1)
		{
			position += numberSpaces;
			turnMessage = turnMessage + "<br>" + coloredName + " advances of " + numberSpaces + " space(s) and arrives on the space " + position;
			System.out.println(name + " advances of " + numberSpaces + " space(s) and arrives on the space " + position);
		}
		else
		{
			position = lastSpace - (position + numberSpaces - lastSpace);
			turnMessage = turnMessage + "<br>" + coloredName + " advances of " + (numberSpaces - (lastSpace - position)) + " space(s) but moves back of " + (lastSpace - position) + " space(s) and arrives on the space " + position;
			System.out.println(name + " advances of " + (numberSpaces - (lastSpace - position)) + " space(s) but moves back of " + (lastSpace - position) + " space(s) and arrives on the space " + position);
		}
	}
	
	/* Méthode qui fait reculer le joueur de n cases */
	public void moveBack(int numberSpaces)
	{
		position -= numberSpaces;
		turnMessage = turnMessage + "<br>" + coloredName + " moves back of " + numberSpaces + " space(s) and arrives on the space " + position;
		System.out.println(name + " moves back of " + numberSpaces + " space(s) and arrives on the space " + position);
	}
	
	/* Méthode qui change la variable passeTour pour faire passer son tour au joueur au tour suivant */
	public void passTurn()
	{
		isPassTurn = true;
	}
	
	/* Méthode qui téléporte le joueur sur la case envoyée en paramètre */
	public void teleport(int targetPosition)
	{
		position = targetPosition;
		turnMessage = turnMessage + "<br>" + coloredName + " is teleported to the space " + targetPosition;
		System.out.println(name + " is teleported to the space " + targetPosition);
	}
	
	/* Méthode test la victoire du joueur */
	public boolean hasWon()
	{
		if(position == lastSpace)
		{
			turnMessage = turnMessage + "<br>" + coloredName + " has won !";
			System.out.println(name + " has won !");
			return true;
		}
		return false;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getPosition()
	{
		return position;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getXInit()
	{
		return xInit;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getYInit()
	{
		return yInit;
	}
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public String getTurnMessage()
	{
		return turnMessage;
	}
	
	/* Méthode qui téléporte le joueur sur la case envoyée en paramètre */
	public boolean isPassTurn()
	{
		return isPassTurn;
	}
}