package com.isl.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.cricket.data.AuctionConstants;
import com.isl.model.Player;
import com.isl.model.Team;

public class TeamCreator {

	public Team getTeam(Team team) {

		List<Player> players = team.getPlayers();
		List<Player> reserved = new ArrayList<Player>();
		List<Player> playersSelected = new ArrayList<Player>();
		List<Player> overseas_players = new ArrayList<Player>();
		List<Player> associate_players = new ArrayList<Player>();
		List<Player> domestic_players = new ArrayList<Player>();
		List<Player> indian_players = new ArrayList<Player>();

		for (int index = 0; index < 8; index++) {
			overseas_players.add(players.get(index));
		}

		for (int index = 8; index < 10; index++) {
			associate_players.add(players.get(index));
		}

		for (int index = 10; index < 12; index++) {
			domestic_players.add(players.get(index));
		}

		for (int index = 12; index < 18; index++) {
			indian_players.add(players.get(index));
		}

		Random randomGenerator = new Random();
		for (int index = 0; index < 3; index++) {
			int random = randomGenerator.nextInt(overseas_players.size() - 1);
			reserved.add(overseas_players.get(random));
			overseas_players.remove(random);
		}

		for (int index = 0; index < 1; index++) {
			int random = randomGenerator.nextInt(associate_players.size() - 1);
			reserved.add(associate_players.get(random));
			associate_players.remove(random);
		}

		for (int index = 0; index < 1; index++) {
			int random = randomGenerator.nextInt(domestic_players.size() - 1);
			reserved.add(domestic_players.get(random));
			domestic_players.remove(random);
		}

		for (int index = 0; index < 2; index++) {
			int random = randomGenerator.nextInt(indian_players.size() - 1);
			reserved.add(indian_players.get(random));
			indian_players.remove(random);
		}

		playersSelected.addAll(overseas_players);
		playersSelected.addAll(associate_players);
		playersSelected.addAll(domestic_players);
		playersSelected.addAll(indian_players);
		playersSelected.addAll(reserved);
		team.setPlayers(playersSelected);

		return team;
	}

	@SuppressWarnings("finally")
	public Team getSquad(String teamName) {

		Team team = new Team();
		team.setName(teamName);
		List<Player> players = new ArrayList<Player>();
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(AuctionConstants.COUNTRIES));
		list.add(AuctionConstants.INDIA);
		list.add(AuctionConstants.DOMESTIC);
		list.addAll(Arrays.asList(AuctionConstants.ASSOCIATE_COUNTRIES));
		Object[] countries = list.toArray();

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(
					new FileReader(AuctionConstants.DATA_DIR + teamName + AuctionConstants.AUCTION_FILE_NAME));

			while ((sCurrentLine = br.readLine()) != null) {
				Player player = new Player();
				for (Object country : countries) {
					if (sCurrentLine.contains(country.toString())) {
						String name = sCurrentLine.substring(0, sCurrentLine.indexOf(country.toString()) - 1);
						String type = sCurrentLine.substring(sCurrentLine.lastIndexOf(" ") + 1);
						player.setName(name);
						player.setCountry(country.toString());
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
