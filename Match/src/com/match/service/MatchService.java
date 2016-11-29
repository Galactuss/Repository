package com.match.service;

import com.isl.model.MatchPlayer;
import com.isl.model.Team;
import com.match.model.Game;
import com.match.model.Match;

public interface MatchService {

	/**
	 * Sets roles for team players
	 * 
	 * @param team
	 */
	void setRoles(Team team);

	/**
	 * Sets batting skills for team players
	 * 
	 * @param team
	 */
	void setBattingSkills(Team team);

	/**
	 * Sets bowling skills for team players
	 * 
	 * @param team
	 */
	void setBowlingSkills(Team team);

	/**
	 * Sets wicketkeeping skills for team players
	 * 
	 * @param team
	 */
	void setWicketKeepingSkills(Team team);

	/**
	 * Sets bowling types for team players
	 * 
	 * @param team
	 */
	void setBowlingTypes(Team team);

	/**
	 * Sets {@link MatchPlayer} for team {@link Team} players
	 * 
	 * @param team
	 */
	void setMatchPlayers(Team team);

	/**
	 * Sets wicketkeeper for the team
	 * 
	 * @param team
	 */
	void setWicketKeeper(Team team);

	/**
	 * Sets bowling lineup
	 * 
	 * @param team
	 * @param max_overs
	 */
	void setBowlingLineup(Team team, Game game);

	/**
	 * Updates statistical records for match {@link Match}
	 * 
	 * @param match
	 */
	void updateCareerRecords(Match match);

	/**
	 * Updates statistical records for team {@link Team}
	 * 
	 * @param team
	 */
	void updateCareeeRecordsForTeam(Team team);

	/**
	 * Refreshes the matchplayers for superover
	 * 
	 * @param match
	 */
	void refreshMatchPlayers(Match match);

	/**
	 * Refreshes the matchplayers {@link MatchPlayer} of a team for superover
	 * 
	 * @param team
	 */
	void refreshMatchPlayers(Team team);

	/**
	 * Resets matchfactors
	 * 
	 * @param matchFactors
	 * @return
	 */
	MatchFactors resetMatchFactors(MatchFactors matchFactors);

	/**
	 * Returns whether result is valid
	 * 
	 * @param required
	 * @param result
	 * @return
	 */
	boolean validateResult(int required, int result);

	/**
	 * Returns whether result is valid
	 * 
	 * @param required
	 * @param result
	 * @return
	 */
	boolean validateExtraResult(int required, int result);
	
	/**
	 * Returns whether pitch factor is to be inserted
	 * @param bowling_type
	 * @param pitch_type
	 * @return
	 */
	boolean isPitchFactor(String bowling_type, String pitch_type);

}