import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 回文专题
 * @author tazheng
 *
 */


public class Palindromes {
	
	/**
	 * 首先，一个最基本的相当于 Utility的method, 判断一个 String 是否是 Palindrome
	 * Leetcode 125. Valid Palindrome
	 */
	public boolean isPalindrome(String s) {
		if (s == null || s.length() < 2) {
            return true;
        }
		s = s.toLowerCase();
        int left = 0, right = s.length() -1;
        while (left < right) {
        	//这里写while是因为leetcode题目要求忽略空格符号
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                } 
                left++;
                right--;
            }
        }
        return true;
	}
	
	/**
	 * 9. Palindrome Number  注意，不用完全调转数字，转到一半就好了
	 */
	public boolean isPalindromeNumber(int x) {
	    if (x<0 || (x!=0 && x%10==0)) return false;
	    int rev = 0;
	    while (x>rev){
	    	rev = rev*10 + x%10;
	    	x = x/10;
	    }
	    return (x==rev || x==rev/10);
	}
	
	/**
	 * 266. Palindrome Permutation
	 * 判断一个string是不是回文的变体 "code" -> False, "aab" -> True, "carerac" -> True.
	 */
	public boolean canPermutePalindrome(String s) {
		if (s == null || s.length() == 0) return true;
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (!set.add(c)) {
                set.remove(c);
            }
        }
        return set.size() <= 1;
    }
	
	/**
	 * 267. Palindrome Permutation II
	 * 生成所有的palindrome permutation
	 * For example: Given s = "aabb", return ["abba", "baab"].  Given s = "abc", return [].
	 * 这里巧妙的利用map来做DFS，每次选一个字符后，把对应的count减少。
	 */
	public List<String> generatePalindromes(String s) {
        List<String> res = new ArrayList<String>();
        Map<Character, Integer> map = new HashMap<>();
        int odd = 0;
        for (char c : s.toCharArray()) {
        	int count = 1;
        	if (map.containsKey(c)) {
        		count = map.get(c) + 1;
        	}
        	map.put(c, count);
        	if (count % 2 == 0) odd--;
        	else odd++;
        }
        
        if (odd > 1) return res;
        
        String mid = "";
        int len = 0;
        for (char c : map.keySet()) {
        	if (map.get(c) % 2 == 1) { 
        		mid += c;
        	}
        	map.put(c, map.get(c)/2);
        	len += map.get(c);
        }
        
        permutation(map, mid, res, "", len);
        System.out.println(Arrays.toString(res.toArray()));
        return res;
    }
	
	private void permutation(Map<Character, Integer> map, String mid, List<String> res, String path, int len) {
		if (path.length() == len) {
			res.add(path + mid + new StringBuilder(path).reverse());
			return;
		}
		
		for (char c : map.keySet()) {
			if (map.get(c) > 0) {
				map.put(c, map.get(c) - 1);
				permutation(map, mid, res, path + c, len);
				map.put(c, map.get(c) + 1);
			}
		}
	}
	
	/**
	 * 409. Longest Palindrome
	 * Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.
	 * This is case sensitive, for example "Aa" is not considered a palindrome here.
	 * "abccccdd"   Output: 7   One longest palindrome that can be built is "dccaccd", whose length is 7.
	 * 
	 * 有了上面的266做铺垫，其实题目就很容易了，还是用一个set，最后set里剩下的元素都是不能组成回文的元素。用s的长减去set剩下的大小，加上1就好了。（因为set其实可以拿一个出来，如果有的话)
	 */
	public int longestPalindrome(String s) {
		Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (set.contains(c)) set.remove(c);
            else set.add(c);
        }
        return s.length() - (set.size() == 0 ? 0 : set.size() - 1);
	}
	
	/**
	 * 以上都是简单题，没有什么技巧，下面开始中等题目和难题，这里有一些通用的技巧可以使用。
	 * 基本上就是构建一个二维数组，存字符串 i->j 的位置是不是回文。
	 */
	/**
	 * Leetcode 5. Longest Palindromic Substring
	 * 遇到这种 longest 的题目，就要看看是不是可以DP做
	 * 暴力破解方法就是做两重循环，对于每一个长度的substring都判断是不是palindrom，O(n^3)
	 * 用DP 可以降复杂度。
	 * 
	 * DP的关键在于建立一个数组
	 * p[i][j] 表示字符串从 i -> j 是不是回文
	 * 		p[i][i] = true;
	 * 		p[i][j] = p[i+1][j-1] && s[i] == s[j] (i < j)
	 * 		对于i,j来说，如果s[i] == s[j]， 而且他们围成的子串p[i+1][j-1]是回文
	 * 		那么他们也是回文 (注意 i-j <2, 也就是i,j 相邻的时候也是）
	 * 
	 * Time O(n^2)， Space O(n^2)
	 */
	public String longestPalindromeSubstring(String s) {
		int len = s.length();
		boolean[][] p = new boolean[len][len];
		for (int i = 0; i < len; i++) {
			p[i][i] = true;
		}
		int start = 0, end = 0, max = 0;
		//注意 i要从大到小，一定。
		for (int i = len - 1; i >= 0; i--) {
			for (int j = i + 1; j < len; j++) {
				if (s.charAt(j) == s.charAt(i)) {
					if (p[i + 1][j - 1] || j - i < 2) {
						p[i][j] = true;
						//以上为核心代码，下边根据题目变换
						if (max < j - i + 1) {
							max = j - i + 1;
							start = i;
							end = j;
						}
					}
				}
			}
		}

		return s.substring(start, end + 1);
	}

	/**
	 * 516. Longest Palindromic Subsequence
	 * Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.
	 * Input "bbbab",  output 4, "bbbb"
	 * Input "cbbd", output 2, "bb"
	 * 
	 * subsequnece 和下一题 substring的不同，就是substring要连续，subsequence可以跳过一些字母。
	 * 所以这里p[i][j] 不再记录True\False,而是数字，i到j位置最大组成的回文子序列的大小，状态转移方程差不多
	 */
	public int longestPalindromeSubseq(String s) {
		if (s == null || s.length() == 0) return 0;
        int len = s.length();
        int[][] p = new int[len][len];
        for (int i = 0; i< len; i++) {
        	p[i][i] = 1;
        }
        
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i+1; j < len; j++) {
                if (s.charAt(j) == s.charAt(i)) {
                    p[i][j] = p[i+1][j-1] + 2;
                } else {
                    p[i][j] = Math.max(p[i+1][j], p[i][j-1]);
                }
            }
        }
        return p[0][len-1];
    }
	
	
	/**
	 * Find all distinct palindromic sub-strings of a given string
	 * 受到上面（Longest Palindromic Substring）的启发，我们可以同样建立一个
	 * 二维数组，表明 i->j 是不是一个 palindrome, 
	 * 
	 * 如果不要输出组合，只要计算到底有多少个
	 * http://www.geeksforgeeks.org/count-palindrome-sub-strings-string/
	 */
	public void findPalindromes(String s) {
		int len = s.length();
		boolean[][] p = new boolean[len][len];
		for (int i = 0; i < len; i++) {
			p[i][i] = true;
		}
		
		HashSet<String> result = new HashSet<String>();
		for (int i = len - 1; i >= 0; i--) {
			for (int j = i + 1; j < len; j++) {
				if (s.charAt(j) == s.charAt(i)) {
					if (p[i + 1][j - 1] || j - i < 2) {
						p[i][j] = true;
						result.add(s.substring(i, j + 1));
					}
				}
			}
		}
	
		for (String res : result) {
			System.out.print(res + ", ");
		}
		System.out.println();
	}
	
	
	/**
	 * 131. Palindrome Partitioning
	 * Given a string s, partition s such that every substring of the partition is a palindrome.
	 * For example, given s = "aab", Return [["aa","b"],["a","a","b"]]
	 * 要返回所有的组合，就只能做DFS了，然而同样可以用DP来构建二维数组，优化时间。  最坏情况 aaaa.... ,有2^n个组合，所以复杂度O（2^n)
	 * 
	 * T(n)=T(n-1)+T(n-2)+..+T(1)
	   T(n+1)=T(n)+T(n-1)+..+T(1)
	   T(n+1)=2T(n)
	   T(n)=2^n
	 */
	public List<List<String>> partition(String s) {
		List<List<String>> res = new ArrayList<>();
		int len = s.length();
		boolean[][] p = new boolean[len][len];
		for (int i = 0; i < len; i++) {
			p[i][i] = true;
		}
		
		for (int i = len - 1; i >= 0; i--) {
			for (int j = i + 1; j < len; j++) {
				if (s.charAt(j) == s.charAt(i)) {
					if (p[i + 1][j - 1] || j - i < 2) {
						p[i][j] = true;
					}
				}
			}
		}
		
		dfs(s, 0, p, res, new ArrayList<String>());
		return res;
	}
	
	private void dfs(String s, int pos, boolean[][] p, List<List<String>> res, List<String> path) {
		if (pos == s.length()) {
			res.add(new ArrayList<String>(path));
			return;
		}
		for (int i = pos; i < s.length(); i++) {
			if (!p[pos][i]) continue;
			path.add(s.substring(pos, i+1));
			dfs(s, i+1, p, res, path);
			path.remove(path.size() - 1);
		}
		
	}

	/**
	 * 132. Palindrome Partitioning II - minCut
	 * Given a string s, partition s such that every substring of the partition is a palindrome.
	 * Return the minimum cuts needed for a palindrome partitioning of s.
	 * For example, given s = "aab",
	 * Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.
	 * 同样还是借助二维数组来。。然后再一次动态规划 Time O(N^2)
	 */
	public int minCut(String s) {
        int len = s.length();
        if(len <= 1) return 0;
        
        boolean[][] p = new boolean[len][len];
        for (int i = len-1; i >= 0; i--) {
        	p[i][i] = true;
        	for (int j = i+1; j < len; j++) {
        		if (s.charAt(i) == s.charAt(j) && (j - i < 2 || p[i+1][j-1])) {
        			p[i][j] = true;
        		}
        	}
        }
        
        int[] f = new int[len+1];
        for (int i = 1; i <= len; i++) {
        	f[i] = Integer.MAX_VALUE;
        	for (int j = 0; j < i; j++) {
        		if (p[j][i-1]) {
        			f[i] = Math.min(f[i], f[j]+1);
        		}
        	}
        }
        return f[s.length()]-1;
	}
	
	
	/**
	 * 然后看一道更难的题目， 336. Palindrome Pairs
	 * Given a list of unique words, find all pairs of distinct indices (i, j) 
	 * in the given list, so that the concatenation of the two words, 
	 * i.e. words[i] + words[j] is a palindrome.
	 * 
	 * 1. 简单的双重循环会超时，这里使用一个hashmap来记录每个词的位置，对于每个词，拆成两半 S = AB, 如果 A是回文，
	 * 而且map里面存在B的翻转词B'， 那么，我们就能构成  B'AB， 同理，如果B是回文，map里存在A的翻转A'， 能构成 A'BA。
	 * 注意，为了避免重复，第二次出现空的要跳过。同时注意index的顺序。
	 * 
	 * 	  O(n*k^2) java solution with Trie structure (n: total number of words; k: average length of each word)
	 * 
	 * 2. Trie的解法参照Trie的总结。 TrieSummary.java
	 * 
	 * @param args
	 */
	public List<List<Integer>> palindromePairs(String[] words) {
		List<List<Integer>> result = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        
        for (int i = 0; i < words.length; i++) {
        	for (int j = 0; j <= words[i].length(); j++) {
        		String str1 = words[i].substring(0, j);
        		String str2 = words[i].substring(j);
        		if (isPalindrome(str1)) {
        			String reverse = new StringBuilder(str2).reverse().toString();  
        			if (map.containsKey(reverse) && map.get(reverse) != i) {
        				result.add(Arrays.asList(map.get(reverse), i)); //注意顺序, STR2' STR1STR2, 所以str2' 位置在前面
        			}
        		}
        		if (isPalindrome(str2) && str2.length() != 0) {//为了避免重复，
        			String reverse = new StringBuilder(str1).reverse().toString();
        			if (map.containsKey(reverse) && map.get(reverse) != i) {  
        				result.add(Arrays.asList(i, map.get(reverse))); //注意顺序 STR1STR2 STR1', 所以str1的位置在前面
        			}
        		}
        	}
        }
        
        return result;
    }
	
	
	/**
	 * 214. Shortest Palindrome
	 * 找到以s.charAt(0)开始的最长的palindrome，然后再用s.length()减去这个长度，就是我们需要添加的字符长度。这时候我们再在原String之前添加就可以了。
		假设s = "bcba"，那么以s.charAt(0)为左边界的longest palindrome是"bcb"，需要添加的是"a"，返回"abcba"。
		假设s = "bba"，那么以s.charAt(0)为左边界的longest palindrome是"bb"，需要添加的是"a"，返回"abba"。
		假设s = "bcd"，那么以s.charAt(0)为左边界的longest palindrome长度为"b"，需要添加的是"cd"，返回"dcbcd"。
	 */
	public String shortestPalindrome(String s) {
		int[] p = new int[s.length()];
		
		
		return null;
	}
	
	public static void main(String[] args) {
		Palindromes clz = new Palindromes();
		System.out.println(clz.longestPalindromeSubseq("cbba"));
		clz.findPalindromes("abaab");
		clz.generatePalindromes("aabb");
		clz.palindromePairs(new String[]{"aa", "baa"});
	}
}
