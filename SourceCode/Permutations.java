import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {
	/**
	 * 46. Permutations
	 * 和subset， combination一样，都是经典的DFS模板，不同的就是这里要检查是不是已经被选择过了。
	 */
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> path = new ArrayList<Integer>();
		helper(nums, path, result);
		return result;
	}
	
	private void helper(int[] nums, List<Integer> path, List<List<Integer>> result) {
		if (path.size() == nums.length) {
			result.add(new ArrayList<Integer>(path));
			return;
		}
		
		for (int i = 0; i < nums.length; i++) {
			if (path.contains(nums[i])) continue;
			path.add(nums[i]);
			helper(nums, path, result);
			path.remove(path.size() -1);
		}
	}
	
	/**
	 * 47. Permutations II (with dups, return unique)
	 */
	public List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> path = new ArrayList<Integer>();
		Arrays.sort(nums);
		boolean[] visited = new boolean[nums.length];
		helper(nums, path, result, visited);
		return result;
    }

	
	private void helper(int[] nums, List<Integer> path, List<List<Integer>> result, boolean[] visited) {
		if (path.size() == nums.length) {
			result.add(new ArrayList<Integer>(path));
			return;
		}
		
		for (int i = 0; i < nums.length; i++) {
			if (visited[i]) continue;
			//去重复的条件，当前数字等于前一个数字的时候，要看前一个数字有没被使用过，如果没有使用过，说明是重复
			if (i > 0 && nums[i] == nums[i-1] && !visited[i-1]) continue; 
			path.add(nums[i]);
			visited[i] = true;
 			helper(nums, path, result, visited);
			path.remove(path.size() - 1);
			visited[i] = false;
		}
	}
	
	/**
	 * 60. Permutation Sequence
	 * Given n and k, return the kth permutation sequence.
	 * Note: Given n will be between 1 and 9 inclusive.
	 * 
	 * 我们看到，其实permution的序列是有规律的，比以第一个数字为开头，后面有 (n-1)! 个组合，比如1开头，后面有 2! 个组合。
	 * 123
	 * 131 
	 * 
	 * 所以要求第K个，就先看看 (K-1) mod (n-1) 是多少，他就是那个位置的第一位。然后以此类推, mod (n-2)! ...
	 */
	 public String getPermutation(int n, int k) {
		 List<Integer> numbers = new ArrayList<Integer>();
		 int mod = 1;
		 for (int i = 1; i < n; i++) {
			 numbers.add(i);
			 mod *= i;
		 }
		 
		 k--;
		 StringBuilder sb = new StringBuilder();
		 for (int i = 0; i < n; i++) {
			 mod /= (n-i);
			 int first = k / mod;
			 sb.append(numbers.get(first));
			 numbers.remove(first);
			 k = k % mod;
		 }
		 
		 return sb.toString();
	 }
	 
	 /**
	  * 31. Next Permutation
	  */
	public void nextPermutation(int[] nums) {
		if (nums.length < 2)
			return;
		int n = nums.length, j = n - 2;
		//找上升序列，直到不上升位置，此时j就是要交换的数字。
		while (j >= 0 && nums[j] >= nums[j + 1]) j--;
		if (j < 0) {
			reverse(nums, 0, n - 1);
			return;
		}
		int i = n - 1;
		//在上升序列中，找第一个大于j的数字。用来和j交换。
		while (i >= j && nums[i] <= nums[j]) i--;
		swap(nums, i, j);
		reverse(nums, j + 1, n - 1);
	}

	private void reverse(int[] nums, int start, int end) {
		for (int i = start, j = end; i < j; i++, j--) {
			int tmp = nums[i];
			nums[i] = nums[j];
			nums[j] = tmp;
		}
	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	
	/**
	  * Previous Permutation
	  * 就是和next permutation反过来。找一个下降序列
	  */
	public void prevPermutation(int[] nums) {
		if (nums.length < 2)
			return;
		int n = nums.length, j = n - 2;
		while (j >= 0 && nums[j] <= nums[j+1]) j--;
		if (j < 0) {
			reverse(nums, 0, n-1);
			return;
		}
		
		int i = n-1;
		while (i >= j && nums[i] >= nums[j]) i--;
		swap(nums, i, j);
		reverse(nums, j+1, n-1);
	}
	
	public static void main(String[] args) {
		Permutations clz = new Permutations();
		int[] nums = {1,2};
		clz.prevPermutation(nums);
		System.out.println(Arrays.toString(nums));
	}
}
