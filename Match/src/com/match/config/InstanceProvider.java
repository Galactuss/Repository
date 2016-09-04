package com.match.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author PUSHPAK
 *
 */
public class InstanceProvider {

	static Map<Class<?>, Object> instances = null;

	/**
	 * Returns new instance of a class if not exist already
	 * @param <T>
	 * 
	 * @param clazz	class whose instance is needed
	 * @return instance of the class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz) {

		T instance = null;
		Map<Class<?>, Object> instanceMap = InstanceProvider.instances;
		if (instanceMap != null) {
			if (instanceMap.containsKey(clazz)) {
				instance = (T) instanceMap.get(clazz);
			}
		} else {
			InstanceProvider.instances = new HashMap<Class<?>, Object>();
			instanceMap = InstanceProvider.instances;
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
