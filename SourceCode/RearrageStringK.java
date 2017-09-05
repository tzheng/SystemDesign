import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class RearrageStringK {
	
	/**
	 * Rearrange String k Distance Apart 按距离为k隔离重排字符串
	 * 
	 * 首先来看原题，这是一道Hard的题目
	 * 
	 * -- 然后看 TaskCoolDown.java
	 */
	public String rearrangeString(String s, int k) {
		StringBuilder rearranged = new StringBuilder();
		// count frequency of each char
		Map<Character, Integer> map = new HashMap<>();
		for (char c : s.toCharArray()) {
			if (!map.containsKey(c)) {
				map.put(c, 0);
			}
			map.put(c, map.get(c) + 1);
		}

		// construct a max heap using self-defined comparator, which holds all
		// Map entries, Java is quite verbose
		PriorityQueue<Map.Entry<Character,Integer>> maxHeap 
			= new PriorityQueue<Map.Entry<Character, Integer>>(10, new Comparator<Map.Entry<Character,Integer>>(){
				public int compare(Map.Entry<Character,Integer> e1, Map.Entry<Character,Integer> e2) {
					return e2.getValue() - e1.getValue();
				}
			});

		Queue<Map.Entry<Character, Integer>> waitList = new LinkedList<>();
		maxHeap.addAll(map.entrySet());

		while (!maxHeap.isEmpty()) {

			Map.Entry<Character, Integer> current = maxHeap.poll();
			rearranged.append(current.getKey());
			current.setValue(current.getValue() - 1);
			waitList.offer(current);

			if (waitList.size() < k) { // intial k-1 chars, waitQueue not full
										// yet
				continue;
			}
			// release from waitQueue if char is already k apart
			Map.Entry<Character, Integer> front = waitList.poll();
			// note that char with 0 count still needs to be placed in waitQueue
			// as a place holder
			if (front.getValue() > 0) {
				maxHeap.offer(front);
			}
		}

		return rearranged.length() == s.length() ? rearranged.toString() : "";	
	}
	
	/**
	 * 另外一个很直接的办法 ，如果不是 26个字母的话，复杂度是O(n^2), 如果是26字母，可以当做O(N)
	 */
	public String rearrangeStringTwoArray(String str, int k) {
        int length = str.length();
        int[] count = new int[26];
        int[] valid = new int[26];
        for(int i=0;i<length;i++){
            count[str.charAt(i)-'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for(int index = 0;index<length;index++){
            int candidatePos = findValidMax(count, valid, index);
            if( candidatePos == -1) return "";
            count[candidatePos]--;
            valid[candidatePos] = index+k;
            sb.append((char)('a'+candidatePos));
        }
        return sb.toString();
    }
    
   private int findValidMax(int[] count, int[] valid, int index){
       int max = Integer.MIN_VALUE;
       int candidatePos = -1;
       for(int i=0;i<count.length;i++){
           if(count[i]>0 && count[i]>max && index>=valid[i]){
               max = count[i];
               candidatePos = i;
           }
       }
       return candidatePos;
   }
	
	
	
	public String rearrangeString1(String s, int k) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		int[] valid = new int[26];
		for (char c : s.toCharArray()) {
			if (!map.containsKey(c)) {
				map.put(c, 1);
			} else {
				map.put(c, map.get(c) + 1);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			int max = -1;
			char next = ' ';
			//find next valid char,  1. count is max among remaining, 2. it's in valid position
			for (Character c : map.keySet()) {
				if (map.get(c) > max && i >= valid[c-'a']) {
					max = map.get(c);
					next = c;
				}
			}
			if (next == ' ') return "";
			int nextCount = map.get(next) - 1;
			if (nextCount == 0) {
				map.remove(next);
			} else {
				map.put(next, nextCount);  //count --
			}
			valid[next-'a'] = i+k;  //valid position is k index away
			sb.append(next);
		}
		
		return sb.toString();
	}
	
	
    
	public static void main(String[] args) {
		RearrageStringK clz = new RearrageStringK();
		System.out.println(clz.rearrangeString("aabbcc", 3));
		System.out.println(clz.rearrangeString("aaabbcc", 3));
	}
}
