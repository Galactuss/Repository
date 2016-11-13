package com.match.handler;

import java.util.List;

import com.isl.model.Partnership;
import com.match.model.ResultType;

public interface SituationHandler {

	/**
	 * Returns the chance based on the result type
	 * @param resultType
	 * @return
	 */
	int getChance(ResultType resultType);

	/**
	 * Returns situational chance
	 * @param partnerships
	 * @param runs_scored
	 * @param resultType
	 * @return
	 */
	int getSituationalChance(List<Partnership> partnerships, int runs_scored, ResultType resultType);

}