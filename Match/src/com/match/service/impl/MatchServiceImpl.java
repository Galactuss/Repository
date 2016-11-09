package com.match.service.impl;

import java.lang.reflect.Field;
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
import com.cricket.config.InstanceProvider;
import com.match.data.MatchConstants;
import com.match.model.Game;
import com.match.model.Match;
import com.match.service.MatchFactors;
import com.match.service.MatchService;
import com.match.util.MatchUtil;
import com.match.util.RandomUtil;
import com.match.util.RandomUtil.RandomGenerator;

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
	public static Map<Integer, Integer> invalidResults;
	public static Map<Integer, Integer> invalidExtraResults;
	public static Map<String, String> pitchFactors;
	private static int[] savedLineup;
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

		Map<Integer, Integer> invalidResults = new HashMap<Integer, Integer>();
		Map<Integer, Integer> invalidExtraResults = new HashMap<Integer, Integer>();
		invalidResults.put(1, 2);
		invalidResults.put(1, 3);
		invalidResults.put(2, 3);
		MatchServiceImpl.invalidResults = invalidResults;

		invalidExtraResults.put(1, 1);
		invalidExtraResults.put(1, 2);
		invalidExtraResults.put(1, 3);
		invalidExtraResults.put(2, 2);
		invalidExtraResults.put(2, 3);
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

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#setRoles(com.isl.model.Team)
	 */
	@Override
	public void setRoles(Team team) {

		MatchUtil.set(team, ROLE, PlayerDao.class);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#setBattingSkills(com.isl.model.Team)
	 */
	@Override
	public void setBattingSkills(Team team) {

		MatchUtil.set(team, BATTING_SKILLS, PlayerDao.class);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#setBowlingSkills(com.isl.model.Team)
	 */
	@Override
	public void setBowlingSkills(Team team) {

		MatchUtil.set(team, BOWLING_SKILLS, PlayerDao.class);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#setWicketKeepingSkills(com.isl.model.Team)
	 */
	@Override
	public void setWicketKeepingSkills(Team team) {

		MatchUtil.set(team, WICKETKEEPING_SKILLS, PlayerDao.class);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#setBowlingTypes(com.isl.model.Team)
	 */
	@Override
	public void setBowlingTypes(Team team) {

		MatchUtil.set(team, BOWLING_TYPES, PlayerDao.class);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#setMatchPlayers(com.isl.model.Team)
	 */
	@Override
	public void setMatchPlayers(Team team) {

		MatchUtil.set(team, MATCHPLAYER, MatchPlayer.class);
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#setBowlingLineup(com.isl.model.Team, int)
	 */
	@Override
	public void setBowlingLineup(Team team, Game game) {

		Collections.sort(team.getPlayers(), new BowlingSkillsComparator());
		List<Player> players = team.getPlayers().stream().filter(player -> team.getPlayers().indexOf(player) < 5)
				.collect(Collectors.toList());
		Collections.sort(players, new BowlingTypeComparator());
		setBowlingLineup(new int[game.getMax_overs()], players, 0, new int[5], 0, 0, team);
		stopExecution = false;
	}

	/**
	 * Set bowling lineup recursively
	 * 
	 * @param players
	 * @param playerList
	 * @param index
	 * @param overs
	 * @param count
	 * @param checked
	 * @param team
	 */
	protected void setBowlingLineup(int[] players, List<Player> playerList, int index, int[] overs, int count,
			int checked, Team team) {

		counter++;
		if (counter > MAX_COUNTER_VALUE) {
			savedLineup = teamDao.getLineup();
			Player[] playerArr = new Player[players.length];
			int arrIndex = 0;
			for (int playerIndex : savedLineup) {
				playerArr[arrIndex++] = playerList.get(playerIndex);
			}
			team.setBowling_lineup(playerArr);
			stopExecution = true;
		}
		if (stopExecution) {
			return;
		}
		if (count == players.length) {
			teamDao.addNewLineup(players);
			Player[] playerArr = new Player[players.length];
			int arrIndex = 0;
			for (int playerIndex : players) {
				playerArr[arrIndex++] = playerList.get(playerIndex);
			}
			team.setBowling_lineup(playerArr);
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
	protected void checkOtherAlternatives(int[] players, List<Player> playerList, int index, int[] overs, int count,
			int checked, Team team, RandomGenerator randomGenerator) {

		int nextIndex = (index + randomGenerator.generateUniqueRandom()) % 5;
		setBowlingLineup(players, playerList, nextIndex, overs, count, checked, team);
		nextIndex = (index + randomGenerator.generateUniqueRandom()) % 5;
		setBowlingLineup(players, playerList, nextIndex, overs, count, checked, team);
		nextIndex = (index + randomGenerator.generateUniqueRandom()) % 5;
		setBowlingLineup(players, playerList, nextIndex, overs, count, checked, team);
		nextIndex = (index + randomGenerator.generateUniqueRandom()) % 5;
		setBowlingLineup(players, playerList, nextIndex, overs, count, checked, team);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#updateCareerRecords(com.match.model.Match)
	 */
	@Override
	public void updateCareerRecords(Match match) {

		Team team;
		team = match.getBatting_team();
		updateCareeeRecordsForTeam(team);
		team = match.getBowling_team();
		updateCareeeRecordsForTeam(team);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#updateCareeeRecordsForTeam(com.isl.model.Team)
	 */
	@Override
	public void updateCareeeRecordsForTeam(Team team) {

		for (Player player : team.getPlayers()) {
			playerDao.updateCareerRuns(player.getName(), player.getMatchPlayer().getRuns_scored());
			playerDao.updateCareerWickets(player.getName(), player.getMatchPlayer().getWickets_taken());
			playerDao.updateCareerMatches(player.getName());
			playerDao.updateHighestScore(player.getName(), player.getMatchPlayer().getRuns_scored());
		}
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#refreshMatchPlayers(com.match.model.Match)
	 */
	@Override
	public void refreshMatchPlayers(Match match) {

		Team team;
		team = match.getBatting_team();
		refreshMatchPlayers(team);
		team = match.getBowling_team();
		refreshMatchPlayers(team);
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#refreshMatchPlayers(com.isl.model.Team)
	 */
	@Override
	public void refreshMatchPlayers(Team team) {

		List<Player> players = team.getPlayers();
		players.forEach(player -> {
			MatchPlayer matchPlayer = new MatchPlayer();
			player.setMatchPlayer(matchPlayer);
		});
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#resetMatchFactors(com.match.service.MatchFactors)
	 */
	@Override
	public MatchFactors resetMatchFactors(MatchFactors matchFactors) {

		Field[] fields = matchFactors.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				field.setBoolean(matchFactors, false);
				field.setAccessible(false);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return matchFactors;
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#validateResult(int, int)
	 */
	@Override
	public boolean validateResult(int required, int result) {

		if (MatchServiceImpl.invalidResults.containsKey(required)) {
			if (MatchServiceImpl.invalidResults.get(required) == result) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.match.service.MatchServicee#isPitchFactor(java.lang.String, java.lang.String)
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
