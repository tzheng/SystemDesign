import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeKSortedList {
	
	/**
	 * 原题  LC 23. Merge k Sorted Lists
	 * 没有什么难度，就是建立一个minHeap，把list元素都加进去，一个一个弹出就好了
	 * 要注意一下null要跳过就行
	 * 
	 * Time O(nlogk)
	 */
	public ListNode mergeKLists(ListNode[] lists) {
	    PriorityQueue<ListNode> minHeap = new PriorityQueue<ListNode>(10, new Comparator<ListNode>(){
			public int compare(ListNode p1, ListNode p2) {
				return p1.val - p2.val; 
			}
		});
		
	    for (ListNode list : lists) {
	    	if (list != null) {
	    		minHeap.offer(list);
	    	}
	    }
		
		ListNode dummy = new ListNode(0);
	    ListNode head = dummy;
	    while (!minHeap.isEmpty()) {
	    	ListNode top = minHeap.poll();
	    	if (top.next != null) {
	    		minHeap.offer(top.next);
	    	}
	    	head.next = top;
	    	head = head.next;
	    }
	    
		return dummy.next;
	}
	
	
	/** Followup 2: 我要返回的不是一个List，而是你写个iterator给我
	 */
	class KSortedListIterator implements Iterator<ListNode> {
		
		PriorityQueue<ListNode> heap;
		public KSortedListIterator(ListNode[] lists) {
			heap = new PriorityQueue<ListNode>(lists.length, new Comparator<ListNode>(){
				public int compare(ListNode p1, ListNode p2) {
					return p1.val - p2.val; 
				}
			});
			for (ListNode list : lists) {
		    	if (list != null) {
		    		heap.offer(list);
		    	}
		    }
		}
		
		public boolean hasNext() {
			return !heap.isEmpty();
		}
		
		public ListNode next() {
			ListNode top = heap.poll();
			if (top.next != null) {
				heap.offer(top.next);
			}
			return top;
		}
		
		@Override
		public void remove() {}
	}
	
	
	/** Followup 2: 我给你的不是ListNode了，而是 Iterators[] iters
	 *  因为我们不能用 Iterator来建立 PriorityQueue， 所以需要一个
	 *  wrapper class， wrapper放当前的值和iterator即可
	 *  
	 *  Time O(nlogk)
	 */
	class IterWrapper implements Comparable<IterWrapper> {
		int value;
		Iterator<Integer> iter;
		
		public IterWrapper(int v, Iterator<Integer> i) {
			value = v;
			iter = i;
		}
		
		public int compareTo(IterWrapper w) {
			return this.value - w.value;
		}
	}
	
	public Iterable<Integer> mergeKLists(Iterator<Integer>[] iters) {
		List<Integer> result = new ArrayList<Integer>();
	    PriorityQueue<IterWrapper> minHeap = new PriorityQueue<IterWrapper>();
		for (Iterator<Integer> iter : iters) {
			if (iter != null && iter.hasNext()) {
				minHeap.offer(new IterWrapper(iter.next(), iter));
			}
		}
		
		while (!minHeap.isEmpty()) {
			IterWrapper top = minHeap.poll();
			result.add(top.value);
			if (top.iter.hasNext()) {
				top.value = top.iter.next();
				minHeap.offer(top);
			}
		}
	    return result;
	}
	
	/**
	 * Followup 3: 我不要你用iterator来了，你给我好好二分
	 * @param args
	 */
	public ListNode mergeKListsDivide(ListNode[] lists) {
		if (lists == null || lists.length == 0) 
			return null;
		return mergeHelper(lists, 0, lists.length - 1);
	}

	private ListNode mergeHelper(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        }
        int mid = start + (end - start) / 2;
        ListNode left = mergeHelper(lists, start, mid);
        ListNode right = mergeHelper(lists, mid + 1, end);
        return mergeTwoSortedLists(left, right);
    }
	
	private ListNode mergeTwoSortedLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                head.next = list1;
                list1 = list1.next;
            } else {
                head.next = list2;
                list2 = list2.next;
            }
            head = head.next;
        }
        head.next = list1 == null?list2:list1;
        return dummy.next;
    }
	
	
	/**
	 * Find smallest range containing elements from k lists
	 * http://www.cdn.geeksforgeeks.org/find-smallest-range-containing-elements-from-k-lists/
	 */
	public void smallestRange(int[][] arr) {
		int k = arr.length;
		PriorityQueue<Pair> heap = new PriorityQueue<Pair>(k, new Comparator<Pair>(){
			public int compare(Pair a, Pair b) {
				return a.val - b.val;
			}
		});
		
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length == 0) { //no valid result;
				return;
			}
			heap.offer(new Pair(arr[i][0], 0, i));
			min = Math.min(arr[i][0], min);
			max = Math.max(arr[i][0], max);
		}
		
		int range = max - min;
		while (heap.size() == k) {
			Pair curr = heap.poll();
			
			if (curr.index + 1 < arr[curr.arrId].length) {
				curr.index += 1;
				curr.val = arr[curr.arrId][curr.index];
				heap.offer(curr);
				max = Math.max(max, curr.val);
			} else {
				break;
			}
			min = heap.peek().val;
			
			if (range > max - min) {
				System.out.println("New Range: " + max + ", " + min);
				range = max - min;
			}
		}
		
		System.out.println("Range is:" + range);
	}
	
	class Pair {
		int val;
		int index;
		int arrId;
		public Pair(int v, int i, int id) { val = v; index = i; arrId = id;}
		
		public String toString() { return val +"";};
	}


	
	public static void main(String[] args) {
		MergeKSortedList clz = new MergeKSortedList();
		int[][] arr = {{4, 7, 9, 12, 15},
				{0, 8, 14, 20},
				{6, 12, 16, 30, 50}
		};
		
		int[][] arr1 = {{4, 10, 15, 24, 26},
						{0, 9, 12, 20},
						{5, 18, 22, 30}};
		clz.smallestRange(arr1);
	}
}
