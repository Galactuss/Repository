package com.cricket.dataWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.cricket.data.AuctionConstants;
import com.cricket.model.Squad;

/**
 * 
 * @author Pushpak
 *
 */
public class DataWriter {
	
	/**
	 * Writes data to a file
	 * @param squad
	 */
	public void writeToFile(Squad squad) {
		
		List<String> players = squad.getPlayers();
		Iterator<String> itr = players.iterator();
		String dir = AuctionConstants.DATA_DIR;
		String file_name = AuctionConstants.AUCTION_FILE_NAME;
		
		try{
    		new File(dir+squad.getName()).mkdirs();
    		File file = new File(dir+squad.getName()+file_name);
    		
    		file.createNewFile();
    		
    		FileWriter fileWritter = new FileWriter(file);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        while(itr.hasNext()) {
    	        	String data = (String) itr.next();
    	        	bufferWritter.write(data);
    	        	bufferWritter.newLine();
    	        }
    	        bufferWritter.close();
    	    
	        
    	}catch(IOException e){
    		e.printStackTrace();
    	}
	}
	
}
