package com.match.util;

import java.util.List;

public class ListUtil {

	/**
	 * Shuffles list partially within the indices(inclusive)
	 * 
	 * @param list
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public static <T> List<T> shufflePartialList(List<T> list, int startIndex, int endIndex)
			throws ArrayIndexOutOfBoundsException {

		int len = list.size();
		int index;
		if ((index = startIndex) < 0 || (index = endIndex) >= len) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			for (int i = startIndex; i <= endIndex; i++) {
				int j = RandomUtil.next(startIndex, i + 1);
				swap(list, i, j);
			}
		}

		return list;
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
	protected static <T> List<T> swap(List<T> list, int firstSwapIndex, int secondSwapIndex)
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
