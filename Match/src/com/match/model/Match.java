package com.match.model;

import com.isl.model.Player;
import com.isl.model.Team;
import com.match.service.MatchFactors;

public class Match {
	
	private Team batting_team;
	private Team bowling_team;
	private String pitch_type;
	private String tossWinner;
	private Team winner;
	private Player man_of_the_match;
	private int batting_team_overs;
	private int bowling_team_overs;
	private boolean freehit = false;
	private MatchFactors matchFactors;
	
	public MatchFactors getMatchFactors() {
		return matchFactors;
	}

	public void setMatchFactors(MatchFactors matchFactors) {
		this.matchFactors = matchFactors;
	}

	public boolean isFreehit() {
		return freehit;
	}

	public void setFreehit(boolean freehit) {
		this.freehit = freehit;
	}

	public int getBatting_team_overs() {
		return batting_team_overs;
	}

	public void setBatting_team_overs(int batting_team_overs) {
		this.batting_team_overs = batting_team_overs;
	}

	public int getBowling_team_overs() {
		return bowling_team_overs;
	}

	public void setBowling_team_overs(int bowling_team_overs) {
		this.bowling_team_overs = bowling_team_overs;
	}

	public Team getBatting_team() {
		return batting_team;
	}
	
	public void setBatting_team(Team batting_team) {
		this.batting_team = batting_team;
	}
	
	public Team getBowling_team() {
		return bowling_team;
	}
	
	public void setBowling_team(Team bowling_team) {
		this.bowling_team = bowling_team;
	}
	
	public String getPitch_type() {
		return pitch_type;
	}
	
	public void setPitch_type(String pitch_type) {
		this.pitch_type = pitch_type;
	}
	
	public String getTossWinner() {
		return tossWinner;
	}
	
	public void setTossWinner(String tossWinner) {
		this.tossWinner = tossWinner;
	}
	
	public Team getWinner() {
		return winner;
	}
	
	public void setWinner(Team winner) {
		this.winner = winner;
	}
	
	public Player getMan_of_the_match() {
		return man_of_the_match;
	}
	
	public void setMan_of_the_match(Player man_of_the_match) {
		this.man_of_the_match = man_of_the_match;
	}
}
