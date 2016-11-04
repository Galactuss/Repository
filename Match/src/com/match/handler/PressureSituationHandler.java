package com.match.handler;

import java.util.LinkedList;
import java.util.ListIterator;

import com.match.model.Game;
import com.match.model.ResultType;

public class PressureSituationHandler {

	/**
	 * Returns chance based on case
	 * 
	 * @param resultType
	 * @return
	 */
	protected static int getChance(ResultType resultType, Game game) {

		switch (resultType) {
		case DOT:
			return game.getPressureFactors().getDot_chance();
		case SINGLE:
			return game.getPressureFactors().getSingle_chance();
		case DOUBLE:
			return game.getPressureFactors().getDouble_chance();
		case TRIPLE:
			return game.getPressureFactors().getTriple_chance();
		case FOUR:
			return game.getPressureFactors().getFour_chance();
		case SIX:
			return game.getPressureFactors().getSix_chance();
		case WICKET:
			return game.getPressureFactors().getWicket_chance();
		}
		return 0;
	}

	/**
	 * Returns pressure situation chance
	 * 
	 * @param partnerships
	 * @param resultType
	 * @return
	 */
	public static int getPressureChance(LinkedList<Integer> partnerships, ResultType resultType, Game game) {

		int pressureConstant;
		int chance;
		pressureConstant = getPressureConstant(partnerships, game);
		chance = (int) ((0.5 + 0.2 * pressureConstant) * getChance(resultType, game));
		return chance;
	}

	/**
	 * 
	 * @param partnerships
	 * @return
	 */
	protected static int getPressureConstant(LinkedList<Integer> partnerships, Game game) {

		int pressureConstant = 0;
		int current_index;
		try {
			current_index = partnerships.indexOf(partnerships.getLast());
		} catch (Exception e) {
			current_index = partnerships.size() - 1;
		}
		ListIterator<Integer> iterator = partnerships.listIterator(current_index);
		while (iterator.hasPrevious()) {
			Integer prev_partnership = iterator.previous();
			if (prev_partnership == null || prev_partnership < game.getPressureThreshold()) {
				pressureConstant++;
				continue;
			} else {
				break;
			}
		}
		return pressureConstant;
	}
}
