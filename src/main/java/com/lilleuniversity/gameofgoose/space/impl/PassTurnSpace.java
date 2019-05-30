package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/**
 * Represents a space that makes a player wait during the next turn
 * @author Jonathan Foucher
 *
 */
public class PassTurnSpace extends Space {
	/**
	 * The constructor
	 * @param x The x position of the space
	 * @param y	The y position of the space
	 */
	public PassTurnSpace(int x, int y) {
		super(x, y);
	}
	
	/**
	 * The action realized when a player arrives on the space, in this case it will make the player waiting during the next turn
	 * @param player The player to make wait
	 */
	public void action(Player player) {
		player.passTurn();
	}
}