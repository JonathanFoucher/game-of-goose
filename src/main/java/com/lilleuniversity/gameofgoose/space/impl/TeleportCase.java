package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/* Ce type de case t�l�porte le joueur sur une autre case pr�d�finie lors de la construction du plateau */
public class TeleportCase extends Space {
	/* numCase : le num�ro de la case vers laquelle le joueur est t�l�port� */
	private int spacesNumber;
	
	/* Methode qui r�alise l'action de la case lorsque le joueur arrive sur la case */
	public TeleportCase(int x, int y, int spacesNumber) {
		super(x, y);
		this.spacesNumber = spacesNumber;
	}
	
	/* Methode qui r�alise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Player player) {
		player.teleport(spacesNumber);
	}
}