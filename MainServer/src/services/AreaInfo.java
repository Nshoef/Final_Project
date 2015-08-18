package services;


public class AreaInfo {
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
	public String getElectionName() {
		return electionName;
	}
	
	public String getArea(){
		return area;
	}
	
	public String[] getCanNames() {
		return cans;
	}
	public int getNumOfVotePerVoter() {
		return numOfVotesPerVoter;
	}
	
	public boolean isRanked() {
		return isRanked;
	}

}
