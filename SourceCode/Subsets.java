import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsets {
	/**
	 * 78. Subsets
	 * 和 combination一样，subset的题目也有模板可以使用，就是这题的解法
	 * Given a set of distinct integers, nums, return all possible subsets.
	 */
	public List<List<Integer>> subsetsDFS(int[] nums) {
		List<List<Integer>> results = new ArrayList<>();
		List<Integer> set = new ArrayList<>();
		dfsHelper(nums, set, results, 0);
		return results;
	}
	
	private void dfsHelper(int[] nums, List<Integer> set, List<List<Integer>> results, int pos) {
		results.add(new ArrayList<Integer>(set));
		for (int i = pos; i < nums.length; i++) {
			set.add(nums[i]);
			dfsHelper(nums, set, results, i+1);
			set.remove(set.size() - 1);
		}
	}
	
	/**
	 * 用迭代的方式来写。
	 * []    // not take 1, not take 2
	   [2]   // not take 1,     take 2 + new
	   [1]   //     take 1, not take 2
	   [1,2] //     take 1,     take 2 + new
	 * @param nums
	 * @return
	 */
	public List<List<Integer>> subsetsBFS(int[] nums) {
		List<List<Integer>> results = new ArrayList<>();
		Arrays.sort(nums); 
		results.add(new ArrayList<Integer>());
		for (int i = 0; i < nums.length; i++) {
			int size = results.size();
			for (int j = 0; j < size; j++) {
				List<Integer> set = new ArrayList<Integer>(results.get(j));
				set.add(nums[i]);
				results.add(set);
			}
		}
		return results;
	}
	
	/**
	 * 90. Subsets II (contains dups, return distinct)
	 * 几乎和combination一样，就是先排序，然后只用重复数字的第一个做subset，剩下的直接跳过
	 */
	public List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> results = new ArrayList<>();
		List<Integer> set = new ArrayList<>();
		Arrays.sort(nums);
		dfsHelperDup(nums, set, results, 0);
		return results;
	}

	private void dfsHelperDup(int[] nums, List<Integer> set, List<List<Integer>> results, int pos) {
		results.add(new ArrayList<Integer>(set));
		for (int i = pos; i < nums.length; i++) {
			if (i > pos && nums[i] == nums[i-1]) continue;
			set.add(nums[i]);
			dfsHelperDup(nums, set, results, i + 1);
			set.remove(set.size() - 1);
		}
	}
	
	/**
	 * 416. Partition Equal Subset Sum
	 * 看一个比较复杂的题目。暴力破解的办法就是套用模板，求出所有的subset，如果subset的和 
	 * sumSubset = totalSum - sumSubset, 那么就是一个解法
	 * 因为题目只要求返回是否，所以是个标准的DP题。
	 * 先看暴力解法，然后再看DP
	 */
	public boolean canPartitionDFS(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        int sum = 0;
        for (int num : nums) 
        	sum += num;
        if (sum % 2 == 1) return false;
        Arrays.sort(nums);
        return partitionHelper(nums, 0, sum, 0);
    }
	
	private boolean partitionHelper(int[] nums, int currSum, int sum, int pos) {
		if (currSum * 2 == sum) {
			return true;
		}
		if (currSum * 2 > sum) {
			return false;
		}
		
		for (int i = pos; i < nums.length; i++) {
			if (partitionHelper(nums, currSum + nums[i], sum, i+1)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 思路：一个背包的题目，背包容量为数组中元素和的一半＋１，这样只要看是否有元素可以正好填满背包即可．
	 * 但是每个元素只能用一次，所以在尝试放一个元素的时候还要避免他对尝试放其他位置时对自己的影响．所以
	 * 在尝试放一个元素到背包的时候需要从容量最大的位置开始，如果（当前位置－当前元素大小）位置可以通过
	 * 放置之前的元素达到，则当前位置也可以通过放置当前元素正好达到这个位置．状态转移方程为：
	 * 		dp[i] = dp[i] || dp[i - nums[k]];
	 */
	public boolean canPartitionDP(int[] nums) {
		if (nums == null || nums.length == 0)
			return true;
		int sum = 0;
		for (int num : nums) 
			sum += num;
		if (sum % 2 == 1) return false;
		sum /= 2;
		boolean[] f = new boolean[sum+1];
		f[0] = true;
		for (int i = 0; i < nums.length; i++) {
			for (int j = sum; j >= nums[i]; j--) {
				f[j] = f[j] || f[j-nums[i]];
			}
		}
		
		return f[sum];
	}
	
	/**
	 * 368. Largest Divisible Subset
	 * 暴力解法就是不断求subset，每次往subset加新元素之前看看新元素能不能和set里面的整除。 复杂度 N*2^N, 非常高。
	 * 所以这里引出了一个DP的解法，解法和最长上升子序列差不多。但是由于我们要求的不是长度，所以要多加一个数组，记录路径
	 */
	public List<Integer> largestDivisibleSubset(int[] nums) {
		int n = nums.length;
		int[] f = new int[n], path = new int[n];
		int max = 0, index = -1;
		Arrays.sort(nums);
		for (int i = 0; i < n; i++) {
			f[i] = 1;
			path[i] = -1;
			for (int j = 0; j < i; j++) {
				if (nums[i] % nums[j] == 0) {
					if (f[j] + 1 > f[i]) {
						f[i] = f[j] + 1;
						path[i] = j;
					}
				}
			}
			if (f[i] > max) {
				max = f[i];
				index = i;
			}
		}
		List<Integer> res = new ArrayList<Integer>();
		while (index >= 0) {
			res.add(nums[index]);
			index = path[index];
		}
		return res;
	}
	
	/**
	 * 求有多少个subsets，里面的min和max之和小于k。国人小哥很nice的提示了可以先排序，然后two pointer从两头往中间找。
	 */
	int subsetcount = 0;
	public void subsetMinMax(int[] nums, int k) {
		//暴力解法，算所有subset，复杂度 2^n * n! 
		helperk(nums, new ArrayList<Integer>(), k, 0);
		System.out.println(subsetcount);
		
		//数学做法，n 个数字，有2^n 个子集
		Arrays.sort(nums);
		int i = 0, j = nums.length - 1;
		int res = 0;
		if (k > 0) res++; //空集
		while (i <= j) {
			if (nums[i] + nums[j] < k) {
				res += Math.pow(2, j-i); //subset的个数是 2^(j-1+1), 但是一定要包含i，那么就是2^(j-i)
				i++;
			} else {
				j--;
			}
		}
		System.out.println(res);
	}
	
	private void helperk(int[] nums, List<Integer> set, int k, int pos) {
		
		if (validSet(set, k)) {
			subsetcount++;
			System.out.println(Arrays.toString(set.toArray()));
		}
		for (int i = pos; i < nums.length; i++) {
			set.add(nums[i]);
			helperk(nums, set, k, i+1);
			set.remove(set.size()-1);
		}
		
		
	}
	
	private boolean validSet(List<Integer> set, int k) {
		if (set.size() == 0) return true;
		int min = set.get(0), max = set.get(0);
		for (int n : set) {
			min = Math.min(min, n);
			max = Math.max(max, n);
		}
		return max + min < k;
	}
	
	/**
	 * 给出一组质数， 求出所有可以得到的乘积，一个乘积只能用一次相同的质数。 
	 * 比如给出{2,3,5} 要返回{2,3,5,6,10,15,30}. (不会有12, 18 因为 6 = 2 * 3, 所以不能在乘以2, 3).
	 */
	public void primeSubset(int[] nums) {
		//null check
		List<Integer> res = new ArrayList<>();
		primeHelper(nums, 0, res, 1);
		System.out.println(Arrays.toString(res.toArray()));
		
		//BFS
		List<Integer> list = new ArrayList<>();
		list.add(1);
		
		for (int i = 0; i < nums.length; i++) {
			int size = list.size();
			for (int j = 0; j < size; j++) {
				list.add(list.get(j) * nums[i]);
			}
			//list.add(nums[i]);
		}
		list.remove(0);
		System.out.println(Arrays.toString(list.toArray()));
	}
	
	private void primeHelper(int[] nums, int pos, List<Integer> res, int product) {
		if (pos != 0)
			res.add(product);
		
		for (int i = pos; i < nums.length; i++) {
			primeHelper(nums, i+1, res, product*nums[i]);
		}
	}
	
	public static void main(String[] args) {
		Subsets clz = new Subsets();
		int[] nums = {1,4,5,6};
		clz.subsetMinMax(nums, 8);
		
		nums = new int[]{3,5,7};
		clz.primeSubset(nums);
	}
}
