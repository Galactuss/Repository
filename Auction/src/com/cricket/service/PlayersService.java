package com.cricket.service;

import java.util.ArrayList;
import java.util.List;

import com.cricket.data.AuctionConstants;

/**
 * 
 * @author Pushpak
 *
 */
public class PlayersService {
	
	/**
	 * Returns list of overseas players
	 * @return
	 */
	public List<String> getForeignPlayers() {
		int count = 0;
		List<String> players = new ArrayList<String>();
		String[] countries = AuctionConstants.COUNTRIES;
		for(String[] array:AuctionConstants.OVERSEAS_PLAYERS) {
			for(String player:array) {
				players.add(getPlayerInfo(player, countries[count], AuctionConstants.OVERSEAS));
			}
			count++;
		}
		return players; 
	}
	
	/**
	 * Returns list of associate players
	 * @return
	 */
	public List<String> getAssociatePlayers() {
		int count = 0;
		String[] associate_countries = AuctionConstants.ASSOCIATE_COUNTRIES;
		List<String> players = new ArrayList<String>();
		for(String[] array:AuctionConstants.ASSOCIATE_PLAYERS) {
			for(String player:array) {
				players.add(getPlayerInfo(player, associate_countries[count], AuctionConstants.ASSOCIATES));
			}
			count++;
		}
		return players; 
	}
	
	/**
	 * Returns list of domestic players
	 * @return
	 */
	public List<String> getDomesticPlayers() {
		List<String> players = new ArrayList<String>();
		for(String player:AuctionConstants.DOMESTIC_PLAYERS) {
			players.add(getPlayerInfo(player, AuctionConstants.DOMESTIC, AuctionConstants.DOMESTIC));
		}
		return players; 
	}
	
	/**
	 * Returns list of Indian players
	 * @return
	 */
	public List<String> getIndianPlayers() {
		List<String> players = new ArrayList<String>();
		for(String player:AuctionConstants.INDIAN_CAPPED_PLAYERS) {
			players.add(getPlayerInfo(player, AuctionConstants.INDIA, AuctionConstants.INDIAN));
		}
		return players;
	}
	
	/**
	 * Constructs player info to be written
	 * @param player
	 * @param country
	 * @param type
	 * @return
	 */
	public String getPlayerInfo(String player, String country, String type) {
		
		StringBuilder builder = new StringBuilder();
		builder.append(player)
				.append(AuctionConstants.EMPTY_SPACE)
				.append(country)
				.append(AuctionConstants.EMPTY_SPACE)
				.append(type);
		return builder.toString();
	}
}