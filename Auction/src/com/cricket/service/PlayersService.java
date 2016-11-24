package com.cricket.service;

import java.util.ArrayList;
import java.util.List;

import com.cricket.data.AuctionConstants;
import com.cricket.model.Country;
import com.util.FunctionUtil;

/**
 * 
 * @author Pushpak
 *
 */
public class PlayersService {

	/**
	 * Returns list of overseas players
	 * 
	 * @return
	 */
	public List<String> getForeignPlayers() {
		Country[] countries = AuctionConstants.COUNTRIES;
		return getPlayers(countries);
	}

	/**
	 * Returns list of associate players
	 * 
	 * @return
	 */
	public List<String> getAssociatePlayers() {
		Country[] associate_countries = AuctionConstants.ASSOCIATE_COUNTRIES;
		return getPlayers(associate_countries);
	}

	/**
	 * Returns list of domestic players
	 * 
	 * @return
	 */
	public List<String> getDomesticPlayers() {
		return getPlayers(AuctionConstants.DOMESTIC);
	}

	/**
	 * Returns list of Indian players
	 * 
	 * @return
	 */
	public List<String> getIndianPlayers() {
		return getPlayers(AuctionConstants.INDIA);
	}

	/**
	 * Returns list of players for country
	 * 
	 * @param country
	 * @return
	 */
	public List<String> getNationalSquadPlayers(Country country) {
		return getPlayers(country);
	}

	private List<String> getPlayers(Country[] countries) {
		List<String> players = new ArrayList<String>();
		FunctionUtil.forEach(countries, country -> {
			players.addAll(getPlayers(country));
		});
		return players;
	}

	private List<String> getPlayers(Country country) {
		List<String> players = new ArrayList<String>();
		FunctionUtil.forEach(country.getPlayers(), player -> {
			players.add(player + " " + country.getName() + " " + country.getType());
		});
		return players;
	}

}