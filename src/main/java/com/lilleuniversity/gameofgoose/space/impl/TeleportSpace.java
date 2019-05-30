package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/**
 * Represents a space that teleports a player to another space
 * @author Jonathan Foucher
 *
 */
public class TeleportSpace extends Space {
	/**
	 * The number of the space the player will be teleported to
	 */
	private int spaceNumber;
	
	/**
	 * The constructor
	 * @param x The x position of the space
	 * @param y	The y position of the space
	 * @param spaceNumber The number of the space the player will be teleported to
	 */
	public TeleportSpace(int x, int y, int spaceNumber) {
		super(x, y);
		this.spaceNumber = spaceNumber;
	}
	
	/**
	 * The action realized when a player arrives on the space, in this case it will teleport the player
	 * @param player The player to teleport
	 */
	public void action(Player player) {
		player.teleport(spaceNumber);
	}
}