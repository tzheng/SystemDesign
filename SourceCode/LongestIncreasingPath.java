
public class LongestIncreasingPath {
	
	
	public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) 
            return 0;
        
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        int[][] cache = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                max = Math.max(dfs(matrix, i, j, visited, cache), max);
            }
        }
        
        return max;
    }
	
	int[] dx = {-1,1,0,0};
	int[] dy = {0,0,-1,1};
	
	private int dfs(int[][] matrix, int sx, int sy, boolean[][] visited, int[][] cache) {
		
		if (cache[sx][sy] != 0) 
			return cache[sx][sy];
		
		visited[sx][sy] = true;
		
		int val = 1;
		for (int i = 0; i < 4; i++) {
			int x = sx + dx[i], y = sy + dy[i];
			if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length || visited[x][y] 
					|| matrix[x][y] <= matrix[sx][sy]) { 
				continue;
			}
			val = Math.max(val, dfs(matrix, x, y, visited, cache) + 1);
		}
		visited[sx][sy] = false;
		
		cache[sx][sy] = val;
		return val;
		
	}
}
