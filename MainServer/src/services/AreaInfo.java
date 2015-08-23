package services;

import java.io.Serializable;

/**
 * 
 * @author Noam
 * This class contain all the relevant information of an area on a specific election.
 * This class used as a return type for a polling station call for information.
 * It includes the name of the election, name of the area, list of the candidate, number of votes per voter, and if the votes should be ranked.
 */
public class AreaInfo implements Serializable{
	private String electionName;
	private String area;
	private String[] cans;
	private int numOfVotesPerVoter;
	private boolean isRanked;
	
	public AreaInfo(){};
	
	public AreaInfo(String name, String area, String[] cans, int novpv, boolean isRanked) {
		this.electionName = name;
		this.area = area;
		this.cans = cans;
		this.numOfVotesPerVoter = novpv;
		this.isRanked = isRanked;	
	}
	
	/**
	 * This method returns the name of the election which the information is refer to.
	 * @return the name of the election which the information is refers to.
	 */
	public String getElectionName() {
		return electionName;
	}
	
	/**
	 * This method returns the name of the area which the information is refers to.
	 * @return the name of the area which the information is refers to.
	 */
	public String getArea(){
		return area;
	}
	
	/**
	 * This method returns the candidates who are running for this election in this area. 
	 * @return the candidates who are running for this election in this area.
	 */
    public String[] getCanNames() {
		return cans;
	}
    
    /**
     * This method returns the number of vote which each voter has to vote.
     * @return the number of vote which each voter has to vote.
     */
	public int getNumOfVotePerVoter() {
		return numOfVotesPerVoter;
	}

	/**
	 * This method returns true if the votes of each voter should be ranked in order or false if there is no meaning to the order.
	 * @return true if the votes of each voter should be ranked in order or false if there is no meaning to the order.
	 */
	public boolean isRanked() {
		return isRanked;
	}

}
