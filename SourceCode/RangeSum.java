
public class RangeSum {
	/**
	 * 303. Range Sum Query - Immutable
	 * 	You may assume that the array does not change.
	 * 	There are many calls to sumRange function.
	 */
	public int sumRange(int[] nums, int i, int j) {
		int[] pathSum = new int[nums.length + 1];
		int sum = 0;
		pathSum[0] = 0;
		for (int k = 1; k <= nums.length; k++) {
			pathSum[k] = pathSum[k-1] + nums[k-1];
		}
		return pathSum[j+1] - pathSum[i];
	}
	
	/**
	 * 304. Range Sum Query 2D - Immutable
	 * You may assume that the matrix does not change.
	 * There are many calls to sumRegion function.
	 * You may assume that row1 ≤ row2 and col1 ≤ col2.
	 * 
	 * 1. 对于每一行算pathsum，然后每次计算,这样 复杂度 O(n), n = num of rows needed
	 * 2. 算整个矩阵的pathSum， sum2origion, 这样复杂度O(1)
	 */
	class NumMatrix {
		int[][] rangeSum = null;
		private int[][] sum2Origin = null;
	    public NumMatrix(int[][] matrix) {
	    	int m = matrix.length, n = matrix[0].length;
	    	rangeSum = new int[m+1][n+1];
	    	for (int i = 1; i <= m; i++) {
	    		for (int j = 1; j <= n; j++) {
	    			rangeSum[i][j] = rangeSum[i-1][j] + rangeSum[i][j-1] - rangeSum[i-1][j-1]
	    					+ matrix[i][j];
	    		}
	    	}
	    }
	    
	    public int sumRegion(int row1, int col1, int row2, int col2) {
	    	if (rangeSum == null)
	            return 0;
	    	return rangeSum[row2+1][col2+1] - rangeSum[row2+1][col1] 
	    			- rangeSum[row1][col2+1] + rangeSum[row1][col1];
	    }
	}
	
	
	/**
	 * Segment Tree
	 * 线段树是一棵二叉树，记为T(a, b)，参数a,b表示区间[a,b]，其中b-a称为区间的长度，记为L。
	 */
	static class SegmentTreeNode {
		int start, end;
		SegmentTreeNode left = null, right = null;
		int sum = 0;
		public SegmentTreeNode(int start, int end) {
			this.start = start; 
			this.end = end;
		}
	}
	
	public static SegmentTreeNode buildTree(int[] nums, int start, int end) {
		if (start > end) 
			return null;
		SegmentTreeNode ret = new SegmentTreeNode(start, end);
		if (start == end) {
			ret.sum = nums[start];
		} else {
			int mid = start + (end-start)/2;
			ret.left = buildTree(nums, start, mid);
			ret.right = buildTree(nums, mid+1, end);
			ret.sum = ret.left.sum + ret.right.sum;
		}
		return ret;
	}
	
	
	/**
	 * 307. Range Sum Query - Mutable
	 * 无法使用 pathSum了就要用Segment Tree
	 */
	class NumArray {
		SegmentTreeNode root = null;
		
	    public NumArray(int[] nums) {
	       root = RangeSum.buildTree(nums, 0, nums.length-1);
	    }
	    
	    public void update(int i, int val) {
	        update(root, i, val);
	    }
	    
	    private void update(SegmentTreeNode root, int pos, int val) {
	    	if (root.start == root.end) {
	    		root.sum = val;
	    		return;
	    	} 
	    	int mid = root.start + (root.end - root.start)/2;
	    	if (pos <= mid) {
	    		update(root.left, pos, val);
	    	} else {
	    		update(root.right, pos, val);
	    	}
	    	root.sum = root.left.sum + root.right.sum;
	    }
	    
	    public int sumRange(int i, int j) {
	        return sumRange(root, i, j);
	    }
	    
	    public int sumRange(SegmentTreeNode root, int i, int j) {
	    	if (root.start == i && root.end == j) {
	    		return root.sum;
	    	}
	    	int mid = root.start + (root.end - root.start)/2;
	    	if (j <= mid) {
	    		return sumRange(root.left, i, j);
	    	} else if (i >= mid+1) {
	    		return sumRange(root.right, i, j);
	    	} else {
	    		return sumRange(root.left, i, mid) + sumRange(root.right, mid+1, j);
	    	}
	    }
	}
}
