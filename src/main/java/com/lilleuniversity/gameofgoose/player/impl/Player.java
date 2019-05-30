package com.lilleuniversity.gameofgoose.player.impl;

import com.lilleuniversity.gameofgoose.player.IPlayer;

import java.util.Random;
import java.awt.Color;

/**
 * Represents a player
 * @author Jonathan Foucher
 *
 */
public class Player implements IPlayer {
	/**
	 * The player's name
	 */
	private String name;
	
	/**
	 * The player's name with his color (html style tags)
	 */
	private String coloredName;
	
	/**
	 * The player's turn message
	 */
	private String turnMessage;
	
	/**
	 * Inidicate if the player should pass his/her turn
	 */
	private boolean isPassTurn;
	
	/**
	 * The number of the space where the player is
	 */
	private int position;
	
	/**
	 * The initial x position of the player piece
	 */
	private int xInit;
	
	/**
	 * The initial y position of the player piece
	 */
	private int yInit;
	
	/**
	 * The number of the last space of the board
	 */
	private int lastSpace;

	/**
	 * The constructor
	 * @param name The player's name
	 * @param lastSpace The number of the last space of the board
	 * @param xInit The initial x position of the player piece
	 * @param yInit The initial y position of the player piece
	 * @param color The player piece color
	 */
	public Player(String name, int lastSpace, int xInit, int yInit, Color color) {
		this.name = name;
		this.coloredName = "<span style=\"color:rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")\">" + name + "</span>";
		this.lastSpace = lastSpace - 1;
		this.isPassTurn = false;
		this.position = 0;
		this.turnMessage = new String("");
		this.xInit = xInit;
		this.yInit = yInit;
	}
	
	/**
	 * Represents the player turn
	 * @return The space's number where the player is at the end of his/her turn
	 */
	public int play() {
		int result = 0;
		
		// verify if the player should pass his/her turn or roll the dice
		if(isPassTurn == false) {
			advance(rollDice());
			result = position;
		} else {
			isPassTurn = false;
			turnMessage = coloredName + " passes his/her turn";
			System.out.println(name + " passes his/her turn");
		}
		
		return result;
	}
	
	/**
	 * Simulate the dice roll for the player
	 * @return The result of the dice roll
	 */
	public int rollDice() {
		// random between 1 and 6 for one dice
		Random random = new Random();
		int randomResult = 1 + random.nextInt(6);
		turnMessage = coloredName + " is on the space " + position + " and rolls the dice : " + randomResult;
		System.out.println(name + " is on the space " + position + " and rolls the dice : " + randomResult);
		return randomResult;
	}
	
	/**
	 * Make the player to advance based on a number of spaces
	 * @param numberSpaces The number of spaces the player will advance
	 */
	public void advance(int numberSpaces) {
		// verify that the player is not going over the last space, if it's the case he/she will roll back of the remaining number of spaces
		if((position + numberSpaces) < lastSpace + 1) {
			position += numberSpaces;
			turnMessage = turnMessage + "<br>" + coloredName + " advances of " + numberSpaces + " space(s) and arrives on the space " + position;
			System.out.println(name + " advances of " + numberSpaces + " space(s) and arrives on the space " + position);
		} else {
			position = lastSpace - (position + numberSpaces - lastSpace);
			turnMessage = turnMessage + "<br>" + coloredName + " advances of " + (numberSpaces - (lastSpace - position)) + " space(s) but moves back of " + (lastSpace - position) + " space(s) and arrives on the space " + position;
			System.out.println(name + " advances of " + (numberSpaces - (lastSpace - position)) + " space(s) but moves back of " + (lastSpace - position) + " space(s) and arrives on the space " + position);
		}
	}
	
	/**
	 * Make the player to move back based on a number of spaces
	 * @param numberSpaces The number of spaces the player will move back
	 */
	public void moveBack(int numberSpaces) {
		position -= numberSpaces;
		turnMessage = turnMessage + "<br>" + coloredName + " moves back of " + numberSpaces + " space(s) and arrives on the space " + position;
		System.out.println(name + " moves back of " + numberSpaces + " space(s) and arrives on the space " + position);
	}
	
	/**
	 * Make the player to pass his/her turn during the next turn
	 */
	public void passTurn() {
		isPassTurn = true;
	}
	
	/**
	 * Make the player to teleport to a specific space
	 * @param targetPosition The number of the space where the player will be teleported to
	 */
	public void teleport(int targetPosition) {
		position = targetPosition;
		turnMessage = turnMessage + "<br>" + coloredName + " is teleported to the space " + targetPosition;
		System.out.println(name + " is teleported to the space " + targetPosition);
	}
	
	/**
	 * Test if the player has won
	 * @return Returns true if the player has win and false otherwise
	 */
	public boolean hasWon() {
		if(position == lastSpace) {
			turnMessage = turnMessage + "<br>" + coloredName + " has won !";
			System.out.println(name + " has won !");
			return true;
		}
		return false;
	}
	
	/**
	 * Get the number of the space where the player is
	 * @return The number of the space where the player is
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Get the initial x position of the player piece
	 * @return The initial x position of the player piece
	 */
	public int getXInit() {
		return xInit;
	}
	
	/**
	 * Get the initial y position of the player piece
	 * @return The initial y position of the player piece
	 */
	public int getYInit() {
		return yInit;
	}
	
	/**
	 * Get the message of the player for the last turn he played
	 * @return The message of the player for the last turn he played
	 */
	public String getTurnMessage() {
		return turnMessage;
	}
	
	/**
	 * Ask if the player should pass his/her turn
	 * @return Returns true if the player should pass his/her turn and false otherwise
	 */
	public boolean isPassTurn() {
		return isPassTurn;
	}
}