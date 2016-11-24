package com.match.model;

import java.util.List;

import com.isl.model.Partnership;
import com.isl.model.Player;
import com.match.service.MatchFactors;

public class ScoreEnginePayLoad {

	private Player batsman;
	private Player bowler;
	private MatchFactors matchFactors;
	private int score;
	private int wickets;
	private List<Partnership> partnerships;

	public ScoreEnginePayLoad(Player batsman, Player bowler, MatchFactors matchFactors, int score, int wickets,
			List<Partnership> partnerships) {
		super();
		this.batsman = batsman;
		this.bowler = bowler;
		this.matchFactors = matchFactors;
		this.score = score;
		this.wickets = wickets;
		this.partnerships = partnerships;
	}

	public Player getBatsman() {
		return batsman;
	}

	public Player getBowler() {
		return bowler;
	}

	public MatchFactors getMatchFactors() {
		return matchFactors;
	}

	public int getScore() {
		return score;
	}

	public int getWickets() {
		return wickets;
	}

	public List<Partnership> getPartnerships() {
		return partnerships;
	}
}
