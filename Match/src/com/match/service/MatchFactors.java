package com.match.service;

import java.util.List;

import com.isl.model.Player;
import com.match.data.SuperOverFactors;
import com.match.handler.SkillDataHandler;
import com.match.model.Game;
import com.isl.model.Partnership;
import com.match.model.ResultType;

/**
 * 
 * @author Pushpak
 *
 */
public class MatchFactors {

	private boolean powerplay_factor = false;
	private boolean death_overs_factor = false;
	private boolean pitch_factor = false;
	private boolean required_runrate_factor = false;
	private boolean freehit_factor = false;
	private boolean reduced_overs_factor = false;
	private boolean super_over_factor = false;
	private boolean balance_factor = false;
	
	public boolean isBalance_factor() {
		return balance_factor;
	}

	public void setBalance_factor(boolean balance_factor) {
		this.balance_factor = balance_factor;
	}

	public boolean isSuper_over_factor() {
		return super_over_factor;
	}

	public void setSuper_over_factor(boolean super_over_factor) {
		this.super_over_factor = super_over_factor;
	}

	public boolean isReduced_overs_factor() {
		return reduced_overs_factor;
	}

	public void setReduced_overs_factor(boolean reduced_overs_factor) {
		this.reduced_overs_factor = reduced_overs_factor;
	}

	public boolean isFreehit_factor() {
		return freehit_factor;
	}

	public void setFreehit_factor(boolean freehit_factor) {
		this.freehit_factor = freehit_factor;
	}

	public boolean isPowerplay_factor() {
		return powerplay_factor;
	}

	public void setPowerplay_factor(boolean powerplay_factor) {
		this.powerplay_factor = powerplay_factor;
	}

	public boolean isDeath_overs_factor() {
		return death_overs_factor;
	}

	public void setDeath_overs_factor(boolean death_overs_factor) {
		this.death_overs_factor = death_overs_factor;
	}

	public boolean isPitch_factor() {
		return pitch_factor;
	}

	public void setPitch_factor(boolean pitch_factor) {
		this.pitch_factor = pitch_factor;
	}

	public boolean isRequired_runrate_factor() {
		return required_runrate_factor;
	}

	public void setRequired_runrate_factor(boolean required_runrate_factor) {
		this.required_runrate_factor = required_runrate_factor;
	}

	public int getDotFactors(Player batsman, Player bowler, List<Partnership> partnerships, Game game) {
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getDot_chance();
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getDot_chance();
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getDot_chance();
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getDot_chance();
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getDot_chance();
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getDot_chance();
		}
		if (super_over_factor) {
			effectiveFactor += SuperOverFactors.DOT_CHANCE;
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getDot_chance();
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				ResultType.DOT);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, ResultType.DOT);
		effectiveFactor += SkillDataHandler.dotChance(batsman, bowler, game);
		return effectiveFactor;
	}

	public int getSingleFactors(Player batsman, Player bowler, List<Partnership> partnerships, Game game) {
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getSingle_chance();
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getSingle_chance();
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getSingle_chance();
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getSingle_chance();
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getSingle_chance();
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getSingle_chance();
		}
		if (super_over_factor) {
			effectiveFactor += SuperOverFactors.SINGLE_CHANCE;
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getSingle_chance();
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				ResultType.SINGLE);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, ResultType.SINGLE);
		effectiveFactor += SkillDataHandler.singleChance(batsman, bowler, game);
		return effectiveFactor;
	}

	public int getDoubleFactors(Player batsman, Player bowler, List<Partnership> partnerships, Game game) {
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getDouble_chance();
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getDouble_chance();
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getDouble_chance();
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getDouble_chance();
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getDouble_chance();
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getDouble_chance();
		}
		if (super_over_factor) {
			effectiveFactor += SuperOverFactors.DOUBLE_CHANCE;
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getDouble_chance();
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				ResultType.DOUBLE);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, ResultType.DOUBLE);
		effectiveFactor += SkillDataHandler.doubleChance(batsman, bowler, game);
		return effectiveFactor;
	}

	public int getTripleFactors(Player batsman, Player bowler, List<Partnership> partnerships, Game game) {
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getTriple_chance();
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getTriple_chance();
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getTriple_chance();
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getTriple_chance();
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getTriple_chance();
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getTriple_chance();
		}
		if (super_over_factor) {
			effectiveFactor += SuperOverFactors.TRIPLE_CHANCE;
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getTriple_chance();
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				ResultType.TRIPLE);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, ResultType.TRIPLE);
		effectiveFactor += SkillDataHandler.tripleChance(batsman, bowler, game);
		return effectiveFactor;
	}

	public int getFourFactors(Player batsman, Player bowler, List<Partnership> partnerships, Game game) {
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getFour_chance();
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getFour_chance();
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getFour_chance();
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getFour_chance();
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getFour_chance();
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getFour_chance();
		}
		if (super_over_factor) {
			effectiveFactor += SuperOverFactors.FOUR_CHANCE;
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getFour_chance();
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				ResultType.FOUR);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, ResultType.FOUR);
		effectiveFactor += SkillDataHandler.fourChance(batsman, bowler, game);
		return effectiveFactor;
	}

	public int getSixFactors(Player batsman, Player bowler, List<Partnership> partnerships, Game game) {
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getSix_chance();
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getSix_chance();
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getSix_chance();
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getSix_chance();
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getSix_chance();
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getSix_chance();
		}
		if (super_over_factor) {
			effectiveFactor += SuperOverFactors.SIX_CHANCE;
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getSix_chance();
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				ResultType.SIX);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, ResultType.SIX);
		effectiveFactor += SkillDataHandler.sixChance(batsman, bowler, game);
		return effectiveFactor;
	}

	public int getWicketFactors(Player batsman, Player bowler, List<Partnership> partnerships, Game game) {
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getWicket_chance();
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getWicket_chance();
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getWicket_chance();
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getWicket_chance();
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getWicket_chance();
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getWicket_chance();
		}
		if (super_over_factor) {
			effectiveFactor += SuperOverFactors.WICKET_CHANCE;
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getWicket_chance();
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				ResultType.WICKET);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, ResultType.WICKET);
		effectiveFactor += SkillDataHandler.wicketChance(batsman, bowler, game);
		return effectiveFactor;
	}

}
