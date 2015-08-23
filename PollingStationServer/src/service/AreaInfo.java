package service;

import java.io.Serializable;

/**
 * This class contain the relevant information of an election
 *  to be used by the polling station server to inform the desks and the manager of the station.
 * @author Noam
 *
 */
public class AreaInfo implements Serializable{
	private String stationName;
	private String electionName;
	private String area;
	private String[] cans;
	private int numOfVotesPerVoter;
	private boolean isRanked;
	
	public AreaInfo(){};
	/**
	 * 
	 * @param stationName is the polling station name
	 * @param electionName is the name of the specific election
	 * @param area is the name if the area of this polling station
	 * @param cans is a list of the candidates who run for this election in this area.
	 * @param novpv is the number of candidates that each voter is allow to choose.
	 * @param isRanked is a boolean specify if the chosen candidates has to be ranked in order or not.
	 */
	public AreaInfo(String stationName, String electionName, String area, String[] cans, int novpv, boolean isRanked) {
		this.stationName = stationName;
		this.electionName = electionName;
		this.area = area;
		this.cans = cans;
		this.numOfVotesPerVoter = novpv;
		this.isRanked = isRanked;	
	}
	
	/**
	 * This method return the name of the polling station.
	 * @return the name of the polling station.
	 */
	public String getStationName() {
		return stationName;
	}
	
	/**
	 * This method return the name of the current election.
	 * @return the name of the current election.
	 */
	public String getElectionName() {
		return electionName;
	}
	
	/**
	 * This method return the name of the area which this polling station is in.
	 * @return the name of the area which this polling station is in.
	 */
	public String getArea(){
		return area;
	}
	
	/**
	 * This method return an array of string with the names of the candidates who run for this election in this area.
	 * @return names of the candidates who run for this election in this area.
	 */
	public String[] getCanNames() {
		return cans;
	}
	
	/**
	 * This method return the number of votes that each voter has to vote.
	 * @return the number of votes that each voter has to vote.
	 */
	public int getNumOfVotePerVoter() {
		return numOfVotesPerVoter;
	}
	
	/**
	 * This method return a boolean that specify if the chosen candidates has to be ranked.
	 * @return true if the candidates has to be ranked, false if there is no meaning to the order.
	 */
	public boolean isRanked() {
		return isRanked;
	}

}
