
public class TwoSumUniquePairs {

	public static void main(String[] args) {
	       // Write your code here
        int[] nums = {1,1,2,45,46,46};
        int target = 47;
        
        int count = 0;
        int start = 0, end = nums.length -1;
        while (start < end) {
            if (nums[start] + nums[end] == target) {
                count++;
                while (start == 0 || nums[start] == nums[start-1]) {
                    start++;
                }
                while (end == nums.length -1 || nums[end] == nums[end+1]) {
                    end--;
                }
                if (start >= end) {
                    break;
                }
            } else if (nums[start] + nums[end] > target) {
                end--;
            } else {
                start++;
            }
        }
        
        System.out.println(count);
	}
}
