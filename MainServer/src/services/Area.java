package services;

import java.io.Serializable;

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
	
	public String getName() {
		return areaName;
	}
	
	public String getSystemName() {
		return systemName;
	}
	
	public int getNumOfVotesPerVoter() {
		return numOfVotesPerVoter;
	}
	
	public boolean isRanked() {
		return isRanked;
	}

}

