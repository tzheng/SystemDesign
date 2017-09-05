import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphValidTree {

	/**
	 * LC246 Graph Valid Tree
	 * 
	 * https://aaronice.gitbooks.io/lintcode/content/union_find/graph_valid_tree.html 
	 * 
	 * BFS 的解法 Time O(n), space O(n) 
	 * 注意，BFS和传统的方法不同，传统BFS， 把东西加入queue的时候，就标记为visited，
	 * 但是这里要出队的时候才标记（因为是无向图，每个节点可以指向他的父亲，然后他的父亲已经被visited过了)
	 * 
	 */
	public boolean validTreeBFS(int n, int[][] edges) {
		if (edges.length != n - 1) {
			return false;
		}

		// Construct a graph, do a BFS, if one point is visited multiple times,
		// means not a tree
		Map<Integer, Set<Integer>> graph = contructGraph(n, edges);
		Queue<Integer> queue = new LinkedList<Integer>();
		Set<Integer> visited = new HashSet<>();

		queue.offer(0);
		while (!queue.isEmpty()) {
			Integer curr = queue.poll();
			if (visited.contains(curr)) {
				return false;
			}
			visited.add(curr);
			
			Set<Integer> neighbors = graph.get(curr);
			for (Integer neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
					queue.offer(neighbor);
				}
			}
		}

		return visited.size() == n;
	}
	
	/**
	 * 用 DFS来解答
	 * time complexity is O(V+E), 最后不忘忘了检查 visited.size() == n, 
	 * 所有点都被遍历过，而且只有一次。 
	 */
	public boolean validTreeDFS(int n, int[][] edges) {
		Map<Integer, Set<Integer>> graph = contructGraph(n, edges);
		Set<Integer> visited = new HashSet<>();
		
		//DFS的思路是用一个 parent的记录当前点的父节点，
		//如果当前节点的邻居被访问过了，而且不是父节点，说明有cycle存在，针对无向图
		visited = new HashSet<>();
		boolean isValid = DFSHelper2(0, graph, visited, -1);
		
		return isValid & visited.size() == n;
	}
	
	private boolean DFSHelper2(int root, Map<Integer, Set<Integer>> graph, 
								Set<Integer> visited,
								int parent) {
		if (visited.contains(root)) {
		    return false;
		}						    
		visited.add(root);
		for (int child : graph.get(root)) {
			if (child == parent) continue;
		    boolean validChild = DFSHelper2(child, graph, visited, root);
			if (!validChild) {
				return false;
			}
		}
		return true;
	}
	

	/**
	 * Followup 1: 如果是有向图， 应该怎么解答
	 * 有向图要注意，首先只能有一个root。而且一定要从root开始遍历（无向图随便从那个节点开始遍历都可以）
	 * 所以这里用indegree来存所有有入度的点。剩下的和无向图差不多，都是每个点只能遍历一次，不过这次不能指向父亲节点了。
	 * 
	 * Followup 2: 检查是否是binary tree
	 * 如果要是binary tree，那么每个节点检查孩子是不是不超过两个即可。
	 */
	public boolean validTreeDirected(int n, int[][] edges) {
		Set<Integer> indegree = new HashSet<>();
		Map<Integer, Set<Integer>> graph = contructDirectedGraph(n, edges, indegree);
		
		int count = 0, root = -1;
		for (int i = 0; i < n; i++) {
			if (!indegree.contains(i)) {
				root = i;
				count++;
			}
		}
		//if has multiple root
		if (count != 1) return false;
		
		Set<Integer> visited = new HashSet<>();
		boolean isValid = DFSHelper1(root, graph, visited);
		return isValid && visited.size() == n;
	}
	
	private boolean DFSHelper1(int root, Map<Integer, Set<Integer>> graph, Set<Integer> visited) {
		if (visited.contains(root)) {
			return false;
		}
		visited.add(root);
		// if (graph.get(root).size() > 2) return false;  if want to make sure it's binary tree
		for (int child : graph.get(root)) {
			if (!DFSHelper1(child, graph, visited)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	/**
	 * 实际上，还有一个变体，就是看一个图里面有没有环存在。做法其实有一些不同
	 * 用两个set来记录访问过的节点，一个是visited记录所有访问过的节点，
	 * 一个是path，记录当前递归栈里面的节点，也就是当前路径。
	 * ！！！注意，这里一定要用path来判断是否有cycle， 不能用 visited，为什么呢，比如图  1,2  1,3  2,3
	 * 他们并没有组成cycle， 然是从1走到2， 2走到3的时候，3已经被visited过了，然后再从1走到3，如果是从visited
	 * 里面判断，会认为3是cycle，实际上并不是。所以只能从当前的recursive stack来判断是不是有cycyle
	 * 
	 * 		1-->2
	 *      |   | 
	 *      |  \|/
	 *      |-->3
	 *   
	 * 
	 * 对于每个点，我们去访问他的邻居，如果邻居没有被访问过，我们就走下一层
	 * 如果邻居被访问过了，而且在path里面，说明这个邻居指向他的某一个祖宗了，也就是有环了。
	 * 
	 * 这个解法针对的是有向图，所以我们要另外开一个 set来记录path，如果是无向图，就更简单
	 * 只要在当前节点的邻居里面发现被访问过的节点，而且该节点不是父亲。那么就可以说有环
	 *  private void dfs(Graph g, int v, int u) {  
	        marked[v] = true;  
	        for(int w : g.adj(v)) {  
	            if(!marked[w])  
	                dfs(g, w, v);  
	            else if(w != u)  
	                hasCycle = true;  
	        }  
	    }  
	 * 
	 */
	public boolean hasCycleDirectedGraph(int n, int[][] edges) {
		Set<Integer> indegree = new HashSet<Integer>(); //not useful in this case. 
		Map<Integer, Set<Integer>> graph = contructDirectedGraph(n, edges, indegree);
		
		Set<Integer> visited = new HashSet<>();
		Set<Integer> path = new HashSet<Integer>();
		boolean hasCycle = hasCycleHelper(0, graph, visited, path);
		return hasCycle;
	}

	private boolean hasCycleHelper(int root, Map<Integer, Set<Integer>> graph, Set<Integer> visited, Set<Integer> path) {
//		if (!visited.contains(root)) {
			visited.add(root);
			path.add(root);
	
			for (int n : graph.get(root)) {
				if (!visited.contains(n)) {
					if (hasCycleHelper(n, graph, visited, path)) {
						return true;
					}
				} else if (path.contains(n))
					return true;
			}
//		}
		path.remove(root);
		return false;
	}
	
	private Map<Integer, Set<Integer>> contructGraph(int n, int[][] edges) {
		
		Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
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
	
	private Map<Integer, Set<Integer>> contructDirectedGraph(int n, int[][] edges, Set<Integer> indegree) {
		Map<Integer, Set<Integer>> graph = new HashMap<Integer, Set<Integer>>();
		for (int i = 0; i < n; i++) {
			graph.put(i, new HashSet<Integer>());
		}
		for (int i = 0; i < edges.length; i++) {
			int u = edges[i][0];
			int v = edges[i][1];
			graph.get(u).add(v);
			indegree.add(v);
		}
		return graph;
	}

	public static void main(String[] args) {
		GraphValidTree clz = new GraphValidTree();
		int[][] edges = { { 0, 1 }, { 0, 2 }, {2, 3}, {2,4}};
//		System.out.println(clz.validTreeBFS(1, edges1));
//		System.out.println(clz.validTreeDFS(4, edges));
		System.out.println(clz.validTreeBFS(5, edges));
	}
}
