package com.match.service;

import java.io.BufferedWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.isl.comparators.BattingSkillsComparator;
import com.isl.model.Player;
import com.isl.model.Team;
import com.match.config.InstanceProvider;
import com.match.data.CommentryConstants;
import com.match.data.MatchConstants;
import com.match.service.MatchFactors;
import com.match.util.ListUtil;
import com.match.util.MatchUtil;
import com.match.model.Extra;
import com.match.model.Game;
import com.match.model.GameType;
import com.match.model.Match;
import com.match.model.ODI;
import com.match.model.Range;
import com.match.model.TtwentyI;

/**
 * 
 * @author Pushpak
 *
 */
public class MatchEngine {

	private String content;
	public static BufferedWriter writer;
	private Match match;
	private Player onstrike;
	private Player non_strike;
	private Player bowler;
	private int score_batting;
	private int wickets_batting;
	private int runsPerOver;
	private int partnership;
	private int index;
	private int overIndex;
	private int ballIndex;
	private int resultPerBall;
	private int result;
	private Extra extra;
	private ScoreEngine scoreEngine;
	private int rainInterruptionReducedOvers;
	private int max_overs;
	private Team batting_team;
	private Team bowling_team;
	private int required;
	private MatchService matchService;
	private GeneralService generalService;
	private boolean isMatchTied = false;
	private MatchFactors matchFactors;
	private LinkedList<Integer> partnerships;
	private Player max;
	private Game game;
	private Range current_powerplay;

	public void runMatchEngine(Team team1, Team team2, GameType gameType) {

		if (gameType.equals(GameType.ODI)) {
			game = InstanceProvider.getInstance(ODI.class);
		} else {
			game = InstanceProvider.getInstance(TtwentyI.class);
		}
		ScoreEngine.game = game;
		generalService = InstanceProvider.getInstance(GeneralService.class);
		matchService = InstanceProvider.getInstance(MatchService.class);
		rainInterruptionReducedOvers = generalService.checkForRainInteruption();
		if (!isMatchTied) {
			match = runPreMatchEngine(team1, team2);
			writer = generalService.getWriter(match);
			if (rainInterruptionReducedOvers != 0) {
				System.out.println("Match has been interrupted by rain and is reduced to "
						+ rainInterruptionReducedOvers + " overs per side.");
			}
		}
		generalService.addTimeLag(3);
		startMatchEngine(match.getBatting_team(), match.getBowling_team(), match);
		startMatchEngine(match.getBowling_team(), match.getBatting_team(), match);
		generalService.addTimeLag(10);
		System.out.println(MatchConstants.NEWLINE);
		getBattingScorecard(match.getBatting_team());
		System.out.println(MatchConstants.NEWLINE);
		getBowlingScorecard(match.getBowling_team());
		System.out.println(MatchConstants.NEWLINE);
		getBattingScorecard(match.getBowling_team());
		System.out.println(MatchConstants.NEWLINE);
		getBowlingScorecard(match.getBatting_team());
		System.out.println(MatchConstants.NEWLINE);
		getResult(match);
		System.out.println(MatchConstants.NEWLINE);
		matchService.updateCareerRecords(match);
		if (isMatchTied) {
			getManOfTheMatch(match);
			matchService.refreshMatchPlayers(match);
			initializeSuperOver();
			System.out.println(MatchConstants.NEWLINE);
			generalService.addTimeLag(5);
			runMatchEngine(team1, team2, gameType);
		} else {
			getManOfTheMatch(match);
			displayManOfTheMatch(match);
			generalService.closeWriter(writer);
			matchService.updateCareerRecords(match);
			System.out.println("The match is over.");
		}
	}

	public int startMatchEngine(Team battingTeam, Team bowlingTeam, Match match) {

		score_batting = 0;
		wickets_batting = 0;
		partnership = 0;
		batting_team = battingTeam;
		bowling_team = bowlingTeam;
		partnerships = new LinkedList<Integer>();
		partnerships.add(null);
		scoreEngine = InstanceProvider.getInstance(ScoreEngine.class);
		if (match.getMatchFactors() == null) {
			matchFactors = InstanceProvider.getInstance(MatchFactors.class);
			match.setMatchFactors(matchFactors);
		} else {
			matchFactors = matchService.resetMatchFactors(match.getMatchFactors());
		}
		Collections.sort(batting_team.getPlayers(), new BattingSkillsComparator());
		setWicketKeeper();
		ListUtil.shufflePartialList(batting_team.getPlayers(), 3, 6);
		getOpeners();
		index = 1;
		displayTarget(batting_team);
		overIndex = 1;
		setMaximumOvers(matchFactors);
		matchService.setBowlingLineup(bowlingTeam, max_overs);
		Player[] bowling_lineup = bowling_team.getBowling_lineup();
		displayOpeners(batting_team);
		generalService.addTimeLag(2);
		if (isMatchTied) {
			setSuperOverFactors(matchFactors);
		}
		current_powerplay = game.getPowerplays().get(0);
		for (overIndex = MatchConstants.MATCH_START_INDEX; overIndex <= max_overs; overIndex++) {
			runsPerOver = 0;
			generalService.addTimeLag(5);
			if (isStartofPowerplay()) {
				startPowerplay(matchFactors);
			} else if(isEndofPowerplay()) {
				endPowerplay(matchFactors);
			}
			if (overIndex > game.getDeathOverThreshold()
					|| ((rainInterruptionReducedOvers != 0) && overIndex > 4 * max_overs / 5)) {
				setDeathOverFactors(matchFactors);
			}
			displayBowler(bowling_lineup);
			setPitchFactors();
			ballIndex = 1;
			for (ballIndex = MatchConstants.OVER_START_INDEX; ballIndex <= MatchConstants.OVER_END_INDEX; ballIndex++) {
				generalService.addTimeLag(2);
				setFreehitFactors(matchFactors);
				displayDelivery();
				generalService.addTimeLag(1);
				if (battingTeam.getName().equals(match.getBowling_team().getName())) {
					getValidResult();
				} else {
					resultPerBall = scoreEngine.getResultForDelievery(onstrike, bowler, matchFactors, score_batting,
							wickets_batting, partnerships);
					if (resultPerBall == 7) {
						bowler.getMatchPlayer().setHattrickCount(bowler.getMatchPlayer().getHattrickCount() + 1);
					} else {
						bowler.getMatchPlayer().setHattrickCount(0);
					}
				}
				match.setFreehit(false);
				if (resultPerBall != 5 && resultPerBall != 7) {
					displayResultPerBall();
					updateScoreBatting(resultPerBall);
					updateRunsPerOver(resultPerBall);
					updatePartnership(resultPerBall);
					MatchUtil.update(onstrike, "runs_scored", resultPerBall);
					MatchUtil.update(bowler, "runs_given", resultPerBall);
				}
				checkForMileStone(onstrike);
				if (resultPerBall != 5) {
					MatchUtil.update(onstrike, "balls_faced", 1);
					MatchUtil.update(bowler, "balls_bowled", 1);
				}

				if (resultPerBall == 5) {
					displayExtraResult(matchFactors);
					continueFreehitFactors(matchFactors);
					if ((CommentryConstants.WIDE).equals(extra.getTypeOfextra())) {
						ballIndex--;
						MatchUtil.update(bowler, "runs_given", result + 1);
						updateScoreBatting(result + 1);
						updateRunsPerOver(result + 1);
						updatePartnership(result + 1);
					} else if ((CommentryConstants.NO).equals(extra.getTypeOfextra())) {
						ballIndex--;
						MatchUtil.update(bowler, "runs_given", result + 1);
						MatchUtil.update(onstrike, "runs_scored", result);
						if (result == 4) {
							MatchUtil.update(onstrike, "four_scored", 1);
						} else if (result == 6) {
							MatchUtil.update(onstrike, "six_scored", 1);
						}
						checkForMileStone(onstrike);
						updateScoreBatting(result + 1);
						updateRunsPerOver(result + 1);
						updatePartnership(result + 1);
					} else {
						MatchUtil.update(bowler, "runs_given", result);
						MatchUtil.update(onstrike, "balls_faced", 1);
						MatchUtil.update(bowler, "balls_bowled", 1);
						updateScoreBatting(result);
						updateRunsPerOver(result);
						updatePartnership(result);
					}
					if (result == 1 || result == 3) {
						changeStrike();
					}
				} else if (resultPerBall == 1 || resultPerBall == 3) {
					changeStrike();
				} else if (resultPerBall == 7) {
					index++;
					content = generalService.getCommentry(resultPerBall);
					generalService.writeMatchData(content);
					displayTypeOfWicket();
					MatchUtil.update(bowler, "wickets_taken", 1);
					generalService.addTimeLag(3);
					updateWicketsBatting();
					onstrike.getMatchPlayer().setNotOut(false);
					updatePartnerships();
					partnership = 0;
					if (bowler.getMatchPlayer().getHattrickCount() >= 3) {
						content = "That's hattrick!!!";
						generalService.writeMatchData(content);
					}
					if (index != 11) {
						getNewBatsman(batting_team);
					} else {
						batting_team.setScore(score_batting);
						batting_team.setWickets(wickets_batting);
						return 0;
					}
				} else if (resultPerBall == 4) {
					MatchUtil.update(onstrike, "four_scored", 1);
				} else if (resultPerBall == 6) {
					MatchUtil.update(onstrike, "six_scored", 1);
				}
				displayScore();

				if ((resultPerBall != 5 && ballIndex == 6) || (resultPerBall == 5
						&& (CommentryConstants.LEG_BYE).equals(extra.getTypeOfextra()) && ballIndex == 6)) {
					changeStrike();
				}

				updateRequired();
				if (isChaseComplete()) {
					return 0;
				}
			}
			if (runsPerOver == 0) {
				displayMaidenOverCommentry();
				MatchUtil.update(bowler, "maiden_overs", 1);
			}
			displayEndOfOver(matchFactors);
			displayBatsmanScore();
			displayBowlerScore();
		}
		batting_team.setScore(score_batting);
		batting_team.setWickets(wickets_batting);
		return 0;
	}

	protected Match runPreMatchEngine(Team team1, Team team2) {

		Match match = InstanceProvider.getInstance(Match.class);
		Random randomGenerator = InstanceProvider.getInstance(Random.class);
		String tossWinner = null;
		String batting_team = null;

		int pitchChance = randomGenerator.nextInt(3);
		System.out.println("The pitch type is " + MatchConstants.PITCH_TYPES[pitchChance]);
		match.setPitch_type(MatchConstants.PITCH_TYPES[pitchChance]);
		int tossChance = randomGenerator.nextInt(2);
		if (tossChance == 0) {
			tossWinner = team1.getName();
			System.out.println(tossWinner + " have won toss");
		} else {
			tossWinner = team2.getName();
			System.out.println(tossWinner + " have won toss");
		}
		match.setTossWinner(tossWinner);

		int battingChance = randomGenerator.nextInt(2);
		if (battingChance == 0) {
			System.out.println(tossWinner + " have decided to bat first");
			batting_team = tossWinner;
		} else {
			System.out.println(tossWinner + " have decided to bowl first");
		}

		if (batting_team != null) {
			if ((batting_team).equals(team1.getName())) {
				match.setBatting_team(team1);
				match.setBowling_team(team2);
			} else {
				match.setBatting_team(team2);
				match.setBowling_team(team1);
			}
		} else {
			if ((match.getTossWinner()).equals(team1.getName())) {
				match.setBatting_team(team2);
				match.setBowling_team(team1);
			} else {
				match.setBatting_team(team1);
				match.setBowling_team(team2);
			}
		}

		return match;
	}

	protected void setWicketKeeper() {

		matchService.setWicketKeeper(bowling_team);
	}

	protected void initializeSuperOver() {

		content = "Super over is uderway.";
		generalService.writeMatchData(content);
	}

	protected void getOpeners() {

		onstrike = batting_team.getPlayers().get(0);
		onstrike.getMatchPlayer().setNotOut(true);
		non_strike = batting_team.getPlayers().get(1);
		non_strike.getMatchPlayer().setNotOut(true);
	}

	protected void setMaximumOvers(MatchFactors matchFactors) {

		if (rainInterruptionReducedOvers != 0 && !isMatchTied) {
			max_overs = rainInterruptionReducedOvers;
			if (max_overs < 15) {
				matchFactors.setReduced_overs_factor(true);
			}
		} else if (isMatchTied) {
			max_overs = 1;
		} else {
			max_overs = game.getMax_overs();
			ScoreEngine.max = max_overs * 600;
		}
	}

	protected void checkForMileStone(Player onstrike) {

		if (onstrike.getMatchPlayer().getRuns_scored() > 49 && !onstrike.getMatchPlayer().isHalf_century()) {
			onstrike.getMatchPlayer().setHalf_century(true);
			content = "That's half century for " + onstrike.getName();
			generalService.writeMatchData(content);
		} else if (onstrike.getMatchPlayer().getRuns_scored() > 99 && !onstrike.getMatchPlayer().isCentury()) {
			onstrike.getMatchPlayer().setCentury(true);
			content = "That's century for " + onstrike.getName();
			generalService.writeMatchData(content);
		}
	}

	protected void displayTarget(Team batting_team) {

		if (batting_team.getName().equals(match.getBowling_team().getName())) {
			content = "Target: " + (match.getBatting_team().getScore() + 1);
			generalService.writeMatchData(content);
			generalService.addTimeLag(10);
		}
	}

	protected void getValidResult() {

		boolean validResult = false;
		while (!validResult) {
			resultPerBall = scoreEngine.getResultForDelievery(onstrike, bowler, matchFactors, score_batting,
					wickets_batting, partnerships);
			validResult = matchService.validateResult(required, resultPerBall);
		}
	}

	protected void changeStrike() {

		Player temp = onstrike;
		onstrike = non_strike;
		non_strike = temp;
	}

	protected void displayMaidenOverCommentry() {

		content = "It's a maiden over.";
		generalService.writeMatchData(content);
	}

	protected void updateScoreBatting(int resultPerBall) {

		score_batting += resultPerBall;
	}

	protected void updateRunsPerOver(int resultPerBall) {

		runsPerOver += resultPerBall;
	}

	protected void updatePartnership(int resultPerBall) {

		partnership += resultPerBall;
		partnerships.removeLast();
		partnerships.add(partnership);
	}

	protected void updatePartnerships() {

		partnerships.add(null);
	}

	protected void updateWicketsBatting() {

		wickets_batting++;
	}

	protected void getNewBatsman(Team batting_team) {

		onstrike = batting_team.getPlayers().get(index);
		onstrike.getMatchPlayer().setNotOut(true);
		content = onstrike.getName() + " walks into the middle";
		generalService.writeMatchData(content);
	}

	protected void displayScore() {

		content = "score: " + score_batting + "/" + wickets_batting + "(" + (overIndex - 1) + "." + ballIndex + ")";
		generalService.writeMatchData(content);
	}

	protected void displayDelivery() {

		content = bowler.getLastName() + " to " + onstrike.getLastName();
		generalService.writeMatchData(content);
	}

	protected void displayResultPerBall() {

		content = resultPerBall + " runs, " + generalService.getCommentry(resultPerBall);
		generalService.writeMatchData(content);
	}

	protected void displayOpeners(Team batting_team) {

		content = batting_team.getName() + " openers " + onstrike.getName() + " and " + non_strike.getName()
				+ " are in the middle";
		generalService.writeMatchData(content);
	}

	protected void setSuperOverFactors(MatchFactors matchFactors) {

		matchFactors.setSuper_over_factor(true);
	}

	protected void startPowerplay(MatchFactors matchFactors) {

		matchFactors.setPowerplay_factor(true);
		if (!isMatchTied) {
			content = "Powerplay restrictions for overs " + current_powerplay.getStart() + "-"
					+ current_powerplay.getEnd();
			generalService.writeMatchData(content);
		}
	}

	protected boolean isStartofPowerplay() {

		Iterator<Range> itr = game.getPowerplays().iterator();
		while (itr.hasNext()) {
			Range range = itr.next();
			if (range.getStart() == overIndex) {
				current_powerplay = range;
				return true;
			}
		}
		return false;
	}
	
	protected boolean isEndofPowerplay() {

		Iterator<Range> itr = game.getPowerplays().iterator();
		while (itr.hasNext()) {
			Range range = itr.next();
			if (range.getEnd() == overIndex - 1) {
				return true;
			}
		}
		return false;
	}

	protected void endPowerplay(MatchFactors matchFactors) {

		matchFactors.setPowerplay_factor(false);
		if (!isMatchTied) {
			content = "End of manadatory powerplay.";
			generalService.writeMatchData(content);
		}
	}

	protected void setDeathOverFactors(MatchFactors matchFactors) {

		matchFactors.setDeath_overs_factor(true);
	}

	protected void displayBowler(Player[] bowling_lineup) {

		bowler = bowling_lineup[overIndex - 1];
		content = bowler.getName() + " comes into the action";
		generalService.writeMatchData(content);
	}

	protected void setFreehitFactors(MatchFactors matchFactors) {

		if (match.isFreehit()) {
			content = CommentryConstants.FREEHIT;
			generalService.writeMatchData(content);
			matchFactors.setFreehit_factor(true);
		} else {
			matchFactors.setFreehit_factor(false);
		}
	}

	protected void continueFreehitFactors(MatchFactors matchFactors) {

		if ((CommentryConstants.NO).equals(extra.getTypeOfextra())
				|| ((CommentryConstants.WIDE).equals(extra.getTypeOfextra()) && matchFactors.isFreehit_factor())) {
			match.setFreehit(true);
		}
	}

	protected void displayExtraResult(MatchFactors matchFactors) {

		extra = scoreEngine.getTypeOfExtra(onstrike, bowler, matchFactors, score_batting, wickets_batting,
				partnerships);
		result = extra.getRuns();
		content = extra.getTypeOfextra() + ", " + result + " runs";
		generalService.writeMatchData(content);
	}

	protected void displayTypeOfWicket() {

		content = scoreEngine.getTypeOfWicket(onstrike, bowler, bowling_team);
		generalService.writeMatchData(content);
	}

	protected void updateRequired() {

		if (batting_team.getName().equals(match.getBowling_team().getName())) {
			required = match.getBatting_team().getScore() - score_batting;
		}
	}

	protected boolean isChaseComplete() {

		if (batting_team.getName().equals(match.getBowling_team().getName()) && required < 0) {
			batting_team.setScore(score_batting);
			batting_team.setWickets(wickets_batting);
			return true;
		}
		return false;
	}

	protected void displayEndOfOver(MatchFactors matchFactors) {

		if (batting_team.getName().equals(match.getBowling_team().getName()) && overIndex != max_overs) {
			content = "End of over " + overIndex + "(RR:" + scoreEngine.getRunrate(score_batting, overIndex) + ") RRR:"
					+ scoreEngine.getRunrate(match.getBatting_team().getScore() + 1 - score_batting,
							max_overs - overIndex);
			generalService.writeMatchData(content);
			setRequiredRunrateFactors(matchFactors);
		} else {
			content = "End of over " + overIndex + "(RR:" + scoreEngine.getRunrate(score_batting, overIndex) + ")";
			generalService.writeMatchData(content);
			setBalanceFactors(matchFactors);
		}
	}

	protected void setRequiredRunrateFactors(MatchFactors matchFactors) {

		if (scoreEngine.getRunrate(match.getBatting_team().getScore() + 1 - score_batting,
				max_overs - overIndex) >= game.getRequiredRunrateThreshold()) {
			matchFactors.setRequired_runrate_factor(true);
		} else {
			matchFactors.setRequired_runrate_factor(false);
		}
	}

	protected void setBalanceFactors(MatchFactors matchFactors) {

		if (batting_team.getName().equals(match.getBatting_team().getName()) && overIndex > game.getBalacedOverThreshold()
				&& scoreEngine.getRunrate(score_batting, overIndex) < game.getBalancedRunrateThreshold()) {
			matchFactors.setBalance_factor(true);
		}
	}

	protected void displayBatsmanScore() {

		content = onstrike.getName() + " " + onstrike.getMatchPlayer().getRuns_scored() + "("
				+ onstrike.getMatchPlayer().getBalls_faced() + ")\t" + non_strike.getName() + " "
				+ non_strike.getMatchPlayer().getRuns_scored() + "(" + non_strike.getMatchPlayer().getBalls_faced()
				+ ")";
		generalService.writeMatchData(content);
	}

	protected void displayBowlerScore() {

		content = bowler.getName() + " " + bowler.getMatchPlayer().getBalls_bowled() / 6 + "-"
				+ bowler.getMatchPlayer().getMaiden_overs() + "-" + bowler.getMatchPlayer().getRuns_given() + "-"
				+ bowler.getMatchPlayer().getWickets_taken();
		generalService.writeMatchData(content);
	}

	protected void setPitchFactors() {

		boolean pitch_factor = matchService.isPitchFactor(bowler.getBowling_type(), match.getPitch_type());
		matchFactors.setPitch_factor(pitch_factor);
	}

	protected void getBattingScorecard(Team team) {

		content = team.getName() + " " + team.getScore() + "/" + team.getWickets();
		generalService.writeMatchData(content);
		team.getPlayers().stream()
				.filter(player -> player.getMatchPlayer().getBalls_faced() != 0 || player.getMatchPlayer().isNotOut())
				.forEach(player -> {
					content = player.getMatchPlayer().isNotOut()
							? player.getName() + "* " + player.getMatchPlayer().getRuns_scored() + "("
									+ player.getMatchPlayer().getBalls_faced() + ") SR:"
									+ scoreEngine.getStrikerate(player.getMatchPlayer().getRuns_scored(),
											player.getMatchPlayer().getBalls_faced())
							: player.getName() + " " + player.getMatchPlayer().getRuns_scored() + "("
									+ player.getMatchPlayer().getBalls_faced() + ") SR:"
									+ scoreEngine.getStrikerate(player.getMatchPlayer().getRuns_scored(),
											player.getMatchPlayer().getBalls_faced());
					generalService.writeMatchData(content);
				});
	}

	protected void getBowlingScorecard(Team team) {

		content = team.getName() + " bowling";
		generalService.writeMatchData(content);
		team.getPlayers().stream().filter(player -> player.getMatchPlayer().getBalls_bowled() != 0).forEach(player -> {
			content = player.getName() + " " + player.getMatchPlayer().getWickets_taken() + "/"
					+ player.getMatchPlayer().getRuns_given() + "(" + player.getMatchPlayer().getBalls_bowled() / 6
					+ "." + player.getMatchPlayer().getBalls_bowled() % 6 + ") ER:" + scoreEngine.getEconomy(
							+player.getMatchPlayer().getRuns_given(), player.getMatchPlayer().getBalls_bowled());
			generalService.writeMatchData(content);
		});
	}

	protected void getResult(Match match) {

		if (match.getBatting_team().getScore() > match.getBowling_team().getScore()) {
			isMatchTied = false;
			content = match.getBatting_team().getName() + " have won the match by "
					+ (match.getBatting_team().getScore() - match.getBowling_team().getScore()) + " runs";
			generalService.writeMatchData(content);
		} else if (match.getBatting_team().getScore() < match.getBowling_team().getScore()) {
			isMatchTied = false;
			content = match.getBowling_team().getName() + " have won the match by "
					+ (10 - match.getBowling_team().getWickets()) + " wickets";
			generalService.writeMatchData(content);
		} else {
			isMatchTied = true;
			content = "Match tied";
			generalService.writeMatchData(content);
		}
	}

	protected void getManOfTheMatch(Match match) {

		if (match.getMan_of_the_match() == null) {
			Player mom;
			mom = getPerformance(getBestPerformer(match.getBatting_team())) > getPerformance(
					getBestPerformer(match.getBowling_team())) ? getBestPerformer(match.getBatting_team())
							: getBestPerformer(match.getBowling_team());
			match.setMan_of_the_match(mom);
		}
	}

	protected void displayManOfTheMatch(Match match) {

		content = "Man of the match is " + match.getMan_of_the_match().getName();
		generalService.writeMatchData(content);
	}

	protected Player getBestPerformer(Team team) {

		max = team.getPlayers().get(0);
		team.getPlayers().forEach(player -> max = getPerformance(player) > getPerformance(max) ? player : max);
		return max;
	}

	protected int getPerformance(Player player) {

		int performance = 0;
		performance = player.getMatchPlayer().getRuns_scored() + 22 * player.getMatchPlayer().getWickets_taken();
		return performance;
	}

}
