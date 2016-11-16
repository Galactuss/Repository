package com.cricket.util;

import java.util.List;
import com.util.ListUtil;

/**
 * 
 * @author PUSHPAK
 *
 */
public class AuctionUtil {
		
	/**
	 * Picks a certain number of players from the given list randomly
	 * @param list List of player names
	 * @param count Number of players to be picked
	 * @return
	 */
	public static List<String> pickPlayers(List<String> list, int count) {
		
		List<String> playersList = ListUtil.pickRandom(count, list);
		return playersList;
	}
}
