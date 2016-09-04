package com.match.service;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.isl.comparators.BowlingSkillsComparator;
import com.isl.comparators.BowlingTypeComparator;
import com.isl.model.MatchPlayer;
import com.isl.model.Player;
import com.isl.model.Team;
import com.isl.service.PlayerDao;
import com.match.config.InstanceProvider;
import com.match.data.MatchConstants;
import com.match.model.Match;
import com.match.util.MatchUtil;

/**
 * 
 * @author PUSHPAK
 *
 */
public class MatchService {
		
	private PlayerDao playerDao = InstanceProvider.getInstance(PlayerDao.class);
	private int MATCH_END_INDEX = MatchConstants.MATCH_END_INDEX;
	private static final String ROLE = "role";
	private static final String BATTING_SKILLS = "batting_skills";
	private static final String BOWLING_SKILLS = "bowling_skills";
	private static final String BOWLING_TYPES = "bowling_type";
	private static final String MATCHPLAYER = "matchPlayer";
	public static Map<Integer, Integer> invalidResults;
	public static Map<Integer, Integer> invalidExtraResults;
	public static Map<String, String> pitchFactors;
	
	/**
	 * Initialzes invalid result map
	 */
	static {
		
		Map<Integer, Integer> invalidResults = new HashMap<Integer, Integer>();
		Map<Integer, Integer> invalidExtraResults = new HashMap<Integer, Integer>();
		invalidResults.put(1, 2);
		invalidResults.put(1, 3);
		invalidResults.put(2, 3);
		MatchService.invalidResults = invalidResults;
		
		invalidExtraResults.put(1, 1);
		invalidExtraResults.put(1, 2);
		invalidExtraResults.put(1, 3);
		invalidExtraResults.put(2, 2);
		invalidExtraResults.put(2, 3);
		MatchService.invalidExtraResults = invalidExtraResults;
	}
	
	/**
	 * Initializes pitch factors
	 */
	static {
		
		Map<String, String> pitchFactors = new HashMap<String, String>();
		pitchFactors.put(MatchConstants.SPINNER, MatchConstants.CRUMBLING);
		pitchFactors.put(MatchConstants.PACER, MatchConstants.GREEN);
		MatchService.pitchFactors = pitchFactors;
	}
	
	/**
	 * Sets roles for team players
	 * @param team
	 */
	public void setRoles(Team team) {
		
		MatchUtil.set(team, ROLE, PlayerDao.class);
	}
	
	/**
	 * Sets batting skills for team players
	 * @param team
	 */
	public void setBattingSkills(Team team) {
		
		MatchUtil.set(team, BATTING_SKILLS, PlayerDao.class);
	}
	
	/**
	 * Sets bowling skills for team players
	 * @param team
	 */
	public void setBowlingSkills(Team team) {
		
		MatchUtil.set(team, BOWLING_SKILLS, PlayerDao.class);
	}
	
	/**
	 * Sets bowling types for team players
	 * @param team
	 */
	public void setBowlingTypes(Team team) {
		
		MatchUtil.set(team, BOWLING_TYPES, PlayerDao.class);
	}
	
	/**
	 * Sets {@link MatchPlayer} for team {@link Team} players
	 * @param team
	 */
	public void setMatchPlayers(Team team) {
		
		MatchUtil.set(team, MATCHPLAYER, MatchPlayer.class);
	}
	
	/**
	 * Sets bowling line-up
	 * @param team
	 */
	public void setBowlingLineup(Team team) {
		
		Collections.sort(team.getPlayers(), new BowlingSkillsComparator());
		List<Player> players = team.getPlayers().stream()
									.filter(player ->
											team.getPlayers().indexOf(player) < 5)
									.collect(Collectors.toList());
		Collections.sort(players, new BowlingTypeComparator());
		int lastIndex = 4;
		Map<Integer, Player> oversMap = new HashMap<Integer, Player>();
		int overIndex = 1;
		int bowlerIndex = 0;
		while(overIndex <= MATCH_END_INDEX) {
			oversMap.put(overIndex, players.get(bowlerIndex));
			overIndex++;
			if(bowlerIndex == lastIndex) {
				bowlerIndex = 0;
			} else {
				bowlerIndex++;
			}
		}
		team.setOversMap(oversMap);
	}

	/**
	 * Updates statistical records for match {@link Match}
	 * @param match
	 */
	public void updateCareerRecords(Match match) {
		
		Team team;
		team = match.getBatting_team();
		updateCareeeRecordsForTeam(team);
		team = match.getBowling_team();
		updateCareeeRecordsForTeam(team);
	}
	
	/**
	 * Updates statistical records for team {@link Team}
	 * @param team
	 */
	public void updateCareeeRecordsForTeam(Team team) {
		
		for(Player player:team.getPlayers()) {
			playerDao.updateCareerRuns(player.getName(), player.getMatchPlayer().getRuns_scored());
			playerDao.updateCareerWickets(player.getName(), player.getMatchPlayer().getWickets_taken());
			playerDao.updateCareerMatches(player.getName());
			playerDao.updateHighestScore(player.getName(), player.getMatchPlayer().getRuns_scored());
		}
	}
	
	/**
	 * Refreshes the matchplayers for superover
	 * @param match
	 */
	public void refreshMatchPlayers(Match match) {
		
		Team team;
		team = match.getBatting_team();
		refreshMatchPlayers(team);
		team = match.getBowling_team();
		refreshMatchPlayers(team);
	}
	
	/**
	 * Refreshes the matchplayers {@link MatchPlayer} of a team for superover
	 * @param team
	 */
	public void refreshMatchPlayers(Team team) {
		
		List<Player> players = team.getPlayers();
		players.forEach(player -> {
				MatchPlayer matchPlayer = new MatchPlayer();
				player.setMatchPlayer(matchPlayer);
		});
	}
	
	/**
	 * Resets matchfactors
	 * @param matchFactors
	 * @return
	 */
	public MatchFactors resetMatchFactors(MatchFactors matchFactors) {
		
		Field[] fields = matchFactors.getClass().getDeclaredFields();
		for(Field field:fields) {
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
	
	/**
	 * Returns whether result is valid
	 * @param required
	 * @param result
	 * @return
	 */
	public boolean validateResult(int required, int result) {
		
		if(MatchService.invalidResults.containsKey(required)) {
			if(MatchService.invalidResults.get(required).compareTo(result) == 0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isPitchFactor(String bowling_type, String pitch_type) {
		
		if(MatchService.pitchFactors.containsKey(bowling_type)) {
			if(MatchService.pitchFactors.get(bowling_type).compareTo(pitch_type) == 0) {
				return true;
			}
		}
		return false;
	}
	
}
