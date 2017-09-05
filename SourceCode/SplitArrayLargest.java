//Input:
//nums = [7,2,5,10,8]
//m = 2
//
//Output:
//18
//
//Explanation:
//There are four ways to split nums into two subarrays.
//The best way is to split it into [7,2,5] and [10,8],
//where the largest sum among the two subarrays is only 18.

public class SplitArrayLargest {
    public int splitArray(int[] nums, int m) {
        if (nums == null) {
        	return 0;
        }
        
       
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int n : nums) {
        	max = Math.max(n, max);
        	sum += n;
        }
        
        int ans = sum;
        int start = max, end = sum;
        while (start <= end) {
        	int mid = start + (end - start)/2;
        	if (valid(mid, nums, m)) {
        		//if valid, it means current mid is too small, more than m subarray sum up to mid
        		
        		start = mid + 1;
        		
        	} else {
        		ans = mid;
        		//if invalid, means current number is too large, can't split to m arrays
        		end = mid - 1;
        		
        		
        	}
        }
        
        return ans;
    }
    
    private boolean valid(int mid, int[] nums, int m) {
    	//找出 和最大 <= mid 的数组的数量
    	int sum = 0;
    	int count = 1;
    	
    	for (int n : nums) {
    		if (n > mid) {
    			return false;
    		}
    		
    		if (sum + n > mid) {
    			count++;
    			sum = n;
    		} else {
    			sum += n;
    		}
    	}
    	
    	return count > m;
    }
    
    public static void main(String[] args) {
    	SplitArrayLargest clz = new SplitArrayLargest();
    	System.out.println(clz.splitArray(new int[]{7,2,5,10,8}, 2));
    }
}
