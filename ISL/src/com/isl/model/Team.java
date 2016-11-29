package com.isl.model;

import java.util.List;

public class Team {

	private String name;
	private List<Player> players;
	private List<Player> batting_lineup;
	private List<Player> bowling_lineup;
	private int score = 0;
	private int wickets;
	private String overs;
	private Player wicket_keeper;
	private List<Partnership> partnerships;
	private Player reserved;

	public Player getReserved() {
		return reserved;
	}

	public void setReserved(Player reserved) {
		this.reserved = reserved;
	}

	public Player getWicket_keeper() {
		return wicket_keeper;
	}

	public void setWicket_keeper(Player wicket_keeper) {
		this.wicket_keeper = wicket_keeper;
	}

	public List<Player> getBatting_lineup() {
		return batting_lineup;
	}

	public void setBatting_lineup(List<Player> batting_lineup) {
		this.batting_lineup = batting_lineup;
	}

	public List<Player> getBowling_lineup() {
		return bowling_lineup;
	}

	public void setBowling_lineup(List<Player> bowling_lineup) {
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

	public String getOvers() {
		return overs;
	}

	public void setOvers(String overs) {
		this.overs = overs;
	}

	public List<Partnership> getPartnerships() {
		return partnerships;
	}

	public void setPartnerships(List<Partnership> partnerships) {
		this.partnerships = partnerships;
	}
	
}
