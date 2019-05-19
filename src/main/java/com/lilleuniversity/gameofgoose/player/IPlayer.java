package com.lilleuniversity.gameofgoose.player;

public interface IPlayer {	
	/* Méthode qui simule l'action lorsque le joueur joue */
	public int joue();
	
	/* Méthode qui simule le lancé de dé */
	public int rollDice();
	
	/* Méthode qui fait avancer le joueur de n cases */
	public void advance(int numberSpaces);
	
	/* Méthode qui fait reculer le joueur de n cases */
	public void moveBack(int numberSpaces);
	
	/* Méthode qui change la variable passeTour pour faire passer son tour au joueur au tour suivant */
	public void passTurn();
	
	/* Méthode qui téléporte le joueur sur la case envoyée en paramètre */
	public void teleport(int targetPosition);
	
	/* Méthode test la victoire du joueur */
	public boolean hasWon();
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getPosition();
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getXInit();
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public int getYInit();
	
	/* Méthode qui renvoie le numéro de la case où se trouve le joueur */
	public String getTurnMessage();
	
	/* Méthode qui téléporte le joueur sur la case envoyée en paramètre */
	public boolean isPassTurn();
}