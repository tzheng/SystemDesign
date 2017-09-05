
public class SingleNumber {
    public int singleNumberII(int[] nums) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                if (((nums[j] >> i) & 1) == 1) {
                    sum++;
                    sum %= 3;
                }
            }
            
            if (sum != 0) {
                //in this digit, there are 1 exist 
                ans |= sum << i;
            }
        }
        return ans;
    }
    
    public int[] singleNumber3(int[] nums) {
        // Pass 1 : 
        // Get the XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        // Get its last set bit
        diff &= -diff;
        
        // Pass 2 :
        int[] rets = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums)
        {
            if ((num & diff) == 0) // the bit is not set
            {
                rets[0] ^= num;
            }
            else // the bit is set
            {
                rets[1] ^= num;
            }
        }
        return rets;
    }
    
    
    public static void main(String[] args) {
    	SingleNumber clz = new SingleNumber();
    	clz.singleNumberII(new int[]{1,1,1,2,2,2,3, 3});
    	clz.singleNumber3(new int[]{1,1,2,2,3,5});
    }
}
