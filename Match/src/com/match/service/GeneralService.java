package com.match.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.match.config.InstanceProvider;
import com.match.data.CommentryConstants;
import com.match.data.MatchConstants;
import com.match.model.Match;
import com.match.web.WebConnector;

/**
 * 
 * @author Pushpak
 *
 */
public class GeneralService {
	
	/** Variables declaration */
	private Random randomGenerator = new Random();
	private String DATE_FORMAT = MatchConstants.DATE_FROMAT;
	private String FLOAT_FORMAT = MatchConstants.FLOAT_FORMAT; 
	private String DATA_DIR = MatchConstants.DATA_DIR;
	private String FILE_NAME = MatchConstants.FILE_NAME;
	private String TEXTFILE_EXTENSION = MatchConstants.TEXTFILE_EXTENSION;
	private Match match;
	
	/**
	 * Add time lag for given time in seconds
	 * @param seconds
	 */
	@SuppressWarnings("static-access")
	public void addTimeLag(int seconds) {
		
		try {
		    Thread.currentThread().sleep(1000*seconds);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * 
	 * @param result
	 * @return Commentry
	 */
	public String getCommentry(int result) {
		
		String commentry = null;
		int index = randomGenerator.nextInt(3);
		switch(result) {
			case 0: commentry = CommentryConstants.DOT_COMMENTRY[index];
					break;
			case 1: commentry = CommentryConstants.SINGLE_COMMENTRY[index];
					break;
			case 2: commentry = CommentryConstants.DOUBLE_COMMENTRY[index];
					break;
			case 3: commentry = CommentryConstants.TRIPLE_COMMENTRY[index];
					break;
			case 4: commentry = CommentryConstants.FOUR_COMMENTRY[index];
					break;
			case 6: commentry = CommentryConstants.SIX_COMMENTRY[index];
					break;
			case 7: commentry = CommentryConstants.WICKET_COMMENTRY[index];
					break;
		}
		return commentry;
	}
	
	/**
	 * 
	 * @param match
	 * @return Bufferedwriter for writing data to file
	 */
	public BufferedWriter getWriter(Match match) {
		
		new File(DATA_DIR+match.getBatting_team().getName()+"vs"+match.getBowling_team().getName()).mkdirs();
		this.match = match;
		File file = new File(generateFileName());
		try {
			file.createNewFile();
			FileWriter fileWritter = new FileWriter(file);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        return bufferWritter;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Write data to files
	 * @param content
	 */
	public void writeMatchData(String content) {
		
		BufferedWriter writer = MatchEngine.writer;
		try {
			writer.write(content);
			writer.newLine();
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close the bufferedwriter
	 * @param writer
	 */
	public void closeWriter(BufferedWriter writer) {
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Format current date to a specific format
	 * @return
	 */
	public String getFormattedDate() {
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return format.format(date);
	}
	
	/**
	 * Round given float to two decimals
	 * @param f
	 * @return
	 */
	public float roundTwoDecimals(float f) {
		  
		DecimalFormat twoDForm = new DecimalFormat(FLOAT_FORMAT);
		return Float.valueOf(twoDForm.format(f));
	}
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public int parseChanceToInteger(float f) {
		return (int)(roundTwoDecimals(f)*100);
	}
	
	/**
	 * 
	 * @return number of overs if match is interrupted by rain
	 */
	public int checkForRainInteruption() {
		
		WebConnector connector = InstanceProvider.getInstance(WebConnector.class);
		URLConnection connection  = connector.getConnetion();
		boolean hasRainInterrupted = connector.CheckForRainFall(connection);
		if(hasRainInterrupted) {
			int minOvers = randomGenerator.nextInt(15)+5;
			return minOvers;
		}
		return 0;
	}
	
	/**
	 * Returns file name
	 * @return
	 */
	public String generateFileName() {
		
		StringBuilder builder = new StringBuilder();
		builder.append(DATA_DIR)
			.append(match.getBatting_team().getName())
			.append("vs")
			.append(match.getBowling_team().getName())
			.append(FILE_NAME)
			.append(getFormattedDate())
			.append(TEXTFILE_EXTENSION);
		return builder.toString();
	}
}
