import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumbersInArray {
	/**
	 * 448. Find All Numbers Disappeared in an Array
	 * 对于这种 1 ≤ a[i] ≤ n  的题目，都是通过转换 a[a[i]] 的状态来实现题目要求的
	 * 比如这题，some elements appear twice and others appear once.
	 * 
	 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
	 * 最后如果第i位数字没有被翻转，说明 i+1 是没出现过的。
	 *
	 */
	public List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			int val = Math.abs(nums[i]) - 1; // 注意要用abs -1，因为有可能当前数字被翻转成负数了
			if (nums[val] > 0) {
				nums[val] = -nums[val];
			}
		}

		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > 0) {
				ret.add(i + 1);
			}
		}
		return ret;
	}
	
	/**
	 * 442. Find All Duplicates in an Array
	 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), 
	 * some elements appear twice and others appear once.
	 * 
	 *  对于每一个 a[i], 把 a[a[i]] 设置为负数，如果 a[i]这个数字出现两次，最后a[a[i]]会变成正数
	 * 那么a[i]就是重复的数字。
	 */
	public List<Integer> findDuplicates(int[] nums) {
		List<Integer> ret = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			int index = Math.abs(nums[i]) - 1;
			if (nums[index] < 0) {
				ret.add(index + 1);
			} else {
				nums[index] = -nums[index];
			}
		}
		return ret;
	}
	
	/**
	 * 41. First Missing Positive
	 * Given an unsorted integer array, find the first missing positive integer.
	 */
	public int firstMissingPositive(int[] A) {
		int i = 0;
		while (i < A.length) {
			if (A[i] == i + 1 || A[i] <= 0 || A[i] > A.length)
				i++;
			else if (A[A[i] - 1] != A[i])
				swap(A, i, A[i] - 1);
			else
				i++;
		}
		i = 0;
		while (i < A.length && A[i] == i + 1)
			i++;
		return i + 1;
	}

	private void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
	
	/**
	 * 268. Missing Number
	 *  Pretty simple since we are told that we are missing only one number in [1,n], 
	 *  we just need to look at the difference between the sum([1,n]) = n * (n+1) / 2 
	 *  and the sum of nums in our array.
	 */
	public int missingNumber(int[] nums) {
		int sum = 0;
        for(int num: nums)
            sum += num;
        return (nums.length * (nums.length + 1) )/ 2 - sum;
    }
	
	
	/**
	 * 287. Find the Duplicate Number
	 * You must not modify the array (assume the array is read only).
	 * You must use only constant, O(1) extra space
	 * 
	 * 考虑到以上几个限制条件
	 * 可以用二分查找做， 如果左边
	 */
	public int findDuplicate(int[] nums) {
		int start = 0, end = nums.length-1;
		while (start <= end) {
			int mid = start + (end - start)/2;
			int count = 0;
			for (int a : nums) {
				if (a <= mid) count++;
			}
			//如果<=mid的数字比 mid少，说明比mid小的数字没有填满，比mid大的数字有重复占了小的的位置
			if (count <= mid) start = mid+1; 
			else end = mid-1;
		}
		return start;
	}
	/**
	 * 还有个O(n)的解法
	 */
	public int findDuplicate1(int[] nums) {
		if (nums.length == 0)
			return 0;
		int slow = 0, fast = 0;
		slow = nums[slow];
		fast = nums[nums[fast]];
		while (slow != fast) {
			if (slow == nums[slow])
				return slow;
			slow = nums[slow];
			fast = nums[nums[fast]];
		}
		fast = 0;
		while (slow != fast) {
			if (slow == nums[slow])
				return slow;
			slow = nums[slow];
			fast = nums[fast];
		}
		return slow;
	}
	
	public static void main(String[] args) {
		NumbersInArray clz = new NumbersInArray();
		int[] nums = {-1,-1,1};
//		System.out.println(clz.findDuplicate(nums));
		
//		System.out.println(clz.checkInclusion("ab", "a"));
		
	}
	

}
