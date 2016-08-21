package com.match.handler;

import com.isl.model.Player;
import com.match.config.MatchConfigurer;
import com.match.service.GeneralService;

public class SkillDataHandler {
	
	private static GeneralService generalService = (GeneralService) MatchConfigurer.getInstance(GeneralService.class);
	public static int dotChance(Player batsman, Player bowler) {
		
		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float)-chance);
	}
	
	public static int singleChance(Player batsman, Player bowler) {
		
		int chance = 0 - getRemaining(batsman, bowler);
		return chance;
	}

	public static int doubleChance(Player batsman, Player bowler) {
		
		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float)chance/3);
	}

	public static int tripleChance(Player batsman, Player bowler) {
		
		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float)chance/30);
	}
	
	public static int fourChance(Player batsman, Player bowler) {
		
		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float)chance/4);
	}
	
	public static int sixChance(Player batsman, Player bowler) {
		
		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float)chance/12);
	}
	
	public static int wicketChance(Player batsman, Player bowler) {
		
		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float)-chance/20);
	}
	
	public static int getRemaining(Player batsman, Player bowler) {
		
		int remaining = dotChance(batsman, bowler) + doubleChance(batsman, bowler) + tripleChance(batsman, bowler) + fourChance(batsman, bowler) + sixChance(batsman, bowler) + wicketChance(batsman, bowler);
		return remaining;
	}
}
