package com.lilleuniversity.gameofgoose.space;

import com.lilleuniversity.gameofgoose.player.impl.Player;

/**
 * Represents a space interface
 * @author Jonathan Foucher
 *
 */
public interface ISpace {
	/**
	 * The action realized when a player arrives on the space (the action depends on the space type)
	 * @param player The player on whom the action is realized
	 */
	public void action(Player player);
	
	/**
	 * Get the x position value of the space
	 * @return The x position value of the space
	 */
	public int getX();
	
	/**
	 * Get the y position value of the space
	 * @return The y position value of the space
	 */
	public int getY();
}