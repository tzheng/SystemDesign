import java.util.HashMap;
import java.util.Map;

public class LongestConsecutive {
	/**
	 * 
	 * 128. Longest Consecutive Sequence
	 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
		For example,
		Given [100, 4, 200, 1, 3, 2],
		The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
		Your algorithm should run in O(n) complexity.
	 */
	public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int max = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (map.containsKey(n)) 
                continue;
            
            int left = (map.containsKey(n - 1)) ? map.get(n - 1) : 0;
            int right = (map.containsKey(n + 1)) ? map.get(n + 1) : 0;
            int sum = left + right + 1;
            
            map.put(n, sum);
            max = Math.max(sum, max);
            
            map.put(n - left, sum);
            map.put(n + right, sum);
        }
        return max;
    }
	
	public static void main(String[] args) {
		int[] nums = {100,4,200,1,3,2};
		System.out.println(longestConsecutive(nums));
	}
}
