package PackageJeuOie;

/* Ce type de case fait avancer le joueur d'un nombre de cases prédéfini */
public class CaseAvancer extends Case
{
	/* nbCase : le nombre de case(s) dont le joueur avance */
	private int nbCases;
	
	/* Constructeur de la classe CaseAvancer */
	public CaseAvancer(int abs, int ord, int n)
	{
		super(abs, ord);
		nbCases = n;
	}

	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Joueur unJoueur)
	{
		unJoueur.avancer(nbCases);
	}
}