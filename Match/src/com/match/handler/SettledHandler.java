package com.match.handler;

import java.util.LinkedList;

import com.match.data.SettledConstants;
import com.match.model.ResultType;

public class SettledHandler {
	
	/**
	 * Returns chance based on case
	 * @param resultType
	 * @return
	 */
	public static int getChance(ResultType resultType) {
		
		switch(resultType) {
			case DOT: return SettledConstants.DOT_CHANCE;
			case SINGLE: return SettledConstants.SINGLE_CHANCE;
			case DOUBLE: return SettledConstants.DOUBLE_CHANCE;
			case TRIPLE: return SettledConstants.TRIPLE_CHANCE;
			case FOUR: return SettledConstants.FOUR_CHANCE;
			case SIX: return SettledConstants.SIX_CHANCE;
			case WICKET: return SettledConstants.WICKET_CHANCE;
		}
		return 0;
	}
	
	/**
	 * Returns chance for settled case
	 * @param partnership
	 * @param score
	 * @param resultType
	 * @return
	 */
	public static int getSettledChance(LinkedList<Integer> partnerships, int score, ResultType resultType) {
		
		int chance = 0;
		int partnership;
		try {
			partnership = partnerships.getLast();
		} catch (Exception e) {
			partnership = 0;
		}
		if(partnership > 40 || score > 49) {
			chance += getChance(resultType);
		}
		return chance;
	}
}
