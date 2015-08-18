package services;

public class Vote {
	private String electionName;
	private String area;
	private int ID;
	private String[] votes;
	
	
	public Vote(String electionName, String area, int id, String[] votes) {
		this.electionName = electionName;
		this.area = area;
		this.ID = id;
		this.votes = votes;
	}
	
	public String getElectionName() {
		return electionName;
	}
	
	public String getArea() {
		return area;
	}
	
	public int getId() {
		return ID;
	}
	
	public String[] getVotes() {
		return votes;
	}

}
 