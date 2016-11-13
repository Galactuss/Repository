package com.isl.model;

import com.isl.model.Player;

/**
 * 
 * @author PUSHPAK
 *
 */
public class Partnership {

	private int runs;
	private Player[] partners;
	private boolean century_partnship;
	private boolean out;

	/**
	 * Constructor for Partnership
	 * 
	 * @param players
	 */
	public Partnership(Player partner1, Player partner2) {
		runs = 0;
		century_partnship = false;
		out = false;
		partners = new Player[2];
		partners[0] = partner1;
		partners[1] = partner2;
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public Player[] getPartners() {
		return partners;
	}

	public void setPartners(Player[] partners) {
		this.partners = partners;
	}

	public boolean isCentury_partnship() {
		return century_partnship;
	}

	public void setCentury_partnship(boolean century_partnship) {
		this.century_partnship = century_partnship;
	}

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

}
