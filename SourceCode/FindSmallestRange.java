import java.util.Comparator;
import java.util.PriorityQueue;

public class FindSmallestRange {
	//http://www.geeksforgeeks.org/find-smallest-range-containing-elements-from-k-lists/2 
	
	class ListElement {
		int index;
		int fromArray;
		int value;
		public ListElement(int i, int f, int v) {
			index = i; fromArray = f; value = v;
		}
	}
	
	public void findSmallestRange(int[][] arr, int k) {
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		
		PriorityQueue<ListElement> minHeap = 
				new PriorityQueue<ListElement>(k, new Comparator<ListElement>(){
					public int compare(ListElement a, ListElement b) {
						return a.value - b.value;
					}
				});
		
		for (int i = 0; i < arr.length; i++) {
			minHeap.offer(new ListElement(0, i, arr[i][0]));
			max = Math.max(max, arr[i][0]);
		}
		int range = Integer.MAX_VALUE;
		int start = -1, end = -1;
		int i = 0;
		
		while (i < arr[0].length) {
			ListElement top = minHeap.poll();
			min = top.value;
			if (range > max - min + 1) {
				range = max - min + 1;
				start = min;
				end = max;
			}
			if (top.index + 1 < arr[top.fromArray].length) {
				top.index = top.index + 1;
				top.value = arr[top.fromArray][top.index];
				if (top.value > max) { 
					max = top.value;
				}
				minHeap.offer(top);
			}
			i++;
		}
		System.out.println(start + ", " + end);
	}
	
	public static void main(String[] args) {
		int[][] arr = {{4, 7, 9, 12, 15},
					   {0, 8, 10, 14, 20},
					   {6, 12, 16, 30, 50}};
		
		FindSmallestRange clz = new FindSmallestRange();
		clz.findSmallestRange(arr, 3);
		
	}
}
