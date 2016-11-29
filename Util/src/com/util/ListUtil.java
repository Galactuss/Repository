package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtil {

	/**
	 * Shuffles list partially within the indices
	 * 
	 * @param list
	 * @param startIndex
	 *            (inclusive)
	 * @param endIndex
	 *            (exclusive)
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
	 * 
	 * @param n
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T>[] pickRandom(int n, List<T> list) {

		int len = list.size();
		if (n > len) {
			throw new IllegalArgumentException();
		} else {
			List<T> res = new ArrayList<T>(n);
			List<T> old = new ArrayList<T>(len - n);
			Random randomGenerator = InstanceProvider.getInstance(Random.class);
			int[] arr = new int[len];
			int rnd = 2;
			int chk;
			int index = 0;
			int cnt = 0;
			while (cnt < n) {
				if (arr[index] != 1) {
					chk = randomGenerator.nextInt(rnd);
					if (chk != rnd - 1) {
						arr[index] = 1;
						cnt++;
					}
				}
				index++;
				if (index == len) {
					index = 0;
					if (n >= len / 2) {
						rnd++;
					}
				}
			}
			return (List<T>[]) new List[] { res, old };
		}

	}

	/**
	 * Replaces occurrences of an object with the given value stating from
	 * startth occurrence to endth occurrence. If list has less than end
	 * occurrences of the given object then only those are replaced.
	 * 
	 * @param t
	 *            object to be replaced
	 * @param replacement
	 *            object to be replaced with
	 * @param list
	 *            list
	 * @param start
	 *            start occurrence
	 * @param end
	 *            end occurrence
	 * @throws IllegalArgumentException
	 *             when start is greater than end
	 */
	public static <T> void replaceOccurrences(T t, T replacement, List<T> list, int start, int end)
			throws IllegalArgumentException, ArrayIndexOutOfBoundsException {

		if (start > end) {
			throw new IllegalArgumentException("start cannot be greater than end");
		}
		int occurrences = 0;
		int index = 0;
		for (T elem : list) {
			if ((elem != null && elem.equals(t)) || (elem == null && t == null)) {
				occurrences++;
				if (occurrences >= start && occurrences <= end) {
					list.set(index, replacement);
				}
				if (occurrences == end) {
					return;
				}
			}
			index++;
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
