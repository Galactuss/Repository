package com.match.matchEngineRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cricket.auctionMachine.AuctionMachine;
import com.cricket.data.AuctionConstants;
import com.isl.model.Player;
import com.isl.model.Team;
import com.isl.service.TeamCreator;
import com.match.config.MatchConfigurer;
import com.match.service.MatchEngine;
import com.match.service.MatchService;
import com.match.service.TeamSelector;

/**
 * 
 * @author Pushpak
 *
 */
public class MatchEngineRunner {

	private static MatchService matchService;

	public static void main(String[] args) {

		int teamIndexOne = 0;
		int teamIndexTwo = 0;
		String run = null;
		boolean validIndex = false;
		boolean runAuction = false;
		boolean isError = false;
		matchService = (MatchService) MatchConfigurer.getInstance("MatchService");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (!runAuction) {
			System.out.println("Do you want to run auction?(yes/no)");
			run = scanner.next();
			if (("yes").equals(run)) {
				AuctionMachine auctionMachine = new AuctionMachine();
				auctionMachine.runAuction();
			}
			runAuction = true;
		}
		while (!validIndex) {
			isError = false;
			System.out.println("Select the teams you would like to see play:");
			for (int index = 0; index < AuctionConstants.TEAM_NAMES.length; index++) {
				System.out.println((index + 1) + ". " + AuctionConstants.TEAM_NAMES[index]);
			}
			if (scanner.hasNextInt()) {
				teamIndexOne = scanner.nextInt();
			} else {
				scanner.next();
				isError = true;
			}
			if (scanner.hasNextInt()) {
				teamIndexTwo = scanner.nextInt();
			} else {
				scanner.next();
				isError = true;
			}
			if (teamIndexOne == teamIndexTwo) {
				System.out.println("Please enter different team index.");
				validIndex = false;
			} else if (teamIndexOne > 8 || teamIndexTwo > 8 || teamIndexOne < 0 || teamIndexTwo < 0 || isError) {
				System.out.println("Please enter valid index.");
				validIndex = false;
			} else {
				validIndex = true;
			}
		}
		TeamCreator teamCreator = new TeamCreator();
		TeamSelector teamSelector = new TeamSelector();
		List<Team> teams = new ArrayList<Team>();
		String[] teamList = { AuctionConstants.TEAM_NAMES[teamIndexOne - 1],
				AuctionConstants.TEAM_NAMES[teamIndexTwo - 1] };
		for (String teamName : teamList) {
			Team teamsGenerated = teamCreator.getTeam(teamCreator.getSquad(teamName));
			matchService.setRoles(teamsGenerated);
			Team team = teamSelector.balanceTeam(teamsGenerated);
			System.out.println("Team: " + team.getName());
			List<Player> players = new ArrayList<Player>();
			players = team.getPlayers();
			for (int index = 0; index < 11; index++) {
				System.out.println(index + 1 + ". " + players.get(index).getName());
			}
			/*
			 * System.out.println("Set the batting order:"); for(int index=0;
			 * index<11; index++) {
			 * System.out.println("Position "+(index+1)+" player index:"); int
			 * playerIndex = scanner.nextInt(); playerIndex--; Player player =
			 * players.get(playerIndex); player.setPosition(index+1);
			 * MatchPlayer matchPlayer = new MatchPlayer();
			 * player.setMatchPlayer(matchPlayer); playersNew.add(player); }
			 */
			matchService.setMatchPlayers(team);
			matchService.setBattingSkills(team);
			matchService.setBowlingSkills(team);
			matchService.setBowlingTypes(team);
			matchService.setBowlingLineup(team);
			/*
			 * Map<Integer, Player> oversMap = new HashMap<Integer, Player>();
			 * System.out.println("Set bowling line-up"); for(int overIndex=1;
			 * overIndex<21; overIndex++) {
			 * System.out.println("Choose bowler for over "+overIndex); for(int
			 * playerIndex=0; playerIndex<11; playerIndex++) {
			 * System.out.println(playerIndex+1+". "+players.get(playerIndex).
			 * getName()); } int playerNum = scanner.nextInt();
			 * oversMap.put(overIndex, players.get(playerNum-1)); }
			 * team.setOversMap(oversMap);
			 */
			teams.add(team);
			com.isl.dataWriter.DataWriter dataWriter = new com.isl.dataWriter.DataWriter();
			dataWriter.writeToFile(team);
		}
		matchService.initializeInValidResults();
		matchService.initializePitchFactors();
		MatchEngine matchEngine = new MatchEngine();
		matchEngine.runMatchEngine(teams.get(0), teams.get(1));

	}

}
