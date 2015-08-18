package manager;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		
		Manager m = new Manager();
		Map<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String,Integer> a = new HashMap<String, Integer>();
		a.put("aa", 200);
		a.put("ab", 300);
		HashMap<String,Integer> b = new HashMap<String, Integer>();
		b.put("ba", 159);
		b.put("bb", 456);
		map.put("A",a);
		map.put("B", b);
		
		System.out.println(map.get("B").get("bb"));
		
	
		
		
		

	}

}
