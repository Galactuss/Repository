package com.isl.comparators;

import java.util.Comparator;

import com.isl.model.Player;

public class BowlingTypeComparator implements Comparator<Player> {

	public int compare(Player player1, Player player2) {
		
		return player1.getBowling_type().compareTo(player2.getBowling_type());
	}

}
