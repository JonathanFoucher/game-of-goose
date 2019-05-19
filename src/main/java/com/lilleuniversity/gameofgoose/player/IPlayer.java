package com.lilleuniversity.gameofgoose.player;

public interface IPlayer {	
	/* M�thode qui simule l'action lorsque le joueur joue */
	public int joue();
	
	/* M�thode qui simule le lanc� de d� */
	public int rollDice();
	
	/* M�thode qui fait avancer le joueur de n cases */
	public void advance(int numberSpaces);
	
	/* M�thode qui fait reculer le joueur de n cases */
	public void moveBack(int numberSpaces);
	
	/* M�thode qui change la variable passeTour pour faire passer son tour au joueur au tour suivant */
	public void passTurn();
	
	/* M�thode qui t�l�porte le joueur sur la case envoy�e en param�tre */
	public void teleport(int targetPosition);
	
	/* M�thode test la victoire du joueur */
	public boolean hasWon();
	
	/* M�thode qui renvoie le num�ro de la case o� se trouve le joueur */
	public int getPosition();
	
	/* M�thode qui renvoie le num�ro de la case o� se trouve le joueur */
	public int getXInit();
	
	/* M�thode qui renvoie le num�ro de la case o� se trouve le joueur */
	public int getYInit();
	
	/* M�thode qui renvoie le num�ro de la case o� se trouve le joueur */
	public String getTurnMessage();
	
	/* M�thode qui t�l�porte le joueur sur la case envoy�e en param�tre */
	public boolean isPassTurn();
}