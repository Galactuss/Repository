package com.match.model;

public class Factors {

	private int dot_chance;
	private int single_chance;
	private int double_chance;
	private int triple_chance;
	private int four_chance;
	private int extra_chance;
	private int six_chance;
	private int wicket_chance;

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
	
	public int getChance(ResultType resultType) {
		
		switch(resultType) {
			case DOT:
				return dot_chance;
			case SINGLE:
				return single_chance;
			case DOUBLE:
				return double_chance;
			case TRIPLE:
				return triple_chance;
			case FOUR:
				return four_chance;
			case EXTRA:
				return extra_chance;
			case SIX:
				return six_chance;
			case WICKET:
				return wicket_chance;
		}
		
		return 0;
	}
}
