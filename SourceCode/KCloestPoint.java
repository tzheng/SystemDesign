import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class KCloestPoint {
	
	/**
	 * Top K Frequent Elements
	 * 1. 统计频率，建立一个数字->频率的 map
	 * 2. 遍历map，建立一个大小为k的minHeap，这样最后的peek 就是第k大的数字
	 */
	public void findKMostFrequent(int[] nums, int k) {
		if (k > nums.length) return;
		Map<Integer, Integer> map = new HashMap<>();
		for (int num : nums) {
			map.put(num, map.containsKey(num) ? map.get(num) + 1 : 1);
		}
		PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(k,
				new Comparator<Map.Entry<Integer, Integer>>() {
					@Override
					public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
						return o1.getValue() - o2.getValue();
					}
				});

		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			minHeap.offer(entry);
			if (minHeap.size() > k) {
				minHeap.poll();
			}
		}
		
		System.out.println(k + " most frequent is :" + minHeap.peek().getKey());
	}
	
	/**
	 * 非 leetcode题目
	 * Find the K closest points to the origin in a 2D plane, given an array containing N points.
	 * 先看一个最容易想到的heap的解法，时间复杂度是 O(nlogk) 
	 */
	public void kclosestPointHeap(ArrayList<Point> points, int k) {
		// max heap
		PriorityQueue<Point> pq = new PriorityQueue<Point>(k, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				return (p2.x * p2.x + p2.y * p2.y) - (p1.x * p1.x + p1.y * p1.y);
			}
		});

		for (Point point : points) {
			pq.offer(point);
			if (pq.size() > k) {
				pq.poll();
			}
		}

		while (!pq.isEmpty()) {
			Point peek = pq.poll();
			System.out.println(peek.x + ", " + peek.y);
		}
	}
	
	Point center;
	public void kclosestPointToCenter(ArrayList<Point> points, Point c, int k) {
		// max heap
		this.center = c;
		PriorityQueue<Point> pq = new PriorityQueue<Point>(k, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				return dist(p1) - dist(p2);
			}
			
			public int dist(Point p) {
				return (p.x - center.x) * (p.x - center.x) + (p.y - center.y) * (p.y - center.y);
			}
		});

		for (Point point : points) {
			pq.offer(point);
			if (pq.size() > k) {
				pq.poll();
			}
		}

		while (!pq.isEmpty()) {
			Point peek = pq.poll();
			System.out.println(peek.x + ", " + peek.y);
		}
	}
	/**
	 * 除了Heap的解法之外，我们还可以用quick select来解决。
	 * quick select就是快速排序的变体，这里我们直接用quick select来实现
	 * 215. Kth Largest Element in an Array
	 * 		Kth Smallest Element in an Array
	 * 
	 * instead of recursing into both sides, as in quicksort, 
	 * quickselect only recurses into one side – the side with 
	 * the element it is searching for. This reduces the average 
	 * complexity from O(n log n) to O(n), with a worst case of O(n2).
	 */
	public int findKthLargest(int[] arr, int k) {
		if (arr == null || arr.length == 0) return 0;
        if (k <= 0) return 0;
        //if find k smallest, quickSelect(arr, 0, arr.length-1, k - 1);
        //else as below
        return quickSelect(arr, 0, arr.length-1, arr.length - k + 1);
	}
	
	public int quickSelect(int[] arr, int left, int right, int k) {
		if (left == right) {
			return arr[left];
		}
		
		int pivot = arr[right];
		int i = left, j = right;
		while (i < j) {
			while (i < j && arr[i] < pivot) i++;
			while (i < j && arr[j] >= pivot) j--;
			if (i < j) {
				swap(arr, i, j);
			}
		}
		swap(arr, j, right);
		if (k == i+1) {
			return pivot;
		} else if (k < i+1) {
			return quickSelect(arr, left, i-1, k);
		} else {
			return quickSelect(arr, i+1, right, k);
		}
	}
	
	public void swap(int[] nums, int n1, int n2) {
    	int tmp = nums[n1];
    	nums[n1] = nums[n2];
    	nums[n2] = tmp;
    }
	
	
	public static void main(String[] args) {
		KCloestPoint clz = new KCloestPoint();
		clz.findKMostFrequent(new int[]{1, 2,3,4,5,5,5,6,6,6,6,4,2}, 2);
		
		ArrayList<Point> points = new ArrayList<Point>();
		int k = 3;
		points.add(new Point(1, 2));
		points.add(new Point(1,3));
		points.add(new Point(2,2));
		points.add(new Point(4,4));
		points.add(new Point(1,1));
		points.add(new Point(3,5));
		points.add(new Point(0,1));
		clz.kclosestPointHeap(points, k);
		
		int[] nums = {1,4,3,6,5,7,2};
		System.out.println(clz.findKthLargest(nums, 2));
	}
}
