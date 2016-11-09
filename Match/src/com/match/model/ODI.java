package com.match.model;

import java.util.ArrayList;

import com.isl.model.GameType;

public class ODI extends Game {
	
	{
		gameType = GameType.ODI;
		max_overs = 50;
		baseFactors = new Factors(10100, 12500, 5000, 300, 1500, 700, 300, 400);
		balanceFactors = new Factors(-1200, 200, 0, 100, 500, 0, 200, 100);
		deathOverFactors = new Factors(-1900, -100, -300, 100, 1800, 0, 200, 200);
		freehitFactors = new Factors(-1200, -2000, -500, 100, 2300, 0, 1600, -12000);
		pitchFactors = new Factors(3700, -1000, -1200, -200, -800, 0, -300, 200);
		powerplayFactors = new Factors(1000, -700, -1000, 100, 600, 0, 0, 100);
		pressureFactors = new Factors(1500, 300, -500, -200, -700, 0, -300, 100);
		reducedOversFactors = new Factors(0, -800, -800, 100, 900, 0, 400, 200);
		requiredRunrateFactors = new Factors(-2500, -700, 1000, 300, 1200, 0, 200, 100);
		settledFactors = new Factors(-2500, 700, 600, 100, 900, 0, 100, 100);
		skillsFactors = new double[]{-1.5, 0.15, 0.025, 0.4, 0.08, -0.07};
		powerplays = new ArrayList<Range>();
		powerplays.add(new Range(1, 10));
		powerplays.add(new Range(36, 40));
		pressureThreshold = 20;
		requiredRunrateThreshold = 8;
		deathOverThreshold = 44;
		balacedOverThreshold = 30;
		balancedRunrateThreshold = 4;
	}
}
