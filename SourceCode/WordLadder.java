import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadder {
	
	/**
	 * 127. Word Ladder
	 * beginWord = "hit"  endWord = "cog"
	 * wordList = ["hot","dot","dog","lot","log","cog"]
	 * 
	 * 找最短的转化路径，beginword不在词典里面，end要在词典里面。
	 * 找最短路径，那么就是BFS, 一旦找到结果就返回。
	 * 时间复杂度 我们每一次找到新单词以后，要计算26个字母组合的可能性，对于每一个新组成的单词，
	 * 我们要判断是存在于set里。branching factor = 26，depth = 单词长度L，set中的查找我们粗略算为O(1)，
	 * 所以这一部分的复杂度就是26 ^ L。假如我们有x个单词满足条件，那么接下来我们要对这x个单词进行同样的步骤。
	 * 最坏情况下我们要检查set中所有的n个单词，所以时间复杂度应该是O(n * 26^L ) ， 空间复杂度也一样。
	 */
	
	Set<String> dict = new HashSet<String>();
    Set<String> visited = new HashSet<String>();
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        for (String str : wordList) {
            dict.add(str);
        }
        dict.add(beginWord);
        dict.add(endWord);
        
        //BFS is better
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        visited.add(beginWord);
        int level = 0;
        while (!queue.isEmpty()) {
        	int size = queue.size();
            
            for (int i = 0; i < size; i++) {
            	String curr = queue.poll();
                if (curr.equals(endWord)) {
                    return level;
                }
                ArrayList<String> nextWords = getNextWords(curr);
                for (String nextWord : nextWords) {
                    queue.offer(nextWord);
                    visited.add(nextWord);
                }
            }
            level++;
        }
        
        return -1;
    }
    
    private ArrayList<String> getNextWords(String curr) {
        ArrayList<String> retList = new ArrayList<String>();
        for (int i = 0; i < curr.length(); i++) {
            char[] arr = curr.toCharArray();
            for (char c = 'a'; c <='z'; c++) {
                if (c == curr.charAt(i)) continue;
                arr[i] = c;
                String after = new String(arr);
                if (dict.contains(after) && !visited.contains(after)) 
                    retList.add(after);
            }
        }
        return retList;
    }
    
    /**
     * BFS 还有一种做法 Two-way BFS
     * http://www.cnblogs.com/yrbbest/p/4438488.html
     */
    
    /**
     * 进阶版。126. Word Ladder II
     * 这题不但要找到最短路径，还要输出所有的最短路径。简单的BFS就不够了。需要BFS和DFS结合。
     * 首先先用BFS找到最短路径的长度，记为len。然后通过DFS来找所有长度为len的合法路径。
     * 
     * 重点：单纯从起点开始做DFS效率会比较低，因为对每一个词都要把各种可能性都走一遍，走到超过len了才剪枝
     * 如果我们做BFS的时候，在visited map里面同时记录一下当前节点离起点有多少层，那么在DFS的时候，就可以
     * 从终点开始走，对于终点的所有变化，只有离起点距离为len-1的点才有可能构成最短路径。
     */
    HashMap<String, List<String>> nextMap = new HashMap<>();
	HashMap<String, Integer> visitedMap = new HashMap<>();
	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> dict = new HashSet<String>();
        for (String word : wordList) {
            dict.add(word);
        }
        dict.add(beginWord);
        if (!dict.contains(endWord)) {
            return result;
        }
        
//        Set<String> visited = new HashSet<String>();
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        visitedMap.put(beginWord, 0);
        int level = 0;
        while (!queue.isEmpty()) {
          	  int size = queue.size();
          	  for (int i = 0; i < size; i++) {
                  String currWord = queue.poll();
                  if (currWord.equals(endWord)) {
                      break;
                  }
                  List<String> nextWords = getNextWords(currWord, dict);
                  for (String nextWord : nextWords) {
                      if (!visitedMap.containsKey(nextWord)) {
                        queue.offer(nextWord);
                        visitedMap.put(nextWord, level + 1);
                      }
                  }
          	  }
            level++;
        }
        
        List<String> path = new ArrayList<String>();
        dfs(endWord, beginWord, dict, path, result);
        return result;
  }
	
	private void dfs(String start, String end, Set<String> dict, List<String> path, List<List<String>> result) {
// 		if (visited.get(start) == visited.get(end)) {
			if (start.equals(end)) {
				List<String> list = new LinkedList<String>(path);
				list.add(start);
				Collections.reverse(list);
				result.add(list);
				return;
			}
// 			return;
// 		}
		
		path.add(start);
		for (String str : getNextWords(start, dict)) {
			if (visitedMap.containsKey(str) && visitedMap.get(str) == visitedMap.get(start) -1) {
				dfs(str, end, dict, path, result);
			}
		}
		path.remove(path.size() - 1);
	}
    
    private List<String> getNextWords(String curr, Set<String> dict) {
        List<String> nextWords = new ArrayList<String>();
        for (int i = 0; i < curr.length(); i++) {
            for (char replace = 'a'; replace <= 'z'; replace++) {
                if (replace == curr.charAt(i)) continue;
                String newStr = curr.substring(0,i) + replace + curr.substring(i+1, curr.length());
                if (dict.contains(newStr) ) {
                    nextWords.add(newStr);
                }
            }
        }
        return nextWords;
    }
    
    
    public static void main(String[] args) {
 	   WordLadder cls = new WordLadder();
 	   String[] strs = {"hot","dot","dog","lot","log","cog"};
 	   List<String> dicts = (List<String>) Arrays.asList(strs);
 	   System.out.println(cls.ladderLength("hit", "cog", dicts));
    }
}
