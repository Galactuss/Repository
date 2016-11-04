package com.match.model;

public class Range {

	private int start;
	private int end;

	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public boolean insideRange(int num) {
		if (num >= start || num <= end) {
			return true;
		}
		return false;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
}
