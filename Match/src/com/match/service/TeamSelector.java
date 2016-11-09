package com.match.service;

import com.isl.model.Team;

public interface TeamSelector {

	/**
	 * Returns a balanced team from randomly generated team
	 * 
	 * @param teams
	 *            Array of teams- team object and second reserved players team
	 *            object
	 * @return Balanced team
	 */
	Team balanceTeam(Team fullteam);

}