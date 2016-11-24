package com.cricket.model;

/**
 * 
 * @author PUSHPAK
 *
 */
public class Country {

	private String name;
	private String type;
	private String[] players;

	public Country(String name, String type, String[] players) {
		this.name = name;
		this.type = type;
		this.players = players;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String[] getPlayers() {
		return players;
	}

}
