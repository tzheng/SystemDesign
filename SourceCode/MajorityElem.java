import java.util.ArrayList;
import java.util.List;

public class MajorityElem {
	
	/**
	 * 最简单的版本，有一个元素超过了 n/2，找到他
	 * LC. 169. Majority Element
	 * 
	 * 很简单，遇到相同的数就累加count，遇到不同的就减1，如果count = 0，
	 * 就更新一下major元素为当前元素，因为某个元素超过了 n/2，那么到最后
	 * 肯定他的 count会 > 0， 因为和他相同的元素比和他不同的元素多。
	 */
	public int majorityElement2(int[] nums) {
		if (nums == null || nums.length == 0) {
            return -1;
        }
		
		int major = -1, count = 1;
		for (int num : nums) {
			if (count == 0) {
				major = num;
				count = 1;
			} else if (num == major) {
				count++;
			} else {
				count--;
			}
		}
		return major;
	}
	
	/**
	 * LC 229.  Majority Element II   
	 * 题目升级了，现在要找到所有出现次数大于 N/3 的元素。
	 * 这种元素最多可以有两个。
	 * 
	 * 我们看到， 找N/2的解法就是用一个count来记录出现次数，
	 * 现在拓展到两个了，那么我们就可以用两个count来记录。
	 * 遍历数组，对于当前数字，如果他等于n1, 或者 n2, 就
	 * 累加c1，c2， 如果c1，c2 等于0， 就最更新为当前数字
	 * 如果数既不是n1也不是n2，那么c1，c2都要减去1. 当然
	 * 要注意，找到n1，n2之后还要再遍历一遍数组，确定他们真
	 * 的出现次数大于N/3
	 * 
	 */
	public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        int n1 = 0, c1 = 0, n2 = 0, c2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (n1 == nums[i] ) {
                c1++;
            } else if (n2 == nums[i]) {
                c2++;
            } else if (c1 == 0) {
            	c1++;
            	n1 = nums[i];
            } else if (c2 == 0) {
            	c2++;
            	n2 = nums[i];
            } else {
                c1--;
                c2--;
            }
        }
        
        c1 = 0; c2 = 0;
        for (int n : nums) {
            if (n == n1) c1++;
            else if (n == n2) c2++;
        }
        
        int n = nums.length;
        if (c1 > n/3) result.add(n1);
        if (c2 > n/3) result.add(n2);
        return result;
    }
	
	/**
	 * 题目再升级， 找出所有出现次数大于n/k的所有数字，并返回；
	 *
	 * 解法和上面的一样，需要开一个 k-1 大小的数组，存数字和他们的count
	 * 时间复杂度O(NK), 空间 O(K)
	 * http://www.geeksforgeeks.org/given-an-array-of-of-size-n-finds-all-the-elements-that-appear-more-than-nk-times/
	 *
	 * @param args
	 */
	public void majorityElementK(int[] nums, int k) {
		if (k < 2) return;

		ElemCount[] tmp = new ElemCount[k-1];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = new ElemCount(); tmp[i].count = 0; tmp[i].elem = null;
		}
		
		for (int i = 0; i < nums.length; i++) {
			int j;
			for (j = 0; j < k-1; j++) {
				if (tmp[j].elem != null && tmp[j].elem == nums[i]) {
					tmp[j].count += 1;
					break;
				}
			}
			//didn't find nums[i] in the array
			if (j == k-1) {
				int l;
				//if tmp still have available space
				for (l = 0; l < k-1; l++) {
					if (tmp[l].count == 0) {
						tmp[l].elem = nums[i];
						tmp[l].count = 1;
						break;
					}
				}
				
				//if tmp don't have availabel space,  
	            //   decrease count of every element by 1
				if (l == k-1) {
					for (l = 0; l < k-1; l++) {
						tmp[l].count -= 1;
					}
				}
			}
		}
		
		for (int i = 0; i < k-1; i++) {
			int actualCount = 0;
			if (tmp[i].elem == null) continue;
			for (int j = 0; j < nums.length; j++) {
				if (nums[j] == tmp[i].elem) {
					actualCount++;
				}
			}
			if (actualCount > nums.length/k) {
				System.out.println("Number: " + tmp[i].elem + " occurs more then " + nums.length/k + " times.");
			}
			
		}	
	}
	
	
	class ElemCount {
		Integer elem;
		int count;
	}
	
	/**
	 * 如果是sorted array呢，还是找出现大于 n/4
	 * 
	 * 比如  1,1,2,2,2,3,3,3
	 * 
	 * 把整个区域分成[0, n/4 - 1]; [n/4, n/2 - 1]; [n/2, 3n/4 - 1]; [3n/4, n-1] 
	 * 分成四块  [1,1] , [2,2], [2,3], [3,3]
	 *		     B1      B2     B3     B4
	 *
	 * 我们看到如果一个数字出现超过N/4次，他必然会在自己相邻的N/4块中再出现一次。因为数组是排序的，而且
	 * 数字出现次数大于N/4，所以一个长度为N/4的区块装不下这个数字。比如上面的2， 在B2出现之后，B3还出现
	 * 3在B3出现了，在B4也出现了。
	 * 
	 * 所以我们可以在在1/4, 1/2, 3/4处做binary search找左右边界，时间复杂度是O(k * log(n/k)) 空间是O(1)
	 */
	public void majorityElementSorted(int[] nums, int k) {
		int n = nums.length;
		int index = n/k;
		while (index < n-1) {
//			System.out.println("Search in Index: " + index);
			int left = binarySearchLower(index- n/k, index-1, nums, nums[index]);
			int right = binarySearchUpper(index, index + n/k-1, nums, nums[index]);
			if (right - left + 1 > n/k) {
				System.out.println("Number: " + nums[index] + " occurs more then " + n/k + " times.");
			}
			index += n/k;
		}
			
	}
	private int binarySearchLower(int start, int end, int[] nums, int target) {
		int res = end;
		while (start <= end) {
			int mid = start + (end - start)/2;
			if (nums[mid] == target) {
				res = mid;
				end = mid-1;
			} else if (nums[mid] <target) {
				start = mid+1;
			} else {
				end = mid-1;
			}
		}
		return res;
	}
	
	private int binarySearchUpper(int start, int end, int[] nums, int target) {
		int res = start;
		while (start <= end) {
			int mid = start + (end - start)/2;
			if (nums[mid] == target) {
				res = mid;
				start = mid+1;
			} else if (nums[mid] < target) {
				start = mid+1;
			} else {
				end = mid-1;
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		MajorityElem clz = new MajorityElem();
//		int[] nums = {1,2,2,3,2,1,1,3};
//		clz.majorityElement(nums);
		
		int[] nums1 = {2,2,2,2,3,3,3,3,6,7};
		clz.majorityElementSorted(nums1, 3);
		System.out.println();
		
		clz.majorityElementK(nums1, 3);
		System.out.println();
		
		int[] nums2 = {1,1,2,3,3,3,3,5,6,7};
		clz.majorityElementSorted(nums2, 4);
		System.out.println();
		clz.majorityElementK(nums2, 4);
	}
}
