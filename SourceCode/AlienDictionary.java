import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class AlienDictionary {
	 private class Node {
        public int degree;
        public ArrayList<Integer> neighbour = new ArrayList<Integer>();
        void Node() {
            degree = 0;
        }
    }
    public String alienOrder(String[] words) {
        Node[] node = new Node[26];
        boolean[] happen = new boolean[26];
        for (int i = 0; i < 26; i++) {
            node[i] = new Node();
        }
        //Build the Graph
        for (int i = 0; i < words.length; i++) {
            int startPoint = 0, endPoint = 0;
            for (int j = 0; j < words[i].length(); j++) {
                happen[charToInt(words[i].charAt(j))] = true;
            }
            if (i != words.length - 1) {
                for (int j = 0; j < Math.min(words[i].length(), words[i + 1].length()); j++) {
                    if (words[i].charAt(j) != words[i + 1].charAt(j)) {
                        startPoint = charToInt(words[i].charAt(j));
                        endPoint = charToInt(words[i + 1].charAt(j));
                        break;
                    }
                }
            }
            if (startPoint != endPoint) {
                node[startPoint].neighbour.add(endPoint);
                node[endPoint].degree++;
            }
        }
        //Topological Sort
        Queue<Integer> queue = new LinkedList<Integer>();
        String ans = "";
        for (int i = 0; i < 26; i++) {
            if (node[i].degree == 0 && happen[i]) {
                queue.offer(i);
                ans = ans + intToChar(i);
            } 
        }
        while (!queue.isEmpty()) {
            int now = queue.poll();
            for (int i : node[now].neighbour) {
                node[i].degree--;
                if (node[i].degree == 0) {
                    queue.offer(i);
                    ans = ans + intToChar(i);
                }
            }
        }
        for (int i = 0; i < 26; i++) {
            if (node[i].degree != 0) {
                return "";
            }
        }
        return ans;
    }
    public char intToChar(int i) {
        return (char)('a' + i);
    }
    public int charToInt(char ch) {
        return ch - 'a';
    }
    
    
    public String alienOrder1(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<Character, Set<Character>>();
        // 节点的计数器
        Map<Character, Integer> indegree = new HashMap<Character, Integer>();
        // 结果存在这个里面
        StringBuilder order = new StringBuilder();
        // 初始化图和计数器
        initialize(words, graph, indegree);
        
        Set<String> edges = new HashSet<String>();
        for (int i = 0;  i < words.length -1; i++) {
            for (int j = 0; j < words[i].length() && j < words[i+1].length(); j++) {
                char from = words[i].charAt(j);
                char to = words[i+1].charAt(j);
                if (from == to) continue;
                if (!edges.contains(from+""+to)) {
                    graph.get(from).add(to);
                    indegree.put(to, indegree.get(to) + 1);
                    edges.add(from+""+to);
                    break;
                }
            }
        }
        
        Queue<Character> queue = new LinkedList<Character>();
        for (Character c : indegree.keySet()) {
            if (indegree.get(c) == 0) {
                queue.offer(c);
            }
        }
        
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Character curr = queue.poll();
            sb.append(curr);
            Set<Character> children = graph.get(curr);
            for (Character child : children) {
                int inde = indegree.get(child);
                inde--;
                indegree.put(child, inde);
                if (inde == 0) {
                    queue.offer(child);
                }
            }
        }
        return sb.toString();
        
    }
    
    private void initialize(String[] words, Map<Character, Set<Character>> graph, Map<Character, Integer> indegree){
        for(String word : words){
            for(int i = 0; i < word.length(); i++){
                char curr = word.charAt(i);
                // 对每个单词的每个字母初始化计数器和图节点
                if(graph.get(curr) == null){
                    graph.put(curr, new HashSet<Character>());
                }
                if(indegree.get(curr) == null){
                    indegree.put(curr, 0);
                }
            }
        }
    }
    
    public static void main(String[] args) {
    	AlienDictionary clz = new AlienDictionary();
    	String[] set1 =  {"wrt","wrf","er","ett","rftt","te"};
    	String[] set2 = {"abc", "abd","ae"};
//    	System.out.println(clz.alienOrder(set2));
    	System.out.println(clz.alienOrder1(set1));
    }
}
