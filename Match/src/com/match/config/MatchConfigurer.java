package com.match.config;

public class MatchConfigurer {
	
	public static Object getInstance(String className) {
		
		Object instance = null;
		String fullClassName = null;
		Class<?> clazz;
		try {
			fullClassName = getFullClassName(className);
			clazz = Class.forName(fullClassName);
			instance = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static String getFullClassName(String className) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("com.match.service.");
		builder.append(className);
		return builder.toString();
		
	}
}
