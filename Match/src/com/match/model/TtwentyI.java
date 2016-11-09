package com.match.model;

import java.util.ArrayList;

import com.isl.model.GameType;

public class TtwentyI extends Game {
		
	{
		gameType = GameType.T20I;
		max_overs = 20;
		baseFactors = new Factors(3500, 4500, 2500, 200, 800, 500, 200, 300);
		balanceFactors = new Factors(-1200, 200, 0, 100, 500, 0, 200, 100);
		deathOverFactors = new Factors(-1000, -200, -300, 100, 900, 0, 300, 200);
		freehitFactors = new Factors(-1200, -2000, -500, 100, 2300, 0, 1600, -12000);
		pitchFactors = new Factors(1500, -100, -400, -100, -400, 0, -300, 200);
		powerplayFactors = new Factors(800, -700, -800, 100, 400, 0, 100, 100);
		pressureFactors = new Factors(700, 300, -100, -100, -400, 0, -300, 100);
		reducedOversFactors = new Factors(0, -800, -800, 100, 900, 0, 400, 200);
		requiredRunrateFactors = new Factors(-1500, -500, 1000, 300, 300, 0, 200, 200);
		settledFactors = new Factors(-2400, 700, 600, 100, 800, 0, 200, 0);
		skillsFactors = new double[]{-1, 0.33, 0.033, 0.25, 0.083, 0.05};
		powerplays = new ArrayList<Range>(2);
		powerplays.add(new Range(1, 6));
		pressureThreshold = 10;
		requiredRunrateThreshold = 10;
		deathOverThreshold = 16;
		balacedOverThreshold = 14;
		balancedRunrateThreshold = 6;
	}
}
