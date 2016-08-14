package com.match.handler;

import java.util.LinkedList;
import java.util.ListIterator;

import com.match.data.PressureConstants;
import com.match.model.ResultType;

public class PressureSituationHandler {
	
	private static int PRESSURE_THRESHOLD = 10;
	/**
	 * Returns chance based on case
	 * @param resultType
	 * @return
	 */
	public static int getChance(ResultType resultType) {
		
		switch(resultType) {
			case DOT: return PressureConstants.DOT_CHANCE;
			case SINGLE: return PressureConstants.SINGLE_CHANCE;
			case DOUBLE: return PressureConstants.DOUBLE_CHANCE;
			case TRIPLE: return PressureConstants.TRIPLE_CHANCE;
			case FOUR: return PressureConstants.FOUR_CHANCE;
			case SIX: return PressureConstants.SIX_CHANCE;
			case WICKET: return PressureConstants.WICKET_CHANCE;
		}
		return 0;
	}
	
	/**
	 * Returns pressure situation chance
	 * @param partnerships
	 * @param resultType
	 * @return
	 */
	public static int getPressureChance(LinkedList<Integer> partnerships, ResultType resultType) {
		
		int pressureConstant;
		int chance;
		pressureConstant = getPressureConstant(partnerships);
		chance = (int) ((0.5 + 0.2*pressureConstant)*getChance(resultType));
		return chance;
	}
	
	/**
	 * 
	 * @param partnerships
	 * @return
	 */
	public static int getPressureConstant(LinkedList<Integer> partnerships) {
		
		int pressureConstant = 0;
		int current_index;
		try {
			current_index = partnerships.indexOf(partnerships.getLast());
		} catch (Exception e) {
			current_index = partnerships.size() - 1;
		}
		ListIterator<Integer> iterator = partnerships.listIterator(current_index);
		while(iterator.hasPrevious()) {
			Integer prev_partnership = iterator.previous();
			if(prev_partnership == null || prev_partnership < PRESSURE_THRESHOLD) {
				pressureConstant++;
				continue;
			} else {
				break;
			}
		}
		return pressureConstant;
	}
}
