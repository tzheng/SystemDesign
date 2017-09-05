import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NSums {
	
	/**
	 * 1.Two Sum
	 * 这是Leetcode的第一题，也是经典题目
	 * Given an array of integers, return INDICES of the two numbers such that they add up to a specific target.
	 * You may assume that each input would have exactly ONE solution, and you may not use the same element twice.
	 */
	public int[] twoSumHash(int[] nums, int target) {
        //solution
		int[] ret = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff) && map.get(diff) != i) {
                ret[1] = i;
                ret[0] = map.get(diff);
            }
            map.put(nums[i], i);
        }
        return ret;
    }
	
	/**
	 * 167. Two Sum II - Input array is sorted
	 * where index1 must be less than index2. 
	 * Please note that your returned answers (both index1 and index2) are NOT zero-based
	 */
	public int[] twoSum(int[] numbers, int target) {
		int[] ret = new int[2];
		int start = 0, end = numbers.length - 1;
		while (start < end) {
			int sum = numbers[start] + numbers[end];
			if (target == sum) {
				ret[0] = start + 1;
				ret[1] = end + 1;
				return ret;
			} else if (target > sum) {
				start++;
			} else {
				end--;
			}
		}
		return ret;
	}
	
	/**
	 * 170. Two Sum III - Data structure design
	 * 注意可以有重复，比如  2 + 2 = 4, 所以加数字的时候可以统计count
	 * key point:   do we have a lot of add, or a lot of find
	 */
	class TwoSum {
	    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	    
	    /**  this is O(1) add **/
	    public void add(int number) {
	        int count = 1;
	        if (map.containsKey(number)) {
	            count = map.get(number) + 1;
	        }  
	        map.put(number, count);
	    }
	    
	    /** this is O(n) find */
	    public boolean find(int value) {
	        for (int num : map.keySet()) {
	            int num2 = value - num;
	            if ((num == num2 && map.get(num) > 1) || (num != num2 && map.containsKey(num2))) {
	            	return true;
	            } 
	        }
	        return false;
	    }
	    
	    
	    /**  this is O(N) add **/
	    Set<Integer> sums = new HashSet<>();
	    Set<Integer> nums = new HashSet<>();
	    public void addON(int number) {
	       for (int num : nums) {
	    	   sums.add(num + number);
	       }
	       nums.add(number);
	    }
	    
	    /** this is O(1) find */
	    public boolean findO1(int value) {
	        return sums.contains(value);
	    }
	}
	
	/**
	 * 3 Sum - Data Structure Design
	 * Followup是func要叫很多次，给hint，先算出所有两数相加的值，转成2sum, 
	 * can resue number
	 * 	
	 * 	Construct O(n^2),  get
	 */
	class ThreeSumMultiple {
		int[] nums;
		HashMap<Integer, Boolean> map = new HashMap<>();
		
		public ThreeSumMultiple(int[] nums) {
			this.nums = nums;
			for (int i = 0; i < nums.length; i++) {
				for (int j = 0; j < nums.length; j++) {
					map.put(nums[i] + nums[j], true);
				}
			}
		}
		
		public boolean threeSum(int target) {
			for (int num : nums) {
				if (map.containsKey(target - num)) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	/**
	 * 15. 3Sum - contains duplicates
	 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? 
	 * Find all UNIQUE triplets in the array which gives the sum of zero.
	 */
	public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
        	if (i > 0 && nums[i] == nums[i-1]) continue; //avoid duplicates
        	int target = -nums[i];
        	int left = i+1, right = nums.length - 1;
        	while (left < right) {
        		int sum = nums[left] + nums[right];
        		if (sum == target) {
        			List<Integer> res = new ArrayList<>();
        			res.addAll(Arrays.asList(nums[i], nums[left], nums[right]));
        			result.add(res);
        			
        			left++;
        			right--;
        			while (left < right && nums[left] == nums[left-1]) left++;
        			while (left < right && nums[right] == nums[right+1]) right--;
        		} else if (sum < target) {
        			left++;
        		} else {
        			right--;
        		}
        	}
        }
        return result;
	}
	
	/**
	 * 3Sum - unsorted, print original index, remove dups
	 * 要求打印所有可能的index，要注意的是sort之后index就变了，要打印原来的index
	 * 可以有重复   比如 {0,1,3,-1,5};  target = 4,  要打印 {3,0,4}, {0,1,2} 
	 * 
	 * 在做题之前，先看一个小技巧，如何找到数组中数字排序之前的坐标
	 * 		1. put both index and value inside a class and sort by value,
	 * 		2. 用一个index array来存， 具体如下getOriginalIndex()
	 * 
	 * 所以，这题的解法就是先排序，但是同时也存原来的index，然后最后输出的时候找数字原来的
	 * index就好了。
	 */
	int[] unsorted_nums;
	public Integer[] getOriginalIndex(int[] nums) {
		unsorted_nums = nums;
		Integer[] index = new Integer[nums.length];
		for (int i = 0; i < nums.length; i++) {
			index[i] = i;
		}
		Arrays.sort(index, new Comparator<Integer>(){
			public int compare(Integer i1, Integer i2) {
				return unsorted_nums[i1] - unsorted_nums[i2];
			}
		});
		//value:  ums[index[0]]
		//original index： index[0]
		return index;
	}

	public void threeSumOriginalIndex(int[] nums, int target) {
		Integer[] index = getOriginalIndex(nums);
		for (int i = 0; i < index.length; i++) {
			if (i > 0 && nums[index[i]] == nums[index[i-1]]) continue;
			int num = target - nums[index[i]];
			int start = i+1, end = index.length -1;
			while (start <= end) {
				int sum = nums[index[start]] + nums[index[end]];
				if (sum == num) {
					System.out.println(index[i] + "," + index[start] + "," + index[end] + " -> " 
										+ nums[index[i]] +"," + nums[index[start]] + "," + nums[index[end]]);
					while (start < end && nums[index[start]] == nums[index[start+1]]) start++;
					while (start < end && nums[index[end]] == nums[index[end-1]]) end--;
					start++; end--;
				} else if (sum < num) {
					start++;
				} else {
					end--;
				}
			}
		}
	}
	
	/**
	 * 2Sum - unsorted, print original index, has dups
	 * 要求打印所有可能的index，要注意的是sort之后index就变了，要打印原来的index
	 * 可以有重复   比如 {3,3,1,3}  target = 4,  要打印 {0,2}, {1,2}, {2,3}
	 * 
	 * 相当于 twoSum，打印出所有解法，最坏的情况一共有C(N,2) = n*(n-1)/2个 pair index 个解法，时间复杂度应该没办法做到O(n),
	 * 还是O(n^2),  所以对于3sum来说，就是O(n^3) 了。
	 */
	public void twoSumOriginalIndexDups(int[] nums, int target) {
		HashMap<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (!map.containsKey(nums[i])) {
				map.put(nums[i], new ArrayList<Integer>());
			}
			map.get(nums[i]).add(i);
		}

		Integer[] index = getOriginalIndex(nums);
		for (int i = 0; i < index.length; i++) {
			int diff = target - nums[index[i]];
			if (map.containsKey(diff)) {
				List<Integer> list = map.get(diff);
				for (int idx : list) {
					if (idx > index[i])
						System.out.println(index[i] + "," + idx + " -> " + nums[index[i]] + "," + nums[idx]);
				}
			}
		}
	}
	
	
	/**
	 * 259. 3Sum Smaller
	 * Given an array of n integers nums and a target, find the number of index triplets i, j, k 
	 * with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
	 * 
	 * 要在O(N^2) 时间内完成，就是说当我们找到第一个组合num[start] + nums[end] < target - nums[i] 的时候，
	 * 说明从start 到 end 之间的任意组合都是满足条件的，这时候 count += end - start 即可，不需要在一个循环来检验
	 * 
	 */
	public int threeSumSmaller(int[] nums, int target) {
		int count = 0;
		Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int num = target - nums[i];
            int start = i+1, end = nums.length - 1;
            while (start < end) {
                int sum = nums[start] + nums[end];
                if (sum > num) {
                	end--;
                } else {
                	count += end - start;
                    start++;
                }
            }
        }
        return count;
    }
	
	/**
	 * 16. 3Sum Closest
	 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, 
	 * target.
	 * Return closest sum.
	 * 和3sum一样，只不过循环时候多加个和min diff的比较。
	 * 
	 * O(n^2) time
	 */
	public int threeSumClosest(int[] nums, int target) {
		Arrays.sort(nums);
        int minDiff = Integer.MAX_VALUE;
        int closest = 0;
        
        for (int i = 0; i < nums.length-2; i++) {
            int start = i+1, end = nums.length-1;
            while (start < end) {
                int sum = nums[i] + nums[start] + nums[end];
                if (sum == target) {
                    return target;
                } else if (sum > target) {
                    end--;
                } else {
                    start++;
                }
                int diff = Math.abs(sum - target);
                if (diff < minDiff) {
                    minDiff = diff;
                    closest = sum;
                }
            }
        }
        return closest;
    }
	
	/**
	 * 3 sum, 可以重复利用数字
	 *   {1,2,4}  target = 6
	 *   可以使用
	 *   1,1,4
	 *   2,2,2
	 */
	public void treeSumReuse(int[] nums, int target) {
    	Arrays.sort(nums);
    	List<List<Integer>> result = new ArrayList<>();
    	helper(nums, 0, new ArrayList<Integer>(), result, target);
    	for (List<Integer> r : result) {
    		System.out.println(Arrays.toString(r.toArray()));
    	}
    }

    private void helper(int[] nums, int index, List<Integer> path, List<List<Integer>> res, int target) {
    	if (path.size() == 3) {
    		if (target == 0) {
    			res.add(new ArrayList<Integer>(path));
    		}
    		return;
    	}

    	for (int i = index; i < nums.length; i++) {
    		if (target - nums[i] < 0) break;
    		path.add(nums[i]);
    		helper(nums, i, path, res, target - nums[i]);
    		path.remove(path.size()-1);
    	}
    }

    
    public void treeSumReuseIterative(int[] nums, int target) {
    	Arrays.sort(nums);
    	List<List<Integer>> result = new ArrayList<>();
    	for (int i = 0; i < nums.length; i++) {
    		int j = i, k = nums.length-1;
    		while (j <= k) {
    			if (nums[j] + nums[k] + nums[i] == target) {
    				result.add(Arrays.asList(nums[i], nums[j], nums[k]));
    				j++; k--;
    			} else if (nums[j] + nums[k] + nums[i] > target) {
    				k--;
    			} else {
    				j++;
    			}
    		}
    	}
    	for (List<Integer> r : result) {
    		System.out.println(Arrays.toString(r.toArray()));
    	}
    }
    
    
	/**
	 * 18. 4Sum Given an array S of n integers, are there elements a, b, c, and
	 * d in S such that a + b + c + d = target? Find all UNIQUE quadruplets in
	 * the array
	 * O(n^3) time, O(1) space
	 */
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> result = new ArrayList<>();
		if (nums.length < 4)
			return result;
		Arrays.sort(nums);

		for (int i = 0; i < nums.length - 3; i++) {
			if (i > 0 && nums[i] == nums[i - 1]) continue;
			for (int j = i + 1; j < nums.length - 2; j++) {
				if (j > i + 1 && nums[j] == nums[j - 1]) continue;
				int start = j + 1, end = nums.length - 1;
				while (start < end) {
					int sum = nums[i] + nums[j] + nums[start] + nums[end];
					if (sum == target) {
						result.add(Arrays.asList(nums[i], nums[j], nums[start], nums[end]));
						while (start < end && nums[start] == nums[start + 1]) start++;
						while (start < end && nums[end] == nums[end - 1]) end--;
						start++;
						end--;
					} else if (sum < target)
						start++;
					else
						end--;
				}
			}
		}
		return result;
	}
	
	/**
	 * 454. 4Sum II
	 * Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l)
	 * there are such that A[i] + B[j] + C[k] + D[l] is zero.
	 * 
	 * O(N^2) space,  O(n^2) time
	 */
	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                int sum = A[i] + B[j];
                int count = 1;
                if (map.containsKey(sum)) {
                    count = map.get(sum) + 1;
                }
                map.put(sum, count);
            }
        }
        int count = 0;
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                 int sum = C[i] + D[j];
                 if (map.containsKey(-sum)) {
                     count += map.get(-sum);
                 }
            }
        }
        return count;
    }
	
	/**
	 * 真正的k sum 问题
	 * http://lifexplorer.me/leetcode-3sum-4sum-and-k-sum/
	 */
//	 private List<List<Integer>> kSum(int length, int target, int start_index) {
//		 
//	 }
	
	
//	fook Interview Question for SDE1s
//	three sum with duplicate, pirnt all indexes, for example: 
//	0 2 -2 -2 
//	(0)(1)(2)(3) 
//	print (0, 1, 2) (0, 1, 3) 
//	can you do it use n^2 (or less) time complexity with as less space as possible? 
//	public List<List<Integer>> threeSum(int[] nums) {}

	
	/**
	 * 最长等差数列。其实和3sum很类似。 b-a = c -b  , 2b = c+a ,不就成了3sum问题。前提是输入数组排序好了 
	 * http://www.geeksforgeeks.org/length-of-the-longest-arithmatic-progression-in-a-sorted-array/
	 * 
	 * TBD
	 */
	public void longestArith (int[] items) {
		int[][] f = new int[items.length][items.length];
		int maxLen = 0, maxInc = -1, last = -1;
		for (int i = 1; i < items.length - 1; i++) {
			int j = i-1, k = i+1;
			while (j >= 0 && k < items.length) {
				if (items[j] + items[k] > items[i] * 2) {
					j--;
				} else if (items[j] + items[k] < items[i] * 2) {
					k++;
				} else {
					f[i][k] = f[j][i] == 0 ? 3 : f[j][i] + 1;
					if (f[i][k] > maxLen) {
						maxLen = f[i][k];
						last = items[k];
						maxInc = items[i] - items[j];
					}
					j--;
					k++;
				}
			}
		}
		
		for (int i = 0; i < maxLen; i++) {
			System.out.print(last + ",");
			last = last - maxInc;
		}
		System.out.println("\n" + maxLen);
	}
	
	
	public static void main(String[] args) {
		NSums clz = new NSums();
//		clz.getOriginalIndex(new int[]{1,5,2,4,7,3,2});
		int[] nums = new int[]{3,3,1,3};
		clz.twoSumOriginalIndexDups(nums, 4); 
		
		int[] nums1 = new int[]{0,1,3,-1,5};
//		clz.threeSumOriginalIndex(nums1, 4);
		
		System.out.println();
		int[] nums2 = {1,2,4};
		clz.treeSumReuse(nums2, 6);
		
		int[] nums3 = {1,1,2};
		clz.treeSumReuse(nums3, 4);
		
		System.out.println();
		nums3 = new int[]{-1,-1,2};
		clz.treeSumReuseIterative(nums3, 0);
		System.out.println();
		
		int[] nums4 =  {1,3,2, 5,7,4};
		clz.longestArith(nums4);
		
	}
}
