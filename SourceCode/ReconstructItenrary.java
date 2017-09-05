import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ReconstructItenrary {
	
	private static final String HashSet = null;

	public List<String> findItinerary(String[][] tickets) {
		List<String> result = new ArrayList<String>();
		
		HashMap<String, PriorityQueue<String>> map = new HashMap<String, PriorityQueue<String>>();
		
		for (String[] tic : tickets) {
			if (!map.containsKey(tic[0])) {
				map.put(tic[0], new PriorityQueue<String>());
			}
			map.get(tic[0]).offer(tic[1]);
		}
		
		if (!map.containsKey("JFK")) {
			return result;
		}
		
		dfs("JFK", map, result);
		return result;
    }
	
	private void dfs(String curr, HashMap<String, PriorityQueue<String>> map, List<String> result) {
		while (map.containsKey(curr) && !map.get(curr).isEmpty()) {
			String next = map.get(curr).poll();
			dfs(next, map, result);
		}
		result.add(curr);
	}
	
	
	public static void main(String[] args) {
		String[][] strs = {{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}};
		String[][] strs1 = {{"MUC","LHR"},{"JFK","MUC"},{"SFO","SJC"},{"LHR","SFO"}};
		
		ReconstructItenrary clz = new ReconstructItenrary();
		List<String> res = clz.findItinerary(strs1);
		for (String str : res) {
			System.out.print(str + "->");
		}
	}
	
	class Ticket {
		String from;
		PriorityQueue<String> to;
		ArrayList<String> children;
		
		public Ticket(String from) { 
			this.from = from;
			to = new PriorityQueue<String>();
			children = new ArrayList<String>();
		}
	}
	
	
	public static List<String> findItineraryBackTracking(String[][] tickets) {
	    // construct graph
	    HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
	    ArrayList<String> al = null;
	    for (String[] ticket : tickets) {
	        al = graph.get(ticket[0]);
	        if (al == null) {
	            al = new ArrayList<String>();
	            graph.put(ticket[0], al);
	        }
	        al.add(ticket[1]);
	    }
	    for (ArrayList<String> curr : graph.values()) {
	        Collections.sort(curr);
	    }
	    List<String> ans = new ArrayList<>();
	    List<String> res = new ArrayList<>();
	    ans.add("JFK");
	    itineraryHelper("JFK", ans, graph, tickets.length + 1, res);
	    return res;
	}
	

	// n is how many stops totally should contain
	public static boolean itineraryHelper(String curr, List<String> path, HashMap<String, ArrayList<String>> graph, int n, List<String> res) {
	    if (path.size() >= n) {
	        res.addAll(path);
	        return true;
	    }
	    if (!graph.containsKey(curr) || graph.get(curr).isEmpty()) {
	        return false;
	    }
	    ArrayList<String> arrivals = graph.get(curr);
	    for (int i = 0; i < arrivals.size(); i++) { // iterate each arrival point
	        String arrival = graph.get(curr).remove(i);
	        path.add(arrival);
	        if (itineraryHelper(arrival, path, graph, n, res)) {
	            return true;
	        }
	        path.remove(path.size() - 1);
	        arrivals.add(i, arrival);
	    }
	    
	    return false;
	}
}
