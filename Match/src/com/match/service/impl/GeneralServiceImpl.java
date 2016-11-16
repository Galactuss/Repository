package com.match.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.util.InstanceProvider;
import com.match.data.CommentryConstants;
import com.match.data.MatchConstants;
import com.match.model.Match;
import com.match.service.GeneralService;
import com.match.service.MatchEngine;
import com.match.web.WebConnector;

/**
 * 
 * @author Pushpak
 *
 */
public class GeneralServiceImpl implements GeneralService {

	/** Variables declaration */
	private Random randomGenerator = InstanceProvider.getInstance(Random.class);
	private static final String DATE_FORMAT = MatchConstants.DATE_FROMAT;
	private static final String FLOAT_FORMAT = MatchConstants.FLOAT_FORMAT;
	private static final String DATA_DIR = MatchConstants.DATA_DIR;
	private static final String FILE_NAME = MatchConstants.FILE_NAME;
	private static final String TEXTFILE_EXTENSION = MatchConstants.TEXTFILE_EXTENSION;
	private Match match;

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#addTimeLag(int)
	 */
	@Override
	@SuppressWarnings("static-access")
	public void addTimeLag(int seconds) {

		try {
			Thread.currentThread().sleep(1000 * seconds);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#getCommentry(int)
	 */
	@Override
	public String getCommentry(int result) {

		String commentry = null;
		int index = randomGenerator.nextInt(3);
		switch (result) {
		case 0:
			commentry = CommentryConstants.DOT_COMMENTRY[index];
			break;
		case 1:
			commentry = CommentryConstants.SINGLE_COMMENTRY[index];
			break;
		case 2:
			commentry = CommentryConstants.DOUBLE_COMMENTRY[index];
			break;
		case 3:
			commentry = CommentryConstants.TRIPLE_COMMENTRY[index];
			break;
		case 4:
			commentry = CommentryConstants.FOUR_COMMENTRY[index];
			break;
		case 6:
			commentry = CommentryConstants.SIX_COMMENTRY[index];
			break;
		case 7:
			commentry = CommentryConstants.WICKET_COMMENTRY[index];
			break;
		}
		return commentry;
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#getWriter(com.match.model.Match)
	 */
	@Override
	public BufferedWriter getWriter(Match match) {

		new File(DATA_DIR + match.getBatting_team().getName() + "vs" + match.getBowling_team().getName()).mkdirs();
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

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#writeMatchData(java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#writeMatchData(java.lang.String)
	 */
	@Override
	public void writeMatchData(StringBuilder contentsb) {

		BufferedWriter writer = MatchEngine.writer;
		try {
			writer.write(contentsb.toString());
			writer.newLine();
			System.out.println(contentsb);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#closeWriter(java.io.BufferedWriter)
	 */
	@Override
	public void closeWriter(BufferedWriter writer) {

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#getFormattedDate()
	 */
	@Override
	public String getFormattedDate() {

		Date date = new Date();
		SimpleDateFormat format = InstanceProvider.getInstance(SimpleDateFormat.class, DATE_FORMAT);
		return format.format(date);
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#roundTwoDecimals(float)
	 */
	@Override
	public float roundTwoDecimals(float f) {

		DecimalFormat twoDForm = InstanceProvider.getInstance(DecimalFormat.class, FLOAT_FORMAT);
		return Float.valueOf(twoDForm.format(f));
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#parseChanceToInteger(float)
	 */
	@Override
	public int parseChanceToInteger(float f) {
		return (int) (roundTwoDecimals(f) * 100);
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#checkForRainInteruption()
	 */
	@Override
	public int checkForRainInteruption() {

		WebConnector connector = InstanceProvider.getInstance(WebConnector.class);
		URLConnection connection = connector.getConnetion();
		boolean hasRainInterrupted = connector.CheckForRainFall(connection);
		if (hasRainInterrupted) {
			int minOvers = randomGenerator.nextInt(15) + 5;
			return minOvers;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.match.service.GeneralService#generateFileName()
	 */
	@Override
	public String generateFileName() {

		StringBuilder builder = new StringBuilder();
		builder.append(DATA_DIR).append(match.getBatting_team().getName()).append("vs")
				.append(match.getBowling_team().getName()).append(FILE_NAME).append(getFormattedDate())
				.append(TEXTFILE_EXTENSION);
		return builder.toString();
	}
}
