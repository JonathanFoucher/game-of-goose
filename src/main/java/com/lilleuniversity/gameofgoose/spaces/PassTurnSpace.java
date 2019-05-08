/* Ce type de case fait passer son tour au joueur pour le prochain tour */
public class PassTurnSpace extends Space
{
	/* Constructeur de la classe CasePasseTour */
	public PassTurnSpace(int x, int y)
	{
		super(x, y);
	}
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Player player)
	{
		player.passTurn();
	}
}