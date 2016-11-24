package com.match.handler;

import com.isl.model.Player;
import com.util.InstanceProvider;
import com.match.model.Game;
import com.match.model.ResultType;
import com.match.service.GeneralService;
import com.match.service.impl.GeneralServiceImpl;

public class SkillDataHandler {

	private static GeneralService generalService = InstanceProvider.getInstance(GeneralServiceImpl.class);

	private static int dotChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[0] * chance);
	}

	private static int singleChance(Player batsman, Player bowler, Game game) {

		int chance = 0 - getRemaining(batsman, bowler, game);
		return chance;
	}

	private static int doubleChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[1] * chance);
	}

	private static int tripleChance(Player batsman, Player bowler, Game game) {

		int chance = batsman.getBatting_skills() - bowler.getBowling_skills();
		return generalService.parseChanceToInteger((float) game.getSkillsFactors()[2] * chance);
	}

	private static int fourChance(Player batsman, Player bowler, Game game) {

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

	private static int getRemaining(Player batsman, Player bowler, Game game) {

		int remaining = dotChance(batsman, bowler, game) + doubleChance(batsman, bowler, game)
				+ tripleChance(batsman, bowler, game) + fourChance(batsman, bowler, game)
				+ sixChance(batsman, bowler, game) + wicketChance(batsman, bowler, game);
		return remaining;
	}
	
	public static int getChance(Player batsman, Player bowler, Game game, ResultType resultType) {
		
		switch(resultType) {
			case DOT:
				return dotChance(batsman, bowler, game);
			case SINGLE:
				return singleChance(batsman, bowler, game);
			case DOUBLE:
				return doubleChance(batsman, bowler, game);
			case TRIPLE:
				return tripleChance(batsman, bowler, game);
			case FOUR:
				return fourChance(batsman, bowler, game);
			case SIX:
				return sixChance(batsman, bowler, game);
			case WICKET:
				return wicketChance(batsman, bowler, game);
			default:
				break;
		}
		
		return 0;
	}
}
