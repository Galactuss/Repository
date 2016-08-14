package com.isl.comparators;

import java.util.Comparator;

import com.isl.model.Player;

public class BowlingSkillsComparator implements Comparator<Player> {

	public int compare(Player player1, Player player2) {
		
		return player2.getBowling_skills() - player1.getBowling_skills();
	}
	
	
}
