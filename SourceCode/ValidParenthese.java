import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ValidParenthese {
	
	/**
	 * 20. Valid Parentheses
	 * 属于基本功题目。
	 */
	public boolean isValid(String s) {
        if (s == null) {
            return true;
        }
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) 
                	return false;
                if (!match(c, stack.pop()))
                    return false;
            }
        }
        return stack.isEmpty();  //注意最后返回的是是否空
    }
	
	 /**
	    * This function determines if the braces ('(' and ')') in a string are properly matched.
	    * it ignores non-brace characters.
	    * Some examples:
	    * "()()()()"   -> true
	    * "((45+)*a3)" -> true
	    * "(((())())"  -> false
	    */
	private boolean isValidNoExtraSpace(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(' ) {
                count++;
            } else if (str.charAt(i) == ')'){
                if (count == 0) return false;
                count--;
            }
        }
        
        return count == 0;
    }
	
    private boolean match(char right, char left) {
        if (left == '(') return right == ')';
        if (left == '{') return right == '}';
        if (left == '[') return right == ']';
        return false;
    }
    
    /**
	 * input could include ":(" frown or ":)" smileys
	 * check if the input is parenthese balance
	 */
	public boolean validParenthesesSmile(String s) {
		
		boolean emotion = false;
//		int smile = 0, frown = 0;
//		int open = 0, close = 0;
//		for (char c : s.toCharArray()) {
//			if (c == '(') {
//				if (emotion) {
//					frown++;
//				}
//				open++;
//			} else if (c == ')') {
//				if (emotion) {
//					smile++;
//				}
//				close++;
//			} 
//			if (c == ':') {
//				emotion = true;
//			} else {
//				emotion = false;
//			}
//			
//			if (close > open + smile) {
//				return false;
//			}
//		}
//		
//		return open - frown == close;
		
		int count = 0;
		for (char c : s.toCharArray()) {
			if (c == '(') {
				if (!emotion)
					count++;
			} else if (c == ')') {
				if (!emotion) {
					if (count == 0) return false;
					count--;
				}
			}
			
			if (c == ':') emotion = true;
			else emotion = false;
		}
		return count == 0;
	}
    
    /**
     * Remove invalid parentheses - only one res
     */
    public void removeInvalidOne(String str) {
    	
    	String orig = str;
    	
    	int count = 0;
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < str.length(); i++) {
    		char c = str.charAt(i);
    		sb.append(c);
    		if (c == '(') {
    			count++;
    		} else if (c == ')') {
    			if (count == 0) {
    				sb.deleteCharAt(sb.length()-1);
    			} else {
    				count--;
    			}
    			
    		}
    	}
    	count = 0;
    	str = sb.toString();
    	sb = new StringBuilder();
    	for (int i = str.length() - 1; i >= 0; i--) {
    		char c = str.charAt(i);
    		sb.append(c);
    		if (c == ')') {
    			count++;
    		} else if (c == '(') {
    			if (count == 0) {
    				sb.deleteCharAt(sb.length()-1);
    			} else {
    				count--;
    			}
    		}
    	}
    	
    	System.out.println(orig + " -> " + sb.reverse().toString());
    }
    
    
    /**
     * 22. Generate Parentheses
     * 给一个数字，生成合法的括号组合。无论BFS还是DFS都是套用模板
     * 开始是空字符串""，然后在每个位置都插入一对字符串 "()", 然后在每个位置都插入 "()()", "(())", "()()"
     * 如此反复
     *  Time: C(2 * n, n) / (n + 1) * n, that's roughly O(n!). 
     */
    public List<String> generateParenthesisBFS(int n) {
    	List<String> result = new LinkedList<String>();
        if (n == 0) return result;
        result.add("");
        for (int i = 0; i < n; i++) {
        	int size = result.size();
        	for (int j = 0; j < size; j++) {
        		String str = result.remove(0);
        		for (int k = 0; k <= str.length(); k++) {
        			String nextStr = str.substring(0,k) + "()" + str.substring(k);
        			if (result.contains(nextStr))
        				continue;
        			result.add(nextStr);
        		}
        		
        	}
        }
        return result;
    }
    
	public List<String> generateParenthesisDFS(int n) {
		ArrayList<String> res = new ArrayList<>();
		generate(res, "", n, n);
		return res;
	}

	public void generate(ArrayList<String> res, String s, int l, int r) {
		if (l == 0 && r == 0) {
			res.add(s);
			return;
		}
		if (l > 0)
			generate(res, s + "(", l - 1, r);
		if (r > l)
			generate(res, s + ")", l, r - 1);
	}
    
	/**
	 * 301. Remove Invalid Parentheses
	 * "()())()" -> ["()()()", "(())()"]
	 * "(a)())()" -> ["(a)()()", "(a())()"]
	 * ")(" -> [""]
	 * 
	 * T(n) = n x C(n, n) + (n-1) x C(n, n-1) + ... + 1 x C(n, 1) = n x 2^(n-1).
	 * 
	 * Followup： 如果我不仅能减，而且还能加，怎么办？只要最小操作就好。
	 */
	public List<String> removeInvalidParentheses(String s) {
		List<String> result = new ArrayList<String>();
		Queue<String> queue = new LinkedList<String>();
		HashSet<String> visited = new HashSet<String>();
		queue.offer(s);
		visited.add(s);
		boolean found = false;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				String curr = queue.poll();
				if (isValid(curr)) {
					found = true;
					result.add(curr);
				}
				if (found) continue;
				for (int j = 0; j < curr.length(); j++) {
					if (curr.charAt(j) != '(' && curr.charAt(j) != ')') 
						continue;
					String nextStr = curr.substring(0, i) + curr.substring(i+1);
					if (visited.contains(nextStr)) continue;
					queue.offer(nextStr);
					visited.add(nextStr);
				}
			}
			
		}
		
		return result;
	}
	
	/**
	 * BFS的算法非常容易理解和记忆，也能通过leetcode的测试。但是事实上还有更好的DFS解法。
	 * http://blog.csdn.net/qq508618087/article/details/50408894
	 * 
	 * 在任何时候如果')'的个数多于左括号，则说明从开始到现在位置必然可以删除一个')'。
	 * 而这段子串可能包含多个')'，删除哪一个呢？当然删除任何一个都可以，解法来自leetcode discussion
	 */
	public List<String> removeInvalidParenthesesDFS(String s) {
	    List<String> ans = new ArrayList<>();
	    remove(s, ans, 0, 0, new char[]{'(', ')'});
	    return ans;
	}
	
	public void remove(String s, List<String> ans, int last_i, int last_j,  char[] par) {
	    for (int stack = 0, i = last_i; i < s.length(); ++i) {
	        if (s.charAt(i) == par[0]) stack++;
	        if (s.charAt(i) == par[1]) stack--;
	        if (stack >= 0) continue;
	        for (int j = last_j; j <= i; ++j)
	            if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1]))
	                remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
	        return;
	    }
	    String reversed = new StringBuilder(s).reverse().toString();
	    if (par[0] == '(') // finished left to right
	        remove(reversed, ans, 0, 0, new char[]{')', '('});
	    else // finished right to left
	        ans.add(reversed);
	}
	
	
	/**
	 * 32. Longest Valid Parentheses
	 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
	 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
	 * 
	 * 这题和上面的很类似，但是注意一点，这里找的是substring，也就是要连在一起。
	 * 而且是找长度，这样就很有可能是DP解法了。 简单的DP解法复杂度n^3, 会超时
	 */
	public int longestValidParenthesesTLE(String s) {
		if (s == null || s.length() < 2) {
            return 0;
        }    
        
        int[] f = new int[s.length() + 1];
        f[0] = 0;
        
        int max = 0;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i-1; j++) {
                String str = s.substring(j, i);
                if (isValid(str)) {
                    f[i] = Math.max(f[i], f[j] + i-j);
                    max = Math.max(max, f[i]);
                }
            }
        }
        
        System.out.println(Arrays.toString(f));
        return max;
	}
	
	/**
	 * 二维的DP
	 * s[i] = '('：  DP[i] = 0
	 *  s[i] = ')'：找i前一个字符的最长括号串DP[i]的前一个字符j = i-2-DP[i-1]
	 *  	DP[i] = DP[i-1] + 2 + DP[j]，如果j >=0，且s[j] = '('
	 *  	DP[i] = 0，如果j<0，或s[j] = ')'
	 *  
	 *  And the DP idea is :

If s[i] is '(', set longest[i] to 0,because any string end with '(' cannot be a valid one.

Else if s[i] is ')'

     If s[i-1] is '(', longest[i] = longest[i-2] + 2

     Else if s[i-1] is ')' and s[i-longest[i-1]-1] == '(', longest[i] = longest[i-1] + 2 + longest[i-longest[i-1]-2]

For example, input "()(())", at i = 5, longest array is [0,2,0,0,2,0], longest[5] = longest[4] + 2 + longest[1] = 6.
	 */
	public int longestValidParenthesesDP(String s) {
		int[] f = new int[s.length()+1];
		int max = 0;
		for (int i = 1; i <= s.length(); i++) {
			int j = i-2-f[i];
			if (j < 0 || s.charAt(j) == ')' || s.charAt(i-1) == '(') {
				continue;
			}
			f[i] = f[i-1] + 2 + f[j];
			max = Math.max(f[i], max);
		}
		return max;
	}

	
	/**
	 * 还能继续优化，成为O(n)的解法。
	 * 基本思路就是维护一个栈，遇到左括号就进栈，遇到右括号则出栈，并且判断当前合法序列是否为最 长序列。不过这道题看似思路简单，
	 * 但是有许多比较刁钻的测试集。具体来说，主要问题就是遇到右括号时如何判断当前的合法序列的长度。比较健壮的方式如下：
		(1) 如果当前栈为空，则说明加上当前右括号没有合法序列（有也是之前判断过的）；
		(2) 否则弹出栈顶元素，
			如果弹出后栈为空，则说明当前括号匹配，我们会维护一个合法开始的起点start，合法序列的长度即为当前元素的位置 i-start+1；
			如果栈内仍有元素，则当前合法序列的长度为当前栈顶元素的位置下一位到当前元素的距离，因为栈顶元素后面的括号对肯定是合法的，而且左括号出过栈了。
		
		因为只需要一遍扫描，算法的时间复杂度是O(n)，空间复杂度是栈的空间，最坏情况是都是左括号，所以是O(n)。
		一个更容易理解的办法 https://discuss.leetcode.com/topic/2289/my-o-n-solution-using-a-stack/2
		
		The workflow of the solution is as below.

Scan the string from beginning to end.
If current character is '(',
push its index to the stack. If current character is ')' and the
character at the index of the top of stack is '(', we just find a
matching pair so pop from the stack. Otherwise, we push the index of
')' to the stack.
After the scan is done, the stack will only
contain the indices of characters which cannot be matched. Then
let's use the opposite side - substring between adjacent indices
should be valid parentheses.
If the stack is empty, the whole input
string is valid. Otherwise, we can scan the stack to get longest
valid substring as described in step 3.
	 */
	public int longestValidParenthesesStack(String s) {
		Stack<Integer> stack = new Stack<>();
		int max = 0;
		int accumulatedLen = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				stack.push(i);
			} else {
				if (stack.isEmpty()) {
					accumulatedLen = 0;
				} else {
					int matchPos = stack.pop();
					int matchLen = i - matchPos + 1;
					if (stack.isEmpty()) {
						accumulatedLen += matchLen;
						matchLen = accumulatedLen;
					} else {
						matchLen = i - stack.peek();
					}
					max = Math.max(matchLen, max);
				}
			}
		}
		return max;
	}

	
	/**
	 * 241. Different Ways to Add Parentheses
	 * 这题题目虽然带着括号两个字，但是实际上并不是括号题目。
	 */
	public List<Integer> diffWaysToCompute(String input) {
		List<Integer> result = new ArrayList<Integer>();
		if (input.length() == 0) 
			return result;
		
		for (int i = 1; i < input.length(); i++) {
			if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*') {
				List<Integer> left = diffWaysToCompute(input.substring(0, i));
				List<Integer> right = diffWaysToCompute(input.substring(i+1));
				for (int l : left) {
					for (int r : right) {
						result.add(calc(l,r,input.charAt(i)));
					}
				}
			}
		}
		if (result.size() == 0) {  //means number
			result.add(Integer.valueOf(input));
		}
		return result;
	}
	private int calc(int l, int r, char op) {
		switch (op) {
			case '+' : return l+r;
			case '-' : return l-r;
			case '*' : return l*r;
		}
		return 0;
	}
	
	
	
	
	
    public static void main(String[] args) {
    	ValidParenthese clz = new ValidParenthese();
    	System.out.println(clz.isValid("()"));
    	clz.longestValidParenthesesTLE("()(()");
    	
    	clz.removeInvalidOne("(a)()");
    	clz.removeInvalidOne("((bc)");
    	clz.removeInvalidOne(")))a((");
    	clz.removeInvalidOne("(a(b)");
    	
    	System.out.println(clz.validParenthesesSmile("((:())"));
    	System.out.println(clz.validParenthesesSmile("(:)()"));
    	System.out.println(clz.validParenthesesSmile("(::))"));
    	
    	clz.removeInvalidParenthesesDFS("())");
    	
    }
}
