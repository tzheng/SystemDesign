import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WindowSubstring {
	/**
	 * 76. Minimum Window Substring
	 * 一个很经典的2 pointer的问题。简单解法，但是这样时间复杂度
	 * 为 O(N*256) 考虑到每次都要判断isValid, 空间O(1), 因为另外开的数组大小固定。
	 */
	public String minWindow(String s, String t) {
		if (s == null || t == null) 
            return ""; 
            
        int[] source = new int[256];
        int[] target = new int[256];
        for (char c : t.toCharArray()) {
        	target[c]++;
        }
        
        int min = Integer.MAX_VALUE;
        int j = 0;
        String res = "";
        for (int i = 0; i < s.length(); i++) {
        	while (j < s.length() && !isValid(source, target)) {
        		source[s.charAt(j)]++;
        		j++;
        	}
        	if (isValid(source, target) && j-i < min) {
        		min = j-i+1;
        		res = s.substring(i, j);
        	}
        	source[s.charAt(i)]--;
        }
        return res;
	}
	private boolean isValid(int[] source, int[] target) {
		for (int i = 0; i < source.length; i++) {
			if (source[i] < target[i]) return false;
		}
		return true;
	}
	
	/**
	 * 另外一种使用hashmap的解法
	 * 时间 O(n）， 空间O(k), k = t.length();
	 * 
	 * 移动窗口右端，而移动窗口左端的条件是当找到满足条件的串之后，一直移动窗口左端直到有字典里的字符不再在窗口里
	 * 一开始key包含字典中所有字符，value就是该字符的数量，然后遇到字典中字符时就将对应字符的数量减一。
	 */
	public String minWindowHash(String s, String t) {
		if (s == null || t == null)
			return "";

		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (char c : t.toCharArray()) {
			map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
		}

		int left = 0, right = 0, count = map.size();
		int minLen = Integer.MAX_VALUE;
		int minLeft = 0;
		while (right < s.length()) {
			char c = s.charAt(right);
			if (map.containsKey(c)) {
				map.put(c, map.get(c) - 1);
				if (map.get(c) == 0)
					count--; // find all occurance of this char
			}
			right++;
			while (count == 0) { // move left pointer, until invalid again
				char tmpc = s.charAt(left);
				if (map.containsKey(tmpc)) {
					map.put(tmpc, map.get(tmpc) + 1);
					if (map.get(tmpc) > 0) {
						count++;
					}
				}
				if (right - left < minLen) {
					minLen = right - left;
					minLeft = left;
				}
				left++;
			}

		}
		if (minLen == Integer.MAX_VALUE)
			return "";
		return s.substring(minLeft, minLeft + minLen);
	}
	
	/**
	 * 30. Substring with Concatenation of All Words
	 * 
	 * input words, that are all of the same length.
	 * 这题和上面一题是类似题，不过难度是H
	 */
	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> result = new ArrayList<>();
		if (s == null || words == null || s.length() == 0 || words.length == 0) {
			return result;
		}
		int len = words[0].length();
		Map<String, Integer> map = new HashMap<>();
		for (String w : words) {
			map.put(w, map.containsKey(w)?map.get(w) + 1 : 1);
		}
		
		int right = 0;
		while (right < s.length() - len*words.length + 1) {
			Map<String, Integer> copy = new HashMap<>(map);
			for (int i = 0; i < words.length; i++) {
				//每次截取长度为 len 的片段，因为words中的每一个词都要出现在substring里面，而且出现次数相同
				String str = s.substring(right + i*len, right + (i+1)*len); 
				if (copy.containsKey(str)) {
					int count = copy.get(str);
					count--;
					if (count == 0) copy.remove(str);
					else copy.put(str, count);
					if (copy.size() == 0) {
						//所有的词都被用到了
						result.add(right);
						break;
					}
				} else {
					break;
				}
			}
			right++;
		}
		return result;
	}
	
	/**
	 * 3. Longest Substring Without Repeating Characters
	 * 很简单，用两个pointer就解决了，每个元素被加入set一次，移除一次
	 * 时间所以复杂度是O(n)
	 */
	public int lengthOfLongestSubstring(String s) {
		if (s == null || s.length() == 0) return 0;
        
        int left = 0, right = 0;
        int max = 0;
        Set<Character> set = new HashSet<Character>();
        while (right < s.length()) {
            while (left < right && set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }
            max = Math.max(right - left + 1, max);
            set.add(s.charAt(right++));
        }
        
        return max;
    }
	
	/**
	 * 159. Longest Substring with At Most Two Distinct Characters Add to List
	 * 340. Longest Substring with At Most K Distinct Characters
	 * 做法和上一题几乎一样，就是一个滑动的窗口。
	 */
	public int lengthOfLongestSubstringTwoDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k == 0) return 0;
        
        int j = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 1;
//        for (int i = 0; i < s.length(); i++) {
//            while (j < s.length() && map.size() <= k) {
//            	map.put(s.charAt(j), map.containsKey(s.charAt(j))?map.get(s.charAt(j)) + 1 : 1);
//            	j++;
//            }
//            max = Math.max(max, map.size() <= k? j-i : j-i-1);
//            int count = map.get(s.charAt(i));
//            if (count == 1) map.remove(s.charAt(i));
//            else map.put(s.charAt(i), count-1);
//        }
        int i = 0;
        while (j < s.length()) {
            while (j < s.length() && (map.size() < k || map.containsKey(s.charAt(j)))) {
                max = Math.max(max, j-i+1);
                map.put(s.charAt(j), map.containsKey(s.charAt(j)) ? map.get(s.charAt(j)) + 1 : 1);
                j++;
            }
            // max = Math.max(max, map.size() <= 2? j-i : j-i-1);
            int count = map.get(s.charAt(i));
            if (count == 1) map.remove(s.charAt(i));
            else map.put(s.charAt(i), count-1);
            i++;
        }
        
        return max;
    }
	
	/**
	 * 综上所述，对于这些题目，可以用一个模板解决
	 * https://leetcode.com/problems/minimum-window-substring/
	 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
	 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
	 * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
	 * https://leetcode.com/problems/find-all-anagrams-in-a-string/
	 *
	 */
	public List<Integer> slidingWindowTemplate(String s, String t) {
        //init a collection or int value to save the result according the question.
        List<Integer> result = new ArrayList<>();
        if(t.length() > s.length()) return result;
        
        //create a hashmap to save the Characters of the target substring.
        //(K, V) = (Character, Frequency of the Character)
        Map<Character, Integer> map = new HashMap<>();
        for(char c : t.toCharArray()){
            map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
        }
        
        //maintain a counter to check whether match the target string.
        int counter = map.size();//must be the map size, NOT the string size because the char may be duplicate.
        
        //Two Pointers: begin - left pointer of the window; end - right pointer of the window
        int left = 0, right = 0;
        
        //the length of the substring which match the target string.
        int len = Integer.MAX_VALUE; 
        
        //loop at the begining of the source string
        while(right < s.length()){
            char c = s.charAt(right);//get a character

            if( map.containsKey(c) ){
                map.put(c, map.get(c)-1);// plus or minus one
                if(map.get(c) == 0) counter--;//modify the counter according the requirement(different condition).
            }
            right++;
            
            //increase begin pointer to make it invalid/valid again
            while(counter == 0 /* counter condition. different question may have different condition */){
                char tempc = s.charAt(left);//***be careful here: choose the char at begin pointer, NOT the end pointer
                if(map.containsKey(tempc)){
                    map.put(tempc, map.get(tempc) + 1); //plus or minus one
                    if(map.get(tempc) > 0) counter++; //modify the counter according the requirement(different condition).
                }
                
                /* save / update(min/max) the result if find a target*/
                // result collections or result int value
                left++;
            }
        }
        return result;
    }
	
	/**
	 * 438. Find All Anagrams in a String
	 */
	 public List<Integer> findAnagrams(String s, String p) {
		List<Integer> result = new ArrayList<>();
		if (s == null || p == null || s.length() < p.length() || p.length() == 0) {
			return result;
		}
		Map<Character, Integer> map = new HashMap<>();
		for (char c : p.toCharArray()) {
			map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
		}
		
		int left = 0, right = 0, count = map.size();
		while (right < s.length()) {
			char c = s.charAt(right);
			if (map.containsKey(c)) {
				map.put(c,  map.get(c) - 1);
				if (map.get(c) == 0) count--;
			}
			right++;
			while (count == 0) {
				char tmpc = s.charAt(left);
				if (map.containsKey(tmpc)) {
					map.put(tmpc, map.get(tmpc) + 1);
					if (map.get(tmpc) == 1) count++;
				}
				
				left++;
			}
			if (right - left == p.length()) {
				result.add(left);
			}
		}
		
		return result;
	 }
	 
	 
	 /**
	  * template
	  * @param args
	  */
//	 int findSubstring(String s){
//	        char[] map = new char[256];
//	        int counter; // check whether the substring is valid
//	        int begin=0, end=0; //two pointers, one point to tail and one  head
//	        int d; //the length of substring
//
//	        for() { /* initialize the hash map here */ }
//
//	        while(end<s.size()){
//
//	            if(map[s[end++]]-- ?){  /* modify counter here */ }
//
//	            while(/* counter condition */){ 
//	                 
//	                 /* update d here if finding minimum*/
//
//	                //increase begin to make it invalid/valid again
//	                
//	                if(map[s[begin++]]++ ?){ /*modify counter here*/ }
//	            }  
//
//	            /* update d here if finding maximum*/
//	        }
//	        return d;
//	  }
	
	public static void main(String[] args) {
		WindowSubstring clz = new WindowSubstring();
		clz.lengthOfLongestSubstringTwoDistinct("eceba", 2);
//		System.out.println(clz.minWindowHash("ADOBECODEBANC", "ABC"));
//		clz.findSubstring("barfoothefoobarman", new String[]{"foo", "bar"});
	}
}
