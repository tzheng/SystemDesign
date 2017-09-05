import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 295. Find Median from Data Stream
 *
 */
public class FindKLargest {
	
	/**
	 * 4. Median of Two Sorted Arrays
	 * 简单的办法就是走一遍，存到heap里面，这样时间 O((m+n)*log(m+n)), 空间m+n
	 */
	public double findMedianSortedArrays(int[] A, int[] B) {
        int len = A.length + B.length;
        if (len % 2 == 0) {
        	return (findKth(A, 0, B, 0, len / 2) + findKth(A, 0, B, 0, len / 2 + 1)) / 2.0;
        } else {
        	return findKth(A, 0, B, 0, len / 2 + 1);
        }
    }
	
	/**
	 * 实际上就是找第k大的元素
	 * http://www.programcreek.com/2012/12/leetcode-median-of-two-sorted-arrays-java/
	 *  Base case: 
	 *  	1. If any of the two arrays is empty, then the kth element is the non-empty array's kth element. 
	 *  	2.If k == 0, the kth element is the first element of A or B.
	 *  Normal Case:
	 *  		 1					2
	 *  	a0 .... ak/2-1 .. ak/2 .... am-1
	 *  	b0 .... bk/2-1 .. bk/2 .... bn-1
	 *  		 3					4
	 *  
	 *  if (a[k/2-1] > b[k/2-1], means kth element couldn't be in the first half of b, drop section 3
	 *  if (a[k/2-1] < b[k/2-1], means kth element couldn't be in the first half of a, drop section 1	
	 *  
	 *  seach k-k/2 element in rest
	 *  
	 * 简单的说，就是或者丢弃最大中位数的右区间，或者丢弃最小中位数的左区间。
	 */
	public double findKth(int[] A, int s1, int[] B, int s2, int k) {
		if (s1 >= A.length) return B[s2+k-1];
		if (s2 >= B.length) return A[s1+k-1];
		if (k == 1) return Math.min(A[s1], B[s2]); //最小的那个，因为要选的是merge后的第一个元素
		
		int m1 = s1 + k/2-1;
		int m2 = s2 + k/2-1;
		
		int mid1 = m1 < A.length ? A[m1] : Integer.MAX_VALUE;
		int mid2 = m2 < B.length ? B[m2] : Integer.MAX_VALUE;
		if (mid1 < mid2) {
			return findKth(A, m1+1, B, s2, k - k/2); // drop first half of A
		} else {
			return findKth(A, s1, B, m2+1, k-k/2); //drop first half of b;
		}
	}
	
	/**
	 * 题目可以进化成 378. Kth Smallest Element in a Sorted Matrix
	 * 先看heap的解法 n*m logk
	 */
	public int kthSmallest(int[][] matrix, int k) {
		//维持一个k大小的maxHeap, 
		 PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(k, Collections.reverseOrder());
		 for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                maxHeap.offer(matrix[i][j]);
                if (maxHeap.size() > k) {
                    maxHeap.poll();
                }
            }
        }
		return maxHeap.peek();
	}
	
	/**
	 * 但是即使是heap的解法，也还是能优化的，因为我们只需要k个
	 *  1. Build a minHeap of elements from the first row.
	 *  2. Do the following operations k-1 times :
	 *     Every time when you poll out the root(Top Element in Heap), 
	 *     you need to know the row number and column number of that 
	 *     element(so we can create a tuple class here), replace that 
	 *     root with the next element from the same column.
	 *     
	 *  因为每一行和每一列都是有序的了，因此维护一个最小堆，每次取出堆顶的元素，然后把它
	 *  下方的元素放进堆(如果是第一行，还要把它右边的元素放入，这样不会重复。)
	 *  这样做K次后堆顶元素就是第K大的了。这样复杂度为多少呢？O(KlogK) 然而K最坏情况是n^2
	 */
	public int kthSmallestHeap(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>();
        for(int j = 0; j <= n-1; j++) pq.offer(new Tuple(0, j, matrix[0][j]));
        for(int i = 0; i < k-1; i++) {
            Tuple t = pq.poll();
            if(t.x == n-1) continue;
            pq.offer(new Tuple(t.x+1, t.y, matrix[t.x+1][t.y]));
        }
        return pq.poll().val;
    }
	
	class Tuple implements Comparable<Tuple> {
	    int x, y, val;
	    public Tuple (int x, int y, int val) {
	        this.x = x;
	        this.y = y;
	        this.val = val;
	    }
	    
	    public int compareTo(Tuple that) {
	        return this.val - that.val;
	    }
	}
	
	
	/**
	 * 如果不用额外空间，那么就应该做binary search了。
	 * 这里的binary search并不是对于 index做，因为纵横都是排好序的，没有办法一行一行找， 比如
	 *   1 5 6
	 *   2 7 8
	 *   3 8 9
	 * 所以这里二分查找只能根据数字的range来做。
	 * O(nlogm) while m = max - min.
	 */
	public int kthSmallestBinary(int[][] matrix, int k) {
		int n = matrix.length;
        int l = matrix[0][0], h = matrix[n-1][n-1];
        while (l < h) {
            int m = l + (h - l)/2;
            int count = binarySearch(matrix, m);
            if (count < k) {
                l = m + 1;
            } else {
                h = m;
            }
        }
        return l;
	}
	public int binarySearch(int[][] matrix, int target) {
        int n = matrix.length, i = n-1, j = 0;
        int res = 0;
        while (i >= 0 && j <= n-1) {
            if (matrix[i][j] > target) i--;
            else {
                // 如果 matrix[i][j] <= target, 我们知道 matrix[0][j] - matrix[i][j]都符合条件，所以加上i-1
                res += i + 1;  
                j++;
            }
        }
        return res;
    }
	
	/**
	 * 295 Find Median from Data Stream   
	 */
	class MedianOfDataStream {
		PriorityQueue<Integer> minHeap;
	    PriorityQueue<Integer> maxHeap;
	    
	    PriorityQueue<Integer> min = new PriorityQueue();
	    PriorityQueue<Integer> max = new PriorityQueue(1000, Collections.reverseOrder());
	    // Adds a number into the data structure.
	    public void addNum(int num) {
	        max.offer(num);
	        min.offer(max.poll());
	        if (max.size() < min.size()){
	            max.offer(min.poll());
	        }
	    }
	
	    // Returns the median of current data stream
	    public double findMedian() {
	        if (max.size() == min.size()) return (max.peek() + min.peek()) /  2.0;
	        else return max.peek();
	    }
	}
	
	
	
	
	public static void main(String[] args) {
		FindKLargest clz = new FindKLargest();
		System.out.println(clz.findMedianSortedArrays(new int[]{1, 2,3}, new int[]{1,2,2}));
	}
}
