package com.lilleuniversity.gameofgoose.game.impl;

import com.lilleuniversity.gameofgoose.game.IGame;
import com.lilleuniversity.gameofgoose.player.impl.Player;
import com.lilleuniversity.gameofgoose.space.impl.BackSpace;
import com.lilleuniversity.gameofgoose.space.impl.ForwardSpace;
import com.lilleuniversity.gameofgoose.space.impl.PassTurnSpace;
import com.lilleuniversity.gameofgoose.space.impl.Space;
import com.lilleuniversity.gameofgoose.space.impl.TeleportSpace;

import java.awt.Color;

/**
 * Represents the game
 * @author Jonathan Foucher
 *
 */
public class Game implements IGame {
	/**
	 * The boolean representing the game state
	 */
	private boolean isGameEnded;
	
	/**
	 * The array containing the players
	 */
	private Player[] players;
	
	/**
	 * The array containing the spaces of the board
	 */
	private Space[] spaces;
	
	/**
	 * The number of players
	 */
	private int playersNumber;
	
	/**
	 * The number of spaces on the board
	 */
	private int spacesNumber;

	/**
	 * The constructor
	 * @param playersNumber The number of players
	 * @param names The array containing the players' names
	 * @param colors The array containing the players' colors
	 * @param x The array containing the players x positions
	 * @param y The array containing the players y positions
	 */
	public Game(int playersNumber, String[] names, Color[] colors, int[] x, int[] y) {
		this.playersNumber = playersNumber;
		spacesNumber = 71;
		initSpaces();
		initPlayers(names, colors, x, y);
		isGameEnded = false;
	}
	
	/**
	 * The players initialization
	 * @param names The array containing the players' names
	 * @param colors The array containing the players' colors
	 * @param x The array containing the x players pieces' position at the initialization
	 * @param y The array containing the y players pieces' position at the initialization
	 */
	public void initPlayers(String[] names, Color[] colors, int[] x, int[] y) {
		players = new Player[playersNumber];
		for(int i = 0; i < playersNumber; i++)
			players[i] = new Player(names[i], spacesNumber, x[i], y[i], colors[i]);
	}
	
	/**
	 * The spaces initialization
	 */
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
	
	/**
	 * Make the next player to play
	 * @param n The number of the player
	 * @param turn The turn number in the game
	 */
	public void play(int n, int turn) {
		// if n is equals to 0, it's a new turn
		if(n == 0) System.out.println("------------------------------------------------------\n\nTURN " + turn + " :\n");
		
		// the player plays his/her turn
		spaces[players[n].play()].action(players[n]);
		
		// check if the game is ended (the game is ended when a player wins)
		isGameEnded = players[n].hasWon();
		System.out.println("");
	}
	
	/**
	 * Get the player position (number of the space he/she is on)
	 * @param n The number of the player
	 * @return The player position
	 */
	public int getPlayerPosition(int n) {
		return (players[n].getPosition());
	}
	
	/**
	 * Get the player x position
	 * @param n The number of the player
	 * @return The player x position
	 */
	public int getPlayerX(int n) {
		// to determinate the new x position of a player, we add the space x position to the initial x position of the player
		return (players[n].getXInit() + spaces[players[n].getPosition()].getX());
	}
	
	/**
	 * Get the player y position
	 * @param n The number of the player
	 * @return The player y position
	 */
	public int getPlayerY(int n) {
		// to determinate the new y position of a player, we add the space y position to the initial y position of the player
		return (players[n].getYInit() + spaces[players[n].getPosition()].getY());
	}
	
	/**
	 * Get the player message
	 * @param n The number of the player
	 * @return The player message
	 */
	public String getPlayerMessage(int n) {
		return players[n].getTurnMessage();
	}
	
	/**
	 * Ask if the player pass his/her turn
	 * @param n The number of the player
	 * @return Returns true if the player passes his/her turn and false otherwise
	 */
	public boolean isPassTurn(int n) {
		return players[n].isPassTurn();
	}
	
	/**
	 * Ask if the game is ended
	 * @return Returns true if the game is ended and false otherwise
	 */
	public boolean isGameEnded() {
		return isGameEnded;
	}
}