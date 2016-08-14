package com.match.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.isl.model.Player;
import com.isl.model.Team;
import com.match.data.MatchConstants;

/**
 * 
 * @author Pushpak
 *
 */
public class TeamSelector {

	private final String BATSMAN = MatchConstants.BATSMAN;
	private final String BISKILLED = MatchConstants.BISKILLED;
	private final String BOWLER = MatchConstants.BOWLER;
	private final int MAX_ALLOWED_BOWLERS = 4;
	private final int MAX_ALLOWED_BATSMEN = 7;
	private final int START_INDEX = 0;

	/**
	 * Returns a balanced team from randomly generated team
	 * 
	 * @param teams
	 *            Array of teams- team object and second reserved players team
	 *            object
	 * @return Balanced team
	 */
	public Team balanceTeam(Team fullteam) {

		Team team = fullteam;
		//Team reserved = teams[1];
		List<Player> replacements;
		int count = 0;
		List<Player> batsmen = team.getPlayers().stream()
				.limit(11)
				.filter(player -> (BATSMAN).equals(player.getRole()) || (BISKILLED).equals(player.getRole()))
				.collect(Collectors.toList());
		List<Player> bowlers = team.getPlayers().stream().limit(11).filter(player -> (BOWLER).equals(player.getRole()))
				.collect(Collectors.toList());
		if (bowlers.size() > MAX_ALLOWED_BOWLERS) {
			Collections.shuffle(bowlers);
			while (bowlers.size() > MAX_ALLOWED_BOWLERS && count < bowlers.size()) {
				replacements = team.getPlayers().stream()
						.skip(11)
						.filter(player -> player.getType().equals(bowlers.get(START_INDEX).getType()))
						.filter(player -> (BATSMAN).equals(player.getRole()) || (BISKILLED).equals(player.getRole()))
						.collect(Collectors.toList());
				if (replacements.size() > 0) {
					batsmen.add(replacements.get(START_INDEX));
					team.getPlayers().remove(replacements.get(START_INDEX));
					bowlers.remove(START_INDEX);
				} else {
					Player temp = bowlers.get(START_INDEX);
					bowlers.add(temp);
					bowlers.remove(START_INDEX);
				}
				count++;
			}
		} else if (batsmen.size() > MAX_ALLOWED_BATSMEN) {
			Collections.shuffle(batsmen);
			while (batsmen.size() > MAX_ALLOWED_BATSMEN && count < batsmen.size()) {
				replacements = team.getPlayers().stream()
						.skip(11)
						.filter(player -> player.getType().equals(batsmen.get(START_INDEX).getType()))
						.filter(player -> (BOWLER).equals(player.getRole())).collect(Collectors.toList());
				if (replacements.size() > 0) {
					bowlers.add(replacements.get(START_INDEX));
					team.getPlayers().remove(replacements.get(START_INDEX));
					batsmen.remove(START_INDEX);
				} else {
					Player temp = batsmen.get(START_INDEX);
					batsmen.add(temp);
					batsmen.remove(START_INDEX);
				}
				count++;
			}
		}
		List<Player> players = new ArrayList<Player>();
		players.addAll(batsmen);
		players.addAll(bowlers);
		team.setPlayers(players);
		return team;
	}
}
