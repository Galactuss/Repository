package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {

	/**
	 * Shuffles list partially within the indices
	 * 
	 * @param list
	 * @param startIndex (inclusive)
	 * @param endIndex (exclusive)
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static <T> List<T> shufflePartialList(List<T> list, int startIndex, int endIndex)
			throws ArrayIndexOutOfBoundsException {

		int len = list.size();
		int index;
		if ((index = startIndex) < 0 || (index = endIndex) > len) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			for (int i = startIndex; i < endIndex; i++) {
				int j = RandomUtil.next(startIndex, i + 1);
				swap(list, i, j);
			}
		}

		return list;
	}

	/**
	 * Picks randomly n elements from the given list
	 * @param n
	 * @param list
	 * @return
	 */
	public static <T> List<T> pickRandom(int n, List<T> list) {
		
		int len = list.size();
		if (n > len) {
			throw new IllegalArgumentException();
		} else {
			List<T> newList = new ArrayList<T>(list);
			Collections.shuffle(newList);
			return list.subList(0, n);
		}
			
	}
	
	/**
	 * Helper function to swap elements at given indices in list
	 * 
	 * @param list
	 * @param firstSwapIndex
	 * @param secondSwapIndex
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private static <T> List<T> swap(List<T> list, int firstSwapIndex, int secondSwapIndex)
			throws ArrayIndexOutOfBoundsException {

		int len = list.size();
		int index;
		if ((index = firstSwapIndex) < 0 || (index = secondSwapIndex) >= len || (index = secondSwapIndex) < 0
				|| (index = firstSwapIndex) >= len) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			T helper = list.get(firstSwapIndex);
			list.set(firstSwapIndex, list.get(secondSwapIndex));
			list.set(secondSwapIndex, helper);
		}

		return list;
	}
}
