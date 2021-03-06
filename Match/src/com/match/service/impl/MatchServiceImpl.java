package com.match.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.isl.comparators.BowlingSkillsComparator;
import com.isl.comparators.BowlingTypeComparator;
import com.isl.model.GameType;
import com.isl.model.MatchPlayer;
import com.isl.model.Player;
import com.isl.model.Team;
import com.isl.service.PlayerDao;
import com.isl.service.TeamDao;
import com.util.FunctionUtil;
import com.util.InstanceProvider;
import com.match.data.MatchConstants;
import com.match.model.Game;
import com.match.model.Match;
import com.match.service.MatchEngine;
import com.match.service.MatchFactors;
import com.match.service.MatchService;
import com.match.util.MatchUtil;
import com.util.RandomUtil;
import com.util.RandomUtil.RandomGenerator;

/**
 * 
 * @author PUSHPAK
 *
 */
public class MatchServiceImpl implements MatchService {

	private static PlayerDao playerDao;
	private static TeamDao teamDao;
	private boolean stopExecution = false;
	private static final String ROLE = "role";
	private static final String BATTING_SKILLS = "batting_skills";
	private static final String BOWLING_SKILLS = "bowling_skills";
	private static final String WICKETKEEPING_SKILLS = "wicketkeeping_skills";
	private static final String BOWLING_TYPES = "bowling_type";
	private static final String MATCHPLAYER = "matchPlayer";
	public static Map<Integer, List<Integer>> invalidResults;
	public static Map<Integer, List<Integer>> invalidExtraResults;
	public static Map<String, String> pitchFactors;
	private static Integer[] savedLineup;
	private static int counter = 0;
	private static final long MAX_COUNTER_VALUE = 100000000;

	public MatchServiceImpl(GameType gameType) {
		playerDao = InstanceProvider.getInstance(PlayerDao.class);
		teamDao = InstanceProvider.getInstance(TeamDao.class, gameType);
	}

	/**
	 * Initializes invalid result map
	 */
	static {

		Map<Integer, List<Integer>> invalidResults = new HashMap<Integer, List<Integer>>();
		Map<Integer, List<Integer>> invalidExtraResults = new HashMap<Integer, List<Integer>>();
		invalidResults.put(1, new ArrayList<Integer>(Arrays.asList(new Integer[] { 2, 3 })));
		invalidResults.put(2, new ArrayList<Integer>(Arrays.asList(new Integer[] { 3 })));
		MatchServiceImpl.invalidResults = invalidResults;

		invalidExtraResults.put(1, new ArrayList<Integer>(Arrays.asList(new Integer[] { 1, 2, 3 })));
		invalidExtraResults.put(2, new ArrayList<Integer>(Arrays.asList(new Integer[] { 2, 3 })));
		MatchServiceImpl.invalidExtraResults = invalidExtraResults;

	}

	/**
	 * Initializes pitch factors
	 */
	static {

		Map<String, String> pitchFactors = new HashMap<String, String>();
		pitchFactors.put(MatchConstants.SPINNER, MatchConstants.CRUMBLING);
		pitchFactors.put(MatchConstants.PACER, MatchConstants.GREEN);
		MatchServiceImpl.pitchFactors = pitchFactors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#setRoles(com.isl.model.Team)
	 */
	@Override
	public void setRoles(Team team) {

		MatchUtil.set(team, ROLE, PlayerDao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#setBattingSkills(com.isl.model.Team)
	 */
	@Override
	public void setBattingSkills(Team team) {

		MatchUtil.set(team, BATTING_SKILLS, PlayerDao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#setBowlingSkills(com.isl.model.Team)
	 */
	@Override
	public void setBowlingSkills(Team team) {

		MatchUtil.set(team, BOWLING_SKILLS, PlayerDao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.match.service.MatchServicee#setWicketKeepingSkills(com.isl.model.
	 * Team)
	 */
	@Override
	public void setWicketKeepingSkills(Team team) {

		MatchUtil.set(team, WICKETKEEPING_SKILLS, PlayerDao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#setBowlingTypes(com.isl.model.Team)
	 */
	@Override
	public void setBowlingTypes(Team team) {

		MatchUtil.set(team, BOWLING_TYPES, PlayerDao.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#setMatchPlayers(com.isl.model.Team)
	 */
	@Override
	public void setMatchPlayers(Team team) {

		MatchUtil.set(team, MATCHPLAYER, MatchPlayer.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#setWicketKeeper(com.isl.model.Team)
	 */
	@Override
	public void setWicketKeeper(Team team) {

		List<Player> players = team.getPlayers();
		Player wicketKeeper = players.get(0);
		int max_skills = 0;
		int player_skills;
		for (Player player : players) {
			player_skills = player.getWicketkeeping_skills();
			if (max_skills < player_skills) {
				max_skills = player_skills;
				wicketKeeper = player;
			}
		}
		team.setWicket_keeper(wicketKeeper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#setBowlingLineup(com.isl.model.Team,
	 * int)
	 */
	@Override
	public void setBowlingLineup(Team team, Game game) {

		List<Player> bowlingLineup = new ArrayList<Player>(team.getPlayers());
		Collections.sort(bowlingLineup, new BowlingSkillsComparator());
		List<Player> players = bowlingLineup.stream().limit(6).collect(Collectors.toList());
		if (players.get(4).getBowling_skills() - players.get(5).getBowling_skills() > 5) {
			team.setReserved(players.remove(5));
		} else {
			if (!isPitchFactor(players.get(4).getBowling_type(), MatchEngine.match.getPitch_type())
					&& isPitchFactor(players.get(5).getBowling_type(), MatchEngine.match.getPitch_type())) {
				team.setReserved(players.remove(4));
			} else {
				team.setReserved(players.remove(5));
			}
		}
		Collections.sort(players, new BowlingTypeComparator());
		setBowlingLineup(new Integer[game.getMax_overs()], players, 0, new int[5], 0, 0, team);
		stopExecution = false;
	}

	/**
	 * Set bowling line-up recursively
	 * 
	 * @param players
	 * @param playerList
	 * @param index
	 * @param overs
	 * @param count
	 * @param checked
	 * @param team
	 */
	protected void setBowlingLineup(Integer[] players, List<Player> playerList, int index, int[] overs, int count,
			int checked, Team team) {

		counter++;
		if (counter > MAX_COUNTER_VALUE) {
			savedLineup = teamDao.getLineup();
			setBowlingLineup(team, playerList, savedLineup);
			stopExecution = true;
		}
		if (stopExecution) {
			return;
		}
		if (count == players.length) {
			teamDao.addNewLineup(players);
			setBowlingLineup(team, playerList, players);
			stopExecution = true;
			return;
		} else if (checked == players.length) {
			return;
		}
		boolean isAdded = false;
		checked++;
		RandomGenerator randomGenerator = RandomUtil.getRandomGenerator(4);
		if (overs[index] != players.length / 5) {
			if (count == 0 || (count > 0 && !playerList.get(players[count - 1]).equals(playerList.get(index)))) {
				players[count++] = index;
				overs[index]++;
				isAdded = true;
			}
		}
		checkOtherAlternatives(players, playerList, index, overs, count, checked, team, randomGenerator);
		if (isAdded) {
			count--;
			overs[index]--;
			checkOtherAlternatives(players, playerList, index, overs, count, checked, team, randomGenerator);
		}
	}

	/**
	 * Check all other alternative ways of selecting bowling lineup
	 * 
	 * @param players
	 * @param playerList
	 * @param index
	 * @param overs
	 * @param count
	 * @param checked
	 * @param randomGenerator
	 */
	private void checkOtherAlternatives(Integer[] players, List<Player> playerList, int index, int[] overs, int count,
			int checked, Team team, RandomGenerator randomGenerator) {

		FunctionUtil.times(4, i -> {
			int nextIndex = (index + randomGenerator.generateUniqueRandom()) % 5;
			setBowlingLineup(players, playerList, nextIndex, overs, count, checked, team);
		});
	}

	/**
	 * Sets bowling line up
	 * 
	 * @param players
	 * @param team
	 * @param playerList
	 * @param source
	 */
	private void setBowlingLineup(Team team, List<Player> playerList, Integer[] source) {
		List<Player> lineup = new ArrayList<Player>(source.length);
		FunctionUtil.forEach(source, playerIndex -> {
			lineup.add(playerList.get(playerIndex));
		});
		team.setBowling_lineup(lineup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#updateCareerRecords(com.match.model.
	 * Match)
	 */
	@Override
	public void updateCareerRecords(Match match) {

		FunctionUtil.forEach(new Team[] { match.getBatting_team(), match.getBowling_team() },
				team -> updateCareeeRecordsForTeam(team));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.match.service.MatchServicee#updateCareeeRecordsForTeam(com.isl.model.
	 * Team)
	 */
	@Override
	public void updateCareeeRecordsForTeam(Team team) {

		team.getPlayers().forEach(player -> {
			if (GameType.ODI.equals(MatchEngine.game.getGameType())) {
				playerDao.updateODICareerRuns(player.getName(), player.getMatchPlayer().getRuns_scored());
				playerDao.updateODICareerInnings(player.getName(), player.getMatchPlayer().hasBatted());
				playerDao.updateODICareerNotouts(player.getName(), player.getMatchPlayer().isNotOut());
				playerDao.updateODICareerAverage(player.getName());
				playerDao.updateODICareerWickets(player.getName(), player.getMatchPlayer().getWickets_taken());
				playerDao.updateODICareerMatches(player.getName());
				playerDao.updateODIHighestScore(player.getName(), player.getMatchPlayer().getRuns_scored());
			} else {
				playerDao.updateT20ICareerRuns(player.getName(), player.getMatchPlayer().getRuns_scored());
				playerDao.updateT20ICareerInnings(player.getName(), player.getMatchPlayer().hasBatted());
				playerDao.updateT20ICareerNotouts(player.getName(), player.getMatchPlayer().isNotOut());
				playerDao.updateT20ICareerAverage(player.getName());
				playerDao.updateT20ICareerWickets(player.getName(), player.getMatchPlayer().getWickets_taken());
				playerDao.updateT20ICareerMatches(player.getName());
				playerDao.updateT20IHighestScore(player.getName(), player.getMatchPlayer().getRuns_scored());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#refreshMatchPlayers(com.match.model.
	 * Match)
	 */
	@Override
	public void refreshMatchPlayers(Match match) {

		FunctionUtil.forEach(new Team[] { match.getBatting_team(), match.getBowling_team() },
				team -> refreshMatchPlayers(team));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.match.service.MatchServicee#refreshMatchPlayers(com.isl.model.Team)
	 */
	@Override
	public void refreshMatchPlayers(Team team) {

		List<Player> players = team.getPlayers();
		players.forEach(player -> {
			player.setMatchPlayer(new MatchPlayer());
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#resetMatchFactors(com.match.service.
	 * MatchFactors)
	 */
	@Override
	public MatchFactors resetMatchFactors(MatchFactors matchFactors) {

		Field[] fields = matchFactors.getClass().getDeclaredFields();
		FunctionUtil.forEach(fields, field -> {
			try {
				if (!field.isAccessible()) {
					field.setAccessible(true);
					field.setBoolean(matchFactors, false);
					field.setAccessible(false);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return matchFactors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#validateResult(int, int)
	 */
	@Override
	public boolean validateResult(int required, int result) {

		if (MatchServiceImpl.invalidResults.containsKey(required)) {
			if (MatchServiceImpl.invalidResults.get(required).indexOf(result) > -1) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#validateResult(int, int)
	 */
	@Override
	public boolean validateExtraResult(int required, int result) {

		if (MatchServiceImpl.invalidExtraResults.containsKey(required)) {
			if (MatchServiceImpl.invalidExtraResults.get(required).indexOf(result) > -1) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.match.service.MatchServicee#isPitchFactor(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean isPitchFactor(String bowling_type, String pitch_type) {

		if (MatchServiceImpl.pitchFactors.containsKey(bowling_type)) {
			if (MatchServiceImpl.pitchFactors.get(bowling_type).compareTo(pitch_type) == 0) {
				return true;
			}
		}
		return false;
	}

}
