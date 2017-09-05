
public class UniquePath {
	/**
	 * 62. Unique Paths
	 * 几乎属于DP 入门问题 f[i][j] 表示到i，j这个点多少条路
	 * 那么肯定到这个点左边有多少条路和到这个点上面有多少路，他们相加就好。
	 */
	public int uniquePaths(int m, int n) {
		int[][] f = new int[m][n];
		
		for (int i = 0; i < n; i++) {
			f[0][i] = 1;
		}
		
		for (int i = 0; i < m; i++) {
			f[i][0] = 1;
		}
		
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				f[i][j] = f[i-1][j] + f[i][j-1];
			}
		}
		return f[m-1][n-1];
	}
	
	/**
	 * 63. Unique Paths II
	 * 加了个障碍
	 */
	public int uniquePathsWithObstacles(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		int[][] f = new int[m][n];
		
		for (int i = 0; i < n; i++) {
			if (grid[0][i] == 1) break;
			f[0][i] = 1;
		}
		
		for (int i = 0; i < m; i++) {
			if (grid[i][0] == 1) break;
			f[i][0] = 1;
		}
		
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (grid[i][j] == 1) continue;
				f[i][j] = f[i-1][j] + f[i][j-1];
			}
		}
		return f[m-1][n-1];
	}
	
	/**
	 * followup, 加入能穿过一个障碍呢，就要变成 f[i][j][k]
	 */
	
}	
