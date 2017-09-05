import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Matrix01 {
    public int[][] updateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return matrix;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[][] ans = new int[m][n];
        
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (matrix[i][j] == 1) {
        			int up = i > 0 ? ans[i-1][j] : Integer.MAX_VALUE - 1;
        			int left = j > 0 ? ans[i][j-1] : Integer.MAX_VALUE - 1;
        			// 特别需要注意，这里要和 integer.max-1 去min，因为在后面，有可能会 + 1
        			ans[i][j] = Math.min(Integer.MAX_VALUE - 1,Math.min(up, left) + 1);
        		}
        	}
        }
        
        for (int i = m-1; i >= 0; i--) {
        	for (int j = n-1; j >= 0; j--) {
        		if (matrix[i][j] == 1) {
	        		int down = i == m-1? Integer.MAX_VALUE-1 : ans[i+1][j];
	        		int right = j == n-1 ? Integer.MAX_VALUE-1 : ans[i][j+1];
	        		ans[i][j] = Math.min(ans[i][j], Math.min(down, right) + 1);
        		}
        	}
        }
     
        return ans;
    }
    
    
    public int[][] updateMatrixBFS(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[] {i, j});
                }
                else {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] d : dirs) {
                int r = cell[0] + d[0];
                int c = cell[1] + d[1];
                if (r < 0 || r >= m || c < 0 || c >= n || 
                    matrix[r][c] <= matrix[cell[0]][cell[1]] + 1) continue;
                queue.add(new int[] {r, c});
                matrix[r][c] = matrix[cell[0]][cell[1]] + 1;
            }
        }
        
        return matrix;
    }

    
    public static void main(String[] args) {
    	Matrix01 clz = new Matrix01();
    	
    }
}
