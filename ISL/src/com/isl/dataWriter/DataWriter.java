package com.isl.dataWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.cricket.data.AuctionConstants;
import com.isl.model.Player;
import com.isl.model.Team;

public class DataWriter {

	public void writeToFile(Team team) {

		List<Player> players = team.getPlayers();
		Iterator<Player> itr = players.iterator();
		String dir = AuctionConstants.DATA_DIR;
		String file_name = AuctionConstants.TEAM_FILE_NAME;

		try {
			new File(dir + team.getName()).mkdirs();
			File file = new File(dir + team.getName() + file_name);

			file.createNewFile();

			FileWriter fileWritter = new FileWriter(file);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			while (itr.hasNext()) {
				Player data = (Player) itr.next();
				bufferWritter.write(data.getName());
				bufferWritter.newLine();
			}
			bufferWritter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
