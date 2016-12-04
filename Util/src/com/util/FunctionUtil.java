package com.util;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 
 * @author PUSHPAK
 *
 */
public class FunctionUtil {

	/**
	 * Performs a certain task for given number of times
	 * 
	 * @param N
	 *            number of times the task should be performed
	 * @param func
	 *            task to be performed
	 */
	public static void times(int N, Consumer<Integer> func) {
		for (int i = 0; i < N; i++) {
			func.accept(i);
		}
	}

	/**
	 * Performs a certain task for given number of times until predicate is true
	 * 
	 * @param N
	 *            number of times the task to be performed
	 * @param func
	 *            the task to be performed
	 * @param predicate
	 *            predicate condition to be checked
	 */
	public static void times(int N, Consumer<Integer> func, Predicate<Integer> predicate) {
		for (int i = 0; i < N; i++) {
			if (predicate.test(i)) {
				func.accept(i);
			} else {
				break;
			}
		}
	}

	/**
	 * Performs a certain task for given number of times until predicate is
	 * true. It goes one step back based on the stepback condition
	 * 
	 * @param N
	 *            number of times the task to be performed
	 * @param func
	 *            the task to be performed
	 * @param predicate
	 *            predicate condition to be checked
	 * @param stepback
	 *            stepback condition to be checked
	 */
	public static void times(int N, Consumer<Integer> func, Predicate<Integer> predicate, Predicate<Integer> stepback) {
		for (int i = 0; i < N; i++) {
			if (predicate.test(i)) {
				func.accept(i);
			} else {
				break;
			}
			if (stepback.test(i)) {
				i--;
			}
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
