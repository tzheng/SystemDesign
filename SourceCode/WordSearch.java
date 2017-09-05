import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSearch {
	/**
	 * 79. Word Search
	 * 标准的DFS模板。
	 * 
	 * Given a 2D board and a word, find if the word exists in the grid.
	 * O(m*n*4^len(word)). Space complexity is O(nm).
	 * 
	 * T(N) = 4T(N-1) = 16T(N-2) ... 4^N*T(0), N = word length;
	 * 
	 */
	public boolean exist(char[][] board, String word) {
		if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        
        int[][] visited = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (find(word, i, j, board, visited)) return true;   
            }
        }
        
        return false;
	}
	
	int[] dx = {-1,1,0,0};
	int[] dy = {0,0,-1,1};
	
	private boolean find(String word, int i, int j, char[][] board, int[][] visited) {
		if (0 == word.length()) {
			return true;
		}
		if (i <0 || i >= board.length || j <0 || j >= board[0].length 
	            || visited[i][j] == 1  //注意，题目要求字母不能重复使用
	            || word.charAt(0) != board[i][j]) {
	            return false;
	    }
		visited[i][j] = 1;
		
		for (int k = 0; k < 4; k++) {
			if (find(word.substring(1), i+dx[k], j+dy[k], board, visited)) {
				return true;
			}
		}
		visited[i][j] = 0;
		return false;
	}
	
	
	/**
	 * 212. Word Search II
	 * Given a 2D board and a list of words from the dictionary, find all words in the board.
	 * 和上面一题不同，这题要多次query，如果对于每一个词都做一遍DFS， 效率太低，会做很多重复搜索，
	 * 所以需要引进一个 Trie, 用输入的单词数组words构建一个 Trie， 当做DFS的时候，看看当前形成的单词在不在trie里面，
	 * 如果不在就可以剪枝了， 如果存在Trie里面，说明当前形成的单词是words中的一个
	 */
	Set<String> res = new HashSet<String>();  
	TrieNode root = new TrieNode();
	public List<String> findWords(char[][] board, String[] words) { 
		
		for (String word : words) {
			insert(word);
		}
		
		String str = "";
		 int[][] visited = new int[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				search(str, board, i, j, root, visited);
			}
		}
		
		return new ArrayList<String>(res);  
	}
	
	private void search(String str, char[][] board, int x, int y, TrieNode trie, int[][] visited) {
		if (x < 0 || x >= board.length || y < 0 || y >= board[0].length
				|| visited[x][y] == 1) return;  

		//为了防止越界，应该在检查之后再判断。如果trieNode已经没有对应的孩子，剪枝
		char c = board[x][y];
		if (trie.children[c-'a'] == null) { 
			return;
		}
		//如果有孩子，而且正好孩子还是一个词的结尾，说明找到这个词了	
		str += c;
		TrieNode next = trie.children[c-'a'];
		if (next.hasWord) {
			res.add(str);
		}
		
		visited[x][y] = 1;
		for (int k = 0; k < 4; k++) {
			search(str, board, x + dx[k], y + dy[k], next, visited);
		}
		
		visited[x][y] = 0;
	}
	
	private void insert(String str) {
		TrieNode node = root;
		for (char c : str.toCharArray()) {
			if (node.children[c-'a'] == null) {
				node.children[c-'a'] = new TrieNode(c);
			}
			node = node.children[c-'a'];
		}
		node.hasWord = true;
	}
	
	
//	
//	
//	class TrieTree {
//		TrieNode root;
//		
//		public void insert(String str) {
//			TrieNode node = root;
//			for (char c : str.toCharArray()) {
//				TrieNode[] children = node.children;
//				if (children[c - 'a'] == null) {
//					children[c - 'a'] = new TrieNode(c);
//				}
//				node = children[c - 'a'];
//			}
//			node.hasWord = true;
//		}
//		
//		public boolean search(String str) {
//			TrieNode node = root;
//			for (char c : str.toCharArray()) {
//				TrieNode[] children = node.children;
//				if (children[c - 'a'] == null) {
//					return false;
//				}
//				node = children[c - 'a'];
//			}
//			return node.hasWord;
//		}
//		
//		public boolean startsWith(String str) {
//			TrieNode node = root;
//			for (char c : str.toCharArray()) {
//				TrieNode[] children = node.children;
//				if (children[c - 'a'] == null) {
//					return false;
//				}
//				node = children[c - 'a'];
//			}
//			return true;
//		}
//		
//	}
	
//	class TrieNode {
//		char label;
//		TrieNode[] children;
//		boolean hasWord = false;
//		
//		public TrieNode() {
//			children = new TrieNode[26];
//		}
//		
//		public TrieNode(char n) {
//			this.label = n;
//			children = new TrieNode[26];
//		}
//	}
}
