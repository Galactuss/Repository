package com.match.handler;

import com.isl.model.Player;
import com.match.config.InstanceProvider;
import com.match.model.Game;
import com.match.service.GeneralService;

public class SkillDataHandler {

	private static GeneralService generalService = InstanceProvider.getInstance(GeneralService.class);

	public static int dotChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[0] * chance);
	}

	public static int singleChance(Player batsman, Player bowler, Game game) {

		int chance = 0 - getRemaining(batsman, bowler, game);
		return chance;
	}

	public static int doubleChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[1] * chance);
	}

	public static int tripleChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[2] * chance);
	}

	public static int fourChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[3] * chance);
	}

	public static int sixChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[4] * chance);
	}

	public static int wicketChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[5] * chance);
	}

	public static int getRemaining(Player batsman, Player bowler, Game game) {

		int remaining = dotChance(batsman, bowler, game) + doubleChance(batsman, bowler, game)
				+ tripleChance(batsman, bowler, game) + fourChance(batsman, bowler, game)
				+ sixChance(batsman, bowler, game) + wicketChance(batsman, bowler, game);
		return remaining;
	}
}
