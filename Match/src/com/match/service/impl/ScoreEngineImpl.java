package com.match.service.impl;

import java.util.Random;

import com.isl.model.Player;
import com.isl.model.Team;
import com.util.InstanceProvider;
import com.match.data.CommentryConstants;
import com.match.data.MatchConstants;
import com.match.service.GeneralService;
import com.match.service.MatchEngine;
import com.match.service.impl.GeneralServiceImpl;
import com.match.service.ScoreEngine;
import com.match.util.MatchUtil;
import com.match.model.Extra;
import com.match.model.ResultType;
import com.match.model.ScoreEnginePayLoad;
import com.match.model.Wicket;
import com.match.model.WicketType;

/**
 * 
 * @author Pushpak
 *
 */
public class ScoreEngineImpl implements ScoreEngine {

	public static int max = 12000;
	Random randomGenerator = InstanceProvider.getInstance(Random.class);
	private GeneralService generalService = (GeneralService) InstanceProvider.getInstance(GeneralServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.match.service.ScoreEngine#getResultForDelievery(com.isl.model.Player,
	 * com.isl.model.Player, com.match.service.MatchFactors, int, int,
	 * java.util.List)
	 */
	@Override
	public int getResultForDelievery(ScoreEnginePayLoad payLoad) {

		int result = randomGenerator.nextInt(8);
		if (result == 0 && getChance(payLoad, ResultType.DOT)) {
			return 0;
		} else if (result == 1 && getChance(payLoad, ResultType.SINGLE)) {
			return 1;
		} else if (result == 2 && getChance(payLoad, ResultType.DOUBLE)) {
			return 2;
		} else if (result == 3 && getChance(payLoad, ResultType.TRIPLE)) {
			return 3;
		} else if (result == 4 && getChance(payLoad, ResultType.FOUR)) {
			return 4;
		} else if (result == 5 && getChance(payLoad, ResultType.EXTRA)) {
			return 5;
		} else if (result == 6 && getChance(payLoad, ResultType.SIX)) {
			return 6;
		} else if (result == 7 && getChance(payLoad, ResultType.WICKET)) {
			return 7;
		} else {
			return getResultForDelievery(payLoad);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.match.service.ScoreEngine#gettChance(com.match.service.MatchFactors,
	 * com.isl.model.Player, com.isl.model.Player, java.util.List)
	 */
	@Override
	public boolean getChance(ScoreEnginePayLoad payLoad, ResultType resultType) {

		int external = payLoad.getMatchFactors().getFactors(payLoad.getBatsman(), payLoad.getBowler(),
				payLoad.getPartnerships(), resultType);
		int chance = randomGenerator.nextInt(12000);
		boolean validate = true;
		if (ResultType.WICKET.equals(resultType)) {
			validate = validateWicket(payLoad.getScore(), payLoad.getWickets());
		}
		if (chance < (MatchEngine.game.getBaseFactors().getChance(resultType) + external) && validate) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getTypeOfExtra(com.isl.model.Player,
	 * com.isl.model.Player, com.match.service.MatchFactors, int, int,
	 * java.util.List)
	 */
	@Override
	public Extra getTypeOfExtra(ScoreEnginePayLoad payLoad) {

		Extra extra = new Extra();
		int typeOfExtraChance = randomGenerator.nextInt(3);
		switch (typeOfExtraChance) {
		case 0:
			extra.setTypeOfextra(CommentryConstants.WIDE);
			extra.setRuns(getWideRuns());
			break;
		case 1:
			extra.setTypeOfextra(CommentryConstants.NO);
			int runs = 5;
			while (runs == 5 || runs == 7) {
				runs = getNoballRuns(payLoad);
			}
			extra.setRuns(runs);
			break;
		case 2:
			extra.setTypeOfextra(CommentryConstants.LEG_BYE);
			extra.setRuns(getLegByes());
			break;
		}
		return extra;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getWideRuns()
	 */
	@Override
	public int getWideRuns() {

		int runs = randomGenerator.nextInt(5);
		int chance = randomGenerator.nextInt(100);
		if ((runs == 0 && chance < 60) || (runs == 1 && chance > 59 && chance < 70)
				|| (runs == 2 && chance > 69 && chance < 80) || (runs == 3 && chance > 79 && chance < 85)
				|| (runs == 4 && chance > 84)) {
			return runs;
		} else {
			return getWideRuns();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getLegByes()
	 */
	@Override
	public int getLegByes() {

		int runs = 1 + randomGenerator.nextInt(4);
		int chance = randomGenerator.nextInt(100);
		if ((runs == 1 && chance < 60) || (runs == 2 && chance > 59 && chance < 70)
				|| (runs == 3 && chance > 69 && chance < 80) || (runs == 4 && chance > 79)) {
			return runs;
		} else {
			return getLegByes();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getNoballRuns(com.isl.model.Player,
	 * com.isl.model.Player, com.match.service.MatchFactors, int, int,
	 * java.util.List)
	 */
	@Override
	public int getNoballRuns(ScoreEnginePayLoad payLoad) {

		int runs = getResultForDelievery(payLoad);
		return runs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getTypeOfWicket(com.isl.model.Player,
	 * com.isl.model.Player, com.isl.model.Team)
	 */
	@Override
	public Wicket getTypeOfWicket(Player batsman, Player bowler, Team bowling_team) {

		int chance = randomGenerator.nextInt(11);
		Wicket wicket = new Wicket();
		String commentry = null;
		if (chance < 3) {
			wicket.setWicketType(WicketType.BOWLED);
			commentry = batsman.getName() + " b " + bowler.getLastName() + " "
					+ batsman.getMatchPlayer().getRuns_scored() + "(" + batsman.getMatchPlayer().getBalls_faced() + "b "
					+ batsman.getMatchPlayer().getFour_scored() + "x4 " + batsman.getMatchPlayer().getSix_scored()
					+ "x6)";
		} else if (chance < 6) {
			wicket.setWicketType(WicketType.LEGBEFORE);
			commentry = batsman.getName() + " lbw " + bowler.getLastName() + " "
					+ batsman.getMatchPlayer().getRuns_scored() + "(" + batsman.getMatchPlayer().getBalls_faced() + "b "
					+ batsman.getMatchPlayer().getFour_scored() + "x4 " + batsman.getMatchPlayer().getSix_scored()
					+ "x6)";
		} else if (chance == 6) {
			if ((MatchConstants.SPINNER).equals(bowler.getBowling_type())) {
				wicket.setWicketType(WicketType.STUMPED);
				MatchUtil.update(bowling_team.getWicket_keeper(), "catches_taken", 1);
				commentry = batsman.getName() + " st " + bowling_team.getWicket_keeper().getLastName() + " b "
						+ bowler.getLastName() + " " + batsman.getMatchPlayer().getRuns_scored() + "("
						+ batsman.getMatchPlayer().getBalls_faced() + "b " + batsman.getMatchPlayer().getFour_scored()
						+ "x4 " + batsman.getMatchPlayer().getSix_scored() + "x6)";
			} else {
				return getTypeOfWicket(batsman, bowler, bowling_team);
			}
		} else if (chance == 7) {
			wicket.setWicketType(WicketType.RUNOUT);
			commentry = batsman.getName() + " run out " + batsman.getMatchPlayer().getRuns_scored() + "("
					+ batsman.getMatchPlayer().getBalls_faced() + "b " + batsman.getMatchPlayer().getFour_scored()
					+ "x4 " + batsman.getMatchPlayer().getSix_scored() + "x6)";
		} else {
			Player catcher = getRandomPlayer(bowling_team);
			MatchUtil.update(catcher, "catches_taken", 1);
			wicket.setWicketType(WicketType.CAUGHT);
			if (catcher.getName().equals(bowler.getName())) {
				commentry = batsman.getName() + " c&b " + bowler.getLastName() + " "
						+ batsman.getMatchPlayer().getRuns_scored() + "(" + batsman.getMatchPlayer().getBalls_faced()
						+ "b " + batsman.getMatchPlayer().getFour_scored() + "x4 "
						+ batsman.getMatchPlayer().getSix_scored() + "x6)";
			} else {
				commentry = batsman.getName() + " c " + catcher.getLastName() + " b " + bowler.getLastName() + " "
						+ batsman.getMatchPlayer().getRuns_scored() + "(" + batsman.getMatchPlayer().getBalls_faced()
						+ "b " + batsman.getMatchPlayer().getFour_scored() + "x4 "
						+ batsman.getMatchPlayer().getSix_scored() + "x6)";
			}
		}
		wicket.setCommentry(commentry);
		return wicket;
	}

	private boolean validateWicket(int score, int wickets) {

		int validate = randomGenerator.nextInt(3);
		if (validate != 2 && score / Math.pow((wickets + 1), 2) < 2) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getRandomPlayer(com.isl.model.Team)
	 */
	@Override
	public Player getRandomPlayer(Team team) {

		int chance = randomGenerator.nextInt(11);
		return team.getPlayers().get(chance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getRunrate(int, int)
	 */
	@Override
	public float getRunrate(int runs, int overs) {

		return generalService.roundTwoDecimals((float) runs / overs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getStrikerate(int, int)
	 */
	@Override
	public float getStrikerate(int runs, int balls) {

		if (balls != 0) {
			return generalService.roundTwoDecimals((float) runs * 100 / balls);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.ScoreEngine#getEconomy(int, int)
	 */
	@Override
	public float getEconomy(int runs, int balls) {

		float effective_overs = (float) balls / 6;
		return generalService.roundTwoDecimals((float) runs / effective_overs);
	}

}
