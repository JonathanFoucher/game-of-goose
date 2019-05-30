package com.lilleuniversity.gameofgoose.space.impl;

import com.lilleuniversity.gameofgoose.space.ISpace;
import com.lilleuniversity.gameofgoose.player.impl.Player;

/**
 * Represents a space with no action
 * @author Jonathan Foucher
 *
 */
public class Space implements ISpace {
	/**
	 * The x position of the space
	 */
	private int x;
	
	/**
	 * The y position of the space
	 */
	private int y;
	
	/**
	 * The constructor
	 * @param x The x position of the space
	 * @param y	The y position of the space
	 */
	public Space(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * The action realized when a player arrives on the space, in this case there is no action done
	 * @param player The player to advance
	 */
	public void action(Player player) {}
	
	/**
	 * The getter for x attribute of the space
	 * @return The x position value of the space
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * The getter for y attribute of the space
	 * @return The y position value of the space
	 */
	public int getY() {
		return this.y;
	}
}