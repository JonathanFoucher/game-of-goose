package com.lilleuniversity.gameofgoose.player;

/**
 * Represents a player interface
 * @author Jonathan Foucher
 *
 */
public interface IPlayer {	
	/**
	 * Represents the player turn
	 * @return The space's number where the player is at the end of his/her turn
	 */
	public int play();
	
	/**
	 * Simulate the dice roll for the player
	 * @return The result of the dice roll
	 */
	public int rollDice();
	
	/**
	 * Make the player to advance based on a number of spaces
	 * @param numberSpaces The number of spaces the player will advance
	 */
	public void advance(int numberSpaces);
	
	/**
	 * Make the player to move back based on a number of spaces
	 * @param numberSpaces The number of spaces the player will move back
	 */
	public void moveBack(int numberSpaces);
	
	/**
	 * Make the player to pass his/her turn during the next turn
	 */
	public void passTurn();
	
	/**
	 * Make the player to teleport to a specific space
	 * @param targetPosition The number of the space where the player will be teleported to
	 */
	public void teleport(int targetPosition);
	
	/**
	 * Test if the player has won
	 * @return Return true if the player has win and false otherwise
	 */
	public boolean hasWon();
	
	/**
	 * Get the number of the space where the player is
	 * @return The number of the space where the player is
	 */
	public int getPosition();
	
	/**
	 * Get the initial x position of the player piece
	 * @return The initial x position of the player piece
	 */
	public int getXInit();
	
	/**
	 * Get the initial y position of the player piece
	 * @return The initial y position of the player piece
	 */
	public int getYInit();
	
	/**
	 * Get the message of the player for the last turn he played
	 * @return The message of the player for the last turn he played
	 */
	public String getTurnMessage();
	
	/**
	 * Ask if the player should pass his/her turn
	 * @return Return true if the player should pass his/her turn and false otherwise
	 */
	public boolean isPassTurn();
}