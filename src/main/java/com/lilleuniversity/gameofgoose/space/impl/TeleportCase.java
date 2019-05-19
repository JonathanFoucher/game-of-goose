package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/* Ce type de case téléporte le joueur sur une autre case prédéfinie lors de la construction du plateau */
public class TeleportCase extends Space {
	/* numCase : le numéro de la case vers laquelle le joueur est téléporté */
	private int spacesNumber;
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public TeleportCase(int x, int y, int spacesNumber) {
		super(x, y);
		this.spacesNumber = spacesNumber;
	}
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Player player) {
		player.teleport(spacesNumber);
	}
}