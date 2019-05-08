package PackageJeuOie;

/* Ce type de case fait reculer le joueur d'un nombre de cases prédéfini */
public class CaseReculer extends Case
{
	/* nbCase : le nombre de case(s) dont le joueur recule */
	private int nbCases;
	
	/* Constructeur de la classe CaseReculer */
	public CaseReculer(int abs, int ord, int n)
	{
		super(abs, ord);
		nbCases = n;
	}
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Joueur unJoueur)
	{
		unJoueur.reculer(nbCases);
	}
}