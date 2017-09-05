import java.util.Arrays;

public class DiagonalTraverse {
	
	/**
	 * 最原始的解法，走一遍
	 */
	public int[] findDiagonalOrder(int[][] matrix) {
		if (matrix == null || matrix.length == 0) {
			return new int[0];
		}
		int m = matrix.length, n = matrix[0].length;
        int[] res = new int[m*n];
        int i = 0, j = 0, index = 0;
        boolean up = true;
        
        while (i < m && j < n) {
        	int x = i, y = j;
        	while (x >= 0 && x < m && y >= 0 && y < n) {
        		res[index++] = matrix[x][y];
        		if (up) {
        			x--; y++;
        		} else {
        			x++; y--;
        		}
        	}
        	
        	if (up) {
        		i = x+1; j = y-1;
        		if (j+1 <n) j++;
        		else i++;
        	} else {
        		i = x-1; j = y+1;
        		if (i+1 < m) i++;
        		else j++;
        	}
        	
        	up = !up;
        }
        return res;
    }
	
	
	
	/**
	 * 超级简化版
	 * 如果 row + col 是偶数，说明往上走  如果到了边界，要么col == n-1, row++， 要么row == 0, col++, 从偶数变成奇数。
	 * 如果 row + col 是奇数，说明往下走，如果到了变价，要么row == m-1, col++， 要么col == 0， row++， 从奇数变成偶数
	 * 
	 */
	public int[] findDiagonalOrderSimple(int[][] matrix) {
		if (matrix.length == 0)
			return new int[0];
		int r = 0, c = 0, m = matrix.length, n = matrix[0].length, arr[] = new int[m * n];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = matrix[r][c];
			if ((r + c) % 2 == 0) { // moving up
				if (c == n - 1) {
					r++;
				} else if (r == 0) {
					c++;
				} else {
					r--;
					c++;
				}
			} else { // moving down
				if (r == m - 1) {
					c++;
				} else if (c == 0) {
					r++;
				} else {
					r++;
					c--;
				}
			}
		}
		return arr;
	}
	
	
	public static void main(String[] args) {
		DiagonalTraverse clz = new DiagonalTraverse();
		int[][] matrix = {
				{ 1, 2, 3, 4 },
				{ 5, 6, 7, 8 },
				{ 9, 10, 11, 12 }
		};
		int[] res = clz.findDiagonalOrder(matrix);
		System.out.println(Arrays.toString(res));
	}
}
