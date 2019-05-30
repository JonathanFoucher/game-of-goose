package com.lilleuniversity.gameofgoose.game.impl;

import com.lilleuniversity.gameofgoose.game.IGame;
import com.lilleuniversity.gameofgoose.player.impl.Player;
import com.lilleuniversity.gameofgoose.space.impl.BackSpace;
import com.lilleuniversity.gameofgoose.space.impl.ForwardSpace;
import com.lilleuniversity.gameofgoose.space.impl.PassTurnSpace;
import com.lilleuniversity.gameofgoose.space.impl.Space;
import com.lilleuniversity.gameofgoose.space.impl.TeleportSpace;

import java.awt.Color;

public class Game implements IGame {
	/*
		fini : le booléen qui indique si la partie est finie ou non
		joueurs : le tableau contenant les joueurs
		cases : le tableau contenant les cases du jeu
		k : le nombre de joueurs
		nbCases : le nombre de cases du jeu
	*/
	private boolean isGameEnded;
	private Player[] players;
	private Space[] spaces;
	private int k, spacesNumber;

	/* Constructeur de la classe JeuOie */
	public Game(int playersNumber, String[] names, Color[] colors, int[] x, int[] y) {
		k = playersNumber;
		spacesNumber = 71;
		initSpaces();
		initPlayers(names, colors, x, y);
		isGameEnded = false;
	}
	
	/* Methode qui initialise les joueurs de la partie */
	public void initPlayers(String[] names, Color[] colors, int[] x, int[] y) {
		players = new Player[k];
		for(int i = 0; i < k; i++)
			players[i] = new Player(names[i], spacesNumber, x[i], y[i], colors[i]);
	}
	
	/* Methode qui initialise les cases de la partie 
	   Les cases du plateau sont de taille 50 pixels
	   La case 0 représente l'origine (0, 0) et on déduite la postion des cases suivantes en décalant soit en abscisse soit en ordonnée de 50
	   Par exemple la case 1 étant à sa droite, elle se situe en (50, 0) */
	public void initSpaces() {
		spaces = new Space[spacesNumber];
		
		spaces[0] = new Space(0, 0);
		for(int i = 1; i < 17; i++) {
			int x = spaces[i-1].getX() + 50;
			int y = spaces[i-1].getY();
			if(i == 14) spaces[i] = new TeleportSpace(x, y, 27);
			else if(i == 5 || i == 10) spaces[i] = new ForwardSpace(x, y, 2);
			else if(i == 11) spaces[i] = new BackSpace(x, y, 2);
			else if(i == 6) spaces[i] = new PassTurnSpace(x, y);
			else spaces[i] = new Space(x, y);
		}

		for(int i = 17; i < 26; i++) {
			int x = spaces[i-1].getX();
			int y = spaces[i-1].getY() + 50;
			if(i == 18 || i == 24) spaces[i] = new ForwardSpace(x, y, 2);
			else if(i == 19) spaces[i] = new BackSpace(x, y, 2);
			else if(i == 22) spaces[i] = new PassTurnSpace(x, y);
			else spaces[i] = new Space(x, y);
		}
		
		for(int i = 26; i < 42; i++) {
			int x = spaces[i-1].getX() - 50;
			int y = spaces[i-1].getY();
			if(i == 39) spaces[i] = new TeleportSpace(x, y, 49);
			else if(i == 36) spaces[i] = new ForwardSpace(x, y, 1);
			else if(i == 31) spaces[i] = new BackSpace(x, y, 1);
			else if(i == 26) spaces[i] = new ForwardSpace(x, y, 2);
			else if(i == 40) spaces[i] = new BackSpace(x, y, 2);
			else if(i == 33) spaces[i] = new PassTurnSpace(x, y);
			else spaces[i] = new Space(x, y);
		}

		for(int i = 42; i < 48; i++) {
			int x = spaces[i-1].getX();
			int y = spaces[i-1].getY() - 50;
			if(i == 47) spaces[i] = new TeleportSpace(x, y, 0);
			else if(i == 43) spaces[i] = new ForwardSpace(x, y, 1);
			else if(i == 42 || i == 46) spaces[i] = new BackSpace(x, y, 1);
			else spaces[i] = new Space(x, y);
		}

		for(int i = 48; i < 60; i++) {
			int x = spaces[i-1].getX() + 50;
			int y = spaces[i-1].getY();
			if(i == 57) spaces[i] = new TeleportSpace(x, y, 64);
			else if(i == 55) spaces[i] = new BackSpace(x, y, 1);
			else if(i == 53) spaces[i] = new PassTurnSpace(x, y);
			else spaces[i] = new Space(x, y);
		}

		for(int i = 60; i < 63; i++) {
			int x = spaces[i-1].getX();
			int y = spaces[i-1].getY() + 50;
			spaces[i] = new Space(x, y);
		}

		for(int i = 63; i < 71; i++) {
			int x = spaces[i-1].getX() - 50;
			int y = spaces[i-1].getY();
			if(i == 64) spaces[i] = new TeleportSpace(x, y, 57);
			else if(i == 65) spaces[i] = new TeleportSpace(x, y, 32);
			else if(i == 67) spaces[i] = new ForwardSpace(x, y, 1);
			else if(i == 69) spaces[i] = new PassTurnSpace(x, y);
			else spaces[i] = new Space(x, y);
		}
	}
	
	/* Methode qui appelle l'action du joueur actuel */
	public void rollDice(int n, int turn) {
		if(n == 0) System.out.println("------------------------------------------------------\n\nTURN " + turn + " :\n");
		spaces[players[n].joue()].action(players[n]);
		isGameEnded = players[n].hasWon();
		System.out.println("");
	}
	
	/* Methode qui permet de faire remonter la position du joueur dans l'interface */
	public int getPlayerPosition(int n) {
		return (players[n].getPosition());
	}
	
	/* Methode qui permet de faire remonter l'abscisse du joueur dans l'interface */
	public int getPlayerX(int n) {
		/* Pour déterminer la nouvelle abscisse d'un joueur, il suffit d'additionner son abscisse initiale avec l'abscisse de la case */
		return (players[n].getXInit() + spaces[players[n].getPosition()].getX());
	}
	
	/* Methode qui permet de faire remonter l'ordonnée du joueur dans l'interface */
	public int getPlayerY(int n) {
		/* Pour déterminer la nouvelle ordonnée d'un joueur, il suffit d'additionner son ordonnée initiale avec l'ordonnée de la case */
		return (players[n].getYInit() + spaces[players[n].getPosition()].getY());
	}
	
	/* Methode qui permet de faire remonter le message du joueur dans l'interface */
	public String getPlayerMessage(int n) {
		return players[n].getTurnMessage();
	}
	
	/* Methode qui permet de faire remonter si le joueur passe son tour dans l'interface */
	public boolean isPassTurn(int n) {
		return players[n].isPassTurn();
	}
	
	/* Methode qui indique à l'interface si la partie est finie */
	public boolean isGameEnded() {
		return isGameEnded;
	}
}