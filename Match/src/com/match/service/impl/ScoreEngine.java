package com.match.service.impl;

import java.util.LinkedList;

import com.isl.model.Player;
import com.isl.model.Team;
import com.match.model.Extra;
import com.match.service.MatchFactors;

public interface ScoreEngine {

	int getResultForDelievery(Player batsman, Player bowler, MatchFactors matchFactors, int score, int wickets,
			LinkedList<Integer> partnerships);

	boolean dotChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships);

	boolean singleChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships);

	boolean doubleChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships);

	boolean tripleChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships);

	boolean fourChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships);

	boolean extraChance(MatchFactors matchFactors);

	boolean sixChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships);

	boolean wicketChance(MatchFactors matchFactors, Player batsman, Player bowler, int score, int wickets,
			LinkedList<Integer> partnerships);

	Extra getTypeOfExtra(Player batsman, Player bowler, MatchFactors matchFactors, int score, int wickets,
			LinkedList<Integer> partnerships);

	int getWideRuns();

	int getLegByes();

	int getNoballRuns(Player batsman, Player bowler, MatchFactors matchFactors, int score, int wickets,
			LinkedList<Integer> partnerships);

	String getTypeOfWicket(Player batsman, Player bowler, Team bowling_team);

	Player getRandomPlayer(Team team);

	float getRunrate(int runs, int overs);

	float getStrikerate(int runs, int balls);

	float getEconomy(int runs, int balls);

}