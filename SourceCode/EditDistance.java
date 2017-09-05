
public class EditDistance {
	/**
	 * 161. One Edit Distance
	 * Given two strings S and T, determine if they are both one edit distance apart.
	 * 
	 * 	1. 先判断两个string长度是否相等，做一个 O(n) 的循环，看是否有不同即可。
	 *     如果不相等，那么找出多出来的字母，删掉它然后看剩下的是否相等。也是O(n)的
	 *  
	 *  2. 
	 */
	public boolean isOneEditDistance(String s, String t) {
		if (s.length() == t.length()) {
			return checkOneEdit(s, t);
		} else if (s.length() > t.length()) {
			return checkOneDelete(s, t);
		} else
			return checkOneDelete(t, s);
	}
	
	private boolean checkOneEdit(String s, String t) {
		int diff = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != t.charAt(i)) {
				if (diff > 0) return false;
				diff++;
			}
		}
		return true;
	}
	
	private boolean checkOneDelete(String a, String b) {
		if (a.length() - b.length() > 1) 
			return false;
		for (int i = 0; i < b.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return b.equals(a.substring(0,i) + a.substring(i+1, a.length()));
			}
		}
		return true;
	}
	
	/**
	 * 当然，一遍循环就够了
	 */
	public boolean isOneEditDistanceOnepass(String s, String t) {
		if (s == null && t == null) {
			return true;
		}
		if (s == null && t.length() > 1)
			return false;
		if (t == null && s.length() > 1)
			return false;

		for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
			if (s.charAt(i) != t.charAt(i)) {
				if (s.length() == t.length())
					return s.substring(i + 1).equals(t.substring(i + 1));
				else if (s.length() < t.length()) 
					return s.substring(i).equals(t.substring(i + 1));
				else 
					return t.substring(i).equals(s.substring(i + 1));
			}
		}
		return Math.abs(s.length() - t.length()) == 1;
	}
	
	/**
	 * Follow up “一个编辑距离”的变体，但其实这题有点难度，输入没有给两个字符串，而是两个自定义的 interface,
	 * 里面只有 next()一个方法，next()返回0的时候就是 end, 我的做法是再实现一个 peek()方法, 
	 * 然后调用这两个方法的时候有点绕晕了，最后差点没写完，不过还是写完了，面试官说是 work 的
	 * 
	 * 关键问题就是现在不知道word的长度了。
	 */
	public boolean isOneEditDistanceFollowup(WordStream s, WordStream t) {
		int diff = 0;
		
		while (s.hasNext() || t.hasNext()) {
			char ps = s.next();
			char pc = s.next();
			if (ps == pc) {
				continue;
			}
			
			diff++;
			if (diff > 1) return false;
			if (ps == t.peek()) {
				t.next();
			} else if (pc == s.peek()) {
				s.next();
			} 
		}

		return diff == 1;  //注意返回，否则""  "" 会出错
	}
	
	class WordStream {
		String word;
		int index;
		public WordStream(String w) {
			this.word = w;
			index = 0;
		}
		public char next() {
			if (!hasNext()) 
				return 0;
			return word.charAt(index++);
		}
		
		public char peek() {
			if (!hasNext()) 
				return 0;
			return word.charAt(index);
		}
		
		public boolean hasNext() {
			return index < word.length();
		}
	}
	
	/**
	 * 72. Edit Distance (H)
	 *  Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
	 *  You have the following 3 operations permitted on a word:
	 *  a) Insert a character
	 *  b) Delete a character
	 *  c) Replace a character
	 *  
	 *  我们先来看DFS的操作。
	 */
	int[][] cache;
	public int minDistance(String word1, String word2) {
		cache = new int[word1.length()][word2.length()];
		return minDistance(word1, word2, 0, 0);
	}
	
	public int minDistance(String word1, String word2, int pos1, int pos2) {
        if (word1.length() == pos1) {
        	return word2.length() - pos2;
        }
        if (word2.length() == pos2) {
        	return word1.length() - pos1;
        }
        
        if (cache[pos1][pos2] > 0) 
        	return cache[pos1][pos2];
        
        if (word1.charAt(pos1) == word2.charAt(pos2)) {
        	cache[pos1][pos2] = minDistance(word1, word2, pos1+1, pos2+1);
        	return cache[pos1][pos2];
        }
     
        int replace = 1 + minDistance(word1, word2, pos1+1, pos2+1);
        int insert = 1 + minDistance(word1, word2, pos1, pos2+1);
        int delete = 1 + minDistance(word1, word2, pos1+1, pos2);
        cache[pos1][pos2] = Math.min(replace, Math.min(insert, delete));
        return cache[pos1][pos2];
    }
	
	/**
	 * 有了DFS的思路，DP的思路就比较清晰了。
	 * 要注意的是初始化
	 */
	public int minDistanceDP(String word1, String word2) {
		int[][] f = new int[word1.length()+1][word2.length()+1];
		for (int i = 0; i <= word1.length(); i++) {
			f[i][0] = i;
		}
		
		for (int i = 0; i <= word2.length(); i++) {
			f[0][i] = i;
		}
		
		for (int i = 1; i <= word1.length(); i++) {
			for (int j = 1; j <= word2.length(); j++) {
				if (word1.charAt(i-1) == word2.charAt(j-1)) {
					f[i][j] = f[i-1][j-1];
				} else {
					f[i][j] = Math.min(f[i-1][j-1], Math.min(f[i-1][j], f[i][j-1])) + 1;
				}
			}
		}
		return f[word1.length()][word2.length()];
	}
	
	
	void testFollowup() {
		WordStream s1 = new WordStream("1203");
		WordStream s2 = new WordStream("123");
		System.out.println(this.isOneEditDistanceFollowup(s1, s2));
	}
	
	public static void main(String[] args) {
		EditDistance clz = new EditDistance();
		System.out.println(clz.minDistance("sea", "eat"));
		
		clz.testFollowup();
	}
	
}
