import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class TaskCoolDown {
//	http://www.cnblogs.com/EdwardLiu/p/5120091.html
//	http://www.cnblogs.com/EdwardLiu/p/5120090.html
		
	/**
	 * Task Cooldown 1, 不能改变task的顺序，求一共多少时间处理。
	 */
	public String coolDown(String s, int k) {
		
		Map<Character, Integer> map = new HashMap<>(); //char -> next avalible time
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		while (i < s.length()) {
			char c = s.charAt(i);
			if (!map.containsKey(c) || sb.length() - map.get(c) > k) {
				map.put(c, sb.length());
				sb.append(c);
				i++;
			} else {
				sb.append('_');
			}
		}
		//index就是一共需要多少时间。
		coolDownLessSpace(s, k);
		System.out.print("Not Rearrange: ");
		return sb.toString();
	}
	/** 上面的解法用了 O(n)的空间，其实没有必要 **/
	public String coolDownLessSpace(String s, int k) {
		System.out.print("Not Rearrange: ");
		Queue<Character> waitList = new LinkedList<>();
		Set<Character> waitSet = new HashSet<>();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		LinkedHashSet<Character> hash = new LinkedHashSet<>();
		while (i < s.length()) {
			char c = s.charAt(i);
			
			if (waitSet.contains(c)) {
				sb.append("_");
				waitList.offer(null);
			} else {
				sb.append(c);
				waitList.offer(c);
				waitSet.add(c);
				i++;
			}
			
			if (waitList.size() > k) {
				Character next = waitList.poll();
				if (next != null) waitSet.remove(next);
			}
		}
		//index就是一共需要多少时间。
		System.out.println(sb.toString() + ", " + sb.length());
		return sb.toString();
	}
	
	/**
	 * Task Cooldown 2, 可以改变task的顺序，求最少多少时间。
	 * 621. Task Scheduler
	 * 
	 * 
	 * 使用一个map来记录每个task出现的频率，放入max heap里面
	 * 使用一个valid来记录每一个task下一次可以安排的时间
	 * 使用一个waitlist来记录当前等待的list
	 * 
	 * 如果heap是空的，但是waitList不是空的，说明没有可以安排的task，把时间增加，直到waitList中有元素可以安排的，再移到heap里面。
	 * 如果heap不是空的，说明有可以安排的task，安排task，然后把valid的时间更新，把task加入waitlist
	 */
	public String coolDown2Heap(String s, int k) {
		System.out.print("Can Rearrange: ");
		Map<Character, Integer> map = new HashMap<>();
		Queue<Map.Entry<Character, Integer>> waitList = new LinkedList<>();
		PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>(10, new Comparator<Map.Entry>(){
			public int compare(Map.Entry o1, Map.Entry o2) {
				return (Integer)o2.getValue() - (Integer)o1.getValue();
			}
		});
		
		for (char c: s.toCharArray()) {
			map.put(c, map.get(c) == null ? 1 : map.get(c) + 1);
		}
		pq.addAll(map.entrySet());
		
		StringBuilder sb = new StringBuilder();
		int count = 0;
		while (!pq.isEmpty() || count < s.length()) {
			if (!pq.isEmpty()) {
				Map.Entry<Character, Integer> curr = pq.poll();
				curr.setValue(curr.getValue()-1);
				sb.append(curr.getKey());
				waitList.offer(curr);
				count++;
			} else {
				sb.append('_');
				waitList.offer(null);
			}
			if (waitList.size() > k) {
				Map.Entry<Character, Integer> available = waitList.poll();
				if (available != null && available.getValue() > 0) {
					pq.offer(available);
				}
			}
		}
		
		
		return sb.toString();
	}
	/**
	 * 用了heap，开了好多数据结构，代码比较复杂，但是时间复杂度比较好
	 * 不用的话也能做，时间 O(n^2), 逻辑比较清晰。
	 */
	public String coolDown2NotHeap(String s, int k) {
		System.out.print("Can Rearrange: ");
		Map<Character, Integer> map = new HashMap<>();
		Map<Character, Integer> valid = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		for (char c: s.toCharArray()) {
			map.put(c, map.get(c) == null ? 1 : map.get(c) + 1);
		}
		
		int time = 0;
		while (!map.isEmpty()) {
			char next = findValidMax(map, valid, time);
			sb.append(next);
			if (next == '_') {
				time++;
				continue;
			}
			if (map.get(next) > 1) {
				map.put(next, map.get(next) - 1);
			} else {
				map.remove(next);
			}
			valid.put(next, time+k+1);
			time++;
		}
		
		return sb.toString();
	}
	
	private char findValidMax(Map<Character, Integer> map, Map<Character, Integer> valid, int time) {
		int max = Integer.MIN_VALUE;
		char res = '_';
		for (char key : map.keySet()) {
			if (map.get(key) > max && (!valid.containsKey(key) ||valid.get(key) <= time)) {
				max = map.get(key);
				res = key;
			}
		}
		return res;
	}
	
	
	public int leastInterval(char[] tasks, int n) {
	    List<int[]> taskMap = new ArrayList<>();
	    for(int i = 0; i < tasks.length; i++) {
	        char c = tasks[i];
	        for(int j = 0; j < 26; j++) {
	            if(taskMap.size() == 0 || (j == 25 && c != 'A' + 25)) {
	                taskMap.add(new int[] {c, 1});
	                break;
	            }
	            if(taskMap.size() > j && taskMap.get(j)[0] == c) {
	                taskMap.get(j)[1]++;
	                break;
	            }
	        }
	    }
	    PriorityQueue<int[]> q = new PriorityQueue<>(tasks.length, new Comparator<int[]>() {
	        @Override
	        public int compare(int[] o1, int[] o2) {
	            if(o1[1] != o2[1]) return o2[1] - o1[1];
	            return o1[0] - o2[0];
	        }
	    });

	    q.addAll(taskMap);

	    int count = 0;
	    while(!q.isEmpty()) {
	        int k = n+1;
	        List<int[]> waitList = new ArrayList<>();
	        while(k > 0 && !q.isEmpty()) {
	            int[] top = q.poll();
	            top[1]--;
	            waitList.add(top);
	            k--;
	            count++;
	        }

	        for(int[] e : waitList) {
	            if(e[1] > 0) q.add(e);
	        }

	        if(q.isEmpty()) break;
	        count = count + k;
	    }
	    return count;
	}
	
	/**
	 * 为了达到O(1)的空间复杂度 需要牺牲一定的时间复杂度 因为已知需要K的cooldown 所以每次遇到一个index i 
	 * 就往回回溯i - k个index 看有没有重复的 有的话就把cooldown加上 这样时间复杂度相当于是n * k
	 */
	
    
	public static void main(String[] args) {
		TaskCoolDown clz = new TaskCoolDown();
		String str = clz.coolDown("1121", 2);
		System.out.println(str + "  " + str.length());
	
		str = clz.coolDown("123123", 3);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown("123456246124", 6);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown("12323", 3);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown("1242353", 4);
		System.out.println(str + "  " + str.length());
		System.out.println();
		
		str = clz.coolDown2Heap("1121", 2);
		System.out.println(str + "  " + str.length());
	
		str = clz.coolDown2Heap("123123", 3);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2Heap("123456246124", 6);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2Heap("12323", 3);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2Heap("1242353", 4);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2Heap("1242353", 4);
		System.out.println(str + "  " + str.length());

		System.out.println();
		
		str = clz.coolDown2NotHeap("1121", 2);
		System.out.println(str + "  " + str.length());
	
		str = clz.coolDown2NotHeap("123123", 3);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2NotHeap("123456246124", 6);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2NotHeap("12323", 3);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2NotHeap("1242353", 4);
		System.out.println(str + "  " + str.length());
		
		str = clz.coolDown2NotHeap("11123", 2);
		System.out.println(str + "  " + str.length());
		
	
	}
	
	
	
}
