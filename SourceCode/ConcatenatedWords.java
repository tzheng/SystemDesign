import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcatenatedWords {
	/**
	 * 472. Concatenated Words
	 * 
	 */
	 //buid a trie and do dfs
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> result = new HashSet<String>();
        if (words == null || words.length == 0) {
            return new ArrayList<String>();
        }
        TrieTree trie = new TrieTree();
        for (String word : words) {
            trie.insert(word);
        }
        
        for (String word : words) {
            dfs(0, 0, word, trie, result);
        }
        return new ArrayList<String>(result);
    }
    
    
    public void dfs(int pos, int count, String word, TrieTree trie, Set<String> result) {
        if (pos >= word.length()) {
        	if (count > 1) {
        		result.add(word);
        	}
            return;
        }
        
        for (int i = pos; i < word.length(); i++) {
            String prefix = word.substring(pos, i+1);
            if (trie.search(prefix)) {
                dfs(i+1, count+1, word, trie, result);
            }
        }
    }
    
    /**
     * 一个更直接的DFS
     */
    private boolean dfs(String s, TrieNode root, int level, Map<String, Boolean> map) {
        if (s.length() == 0 && level > 1) {
            return true;
        }
        if (level > 0 && map.containsKey(s)) {
            return map.get(s);
        }
        TrieNode node = root;
        for (int i = 0; i < s.length(); i++) {
            node = node.children[s.charAt(i) - 'a'];
            if (node == null) break;
            if (node.hasWord) {
                if (dfs(s.substring(i + 1), root, level + 1, map)) {
                    map.put(s, true);
                    return true;
                }
            }
        }
        if (level > 0) {
            // don't store level-0 because it will mess up with level-1 and above
            // dfs(dog, level-0) will return false, but dfs(catdog, level-0)-->dfs(dog, level-1) will return true
              map.put(s, false);
         }
        return false;
    }
    
    class TrieTree {
        TrieNode root = new TrieNode(' ');
        
        public void insert(String str) {
            TrieNode node = root;
            for (char  c : str.toCharArray()) {
                TrieNode[] children = node.children;
                if (children[c-'a'] == null) {
                    children[c-'a'] = new TrieNode(c);
                }
                node = children[c-'a'];
            }
            node.hasWord = true;
        }
        
        public boolean search(String str) {
            TrieNode node = root;
            for (char  c : str.toCharArray()) {
                TrieNode[] children = node.children;
                if (children[c-'a'] == null) {
                    return false;
                }
                node = children[c-'a'];
            }
            return node.hasWord;
        }
    }
    
    
    public static void main(String[] args) {
    	ConcatenatedWords clz = new ConcatenatedWords();
    	String[] words = {"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
    	List<String> res = clz.findAllConcatenatedWordsInADict(words);
    	System.out.println(Arrays.toString(res.toArray()));
    }
    
//    class TrieNode {
//        char label;
//	    TrieNode[] children;
//	    boolean hasWord = false;
//	
//    	public TrieNode(char n) {
//    		this.label = n;
//    		children = new TrieNode[26];
//    	}
//    }
    
/**
 * 
 * 超时的DFS做法，暴力DFS
 *  public List<String> findAllConcatenatedWordsInADict(String[] words) {
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
	
 */
    
 /**
  * DP的做法
  * n^2* k^2. 
  * 
  * private static boolean canForm(String word, Set<String> dict) {
        if (dict.isEmpty()) return false;
		boolean[] dp = new boolean[word.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= word.length(); i++) {
		    for (int j = 0; j < i; j++) {
			if (!dp[j]) continue;
			if (dict.contains(word.substring(j, i))) {
			    dp[i] = true;
			    break;
			}
		    }
		}
		return dp[word.length()];
    }
  */
}
