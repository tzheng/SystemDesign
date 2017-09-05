import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TopologicalSort {
	/**
	 * 207. Course Schedule
	 * 这是一个标准的拓扑排序模板，拓扑排序是不需要记录visited的，这一点和BFS不一样
	 * ！！最后需要检查 count == n, 有可能有的东西没被遍历到
	 */
	public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        int[] indegrees = new int[numCourses];
        //注意，所有的course都需要初始化，以防某一些课没有outdegree，graph.get()返回null
        for (int i = 0; i < numCourses; i++) {
            graph.put(i, new HashSet<Integer>());
        }
        
        for (int[] pre : prerequisites) {
            int c = pre[0], p = pre[1];
            graph.get(p).add(c);  // preq -> c
            indegrees[c]++;
        }
        
        int count = 0;
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (indegrees[i] == 0)
                queue.offer(i);
        }
        
        while (!queue.isEmpty()) {
            Integer curr = queue.poll();
            count++;
            for (int child : graph.get(curr)) {
                indegrees[child]--;
                if (indegrees[child] == 0) {
                    queue.offer(child);
                }
            }
        }
        return count == numCourses;
    }
	
	/**
	 * 210. Course Schedule II
	 * 上面是输出是否，这里是输出一个有效的解法，解法一模一样，就是加一个数组存就好了
	 */
	public int[] findOrder(int n, int[][] prerequisites) {
		HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        int[] indegrees = new int[n];
        //注意，所有的course都需要初始化，以防某一些课没有outdegree，graph.get()返回null
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<Integer>());
        }
        for (int[] pre : prerequisites) {
            int c = pre[0], p = pre[1];
            graph.get(p).add(c);  // preq -> c
            indegrees[c]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegrees.length; i++) {
        	if (indegrees[i] == 0) queue.offer(i);
        }
        
        int count = 0;
        int[] res = new int[n];
        while (!queue.isEmpty()) {
        	int curr = queue.poll();
        	res[count++] = curr;
        	for (int child : graph.get(curr)) {
        		indegrees[child]--;
        		if (indegrees[child] == 0) {
        			queue.offer(child);
        		}
        	}
        }
        
        if (count != n) {
        	return new int[0];
        }
        return res;
	}
	
	/**
	 * 269. Alien Dictionary
	 * 首先要遍历所有的词，对于每个词汇和它的前一个词，找到不同的位置i，然后前一个词i位置所对应的
	 * 字母就是当前词i位置所对应的字母的前驱，一旦找到不同的地方，就可以返回了，不能再比较i+1位
	 * !!如果 a -> b的顺序已经存在，不要增加indegree, edges set的作用
	 * 拓扑排序时间都是O(VE),要把所有的边和点都走一遍
	 */
	public String alienOrder(String[] words) {
		Map<Character, Set<Character>> graph = new HashMap<>();
		Map<Character, Integer> indegree = new HashMap<>();
		//给所有字符都加上indegree和child，以防后面出错
		initialize(words, graph, indegree);
		
		Set<String> edges = new HashSet<String>();//注意不能重复统计
		for (int i = 1; i < words.length; i++) {
			int j = 0;
			while (j < words[i-1].length() && j < words[i].length() 
					&& words[i-1].charAt(j) == words[i].charAt(j)) {
				j++;
			}
			if (j < words[i-1].length() && j < words[i].length()) {
				String edg = words[i-1].charAt(j) + "" + words[i].charAt(j);
				if (!edges.contains(edg)) {
					graph.get(words[i-1].charAt(j)).add(words[i].charAt(j));
					indegree.put(words[i].charAt(j), indegree.get(words[i].charAt(j)) + 1);
					edges.add(edg);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		Queue<Character> queue = new LinkedList<>();
		for (char c : indegree.keySet()) {
			if (indegree.get(c) == 0) {
				queue.offer(c);
			}
		}
		
		while (!queue.isEmpty()) {
			char curr = queue.poll();
			sb.append(curr);
			for (char child : graph.get(curr)) {
				int inde = indegree.get(child) - 1;
				indegree.put(child, inde);
				if (inde == 0) {
					queue.offer(child);
				}
			}
		}
		//最后不要忘了比较一下长度
		return sb.length() == indegree.size() ? sb.toString() : "";
	}
	
	private void initialize(String[] words, Map<Character, Set<Character>> graph, Map<Character, Integer> indegree) {
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				char curr = word.charAt(i);
				// 对每个单词的每个字母初始化计数器和图节点
				if (graph.get(curr) == null) {
					graph.put(curr, new HashSet<Character>());
				}
				if (indegree.get(curr) == null) {
					indegree.put(curr, 0);
				}
			}
		}
	}
	
	/**
	 * 444. Sequence Reconstruction
	 * 也同样是做拓扑排序，但是由于需要结果唯一，也就是说每个时段，indregee为0的数字只能有一个
	 * 如果超过一个，说明有多个组合
	 * 如果只有一个，那么就比较当前数组和org里面对应位的数字是不是一样，一样才继续往后走
	 */
	public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
	    Map<Integer, Integer> indegree = new HashMap<>();
	    
	    for (List<Integer> seq : seqs) {
	    	for (int i = 0; i < seq.size(); i++) {
	    		if (!graph.containsKey(seq.get(i))) {
	    			graph.put(seq.get(i), new ArrayList<Integer>());
	    			indegree.put(seq.get(i), 0);
	    		}
	    		if (i > 0) {
	    			graph.get(seq.get(i-1)).add(seq.get(i));
	    			indegree.put(seq.get(i), indegree.get(seq.get(i)) + 1);
	    		}
	    	}
	    }
	    
	    if (org.length != indegree.size()) {
	    	return false;
	    }
	    
	    Queue<Integer> queue = new LinkedList<>();
	    for (int i : indegree.keySet()) {
	    	if (indegree.get(i) == 0) {
	    		queue.offer(i);
	    	}
	    }
	    
	    int count = 0; //最后需要检查count是否够
	    while (!queue.isEmpty()) {
	    	if (queue.size() > 1) {
	    		return false;
	    	}
	    	int curr = queue.poll();
	    	//不要忘了检查对应的数字是不是正确
	    	if (org[count++] != curr) {  
	    		return false;
	    	}
	    	for (int child : graph.get(curr)) {
	    		indegree.put(child, indegree.get(child) - 1);
	    		if (indegree.get(child) == 0) {
	    			queue.offer(child);
	    		}
	    	}
	    }
	    return count == org.length;
    }
	
	
	
}
