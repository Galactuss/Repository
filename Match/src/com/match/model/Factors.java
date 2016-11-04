package com.match.model;

public class Factors {

	protected int dot_chance;
	protected int single_chance;
	protected int double_chance;
	protected int triple_chance;
	protected int four_chance;
	protected int extra_chance;
	protected int six_chance;
	protected int wicket_chance;

	public Factors(int dot_chance, int single_chance, int double_chance, int triple_chance, int four_chance,
			int extra_chance, int six_chance, int wicket_chance) {
		super();
		this.dot_chance = dot_chance;
		this.single_chance = single_chance;
		this.double_chance = double_chance;
		this.triple_chance = triple_chance;
		this.four_chance = four_chance;
		this.extra_chance = extra_chance;
		this.six_chance = six_chance;
		this.wicket_chance = wicket_chance;
	}

	public int getDot_chance() {
		return dot_chance;
	}

	public int getSingle_chance() {
		return single_chance;
	}

	public int getDouble_chance() {
		return double_chance;
	}

	public int getTriple_chance() {
		return triple_chance;
	}

	public int getFour_chance() {
		return four_chance;
	}

	public int getExtra_chance() {
		return extra_chance;
	}

	public int getSix_chance() {
		return six_chance;
	}

	public int getWicket_chance() {
		return wicket_chance;
	}
}
