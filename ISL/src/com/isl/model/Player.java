package com.isl.model;


public class Player implements Comparable<Player>{
	
	private int id;
	private int batting_skills;
	private int bowling_skills;
	private int position;
	private String name;
	private String role;
	private String bowling_type;
	private String country;
	private MatchPlayer matchPlayer;
	private String type; 

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBowling_type() {
		return bowling_type;
	}

	public void setBowling_type(String bowling_type) {
		this.bowling_type = bowling_type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getBatting_skills() {
		return batting_skills;
	}

	public void setBatting_skills(int batting_skills) {
		this.batting_skills = batting_skills;
	}

	public int getBowling_skills() {
		return bowling_skills;
	}

	public void setBowling_skills(int bowling_skills) {
		this.bowling_skills = bowling_skills;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public MatchPlayer getMatchPlayer() {
		return matchPlayer;
	}

	public void setMatchPlayer(MatchPlayer matchPlayer) {
		this.matchPlayer = matchPlayer;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public int compareTo(Player o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
