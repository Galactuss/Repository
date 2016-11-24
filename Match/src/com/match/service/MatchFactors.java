package com.match.service;

import java.util.List;

import com.isl.model.Player;
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

	public int getFactors(Player batsman, Player bowler, List<Partnership> partnerships, ResultType resultType) {
		Game game = MatchEngine.game;
		int effectiveFactor = 0;
		if (powerplay_factor) {
			effectiveFactor += game.getPowerplayFactors().getChance(resultType);
		}
		if (death_overs_factor) {
			effectiveFactor += game.getDeathOverFactors().getChance(resultType);
		}
		if (pitch_factor) {
			effectiveFactor += game.getPitchFactors().getChance(resultType);
		}
		if (required_runrate_factor) {
			effectiveFactor += game.getRequiredRunrateFactors().getChance(resultType);
		}
		if (freehit_factor) {
			effectiveFactor += game.getFreehitFactors().getChance(resultType);
		}
		if (reduced_overs_factor) {
			effectiveFactor += game.getReducedOversFactors().getChance(resultType);
		}
		if (super_over_factor) {
			effectiveFactor += game.getSuperoverFactors().getChance(resultType);
		}
		if (balance_factor) {
			effectiveFactor += game.getBalanceFactors().getChance(resultType);
		}
		effectiveFactor += MatchEngine.settledSituationHandler.getSituationalChance(partnerships, batsman.getMatchPlayer().getRuns_scored(),
				resultType);
		effectiveFactor += MatchEngine.pressureSituationHandler.getSituationalChance(partnerships, 0, resultType);
		effectiveFactor += SkillDataHandler.getChance(batsman, bowler, game, resultType);
		return effectiveFactor;
	}

}
