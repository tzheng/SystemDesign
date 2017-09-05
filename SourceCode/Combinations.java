import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Combinations {
	
	/**
	 * 77. Combinations
	 * 最基础的的，生成各种组合，时间 n!
	 * 非常标准的递归模板
	 */
	public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (k == 0 || n < k) return result;
        combineHelper(n, k, 0, result, new ArrayList<Integer>());
        return result;
    }
    
    private void combineHelper(int n, int k, int curr, List<List<Integer>> result, List<Integer> path) {
        if (path.size() == k) {
            result.add(new ArrayList<Integer>(path));
            return;
        }
        for (int i = curr; i < n; i++) {
            path.add(i+1);
            combineHelper(n, k, i+1, result, path);
            path.remove(path.size()-1);
        }
    }

    
	/**
	 * 然后看一道经典题目 17. Letter Combinations of a Phone Number Add to List
	 * 1 解法1，递归做，时间3^N， 方法就是套用递归模板
	 */
	String[] keys = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
	public List<String> letterCombinations(String digits) {
		List<String> result = new ArrayList<>();
		if (digits == null || digits.length() == 0) {
			return result;
		}
		letterHelper(0, digits, result, "");
		return result;
	}

	private void letterHelper(int pos, String digits, List<String> result, String currStr) {
		if (pos >= digits.length()) {
			result.add(currStr);
			return;
		}
		int number = (int) (digits.charAt(pos) - '0');
		String key = keys[number];
		for (char c : key.toCharArray()) {
			letterHelper(pos + 1, digits, result, currStr + c);
		}
	}
	
	/**
	 * 解法2，用BFS的办法来做，时间复杂度是一样的。
	 */
	public List<String> letterCombinationsBFS(String digits) {
		LinkedList<String> result = new LinkedList<String>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        result.add("");
        for (int i = 0; i < digits.length(); i++) {
            int num = digits.charAt(i) - '0';
            int size = result.size();
            for (int k = 0; k < size; k++) {
                String tmp = result.pop();
                for (int j = 0; j < keys[num].length(); j++) {
                    result.offer(tmp + keys[num].charAt(j));
                }
            }
        }
        
        List<String> rec = new LinkedList<>();
        rec.addAll(result);
        return rec;
	}

	/**
	 * 然后来看一系列问题，问题基本来说就是在数组里面找各种组合，组合的和为某一个值，
	 * 题目有很多变化，比如去重复元素之类的。先看一个简单的
	 * 
	 * 39. Combination Sum (without duplicates) 
	 * 		关键点1：没有重复元素。 2：可以重复使用同一个元素。3：排序数组，可以优化时间 beat 96%
	 * 
	 * 这里递归的最大深度是d= target / min
	 * Branching factor b = m， m是candidate数组里小于等于target的distinct元素的个数。 其实这里多算了，每一层的m都在减少，而且是不规则减少 
	 * 我们利用DFS公式可以算出这里的Time Complexty = O(m^d)，  Space Complexity = O(m)
	 */
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> path = new ArrayList<>();
		Arrays.sort(candidates);
		sumHelper1(candidates, 0, path, result, target);
		return result;
	}
	
	private void sumHelper1(int[] candidates, int pos, List<Integer> path, List<List<Integer>> result, int target) {
		if (target < 0) return;
		if (target == 0) {
			result.add(new ArrayList<Integer>(path));
			return;
		}
		
		for (int i = pos; i < candidates.length; i++) {
		    if (target - candidates[i] < 0) break;
			path.add(candidates[i]);
			sumHelper1(candidates, i, path, result, target - candidates[i]);
			path.remove(path.size() - 1);
		}
	}
	
	/**
	 * 40. Combination Sum II (The solution set must not contain duplicate combinations)
	 * 原始数组可以有重复元素，输出结果不能有重复组合, beat 99.07%
	 * 关键点 1：排序，对于有重复的，就使用第一个，其他跳过。 2. 元素不能重复使用
	 */
	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> path = new ArrayList<Integer>();
		Arrays.sort(candidates);
		sumHelper2(candidates, 0, path, result, target);
		return result;
	}
	
	private void sumHelper2(int[] candidates, int pos, List<Integer> path, List<List<Integer>> result, int target) {
		if (target < 0) return;
		//如果允许负数，这里要加一个检查
		if (target == 0 && path.size() > 0) {
			System.out.println(Arrays.toString(path.toArray()));
			result.add(new ArrayList<Integer>(path));
			return;
		}
		
		for (int i = pos; i < candidates.length; i++) {
			if (i > pos && candidates[i] == candidates[i-1]) continue; 
			//出了这句去除重复元素，其他和模板完全一样。
//		    if (target - candidates[i] < 0) break; //如果允许负数，这里要去掉
			path.add(candidates[i]);
			sumHelper2(candidates, i+1, path, result, target - candidates[i]);
			path.remove(path.size() - 1);
		}
	}
	
	/**
	 * 216. Combination Sum III
	 * given that only numbers from 1 to 9 can be used and 
	 * each combination should be a unique set of numbers.
	 * 任何限制条件都不会改变，这里限制条件是结果组合的大小要为k
	 */
	public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<Integer>();
        sumHelper3(k, n, result, path, 1);
        return result;
    } 
	
	private void sumHelper3(int k, int n, List<List<Integer>> result, List<Integer> path, int pos){
		if (path.size() == k && n == 0) {
			result.add(new ArrayList<Integer>(path));
			return;
		}
		
		for (int i = pos; i <= 9; i++) { //除了循环变化之外，还是标准模板
			if (n - i < 0) break;
			path.add(i);
			sumHelper3(k, n-i, result, path, i+1);
			path.remove(path.size() - 1);
		}
	}
	
	/**
	 * 377. Combination Sum IV
	 * 关键点 1.求多少个解法（说明可以DP）  2. 可以重复使用。 3. [1,3] 和 [3,1]是两种解法
	 * 如果用DFS的解法，很显然会超时。 所以要用DP的解法
	 */
	public int combinationSum4DFS(int[] nums, int target) {
		if (target == 0) return 1;
		if (target < 0) return 0;
		int ret = 0;
		for (int i = 0; i < nums.length; i++) {
			if (target - nums[i] < 0) break;
			ret += combinationSum4DFS(nums, target - nums[i]);
		}
		return ret;
	}
	
	public int combinationSum4(int[] nums, int target) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		
		int[] f = new int[target+1];
		f[0] = 1; 
		Arrays.sort(nums);
		for (int i = 1; i <= target; i++) {
			for (int num : nums) {
				if (i - num < 0) break;
				f[i] += f[i-num];
			}
		}
		return f[target];
	}
	
	/**
	 * followup: 如果有负数怎么办
	 * https://discuss.leetcode.com/topic/52290/java-follow-up-using-recursion-and-memorization
	 */
	
	
	/**
	 * 254. Factor Combinations
	 * 8 = 2 x 2 x 2;
	 *   = 2 x 4.
	 *   Time Complexity - O(2^n)， Space Complexity - O(n).
	 * 
	 * 更像是subset的问题
	 */
	public List<List<Integer>> getFactors(int n) {
		List<List<Integer>> result = new ArrayList<>();
		if (n <= 1) return result;
		factorHelper(n, result, new ArrayList<Integer>(), 2);
		return result;
    }
	
	private void factorHelper(int n, List<List<Integer>> result, List<Integer> path, int factor) {
		if (path.size() > 0) {
		    path.add(n);
		    result.add(new ArrayList<>(path));
		    path.remove(path.size() - 1);  //注意remove
		}
		// 最关键的在这里，i = factor而不是2，因为每次从2开始的话， 会有重复的
		for (int i = factor; i <= Math.sqrt(n); i++) {
			if (n % i == 0 ) {
				path.add(i);
				factorHelper(n / i, result, path, i);
				path.remove(path.size() - 1);
			}
		}
	}
	
	

	public static void main(String[] args) {
		Combinations clz = new Combinations();
		int[] nums = {4,5,3,-2,2, -1, -9};
		int target = 0;
		clz.combinationSum2(nums, target);
	}
}
