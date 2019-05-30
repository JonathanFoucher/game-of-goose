package com.lilleuniversity.gameofgoose.game;

import java.awt.Color;

/**
 * Represents the game interface
 * @author Jonathan Foucher
 *
 */
public interface IGame {
	/**
	 * The players initialization
	 * @param names The array containing the players' names
	 * @param colors The array containing the players' colors
	 * @param x The array containing the x players pieces' position at the initialization
	 * @param y The array containing the y players pieces' position at the initialization
	 */
	public void initPlayers(String[] names, Color[] colors, int[] x, int[] y);
	
	/**
	 * The spaces initialization
	 */
	public void initSpaces();

	/**
	 * Roll the dice for the next player
	 * @param n The number of the player
	 * @param turn The turn number in the game
	 */
	public void play(int n, int turn);
	
	/**
	 * Get the player position (number of the space he/she is on)
	 * @param n The number of the player
	 * @return The player position
	 */
	public int getPlayerPosition(int n);
	
	/**
	 * Get the player x position
	 * @param n The number of the player
	 * @return The player x position
	 */
	public int getPlayerX(int n);
	
	/**
	 * Get the player y position
	 * @param n The number of the player
	 * @return The player y position
	 */
	public int getPlayerY(int n);
	
	/**
	 * Get the player message
	 * @param n The number of the player
	 * @return The player message
	 */
	public String getPlayerMessage(int n);
	
	/**
	 * Ask if the player pass his/her turn
	 * @param n The number of the player
	 * @return Returns true if the player passes his/her turn and false otherwise
	 */
	public boolean isPassTurn(int n);
	
	/**
	 * Ask if the game is ended
	 * @return Returns true if the game is ended and false otherwise
	 */
	public boolean isGameEnded();
}