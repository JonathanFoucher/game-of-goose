package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/**
 * Represents a space that moves back a player to another space
 * @author Jonathan Foucher
 *
 */
public class BackSpace extends Space {
	/**
	 * The number of spaces the player will move back
	 */
	private int spacesNumber;
	
	/**
	 * The constructor
	 * @param x The x position of the space
	 * @param y	The y position of the space
	 * @param spacesNumber The number of spaces the player will move back
	 */
	public BackSpace(int x, int y, int spacesNumber) {
		super(x, y);
		this.spacesNumber = spacesNumber;
	}
	
	/**
	 * The action realized when a player arrives on the space, in this case it will move back the player
	 * @param player The player to move back
	 */
	public void action(Player player) {
		player.moveBack(spacesNumber);
	}
}