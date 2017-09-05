import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class SubarrayProblems {
	/**
	 * Leetcode 53 Maximum Subarray 找subarray， sum最大
	 * For example, given the array [−2,1,−3,4,−1,2,1,−5,4], 
	 * the contiguous subarray [4,−1,2,1] has the largest sum = 6. 
	 */
	public int maxSubarray(int[] a) {
		int sum = 0, max = Integer.MIN_VALUE;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			max = Math.max(sum, max);
			if (sum < 0) {
				sum = 0;
			}
		}
		return max;
	}
	
	public void maxSubarrayPrint(int[] a) {
		int max = Integer.MIN_VALUE, max_s = 0, max_e = 0, sum=0, start = 0, end = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            if (sum > max) {
            	max = sum;
                end = i;
                max_s = start;
                max_e = end;
            }
            if (sum < 0) {
                start = i+1;
                sum = 0;
            }
        }
        for(int i=max_s;i<=max_e;i++)
            System.out.print(a[i]+" ");
        System.out.println();
	}
	
	/**
	 * Followup: Minimum Subarray 找subarray， sum最小
	 *  第一种方法，和max差不错，但是反过来
	 */
	public int minSubarray(int[] a) {
		int sum = 0, min = Integer.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			min = Math.min(min, sum);
			if (sum > 0) {
				sum = 0;
			}
		}
		return min;
	}
	
	/**
	 * Leetcode 209. Minimum Size Subarray Sum
	 *   找subarry，sum 大于s，而且长度最小
	 * 
	 * 两个指针，滑动窗口就可以了。注意 right - left + 1 才是长度。
	 * 时间复杂度 O(N)
	 */
	public int minSizeSubarray(int[] nums, int s) {
		int left = 0, right = 0;
        int sum = 0, min = Integer.MAX_VALUE;
       
        while ( right < nums.length) {
            sum += nums[right];
            while (sum >= s) {
                min = Math.min(right - left + 1, min);
                sum -= nums[left++];
            }
            right++;
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
	
	/**
	 * Find subarray with given sum | Set 1 (Nonnegative Numbers)
	 * Given an unsorted array of nonnegative integers, find a continous subarray which adds to a given number.
	 * Two pointers, O(n) time. O(1) space
	 */
	public void subarrySumToK(int[] a, int s) {
		int i= 0, j = 0, sum=0;
		boolean flag = false;
		for (j = 0; j < a.length; j++) {
		    sum += a[j];
		    while (i < j && sum > s) {
		        sum -= a[i++];
		    }
		    if (sum == s) {
		        System.out.println(i + " " + j);
		        flag = true;
			    break;
		    }
		}
		if (!flag) System.out.println("No subarray found");
	}
	
	
	/**
	 * 找size为k的subarry，和最大
	 */
	public void subarraySizeK(int[] nums, int k) {
		if (k == 0) return;
		int max = Integer.MIN_VALUE, sum = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i >= k) {
				max = Math.max(max, sum);
				sum -= nums[i-k];
			}
			sum += nums[i];
		}
		max = Math.max(max, sum);
		System.out.println(max);
	}
	
	/**
	 * Leetcode 325 Maximum Size Subarray Sum Equals k 
	 * Find subarray with given sum | Set 2 (Handles Negative Numbers)
	 *  
	 *  !!!当有负数的时候，滑动窗口就不太适用了！！！
	 *  
	 *  O(n) time, O(n) space,  实际上就是找一个i < j， sum[j] - sum[i] = k, 
	 * 		当k = 0 的时候是另外一题，看看有没有subarry sum up to 0
	 * 
	 * 对于每一个数 i，我们算从 0 到该数字的sum，同时看看map里面有没有 sum - k 存在，有的话说明从 map[sum-k] 到当前位置有一组答案
	 * 同时,也把当前位置的sum放到map里面 map[sum] = i
	 * 
	 * 这个解法同样适用于非负数的情况，不过空间复杂度更高
	 * 
	 * 需要注意的是，这里求 max size， map不用一直更新。
	 */
	public void maxSubarrySumToK(int[] a, int k) {
		int sum = 0;
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			if (map.containsKey(sum - k)) {
				max = Math.max(max, i - map.get(sum-k));
			}
			if (!map.containsKey(sum)) { //如果是要找所有的组合，那么就把if条件去掉就可以。
				map.put(sum, i);
			}
		}
		System.out.println(max);
	}
	/**
	 * 如果要算究竟多少个subarray sum = k, 
	 * Input:nums = [1,1,1], k = 2
	 * Output: 2
	 * 
	 * 这里map就存有多少个subarry能和为k
	 */
	public int subarraySum(int[] a, int k) {
        int sum = 0;
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, 1);
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			if (map.containsKey(sum - k)) {
				count += map.get(sum-k);
			}
			if (!map.containsKey(sum)) { //如果是要找所有的组合，那么就把if条件去掉就可以。
				map.put(sum, 1);
			} else {
			    map.put(sum, map.get(sum) + 1);
			}
		}
		return count;
    }
	
	/**
	 * Leetcode 152 Maximum Product Subarray
	 * 考虑到是做乘法，极有可能负负得正，所以这里存max 和 min，每次取值都要比较max * nums[i]， min * nums[i]之间大小
	 * 初始值应该是 nums[0], 和加法不一样。
	 * 同时还要考虑，数组中间会有0的情况出现。
	 */
	public int maxProduct(int[] nums) {
		int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        min[0] = max[0] = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
        	max[i] = min[i] = nums[i];
        	if (nums[i] > 0) {
        		max[i] = Math.max(max[i], max[i-1] * nums[i]);
        		min[i] = Math.min(min[i], min[i-1] * nums[i]);
        	} else {
        		max[i] = Math.max(max[i], min[i-1] * nums[i]);
        		min[i] = Math.min(min[i], max[i-1] * nums[i]);
        	}
        	
        	result = Math.max(result, max[i]);
        }
        return result;
    }
	
	public int maxProductConstantSpace(int[] nums) {
		int result = nums[0], max = nums[0], min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int tmp = max;
            max = Math.max(nums[i], Math.max(tmp*nums[i], min*nums[i]));
            min = Math.min(nums[i], Math.min(tmp*nums[i], min*nums[i]));
            result = Math.max(max, result);
        }
        return result;
	}
	
	public void maxProductOutputArr(int[] nums) {
		int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        min[0] = max[0] = nums[0];
        int result = nums[0];
        int maxStart = 0, start = 0, end = 0, minStart = 0;
        for (int i = 1; i < nums.length; i++) {
        	max[i] = min[i] = nums[i];
        	if (nums[i] > 0) {
        		if (max[i] > max[i-1] * nums[i]) {
        			maxStart = i;
        		} else {
        			max[i] = max[i-1] * nums[i];
        		}
        		if (min[i] < min[i-1] * nums[i]) {
        			minStart = i;
        		} else {
        			min[i] = min[i-1] * nums[i];
        		}
        	} else {
        		if (max[i] > min[i-1] * nums[i]) {
        			maxStart = i;
        		} else {
        			max[i] = min[i-1] * nums[i];
        			maxStart = minStart;
        		}
        		if (min[i] < max[i-1] * nums[i] ) {
        			minStart = i;
        		} else {
        			min[i] = max[i-1] * nums[i];
        			minStart = maxStart;
        		}
        	}
        	
        	if (max[i] > result) {
        		start = maxStart;
        		end = i;
        		result = max[i];
        	}
        }
        
        System.out.println(result + ", Max subarray start at: " + start + ", end at: " + end);
	}
	
	
	/**
	 * Leetcode 523	Continuous Subarray Sum 
	 * 已经知道了怎么找subarry，里面元素sum = k， 这里还可以做一个引申，让他们成为k的倍数
	 * 注意：0 也是倍数
	 * Given a list of non-negative numbers and a target integer k, write a function to check 
	 * if the array has a continuous subarray of size at least 2 that sums up to the multiple 
	 * of k, that is, sums up to n*k where n is also an integer.
	 * if any sub-array sum from index i’th to j’th is divisible by k then we can say a[0]+…a[i-1] (mod k) = a[0]+…+a[j] (mod k)
	 * 注意，这里要的是size at least 2，而且是 n*k
	 */
	public boolean checkSubarraySum(int[] nums, int k) {
		if (nums.length < 2) { return false; }
		
		HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			if (k != 0) sum = sum % k;
			if (map.containsKey(sum)) {
				if (i - map.get(sum) >= 2) return true;
			} else {
				map.put(sum, i);
			}
		}
		return false;
	}
	
	
	
	/**
	 * 前面大部分问题，都是找一个subarry，让sum最大或者最小，或者让跨越范围最大最小
	 * 还有一个变种，就是找两个subarray，让他们绝对值差距最大 ， 其实也就是相当于把
	 * array分成两份，左右互相比较
	 * 
	 * Maximum absolute difference between sum of two contiguous sub-arrays
	 * {2, -1, -2, 1, -4, 2, 8}   return 16  , {2,8} - {-1, -2, 1, -4}
	 */
	public int maxSubarrayDiff(int[] a) {
		int max = 0;
		
		System.out.println("Array is: " + Arrays.toString(a));
		//leftMax[i] = maximum subarray sum in s  -- find three disjoint subarrayubarray arr[0..i]
		int[] leftMax = maxSubarrayLeft(a);
		System.out.println("Max Subarray from Left: " + Arrays.toString(leftMax));
		//rightMax[i] = maximum subarray sum in subarray arr[i+1..n-1]
		int[] rightMax = maxSubarrayRight(a); 
		System.out.println("Max Subarray from Right: " + Arrays.toString(rightMax));
		
		/* The idea is to change the sign of each element in the array and run Kadane Algorithm 
		 * to find maximum sum subarray that lies in arr[0…i] and arr[i+1 … n-1]. 
		 * Now invert the sign of maximum subarray sum found. That will be our minimum subarray sum
		 */
		int[] invertArr = new int[a.length];
	    for (int i = 0; i < a.length; i++)
	        invertArr[i] = -a[i];
		//leftMin[i] = min subarray sum in subarray arr[0..i]
//		int[] leftMin = maxSubarrayLeft(invertArr);
//		for (int i = 0; i < a.length; i++)
//	        leftMin[i] = -leftMin[i];
		int[] leftMin = minSubarrayLeft(a);
		System.out.println("Min Subarray from Left: " + Arrays.toString(leftMin));

		//rightMin[i] = min subarray sum in subarray arr[i+1..n-1]
//		int[] rightMin = maxSubarrayRight(invertArr); 
//		for (int i = 0; i < a.length; i++)
//			rightMin[i] = -rightMin[i];
		int[] rightMin = minSubarrayRight(a);
		System.out.println("Min Subarray from Right: " + Arrays.toString(rightMin));
		
		
		for (int i = 0; i < a.length - 1; i++) {
			int leftMaxRightMin = Math.abs(leftMax[i] - rightMin[i+1]);
			int leftMinRightMax = Math.abs(rightMax[i+1] - leftMin[i]);
			int tmpMax = Math.max(leftMaxRightMin, leftMinRightMax);
			max = Math.max(tmpMax, max);
		}
		
		return max;
	}
	
	private int[] maxSubarrayLeft(int[] a) {
		int[] ret = new int[a.length];
		int sum = 0, max = Integer.MIN_VALUE;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			max = Math.max(sum, max);
			if (sum < 0) {
				sum = 0;
			}
			ret[i] = max;
		}
		return ret;
	}
	
	private int[] maxSubarrayRight(int[] a) {
		int[] ret = new int[a.length];
		int sum = 0, max = Integer.MIN_VALUE;
		for (int i = a.length-1; i >= 0; i--) {
			sum += a[i];
			max = Math.max(sum, max);
			if (sum < 0) {
				sum = 0;
			}
			ret[i] = max;
		}
		return ret;
	}
	
	private int[] minSubarrayLeft(int[] a) {
		int[] ret = new int[a.length];
		int sum = 0, min = Integer.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			min = Math.min(min, sum);
			sum = Math.min(0, sum);
			ret[i] = min;
		}
		return ret;
	}
	
	private int[] minSubarrayRight(int[] a) {
		int[] ret = new int[a.length];
		int sum = 0, min = Integer.MAX_VALUE;
		for (int i = a.length - 1; i >= 0; i--) {
			sum += a[i];
			min = Math.min(min, sum);
			sum = Math.min(0, sum);
			ret[i] = min;
		}
		return ret;
	}
	
	/**
	 * 找 sum最大，每个长度都是k 的三个subarray。 三个subarray不能有overlap。 
	 * 例如 1,2,1,2,6,7,5,1。k = 2。 这个里面找到的就应该是 [1,2], 1,[2,6],[7,5],1 同样返回和
	 * 
	 * 这个二维表的行是假设有nn个subarray, nn就是  行index+1
	 * 这个是状态转移方程， DP是当有nn个subarray的个数下，从0到i的最大值。sum(i-k, i)是元素从i-k到i的累加和
	 * 
	 * DP(nn, i) = Max(DP(nn-1, i-k)+sum(i-k, i), 
	 * 				  DP(nn, i-1))
	 * 
	 * 后面的解法更好
	 */
	public void subarraybyk(int[] a, int k, int n) {
		int[] lastMaxK = new int[a.length];
		int[] maxK = new int[a.length];
		
		for (int i = 0; i < n; i++) {
			int currSum = 0;
			for (int j = i*k; j < a.length; j++) {
				currSum += a[j];
				if (j < (i+1)*k-1) continue;
				if (j >= (i+1)*k) currSum -= a[j-k];
				if (j == (i+1)*k-1) 
					maxK[j] = currSum;
				else {
					maxK[j] = Math.max(lastMaxK[j-k] + currSum, maxK[j-1]);
				}
			}
			int[] tmp = lastMaxK;
			lastMaxK = maxK;
			maxK = tmp;
		}
		
		System.out.println(lastMaxK[maxK.length - 1]);
	}
	
	/**
	 * 还是找 sum最大，每个长度都是k 的三个subarray。 三个subarray不能有overlap。 
	 * 第二题确实有o(n)解，建3个array，从0循环到n，一个是以i为结尾的size为k的subarray的值，
	 * 一个left是i左边最大的size为k的值，一个right是i右边size为k的最大值，然后完了你再循环一遍找出最大值就可以了
	 */
	public void subarraybyk2(int[] a, int k) {
		int n = a.length;
		int[] mid = new int[n], left = new int[n], right = new int[n];
		
		//get sum of size k subarry that ends at i
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += a[i];
			if (i >= k) sum -= a[i-k];
			if (i - k + 1 >= 0) mid[i] = sum;
		}
		System.out.println("Mid:   " + Arrays.toString(mid));
		
		//get max subarray sum befor i - k
		//start from k because we need to keep size k subarry on the left
		int max = 0;
		for (int i = k; i < n-k+1; i++) {
			max = Math.max(max, mid[i-k]);
			left[i] = max;
		}
		System.out.println("Left:  " + Arrays.toString(left));
		
		//get max k size subarry sum between i and arr.length
		// start from n-k-1, because we always need to keep size k subarry on the right
		max = 0;
		for (int i = n-k-1; i >= k; i--) {
			max = Math.max(max, mid[i+k]);
			right[i] = max;
		}
		System.out.println("Right: " + Arrays.toString(right));
		
		max = 0;
		int index = -1;
		for (int i = 0; i < n; i++) {
			if (left[i] + right[i] + mid[i] > max) {
				max = left[i] + right[i] + mid[i];
				index = i;
			}
		}
		System.out.println(max + " at index: " + index);
	}
	
	/**
	 * 410. Split Array Largest Sum
	 * 跟上面的题目差不多, 但是这里是split，不是找n个长度为k的，这样的话就可以二分解决。
	 * 找到一个 mid，数组恰好不可以分为 m个子数组，每个子数组sum < k;
	 */
	public int splitArray(int[] nums, int m) {
		if (nums == null) {
        	return 0;
        }
       
        long sum = 0, max = 0;
        for (int n : nums) {
        	max = Math.max(n, max);
        	sum += n;
        }
        
        int ans = 0;
        long start = max, end = sum;
        while (start <= end) {
        	long mid = start + (end - start)/2;
        	if (valid(mid, nums, m)) {
        		start = mid + 1;
        	} else {
        		ans = (int)mid;
        		end = mid-1;
        	}
        }
        return ans;
	}
	
	private boolean valid(long mid, int[] nums, int m) {
    	//找出 和最大 <= mid 的数组的数量
    	long sum = 0;
    	int count = 1;
    	
    	for (int n : nums) {
    		if (n > mid) return false;
    		if (sum + n > mid) {
    			count++;
    			sum = n;
    		} else {
    			sum += n;
    		}
    	}
    	
    	return count > m;
    }
	
	/**
	 * For a given array, find the subarray (containing at least k number) which has the largest sum. 
		Example: 
		// [-4, -2, 1, -3], k = 2, return -1, and the subarray is [-2, 1] 
		// [1, 1, 1, 1, 1, 1], k = 3, return 6, and the subarray is [1, 1, 1, 1, 1, 1] 
		try to do it in O(n) time 
		Followup, if input is stream, how to solve it 
		public int maxSubArray(int[] nums, int k) {}
	 */
	public int maxSubArraySizeK(int[] nums, int k) {
		
		int[] maxSums = new int[nums.length];
		int sum = 0, max = Integer.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			max = Math.max(max, sum);
			maxSums[i] = max;
			if (sum < 0) {
				sum = 0;
			}
		}
		
		sum = 0;
		for (int i = 0; i < k; i++) {
			sum += nums[i];
		}
		
		max = sum;
		for (int i = k; i < nums.length; i++) {
			sum = sum + nums[i] - nums[i-k];
			
			int currMax = Math.max(sum + maxSums[i-k], sum);
			max = Math.max(max, currMax);
		}
		
		return max;
	}
	/**
	 * For a given array, find the subarray (containing at MOST k number) which has the largest sum. 
	 */
	public int maxSubArraySizeLessK(int[] nums, int k) {
		int sum = 0, max = Integer.MIN_VALUE, i = 0, j = 0;
		while (j < nums.length) {
			sum += nums[j++];
			if (j - i > k) {
				sum -= nums[i++];
			}
			max = Math.max(max, sum);
			while (i < j &&nums[i] < 0 ) {
				sum -= nums[i++];
			}
		}
		return max;
	}
	
	
	/**
	 * Extend to 2D , min size subarray, find if a rectangle, sum == k
	 * 
	 * right对列进行循环，，对于每一列，用left = right -> 0，算row sum, 如果当前列有rows sum之差为target
	 * 那么这两个row之间就是要找的区间。如果当前列都算完了，还没有找到。
	 * 那么清空 set，然后left--，找前面一列。 这里 row Sum不能清空，因为是累计的，但是set要清空。画图就明白了。
	 * 
	 */
	public void find(int[][] matrix, int target) {
		for (int right = 0; right < matrix[0].length; right++) {
			int[] rowSum = new int[matrix.length];
			for (int left = right; left >= 0; left--) {
				Set<Integer> sum = new HashSet<>();
				int currSum = 0;
				sum.add(0);
				for (int row = 0; row < matrix.length; row++) {
					rowSum[row] += matrix[row][left];
					currSum += rowSum[row];
					if (sum.contains(currSum - target)) {
						System.out.println("Found");
						return;
					}
					sum.add(currSum);
				}
			}
		}
	}
	
	
	/**
	 * Given an array of integers and a number k, find k non-overlapping subarrays which have the largest sum.
	 * http://www.lintcode.com/en/problem/maximum-subarray-iii/
	 * 分成k个subarrray，要和最大，每个subarray长度不固定
	 */
	public int maxSubArray(int[] nums, int k) {
        // write your code here
        if(nums.length < k){
            return 0;
        }

        int len = nums.length;
        //local[i][k]表示前i个元素取k个子数组并且必须包含第i个元素的最大和。
        /* 有两种情况，第一种是第i个元素自己组成一个子数组，则要在前i－1个元素中找k－1个子数组，
         * 第二种情况是第i个元素属于前一个元素的子数组，因此要在i－1个元素中找k个子数组（并且必须包含第i－1个元素，
         * 这样第i个元素才能合并到最后一个子数组中），取两种情况里面大的那个。
         */
        int[][] localMax = new int[len + 1][k + 1];
        
        //global[i][k]表示前i个元素取k个子数组不一定包含第i个元素的最大和。
        /* 有两种情况，第一种是不包含第i个元素，所以要在前i－1个元素中找k个子数组，
         * 第二种情况为包含第i个元素，在i个元素中找k个子数组且必须包含第i个元素，取两种情况里面大的那个。
         */
        int[][] globalMax = new int[len + 1][k + 1];

        for(int j = 1; j <= k; j++){
        //前j－1个元素不可能找到不重叠的j个子数组，因此初始化为最小值，以防后面被取到
            localMax[j - 1][j] = Integer.MIN_VALUE;
            for(int i = j; i <= len; i++){
                localMax[i][j] = Math.max(globalMax[i - 1][j - 1], localMax[i - 1][j]) + nums[i - 1];
                if(i == j){
                    globalMax[i][j] = localMax[i][j];
                }else{
                    globalMax[i][j] = Math.max(globalMax[i - 1][j], localMax[i][j]);
                }
            }
        }

        return globalMax[len][k];
    }
	/** 另外一个解法 **/
	public int maxSubArray(ArrayList<Integer> nums, int k) {  
	    // write your code  
	    int n = nums.size();  
	    int[][] d = new int[n+1][k+1];  
	    for (int j = 1; j <= k; j++) {  
	        for (int i = j; i <= n; i++) {  
	            d[i][j] = Integer.MIN_VALUE;  
	            int max = Integer.MIN_VALUE;  
	            int localMax = 0;  
	            for (int m = i-1; m >= j-1; m--) {  
	                localMax = Math.max(nums.get(m), nums.get(m)+localMax);  
	                max = Math.max(localMax, max);  
	                d[i][j] = Math.max(d[i][j], d[m][j-1] + max);  
	            }  
	        }  
	    }  
	    return d[n][k];  
	}  

	
	/**
	 * 总结，这题有好多种情况，但是解法无外乎两种，一种是滑动窗口，一种是通过hashmap记录之前的sum和位置
	 * 滑动窗口一般适合正数，hashMap适用于负数，以及求最大长度的情况。看到题目的时候要注意分析，判断哪种
	 * 方法可以使用，不能一下写太快。
	 */
	
	public static void main(String[] args) {
		int[] arr1 = {1, 4, 20, 3, 10, 5};
		int[] arr2 = {1, 4, 0, 0, 3, 10, 5};
		int[] arr3 = {1, 4};
		SubarrayProblems clz = new SubarrayProblems();
		
		int[] a = {-3, 4, -10, -1, -6, 8, 8, -8, -6, -5, -5, -9 };
		int[] b = {1, 2, 5, -7, 2, 3};
		clz.maxSubarrayPrint(b);
		
		clz.subarraySizeK(new int[]{3, 4,5,1,2}, 3);
//		clz.subarrySumToK(arr1, 33);
//		clz.subarrySumToK(arr2, 7);
//		clz.subarrySumToK(arr3, 0);
//		
//		System.out.println("=============");
//		int[] arr4 = {10, 2, -2, -20, 10};
//		clz.subarrySumToKNeg(arr1, 33);
//		clz.subarrySumToKNeg(arr2, 7);
//		clz.subarrySumToKNeg(arr3, 0);
//		clz.subarrySumToKNeg(arr4, -10);
//		int[] arr5 = {-10, 0, 2, -2, -20, 10};
//		clz.subarrySumToKNeg(arr5, 20);
	
		
		int[] arr6 = {2, -1, -2, 1, -4, 2, 8};
//		System.out.println(clz.minSubarray(arr6));
		System.out.println(clz.maxSubarrayDiff(arr6));
		
//		int[] arr7 = {1,3,4};
//		System.out.println(clz.minSubarray(arr7));
		
//		
//		int[] arr10 = {1,1,1,1,1,1};
//		System.out.println(clz.maxSubArraySizeK(arr10, 2));
//		
//		
//		int[] ar0 = {-2,-1,-3,-4,-1};
//		int[] ar1 = {-3,10};
//		int[] ar2 = {3,-10};
//		int[] ar3 = {-2,5,60,-10,-23};
//		int[] ar4 = {6,2,4,-1,5,9,-2,10};
//		
//		System.out.println(clz.maxSubArraySizeK(ar0, 2));
//		System.out.println(clz.maxSubArraySizeLessK(ar1, 4));
//		System.out.println(clz.maxSubArraySizeLessK(ar2, 4));
//		System.out.println(clz.maxSubArraySizeLessK(ar3, 4));
//		System.out.println(clz.maxSubArraySizeLessK(ar4, 4));
//		
//		
		int[] ar5 = {2,3,-2,4};
		clz.maxProductOutputArr(ar5);
		
		int[] ar6 = {2,3,-2,-5, 4};
		clz.maxProductOutputArr(ar6);
		
		
		
		int[] arr = {7,2,5,5,1,1,4,4,3,3,0,0};
		arr = new int[]{1,2,1,2,6,7,5,1};
		clz.subarraybyk(arr, 2, 3);
		clz.subarraybyk2(arr, 2);
		
		
		
	}
	
	
	
}
