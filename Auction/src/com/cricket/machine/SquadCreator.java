package com.cricket.machine;

import org.apache.commons.lang3.ArrayUtils;

import com.cricket.data.AuctionConstants;
import com.cricket.dataWriter.DataWriter;
import com.cricket.model.Country;
import com.cricket.model.NationalSquad;
import com.cricket.service.PlayersService;
import com.cricket.util.AuctionUtil;
import com.util.FunctionUtil;
import com.util.InstanceProvider;

/**
 * 
 * @author PUSHPAK
 *
 */
public class SquadCreator {

	public void createSquads() {
		PlayersService service = InstanceProvider.getInstance(PlayersService.class);
		Country[] countries = ArrayUtils.add(ArrayUtils.addAll(AuctionConstants.COUNTRIES, AuctionConstants.ASSOCIATE_COUNTRIES), AuctionConstants.INDIA);
		FunctionUtil.forEach(countries, country -> {
			NationalSquad squad = new NationalSquad();
			squad.setName(country.getName());
			squad.setPlayers(AuctionUtil.pickPlayers(service.getNationalSquadPlayers(country), 14, true));
			DataWriter writer = InstanceProvider.getInstance(DataWriter.class);
			writer.writeToFile(squad);
		});
	}
}
