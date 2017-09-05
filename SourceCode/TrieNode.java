
public class TrieNode {
	char label;
	TrieNode[] children;
	boolean hasWord = false;
	
	public TrieNode() {
		children = new TrieNode[26];
	}
	
	public TrieNode(char n) {
		this.label = n;
		children = new TrieNode[26];
	}
}