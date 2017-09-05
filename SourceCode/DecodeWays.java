import java.util.HashMap;
import java.util.Map;

public class DecodeWays {
	/**
	 * 91. Decode Ways 首先用递归来写, 思路直接参照代码，但是由于递归效率比较低，对于大数据来说会超时
	 * 因为如果数据很长的话，递归栈会特别大。
	 */
	public int numDecodingsRecursive(String s) {
		int count = 0;
		if (s.length() <= 1) {
			return "0".equals(s) ? 0 : 1;
		}

		char c = s.charAt(0);
		if (c > '0' && c <= '9') {
			count += numDecodingsRecursive(s.substring(1));
		}

		if (s.length() > 1) {
			char c2 = s.charAt(1);
			int number = (int) (c - '0') * 10 + (int) (c2 - '0');
			if (number >= 10 && number <= 26) {
				count += numDecodingsRecursive(s.substring(2));
			}
		}
		return count;
	}

	/**
	 * Followup 1: 优化递归
	 * 
	 * 我们看到，实际上递归 比如 1234 ， 1的时候解码了234，然后34，然后 4，继续走到2，在这里 又重新解码了 34,
	 * 4，导致多次重复。所以我们可以用动态规划来存之前解码过的结果，这样就不用 反复计算。
	 * 
	 * 状态转移方程 f[i] = number of decode ways from 0 to i-1; f[0] = f[1] = 1 ,
	 * 注意，初始化的时候 f[0]是1，因为当f(2) 有效的时候，f(2)会加上f(0)的值 然后就很简单了，循环从2开始，遵守下面的规则
	 * 
	 * num = s[i-1]*10+s[i]
	 * 
	 * f(i) = f(i-2) + f(i-1), 10 <= num <= 26 = f(i-1), num < 10 or num >26
	 * 
	 * 这样做的时间复杂度是 O(n), 空间复杂度是 O(n)
	 */
	public int numDecodings(String s) {
		int[] f = new int[s.length() + 1];
		f[0] = 1;
		f[1] = s.charAt(0) == '0' ? 0 : 1;

		for (int i = 2; i <= s.length(); i++) {
			char c = s.charAt(i - 1);
			if (c > '0' && c <= '9') {
				f[i] += f[i - 1];
			}
			int c2 = s.charAt(i - 2);
			int number = (int) (c2 - '0') * 10 + (int) (c - '0');
			if (number >= 10 && number <= 26) {
				f[i] += f[i - 2];
			}
		}

		return f[s.length()];
	}
	
	/**
	 * Followup 2: 能不能用O(1) 的空间来解决问题
	 */
	public int numDecodingsOpt(String s) {
		if (s == null || s.length() == 0 || s.charAt(0) == '0') {
			return 0;
		}
		int f2 = 1; // two digit
		int f1 = 1; // one digit
		for (int i = 2; i <= s.length(); i++) {
			int curr = 0;
			char c = s.charAt(i - 1);
			if (c > '0' && c <= '9') {
				curr += f1;
			}
			int c2 = s.charAt(i - 2);
			int number = (int) (c2 - '0') * 10 + (int) (c - '0');
			if (number >= 10 && number <= 26) {
				curr += f2;
			}
			f2 = f1;
			f1 = curr;
		}

		return f1; //注意，这里要返回的是f1,因为f1最后被更新了
	}
	
	/**
	 * Followup 3: find all decode ways
	 * 
	 */
	String[] code = {"", "A", "B", "C", "D", "E", "F","G",
				     "H","I","J","K","L","M","N","O","P",
				     "Q","R","S","T","U","V","W","X","Y","Z"};
	public void findAllDecodeWays(String s, String path) {
		if (s.length() == 0) {
			System.out.println(path);
			return;
		}

		char c = s.charAt(0);
		if (c > '0' && c <= '9') {
			findAllDecodeWays(s.substring(1), path + code[c-'0']);
		} 
		//如果有两位
		if (s.length() > 1) {
			char c2 = s.charAt(1);
			int number = (int) (c - '0') * 10 + (int) (c2 - '0');
			if (number >= 10 && number <= 26) {
				findAllDecodeWays(s.substring(2), path + code[number]);
			}
		}
	}

	/**
	 * Followup 4:
	 * 如果 ABC 不对应1，2，3了，我给你一个map，去map其他数字
	 * 比如 a->13 b->51 c->55, 但是范围小于 100，怎么办
	 * 
	 * 
	 * int fn(string s  hashmap<char,int> table)
		我们把table里面的int装进一个hashset里就行了， valid的helper函数更简单  
	 */
	public int numDecodingsCustomMap(String s, Map<Integer, Character> map) {
		if (s == null || s.length() == 0 || s.charAt(0) == '0') {
			return 0;
		}
		int f2 = 1; // two digit
		int f1 = 1; // one digit
		for (int i = 2; i <= s.length(); i++) {
			int curr = 0;
			if (isValid(s.substring(i-1, i), map)) {
				curr += f1;
			}
			if (isValid(s.substring(i-2, i), map)) {
				curr += f2;
			}
			f2 = f1;
			f1 = curr;
		}

		return f1; //注意，这里要返回的是f1,因为f1最后被更新了
	}
	
	public int numDecodingsCustomMapAnyDigit(String s, Map<Integer, Character> map) {
		if (s == null || s.length() == 0 || s.charAt(0) == '0') {
			return 0;
		}
		int[] f = new int[s.length()+1];
		f[0] = 1;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 1; j <= i; j++) {
				if (isValid(s.substring(i-j, i), map)) {
					f[i] += f[i-j];
				}
			}
		}

		return f[s.length()]; //注意，这里要返回的是f1,因为f1最后被更新了
	}
	
	private boolean isValid(String num, Map<Integer, Character> map) {
		return map.containsKey(Integer.valueOf(num));
	}
	
	private boolean isValid(String str) {
		if (str.charAt(0) == '0') {
            return false;
        }
		int num = Integer.valueOf(str);
		return num > 0 && num <= 26;
	}

	/**
	 * Followup 5:  如果我的string可以有， * 代表任意字符
	 * 	 比如 1*2，可以有  102 （1），112 （3）， 122（3），132(2), 142(2), 152(2), 162(2), 172(2) 182(2) 192 (2)
	 * 
	 *   比如 2*2，可以有  202 （1），212 （3）， 222（3），232(2), 242(2), 252(2), 262(2), 272(1) 282(1) 292 (1)
	 */
	public int decodeWithStar(String s) {
		if (s == null || s.length() == 0 || s.charAt(0) == '0') {
			return 0;
		}
		int f2 = 1; // two digit
		int f1 = s.charAt(0) == '*'? 9 : 1; // one digit
		for (int i = 2; i <= s.length(); i++) {
			int curr = 0;
			char c = s.charAt(i-1);
			char d = s.charAt(i-2);
			if (c == '0') {
				curr = 0;
			} else if (c != '*') {
				curr += f1;
				if (d != '*') {
					int num = d*10 + c;
					if (num >= 10 && num <= 26) curr += f2;
				} else {
					if (c >= '0' && c <= '6') {
						curr += f2 * 2;
					} else {
						curr += f2;
					}
				}
			} else { //c is '*'
				curr += f1 * 9;
				if (d == '1') curr += f2 * 10;
				else if (d == '2') curr += f2 * 7;
				else curr += f2 * 17;
 			}
			
			f2 = f1;
			f1 = curr;
		}
		return f1;
	}
	
	private static final int[] INT_ZERO_TO_SIX = {0, 1, 2, 3, 4, 5, 6};
	private static final int[] INT_ZERO_TO_NINE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	public int numDecodingsWithStar(String s) {
		int n = s.length();
		if (n == 0) {
			return 0;
		}
		// c[i] is number of ways of decoding corresponding to number i
		int[] c1 = new int[10];
		c1[0] = 1; // so that when iterate c1, we have one 1	
		int[] c2 = new int[10];
		
		if (s.charAt(0) != '0') {
			if (s.charAt(0) == '*') {
				for (int i = 1; i <= 9; i++) {
					c2[i] = 1;
				}
			} else {
				c2[s.charAt(0) - '0'] = 1;
			}
		}
		for (int i = 2; i <= n; i++) {
			int[] curr = new int[10];
			char s1 = s.charAt(i - 2);
			char s2 = s.charAt(i - 1);
			if (s2 >= '1' && s2 <= '9') {
				for (int j = 0; j <= 9; j++) {
					curr[s2 - '0'] += c2[j];
				}
			} else if (s2 == '*') {
				for (int k = 1; k <= 9; k++) {
					for (int j = 0; j <= 9; j++) {
						curr[k] += c2[j];
					}
				}
			}

			if ((s1 == '*' || s1 == '1' || s1 == '2') && ((s2 <= '6' && s2 >= '0') || s2 == '*')) {
				int n1 = 1;
				if (s1 == '*') {
					n1 = 2;
				}
				int[] arr2 = { s2 - '0' };
				if (s2 == '*') {
					if (s1 == '2') {
						arr2 = INT_ZERO_TO_SIX;
					} else {
						arr2 = INT_ZERO_TO_NINE;
					}
				}
				for (int j = 0; j <= 9; j++) {
					for (int k : arr2) {
						curr[k] += c1[j] * n1;
					}
				}
			}
			// c1, c2 = c2, c3
			c1 = c2;
			c2 = curr;
		}
		int sum = 0;
		for (int num : c2) {
			sum += num;
		}
		return sum;
	}
	
	public static void main(String[] args) {
		DecodeWays clz = new DecodeWays();
		// System.out.println(clz.numDecodings("124"));
		System.out.println(clz.numDecodingsOpt("02"));
		
		Map<Integer, Character> map = new HashMap<>();
//		for (int i = 9; i <= 34; i++) {
//			map.put(i, (char)('A' + i-1));
//		}
		map.put(9, 'A');
		map.put(19, 'B');
		map.put(91, 'C');
		System.out.println(clz.numDecodingsCustomMap("919", map));
		
		map.clear();
		map.put(1, 'A');
		map.put(23, 'B');
		map.put(2, 'C');
		map.put(123, 'D');
		map.put(1232, 'E'); 
		map.put(12, 'F');
		map.put(32, 'G');
		System.out.println(clz.numDecodingsCustomMapAnyDigit("1232", map));
		
		
//		clz.findAllDecodeWays("1001", "");
		
		String str1 = "1*";
		System.out.println(clz.numDecodingsWithStar(str1) + "," + clz.decodeWithStar(str1));
		
		str1 = "19*3";
		System.out.println(clz.numDecodingsWithStar(str1) + "," + clz.decodeWithStar(str1));
		
		str1 = "9**3";
		System.out.println(clz.numDecodingsWithStar(str1) + "," + clz.decodeWithStar(str1));
		
		str1 = "1**9";
		System.out.println(clz.numDecodingsWithStar(str1) + "," + clz.decodeWithStar(str1));
		
		str1 = "1**3";
		System.out.println(clz.numDecodingsWithStar(str1) + "," + clz.decodeWithStar(str1));
		
		str1 = "1*0";
		System.out.println(clz.numDecodingsWithStar(str1) + "," + clz.decodeWithStar(str1));
	}
}
