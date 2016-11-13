package com.match.handler.impl;

import java.util.List;

import com.match.handler.SituationHandler;
import com.match.model.Factors;
import com.match.model.Game;
import com.isl.model.Partnership;
import com.match.model.ResultType;

public class SituationHandlerImpl implements SituationHandler {

	protected Factors factors;
	protected Game game;
	
	/* (non-Javadoc)
	 * @see com.match.handler.impl.SituationHandler#getChance(com.match.model.ResultType)
	 */
	@Override
	public int getChance(ResultType resultType) {
		
		switch (resultType) {
		case DOT:
			return factors.getDot_chance();
		case SINGLE:
			return factors.getSingle_chance();
		case DOUBLE:
			return factors.getDouble_chance();
		case TRIPLE:
			return factors.getTriple_chance();
		case FOUR:
			return factors.getFour_chance();
		case SIX:
			return factors.getSix_chance();
		case WICKET:
			return factors.getWicket_chance();
		}
		return 0;
	}

	@Override
	public int getSituationalChance(List<Partnership> partnerships, int runs_scored, ResultType resultType) {
		return 0;
	}

}
