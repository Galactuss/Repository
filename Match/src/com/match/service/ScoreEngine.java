package com.match.service;

import java.util.LinkedList;
import java.util.Random;

import com.isl.model.Player;
import com.isl.model.Team;
import com.match.config.InstanceProvider;
import com.match.data.CommentryConstants;
import com.match.service.MatchFactors;
import com.match.model.Extra;

/**
 * 
 * @author Pushpak
 *
 */
public class ScoreEngine {
	
	Random randomGenerator = InstanceProvider.getInstance(Random.class);
	private GeneralService generalService = (GeneralService) InstanceProvider.getInstance(GeneralService.class);
	
	public int getResultForDelievery(Player batsman, Player bowler, MatchFactors matchFactors, int score, int wickets, LinkedList<Integer> partnerships){
		
		
		int result = randomGenerator.nextInt(8);
		if(result == 0 && dotChance(matchFactors, batsman, bowler, partnerships)) {
			return 0;
		} else if(result == 1 && singleChance(matchFactors, batsman, bowler, partnerships)) {
			return 1;
		} else if(result == 2 && doubleChance(matchFactors, batsman, bowler, partnerships)) {
			return 2;
		} else if(result == 3 && tripleChance(matchFactors, batsman, bowler, partnerships)) {
			return 3;
		} else if(result== 4 && fourChance(matchFactors, batsman, bowler, partnerships)) {
			return 4;
		} else if(result == 5 && extraChance(matchFactors)) {
			return 5;
		} else if(result == 6 && sixChance(matchFactors, batsman, bowler, partnerships)) {
			return 6;
		} else if(result == 7 && wicketChance(matchFactors, batsman, bowler, score, wickets, partnerships)) {
			return 7;
		} else {
			return getResultForDelievery(batsman, bowler, matchFactors, score, wickets, partnerships);
		}
	}
	
	public boolean dotChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		
		int external = matchFactors.getDotFactors(batsman, bowler, partnerships);
		int dotChance = randomGenerator.nextInt(12000);
		if(dotChance < (3500+external)) {
			return true;
		}
		return false;
	}
	
	public boolean singleChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		
		int external = matchFactors.getSingleFactors(batsman, bowler, partnerships);
		int singleChance = randomGenerator.nextInt(12000);
		if(singleChance < (4500+external)) {
			return true;
		}
		return false;
	}
	
	public boolean doubleChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		
		int external = matchFactors.getDoubleFactors(batsman, bowler, partnerships);
		int doubleChance = randomGenerator.nextInt(12000);
		if(doubleChance < (2500+external)) {
			return true;
		}
		return false;
	}
	
	public boolean tripleChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		
		int external = matchFactors.getTripleFactors(batsman, bowler, partnerships);
		int tripleChance = randomGenerator.nextInt(12000);
		if(tripleChance < (200+external)) {
			return true;
		}
		return false;
	}
	
	public boolean fourChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		
		int external = matchFactors.getFourFactors(batsman, bowler, partnerships);
		int fourChance = randomGenerator.nextInt(12000);
		if(fourChance < (800+external)) {
			return true;
		}
		return false;
	}
	
	public boolean extraChance(MatchFactors matchFactors) {
		
		int extraChance = randomGenerator.nextInt(12000);
		if(extraChance < 500) {
			return true;
		}
		return false;
	}
	
	public boolean sixChance(MatchFactors matchFactors, Player batsman, Player bowler, LinkedList<Integer> partnerships) {
		
		int external = matchFactors.getSixFactors(batsman, bowler, partnerships);
		int sixChance = randomGenerator.nextInt(12000);
		if(sixChance < (200+external)) {
			return true;
		}
		return false;
	}
	
	public boolean wicketChance(MatchFactors matchFactors, Player batsman, Player bowler, int score, int wickets, LinkedList<Integer> partnerships) {
		
		int external = matchFactors.getWicketFactors(batsman, bowler, partnerships);
		int wicketChance = randomGenerator.nextInt(12000);
		boolean validate = validateWicket(score, wickets);
		if((wicketChance < (300+external)) && validate) {
			return true;
		}
		return false;
	}
	
	public Extra getTypeOfExtra(Player batsman, Player bowler, MatchFactors matchFactors, int score, int wickets, LinkedList<Integer> partnerships) {
		
		Extra extra = new Extra();
		int typeOfExtraChance = randomGenerator.nextInt(3);
		switch(typeOfExtraChance) {
			case 0: extra.setTypeOfextra(CommentryConstants.WIDE);
					extra.setRuns(getWideRuns());
					break;
			case 1: extra.setTypeOfextra(CommentryConstants.NO);
					int runs = 5;
					while(runs==5 || runs==7) {
						runs = getNoballRuns(batsman, bowler, matchFactors, score, wickets, partnerships);
					}
					extra.setRuns(runs);
					break;
			case 2: extra.setTypeOfextra(CommentryConstants.LEG_BYE);
					extra.setRuns(getLegByes());
					break;
		}	
		return extra;
	}
	
	public int getWideRuns() {
		
		int runs = randomGenerator.nextInt(5);
		int chance = randomGenerator.nextInt(100);
		if((runs==0 && chance<60) || (runs==1 && chance>59 && chance<70) || (runs==2 && chance>69 && chance<80) || (runs==3 && chance>79 && chance<85) || (runs==4 && chance>84)) {
			return runs;
		} else {
			return getWideRuns();
		}
	}
	
	public int getLegByes() {
		
		int runs = 1 + randomGenerator.nextInt(4);
		int chance = randomGenerator.nextInt(100);
		if((runs==1 && chance<60) || (runs==2 && chance>59 && chance<70) || (runs==3 && chance>69 && chance<80) || (runs==4 && chance>79)) {
			return runs;
		} else {
			return getLegByes();
		}
	}
	
	public int getNoballRuns(Player batsman, Player bowler, MatchFactors matchFactors, int score, int wickets, LinkedList<Integer> partnerships) {
		
		int runs = getResultForDelievery(batsman, bowler, matchFactors, score, wickets, partnerships);
		return runs;
	}
	
	public String getTypeOfWicket(Player batsman, Player bowler, Team bowling_team) {
		
		int chance = randomGenerator.nextInt(3);
		if(chance == 1) {
			return batsman.getName()+" b "+bowler.getName()+" "+batsman.getMatchPlayer().getRuns_scored()+"("+batsman.getMatchPlayer().getBalls_faced()+"b "+batsman.getMatchPlayer().getFour_scored()+"x4 "+batsman.getMatchPlayer().getSix_scored()+"x6)";
		} else if(chance == 2) {
			return batsman.getName()+" lbw "+bowler.getName()+" "+batsman.getMatchPlayer().getRuns_scored()+"("+batsman.getMatchPlayer().getBalls_faced()+"b "+batsman.getMatchPlayer().getFour_scored()+"x4 "+batsman.getMatchPlayer().getSix_scored()+"x6)";
		} else {
			Player catcher = getRandomPlayer(bowling_team);
			if(catcher.getName().equals(bowler.getName())) {
				return batsman.getName()+" c&b "+bowler.getName()+" "+batsman.getMatchPlayer().getRuns_scored()+"("+batsman.getMatchPlayer().getBalls_faced()+"b "+batsman.getMatchPlayer().getFour_scored()+"x4 "+batsman.getMatchPlayer().getSix_scored()+"x6)";
			} else {
				return batsman.getName()+" c "+catcher.getName()+" b "+bowler.getName()+" "+batsman.getMatchPlayer().getRuns_scored()+"("+batsman.getMatchPlayer().getBalls_faced()+"b "+batsman.getMatchPlayer().getFour_scored()+"x4 "+batsman.getMatchPlayer().getSix_scored()+"x6)";
			}
		}
	}
	
	public boolean validateWicket(int score, int wickets) {
		
		int validate = randomGenerator.nextInt(3);
		if(validate != 2 && score/Math.pow((wickets+1), 2) < 2) {
			return false;
		}
		return true;
	}
	
	public Player getRandomPlayer(Team team) {
		
		int chance = randomGenerator.nextInt(11);
		return team.getPlayers().get(chance);
	}
	
	public float getRunrate(int runs, int overs) {
		
		return generalService.roundTwoDecimals((float)runs/overs);
	}
	
	public float getStrikerate(int runs, int balls) {
		
		if(balls != 0) {
			return generalService.roundTwoDecimals((float)runs*100/balls);
		}
		return 0;
	}
	
	public float getEconomy(int runs, int balls) {
		
		float effective_overs = (float)balls/6;
		return generalService.roundTwoDecimals((float)runs/effective_overs);
	}

}


