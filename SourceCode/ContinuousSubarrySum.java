import java.util.HashMap;

public class ContinuousSubarrySum {
	public boolean checkSubarraySum(int[] nums, int k) {
        if (nums.length < 2) {
            return false;
        }
        if (k < 0) {
            k = -k;
        }

        int[] sum = new int[nums.length + 1];
        
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i-1] + nums[i-1];
            for (int j = 0; j < i; j++) {
                if (i - j >= 2 && ( (k == 0 && sum[i] == sum[j]) || 
                		(k != 0 && (sum[i] - sum[j])%k == 0)   )) {
                    return true;
                }
            }
        }
        
        return false;
    }
	 
	public static void main(String[] args) {
		ContinuousSubarrySum clz = new ContinuousSubarrySum();
		int[] num = {0, 0, 4, 6, 7};
		System.out.println(clz.checkSubarraySum(num, 0));
	}
}
