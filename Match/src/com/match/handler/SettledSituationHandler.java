package com.match.handler;

import java.util.List;
import com.match.handler.impl.SituationHandlerImpl;
import com.match.model.Game;
import com.isl.model.Partnership;
import com.match.model.ResultType;

public class SettledSituationHandler extends SituationHandlerImpl {

	/**
	 * Returns Constructor for {@link SettledSituationHandler}
	 * 
	 * @param game
	 */
	public SettledSituationHandler(Game game) {
		this.game = game;
		this.factors = game.getSettledFactors();
	}

	/**
	 * Returns chance for settled case
	 * 
	 * @param partnership
	 * @param score
	 * @param resultType
	 * @return
	 */
	@Override
	public int getSituationalChance(List<Partnership> partnerships, int score, ResultType resultType) {

		int chance = 0;
		Partnership partnership;
		int curr_partnership;
		partnership = partnerships.get(partnerships.size() - 1);
		curr_partnership = partnership.getRuns();
		if (curr_partnership > 40 || curr_partnership > 49) {
			chance += getChance(resultType);
		}
		return chance;
	}
}
