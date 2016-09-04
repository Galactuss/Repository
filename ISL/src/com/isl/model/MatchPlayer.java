package com.isl.model;

public class MatchPlayer {

	private int runs_scored = 0;
	private int balls_faced = 0;
	private int four_scored = 0;
	private int six_scored = 0;
	private int runs_given = 0;
	private int balls_bowled = 0;
	private int wickets_taken = 0;
	private int maiden_overs = 0;
	private boolean half_century = false;
	private boolean century = false;
	private boolean isNotOut = false;
	private int hattrickCount = 0;

	public int getHattrickCount() {
		return hattrickCount;
	}

	public void setHattrickCount(int hattrickCount) {
		this.hattrickCount = hattrickCount;
	}

	public boolean isNotOut() {
		return isNotOut;
	}

	public void setNotOut(boolean isNotOut) {
		this.isNotOut = isNotOut;
	}

	public int getMaiden_overs() {
		return maiden_overs;
	}

	public void setMaiden_overs(int maiden_overs) {
		this.maiden_overs = maiden_overs;
	}

	public boolean isHalf_century() {
		return half_century;
	}

	public void setHalf_century(boolean half_century) {
		this.half_century = half_century;
	}

	public boolean isCentury() {
		return century;
	}

	public void setCentury(boolean century) {
		this.century = century;
	}

	public int getRuns_scored() {
		return runs_scored;
	}

	public void setRuns_scored(int runs_scored) {
		this.runs_scored = runs_scored;
	}

	public int getBalls_faced() {
		return balls_faced;
	}

	public void setBalls_faced(int balls_faced) {
		this.balls_faced = balls_faced;
	}

	public int getFour_scored() {
		return four_scored;
	}

	public void setFour_scored(int four_scored) {
		this.four_scored = four_scored;
	}

	public int getSix_scored() {
		return six_scored;
	}

	public void setSix_scored(int six_scored) {
		this.six_scored = six_scored;
	}

	public int getRuns_given() {
		return runs_given;
	}

	public void setRuns_given(int runs_given) {
		this.runs_given = runs_given;
	}

	public int getBalls_bowled() {
		return balls_bowled;
	}

	public void setBalls_bowled(int balls_bowled) {
		this.balls_bowled = balls_bowled;
	}

	public int getWickets_taken() {
		return wickets_taken;
	}

	public void setWickets_taken(int wickets_taken) {
		this.wickets_taken = wickets_taken;
	}
}
