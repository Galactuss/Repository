package com.match.handler;

import java.util.LinkedList;

import com.match.model.Game;
import com.match.model.ResultType;

public class SettledHandler {

	/**
	 * Returns chance based on case
	 * 
	 * @param resultType
	 * @return
	 */
	protected static int getChance(ResultType resultType, Game game) {

		switch (resultType) {
		case DOT:
			return game.getSettledFactors().getDot_chance();
		case SINGLE:
			return game.getSettledFactors().getSingle_chance();
		case DOUBLE:
			return game.getSettledFactors().getDouble_chance();
		case TRIPLE:
			return game.getSettledFactors().getTriple_chance();
		case FOUR:
			return game.getSettledFactors().getFour_chance();
		case SIX:
			return game.getSettledFactors().getSix_chance();
		case WICKET:
			return game.getSettledFactors().getWicket_chance();
		}
		return 0;
	}

	/**
	 * Returns chance for settled case
	 * 
	 * @param partnership
	 * @param score
	 * @param resultType
	 * @return
	 */
	public static int getSettledChance(LinkedList<Integer> partnerships, int score, ResultType resultType, Game game) {

		int chance = 0;
		int partnership;
		try {
			partnership = partnerships.getLast();
		} catch (Exception e) {
			partnership = 0;
		}
		if (partnership > 40 || score > 49) {
			chance += getChance(resultType, game);
		}
		return chance;
	}
}
