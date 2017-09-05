import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreak {
	HashSet<String> dicts = new HashSet<>();
	
	/**
	 * 139. Word Break
	 * 
	 * 原始版本，只返回T和F，一般看到返回是否的问题，都能用DP解决，这里给出两种做法
	 * 
	 * For example, given
	 * s = "leetcode",  dict = ["leet", "code"].
	 * Return true because "leetcode" can be segmented as "leet code".
	 * 
	 * 使用DFS的时间复杂度证明来自于此处：
	 * https://discuss.leetcode.com/topic/3454/hi-anyone-who-knows-the-time-complexity-of-recursion-with-dfs-here-is-the-code/3
	 * The time complexity should be O(2^n).
	 * Let f(n) be the total loop times, where n is s.length() - pos, i.e. the length of the string.
	 * Given s="aaa…ab", dict=["a","aa","aaa",…"aaa…a"],
	 * f(n) = f(n-1) + f(n-2) + … + f(1)
	 * f(n-1) = f(n-2) + f(n-3) + … + f(1)
	 * …
	 * f(1) = 1  
	 * then substitute f(n-1) in f(n):
	 * f(n)  = ( f(n-2) + f(n-3) + … + f(1) ) + ( f(n-2) + f(n-3) + … + f(1) )
		     = 2 × ( f(n-2) + f(n-3) + … + f(1) )
		     = 2 × 2 × ( f(n-3) + f(n-4) + … + f(1) )
		     = 2^(n-1)
	 * And searching in the dict take O(1) time in average. So the total time is O(2^n).
	 */
	public boolean wordBreakDFS(String s, List<String> wordDict) {
		if (s == null || wordDict == null || wordDict.size() == 0) {
			return false;
		}
		dicts = new HashSet<String>();
		for (String word : wordDict) {
			dicts.add(word);
		}
		
		return DFSTrueFalse(0, s);
	}
	//这里用一个hashmap存已经搜索过的结果，避免重复搜索
	HashMap<String, Boolean> results = new HashMap<>();
	private boolean DFSTrueFalse(int pos, String str) {
		if (pos == str.length()) {
			return true;
		}
		boolean flag = false;
		for (int i = pos; i< str.length(); i++) {
			String prefix = str.substring(pos, i+1);
			if (!dicts.contains(prefix)) continue;
			String suffix = str.substring(i+1);
			if (results.containsKey(suffix)) {
				flag = results.get(suffix);
			} else {
				flag = DFSTrueFalse(i+1, str);
				results.put(suffix, flag);
			}
			if (flag) return true;
		}
		return flag;
	}
	
	/**
	 * 既然DFS那么复杂，DP就是降空间复杂度最好的办法
	 * 状态转移方程式就是  
	 * f(n) 为 0....n 是否能break
	 * f(0) = true;
	 * f(i) = f(j) && substring(i,j) in dictionary,  j = [0...i]
	 * 
	 * 最终返回 f(n), n = length of input string
	 * 两重循环搞定，时间复杂度O(n^2)，空间还是O(N)
	 */
	public boolean wordBreakDP(String s, List<String> wordDict) {
		if (s == null || wordDict == null || wordDict.size() == 0) {
			return false;
		}
		dicts = new HashSet<String>();
		for (String word : wordDict) {
			dicts.add(word);
		}
		boolean[] f = new boolean[s.length() + 1];
		f[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (f[j] && dicts.contains(s.substring(j, i))) {
					f[i] = true;
					break;
				}
			}
		}
		
		return f[s.length()];
	}
	
	/**
	 * 140. Word Break II
	 * s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"].
	 * A solution is ["cats and dog", "cat sand dog"].
	 * 
	 * 这下要输出所有的解法，就没有办法用DP了，老老实实用 2^N 的深度优先搜索
	 * 当然，还是有一些时间上的优化可以做，就是缓存已经搜索过得结果
	 */
	public List<String> wordBreak(String s, List<String> wordDict) {
		for (String str : wordDict) {
			dicts.add(str);
		}
		return DFS(s, new HashMap<String, List<String>>());
	}
	
	private List<String> DFS(String s, Map<String, List<String>> map) {
		if (map.containsKey(s)) {
			return map.get(s);
		}
		List<String> ret = new ArrayList<String>();
		if (s.length() == 0) {
			ret.add("");
			return ret;
		}
		for (int i = 0; i < s.length(); i++) {
			if (dicts.contains(s.substring(0, i+1))) {
				List<String> nextLevel = DFS(s.substring(i+1), map);
				map.put(s.substring(i+1), nextLevel);
				for (String str : nextLevel) {
					ret.add(s.substring(0, i+1) + (str.length() == 0 ? "": " " + str));
				}
			}
		}
		return ret;
	}
	
	/**
	 * 会超时的DFS
	 */
    private void helper(String s, Set<String> dict, List<String> res, List<String> path, int pos) {
        if (pos == s.length()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                sb.append(path.get(i));
                if (i != path.size() - 1) sb.append(" ");
            }
            res.add(sb.toString());
            return;
        }
        
        for (int i = pos; i < s.length(); i++) {
            if (dict.contains(s.substring(pos, i+1))) {
                path.add(s.substring(pos, i+1));
                helper(s, dict, res, path, i+1);
                path.remove(path.size()-1);
            }
        }
    }
	
	
	/**
	 * 472. Concatenated Words
	 * 看完了word break，再看一道非常类似的题目。
	 * Given a list of words (without duplicates), please write a program that returns all 
	 * concatenated words in the given list of words. A concatenated word is defined as a 
	 * string that is comprised entirely of at least two shorter words in the given array.
	 * 
	 * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
	 * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
	 * Explanation: 
	 * "catsdogcats" can be concatenated by "cats", "dog" and "cats"; 
	 * "dogcatsdog" can be concatenated by "dog", "cats" and "dog"; 
	 * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
	 * 
	 * 尽管这题可以并且推荐用Trie来做，但是考虑到这里是要总结类似题目的DP和DFS做法，所以我们用DFS来解题。
	 * 用Trie的解法可以看Trie的总结。
	 * DFS的解法和 139. Word Break 差不多，但是要注意一个区别，判断一个词要由至少两个其他词构成才行。所以要做一些小修改。
	 * 时间复杂度 O(n^2*k)， n is the average length of words and k is number of words
	 */
	public List<String> findAllConcatenatedWordsInADict(String[] words) {
		List<String> result = new ArrayList<>();
		if (words == null || words.length <= 2) return result;
		for (String str : words) {
			dicts.add(str);
		}
		for (String word : words) {
			if (isConcatenated(word, 0)) {
				result.add(word);
			}
		}
		return result;
	}
	
	private boolean isConcatenated(String str, int count) {
		if (str.length() == 0) {
			return count > 1; //need to be comprised entirely of at least two shorter words
		}
		
		for (int i = 0; i < str.length(); i++) {
			if (dicts.contains(str.substring(0, i+1))) {
				if (isConcatenated(str.substring(i+1), count+1)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		WordBreak wb = new WordBreak();
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("cat", "cats", "and", "sand", "dog"));
		
		System.out.println(wb.wordBreakDP("catsanddog", list));
		System.out.println(Arrays.toString(wb.wordBreak("catsanddog", list).toArray()));
		System.out.println(Arrays.toString(wb.
				findAllConcatenatedWordsInADict(new String[]{"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"})
				.toArray()));
		
	}
}
