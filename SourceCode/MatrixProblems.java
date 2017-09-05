
public class MatrixProblems {
	 
	/**
	 * 73. Set Matrix Zeroes
	 * 空间O（m+n）的解法很容易想到，就是row[], col[] 两个数组
	 *   if (row[i] || col[j] ) matrix[i][j] = 0;
	 *   
	 * 这里要优化成O（1）的解法，实际上就是利用第一行，第一列来作为row[], col[]使用
	 */
	public void setZeroes(int[][] matrix) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		
		boolean row0 = false, col0 = false;
		for (int i = 0; i < cols && !row0; i++) {
			if (matrix[0][i] == 0)
				row0 = true;
		}
		
		for (int i = 0; i < rows && !col0; i++) {
			if (matrix[i][0] == 0)
				col0 = true;
		}
		
		for (int i = 1; i < rows; i++) {
			for (int j = 1; j < cols; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}
		
		for (int i = 1; i < rows; i++) {
			for (int j = 1; j < cols; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) {
					matrix[i][j] = 0;
				}
			}
		}
		
		if (row0) 
			for (int i = 0; i < cols; i++) 
				matrix[0][i] = 0;
		if (col0)
			for (int i = 0; i < rows; i++) 
				matrix[i][0] = 0;
	}
	
	/**
	 * 463. Island Perimeter
	 * 虽然给了一个矩阵，但是是个纯数学题。
	 */
	public int islandPerimeter(int[][] grid) {
        int islands = 0, neighbors = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    islands++;
                    if (i < grid.length-1 && grid[i+1][j] == 1) neighbors++;
                    if (j < grid[0].length -1 && grid[i][j+1] == 1) neighbors++;
                }
            }
        }
        
        /**
         * 
         * 非数学的做法
         *     
         * for(int i=0; i<grid.length; i++) {
		        for(int j=0; j<grid[0].length; j++) {
		            if(grid[i][j] == 0)
		                continue;
		            
		            for(int k=0; k<d.length; k++) {
		                int x=i+d[k][0], y=j+d[k][1];
		                if(x<0 || x>=grid.length || y<0 || y>=grid[0].length || grid[x][y] == 0)
		                    perimeter++;
		            }
		        }
		    }
         */
        return 4 * islands - 2* neighbors;
    }
	
	
	/**
	 * 221. Maximal Square
	 * 暴力破解办法，对于每一个1，算它能围成的面积。计算从长度1开始, 每次验证新加进来的行和列是不是都是1
	 *       验证这一列
	 *       |
	 *      \|/
	 *       '
	 *    1 |1   
	 *    __|
	 *    1  1  <- 验证这一行
	 */
	public int maximalSquare(char[][] matrix) {
        int max = 0;
        for (int i = 0 ; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '0') {
                    continue;
                }
                max = Math.max(max, square(matrix, i, j));
            }
        }
        return max;
    }
	
	public int square(char[][] m, int i, int j) {
		int len = 1; 
		boolean hasZero = false;
		while (!hasZero && i + len < m.length && j + len < m[0].length) {
			for (int k = j; k <= j + len; k++) {
				if (m[i+len][k] == '0') {
					hasZero = true;
					break;
				}
			}
			for (int k = i; k <= i + len; k++) {
				if (m[k][j+len] == '0') {
					hasZero = true;
					break;
				}
 			}
			if (!hasZero) len++;
		}
		return len * len;
	}
	
	/**
	 *  暴力做法时间是 O(n^2 * m^2)
	 *  这题同样可以动态规划解决。
	 */
	public int maximalSquareDP(char[][] matrix) {
        int[][] max = new int[matrix.length][matrix[0].length];
        
        int len = 0;
        for (int i = 0; i < matrix[0].length; i++) {
        	if (matrix[0][i] == '1')  {
        		max[0][i] = 1;
        		len = 1;
        	}
        }
        
        for (int i = 0; i < matrix.length; i++) {
        	if (matrix[i][0] == '1') {
        		max[i][0] = 1;
        		len = 1;
        	}
        }
        
        for (int i = 1 ; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] == '1') {
                	max[i][j] = Math.min(max[i-1][j-1], Math.min(max[i-1][j], max[i][j-1])) + 1;
                	len = Math.max(len, max[i][j]);
                }
            }
        }
        return len * len;
    }
	
	/**
	 * 361. Bomb Enemy 炸弹人游戏
	 * 1. 简单做法，就是维持一个rowHit和columnHit，初始的时候，或者遇到墙的时候，适当地做更新，之后就可以一直使用了。
	 * 	  时间是 O(mn)
	 * 2. DP https://discuss.leetcode.com/topic/48603/java-straightforward-solution-dp-o-mn-time-and-space/2
	 */
	public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0) {
        	return 0;
        }
        
        int n = grid.length, m = grid[0].length;
        int rowHit = 0;
        int[] colHit = new int[m];
        int max = 0;
        
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < m; j++) {
        		if (i == 0 || grid[i-1][j] == 'W') {
        			colHit[j] = 0;
        			for (int k = i; k < n; k++) {
        				if (grid[k][j] == 'E') colHit[j]++;
        				else if (grid[k][j] == 'W') break;
        			}
        		}
        		
        		if (j == 0 || grid[i][j-1] == 'W') {
        			rowHit = 0;
        			for (int k = j; k < m; k++) {
        				if (grid[i][k] == 'E') rowHit++;
        				else if (grid[i][k] == 'W') break;
        			}
        		}
        		
        		if (grid[i][j] == '0') {
        			max = Math.max(max, rowHit + colHit[j]);
        		}
        	}
        }
        
        return max;
	}
	/**
	 * DP的做法就是先建立up, down, left, right 四个数组，
	 * @param grid
	 * @return
	 */
	public int maxKilledEnemiesDP(char[][] grid) {
        if (grid == null || grid.length == 0) {
        	return 0;
        }
        
        int n = grid.length, m = grid[0].length;
        int[][] up = new int[n][m], down = new int[n][m];
        int[][] right = new int[n][m], left = new int[n][m];
        
        for (int i = 0; i < n; i++) {
        	for (int j = 0; j < m; j++) {
        		if (grid[i][j] != 'W') {
        			int add = (grid[i][j] == 'E'? 1:0);//要看当前位置是不是E
        			up[i][j] = i == 0? add: up[i-1][j] + add;
        			left[i][j] = j== 0? add: left[i][j-1] + add;
        		}
        	}
        }
        
        int max = 0;
        for (int i = n-1; i >= 0; i--) {
        	for (int j = m-1; j >= 0; j--) {
        		if (grid[i][j] != 'W') {
        			int add = (grid[i][j] == 'E'? 1:0); //要看当前位置是不是E
        			down[i][j] = i == n-1 ? add:down[i+1][j] + add;
        			right[i][j] = j == m-1? add:right[i][j+1] + add;
        		}
        		if (grid[i][j] == '0') //只有在0的位置才可以计算，如果在E的位置也计算，会出错的
        			max = Math.max(max, left[i][j] + right[i][j] + up[i][j] + down[i][j]);
        	}
        }
        
        return max;
	}
	
	/**
	 *  Bomb Enemy 变体
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
		
	 *  解法和炸弹人问题差不多。甚至还更简单，因为没有墙壁了
	 *  如果直接暴力破解，对于每一个 1， 都上下左右搜索，时间复杂度是 O(M*N*(M+N)) 
	 *  为了优化，我们可以开四个二维数组，left[], right[], up[], down[], 分别存
	 *  某一个点往上，下，左，右四个方向走最多有多少个1， 之后再做一遍循环找最大值
	 *  建立四个数组时间复杂度是O(MN), 寻找最优解时间也是一样，最后总的复杂度就是 O(MN)
	 */
	public void maxPlusLength(int[][] grid) {
		//null check ...
		int n = grid.length, m = grid[0].length;
		int[][] up = new int[n][m], down = new int[n][m];
        int[][] right = new int[n][m], left = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (grid[i][j] != 1) continue;
				up[i][j] = i == 0 ? 1 : up[i-1][j] + 1;
				left[i][j] = j == 0? 1 : left[i][j-1] + 1;
			}
		}
		
		int max = 0;
        for (int i = n-1; i >= 0; i--) {
        	for (int j = m-1; j >= 0; j--) {
        		if (grid[i][j] != 1) continue;
        		int add = 1;
        		down[i][j] = i == n-1? add : down[i+1][j] + add;
        		right[i][j] = j == m-1? add : right[i][j+1] + add;
        		int min = Math.min(Math.min(up[i][j], left[i][j]), 
        						   Math.min(down[i][j], right[i][j]));
        		max = Math.max(max, min);
        	}
        }
		
        System.out.println("Max Plus Len: " + max);
	}
	
	
	
	public static void main(String[] args) {
		String[] strs = {"0E00","E0WE","0E00"};
		char[][] arr = new char[3][4];
		for (int i = 0; i < strs.length; i++) {
			arr[i] = strs[i].toCharArray();
		}
		MatrixProblems clz = new MatrixProblems();
		clz.maxKilledEnemiesDP(arr);
		
		int[][] grid1 ={{0, 0, 1, 0, 0, 1, 0},
					    {1, 0, 1, 0, 1, 0, 1},
					    {1, 1, 1, 1, 1, 1, 1},
					    {0, 0, 1, 0, 0, 0, 0},
					    {0, 0, 0, 0, 0, 0, 0}};
		int[][] grid2 = {{0,0,1,0},
						 {1,0,1,0},
						 {1,1,1,1},
						 {1,0,1,1}};
		clz.maxPlusLength(grid2);
	}
	
}

