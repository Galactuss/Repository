package com.match.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author PUSHPAK
 *
 */
public class MatchConfigurer {

	static Map<Class<?>, Object> instances = null;

	/**
	 * Returns new instance of a class if not exist already
	 * 
	 * @param clazz	class whose instance is needed
	 * @return instance of the class
	 */
	public static Object getInstance(Class<?> clazz) {

		Object instance = null;
		Map<Class<?>, Object> instanceMap = MatchConfigurer.instances;
		if (instanceMap != null) {
			if (instanceMap.containsKey(clazz)) {
				return instanceMap.get(clazz);
			}
		} else {
			MatchConfigurer.instances = new HashMap<Class<?>, Object>();
			instanceMap = MatchConfigurer.instances;
		}
		try {
			instance = clazz.newInstance();
			instanceMap.put(clazz, instance);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return instance;
	}
}
