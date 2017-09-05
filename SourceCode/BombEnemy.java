
public class BombEnemy {
	/**
	 * 一个和bomb enemy非常类似的题目
	 *  http://www.1point3acres.com/bbs/thread-211221-1-1.html
	 *  Matrix中有0和1， 求1组成的最大的plus （+）形状的长度
	 *  
	 *  举个🌰 就是以每个不为1的cell为中心，向上下左右四个方向扩展，找到四个长度中最小的那个就是以这个Cell为中心的plus. 
	 *  例如，下面的。 以（2,2）为中心的plus长度为1. （Plus的外围是一个正方形）. 
	 *	0 0 1 0 0 1 0
		1 0 1 0 1 0 1
		1 1 1 1 1 1 1
		0 0 1 0 0 0 0
		0 0 0 0 0 0 0
		
	 *  如果直接暴力破解，对于每一个 1， 都上下左右搜索，时间复杂度是 O(M*N*(M+N)) 
	 *  为了优化，我们可以开四个二维数组，left[], right[], up[], down[], 分别存
	 *  某一个点往上，下，左，右四个方向走最多有多少个1， 之后再做一遍循环找最大值
	 *  建立四个数组时间复杂度是O(MN), 寻找最优解时间也是一样，最后总的复杂度就是 O(MN)
	 */
	public void maxPlusLength(int[][] grid) {
		//check if grid is null ....
		int n = grid.length;
		int m = grid[0].length;
		int[][] up = new int[n][m];  //how many 1 from 0,j to i,j
		int[][] down = new int[n][m]; // how many 1 from n-1,j to i,j
		int[][] left = new int[n][m]; //how many 1 from i,0 to i,j
		int[][] right = new int[n][m];//how many 1 from i,m-1 to i,j
		
		for (int i = 0; i < n; i++) {
			int count = 0;
			for (int j = 0; j < m; j++) {
				if (grid[i][j] == 0) count = 0;
				else count++;
				left[i][j] = count;
			}
		}
		
		for (int i = 0; i < n; i++) {
			int count = 0;
			for (int j = m-1; j >= 0; j--) {
				if (grid[i][j] == 0) count = 0;
				else count++;
				right[i][j] = count;
			}
		}
		
		for (int j = 0; j < m; j++) {
			int count = 0;
			for (int i = 0; i < n; i++) {
				if (grid[i][j] == 0) count = 0;
				else count++;
				up[i][j] = count;
			}
		}
		
		for (int j = 0; j < m; j++) {
			int count = 0;
			for (int i = n-1; i >= 0; i--) {
				if (grid[i][j] == 0) count = 0;
				else count++;
				down[i][j] = count;
			}
		}
		
		int max = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int minLeftRight = Math.min(left[i][j], right[i][j]);
				int minUpDown = Math.min(up[i][j], down[i][j]);
				int min = Math.min(minLeftRight, minUpDown);
				max = Math.max(max, min);
			}
		}
		System.out.println(max-1);
	}
	
	public static void main(String[] args) {
		int[][] grid1 ={{0, 0, 1, 0, 0, 1, 0},
						{1, 0, 1, 0, 1, 0, 1},
						{1, 1, 1, 1, 1, 1, 1},
						{0, 0, 1, 0, 0, 0, 0},
						{0, 0, 1, 0, 0, 0, 0}};
		int[][] grid2 = {{0,0,1,0},
						 {1,0,1,0},
						 {1,1,1,1},
						 {1,0,1,1}};
		
		BombEnemy clz = new BombEnemy();
		clz.maxPlusLength(grid1);
	}
}
