# Topological Sort 拓扑排序

##### 269. Alien Dictionary
经典的外星人词典：
  * 注意环， 注意indegree 要把所有的字符都统计上，注意出现重复的情况，比如["a", "a"]
  * 注意重复出现的情况， 比如 ["ac","ab","zc","zb"] , c->b 会出现两次， indegree不应该加上2
  * 注意一个字符可以对应多个，[ab, ac, zb, zd], b -> c,d
  * 注意前面字符比后面长的情况 ["abc", "ab"] ，这样是非法的
```
 // time complexity: O(n) , n is total len of all words
 // the bfs part, is O(v+e), 
 // space O(v+e) becuase we store every pair. 
 public String alienOrder(String[] words) {
        Map<Character, List<Character>> map = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        for (String w : words) {
            for (char c : w.toCharArray()) {
                indegree.put(c, 0);
                map.put(c, new ArrayList<>());
            }
        }
        for (int i = 0; i < words.length - 1; i++) {
            String str1 = words[i], str2 = words[i+1];
            if (str1.length() > str2.length()  && str1.startsWith(str2)) {
                return "";
            }
            int j = 0;
            while (j < str1.length() && j < str2.length()) {
                if (str1.charAt(j) != str2.charAt(j)) break;
                j++;
            }
            if (j < str1.length() && j < str2.length()) {
                map.get(str1.charAt(j)).add(str2.charAt(j));
                indegree.put(str2.charAt(j), indegree.getOrDefault(str2.charAt(j), 0) + 1);
            }
        }
        
        Queue<Character> queue = new LinkedList<>();
        for (Character c : indegree.keySet()) {
            if (indegree.get(c) == 0) queue.offer(c);
        }
        
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            sb.append(c);
            for (char next : map.get(c)) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }
        
        if (sb.length() != indegree.keySet().size()) {
            return "";
        }
        return sb.toString();
    }
 ```   
 使用DFS也可以解，用两个set，一个visited 存一个点是不是被访问过，一个path存当前路径，用来判断环。
 ```
  public String alienOrderDFS(String[] words) {
    Map<Character, List<Character>> map = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    if (!buildGraph(map, indegree, words)) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    Set<Character> visited = new HashSet<>();
    for (char c : indegree.keySet()) {
      if (indegree.get(c) == 0 && !visited.contains(c)) {
        Set<Character> path = new HashSet<>();
        if (!dfsHelper(c, map, visited, path, sb)) {
          return "";
        }
      }
    }
    return sb.length() == indegree.keySet().size() ? sb.reverse().toString() : "";
  }

  private boolean dfsHelper(char c, Map<Character, List<Character>> map, Set<Character> visited, Set<Character> path, StringBuilder sb) {
    path.add(c);
    if (!visited.add(c)) {
      return true;
    }
    for (char next : map.get(c)) {
      if (path.contains(next) || !dfsHelper(next, map, visited, path, sb)) {
          return false;
        }
    }
    path.remove(c);
    sb.append(c);
    return true;
  }

  private boolean buildGraph(Map<Character, List<Character>> map, Map<Character, Integer> indegree, String[] words) {
    for (String w : words) {
      for (char c : w.toCharArray()) {
        indegree.put(c, 0);
        map.put(c, new ArrayList<>());
      }
    }
    for (int i = 0; i < words.length - 1; i++) {
      String str1 = words[i], str2 = words[i+1];
      if (str1.length() > str2.length()  && str1.startsWith(str2)) {
        return false;
      }
      int j = 0;
      while (j < str1.length() && j < str2.length()) {
        if (str1.charAt(j) != str2.charAt(j)) break;
        j++;
      }
      if (j < str1.length() && j < str2.length()) {
        map.get(str1.charAt(j)).add(str2.charAt(j));
        indegree.put(str2.charAt(j), indegree.getOrDefault(str2.charAt(j), 0) + 1);
      }
    }
    return true;
  }
 ```

 如果要找出所有的解
 ```
 public List<String> alienOrderPrintAll(String[] words) {
    Map<Character, List<Character>> map = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    List<String> res = new ArrayList<>();
    if (!buildGraph(map, indegree, words)) {
      return res;
    }
    Set<Character> path = new HashSet<>();
    findPaths(map, indegree, path, new StringBuilder(), res);
    return res;
  }

  private void findPaths(Map<Character, List<Character>> map, Map<Character, Integer> indegree, Set<Character> path, StringBuilder sb, List<String> res) {
    if (sb.length() == indegree.keySet().size()) {
      res.add(sb.toString());
      return;
    }
    for (char c : indegree.keySet()) {
      if (indegree.get(c) == 0 && !path.contains(c)) {
        sb.append(c);
        for (char child : map.get(c)) {
          indegree.put(child, indegree.get(child) - 1);
        }
        path.add(c);
        findPaths(map, indegree, path, sb, res);
        path.remove(c);
        for (char child : map.get(c)) {
          indegree.put(child, indegree.get(child) + 1);
        }
        sb.deleteCharAt(sb.length() - 1);
      }
    }
  }
 ```