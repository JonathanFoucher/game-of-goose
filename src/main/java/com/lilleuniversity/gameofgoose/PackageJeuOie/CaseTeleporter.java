package PackageJeuOie;

/* Ce type de case téléporte le joueur sur une autre case prédéfinie lors de la construction du plateau */
public class CaseTeleporter extends Case
{
	/* numCase : le numéro de la case vers laquelle le joueur est téléporté */
	private int numCase;
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public CaseTeleporter(int abs, int ord, int n)
	{
		super(abs, ord);
		numCase = n;
	}
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Joueur unJoueur)
	{
		unJoueur.teleporter(numCase);
	}
}