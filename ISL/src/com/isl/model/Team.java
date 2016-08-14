package com.isl.model;

import java.util.List;
import java.util.Map;

public class Team {
	
	private String name;
	private List<Player> players;
	private Map<Integer, Player> oversMap;
	private int score = 0;
	private int wickets;
	
	public Map<Integer, Player> getOversMap() {
		return oversMap;
	}

	public void setOversMap(Map<Integer, Player> oversMap) {
		this.oversMap = oversMap;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getWickets() {
		return wickets;
	}

	public void setWickets(int wickets) {
		this.wickets = wickets;
	}
}
