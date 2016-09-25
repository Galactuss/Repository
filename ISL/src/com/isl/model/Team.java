package com.isl.model;

import java.util.List;
public class Team {
	
	private String name;
	private List<Player> players;
	private Player[] bowling_lineup;
	private int score = 0;
	private int wickets;
	
	

	public Player[] getBowling_lineup() {
		return bowling_lineup;
	}

	public void setBowling_lineup(Player[] bowling_lineup) {
		this.bowling_lineup = bowling_lineup;
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
