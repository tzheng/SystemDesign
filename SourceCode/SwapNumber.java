import java.util.Arrays;

public class SwapNumber {
	/**
	 * 给你一个数组int[] nums，只swap一次，让你maximize这个数组能组成的数，
	 * 假设数组里所有的数都是一位数,举个例子，[4, 2, 1, 3, 5]代表的数是42135，
	 * swap一次可以得到最大的数是[5, 2, 1, 3, 4] 
	 * 
	 * 其实就是从后往前找最大的值的idx，如果找到了，mark下，作为targetIdx。如果看到比最大值小的，纪录resIdx。
	 * 注意如果更新了最大，不可以马上更新target因为左边如果没有更小的话，就还是之前的res和target：
	 */
	public void swapOnce(int[] nums) {
		System.out.println("Before: " + Arrays.toString(nums));
		
		int targetIdx = -1;
		int max = Integer.MIN_VALUE;
		
		int resIdx = -1;
		for (int i = nums.length - 1; i >= 0; i--) {
			if (nums[i] > max) {
				max = nums[i];
				targetIdx = i;
			} else {
				resIdx = i;
			}
		}
		
		if (resIdx != -1) {
			int swap = nums[resIdx];
			nums[resIdx] = nums[targetIdx];
			nums[targetIdx] = swap;
		}
		
		System.out.println("After: " + Arrays.toString(nums));
	}
	
	/**
	 * 如果可以Swap K 次怎么办
	 * http://www.geeksforgeeks.org/find-maximum-number-possible-by-doing-at-most-k-swaps/
	 */
	public static void main(String[] args) {
		int[] num1 = {4, 2, 1, 3, 5};
		
		SwapNumber clz = new SwapNumber();
		clz.swapOnce(num1);
		
		clz.swapOnce(new int[]{1,7,7});
	}
	
}
