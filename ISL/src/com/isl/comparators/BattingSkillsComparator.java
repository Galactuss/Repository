package com.isl.comparators;

import java.util.Comparator;

import com.isl.model.Player;

public class BattingSkillsComparator implements Comparator<Player> {

	public int compare(Player player1, Player player2) {
		
		return player2.getBatting_skills() - player1.getBatting_skills();
	}

}
