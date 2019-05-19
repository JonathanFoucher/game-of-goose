package com.lilleuniversity.gameofgoose.space;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/* Ce type de case ne r�alise aucune action sur le joueur */
public interface ISpace {
	/* Methode qui r�alise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Player player);
	
	/* Methode qui renvoie l'abscisse du joueur */
	public int getX();
	
	/* Methode qui renvoie l'ordonn�e du joueur */
	public int getY();
}