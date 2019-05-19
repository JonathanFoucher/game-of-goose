package com.lilleuniversity.gameofgoose.game;

import java.awt.Color;

public interface IGame {
	/* Methode qui initialise les joueurs de la partie */
	public void initPlayers(String[] names, Color[] colors, int[] x, int[] y);
	
	/* Methode qui initialise les cases de la partie 
	   Les cases du plateau sont de taille 50 pixels
	   La case 0 repr�sente l'origine (0, 0) et on d�duite la postion des cases suivantes en d�calant soit en abscisse soit en ordonn�e de 50
	   Par exemple la case 1 �tant � sa droite, elle se situe en (50, 0) */
	public void initSpaces();

	/* Methode qui appelle l'action du joueur actuel */
	public void rollDice(int n, int turn);
	
	/* Methode qui permet de faire remonter la position du joueur dans l'interface */
	public int getPlayerPosition(int n);
	
	/* Methode qui permet de faire remonter l'abscisse du joueur dans l'interface */
	public int getPlayerX(int n);
	
	/* Methode qui permet de faire remonter l'ordonn�e du joueur dans l'interface */
	public int getPlayerY(int n);
	
	/* Methode qui permet de faire remonter le message du joueur dans l'interface */
	public String getPlayerMessage(int n);
	
	/* Methode qui permet de faire remonter si le joueur passe son tour dans l'interface */
	public boolean isPassTurn(int n);
	
	/* Methode qui indique � l'interface si la partie est finie */
	public boolean isGameEnded();
}