package com.match.service;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.isl.comparators.BattingSkillsComparator;
import com.isl.model.Player;
import com.isl.model.Team;
import com.util.FunctionUtil;
import com.util.InstanceProvider;
import com.match.data.CommentryConstants;
import com.match.data.MatchConstants;
import com.match.handler.PressureSituationHandler;
import com.match.handler.SettledSituationHandler;
import com.match.handler.SituationHandler;
import com.match.service.MatchFactors;
import com.match.service.impl.GeneralServiceImpl;
import com.match.service.impl.MatchServiceImpl;
import com.match.service.impl.ScoreEngineImpl;
import com.match.service.ScoreEngine;
import com.util.ListUtil;
import com.match.util.MatchUtil;
import com.match.model.Extra;
import com.match.model.Game;
import com.isl.model.GameType;
import com.match.model.Match;
import com.match.model.ODI;
import com.isl.model.Partnership;
import com.match.model.Range;
import com.match.model.ScoreEnginePayLoad;
import com.match.model.TtwentyI;
import com.match.model.Wicket;
import com.match.model.WicketType;

/**
 * 
 * @author Pushpak
 *
 */
public class MatchEngine {

	private String content;
	private StringBuilder contentsb;
	public static BufferedWriter writer;
	public static Match match;
	private Player onstrike;
	private Player non_strike;
	private Player bowler;
	private int score_batting;
	private int wickets_batting;
	private int runsPerOver;
	private Partnership partnership;
	private int index;
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
	private List<Partnership> partnerships;
	private Player max;
	public static Game game;
	private Range current_powerplay;
	public static SituationHandler settledSituationHandler;
	public static SituationHandler pressureSituationHandler;
	private boolean replacementOccurred = false;
	private boolean stop = false;
	private boolean repeat = false;
	private boolean isOverComplete = false;

	public void runMatchEngine(Team team1, Team team2, GameType gameType) {

		if (gameType.equals(GameType.ODI)) {
			game = InstanceProvider.getInstance(ODI.class);
		} else {
			game = InstanceProvider.getInstance(TtwentyI.class);
		}
		generalService = InstanceProvider.getInstance(GeneralServiceImpl.class);
		matchService = InstanceProvider.getInstance(MatchServiceImpl.class, game.getGameType());
		rainInterruptionReducedOvers = generalService.checkForRainInteruption();
		if (!isMatchTied) {
			match = runPreMatchEngine(team1, team2);
			writer = generalService.getWriter();
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
		getFallOfWickets(match.getBatting_team());
		System.out.println(MatchConstants.NEWLINE);
		getBattingScorecard(match.getBowling_team());
		System.out.println(MatchConstants.NEWLINE);
		getBowlingScorecard(match.getBatting_team());
		System.out.println(MatchConstants.NEWLINE);
		getFallOfWickets(match.getBowling_team());
		System.out.println(MatchConstants.NEWLINE);
		getResult(match);
		System.out.println(MatchConstants.NEWLINE);
		matchService.updateCareerRecords(match);
		if (isMatchTied && GameType.T20I.equals(gameType)) {
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
			System.out.println("The match is over.");
		}
	}

	public int startMatchEngine(Team battingTeam, Team bowlingTeam, Match match) {

		score_batting = 0;
		wickets_batting = 0;
		batting_team = battingTeam;
		bowling_team = bowlingTeam;
		scoreEngine = InstanceProvider.getInstance(ScoreEngineImpl.class);
		pressureSituationHandler = new PressureSituationHandler(game);
		settledSituationHandler = new SettledSituationHandler(game);
		if (match.getMatchFactors() == null) {
			matchFactors = InstanceProvider.getInstance(MatchFactors.class);
			match.setMatchFactors(matchFactors);
		} else {
			matchFactors = matchService.resetMatchFactors(match.getMatchFactors());
		}
		List<Player> batting_lineup = new ArrayList<Player>(batting_team.getPlayers());
		Collections.sort(batting_lineup, new BattingSkillsComparator());
		setWicketKeeper();
		if (GameType.T20I.equals(game.getGameType())) {
			ListUtil.shufflePartialList(batting_lineup, 4, 6);
		}
		batting_team.setBatting_lineup(batting_lineup);
		batting_team.setOvers(game.getMax_overs() + ".0");
		getOpeners();
		partnerships = new ArrayList<Partnership>();
		battingTeam.setPartnerships(partnerships);
		partnership = new Partnership(onstrike, non_strike);
		partnerships.add(partnership);
		index = 1;
		displayTarget(batting_team);
		setMaximumOvers(matchFactors);
		matchService.setBowlingLineup(bowlingTeam, game);
		List<Player> bowling_lineup = bowling_team.getBowling_lineup();
		displayOpeners(batting_team);
		generalService.addTimeLag(2);
		if (isMatchTied) {
			setSuperOverFactors(matchFactors);
		}
		current_powerplay = game.getPowerplays().get(0);
		stop = false;
		FunctionUtil.times(max_overs, overIndex -> {
			isOverComplete = false;
			runsPerOver = 0;
			generalService.addTimeLag(5);
			if (isStartofPowerplay(overIndex + 1)) {
				startPowerplay(matchFactors);
			} else if (isEndofPowerplay(overIndex + 1)) {
				endPowerplay(matchFactors);
			}
			if (overIndex > game.getDeathOverThreshold()
					|| ((rainInterruptionReducedOvers != 0) && overIndex + 1 > 4 * max_overs / 5)) {
				setDeathOverFactors(matchFactors);
			}
			displayBowler(bowling_lineup, overIndex + 1);
			setPitchFactors();
			FunctionUtil.times(MatchConstants.OVER_END_INDEX, ballIndex -> {
				repeat = false;
				generalService.addTimeLag(2);
				setFreehitFactors(matchFactors);
				displayDelivery();
				generalService.addTimeLag(1);
				if (battingTeam.getName().equals(match.getBowling_team().getName())) {
					getValidResult();
				} else {
					resultPerBall = scoreEngine.getResultForDelievery(getScoreEnginePayLoad());
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
						repeat = true;
						MatchUtil.update(bowler, "runs_given", result + 1);
						updateScoreBatting(result + 1);
						updateRunsPerOver(result + 1);
						updatePartnership(result + 1);
					} else if ((CommentryConstants.NO).equals(extra.getTypeOfextra())) {
						ballIndex--;
						repeat = true;
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
					generalService.addTimeLag(3);
					updateWicketsBatting();
					onstrike.getMatchPlayer().setNotOut(false);
					if (bowler.getMatchPlayer().getHattrickCount() >= 3) {
						content = "That's hattrick!!!";
						generalService.writeMatchData(content);
					}
					if (index != 11) {
						getNewBatsman(batting_team);
					} else {
						partnership.setOut(true);
						batting_team.setScore(score_batting);
						batting_team.setWickets(wickets_batting);
						batting_team.setOvers((overIndex) + "." + (ballIndex + 1));
						stop = true;
					}
					updatePartnerships();
				} else if (resultPerBall == 4) {
					MatchUtil.update(onstrike, "four_scored", 1);
				} else if (resultPerBall == 6) {
					MatchUtil.update(onstrike, "six_scored", 1);
				}
				displayScore(overIndex + 1, ballIndex + 1);

				if (!stop && ((resultPerBall != 5 && ballIndex + 1 == 6) || (resultPerBall == 5
						&& (CommentryConstants.LEG_BYE).equals(extra.getTypeOfextra()) && ballIndex + 1 == 6))) {
					changeStrike();
				}

				updateRequired();
				if (!stop && isChaseComplete(overIndex + 1, ballIndex + 1)) {
					stop = true;
				}
				if (ballIndex == 5) {
					isOverComplete = true;
				}
			}, j -> !stop, k -> repeat);
			if (isOverComplete) {
				if (runsPerOver == 0) {
					displayMaidenOverCommentry();
					MatchUtil.update(bowler, "maiden_overs", 1);
				}
				if (!stop) {
					displayEndOfOver(matchFactors, overIndex + 1);
					displayBatsmanScore();
					displayBowlerScore();
					displayRequired(overIndex + 1);
					changeBowlerIfNeeded(bowling_lineup);
				}
			}
		}, j -> !stop);
		batting_team.setScore(score_batting);
		batting_team.setWickets(wickets_batting);
		return 0;
	}

	private Match runPreMatchEngine(Team team1, Team team2) {

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

	private void setWicketKeeper() {

		matchService.setWicketKeeper(bowling_team);
	}

	private void initializeSuperOver() {

		content = "Super over is uderway.";
		generalService.writeMatchData(content);
	}

	private void getOpeners() {

		onstrike = batting_team.getBatting_lineup().get(0);
		onstrike.getMatchPlayer().setNotOut(true);
		onstrike.getMatchPlayer().setBatted(true);
		non_strike = batting_team.getBatting_lineup().get(1);
		non_strike.getMatchPlayer().setNotOut(true);
		non_strike.getMatchPlayer().setBatted(true);
	}

	private void setMaximumOvers(MatchFactors matchFactors) {

		if (rainInterruptionReducedOvers != 0 && !isMatchTied) {
			max_overs = rainInterruptionReducedOvers;
			if (max_overs < 15) {
				matchFactors.setReduced_overs_factor(true);
			}
		} else if (isMatchTied) {
			max_overs = 1;
		} else {
			max_overs = game.getMax_overs();
			ScoreEngineImpl.max = max_overs * 600;
		}
	}

	private ScoreEnginePayLoad getScoreEnginePayLoad() {
		ScoreEnginePayLoad payLoad = new ScoreEnginePayLoad(onstrike, bowler, matchFactors, score_batting,
				wickets_batting, partnerships);
		return payLoad;
	}

	private void checkForMileStone(Player onstrike) {

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

	private void displayTarget(Team batting_team) {

		if (batting_team.getName().equals(match.getBowling_team().getName())) {
			content = "Target: " + (match.getBatting_team().getScore() + 1);
			generalService.writeMatchData(content);
			generalService.addTimeLag(10);
		}
	}

	private void getValidResult() {

		boolean validResult = false;
		while (!validResult) {
			resultPerBall = scoreEngine.getResultForDelievery(getScoreEnginePayLoad());
			if (resultPerBall == 5) {
				extra = scoreEngine.getTypeOfExtra(getScoreEnginePayLoad());
				result = extra.getRuns();
				if (!CommentryConstants.LEG_BYE.equals(extra.getTypeOfextra())) {
					validResult = matchService.validateExtraResult(required, result);
				} else {
					validResult = matchService.validateResult(required, result);
				}
			} else {
				validResult = matchService.validateResult(required, resultPerBall);
			}
		}
	}

	private void changeStrike() {

		Player temp = onstrike;
		onstrike = non_strike;
		non_strike = temp;
	}

	private void displayMaidenOverCommentry() {

		content = "It's a maiden over.";
		generalService.writeMatchData(content);
	}

	private void updateScoreBatting(int resultPerBall) {

		score_batting += resultPerBall;
	}

	private void updateRunsPerOver(int resultPerBall) {

		runsPerOver += resultPerBall;
	}

	private void updatePartnership(int resultPerBall) {

		partnership.setRuns(partnership.getRuns() + resultPerBall);
		if (partnership.getRuns() >= 100 && !partnership.isCentury_partnship()) {
			content = "100 runs partnership between these two batsmen";
			generalService.writeMatchData(content);
			partnership.setCentury_partnship(true);
		}
	}

	private void updatePartnerships() {

		partnership.setOut(true);
		partnership = new Partnership(onstrike, non_strike);
		partnerships.add(partnership);
	}

	private void updateWicketsBatting() {

		wickets_batting++;
	}

	private void getNewBatsman(Team batting_team) {

		onstrike = batting_team.getBatting_lineup().get(index);
		onstrike.getMatchPlayer().setBatted(true);
		onstrike.getMatchPlayer().setNotOut(true);
		content = onstrike.getName() + " walks into the middle";
		generalService.writeMatchData(content);
	}

	private void displayScore(int overIndex, int ballIndex) {

		content = "score: " + score_batting + "/" + wickets_batting + "(" + (overIndex - 1) + "." + ballIndex + ")";
		generalService.writeMatchData(content);
	}

	private void displayDelivery() {

		content = bowler.getLastName() + " to " + onstrike.getLastName();
		generalService.writeMatchData(content);
	}

	private void displayResultPerBall() {

		content = resultPerBall + " runs, " + generalService.getCommentry(resultPerBall);
		generalService.writeMatchData(content);
	}

	private void displayOpeners(Team batting_team) {

		content = batting_team.getName() + " openers " + onstrike.getName() + " and " + non_strike.getName()
				+ " are in the middle";
		generalService.writeMatchData(content);
	}

	private void setSuperOverFactors(MatchFactors matchFactors) {

		matchFactors.setSuper_over_factor(true);
	}

	private void startPowerplay(MatchFactors matchFactors) {

		matchFactors.setPowerplay_factor(true);
		if (!isMatchTied) {
			content = "Powerplay restrictions for overs " + current_powerplay.getStart() + "-"
					+ current_powerplay.getEnd();
			generalService.writeMatchData(content);
		}
	}

	private boolean isStartofPowerplay(int overIndex) {

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

	private boolean isEndofPowerplay(int overIndex) {

		Iterator<Range> itr = game.getPowerplays().iterator();
		while (itr.hasNext()) {
			Range range = itr.next();
			if (range.getEnd() == overIndex - 1) {
				return true;
			}
		}
		return false;
	}

	private void endPowerplay(MatchFactors matchFactors) {

		matchFactors.setPowerplay_factor(false);
		if (!isMatchTied) {
			content = "End of manadatory powerplay.";
			generalService.writeMatchData(content);
		}
	}

	private void setDeathOverFactors(MatchFactors matchFactors) {

		matchFactors.setDeath_overs_factor(true);
	}

	private void displayBowler(List<Player> bowling_lineup, int overIndex) {

		bowler = bowling_lineup.get(overIndex - 1);
		content = bowler.getName() + " comes into the action";
		generalService.writeMatchData(content);
	}

	private void setFreehitFactors(MatchFactors matchFactors) {

		if (match.isFreehit()) {
			content = CommentryConstants.FREEHIT;
			generalService.writeMatchData(content);
			matchFactors.setFreehit_factor(true);
		} else {
			matchFactors.setFreehit_factor(false);
		}
	}

	private void continueFreehitFactors(MatchFactors matchFactors) {

		if ((CommentryConstants.NO).equals(extra.getTypeOfextra())
				|| ((CommentryConstants.WIDE).equals(extra.getTypeOfextra()) && matchFactors.isFreehit_factor())) {
			match.setFreehit(true);
		}
	}

	private void displayExtraResult(MatchFactors matchFactors) {

		extra = scoreEngine.getTypeOfExtra(getScoreEnginePayLoad());
		result = extra.getRuns();
		content = extra.getTypeOfextra() + ", " + result + " runs";
		generalService.writeMatchData(content);
	}

	private void displayTypeOfWicket() {

		Wicket wicket = scoreEngine.getTypeOfWicket(onstrike, bowler, bowling_team);
		if (!WicketType.RUNOUT.equals(wicket.getWicketType())) {
			MatchUtil.update(bowler, "wickets_taken", 1);
		}
		content = wicket.getCommentry();
		generalService.writeMatchData(content);
	}

	private void updateRequired() {

		if (batting_team.getName().equals(match.getBowling_team().getName())) {
			required = match.getBatting_team().getScore() - score_batting + 1;
		}
	}

	private void displayRequired(int overIndex) {
		if (batting_team.getName().equals(match.getBowling_team().getName()) && (overIndex < max_overs)
				&& (required < 100 || overIndex >= max_overs * 2 / 3)) {
			content = required + " runs needed from " + (max_overs - overIndex) + " overs";
			generalService.writeMatchData(content);
		}
	}

	private boolean isChaseComplete(int overIndex, int ballIndex) {

		if (batting_team.getName().equals(match.getBowling_team().getName()) && required <= 0) {
			batting_team.setScore(score_batting);
			batting_team.setWickets(wickets_batting);
			batting_team.setOvers((overIndex - 1) + "." + ballIndex);
			return true;
		}
		return false;
	}

	private void displayEndOfOver(MatchFactors matchFactors, int overIndex) {

		if (batting_team.getName().equals(match.getBowling_team().getName()) && overIndex != max_overs) {
			content = "End of over " + overIndex + "(RR:" + scoreEngine.getRunrate(score_batting, overIndex) + ") RRR:"
					+ scoreEngine.getRunrate(match.getBatting_team().getScore() + 1 - score_batting,
							max_overs - overIndex);
			generalService.writeMatchData(content);
			setRequiredRunrateFactors(matchFactors, overIndex);
		} else {
			content = "End of over " + overIndex + "(RR:" + scoreEngine.getRunrate(score_batting, overIndex) + ")";
			generalService.writeMatchData(content);
			setBalanceFactors(matchFactors, overIndex);
		}
	}

	private void setRequiredRunrateFactors(MatchFactors matchFactors, int overIndex) {

		if (scoreEngine.getRunrate(match.getBatting_team().getScore() + 1 - score_batting,
				max_overs - overIndex) >= game.getRequiredRunrateThreshold()) {
			matchFactors.setRequired_runrate_factor(true);
		} else {
			matchFactors.setRequired_runrate_factor(false);
		}
	}

	private void setBalanceFactors(MatchFactors matchFactors, int overIndex) {

		if (batting_team.getName().equals(match.getBatting_team().getName())
				&& overIndex > game.getBalacedOverThreshold()
				&& scoreEngine.getRunrate(score_batting, overIndex) < game.getBalancedRunrateThreshold()) {
			matchFactors.setBalance_factor(true);
		}
	}

	private void displayBatsmanScore() {

		contentsb = new StringBuilder();
		contentsb.append(onstrike.getName()).append(" ").append(onstrike.getMatchPlayer().getRuns_scored()).append("(")
				.append(onstrike.getMatchPlayer().getBalls_faced()).append(")\t").append(non_strike.getName())
				.append(" ").append(non_strike.getMatchPlayer().getRuns_scored()).append("(")
				.append(non_strike.getMatchPlayer().getBalls_faced()).append(")");
		generalService.writeMatchData(contentsb);
	}

	private void displayBowlerScore() {

		contentsb = new StringBuilder();
		contentsb.append(bowler.getName()).append(" ").append(bowler.getMatchPlayer().getBalls_bowled() / 6).append("-")
				.append(bowler.getMatchPlayer().getMaiden_overs()).append("-")
				.append(bowler.getMatchPlayer().getRuns_given()).append("-")
				.append(bowler.getMatchPlayer().getWickets_taken());
		generalService.writeMatchData(contentsb);
	}

	private void changeBowlerIfNeeded(List<Player> bowlingLineup) {
		if (!replacementOccurred && bowler.getMatchPlayer().getBalls_bowled() == 30
				&& bowler.getMatchPlayer().getRuns_given() >= 50) {
			ListUtil.replaceOccurrences(bowler, bowling_team.getReserved(), bowlingLineup, 6, 10);
			replacementOccurred = true;
		}
	}

	private void setPitchFactors() {

		boolean pitch_factor = matchService.isPitchFactor(bowler.getBowling_type(), match.getPitch_type());
		matchFactors.setPitch_factor(pitch_factor);
	}

	private void getBattingScorecard(Team team) {

		contentsb = new StringBuilder();
		contentsb.append(team.getName()).append(" ").append(team.getScore()).append("/").append(team.getWickets())
				.append("(").append(team.getOvers()).append(")");
		generalService.writeMatchData(contentsb);
		team.getBatting_lineup().stream()
				.filter(player -> player.getMatchPlayer().getBalls_faced() != 0 || player.getMatchPlayer().isNotOut())
				.forEach(player -> {
					content = player.getMatchPlayer().isNotOut() ? player.getName() + "* " : player.getName() + " ";
					content += player.getMatchPlayer().getRuns_scored() + "(" + player.getMatchPlayer().getBalls_faced()
							+ ") SR:"
							+ scoreEngine.getStrikerate(player.getMatchPlayer().getRuns_scored(),
									player.getMatchPlayer().getBalls_faced())
							+ " (" + player.getMatchPlayer().getFour_scored() + "x4 "
							+ player.getMatchPlayer().getSix_scored() + "x6)";
					generalService.writeMatchData(content);
				});
	}

	private void getFallOfWickets(Team team) {

		contentsb = new StringBuilder("Fall of wickets: ");
		int runs = 0;
		int wicketIndex = 1;
		for (Partnership partnership : team.getPartnerships()) {
			if (partnership.isOut()) {
				runs += partnership.getRuns();
				contentsb.append(wicketIndex).append("-").append(runs).append(", ");
				wicketIndex++;
			}
		}
		contentsb = contentsb.deleteCharAt(contentsb.length() - 1);
		contentsb = contentsb.deleteCharAt(contentsb.length() - 1);
		generalService.writeMatchData(contentsb);
	}

	private void getBowlingScorecard(Team team) {

		content = team.getName() + " bowling";
		generalService.writeMatchData(content);
		team.getPlayers().stream().filter(player -> player.getMatchPlayer().getBalls_bowled() != 0).forEach(player -> {
			contentsb = new StringBuilder();
			contentsb.append(player.getName()).append(" ").append(player.getMatchPlayer().getWickets_taken())
					.append("/").append(player.getMatchPlayer().getRuns_given()).append("(")
					.append(player.getMatchPlayer().getBalls_bowled() / 6).append(".")
					.append(player.getMatchPlayer().getBalls_bowled() % 6).append(") ER:")
					.append(scoreEngine.getEconomy(player.getMatchPlayer().getRuns_given(),
							player.getMatchPlayer().getBalls_bowled()));
			generalService.writeMatchData(contentsb);
		});
	}

	private void getResult(Match match) {

		contentsb = new StringBuilder();
		if (match.getBatting_team().getScore() > match.getBowling_team().getScore()) {
			isMatchTied = false;
			contentsb.append(match.getBatting_team().getName()).append(" have won the match by ")
					.append(match.getBatting_team().getScore() - match.getBowling_team().getScore()).append(" runs");
			generalService.writeMatchData(contentsb);
		} else if (match.getBatting_team().getScore() < match.getBowling_team().getScore()) {
			isMatchTied = false;
			contentsb.append(match.getBowling_team().getName()).append(" have won the match by ")
					.append((10 - match.getBowling_team().getWickets())).append(" wickets");
			generalService.writeMatchData(contentsb);
		} else {
			isMatchTied = true;
			content = "Match tied";
			generalService.writeMatchData(content);
		}
	}

	private void getManOfTheMatch(Match match) {

		if (match.getMan_of_the_match() == null) {
			Player mom;
			Player batting_team_mom = getBestPerformer(match.getBatting_team());
			Player bowling_team_mom = getBestPerformer(match.getBowling_team());
			mom = getPerformance(batting_team_mom) > getPerformance(bowling_team_mom) ? batting_team_mom
					: bowling_team_mom;
			match.setMan_of_the_match(mom);
		}
	}

	private void displayManOfTheMatch(Match match) {

		content = "Man of the match is " + match.getMan_of_the_match().getName();
		generalService.writeMatchData(content);
	}

	private Player getBestPerformer(Team team) {

		max = team.getPlayers().get(0);
		team.getPlayers().forEach(player -> max = getPerformance(player) > getPerformance(max) ? player : max);
		return max;
	}

	private int getPerformance(Player player) {

		int performance = 0;
		performance = player.getMatchPlayer().getRuns_scored() + 22 * player.getMatchPlayer().getWickets_taken()
				+ 10 * player.getMatchPlayer().getCatches_taken();
		return performance;
	}

}
