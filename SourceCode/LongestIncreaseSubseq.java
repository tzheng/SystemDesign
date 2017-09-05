import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestIncreaseSubseq {
	
	/**
	 * 一般动态规划 n^2的复杂度已经很好了。
	 */
	public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[] f = new int[nums.length];
        f[0] = 1;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            f[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            max = Math.max(f[i], max);
        }
        
        return max;
    }
	
	/**
	 * 还有更优秀的解法，耐心排序
	 * https://segmentfault.com/a/1190000003819886
	 */
	public int longestIncreasingSubsequence(int[] A) {
		int size = A.length;
		int[] tailTable = new int[size];
		int len; // always points empty slot

		tailTable[0] = A[0];
		len = 1;
		for (int i = 1; i < size; i++) {
			if (A[i] < tailTable[0])
				// new smallest value
				tailTable[0] = A[i];
			else if (A[i] > tailTable[len - 1])
				// A[i] wants to extend largest subsequence
				tailTable[len++] = A[i];
			else
				// A[i] wants to be current end candidate of an existing
				// subsequence. It will replace ceil value in tailTable
				tailTable[CeilIndex(tailTable, -1, len - 1, A[i])] = A[i];
		}

		return len;
	}

	int CeilIndex(int A[], int l, int r, int key) {
		while (l+1 < r) {
			int m = l + (r - l) / 2;
			if (A[m] >= key)
				r = m;
			else
				l = m;
		}
		return r;
	}
	
	/**
	 * 334. Increasing Triplet Subsequence
	 * Return true if there exists i, j, k 
	 * such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
	 * 
	 * 本来正常的做法应该是，找递增子序列，只要有个的长度大于3，说明存在i,j,k
	 * 但是由于3是固定的，所以我们就用一个更巧妙的解法
	 */
	public boolean increasingTriplet(int[] nums) {
        int x = Integer.MAX_VALUE;
        int y = Integer.MAX_VALUE;
        for (int z : nums) {
            if (x >= z) {
                x = z;
            } else if (y >= z) {
                y = z;
            } else {
                return true;
            }
        }
        return false;
    }
	
	/**
	 * Followup 1: 上面的3 不固定了
	 * 那就彻底成为了找长度为K的increasing subsequnce是否存在的问题了。这里就不写答案了。
	 */
	public boolean increasingTriplet(int[] nums, int k) {
        int len = 1;
        int[] tail = new int[nums.length];
        tail[0] = nums[0];
        for (int i = 0; i < nums.length; i++) {
        	if (nums[i] < tail[0]) {
        		tail[0] = nums[i];
        	} else if (nums[i] > tail[len-1]) {
        		tail[len++] = nums[i];
        		if (len >= k) return true;
        	} else {
        		int index = CeilIndex(tail, -1, len - 1, nums[i]);
        		tail[index] = nums[i];
        	}
        }
        return len >= k;
    } 
	
	/**
	 * 最长递增子序列的个数
	 * 比如   1,2,3,6,5,4 最长的有  1,2,3,6,  1,2,3,5，  1，2，3，4 三个
	 * 比如 2,1,4,3,6, 最长的有 2,4,6   1,3,6 , 2,3,6, 1,4,6 
	 * 两个数组，一个存到当前位置的LIS的长度
	 * 一个存到当前位置LIS的个数
	 */
	public void countLongestIncreasing(int[] nums) {
		int[] f = new int[nums.length], count = new int[nums.length];
        f[0] = 1; count[0] = 1;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            f[i] = 1;
            count[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                	if (f[j] + 1 > f[i]) {
                		count[i] = count[j];
                		f[i] = f[j] + 1;
                	} else if (f[j] + 1 == f[i]) {
                		count[i] += count[j];
                	}
                }
            }
            max = Math.max(f[i], max);
        }
        
        int numOfInc = 0;
        for (int i = 0; i < f.length; i++) {
        	if (f[i] == max) 
        		numOfInc += count[i];
        }
        
        System.out.println("Longest is : " + max +", count: " + numOfInc);
        System.out.println(Arrays.toString(f));
        System.out.println(Arrays.toString(count));
	}
	
	
	/**
	 * 354. Russian Doll Envelopes
	 * 把问题拓展到二维，俄罗斯套娃
	 * Given envelopes = [[5,4],[6,4],[6,7],[2,3]], 
	 * the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
	 * 
	 * Sort the array. Ascend on width and descend on height if width are same.
	 * Find the longest increasing subsequence based on height.
	 * Since the width is increasing, we only need to consider height.
	 * [3, 4] cannot contains [3, 3], so we need to put [3, 4] before [3, 3] when sorting 
	 * otherwise it will be counted as an increasing number if the order is [3, 3], [3, 4]
	 */
	public int maxEnvelopes(int[][] envelopes) {
		Arrays.sort(envelopes, new Comparator<int[]>(){
            public int compare(int[] a, int[] b) {
                if (a[0] < b[0] && a[1] < b[1]) {
                    return -1;
                } else {
                    return 1;
                }
            }    
        });
        return 0;
    }
	
	
	/**
	 * 最长等差数列
	 * 1， 6， 3， 5， 9， 7 
	 * 顺序不能改变， 就是 1,3,5,7,
	 * 可以用hashmap，记录数字两两之间的差值
	 */
	public void longestArithmetic(int[] nums) {
		Map<Integer, List<int[]>> map = new HashMap<>();
		
		for (int i = 0; i < nums.length-1; i++) {
			for (int j = i+1; j < nums.length; j++) {
				int diff = nums[j] - nums[i];
				if (!map.containsKey(diff)) {
					map.put(diff, new ArrayList<int[]>());
				}
				map.get(diff).add(new int[]{i, j});
			}
		}
		
		int max = 0, maxEnd = -1, maxDiff = -1;
		for (int key : map.keySet()) {
			int[] len = new int[nums.length];
//			Arrays.fill(len, 1);
			for (int[] pair : map.get(key)) {
				len[pair[1]] = len[pair[0]] == 0 ? 2 : len[pair[0]]+ 1;
				if (len[pair[1]] > max) {
					max = len[pair[1]];
					maxEnd = nums[pair[1]];
					maxDiff = key;
				}
			}
		}
		System.out.println("Longest1 is: " + max + " diff: " + maxDiff + ", ends at: " + maxEnd);
	}
	
	/**
	 * 最长等差数列
	 * 1， 6， 3， 5， 9， 7 
	 * 顺序能改变， 就是 1,3,5,7,9
	 */
	public void longestArithmetic2(int[] nums) {
		Arrays.sort(nums);
		int[][] f = new int[nums.length][nums.length];
		int max = 0, maxDiff = -1, maxEnd = -1;
		
		for (int i = 0; i < nums.length; i++) {
			int j = i - 1, k = i+1;
			while (j >= 0 && k < nums.length) {
				if (nums[j] + nums[k] > 2*nums[i]) {
					j--;
				} else if (nums[j] + nums[k] < 2*nums[i]) {
					k++;
				} else {
					f[i][k] = f[j][i] == 0 ? 3 : f[j][i] + 1;
					if (f[i][k] > max) {
						max = f[i][k];
						maxEnd = k;
						maxDiff = nums[i] - nums[j];
					}
					j--; k++;
				}
			}
		}
		System.out.println("Longest2 is: " + max + " diff: " + maxDiff + ", ends at: " + maxEnd);
	}

	class Pair {
		int a, b;
		public Pair(int a, int b) {this.a = a; this.b = b;}
	}
	
	public static void main(String[] args) {
		LongestIncreaseSubseq clz = new LongestIncreaseSubseq();
		int[] nums = {1,3,6,7,9,4,10,5,6};
		clz.lengthOfLIS(nums);
		
		nums = new int[]{1,2,3,6,5,4};
		clz.countLongestIncreasing(nums);
		
		nums = new int[]{2,1,4,3,6};
		clz.countLongestIncreasing(nums);
		
		nums = new int[]{1,6,3,5,9,7};
		clz.longestArithmetic(nums);
		clz.longestArithmetic2(nums);
		
		nums = new int[]{1, 7, 10, 13, 14, 19};
		clz.longestArithmetic(nums);
		clz.longestArithmetic2(nums);
	}
}
