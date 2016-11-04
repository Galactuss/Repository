package com.isl.comparators;

import java.util.Comparator;

import com.isl.model.Player;

public class WicketKeepingSkillsComparator implements Comparator<Player> {
	
	public int compare(Player player1, Player player2) {
		
		return player2.getWicketkeeping_skills() - player1.getWicketkeeping_skills();
	}
}
