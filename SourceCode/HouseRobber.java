
public class HouseRobber {
	/**
	 * 198. House Robber
	 * 两个连起来的房子不能抢，给每个房子的价值，求最大。
	 *
	 */
	public int rob(int[] nums, int low, int high) {
		if (nums == null || nums.length <=1) {
            return nums.length == 0 ? 0 : nums[0];
        }
		int include = 0, exclude = 0;
		for (int i = low; i <= high; i++) {
			int rob = exclude + nums[i];
            int unrob = Math.max(include, exclude);
            exclude = unrob;
            include = rob;
		}
		return Math.max(include, exclude);
		
		//上面给出的是不需要额外空间的解法，对应的状态转移方程思路如下
//        int[][] dp = new int[nums.length][2];
//        dp[0][0] = 0;
//        dp[0][1] = nums[0];
//	    for (int i = 1; i < nums.length; i++) {
//	        dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
//	        dp[i][1] = nums[i] + dp[i - 1][0];
//	    }
//	    return Math.max(dp[nums.length-1][0], dp[nums.length-1][1]);
	}
	
	/**
	 * 213. House Robber II
	 * 和上面一题一样，唯一的不同是，他们围成圈子，第一个和最后一个相邻。
	 * 思路就是分别计算 0 - len-2,  1 - len-1 的结果
	 * 相当于抢第 0 个，不抢最后一个， 和不抢第0个，抢最后一个的结果。然后取最大。
	 */
	public int rob(int[] nums) {
		if (nums == null || nums.length <=1) {
            return nums.length == 0 ? 0 : nums[0];
        }
		if (nums.length == 2) return Math.max(nums[0], nums[1]);
		return Math.max(rob(nums, 0, nums.length-2), rob(nums, 1, nums.length-1));
	}
	
	
	/**
	 * 337. House Robber III (uber)
	 * 题目改成 all houses in this place forms a binary tree
	 * 如果两个连着的点被抢了，就报警。 
	 * 思路就是做DFS，每次返回两个值，一个是抢了当前节点的，一个是不抢当前节点的。
	 * 对于当前节点，如果抢了，那么就取不抢左右节点的和，与当前节点的价值相加。
	 * 如果不抢，那么要看max(不抢左边，抢左边), max(不抢右边，抢右边) 相加。
	 */
	public int rob(TreeNode root) {
		int[] ret = robTree(root);
		return Math.max(ret[0], ret[1]);
	}
	
	public int[] robTree(TreeNode node) {
		if (node == null) return new int[]{0,0};
		if (node.left == null && node.right == null) {
			return new int[]{node.val, 0};
		}
		
		int[] left = robTree(node.left);
		int[] right = robTree(node.right);
		
		int include = node.val + left[1] + right[1];
		int exclude = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
		return new int[]{include, exclude};
	}
	
		
}
