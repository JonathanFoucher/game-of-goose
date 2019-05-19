package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/* Ce type de case fait avancer le joueur d'un nombre de cases prédéfini */
public class ForwardSpace extends Space {
	/* nbCase : le nombre de case(s) dont le joueur avance */
	private int spacesNumber;
	
	/* Constructeur de la classe CaseAvancer */
	public ForwardSpace(int abs, int ord, int spacesNumber) {
		super(abs, ord);
		this.spacesNumber = spacesNumber;
	}

	/* Methode qui réalise l'action de la case lorsque le joueur arrive sur la case */
	public void action(Player player) {
		player.advance(spacesNumber);
	}
}