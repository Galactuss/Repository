package com.match.matchEngineRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cricket.machine.AuctionMachine;
import com.cricket.machine.SquadCreator;
import com.cricket.data.AuctionConstants;
import com.isl.model.Player;
import com.isl.model.Team;
import com.isl.service.TeamCreator;
import com.util.FunctionUtil;
import com.util.InstanceProvider;
import com.isl.model.GameType;
import com.match.service.MatchEngine;
import com.match.service.impl.MatchServiceImpl;
import com.match.service.TeamSelector;
import com.match.service.MatchService;
import com.match.service.impl.TeamSelectorImpl;

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
		GameType gameType;
		boolean validIndex = false;
		boolean runAuction = false;
		boolean isError = false;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to play ISL/Normal match?");
		boolean isISL = false;
		String matchType = scanner.next();
		String[] displayTeams;
		if (matchType.equals("ISL")) {
			isISL = true;
			displayTeams = AuctionConstants.TEAM_NAMES;
			while (!runAuction) {
				System.out.println("Do you want to run auction?(yes/no)");
				run = scanner.next();
				if (("yes").equals(run)) {
					AuctionMachine auctionMachine = InstanceProvider.getInstance(AuctionMachine.class);
					auctionMachine.runAuction();
				}
				runAuction = true;
			}
		} else {
			while (!runAuction) {
				System.out.println("Do you want to shuffle team selection?(yes/no)");
				run = scanner.next();
				if (("yes").equals(run)) {
					SquadCreator squadCreator = InstanceProvider.getInstance(SquadCreator.class);
					squadCreator.createSquads();
				}
				runAuction = true;
			}
			isISL = false;
			displayTeams = AuctionConstants.COUNTRY_NAMES;
		}
		while (!validIndex) {
			isError = false;
			System.out.println("Select the teams you would like to see play:");
			FunctionUtil.times(displayTeams.length, index -> {
				System.out.println((index + 1) + ". " + displayTeams[index]);
			});
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
			} else if (teamIndexOne > displayTeams.length || teamIndexTwo > displayTeams.length || teamIndexOne < 0 || teamIndexTwo < 0 || isError) {
				System.out.println("Please enter valid index.");
				validIndex = false;
			} else {
				validIndex = true;
			}
		}
		System.out.println("Match type?(ODI/T20I)");
		String type = scanner.next();
		if (("ODI").equals(type)) {
			gameType = GameType.ODI;
		} else {
			gameType = GameType.T20I;
		}
		scanner.close();
		matchService = InstanceProvider.getInstance(MatchServiceImpl.class, gameType);
		TeamCreator teamCreator = new TeamCreator();
		TeamSelector teamSelector = new TeamSelectorImpl();
		List<Team> teams = new ArrayList<Team>();
		String[] teamList = { displayTeams[teamIndexOne - 1],
				displayTeams[teamIndexTwo - 1] };
		for (String teamName : teamList) {
			Team teamsGenerated = teamCreator.getSquad(teamName);
			if (isISL) {
				teamsGenerated = teamCreator.getISLTeam(teamsGenerated);
			}
			matchService.setRoles(teamsGenerated);
			matchService.setBattingSkills(teamsGenerated);
			matchService.setBowlingSkills(teamsGenerated);
			matchService.setBowlingTypes(teamsGenerated);
			matchService.setWicketKeepingSkills(teamsGenerated);
			Team team = teamSelector.balanceTeam(teamsGenerated, !isISL);
			System.out.println("Team: " + team.getName());
			List<Player> players = team.getPlayers();
			FunctionUtil.times(11, index -> {
				System.out.println(index + 1 + ". " + players.get(index).getName());
			});
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
			com.isl.dataWriter.DataWriter dataWriter = InstanceProvider.getInstance(com.isl.dataWriter.DataWriter.class);
			dataWriter.writeToFile(team);
		}
		MatchEngine matchEngine = new MatchEngine();
		matchEngine.runMatchEngine(teams.get(0), teams.get(1), gameType);

	}

}
