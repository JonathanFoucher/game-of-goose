package com.lilleuniversity.gameofgoose.space;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/* Ce type de case ne réalise aucune action sur le joueur */
public interface ISpace {
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Player player);
	
	/* Methode qui renvoie l'abscisse du joueur */
	public int getX();
	
	/* Methode qui renvoie l'ordonnée du joueur */
	public int getY();
}