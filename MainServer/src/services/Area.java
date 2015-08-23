package services;

import java.io.Serializable;
/**
 * 
 * @author Noam
 * This class represent one voting area. it contain all the information of this area.
 * It has fields for the area name, the voting system which is area is belong to, the number of votes per voter, and is the votes
 * of each voter are ranked.
 * 
 */
public class Area implements Serializable{
	private String areaName;
	private String systemName;
	private int numOfVotesPerVoter;
	private boolean isRanked;
	
	public Area(){}
	
	public Area(String name, String sysName, int novpv, boolean isRanked) {
		this.areaName = name;
		this.systemName = sysName;
		this.numOfVotesPerVoter = novpv;
		this.isRanked = isRanked;
	}
	
	/**
	 * This method return the name of the area
	 * @return the name of the area.
	 */
	public String getName() {
		return areaName;
	}
	
	/**
	 * This method return the name of the voting system which this area is in.
	 * @return the name of the voting system this area is in.
	 */
	public String getSystemName() {
		return systemName;
	}
	
	/**
	 * This method return the number of votes which each voter need to choose in this area.
	 * @return the number of votes which each voter need to choose in this area.
	 */
	public int getNumOfVotesPerVoter() {
		return numOfVotesPerVoter;
	}
	
	/**
	 * This method return true if the votes of each voter are ranked or false is there is no meaning to the order.
	 * @return true if the votes of each voter are ranked or false is there is no meaning to the order.
	 */
	public boolean isRanked() {
		return isRanked;
	}

}

