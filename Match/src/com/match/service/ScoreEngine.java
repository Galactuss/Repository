package com.match.service;

import com.isl.model.Player;
import com.isl.model.Team;
import com.match.model.Extra;
import com.match.model.ResultType;
import com.match.model.ScoreEnginePayLoad;
import com.match.model.Wicket;

public interface ScoreEngine {

	int getResultForDelievery(ScoreEnginePayLoad payLoad);

	boolean getChance(ScoreEnginePayLoad payLoad, ResultType resultType);

	Extra getTypeOfExtra(ScoreEnginePayLoad payLoad);

	int getWideRuns();

	int getLegByes();

	Wicket getTypeOfWicket(Player batsman, Player bowler, Team bowling_team);

	Player getRandomPlayer(Team team);

	float getRunrate(int runs, int overs);

	float getStrikerate(int runs, int balls);

	float getEconomy(int runs, int balls);

	int getNoballRuns(ScoreEnginePayLoad payLoad);

}