package com.match.service;

import java.io.BufferedWriter;

import com.match.model.Match;

public interface GeneralService {

	/**
	 * Add time lag for given time in seconds
	 * 
	 * @param seconds
	 */
	void addTimeLag(int seconds);

	/**
	 * 
	 * @param result
	 * @return Commentry
	 */
	String getCommentry(int result);

	/**
	 * 
	 * @param match
	 * @return Bufferedwriter for writing data to file
	 */
	BufferedWriter getWriter(Match match);

	/**
	 * Write data to files
	 * 
	 * @param content
	 */
	void writeMatchData(String content);

	/**
	 * Close the bufferedwriter
	 * 
	 * @param writer
	 */
	void closeWriter(BufferedWriter writer);

	/**
	 * Format current date to a specific format
	 * 
	 * @return
	 */
	String getFormattedDate();

	/**
	 * Round given float to two decimals
	 * 
	 * @param f
	 * @return
	 */
	float roundTwoDecimals(float f);

	/**
	 * 
	 * @param f
	 * @return
	 */
	int parseChanceToInteger(float f);

	/**
	 * 
	 * @return number of overs if match is interrupted by rain
	 */
	int checkForRainInteruption();

	/**
	 * Returns file name
	 * 
	 * @return
	 */
	String generateFileName();

}