import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MaxSumRectangle {
	/**
	 * 363. Max Sum of Rectangle No Larger Than K
	 * 
	 * O(n) = n^2 * mlogm.
	 */
	public int maxSumSubmatrix(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
	        return 0;
	    }
        int m = matrix.length, n = matrix[0].length, res = Integer.MIN_VALUE;
        
        for (int right = 0; right < n; right++) {
            int[] rowSum = new int[m];
            for (int left = right; left >= 0; left--) {
                int currSum = 0;
                TreeSet<Integer> set = new TreeSet<>();
                set.add(0);
                
                for (int row = 0; row < m; row++) {
                    rowSum[row] += matrix[row][left];
                    currSum += rowSum[row];
                    
                    Integer sum = set.ceiling(currSum- k);
                    if (sum != null) {
                        res = Math.max(res, currSum - sum);
                    }
                    set.add(currSum);
                }
            }
        }
        
        return res;
    }
	
	/**
	 * Find rectangle sum == k
	 */
	 public boolean find(int[][] matrix, int target) {
        for (int right = 0; right < matrix[0].length; right++) {
            int[] rowSum = new int[matrix.length];
            for (int left = right; left >= 0; left--) {
                Set<Integer> sum = new HashSet<>();
                int curSum = 0;
                sum.add(0);
                for (int row = 0; row < matrix.length; row++) {
                    rowSum[row] += matrix[row][left];
                    curSum += rowSum[row];
                    if (sum.contains(curSum - target)) {
                        return true;
                    }
                    sum.add(curSum);
                }
            }
        }
        return false;
    }

	
	
}
