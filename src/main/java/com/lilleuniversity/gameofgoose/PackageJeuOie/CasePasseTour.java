package PackageJeuOie;

/* Ce type de case fait passer son tour au joueur pour le prochain tour */
public class CasePasseTour extends Case
{
	/* Constructeur de la classe CasePasseTour */
	public CasePasseTour(int abs, int ord)
	{
		super(abs, ord);
	}
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Joueur unJoueur)
	{
		unJoueur.passeSonTour();
	}
}