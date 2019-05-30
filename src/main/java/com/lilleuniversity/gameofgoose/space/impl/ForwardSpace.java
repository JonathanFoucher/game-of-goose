package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/**
 * Represents a space that advances a player to another space
 * @author Jonathan Foucher
 *
 */
public class ForwardSpace extends Space {
	/**
	 * The number of spaces the player will advance
	 */
	private int spacesNumber;
	
	/**
	 * The constructor
	 * @param x The x position of the space
	 * @param y	The y position of the space
	 * @param spacesNumber The number of spaces the player will advance
	 */
	public ForwardSpace(int x, int y, int spacesNumber) {
		super(x, y);
		this.spacesNumber = spacesNumber;
	}

	/**
	 * The action realized when a player arrives on the space, in this case it will advance the player
	 * @param player The player to advance
	 */
	public void action(Player player) {
		player.advance(spacesNumber);
	}
}