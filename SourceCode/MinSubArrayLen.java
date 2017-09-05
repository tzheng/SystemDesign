
public class MinSubArrayLen {
	public int minSubArrayLen(int s, int[] nums) {
        //since they are all position,
        int min = 0;
        
        int[] pathSum = new int[nums.length + 1];
        for (int i = 1; i < nums.length+1; i++) {
            pathSum[i] = pathSum[i-1] + nums[i-1];
        }
        
        int i = 0, j = pathSum.length-1;
        while (i < j) {
            if (pathSum[j] - pathSum[i] < s) {
                break;
            } 
            if (min == 0) 
            	min = j-i;
            else
            	min = Math.min(min, j-i);
            i++;
        }

        
        return min;
    }
	
	public static void main(String[] args) {
		int[] arr = {1,2,3,4,5};
		MinSubArrayLen clz = new MinSubArrayLen();
		System.out.println(clz.minSubArrayLen(15, arr));
	}
}
