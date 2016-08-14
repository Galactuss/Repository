package com.cricket.model;

import java.util.ArrayList;
import java.util.List;

public class Squad {
	
	private List<String> overseas_players;
	private List<String> indian_capped_players;
	private List<String> domestic_players;
	private List<String> associate_players;
	private String name;

	public List<String> getPlayers() {
		List<String> players = new ArrayList<String>();
		players.addAll(overseas_players);
		players.addAll(associate_players);
		players.addAll(domestic_players);
		players.addAll(indian_capped_players);
		return players;
	}

	
	public List<String> getAssociate_players() {
		return associate_players;
	}

	public void setAssociate_players(List<String> associate_players) {
		this.associate_players = associate_players;
	}
	
	public List<String> getDomestic_players() {
		return domestic_players;
	}

	public void setDomestic_players(List<String> domestic_players) {
		this.domestic_players = domestic_players;
	}
	
	public List<String> getIndian_capped_players() {
		return indian_capped_players;
	}

	public void setIndian_capped_players(List<String> indian_capped_players) {
		this.indian_capped_players = indian_capped_players;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getoverseas_players() {
		return overseas_players;
	}

	public void setoverseas_players(List<String> overseas_players) {
		this.overseas_players = overseas_players;
	}
}
