import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ConnectedComponent {

	boolean[] processed;
	HashMap<Integer, List<Integer>> graph;
	
	public void connect(int size, int[][] pairs) {
		
		graph = new HashMap<>();
		processPairs(graph, pairs);
		
		processed = new boolean[size+1];
		
		int count = 0;
		for (int key = 0; key < size; key++) {
			if (!processed[key]) {
				System.out.print("Component " + count + ": ");
				bfs(key);
				System.out.println();
				count++;
			}
		}
		
		System.out.println(count);
	}
	
	private void bfs(int start) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(start);
		processed[start] = true;
		
		while (!queue.isEmpty()) {
			Integer curr = queue.poll();
//			processed[curr] = true;
			System.out.print(curr + ", ");
			List<Integer> children = graph.get(curr);
			for (Integer child : children) {
				if (!processed[child]) {
					queue.offer(child);
					processed[child] = true;
				}
			}
		}
	}
	
	private void processPairs(HashMap<Integer, List<Integer>> map, int[][] pairs) {
		for (int[] pair : pairs) {
			if (!map.containsKey(pair[0])) {
				map.put(pair[0], new ArrayList<Integer>());
			}
			if (!map.containsKey(pair[1])) {
				map.put(pair[1], new ArrayList<Integer>());
			}
			map.get(pair[0]).add(pair[1]);
			map.get(pair[1]).add(pair[0]);
		}
	}
	
	public static void main(String[] args) {
		int n = 13;
		int[][] pairs= new int[][]{{0,5}, {4,3}, {0,1},{9,12},{6,4},{5,4}, {0,2}, 
			{11,12},{9,10},{0,6},{7,8},{9,11},{5,3}};
		
		int n1 = 6;	
		int[][] pairs1= new int[][]{{1,2}, {2,3},{2,4},{4,5},{5,6},{6,3},{6,4}};
				
		ConnectedComponent	clz = new ConnectedComponent();
		clz.connect(n, pairs);
	}
	
}
