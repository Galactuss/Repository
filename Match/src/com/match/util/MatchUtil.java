package com.match.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.isl.model.MatchPlayer;
import com.isl.model.Player;
import com.isl.model.Team;

/**
 * 
 * @author Pushpak
 *
 */
public class MatchUtil {
	
	public static final String GET= "get";
	public static final String SET = "set";
	public static final String NAME = "name";
	public static final String MATCHPLAYER = "matchPlayer";
	
	/**
	 * Sets parameter provided for all team players from given source class.
	 * Source class instance can itself be a parameter value or has a field or
	 * method that returns the parameter value.
	 * @param team {@link Team} whose player data is to be updated.
	 * @param attribute The Attribute to be updated.
	 * @param clazz Source class, which provides new values for the attribute.
	 */
	public static void set(Team team, String attribute, Class<?> clazz) {
		
		try {
			Field field = Player.class.getDeclaredField(attribute);
			if(field.getType().equals(clazz)) {
				Method setMethod = getMethod(Player.class, attribute, SET);
				team.getPlayers().forEach(player -> {
						try {
							Object newInstance  = clazz.newInstance();
							Object[] playerArgs = {newInstance};
							setMethod.invoke(player, playerArgs);
						} catch (Exception e) {
							e.printStackTrace();
						}
				});
			} else {
				Object instance  = clazz.newInstance();
				Method setterMethod = getMethod(Player.class, attribute, SET);
				Method getterMethod = getMethod(Player.class, NAME, GET);
				Method sourceMethod = getMethod(clazz, attribute, GET);
				team.getPlayers().forEach(player -> {
						try {
							String playerName = (String) getterMethod.invoke(player);
							Object[] playerDaoArgs = {playerName};
							Object returnVal = sourceMethod.invoke(instance, playerDaoArgs);
							Object[] playerArgs = {returnVal}; 
							setterMethod.invoke(player, playerArgs);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
			}
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates player attributes with given changeValue.
	 * @param player {@link Player} object whose attribute is to be changed.
	 * @param attribute Attribute of {@link Player} that is to be changed. 
	 * @param changeValue The change to be added to the attribute.
	 */
	public static void update(Player player, String attribute, int changeValue) {
		
		Object matchPlayer;
		int currentValue;
		int newValue;
		Method getterMethod = getMethod(MatchPlayer.class, attribute, GET);
		Method setterMethod = getMethod(MatchPlayer.class, attribute, SET);
		Method getterMatchPlayerMethod = getMethod(Player.class, MATCHPLAYER, GET);
		try {
			matchPlayer = getterMatchPlayerMethod.invoke(player);
			currentValue = (int) getterMethod.invoke(matchPlayer);
			newValue = currentValue + changeValue;
			Object[] parameters = {newValue};
			setterMethod.invoke(matchPlayer, parameters);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the method of class given for parameter and methodtype.
	 * @param clazz
	 * @param parameter
	 * @param methodType
	 * @return
	 */
	protected static Method getMethod(Class<?> clazz, String parameter, String methodType) {
		
		String methodName = methodType + parameter;
		Method[] methods = clazz.getDeclaredMethods();
		List<Method> methodsList = Arrays.asList(methods);
		
		Object[] finalMethod = methodsList.stream()
				.filter(method -> 
						method.getName().compareToIgnoreCase(methodName) == 0 ||
							method.getName().compareToIgnoreCase(methodName.replace("_", "")) == 0)
				.toArray();
		
		return (Method) finalMethod[0];
	}
	
}
