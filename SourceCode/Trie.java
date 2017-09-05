public class Trie {
    
   TrieNode root;
   
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode node = root;
        
        for (int i = 0; i < word.length(); i++) {
        	char c = word.charAt(i);
        	int pos = c - 'a';
        	if (node.children[pos] == null) {
        		node.children[pos] = new TrieNode(c);
        	}
        	node = node.children[pos];
        }
        node.hasWord = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = root;
        
        int i = 0;
        while (i < word.length()) {
        	char c = word.charAt(i);
        	int pos = c - 'a';
        	if (node.children[pos] == null) {
        		break;
        	}
        	node = node.children[pos];
        	i++;
        }
        
        if (i != word.length())
        	return false;
        
        return node.hasWord;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
    	TrieNode node = root;
        
        int i = 0;
        while (i < prefix.length()) {
        	char c = prefix.charAt(i);
        	int pos = c - 'a';
        	if (node.children[pos] == null) {
        		break;
        	}
        	node = node.children[pos];
        	i++;
        }
        
        return i == prefix.length();
    }
    
    
    public static void main(String[] args) {
    	Trie trie = new Trie();
    	trie.insert("abc");
    	trie.insert("abd");
    	trie.insert("ab");
    	System.out.println(trie.search("ab"));
    	System.out.println(trie.search("abe"));
    	System.out.println(trie.startsWith("ab"));
    	System.out.println(trie.startsWith("ac"));
    }
}
