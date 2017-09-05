
public class DistinctSubsequence {
	
	/**
	 * 115. Distinct Subsequences
	 * 
	 * 最直接的做法就是DFS，首先看当前字母是不是匹配，如果匹配，我们可以用s的下一个字母再来匹配t的当前字母，也可以用s的下一个字母来匹配t的下一个字母
	 * 如果当前字母不匹配，我们就只能看s的下一个字母是否匹配了。
	 * 注意递归的条件，如果t 走到结尾了，说明找到匹配了，如果s走到结尾，说明并没有匹配。
	 */
	public int numDistinctDFS(String s, String t) {
		if (s == null || t == null) return 0;
		return dfs(s, t, 0, 0);
	}
	
	private int dfs(String s, String t, int i, int j) {
		if (j == t.length()) return 1;
		if (i == s.length()) {
			return 0;
		} 
		
		int count = 0;
		if (s.charAt(i) == t.charAt(j)) {
			count = dfs(s, t, i+1, j+1) + dfs(s, t, i+1, j);
		} else {
			count = dfs(s, t, i+1, j);
		}
		return count;
	}
	
	/**
	 * 递归的缺点也很明显，就是反复计算，会超时，我们实际上可以计算结果缓存起来
	 * f[i][j] 为截止到t的第i位和s的第j位，一共有多少匹配。
	 * 初始化的时候， f[0][j] = 1  since aligning T = "" with any substring of S would have only ONE solution which is to delete all characters in S.
	 * 状态转移方程和递归的公式一样
	 * f[i+1][j+1] = f[i][j] + f[i+1][j]  当s[i]和t[j]的匹配的时候，可以都往后走，或者s往后走，t不变化
	 * 			   = f[i+1][j]            如果不匹配，只能s往后走。 
	 * 
	 * case 1). if T[i] != S[j], then the solution would be to ignore the character S[j] and align substring T[0..i] with S[0..(j-1)]. Therefore, dp[i][j] = dp[i][j-1].
	   case 2). if T[i] == S[j], then first we could adopt the solution in case 1), 
	   							 but also we could match the characters T[i] and S[j] and 
	   							 align the rest of them (i.e. T[0..(i-1)] and S[0..(j-1)]. 
	   							 As a result, dp[i][j] = dp[i][j-1] + d[i-1][j-1]

								e.g. T = B, S = ABC
								dp[1][2]=1: Align T'=B and S'=AB, only one solution, which is to remove character A in S'.
	 */
	public int numDistinctDP(String s, String t) {
        int[][] f = new int[s.length()+1][t.length()+1];
        
        for (int i = 0; i <= s.length(); i++) {
            f[i][0] = 1;
        }
        
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i-1) == t.charAt(j-1)) {
                    f[i][j] = f[i-1][j-1] + f[i-1][j];
                } else {
                    f[i][j] = f[i-1][j];
                }
            }
        }
        return f[s.length()][t.length()];
    }
	
	/**
	 * 再看两个难题，但是由于是高频题目，解法也要掌握
	 * 44. Wildcard Matching
	 *		'?' Matches any single character.
	 * 		'*' Matches any sequence of characters (including the empty sequence).
	 * 
	 * 思路差不多，就是看当前位置，s和p是不是match，如果不是就返回了，如果match，还有两种情况
	 * 1. 字母和字母或者？匹配，那么s和p都往下走
	 * 2. 字母和*匹配， 需要一个循环从当前字母一直到末尾，只要存在一个匹配就返回，因为*可以匹配任意数量的字母。
	 * 
	 * 先看DFS解法，理清思路。
	 */
	public boolean isMatch(String str, int i, String pattern, int j) {
		if (j == pattern.length()) {
			return i == str.length();
		}
//		if (i == str.length()) return false;  这个if是错的，考虑 str="", pattern = "*" 的情况，所以后面的if要检查 i < str.len
		
		boolean hasMatch = false;
		if (i < str.length() && (str.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '?')) {
			return isMatch(str, i+1, pattern, j+1);
		} else if (pattern.charAt(j) == '*') {
			while (j < pattern.length() && pattern.charAt(j) == '*') j++;
			for (; i < str.length(); i++) {
				hasMatch = isMatch(str, i, pattern, j);
				if (hasMatch) 
					return true;
			}
			return isMatch(str, i, pattern, j);  //注意，这里返回的是 ，同样是考虑 str="a", pattern = "a*" 的情况
		} else {
			return false;
		}
	}
	
	/**
	 * DFS算法思路比较清晰，但是时间复杂度太高了。所以应该考虑用DP来做。既然是判断两个string之间的关系，
	 * 那么状态转移方程应该是一个二维的数组
	 * f[i][j] 表示 str前i位和pattern前j位置是否匹配
	 * 		f[0][0] = true, f[0][j] = f[0][j - 1] && p.charAt(j - 1) == '*'
	 * 
	 * 		f[i][j] =  f[i-1][j-1],  			if s[i-1] == p[j-1] || p[j-1] == '?'
	 * 			    =  f[i][j-1] || f[i-1][j],  if p[j] == '*', 0 <= k <= i;
	 * 
	 * 最后返回 f[str.len][pattern.len]
	 * 
	 * 
	 * http://blog.csdn.net/lifajun90/article/details/10582733
	 * 
	 * Time Complexity - O(mn)， Space Complexity - O(mn)。
	 */
	public boolean isMatchDP(String str, String pattern) {
		int n = str.length(), m = pattern.length();
		if (str.length() == 0) {
			return "*".equals(pattern) || "".equals(pattern);
		}
		 
		boolean[][] f = new boolean[n+1][m+1];
		f[0][0] = true;
		//关键在于这个初始化。接下来对pattern p的首行进行初始化， 例子 aaab  *b
		//f[0][j] = f[0][j - 1] && p.charAt(j - 1) == '*'。
		//这里表示，假如f[0][j - 1]，也就是""与p.charAt(j - 2)成功match，
		//那么因为当前字符p.charAt(j - 1) = '*'可以代表空字符串，所以f[0][j]也肯定match成功
		for (int j = 1; j <= m; j++) {
		    if (pattern.charAt(j-1) == '*') 
		        f[0][j] = true;
		    else break;
		}
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (str.charAt(i-1) == pattern.charAt(j-1) || pattern.charAt(j-1) == '?') {
					f[i][j] = f[i-1][j-1];
				} else if (pattern.charAt(j-1) == '*') {
					/*
					 * res[i][j - 1] == true，即s.charAt(i - 1) match p.charAt(j - 2)，
					 * 这里'*'号可以当作""，表示0 matching，这种情况下，我们可以认为状态合理，res[i][j] = true。 
					 * 例 "C" match "C*"， i = 2， j = 2，这里C match C，'*'作为空字符串""，所以我们match成功
					 * 
					 * res[i - 1][j] == true，即s.charAt(i - 2) match p.charAt(j - 1)，
					 * 这里'*'号是被当做"s[j - 2]" + "s[j - 1]"这两个字符拼起来的字符串。因为'*'可以代表任意字符串，
					 * 所以假如res[i - 1][j] == true，那么res[i][j]也一定是true，
					 * 其实这样类推下去，res[i + 1][j]也是true，这一列都是true。
					 */
					f[i][j] = f[i][j-1] || f[i-1][j];
					
				}
			}
		}
		
		return f[n][m];
		
	}
	
	
	
	/**
	 * 10. Regular Expression Matching
	 * 还是先看DFS的解法
	 */
	public boolean isMatchRegexDFS(String s, String p) {
        return helper(s, p, 0, 0);
    }
	
	private boolean helper(String s, String p, int i, int j) {
		if (j >= p.length()) {
			return i == s.length();
		}
		
		if (j == p.length() - 1) {
			return (i == s.length() - 1) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
		}
		
		if (p.charAt(j+1) != '*') {
			if (i >= s.length()) return false;
			if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') 
				return helper(s, p, i+1, j+1);
			return false;
		}
		
		//p.charAt(j+1) == '*', next is *, means current char can appear 0 times to n times, abb, ab*
		while (i < s.length() && j < p.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.')) {
			//如果正好s的当前字母和p的当前字母匹配，我们就可以看，s后面的字母是不是也匹配
			if (helper(s, p, i, j+2)) {
				return true;
			}
			i++;
		}
		
		//当前匹配成功了，还要看后面是否匹配成功。  aabb 和 a*bb， 当前 aa 和 a*匹配了，但是还没完还要看bb和b*是否匹配
		//这里是和wildcard matching不同的地方。
		//完全不和p的当前字母匹配。
		return helper(s, p, i, j+2); 
	}
	
	/**
	 * 动态规划
	 * p[j-1] != '.' && p[j-1] != '*'：dp[i][j] = dp[i-1][j-1] && (s[i-1] == p[j-1])
	 * p[j-1] == '.'：dp[i][j] = dp[i-1][j-1]
	 * 
	 * 而关键的难点在于 p[j-1] = '*'。由于星号可以匹配0，1，乃至多个p[j-2]。
		1. 匹配0个元素，即消去p[j-2]，此时p[0: j-1] = p[0: j-3]
		dp[i][j] = dp[i][j-2]
		
		2. 匹配1个元素，此时p[0: j-1] = p[0: j-2]
		dp[i][j] = dp[i][j-2]
		
		3. 匹配多个元素，此时p[0: j-1] = { p[0: j-2], p[j-2], ... , p[j-2] }
		dp[i][j] = dp[i-1][j] && (p[j-2]=='.' || s[i-2]==p[j-2])
	 */
	public boolean isMatchRegexDP(String s, String p) {
		boolean[][] dp = new boolean[s.length()+1][p.length()+1];
		dp[0][0] = true;
		//初始化第0列，只有X*能匹配空串，如果有*，它的真值一定和p[0][j-2]的相同（略过它之前的符号）
		for (int j = 1; j <= p.length(); j++)
			dp[0][j] = j > 1 && p.charAt(j-1) == '*' && dp[0][j-2]; 
					
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 1; j <= p.length(); j++) {
				if (p.charAt(j-1) == '*') {
					dp[i][j] = dp[i][j-2] || (
							   (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.') && dp[i-1][j]);
				} else {
					dp[i][j] = dp[i-1][j-1] && (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.');
				}
			}
		}
		
        return dp[s.length()][p.length()];
    }
	
	
	public static void main(String[] args) {
		DistinctSubsequence clz = new DistinctSubsequence();
		
//		System.out.println(clz.numDistinctDFS("rabbbit", "rabbit"));
		
		String a = "ab*";
		String b = "abb";
//		System.out.println(clz.isMatchDFS(b, a));
	}
}
