
public class JumpGame {
	/**
	 * 55. Jump Game
	 * 判断是否能跳
	 * DP的公式很容易想到，如下，但是复杂度是n 平方。
	 */
	public boolean canJump(int[] nums) {
		if (nums == null || nums.length == 0) {
			return false;
		}
		boolean[] f = new boolean[nums.length];
		f[0] = true;
		for (int i = 1; i < nums.length; i++) {
			for (int j = 0; j < i; j++) {
				if (f[j] && j + nums[j] >= i) {
					f[i] = true;
					break;
				}
			}
		}
		return f[nums.length-1];
	}
	
	/**
	 * 实际上可以直接O(N)来解决。只需要贪心就好。
	 */
	public boolean canJumpGreedy(int[] nums) {
		if (nums == null || nums.length == 0) {
			return false;
		}
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i > max) return false;
			max = Math.max(max, i + nums[i]);
		}
		return true;
	}
	
	/**
	 * 45. Jump Game II
	 * 如果要计算多少步，和判断是否一样，简单的DP很容易，复杂度一样是O(n^2)，会超时
	 */
	public int jump(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int[] f = new int[nums.length];
		f[0] = 0;
		for (int i = 1; i < nums.length; i++) {
			f[i] = Integer.MAX_VALUE;
			for (int j = 0; j < i; j++) {
				if (f[j] != Integer.MAX_VALUE && j + nums[j] >= i) {
					f[i] = Math.min(f[i], f[j] + 1);
				}
			}
		}
		return f[nums.length-1];
	}
	
	/**
	 * 同样可以用greedy 的办法解决。
	 * try to change this problem to a BFS problem, where nodes in level i are all the nodes that can be reached in i-1th jump. 
	 * for example. 2 3 1 1 4 , is
			2||
			3 1||
			1 4 ||
	 */
	public int jumpGreedy(int[] nums) {
		if (nums.length < 2) return 0;
		int level = 0, currMax = 0, i = 0, nextMax = 0;
		
		while (currMax < nums.length) {
			level++;
			nextMax = 0;
			for (; i <= currMax; i++) {
				nextMax = Math.max(nextMax, i + nums[i]);
				if (nextMax >= nums.length-1) 
					return level;
			}
			if (nextMax > currMax)
			    currMax = nextMax;
		}
		
		return level;
	}
	
	public static void main(String[] args) {
		JumpGame clz = new JumpGame();
		clz.jumpGreedy(new int[]{1,1,1,1});
	}
	
}
