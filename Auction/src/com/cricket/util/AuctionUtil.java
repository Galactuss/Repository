package com.cricket.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Pushpak
 *
 */
public class AuctionUtil {
	
	private static Random randomGenerator = new Random();
	
	/**
	 * Picks a certain number of players from the given list randomly
	 * @param list List of player names
	 * @param count Number of players to be picked
	 * @return
	 */
	public static List<String> pickPlayers(List<String> list, int count) {
		
		List<String> playersList = new ArrayList<String>();
		for(int i=0; i<count; i++) {
			int size = list.size() - 1;
			int index = randomGenerator.nextInt(size);
			playersList.add(list.get(index));
			list.remove(index);
			/*try {
			    Thread.sleep(5000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}*/
		}
		return playersList;
	}
}
