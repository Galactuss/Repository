package com.cricket.util;

import java.util.ArrayList;
import java.util.List;

import com.util.FunctionUtil;
import com.util.RandomUtil;

/**
 * 
 * @author PUSHPAK
 *
 */
public class AuctionUtil {

	/**
	 * Picks a certain number of players from the given list randomly
	 * 
	 * @param list
	 *            List of player names
	 * @param count
	 *            Number of players to be picked
	 * @return
	 */
	public static List<String> pickPlayers(List<String> list, int count, boolean isNormal) {

		List<String> playersList = new ArrayList<String>();
		if (count > list.size()) {
			count = list.size();
		}
		if (isNormal && count < list.size() * 3 / 4) {
			count = list.size() * 3 / 4;
		}
		FunctionUtil.times(count, i -> {
			int index = RandomUtil.generateRandom(list.size());
			playersList.add(list.get(index));
			list.remove(index);
		});
		return playersList;
	}
}
