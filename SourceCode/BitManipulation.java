
public class BitManipulation {
	
	
	public int reverseBits(int n) {
		int res = 0;
		while (n > 0) {
			int curr = n & 1;
			n = n >>> 1;
			res = res << 1;
			res = res | curr;
		}
		return res;
    }
	
	/**Count 1 in Binary
	 * x & (x - 1) 的含义为去掉二进制数中1的最后一位，无论 x 是正数还是负数都成立。
	 */
	public int countOnes(int num) {
		int count = 0;
		while (num != 0) {
			num = num & (num - 1);
			count++;
		}
		return count;
	}
	
	/**
	 * 461. Hamming Distance
	 */
	public int hammingDistance(int x, int y) {
        int res = x^y;
        int count = 0;
        while (res != 0) {
            count += res&1;
            res = res >> 1;
        }
        return count;
    }
	
	/**
	 * 477. Total Hamming Distance
	 * For each bit position 1-32 in a 32-bit integer, we count the number of integers in the array which have that bit set.
	 *  Then, if there are n integers in the array and k of them have a particular bit set and (n-k) do not, then that bit
	 *   contributes k*(n-k) hamming distance to the total.
	 */
	public int totalHammingDistance(int[] nums) {
		int total = 0;
        for (int i = 0; i < 32; i++) {
        	int hasOne = 0;
        	for (int j = 0; j < nums.length; j++) {
        		hasOne += (nums[j] >> i) & 1;
        	}
        	total += hasOne * (nums.length - hasOne);
        }
        return total;
    }
	
	/**
	 * Count the number of ones in the binary representation of the given number
	 */
	int count_one(int n) {
		int count = 0;
	    while(n > 0) {
	        n = n&(n-1);
	        count++;
	    }
	    return count;
	}
	
	/**
	 * 
	 * ^ tricks

		Use ^ to remove even exactly same numbers and save the odd, or save the distinct bits and remove the same.
		
		SUM OF TWO INTEGERS
		
		Use ^ and & to add two integers
		I find this a bit tricky to explain, but here's an attempt; think bit by bit addition, there are only 4 cases;

0+0=0 
0+1=1 
1+0=1 
1+1=0 (and generates carry)
The two lines handle different cases

sum = a ^ b
Handles case 0+1 and 1+0, sum will contain the simple case, all bit positions that add up to 1.

carry = (a & b) << 1
The (a & b) part finds all bit positions with the case 1+1. Since the addition results in 0, it's the 
carry that's important, and it's shifted to the next position to the left (<<1). The carry needs to be
 added to that position, so the algorithm runs again.
	 	**/		
		public int getSum(int a, int b) {
			if (a == 0) return b;
			if (b == 0) return a;
		
			while (b != 0) {
				int carry = a & b;
				a = a ^ b;
				b = carry << 1;
			}
			
			return a;
		}
	
	
	public static void main(String[] args) {
		BitManipulation clz = new BitManipulation();
		clz.reverseBits(5);
		
		clz.getSum(1, 3);
	}
}
