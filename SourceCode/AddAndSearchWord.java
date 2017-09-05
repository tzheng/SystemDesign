import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddAndSearchWord {
	
	TrieNode root;
    
    /** Initialize your data structure here. */
    public AddAndSearchWord() {
        root = new TrieNode();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
    	TrieNode node = root;
    	for (char c : word.toCharArray()) {
    		TrieNode[] children = node.children;
    		if (children[c-'a'] == null) {
    			children[c-'a'] = new TrieNode(c);
    		}
    		node = children[c-'a'];
    	}
    	node.hasWord = true;
    }
    
    /** Returns if the word is in the data structure. 
     * A word could contain the dot character '.' to represent any one letter. 
     **/
    public boolean search(String word) {
    	return dfs(0, root, word);
    }
    
    
    /**
     *  Followup 1:  support * as well, * can match any number of chars
     *  关键是当search word遇到'*'，要跳过所有可能连续的'*'继续寻找下一个非'*'的char。
	 *	在字典数据结构的Trie中就对于找当前node的所有对应的子孙nodes（若是'.'就找所有子孙
	 *	然后再循环每个子孙node递归。一个edge case就是若word以连续的'*'结尾的话就直接返回
	 *	true，因为'*'之前的prefix在字典中已经找到了。
	 *
	 *  时间复杂度可以这么考虑，假设我用一个 *a， 也就是说我要把所有的node都找一遍，这样
	 *  的话复杂度就是 O(N), N is number of nodes
	 *  
	 *  如果不支持 *， 那么很显然我们在O(logN) 的时间内就能找到
     */
    private boolean dfs(int index, TrieNode root, String word) {
    	if (index == word.length()) {
    		return root.hasWord;
    	}
    	
    	TrieNode[] children = root.children;
    	char c = word.charAt(index);
    	
    	if (Character.isLowerCase(c)) {
    		if (children[c-'a'] == null) {
    			return false;
    		} else {
    			return dfs(index+1, children[c-'a'], word);
    		}
    	} else if (c == '.') {
    		for (TrieNode child : children) {
    			if (child != null && dfs(index+1, child, word)) {
    				return true;
    			}
    		}
    	} else if (c == '*') {
    		//如果上面用 * match 没有找到解法，那么跳过 *， 看看 * 后面的和下一层是不是match
    		while (index < word.length() && word.charAt(index) == '*') {
    			index++;
    		}
    		//match 0 个
    		if (dfs(index, root, word)) {
    			return true;
    		}
    		for (TrieNode child : children) {
    			//让 * match 这一层，然后到下一层再判断要不要match， match 1->n 个
	    		if (child != null && dfs(index-1, child, word)) {
	    			return true;
	    		}
    		}
    		
    		/* 另外一种解法
    		 * index跳过所有的 *， 指向 * 后面第一个非 * 字符
    		 * 把当前 node 的所有孩子都找出来, 然后进行匹配，
    		 * 因为找出来的是所有的孩子，不管是那一层都有，也就相当于 * 匹配的任意个字母
    		 * 
    		 * List<TrieNode> nodes = getAllChildren(root);
    		 * for (TrieNode node : nodes) {
    		 * 		if (dfs1(index, node, word)) return true;
    		 * }
    		 */
    	}
    	
    	return false;
    }
    
    /**
     * 还有一个变体：
     * 说一个字母合集L, 还有个string word包含有?的通配符，还有另外一个dict. dict里的字母只会存在于L里。 查在这个dict里存在的符合 word 结构的所有词。
     * 回答方法的时候，说了三种， trie+dfs, 26个字母替换'?'+set+dfs, 和直接one by one 搜索dict里的所有words然后比较。（是的，楼主嘴贱，说了trie+dfs）.
     * 然后人家说，好吧，来个trie+dfs吧。 然后楼主没写完 。。。 
     * 估计本来人家只希望你写个set+dfs就行了，楼主嘴贱阿。。。只能期待奇迹。。。
     * 举个例子, L={a, b, c, d, s, g, e , w}, string word = "c??", dict={abc, cba, dsgs, dsgew, as cdd}
     */
    
    public static void main(String[] args) {
    	AddAndSearchWord clz = new AddAndSearchWord();
    	clz.addWord("bacd");
    	clz.addWord("ba");
    	clz.addWord("ad");
    	System.out.println(clz.search("a*d"));
    }
}
