import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class SlidingWindow {
	/** 
	 * 239. Sliding Window Maximum
	 * Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
		Window position                Max
		---------------               -----
		[1  3  -1] -3  5  3  6  7       3
		 1 [3  -1  -3] 5  3  6  7       3
		 1  3 [-1  -3  5] 3  6  7       5
		 1  3  -1 [-3  5  3] 6  7       5
		 1  3  -1  -3 [5  3  6] 7       6
		 1  3  -1  -3  5 [3  6  7]      7
		Therefore, return the max sliding window as [3,3,5,5,6,7].
		
	 * 首先来看一个简单的解法，就是用一个maxHeap来实现。第i个数字进来的时候，把i-k移除
	 * 然后加上新的数字，这样就能一直保持一个k大小的heap，heap的顶端是最大的元素
	 * 时间复杂度 O(NlogK), 空间是O(k)
	 */
	public int[] maxSlidingWindowHeap(int[] nums, int k) {
		 if(nums == null || nums.length == 0) return new int[0];
		 int[] res = new int[nums.length - k + 1];
		 PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, Collections.reverseOrder());
		 
		 for (int i = 0; i < nums.length; i++) {
			 if (i >= k) maxHeap.remove(nums[i-k]);
			 maxHeap.offer(nums[k]);
			 if (i+1 >= k) {
				 res[i+1-k] = maxHeap.peek();
			 }
		 }
		 return res;
	}
	
	/**
	 * Heap的解法很容易想到，但是我们可以再优化一下，让时间复杂度变成O(n);
	 * 
	 * 做法就是使用一个LinkedList，存最大值的index，不是值本身。
	 * 当有新元素进来的时候，如果List中有元素所对应的数字比新的数字小
	 * 把这些元素都移除，然后再加新数字的index到list的末尾，始终保持List是一个
	 * 下降序列。这样List的第一个元素就是当前这个window的最大值。
	 * 
	 * 同时，让list的第一个元素 <= i-k的时候，说明滑动窗口已经滑出第一个元素所在
	 * 的位置了， 要把他移除。
	 */
	public int[] maxSlidingWindow(int[] nums, int k) {
		 if(nums == null || nums.length == 0) return new int[0];
		 int[] res = new int[nums.length - k + 1];
		 LinkedList<Integer> window = new LinkedList<Integer>();
		 
		 for (int i = 0; i < nums.length; i++) {
			if (!window.isEmpty() && window.peek() == i - k) {
				window.poll();
			}
			while (!window.isEmpty() && nums[window.peekLast()] < nums[i]) {
				window.removeLast();
			}
			window.offer(i);
			if (i+1 >= k) { 
				res[i+1-k] = nums[window.peek()];
			}
		 }
		 return res;
	}
	
	/**
	 * 再看一题类似的题目 480. Sliding Window Median
	 * 要求中位数，用一个保持降序序列的LinkedList就不行了，这时候只能通过Heap来解决。
	 * 所以在看这题的同时，首先看另外一题。
	 * 
	 * 295. Find Median from Data Stream
	 * 这题的思路就是使用两个Heap，一个最大堆，一个最小堆。
	 * 最大堆和最小堆的大小差距不超过1，这样，最大堆和最小堆的peek()肯定是中位数
	 * 因为最大堆其他节点都比peek小，最小堆所有节点都比peek大，他们大小又一样，
	 * 这样定点肯定就是中位数。
	 */
	class MedianFinder {
	    PriorityQueue<Integer> min = new PriorityQueue();
	    PriorityQueue<Integer> max = new PriorityQueue(1000, Collections.reverseOrder());
	    // Adds a number into the data structure.
	    public void addNum(int num) {
	    	if (max.isEmpty() || num <= max.peek()) {
	            max.offer(num);
	        } else {
	            min.offer(num);
	        }
	        if (max.size() < min.size()) max.offer(min.poll());
	        if (max.size() - 1 > min.size()) min.offer(max.poll());

	    }
	    
	    public double findMedian() {
	        if (max.size() == min.size()) return (max.peek() + min.peek()) /  2.0;
	        else return max.peek();
	    }
	}
	
	/**
	 * 480. Sliding Window Median(H)
	 * 现在回头看这一题，思路就比较清晰了，从头到尾走一遍，然后当i >= k 的时候
	 * 需要把 nums[i-k] 从heap里面移出去，它可能在最大堆，也可能在最小堆
	 * 
	 * 时间复杂度是 O(nk)  -- remove(Object) takes linear time.
	 * 我们可以用TreeSet来做，它的remove是logK 时间复杂度
	 */
	public double[] medianSlidingWindow(int[] nums, int k) {
		double[] ret = new double[nums.length - k +1];
        PriorityQueue<Integer> min = new PriorityQueue();
	    PriorityQueue<Integer> max = new PriorityQueue(k, Collections.reverseOrder());
        
	    for (int i = 0; i < nums.length; i++) {
        	if (max.isEmpty() || max.peek() >= nums[i]) {
        		max.offer(nums[i]);
        	} else {
        		min.offer(nums[i]);
        	}
        	
        	if (i - k >= 0) {
        		if (nums[i-k] <= max.peek()) max.remove(nums[i-k]);
        		else min.remove(nums[i-k]);
        	}
        	if (max.size() < min.size()) max.offer(min.poll());
	        if (max.size() - 1 > min.size()) min.offer(max.poll());
        	
	        if (i+1 - k >= 0) {
	        	//conver to double prevent overflow
	        	if (max.size() == min.size()) ret[i+1-k] = ((double)max.peek() + (double)min.peek()) / 2.0;
	        	else ret[i+1-k] = (double)max.peek();
	        }
        }
	 
	    
        return ret;
    }
	
	
	public static void main(String[] args) {
		SlidingWindow clz = new SlidingWindow();
		int[] nums = {1,1,1,1};
		clz.medianSlidingWindow(nums, 2);
	}
}
