# Sliding Window - 滑动窗口

####模板：窗口大小固定，找特定解


#### 模板：窗口大小不固定，找最大满足条件窗口
```
public int template() {
    // 左右指针都设为 0
    int i = 0, j = 0;
    // 当右指针没有到末尾的时候
    while (j < len) {
        // 做和移动左指针有关的操作，比如 count++
        while (not meet condition) {
            // 当前情况超过条件限定，比如 count > k，移动右指针
            i++;
            // 做和移动右指针有关的操作，比如 count--
        }
        // 现在肯定满足条件了
        // 更新 window size
        maxSize = max(maxSize, j - i + 1);
        j++; // 移动左指针
    }
    return maxSize;
}
```
##### 模板题 340. Longest Substring with At Most K Distinct Characters
```
public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character,Integer> map = new HashMap<>();      
        if (k == 0) return 0;
        if (s.length() <= k) return s.length();
        
        int i = 0, j = 0, max = 0;
        while (j < s.length()) {
            map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0) + 1);
            while (i < j && map.size() > k) {
                char c = s.charAt(i++);
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) {
                    map.remove(c);
                }
            }
            j++;
            int len = j - i;
            max = Math.max(max, len);
        }
        return max;
    }
```

#### 模板：窗口大小不固定，找最小满足条件窗口
外层循环先滑动右指针，直到满足条件，内层循环在慢慢滑动左指针，直到不满足条件
##### 模板题 76. Minimum Window Substring
```
public String minWindow(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int i = 0, j = 0, min = Integer.MAX_VALUE, count = 0, minLeft = 0;
        while (j < s.length()) {
            // 先滑动右指针
            char right = s.charAt(j);
            if (map.containsKey(right)) {
                map.put(right, map.get(right) - 1);
                if (map.get(right) >= 0) {
                    count++;
                }
            }
            j++;
            // 满足条件，内层循环在慢慢滑动左指针，直到不满足条件
            while (count == t.length()) {
                if (j - i < min) {
                    min = j - i;
                    minLeft = i;
                }
                char left = s.charAt(i);
                if (map.containsKey(left)) {
                    map.put(left, map.get(left) + 1);
                    if (map.get(left) > 0) {
                        count--;
                    }
                }
                i++;
            }
        }
        if (min > s.length()) {
            return "";
        }
        return s.substring(minLeft, minLeft + min);
    }
```

##### 3. Longest Substring Without Repeating Characters
```
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int i = 0, j = 0, max = 0;
        while (j < s.length()) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                max = Math.max(j - i, max);
            } else {
                set.remove(s.charAt(i++));
            }
        }        
        return max;
    }
```

##### 159. Longest Substring with At Most Two Distinct Characters
```
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int i = 0, j = 0, max = 0;
        while (j < s.length()) {
            char c = s.charAt(j);
            map.put(c, map.getOrDefault(c, 0) + 1);
            while (i < j && map.size() > 2) {
                char rm = s.charAt(i);
                map.put(rm, map.get(rm) - 1);
                if (map.get(rm) == 0) {
                    map.remove(rm);
                }
                i++;
            }
            j++;
            max = Math.max(j - i, max);
        }
        return max;
    }
```

##### 424. Longest Repeating Character Replacement
```
    public int characterReplacement(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        int i = 0, j = 0, maxCount = 0, maxLen = 0;
        while (j < s.length()) {
            char c = s.charAt(j);
            map.put(c, map.getOrDefault(c, 0) + 1);
            maxCount = Math.max(map.get(c), maxCount);
            while (maxCount + k < j - i + 1) {
                map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
                i++;
            }
            j++;
            maxLen = Math.max(j - i, maxLen);
        }
        return maxLen;
    }
```

##### 992. Subarrays with K Different Integers
```
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k-1);
    }
    // what if it's at most k
    private int atMost(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int i = 0, j = 0, count = 0, distinct = 0;
        while (j < nums.length) {
            map.put(nums[j], map.getOrDefault(nums[j], 0) + 1);
            if (map.get(nums[j]) == 1) {
                distinct++;
            }
            while (distinct > k) {
                map.put(nums[i], map.get(nums[i]) - 1);
                if (map.get(nums[i]) == 0) {
                    distinct--;
                }
                i++;
            }
            count += j - i + 1;
            j++;
        }
        return count;
    }
```