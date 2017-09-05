import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 从 GraphValidTree 引申出来的一些无向图的基本操作
 * 	http://shmilyaw-hotmail-com.iteye.com/blog/2113093
 */
public class GraphBasics {
	
	int count;
	Map<Integer, Set<Integer>> graph;
	int n;
	
	public GraphBasics(int n, int[][] edges) {
		this.n = n;
		this.contructGraph(n, edges);
	}
	
	/**
	 * 判断图是否连通
	 * 通过深度遍历或者广度遍历。用count记录被访问过的点，最后看
	 * count 是否等于图中所有点的数量
	 */
	public boolean connectedGraphDFS() {
		Set<Integer> visited = new HashSet<Integer>();
		count = 0;
		dfs(0, visited);
		return count == n;
	}
	private void dfs(int node, Set<Integer> visited) {
		visited.add(node);
		count++;
		Set<Integer> adjs = graph.get(node);
		for (int adj : adjs) {
			if (!visited.contains(adj)) {
				dfs(adj, visited);
			}
		}
	}
	
	public boolean connectedGraphBFS() {
		count = 0;
		Set<Integer> visited = new HashSet<Integer>();
		Queue<Integer> queue = new LinkedList<Integer>();  
		queue.offer(0);
		visited.add(0);
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			count++;
			for (int adj : graph.get(curr)) {
				if (!visited.contains(adj)) {
					queue.offer(adj);
					visited.add(adj);
				}
			}
		}
		return count == n;
	}
	
	
	/**
	 * 图中间任意两个点的连通性
	 * 1. 可以用UNION FIND 来判断，只要find的时候他们root相同，说明连通
	 * 2. DFS 或者 BFS， 对于每一个连通的部分，我们赋予一个不同的id，最后
	 * 看两个点所属的ID是否一样， 其实和union find差不多
	 * 
	 */
	public boolean connected(int p, int q) {
		Set<Integer> visited = new HashSet<Integer>();
		int count = 0;
		int[] id = new int[n];
		
		//DFS
		for (int i = 0; i < n; i++) {
			if (!visited.contains(i)) {
				dfsConnected(i, count, id, visited);
				count++;
			}
		}
				
		System.out.println("The Graph has " + count + " connected components");
		return id[p] == id[q];
	}
	private void dfsConnected(int node, int count, int[] id, Set<Integer> visited) {
		visited.add(node);
		id[node] = count;
		Set<Integer> adjs = graph.get(node);
		for (int adj : adjs) {
			if (!visited.contains(adj)) {
				dfsConnected(adj, count, id, visited);
			}
		}
	}
	 
	
	
	/**
	 * 判断二分图(bipartite)
	 * 遍历这个图。然后每次在判断的时候假定一个节点的颜色为某个值，那么再将它
	 * 相邻的节点颜色都设置成不同的。两种颜色可以用boolean来表示， 如果某个
	 * 邻居节点已经被访问过了，而且他的节点和当前节点颜色一样的。说明他不是二
	 * 分图.
	 */
	public boolean isBipartiteBFS() {
		Set<Integer> visited = new HashSet<Integer>();
		boolean[] color = new boolean[n];
		
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(0);
		visited.add(0);
		boolean isBipartite = true;
		while (!queue.isEmpty()) {
			int size = queue.size();
			if (!isBipartite) break;
			for (int i = 0; i < size; i++) {
				int curr = queue.poll();
				for (int adj : graph.get(curr)) {
					if (!visited.contains(adj)) {
						color[adj] = !color[curr];
						visited.add(adj);
						queue.offer(adj);
					} else if (color[adj] == color[curr]) {
						isBipartite = false;
					}
				}
			}
		}
		
		return isBipartite;
	}
	/** DFS 的做法就不全写了，这里写出递归的核心部分 **/
	private void isBipartiteDFS(int node, boolean[] color, Set<Integer> visited) {
		visited.add(node);
		for (int adj : graph.get(node)) {
			if (!visited.contains(adj)) {
				color[adj] = !color[node];
				isBipartiteDFS(adj, color, visited);
			} else if (color[adj] == color[node]) {
				//false;
			}
		}
	}
	
	
	public static void main(String[] args) {
		int n = 4;
		int[][] edges = { { 0, 1 }, { 0, 2 }, {1,3}, {1,2}, {2,3}};
		int[][] edges1 = { { 0, 1 }, { 0, 2 }};
		int[][] edges2 = { { 0, 2 }, { 0, 3 }, {1,2}, {1,3}, {0,1}};
		
		GraphBasics clz = new GraphBasics(n, edges);
		
//		System.out.println(clz.connectedGraphDFS());
//		System.out.println(clz.connectedGraphBFS());
//		System.out.println(clz.connected(1, 3));
		System.out.println(clz.isBipartiteBFS());
		
	}
	
	private Map<Integer, Set<Integer>> contructGraph(int n, int[][] edges) {
		
		graph = new HashMap<Integer, Set<Integer>>();
		for (int i = 0; i < n; i++) {
			graph.put(i, new HashSet<Integer>());
		}
		for (int i = 0; i < edges.length; i++) {
			int u = edges[i][0];
			int v = edges[i][1];
			graph.get(u).add(v);
			graph.get(v).add(u);
		}
		return graph;
	}
}
