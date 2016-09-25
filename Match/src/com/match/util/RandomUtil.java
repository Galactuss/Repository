package com.match.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author PUSHPAK
 *
 */
public class RandomUtil {

	/**
	 * Private constructor
	 */
	private RandomUtil() {

	}

	/**
	 * Return instance of static class RandomGenerator
	 * 
	 * @param bound
	 * @return
	 */
	public static RandomUtil.RandomGenerator getRandomGenerator(int bound) {
		return new RandomUtil.RandomGenerator(bound);
	}

	/**
	 * 
	 * @author PUSHPAK
	 *
	 */
	public static class RandomGenerator {
		
		private List<Integer> list;
		private int currIndex;

		/**
		 * Constructor
		 * 
		 * @param bound
		 */
		public RandomGenerator(int bound) {
			this.list = new ArrayList<Integer>(bound);
			for(int i=0; i<bound; i++) {
				this.list.add(i+1);
			}
			this.currIndex = 0;
			Collections.shuffle(this.list);
		}

		/**
		 * Returns unique random number between 1 and bound
		 * 
		 * @return
		 */
		public int generateUniqueRandom() {
			if(this.currIndex == this.list.size()) {
				Collections.shuffle(this.list);
				this.currIndex = 0;
			}
			return this.list.get(this.currIndex++);
		}
	}
}
