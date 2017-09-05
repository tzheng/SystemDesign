import java.util.Arrays;

public class ProductButSelf {
	/**
	 * 238	 Product of Array Except Self   
	 */
	public int[] productExceptSelf(int[] nums) {
		int[] res = new int[nums.length];
		int product = 1;
		
		for (int i = 0; i < nums.length; i++) {
			res[i] = product;
			product *= nums[i];
		}
		
		product = nums[nums.length-1];
		for (int i = nums.length-2; i >= 0; i--) {
			res[i] = res[i] * product;
			product *= nums[i];
		}
		
		return res;
	}
	
	
	public int[] productExceptSelfDP(int[] nums) {
		int[] res = new int[nums.length];
		/**
		 * i = 1 to n  left[i] = left[i-1] * num[i-1]
		 * 
		 *	     1  2  3  4 
		 * left  1  1  2  6
		 * right 24 12 4  1
		 * 
		 * i = n-2 to 0,  right[i] = right[i+1] * num[i+1]
		 */
		return res;
	}
	
	public static void main(String[] args) {
		ProductButSelf clz = new ProductButSelf();
		int[] nums = {1,2,3,4};
		clz.productExceptSelfDP(nums);
	}
	
	
}
