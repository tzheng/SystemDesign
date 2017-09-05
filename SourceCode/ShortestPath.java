import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPath {
	int smallest = Integer.MAX_VALUE;
	int target_i, target_j;
	
	/**
	 * 最短路径之可以穿过墙 maxCheat次
	 */
	public int findShortestPathDFS(int[][] matrix, int i, int j, int maxCheat) {
	    boolean[][] visited = new boolean[matrix.length][matrix[0].length];
	    target_i = i;
	    target_j = j;
	    helper(matrix, visited, 0, 0, 0, maxCheat);
	    return smallest;
	}

	private void helper(int[][] matrix, boolean[][] visited, int cur_i, int cur_j, int cur_len, int numCheat) {
	    if (cur_i < 0 || cur_i >= matrix.length 
	    		|| cur_j < 0 || cur_j >= matrix[0].length 
	    		|| visited[cur_i][cur_j] || matrix[cur_i][cur_j] == 1 && numCheat == 0) return;

	    if (cur_i == target_i && cur_j == target_j) {
	        smallest = Math.min(smallest, cur_len);
	        return;
	    }
	    if (matrix[cur_i][cur_j] == 1) numCheat--;
	    visited[cur_i][cur_j] = true;

	    helper(matrix, visited, cur_i - 1, cur_j, cur_len + 1, numCheat);
	    helper(matrix, visited, cur_i, cur_j - 1, cur_len + 1, numCheat);
	    helper(matrix, visited, cur_i + 1, cur_j, cur_len + 1, numCheat);
	    helper(matrix, visited, cur_i, cur_j + 1, cur_len + 1, numCheat);

	    visited[cur_i][cur_j] = false;
	}
	
	
	int[] dx = {-1,1,0,0};
	int[] dy = {0,0,-1,1};
	public void shortestPathTwoWayBFS(int[][] matrix, int[] orig, int[] dest) {
		int m = matrix.length, n = matrix[0].length;
		boolean[][] visited = new boolean[m][n];
		Queue<int[]> start = new LinkedList<>(), end = new LinkedList<>();
		start.offer(orig);
		end.offer(dest);
		int level = 0;
		while (!start.isEmpty() && !end.isEmpty()) {
			if (start.size() > end.size()) {
				Queue<int[]> tmp = start;
				start = end;
				end = tmp;
			}
			
			int size = start.size();
			for (int j = 0; j < size; j++) {
				int[] curr = start.poll();
				
				for (int i = 0; i < 4; i++) {
					int x = curr[0] + dx[i], y = curr[1] + dy[i];
					if (x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] == '1') {
						continue;
					}
					int[] next = new int[]{x, y};
					//注意要在这里检查是否在end里
					if (inEnd(end, curr)) {
						System.out.println("Find Path: " + (level+1) + ", " + Arrays.toString(curr));
						return;
					}
					//之后再检查是否visite过， 因为如果一个点在end里面，那么这个点一定被visit过
					if (!visited[x][y]) {
						visited[x][y] = true;
						start.offer(next);
					}
				}
			}
			level++;
		}
		System.out.println("No Path");
	}
	
	public void shortestPathBFS(int[][] matrix, int[] orig, int[] dest) {
		int m = matrix.length, n = matrix[0].length;
		boolean[][] visited = new boolean[m][n];
		Queue<int[]> queue = new LinkedList<>();
		
		queue.offer(orig);
		int level = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int j = 0; j < size; j++) {
				int[] curr = queue.poll();
				if (curr[0] == dest[0] && curr[1] == dest[1]) {
					System.out.println("Find Path: " + level + ", " + Arrays.toString(curr));
					return;
				}
				for (int i = 0; i < 4; i++) {
					int x = curr[0] + dx[i], y = curr[1] + dy[i];
					if (x < 0 || x >= m || y < 0 || y >= n || matrix[x][y] == '1' || visited[x][y]) {
						continue;
					}
					int[] next = new int[]{x, y};
					visited[x][y] = true;
					queue.offer(next);
				}
			}
			level++;
		}
		
		System.out.println("No Path");
	}
	
	
	public boolean inEnd(Queue<int[]> end, int[] curr) {
		for (int[] tmp : end) {
			if (curr[0] == tmp[0] && curr[1] == tmp[1]) return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		int[][] matrix = {{0,1,0,0,0},
						  {0,0,0,1,0},
						  {0,1,0,1,0},
						  {0,1,0,0,0},
						  {0,0,0,1,0}};
		int[] start = {0,0};
		int[] end = {4,4};
		
//		int[][] matrix = {{0,0,0},
//				  		  {0,0,0}};
//		int[] start = {0,0};
//		int[] end = {1,2};
		
		Queue<int[]> test = new LinkedList<>();
		test.offer(start);
		int[] tmp = {0,0};
		
		
		ShortestPath clz = new ShortestPath();
		System.out.println(clz.inEnd(test,tmp));
		clz.shortestPathTwoWayBFS(matrix, start, end);
		clz.shortestPathBFS(matrix, start, end);
	}
}
