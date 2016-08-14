package com.match.service;


import java.util.LinkedList;

import com.isl.model.Player;
import com.match.data.BalanceConstants;
import com.match.data.DeathOversFactors;
import com.match.data.FreeHitFactors;
import com.match.data.PitchFactors;
import com.match.data.PowerPlayFactors;
import com.match.data.ReducedOversFactors;
import com.match.data.RequiredRunrateFactors;
import com.match.data.SuperOverFactors;
import com.match.handler.PressureSituationHandler;
import com.match.handler.SettledHandler;
import com.match.handler.SkillDataHandler;
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
	
	public int getDotFactors(Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		int effectiveFactor = 0;
		if(powerplay_factor) {
			effectiveFactor += PowerPlayFactors.DOT_CHANCE;
		}
		if(death_overs_factor) {
			effectiveFactor += DeathOversFactors.DOT_CHANCE;
		}
		if(pitch_factor) {
			effectiveFactor += PitchFactors.DOT_CHANCE;
		}
		if(required_runrate_factor) {
			effectiveFactor += RequiredRunrateFactors.DOT_CHANCE;
		}
		if(freehit_factor) {
			effectiveFactor += FreeHitFactors.DOT_CHANCE;
		}
		if(reduced_overs_factor) {
			effectiveFactor += ReducedOversFactors.DOT_CHANCE;
		}
		if(super_over_factor) {
			effectiveFactor += SuperOverFactors.DOT_CHANCE;
		}
		if(balance_factor) {
			effectiveFactor += BalanceConstants.DOT_CHANCE;
		}
		effectiveFactor += SettledHandler.getSettledChance(partnerships, batsman.getMatchPlayer().getRuns_scored(), ResultType.DOT);
		effectiveFactor += PressureSituationHandler.getPressureChance(partnerships, ResultType.DOT);
		effectiveFactor += SkillDataHandler.dotChance(batsman, bowler);
		return effectiveFactor;
	}
	
	public int getSingleFactors(Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		int effectiveFactor = 0;
		if(powerplay_factor) {
			effectiveFactor += PowerPlayFactors.SINGLE_CHANCE;
		}
		if(death_overs_factor) {
			effectiveFactor += DeathOversFactors.SINGLE_CHANCE;
		}
		if(pitch_factor) {
			effectiveFactor += PitchFactors.SINGLE_CHANCE;
		}
		if(required_runrate_factor) {
			effectiveFactor += RequiredRunrateFactors.SINGLE_CHANCE;
		}
		if(freehit_factor) {
			effectiveFactor += FreeHitFactors.SINGLE_CHANCE;
		}
		if(reduced_overs_factor) {
			effectiveFactor += ReducedOversFactors.SINGLE_CHANCE;
		}
		if(super_over_factor) {
			effectiveFactor += SuperOverFactors.SINGLE_CHANCE;
		}
		if(balance_factor) {
			effectiveFactor += BalanceConstants.SINGLE_CHANCE;
		}
		effectiveFactor += SettledHandler.getSettledChance(partnerships, batsman.getMatchPlayer().getRuns_scored(), ResultType.SINGLE);
		effectiveFactor += PressureSituationHandler.getPressureChance(partnerships, ResultType.SINGLE);
		effectiveFactor += SkillDataHandler.singleChance(batsman, bowler);
		return effectiveFactor;
	}
	
	public int getDoubleFactors(Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		int effectiveFactor = 0;
		if(powerplay_factor) {
			effectiveFactor += PowerPlayFactors.DOUBLE_CHANCE;
		}
		if(death_overs_factor) {
			effectiveFactor += DeathOversFactors.DOUBLE_CHANCE;
		}
		if(pitch_factor) {
			effectiveFactor += PitchFactors.DOUBLE_CHANCE;
		}
		if(required_runrate_factor) {
			effectiveFactor += RequiredRunrateFactors.DOUBLE_CHANCE;
		}
		if(freehit_factor) {
			effectiveFactor += FreeHitFactors.DOUBLE_CHANCE;
		}
		if(reduced_overs_factor) {
			effectiveFactor += ReducedOversFactors.DOUBLE_CHANCE;
		}
		if(super_over_factor) {
			effectiveFactor += SuperOverFactors.DOUBLE_CHANCE;
		}
		if(balance_factor) {
			effectiveFactor += BalanceConstants.DOUBLE_CHANCE;
		}
		effectiveFactor += SettledHandler.getSettledChance(partnerships, batsman.getMatchPlayer().getRuns_scored(), ResultType.DOUBLE);
		effectiveFactor += PressureSituationHandler.getPressureChance(partnerships, ResultType.DOUBLE);
		effectiveFactor += SkillDataHandler.doubleChance(batsman, bowler);
		return effectiveFactor;
	}
	
	public int getTripleFactors(Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		int effectiveFactor = 0;
		if(powerplay_factor) {
			effectiveFactor += PowerPlayFactors.TRIPLE_CHANCE;
		}
		if(death_overs_factor) {
			effectiveFactor += DeathOversFactors.TRIPLE_CHANCE;
		}
		if(pitch_factor) {
			effectiveFactor += PitchFactors.TRIPLE_CHANCE;
		}
		if(required_runrate_factor) {
			effectiveFactor += RequiredRunrateFactors.TRIPLE_CHANCE;
		}
		if(freehit_factor) {
			effectiveFactor += FreeHitFactors.TRIPLE_CHANCE;
		}
		if(reduced_overs_factor) {
			effectiveFactor += ReducedOversFactors.TRIPLE_CHANCE;
		}
		if(super_over_factor) {
			effectiveFactor += SuperOverFactors.TRIPLE_CHANCE;
		}
		if(balance_factor) {
			effectiveFactor += BalanceConstants.TRIPLE_CHANCE;
		}
		effectiveFactor += SettledHandler.getSettledChance(partnerships, batsman.getMatchPlayer().getRuns_scored(), ResultType.TRIPLE);
		effectiveFactor += PressureSituationHandler.getPressureChance(partnerships, ResultType.TRIPLE);
		effectiveFactor += SkillDataHandler.tripleChance(batsman, bowler);
		return effectiveFactor;
	}
	
	public int getFourFactors(Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		int effectiveFactor = 0;
		if(powerplay_factor) {
			effectiveFactor += PowerPlayFactors.FOUR_CHANCE;
		}
		if(death_overs_factor) {
			effectiveFactor += DeathOversFactors.FOUR_CHANCE;
		}
		if(pitch_factor) {
			effectiveFactor += PitchFactors.FOUR_CHANCE;
		}
		if(required_runrate_factor) {
			effectiveFactor += RequiredRunrateFactors.FOUR_CHANCE;
		}
		if(freehit_factor) {
			effectiveFactor += FreeHitFactors.FOUR_CHANCE;
		}
		if(reduced_overs_factor) {
			effectiveFactor += ReducedOversFactors.FOUR_CHANCE;
		}
		if(super_over_factor) {
			effectiveFactor += SuperOverFactors.FOUR_CHANCE;
		}
		if(balance_factor) {
			effectiveFactor += BalanceConstants.FOUR_CHANCE;
		}
		effectiveFactor += SettledHandler.getSettledChance(partnerships, batsman.getMatchPlayer().getRuns_scored(), ResultType.FOUR);
		effectiveFactor += PressureSituationHandler.getPressureChance(partnerships, ResultType.FOUR);
		effectiveFactor += SkillDataHandler.fourChance(batsman, bowler);
		return effectiveFactor;
	}
	
	public int getSixFactors(Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		int effectiveFactor = 0;
		if(powerplay_factor) {
			effectiveFactor += PowerPlayFactors.SIX_CHANCE;
		}
		if(death_overs_factor) {
			effectiveFactor += DeathOversFactors.SIX_CHANCE;
		}
		if(pitch_factor) {
			effectiveFactor += PitchFactors.SIX_CHANCE;
		}
		if(required_runrate_factor) {
			effectiveFactor += RequiredRunrateFactors.SIX_CHANCE;
		}
		if(freehit_factor) {
			effectiveFactor += FreeHitFactors.SIX_CHANCE;
		}
		if(reduced_overs_factor) {
			effectiveFactor += ReducedOversFactors.SIX_CHANCE;
		}
		if(super_over_factor) {
			effectiveFactor += SuperOverFactors.SIX_CHANCE;
		}
		if(balance_factor) {
			effectiveFactor += BalanceConstants.SIX_CHANCE;
		}
		effectiveFactor += SettledHandler.getSettledChance(partnerships, batsman.getMatchPlayer().getRuns_scored(), ResultType.SIX);
		effectiveFactor += PressureSituationHandler.getPressureChance(partnerships, ResultType.SIX);
		effectiveFactor += SkillDataHandler.sixChance(batsman, bowler);
		return effectiveFactor;
	}
	
	public int getWicketFactors(Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		int effectiveFactor = 0;
		if(powerplay_factor) {
			effectiveFactor += PowerPlayFactors.WICKET_CHANCE;
		}
		if(death_overs_factor) {
			effectiveFactor += DeathOversFactors.WICKET_CHANCE;
		}
		if(pitch_factor) {
			effectiveFactor += PitchFactors.WICKET_CHANCE;
		}
		if(required_runrate_factor) {
			effectiveFactor += RequiredRunrateFactors.WICKET_CHANCE;
		}
		if(freehit_factor) {
			effectiveFactor += FreeHitFactors.WICKET_CHANCE;
		}
		if(reduced_overs_factor) {
			effectiveFactor += ReducedOversFactors.WICKET_CHANCE;
		}
		if(super_over_factor) {
			effectiveFactor += SuperOverFactors.WICKET_CHANCE;
		}
		if(balance_factor) {
			effectiveFactor += BalanceConstants.WICKET_CHANCE;
		}
		effectiveFactor += SettledHandler.getSettledChance(partnerships, batsman.getMatchPlayer().getRuns_scored(), ResultType.WICKET);
		effectiveFactor += PressureSituationHandler.getPressureChance(partnerships, ResultType.WICKET);
		effectiveFactor += SkillDataHandler.wicketChance(batsman, bowler);
		return effectiveFactor;
	}
	
}
