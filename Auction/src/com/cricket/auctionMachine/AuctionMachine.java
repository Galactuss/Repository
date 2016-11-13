package com.cricket.auctionMachine;

import java.util.List;

import com.cricket.data.AuctionConstants;
import com.cricket.dataWriter.DataWriter;
import com.cricket.model.Squad;
import com.cricket.service.PlayersService;
import com.cricket.util.A;
import com.cricket.util.AuctionUtil;

public class AuctionMachine {

	public void runAuction() {

		PlayersService players = new PlayersService();
		List<String> overseasAuctionList = players.getForeignPlayers();
		List<String> associateAuctionList = players.getAssociatePlayers();
		List<String> domesticAuctionList = players.getDomesticPlayers();
		List<String> indianAuctionList = players.getIndianPlayers();
		DataWriter dataWriter = new DataWriter();

		A.forEach(AuctionConstants.TEAM_NAMES, teamName -> {
			Squad squad = new Squad();
			squad.setName(teamName);
			squad.setoverseas_players(AuctionUtil.pickPlayers(overseasAuctionList, 8));
			squad.setAssociate_players(AuctionUtil.pickPlayers(associateAuctionList, 2));
			squad.setDomestic_players(AuctionUtil.pickPlayers(domesticAuctionList, 2));
			squad.setIndian_capped_players(AuctionUtil.pickPlayers(indianAuctionList, 6));
			dataWriter.writeToFile(squad);
		});
	}
}
