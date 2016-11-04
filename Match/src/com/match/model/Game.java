package com.match.model;

import java.util.List;

public class Game {

	protected int max_overs;
	protected Factors baseFactors;
	protected Factors balanceFactors;
	protected Factors pitchFactors;
	protected Factors freehitFactors;
	protected Factors pressureFactors;
	protected Factors reducedOversFactors;
	protected Factors requiredRunrateFactors;
	protected Factors settledFactors;
	protected Factors powerplayFactors;
	protected Factors deathOverFactors;
	protected double[] skillsFactors;
	protected List<Range> powerplays;
	protected int pressureThreshold;
	protected int requiredRunrateThreshold;
	protected int deathOverThreshold;
	protected int balacedOverThreshold;
	protected int balancedRunrateThreshold;

	public int getBalacedOverThreshold() {
		return balacedOverThreshold;
	}

	public int getBalancedRunrateThreshold() {
		return balancedRunrateThreshold;
	}

	public int getMax_overs() {
		return max_overs;
	}

	public Factors getBaseFactors() {
		return baseFactors;
	}

	public Factors getPowerplayFactors() {
		return powerplayFactors;
	}

	public Factors getDeathOverFactors() {
		return deathOverFactors;
	}

	public List<Range> getPowerplays() {
		return powerplays;
	}

	public Factors getBalanceFactors() {
		return balanceFactors;
	}

	public Factors getPitchFactors() {
		return pitchFactors;
	}

	public Factors getFreehitFactors() {
		return freehitFactors;
	}

	public Factors getPressureFactors() {
		return pressureFactors;
	}

	public Factors getReducedOversFactors() {
		return reducedOversFactors;
	}

	public Factors getRequiredRunrateFactors() {
		return requiredRunrateFactors;
	}

	public Factors getSettledFactors() {
		return settledFactors;
	}

	public int getPressureThreshold() {
		return pressureThreshold;
	}

	public int getRequiredRunrateThreshold() {
		return requiredRunrateThreshold;
	}

	public double[] getSkillsFactors() {
		return skillsFactors;
	}

	public int getDeathOverThreshold() {
		return deathOverThreshold;
	}

}
