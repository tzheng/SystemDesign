import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class FirstIndexOfAnagram {
	
	/**
	 * Simple brute force,  O(n*k)
	 * @param str  length = n
	 * @param t		length = k
	 */
	private void firstIndex(String str, String t) {
		int len = t.length();
		
		int[] arr = new int[26];
		for (char c : t.toCharArray()) {
			arr[c-'a']++;
		}
		
		int[] moving = new int[26];
		for (int i = 0; i < len; i++) {
			moving[str.charAt(i) - 'a']++;
		}
		
		int i = 0, j = len-1;
		while (j < str.length()) {
			if (match(moving, arr)) {
				System.out.println("Found anagram: " + i + ", " + str.substring(i, j+1));
				return;
			}
			if (i == str.length() - t.length()) 
				break;
			
			moving[str.charAt(i) - 'a']--;
			i++;
			j++;
			moving[str.charAt(j) - 'a']++;
		}
		System.out.println("Not Found");
	}

	private boolean match(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) return false;
		}
		return true;
	}
	
	/**
	 * O(N) solution, two pointer
	 * @param args
	 */
	public int firstindexofanagram(String original, String target){
	    int count = target.length();
	    int[] map = new int[26];
	    for(char ch : target.toCharArray()) {
	    	map[ch-'a']++;
	    }
	    
	    int tmpdis = 0, i=0, j=0, len = original.length();
	    
	    while(j<len){
	          if(map[original.charAt(j) - 'a'] > 0) {
	                 map[original.charAt(j) - 'a']--;
	                 j++;
	                 if(++tmpdis==count) {
	                	 System.out.println("Found anagram: " + i + ", " + original.substring(i, j));
	                	 return i;
	                 }
	          }else{
	                 map[original.charAt(i) - 'a']++;    
	                 //这里比较tricky，比如在teacher里面找er,那第一个t是不存在的，我就把i往后移动并且更新Map
	                 i++;
	                 tmpdis--;
	          }
	          System.out.println(Arrays.toString(map));
	    }
	    return -1;
	    
	    
	}
	
	public static void main(String[] args) {
		FirstIndexOfAnagram clz = new FirstIndexOfAnagram();
		clz.firstIndex("teacher", "reh");
		clz.firstindexofanagram("teacher", "reh");
//		clz.groupStrings(new String[]{"ba", "az"});
		System.out.println(-1 % 26);
	}
}
