import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {
	/**
	 * 49. Group Anagrams
	 *  简单的办法，排序然后放到hash里面，
	 *  Time O(n*mlogm) , Space O(n)
	 */
	public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String sortedStr = new String(chars);
            if (!map.containsKey(sortedStr)) {
                map.put(sortedStr, new ArrayList<String>());
            }
            map.get(sortedStr).add(str);
            int c = str.codePointAt(0);
            
        }
        
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        
        return result;
    }
	
	/**
	 * 另外一种做法
	 * If the average length of verbs is m and array length is n, 
	 * then the time is O(n*m).
	 */
	public List<List<String>> groupAnagrams1(String[] strs) {
	    List<List<String>> result = new ArrayList<List<String>>();
	 
	    HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	    for(String str: strs){
	        char[] arr = new char[26];
	        for(int i=0; i<str.length(); i++){
	            arr[str.charAt(i)-'a']++;
	        }
	        String ns = new String(arr);
	 
	        if(map.containsKey(ns)){
	            map.get(ns).add(str);
	        }else{
	            ArrayList<String> al = new ArrayList<String>();
	            al.add(str);
	            map.put(ns, al);
	        }
	    }
	 
	    result.addAll(map.values());
	 
	    return result;
	}
	
	/**
	 * 249. Group Shifted Strings Add to List
	 * "abc" -> "bcd" -> ... -> "xyz"
	 * 
	 *  这题和上面一题的不同，就是字母不一定对应，但是字母之间的关系是对应的，
	 *  这样的话，hashmap里面的key应该存的是关系，比如 abc,  b-a = 1, c-b = 1,就存
	 *  注意，这里要处理负数，万一后面比前面小，要加上26
	 */
	public List<List<String>> groupStrings(String[] strings) {
        HashMap<String, List<String>> map = new HashMap<>();
       
       for (String str : strings) {
           StringBuilder keySb = new StringBuilder();
           for (int i = 1; i < str.length(); i++) {
               int diff = ((int)str.charAt(i) - str.charAt(i-1)) % 26;
               if (diff < 0) { //in case s[i] is smaller than s[i-1]
               	diff += 26;
               }
               keySb.append(diff);
           }
           String key = keySb.toString();
           if (!map.containsKey(key)) {
               map.put(key, new ArrayList<String>());
           }
           map.get(key).add(str);
       }
       
       List<List<String>> result = new ArrayList<>();
       for (Map.Entry<String, List<String>> entry : map.entrySet()) {
           result.add(entry.getValue());
       }
       return result;
   }
	
	/**
	 * follow up是如果考虑所有字符 甚至汉字日文韩文klingon 怎么办. 
	 * trick 在于 unicode take 1 to 4 bytes, 而java charAt() 返回长为2 bytes 的char， 这里要用codepoint 来表征字符
	 */
	public static void getCodePoint(String str) {
		int index = 0;
		while (index < str.length()) {
			int codepoint = str.codePointAt(index);
			System.out.print(codepoint);
			index += Character.charCount(codepoint);
		}
	}
	
	public static void main(String[] args) {
		getCodePoint("A你好");
	}
}
