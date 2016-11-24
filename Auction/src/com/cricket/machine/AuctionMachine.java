package com.cricket.machine;

import java.util.List;

import com.cricket.data.AuctionConstants;
import com.cricket.dataWriter.DataWriter;
import com.cricket.model.ISLSquad;
import com.cricket.service.PlayersService;
import com.cricket.util.AuctionUtil;
import com.util.FunctionUtil;
import com.util.InstanceProvider;

public class AuctionMachine {

	public void runAuction() {

		PlayersService service = InstanceProvider.getInstance(PlayersService.class);
		List<String> overseasAuctionList = service.getForeignPlayers();
		List<String> associateAuctionList = service.getAssociatePlayers();
		List<String> domesticAuctionList = service.getDomesticPlayers();
		List<String> indianAuctionList = service.getIndianPlayers();
		DataWriter dataWriter = new DataWriter();

		FunctionUtil.forEach(AuctionConstants.TEAM_NAMES, teamName -> {
			ISLSquad squad = new ISLSquad();
			squad.setName(teamName);
			squad.setoverseas_players(AuctionUtil.pickPlayers(overseasAuctionList, 8, false));
			squad.setAssociate_players(AuctionUtil.pickPlayers(associateAuctionList, 2, false));
			squad.setDomestic_players(AuctionUtil.pickPlayers(domesticAuctionList, 2, false));
			squad.setIndian_capped_players(AuctionUtil.pickPlayers(indianAuctionList, 6, false));
			dataWriter.writeToFile(squad);
		});
	}
}
