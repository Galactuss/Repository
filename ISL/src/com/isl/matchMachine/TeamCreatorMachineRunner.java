package com.isl.matchMachine;

import com.isl.service.TablePopulator;

public class TeamCreatorMachineRunner {
	
	public static void main(String[] args) {
		
		/*AuctionMachine auctionMachine =  new AuctionMachine();
		auctionMachine.runAuction();
		TeamCreator teamCreator = new TeamCreator();
		for(String teamName:AuctionConstants.TEAM_NAMES) {
			Team team = teamCreator.getTeam(teamCreator.getSquad(teamName));
			com.isl.dataWriter.DataWriter dataWriter = new com.isl.dataWriter.DataWriter();
			dataWriter.writeToFile(team);
		}*/
		TablePopulator tablePopulator = new TablePopulator();
		tablePopulator.populateTable();
	}
}
