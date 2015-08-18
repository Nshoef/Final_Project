package services;

import java.util.HashMap;
import java.util.Map;

public class AreaResult {
	private String electionName;
	private String area;
	private Map<String, Integer[]> result;
	
	public AreaResult(){}
	
	public AreaResult(String electionName, String area) {
		this.electionName = electionName;
		this.area = area;
		result = new HashMap<String, Integer[]>();
	}
	
	public boolean addVote(String can, Integer[] votes) {
		result.put(can, votes);
		return true;
	}
	
	public String getName() {
		return electionName;
	}
	
	public String getArea() {
		return area;
	}
	
	public Integer[] getResults(String can) {
		return result.get(can);
	}
	
	

}