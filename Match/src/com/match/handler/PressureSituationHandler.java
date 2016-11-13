package com.match.handler;

import java.util.List;
import java.util.ListIterator;
import com.match.handler.impl.SituationHandlerImpl;
import com.match.model.Game;
import com.isl.model.Partnership;
import com.match.model.ResultType;

public class PressureSituationHandler extends SituationHandlerImpl {

	/**
	 * Constructor for {@link PressureSituationHandler}
	 * 
	 * @param game
	 */
	public PressureSituationHandler(Game game) {
		this.game = game;
		this.factors = game.getPressureFactors();
	}

	/**
	 * Returns pressure situation chance
	 * 
	 * @param partnerships
	 * @param resultType
	 * @return
	 */
	@Override
	public int getSituationalChance(List<Partnership> partnerships, int param, ResultType resultType) {

		int pressureConstant;
		int chance;
		pressureConstant = getPressureConstant(partnerships);
		chance = (int) ((0.5 + 0.2 * pressureConstant) * getChance(resultType));
		return chance;
	}

	/**
	 * 
	 * @param partnerships
	 * @return
	 */
	private int getPressureConstant(List<Partnership> partnerships) {

		int pressureConstant = 0;
		int current_index;
		current_index = partnerships.size();
		ListIterator<Partnership> iterator = partnerships.listIterator(current_index);
		while (iterator.hasPrevious()) {
			Partnership prev_partnership = iterator.previous();
			if (prev_partnership.getRuns() < game.getPressureThreshold()) {
				pressureConstant++;
				continue;
			} else {
				break;
			}
		}
		return pressureConstant;
	}
}
