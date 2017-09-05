import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionFindSummary {
	/**
	 * union find 是一个写一遍就能掌握的概念，对于很多无向图的联通体的问题都可以秒杀
	 * 首先先写一个union find的模板
	 * 
	 * （ N 个对象， M 次 union-find 操作）  最坏时间复杂度 N（初始化） + MlogN 
	 */
	class UnionFind {
		int[] id;
		int[] size;  //size 的存在是为了更好的平衡，并不是必要的
		int count;  //当前一共有多少个connected component
		public UnionFind(int n) {
			id = new int[n];
			size = new int[n];
			for (int i = 0; i < n; i++) {
				id[i] = i;
				size[i] = 1;
			}
			count = n;
		}
		
		public int find(int p) {
			while (p != id[p]) {
				id[p] = id[id[p]];  //path compression, 保证logn的复杂度
				p = id[p];
			}
			return p;
		}
		
		public void union(int p, int q) {
			int i = find(p), j = find(q);
			if (i == j) return;  //如果已经联通了，不做操作。
			if (size[i] > size[j]) { //如果没有size， 直接 id[j] = i即可
				id[j] = i;
				size[i] += size[j];
			} else {
				id[i] = j;
				size[j] += size[i];
			}
			count--; //把两个不联通的联通起来，要减少数量
		}
		
		public int count() {
			return this.count;
		}
	}
	
	/**
	 * 有了上面的模板，再来看leetcode上面相关题目。
	 * 	200	Number of Islands	33.5%	Medium	
	 *  128	Longest Consecutive Sequence	36.0%	Hard	
	 *  305	Number of Islands II 	38.6%	Hard	
	 *  261	Graph Valid Tree 	37.3%	Medium	
	 *  323	Number of Connected Components in an Undirected Graph 	47.5%	Medium	
	 *  547	Friend Circles	
	 */
	
	/**
	 * 200	Number of Islands 
	 *  BFS 的解法另外总结，不在这里写，这里注意，把二维转换为一维的方法 i*columLength + j
	 *  只要有个1和他们右边或者下面相连，就把它们union起来。
	 */
	public int numIslands(char[][] grid) {
		if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
		
		int m = grid.length, n = grid[0].length, count = 0;
		for(int i = 0; i < m; i++) {
	        for(int j = 0; j < n; j++) {
	            if(grid[i][j] == '1') count++;
	        }
	    }
		
		UnionFind uf = new UnionFind(n*m);
		uf.count = count; //需要注意一点，这里的count并不是 n*m, 因为题目只看island的个数
		for (int i = 0; i < m; i++) {
	        for(int j = 0; j < n; j++) {
	            if(grid[i][j] == '0') continue;
	            int p = i * n + j;
	            int q;
	            if(i > 0 && grid[i - 1][j] == '1') {
	                q = p - n;
	                uf.union(p, q);
	            }
	            if(i < m - 1 && grid[i + 1][j] == '1') {
	                q = p + n;
	                uf.union(p, q);
	            }
	            if(j > 0 && grid[i][j - 1] == '1') {
	                q = p - 1;
	                uf.union(p, q);
	            }
	            if(j < n - 1 && grid[i][j + 1] == '1') {
	                q = p + 1;
	                uf.union(p, q);
	            }
	        }
	    }
		return uf.count();
	}
	
	/**
	 * 305. Number of Islands II  -- 不断往grid里面加新的点，看看每次加完之后岛的个数。
	 * 这个就一定要使用union find了，时间复杂度是 nlogn, n是position的个数。
	 * 不然每次新添加一个position最一次bfs，效率太低了。
	 */
	int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
	public List<Integer> numIslands2(int m, int n, int[][] positions) {
		 List<Integer> result = new ArrayList<>();
		 UnionFind uf = new UnionFind(m*n);
		 uf.count = 0;
		 int[][] matrix = new int[m][n];
		 for (int[] p : positions) {
			 uf.count++;
			 matrix[p[0]][p[1]] = 1;
			 for (int i = 0; i < 4; i++) {
				 int x = p[0] + dx[i];
				 int y = p[1] + dy[i];
				 if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] == 1) {
					 uf.union(p[0]*n + p[1], x*n + y);
				 }
			 }
			 result.add(uf.count);
		 }
		 return result;
	}
	
	/**
	 * 323. Number of Connected Components in an Undirected Graph
	 * 这就是一个更标准的union find了
	 */
	public int countComponents(int n, int[][] edges) {
		UnionFind uf = new UnionFind(n);
		for (int[] edge : edges) {
			uf.union(edge[0], edge[1]);
		}
		return uf.count();
	}
	
	/**
	 * 128. Longest Consecutive Sequence
	 * Given [100, 4, 200, 1, 3, 2],
	 * The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
	 * 
	 * 咋看之下和union find没有关系。但是实际上连续的数字都可以连起来成为一个connected component，最后找size最大的
	 * 复杂度 最坏O(nlogn) - O(n)
	 */
	public int longestConsecutive(int[] nums) {
		 Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		 UnionFind uf = new UnionFind(nums.length);
		 for (int i = 0; i < nums.length; i++) {
			 if (map.containsKey(nums[i])) continue;
			 map.put(nums[i], i);
			 if (map.containsKey(nums[i] - 1)) {
				 uf.union(nums[i], nums[i]-1);
			 }
			 if (map.containsKey(nums[i] + 1)) {
				 uf.union(nums[i], nums[i]+1);
			 }
		 }
		 int max = 1;
		 for (int num : uf.size) {
			 max = Math.max(max, num);
		 }
		 return max;
	}
	
	/**
	 * 当然，这题union find并不是最好的办法，HashMap 可以做 O(N)
	 */
	public int longestConsecutiveHash(int[] num) {
		int res = 0;
	    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	    for (int n : num) {
	        if (!map.containsKey(n)) {
	            int left = (map.containsKey(n - 1)) ? map.get(n - 1) : 0;
	            int right = (map.containsKey(n + 1)) ? map.get(n + 1) : 0;
	            // sum: length of the sequence n is in
	            int sum = left + right + 1;
	            map.put(n, sum);
	            // keep track of the max length 
	            res = Math.max(res, sum);
	            // extend the length to the boundary(s)
	            // of the sequence
	            // will do nothing if n has no neighbors
	            map.put(n - left, sum);
	            map.put(n + right, sum);
	        }
	    }
	    return res;
    }
	
	/**
	 * 261. Graph Valid Tree
	 * 这题同样有DFS的解法，但是因为是无向图，所以可以用union find, 注意！如果是有向图就不可以了。
	 */
	public boolean validTree(int n, int[][] edges) {
        // initialize n isolated islands
        int[] nums = new int[n];
        Arrays.fill(nums, -1);
        
        // perform union find
        for (int i = 0; i < edges.length; i++) {
            int x = find(nums, edges[i][0]);
            int y = find(nums, edges[i][1]);
            // if two vertices happen to be in the same set
            // then there's a cycle
            if (x == y) return false;
            // union
            nums[x] = y;
        }
        
        return edges.length == n - 1;
    }
    
    int find(int nums[], int i) {
        if (nums[i] == -1) return i;
        return find(nums, nums[i]);
    }
    
    /**
     * 547. Friend Circles
     * 又是一个标准的union find 模板题目。
     */
    public int findCircleNum(int[][] M) {
    	int n = M.length;
    	UnionFind uf = new UnionFind(n);
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			if (M[i][j] == 1) {
    				uf.union(i, j);
    			}
    		}
    	}
    	return uf.count;
    }
    
    
}
