import java.util.Arrays;

public class MatchstickToSquare {
	
	//DFS会超时
	public boolean makesquare(int[] nums) {
		int sum = 0;
        for (int num : nums) sum += num;
        if (sum % 4 != 0) return false;
        int len = sum/4;
        Arrays.sort(nums);
//        boolean[] used = new boolean[nums.length];
//        return dfs(len, 0, nums, used, 4, 0);
		
		boolean[] f = new boolean[sum+1];
		f[0] = true;
		for (int num : nums) {
			for (int i = sum; i >= num; i--) {
				f[i] = f[i] || f[i-num];
			}
		}
		return f[len] && f[2*len] && f[3*len] && f[sum];
    }
    
    private boolean dfs(int len, int currLen, int[] nums, boolean[] used, int side, int count) {
    	 if (count == nums.length) {
             if (side == 0 && currLen == 0) return true;
             return false;
         }
         boolean exist = false;
         for (int i = 0; i < nums.length; i++) {
             if (used[i]) continue;
             if (currLen + nums[i] > len) break;
             
             used[i] = true;
             
             if (currLen + nums[i] == len) {
                 exist = dfs(len, 0, nums, used, side-1, count+1); 
             } else {
                 exist = dfs(len, currLen + nums[i], nums, used, side, count+1);
             }
             if (exist) return true;
             used[i] = false;
         }
         return exist;
    }
    
    public static void main(String[] args) {
    	MatchstickToSquare clz = new MatchstickToSquare();
    	System.out.println(clz.makesquare(new int[]{5,5,5,5, 4,4,4,4,3,3,3,3}));
    }
}
