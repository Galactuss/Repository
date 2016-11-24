package com.match.handler.impl;

import java.util.List;

import com.match.handler.SituationHandler;
import com.match.model.Factors;
import com.match.model.Game;
import com.isl.model.Partnership;
import com.match.model.ResultType;

public abstract class SituationHandlerImpl implements SituationHandler {

	protected Factors factors;
	protected Game game;
	
	/* (non-Javadoc)
	 * @see com.match.handler.impl.SituationHandler#getChance(com.match.model.ResultType)
	 */
	@Override
	public int getChance(ResultType resultType) {
		
		return factors.getChance(resultType);
	}

	public abstract int getSituationalChance(List<Partnership> partnerships, int runs_scored, ResultType resultType);

}
