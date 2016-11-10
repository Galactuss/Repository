package com.cricket.util;

import java.util.function.Consumer;

/**
 * 
 * @author PUSHPAK
 *
 */
public class A {

	/**
	 * Performs a certain task for given number of times
	 * 
	 * @param N
	 *            number of times the task should be performed
	 * @param func
	 *            task to be performed
	 */
	public static void forEach(int N, Consumer<Integer> func) {
		for (int i = 0; i < N; i++) {
			func.accept(0);
		}
	}

	/**
	 * Performs a certain task on each element of an array
	 * 
	 * @param array
	 *            array
	 * @param func
	 *            task to be performed]
	 */
	public static <R> void forEach(R[] array, Consumer<R> func) {
		for (R r : array) {
			func.accept(r);
		}
	}
}
