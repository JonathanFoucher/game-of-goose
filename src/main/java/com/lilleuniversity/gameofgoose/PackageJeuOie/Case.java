package PackageJeuOie;

/* Ce type de case ne réalise aucune action sur le joueur */
public class Case extends Object
{
	/*
		x : l'abscisse de la case sur le plateau par rapport à la case 0
		y : l'ordonné de la case sur le plateau par rapport à la case 0
	*/
	private int x, y;
	
	/* Constructeur de la classe Case */
	public Case(int abs, int ord)
	{
		x = abs;
		y = ord;
	}
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Joueur unJoueur){}
	
	/* Methode qui renvoie l'abscisse du joueur */
	public int getX()
	{
		return x;
	}
	
	/* Methode qui renvoie l'ordonnée du joueur */
	public int getY()
	{
		return y;
	}
}