import java.util.Arrays;

public class MoveZeros {
	
	/**
	 * 	283	Move Zeroes
	 *  保持原来数组顺序
	 *  
	 *  num of operations: nums.length, if there are lots of non-zeros in array, use this
	 */
	public void moveZeroes(int[] nums) {
        // Write your code here
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[index++] = nums[i];
            } 
        }
        
        for (int i = index; i < nums.length; i++) 
            nums[i] = 0;
    }

	
	/**
	 * 如果有很多个0
	 *  num of operations: 2 * (num of non-zero), 
	 * if there are lots of zeros in array, use this  
	 * 
	 */
	public void moveZeroesSwap(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[j];
                nums[j++] = nums[i];
                nums[i] = temp;
            }
        }
        
    }
	
	/**
	 * 不要求保持顺序, 要的是非0的个数 或者是不管后面的数字
	 * return number of non_zeros
	 */
	public int moveZeroesUnorder(int[] nums) {
        // Write your code here
       int left = 0, right = nums.length - 1;
       while (left < right) {
    	   while (left < right && nums[left] != 0) left++;
    	   while (left < right && nums[right] == 0) right--;
    	   if (left < right) {
    		   nums[left++] = nums[right--];
    	   }
       }
       //!!!这里要注意，left和right 相遇的时候，有可能left是0
       //也有可能已经排好序了   1，1，1，0，0，0， 这时只要返回left
       System.out.println("after: " + Arrays.toString(nums));
       if (nums[left] == 0 || left > right) { 
    	   return left;
       }
       //否则返回left + 1, e.g. {1,0,2,0,5,3,4}
       System.out.println("----");
       return left+1;
    }
	

	
	/**
	 * moveZero to front, moveOne to back, 
	 * maintain the order of other non-zero and non-one element
	 */
	public void moveZeroAndOne(int[] nums) {
		//1. move non-one to front
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != 1) {
				nums[index++] = nums[i];
			}
		}
		for (int i = index; i < nums.length; i++) {
			nums[i] = 1;
		}
		//2. move non zero to the middle
		index--;
		for (int i = index; i >= 0; i--) {
			if (nums[i] != 0) {
				nums[index--] = nums[i];
			}
		}
		while (index >= 0) {
			nums[index--] = 0;
		}
	}
	
	public int moveZeroesLeft(int[] nums) {
        // Write your code here
       int left = 0, right = nums.length - 1;
       while (left < right) {
    	   while (left < right && nums[left] == 0) left++;
    	   while (left < right && nums[right] != 0) right--;
    	   if (left < right) {
    		   int tmp = nums[left];
    		   nums[left] = nums[right];
    		   nums[right] = tmp;
    	   }
       }
       System.out.println(Arrays.toString(nums));
       return nums.length - left;
    }
	
	
	public static void main(String[] args) {
		MoveZeros clz = new MoveZeros();
		int[] arr = {1,0,2,0,5,3,4};
//		System.out.println(clz.moveZeroesUnorder(arr));
		
		System.out.println(clz.moveZeroesLeft(arr));
		
		
		arr = new int[]{1,1,1,0,0};
		System.out.println(clz.moveZeroesLeft(arr));
		
		arr = new int[]{0,0,0,1,1,1};
		System.out.println(clz.moveZeroesLeft(arr));
	} 
	
	
}
