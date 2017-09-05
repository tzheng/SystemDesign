import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class ReconstructItinerary {
	
	/**
	 * 332. Reconstruct Itinerary
	 * the itinerary must begin with JFK.
	 * 
	 * 
	 * DFS 先看一个比较直接的DFS,  先建立一个Map，存每个城市能到哪些地方，而且这些list还有序。
	 * 然后对于每个城市等到达的城市，做DFS，如果都能到达，返回true，不再继续搜索。因为存的list都是有序的。
	 * 是第一个搜到的肯定是排序最小的。
	 * 
	 */
	public static List<String> findItinerary(String[][] tickets) {
	    // construct graph
	    HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
	    for (String[] ticket : tickets) {
	        if (!graph.containsKey(ticket[0])) {
	            graph.put(ticket[0], new ArrayList<String>());
	        } 
	        if (!graph.containsKey(ticket[1])) {
	            graph.put(ticket[1], new ArrayList<String>());
	        }
	        graph.get(ticket[0]).add(ticket[1]);
	    }
	    for (ArrayList<String> curr : graph.values()) {
	        Collections.sort(curr);
	    }
	    List<String> path = new ArrayList<>();
	    List<String> res = new ArrayList<>();
	    path.add("JFK");
	    itineraryHelper("JFK", path, graph, tickets.length + 1, res);
	    return res;
	}
	

	// n is how many stops totally should contain
	public static boolean itineraryHelper(String curr, List<String> path, HashMap<String, ArrayList<String>> graph, int n, List<String> res) {
	    if (path.size() >= n) {
	        res.addAll(path);
	        return true;
	    }
	    ArrayList<String> arrivals = graph.get(curr);
	    for (int i = 0; i < arrivals.size(); i++) { // iterate each arrival point
	        
	        String arrival = graph.get(curr).remove(i);
	        path.add(arrival);
	        if (itineraryHelper(arrival, path, graph, n, res)) {
	            return true;
	        }
	         // backtrack
	         
	        path.remove(path.size() - 1);
	        arrivals.add(i, arrival);
	    }
	    
	    return false;
	}
	
	/**
	 * 其实有个更简洁的DFS，对于每个城市，一直往后找。
	 */
	private void dfs(String curr, HashMap<String, PriorityQueue<String>> map, List<String> result) {
		while (map.containsKey(curr) && !map.get(curr).isEmpty()) {
			String next = map.get(curr).poll();
			dfs(next, map, result);
		}
		result.add(curr);
	}
	
	public void listAdd(String value, ArrayList<String> list){
        if(list.size() == 0){
            list.add(value);
            return;
        }
        else{
            int i = 0;
            while(i < list.size()){
                if(value.compareTo(list.get(i)) <= 0){
                    list.add(i, value);
                    return;
                }
                i++;
            }
            list.add(value);
            return;
        }
    }
	
	/**
	 *  一个巧妙的解法 greedy的Hierholzer's algorithm
	 * 先一直走，只要当前机场还能到其他机场，就选字母顺序最小的那个走，一直到不能再走为止。如果不能再走了，
	 * 就把当前机场放进一个stack里面，看上一个机场还能不能到其他机场。
	 * Time Complexity: O(n+e). Space: O(n+e).
	 */
	public List<String> findItineraryStack(String[][] tickets) {
		List<String> res = new ArrayList<>();
		Map<String, PriorityQueue<String>> ticketsMap = new HashMap<>();
		for (int i = 0; i < tickets.length; i++) {
			 if(!ticketsMap.containsKey(tickets[i][0])) 
				 ticketsMap.put(tickets[i][0], new PriorityQueue<String>());
			 ticketsMap.get(tickets[i][0]).add(tickets[i][1]);
		}
		
		String curr = "JFK";
		Stack<String> stack = new Stack<>();
		for (int i = 0; i < tickets.length; i++) {
			//当前机场无路可走，返回上一个机场
			while (!ticketsMap.containsKey(curr) || ticketsMap.get(curr).size() == 0) {
				stack.push(curr);
				curr = res.remove(res.size()-1);
			}
			res.add(curr);
			curr = ticketsMap.get(curr).poll();
		}
		
		res.add(curr);
		while (!stack.isEmpty())
			res.add(stack.pop());
		return res;
	}
}	
