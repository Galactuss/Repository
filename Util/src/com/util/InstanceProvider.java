package com.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import com.util.model.InstanceModel;

/**
 * 
 * @author PUSHPAK
 *
 */
public class InstanceProvider {

	static Map<InstanceModel, Object> instances = null;

	/**
	 * Private constructor
	 */
	private InstanceProvider() {

	}

	/**
	 * Returns new instance of a class if not exist already
	 * 
	 * @param <T>
	 * 
	 * @param clazz
	 *            class whose instance is needed
	 * @param args
	 *            class constructor parameters
	 * @return instance of the class
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T getInstance(Class<T> clazz, Object... args) {

		T instance = null;
		InstanceModel instanceModel = new InstanceModel(clazz, args);
		Map<InstanceModel, Object> instanceMap = InstanceProvider.instances;
		if (instanceMap != null) {
			if (instanceMap.containsKey(instanceModel)) {
				return (T) instanceMap.get(instanceModel);
			}
		} else {
			InstanceProvider.instances = new HashMap<InstanceModel, Object>();
			instanceMap = InstanceProvider.instances;
		}
		try {
			Class<?>[] argClasses = new Class<?>[args.length];
			int index = 0;
			for (Object arg : args) {
				argClasses[index++] = arg.getClass();
			}
			instance = clazz.getDeclaredConstructor(argClasses).newInstance(args);
			instanceMap.put(instanceModel, instance);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return instance;
	}
}
