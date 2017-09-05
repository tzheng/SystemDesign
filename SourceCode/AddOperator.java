import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOperator {
	
 /**
  *  给你一个数组由1-9组成，给你一个target value，可以在数字中间加 + 或者 -, 问你有多少种组合可以得到target value。
  *  同时有一个eval method，把表达式传进去，返回计算结果。
  *  类似于lc282,494的mix。这里和282相比不需要做乘法，并且符号可以加在第一数的前面，这里和484一样。
  *  但是和494不同的是数字并不一定是一位，可以是多位组成，这里和282一样。
  *  写完后，让跑test,最后问时间复杂度。 
  *  
  *  比如 [2,1,9]  2+1+9 = 12,  21-9 = 12
  *  
  *  T(n) = 2T(n-1) + 2T(n-2) + ... + 2T(1)
  *  T(n-1) = 2T(n-2) + 2T(n-3) + ... + 2T(1)
  *  
  *  T(n) = 3T(n-1),  O(3^n)
  */
	
	public void calc(int[] nums, int target) {
//		helper(0, nums, target, "", new ArrayList<Integer>());
		helperMulti(nums, 0, target, 0, 0, "");
		
	}
	
	private void helper(int pos, int[] nums, int target, String str, List<Integer> path) {
		if (pos == nums.length) {
			if (evaluate(path) == target) {
				System.out.println("Found: " + str + " = " + evaluate(path));
			} else {
				System.out.println("Not Found: " + str + " = " + evaluate(path));
			}
		}
		
		int num = 0;
		for (int i = pos; i < nums.length; i++) {
			if (i > pos && nums[pos] == 0) break;
			num = num*10 + nums[i];
			path.add(num);
			helper(i+1, nums, target, str + "+" + num, path);
			path.remove(path.size()-1);
			
			path.add(-num);
			helper(i+1, nums, target, str + "-" + num, path);
			path.remove(path.size()-1);
		}
	}
	
	private int evaluate(List<Integer> path) {
		int sum = 0;
		for (int n : path) sum+= n;
		return sum;
	}
	
	
	/**
	 * 给一串数字，每个数字都要有 + 或者 * ，求最大
	 */
	public void addExpression(int[] nums) {
		helper(nums, 0, 0, 0, "");
		System.out.println("Max is: " + max);
	}
	
	int max = 0;
	void helper(int[] nums, int pos, int sum, int prev, String path) {
		if (pos == nums.length) {
			System.out.println(path + " = " + sum);
			max = Math.max(max, sum);
			return;
		}
		
		helper(nums, pos+1, sum + nums[pos], nums[pos], path + "+" + nums[pos]);
		if (pos > 0) {
			helper(nums, pos+1, sum - prev + prev*nums[pos], prev*nums[pos], path + "*" + nums[pos]);
		}
	}
	

	/**
	 * 给一个数字组成的字符串，可以在任意两个数字之间放加号或乘号，求可以得到的最大值。
	 */
	Map<String, Integer> map = new HashMap<>();
	public void addExpressionMulti(String s) {
		max = 0;
		helper(s, 0,0,0, "");
		System.out.println("Max is: " + max);
	}
	
	void helper(String s, int pos, int sum, int prev, String path) {
		if (pos == s.length()) {
			System.out.println(path + " = " + sum);
			max = Math.max(max, sum);
			return;
		}
		
		for (int i = pos; i < s.length(); i++) {
			if (s.charAt(pos) == '0' && i != pos) break;
			if (pos == 0 && i == s.length()-1) break;
			
			int num = Integer.valueOf(s.substring(pos, i+1));
			helper(s, i+1, sum + num, num, path + "+" + num);
			if (pos > 0) {
				helper(s, i+1, sum - prev + prev*num, prev*num, path + "*" + num);
			}
			
		}
		
	}
	
	public int helper(String s, int start, int end) {
		if (start > end) return 0;
		if (start == end) return s.charAt(start)- '0';
		
		if (map.containsKey(s.substring(start, end + 1))) {
			return map.get(s.substring(start, end+1));
		}
		int max = 0;
		
		for (int i = start; i < end; i++) {
			int num = Integer.valueOf(s.substring(start, i+1));
			int add = num + helper(s, i+1, end);
			int multi = num * helper(s, i+1, end);
			max = Math.max(max, Math.max(add, multi));
		}
		
		if (start != 0) {
			max = Math.max(max, Integer.valueOf(s.substring(start, end+1)));
		}
		
		map.put(s.substring(start, end+1), max);
		return max;
	}
	
	//支持加减乘除，单个数字
	public void helper(int[] nums, int pos, int target, int sum, int prevNum, String path) {
		if (pos == nums.length) {
			if (sum == target) {
				System.out.println(path + " = " + target);
			}
			return;
		}
		
		if (pos == 0) {
			helper(nums, pos+1, target, sum+nums[pos], nums[pos], "" + nums[pos]);
		} else {
			helper(nums, pos+1, target, sum+nums[pos], nums[pos], path + "+" + nums[pos]);
			helper(nums, pos+1, target, sum-nums[pos], -nums[pos], path + "-" + nums[pos]);
			helper(nums, pos+1, target, sum-prevNum + prevNum*nums[pos], prevNum*nums[pos], path + "*" + nums[pos]);
			if (nums[pos] > 0) {
				helper(nums, pos+1, target, sum-prevNum + prevNum/nums[pos], prevNum/nums[pos], path + "/" + nums[pos]);
			}
		}
		
	}
	
	//支持加减乘除，外加多位数字
	public void helperMulti(int[] nums, int pos, int target, int sum, int prevNum, String path) {
		if (pos == nums.length) {
			if (sum == target) {
				System.out.println(path + " = " + target);
			}
			return;
		}
		
		int num = 0;
		for (int i = pos; i < nums.length; i++) {
			if (i > pos && nums[pos] == 0) break;
			num = num*10 + nums[i]; 
			if (pos == 0) {
				helper(nums, i+1, target, sum+num, num, "" + num);
			} else {
				helper(nums, i+1, target, sum+num, num, path + "+" + num);
				helper(nums, i+1, target, sum-num, num, path + "-" + num);
				helper(nums, i+1, target, sum-prevNum + prevNum * num, prevNum*num, path + "*" + num);
				if (num != 0) {
					helper(nums, i+1, target, sum-prevNum + prevNum/num, prevNum/num, path + "/" + num);
				}
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
		AddOperator clz = new AddOperator();
		int[] nums = {0,2,6};
		clz.calc(nums, 7);
	
//		nums = new int[]{2,2,3};
//		clz.addExpression(nums);
//		
//		clz.addExpressionMulti("223");
		
//		clz.getMax("130");
//		clz.getMax("223");
	}
	
	
}
