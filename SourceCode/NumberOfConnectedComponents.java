import java.util.HashSet;

public class NumberOfConnectedComponents {

	
	int[] id;
	int count;
	
	public int countComponents(int n, int[][] edges) {
		count = n;
		id = new int[n];
		for (int i = 0; i < n; i++) {
			id[i] = i;
		}
		
		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			connect(u, v);
		}
		
		/**
		 * for each node if (parent[node] == node)  
    			connected_component += 1
		 */
		return count;
	}
	
	private int root(int p) {
		
		while (p != id[p]) {
			id[p] = id[id[p]];
			p = id[p];
		}
		return p;
	}
	
	private void connect(int p, int q) {
		int i = root(p);
		int j = root(q);
		if (i == j) return;
		
		//只有当把两个原本不相连的元素连起来的时候，才需要减少count
		id[j] = i;
		count--;
	}
	
	public static void main(String[] args) {
		NumberOfConnectedComponents clz = new NumberOfConnectedComponents();
		int[][] edges1 = {{0, 1}, {1, 2}, {3, 4}};
//		System.out.println(clz.countComponents(5, edges1));
		
		int[][] edges2 = {{0, 1}, {2, 3}, {1, 2}};
		System.out.println(clz.countComponents(4, edges2));
		
	}
}
