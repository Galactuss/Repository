package com.isl.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.*;

import com.cricket.data.AuctionConstants;
import com.isl.model.Player;
import com.isl.model.Team;

public class TeamCreator {

	public Team getTeam(Team team) {

		List<Player> players = team.getPlayers();
		int[] playersMaxIndex = { 7, 1, 1, 5 };
		int[] playerCount = { 3, 1, 1, 2 };
		Random randomGenerator = new Random();
		int startIndex = 0;
		int i = 0;
		while (i < 4) {
			for (int index = 0; index < playerCount[i]; index++) {
				int playerIndex = randomGenerator.nextInt(playersMaxIndex[i]) + startIndex;
				Player temp = players.get(playerIndex);
				players.remove(playerIndex);
				players.add(temp);
				playersMaxIndex[i]--;
			}
			startIndex += playersMaxIndex[i] + 1;
			i++;
		}

		return team;
	}

	@SuppressWarnings("finally")
	public Team getSquad(String teamName) {

		Team team = new Team();
		String[] countries = ArrayUtils.addAll(ArrayUtils.addAll(AuctionConstants.COUNTRIES, AuctionConstants.INDIA), ArrayUtils.addAll(AuctionConstants.ASSOCIATE_COUNTRIES, AuctionConstants.DOMESTIC));
		team.setName(teamName);
		List<Player> players = new ArrayList<Player>();
		
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(
					new FileReader(AuctionConstants.DATA_DIR + teamName + AuctionConstants.AUCTION_FILE_NAME));

			while ((sCurrentLine = br.readLine()) != null) {
				Player player = new Player();
				for (String country : countries) {
					if (sCurrentLine.contains(country)) {
						String name = sCurrentLine.substring(0, sCurrentLine.indexOf(country) - 1);
						String type = sCurrentLine.substring(sCurrentLine.lastIndexOf(" ") + 1);
						player.setName(name);
						player.setCountry(country);
						player.setType(type);
						players.add(player);
					}
				}
			}
			team.setPlayers(players);

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null) {
					br.close();

				}
			} catch (IOException ex) {

				ex.printStackTrace();

			}

			return team;

		}
	}
}
