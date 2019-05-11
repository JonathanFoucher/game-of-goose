package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/* Ce type de case fait reculer le joueur d'un nombre de cases prédéfini */
public class BackSpace extends Space {
	/* nbCase : le nombre de case(s) dont le joueur recule */
	private int spacesNumber;
	
	/* Constructeur de la classe CaseReculer */
	public BackSpace(int x, int y, int spacesNumber) {
		super(x, y);
		this.spacesNumber = spacesNumber;
	}
	
	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Player player) {
		player.moveBack(spacesNumber);
	}
}