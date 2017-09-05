import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SkylineProblem {
	
	/**
	 * 84. Largest Rectangle in Histogram
	 * O(n) solution
	 */
	public int largestRectangleArea(int[] heights) {
		Stack<Integer> stack = new Stack<Integer>();
		int[] height = new int[heights.length + 1];
		height = Arrays.copyOf(heights, heights.length + 1);
		int i = 0, max = 0;
		while (i < height.length) {
			if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
				stack.push(i++);
			} else {
				int top = stack.pop();
				int area = height[top] * (stack.isEmpty() ? i : i - top);
				max = Math.max(max, area);
			}
		}
		return max;
	}
	
	/**
	 * 
	 * 85. Maximal Rectangle
	 * 把问题转化为对每一行，求Largest Rectangle in Histogram
	 * O(MN)
	 */
	public int maximalRectangle(char[][] matrix) {
		int max = 0;
		int m = matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        int[][] height = new int[m][n + 1];
        
        for (int i = 0; i < m; i++) {
        	for (int j = 0; j < n; j++) {
        		if (matrix[i][j] != '0') 
        			height[i][j] = i == 0 ? 1 : height[i-1][j] + 1;
        	}
        }
        
        for (int i = 0; i < m; i++) {
        	int area = largestRectangleArea(height[i]);
        	max = Math.max(max, area);
        }
        return max;
	}
	

	/**
	 * 218. The Skyline Problem
	 * https://briangordon.github.io/2014/08/the-skyline-problem.html
	 * 
	 * 把矩形拆成起点，高度  和终点，高度。排好序，如果这个终点和下一个起点在同一个x上，终点排在前面。
	 * 这样就组成了一堆关键点 keypoint, 顺着x轴扫描，每次扫描到起点，就把起点对应的高度加入到maxHeap里面，
	 * 如果遇到终点，就把maxHeap里面对应高度去掉。 对于每一个位置， maxHeap的顶点就是当前高度的最大值。
	 * 当 maxHeap.peek() != prev 前一个最大值 的时候，说明高度出现了变化，加入到结果里面，然后把prev高度更新。
	 * 如果新加的起点高度比较矮，maxHeap.peek() 就没有变化，因此不会被打印出来。
	 * {{2, 9, 10},{3, 7, 15},{5, 12, 12}};
	 */
	
	

	public ArrayList<int[]> getSkyline1(int[][] buildings) {
		ArrayList<SkyNode> nodes = new ArrayList<SkyNode>();
		ArrayList<int[]> result = new ArrayList<>();
		for (int[] b : buildings) {
			// b[0] left, b[1] right, b[2] height;
			nodes.add(new SkyNode(b[0], b[2], true));
			nodes.add(new SkyNode(b[1], b[2], false));
		}

		Collections.sort(nodes, new Comparator<SkyNode>() {
			public int compare(SkyNode n1, SkyNode n2) {
				if (n1.x != n2.x) {
					return n1.x - n2.x;
				}

				if (n1.left && n2.left) {
					return n1.height - n2.height;
				}

				if (!n1.left && !n2.left) {
					return n1.height - n2.height;
				}

				return n1.left ? -1 : 1;
			}
		});

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
			public int compare(Integer n1, Integer n2) {
				return n2 - n1;
			}
		});

		int prev = 0; // horizon
		pq.offer(0);
		for (SkyNode node : nodes) {
			if (node.left) {
				pq.offer(node.height);
			} else {
				pq.remove(node.height);
			}

			int peek = pq.peek();
			if (peek != prev) { // when there is height change!
				result.add(new int[] { node.x, peek });
				prev = peek;
			}
		}

		return result;
	}

	public ArrayList<int[]> getSkyline(int[][] buildings) {
		ArrayList<int[]> result = new ArrayList<>();
		ArrayList<int[]> height = new ArrayList<>();
		// 拆解矩形，构建顶点的列表
		for (int[] b : buildings) {
			// 左顶点存为负数
			height.add(new int[] { b[0], -b[2] });
			// 右顶点存为正数
			height.add(new int[] { b[1], b[2] });
		}
		// 根据横坐标对列表排序，相同横坐标的点纵坐标小的排在前面
		Collections.sort(height, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if (a[0] != b[0]) {
					return a[0] - b[0];
				} else {
					return a[1] - b[1];
				}
			}
		});
		// 构建堆，按照纵坐标来判断大小
		Queue<Integer> pq = new PriorityQueue<Integer>(11, new Comparator<Integer>() {
			public int compare(Integer i1, Integer i2) {
				return i2 - i1;
			}
		});

		// 将地平线值0先加入堆中
		pq.offer(0);
		// prev用于记录上次keypoint的高度
		int prev = 0;
		for (int[] h : height) {
			// 将左顶点加入堆中
			if (h[1] < 0) {
				pq.offer(-h[1]);
			} else {
				// 将右顶点对应的左顶点移去
				pq.remove(h[1]);
			}
			int cur = pq.peek();
			// 如果堆的新顶部和上个keypoint高度不一样，则加入一个新的keypoint
			if (prev != cur) {
				result.add(new int[] { h[0], cur });
				prev = cur;
			}
		}
		return result;
	}
	
	
	/**
	 * 11. Container With Most Water
	 */
	public int maxArea(int[] heights) {
		int left = 0, right = heights.length-1, max = 0;
		while (left < right) {
			int area = Math.min(heights[left], heights[right]) * (right - left);
			max = Math.max(area, max);
			if (heights[left] < heights[right]) {
				left++;
			} else {
				right--;
			}
		}
		return max;
	}
	
	/**
	 * 42. Trapping Rain Water
	 * 第一个， O(N)空间。
	 * 思路就是保持一个 stack， 保持一个降序的stack
	 */
	public int trap(int[] a) {
        if (a == null || a.length == 0) return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int i = 0, sum = 0, currSum = 0;
        while (i < a.length) {
        	if (stack.isEmpty() || a[i] <= a[stack.peek()]) {
        		stack.push(i++);
        	} else {
        		int top = stack.pop();
        		currSum = stack.isEmpty() ? 0 : (Math.min(a[stack.peek()], a[i]) - a[top]) * (i - stack.peek() - 1);
        		sum += currSum;
        	}
        }
        return sum;
    }
	/**
	 * 另外一个解法，O(1) 的空间，O(n)时间
	 */
	public int trapTwoPointer(int[] A) {
	    if (A.length < 3) return 0;
	    
	    int ans = 0;
	    int l = 0, r = A.length - 1;
	    
	    // find the left and right edge which can hold water
	    while (l < r && A[l] <= A[l + 1]) l++;
	    while (l < r && A[r] <= A[r - 1]) r--;
	    
	    while (l < r) {
	        int left = A[l];
	        int right = A[r];
	        if (left <= right) {
	            // add volum until an edge larger than the left edge
	            while (l < r && left >= A[++l]) {
	                ans += left - A[l];
	            }
	        } else {
	            // add volum until an edge larger than the right volum
	            while (l < r && A[--r] <= right) {
	                ans += right - A[r];
	            }
	        }
	    }
	    return ans;
	}

	public static void main(String[] args) {
		SkylineProblem clz = new SkylineProblem();
//		int[][] buildings = { { 0, 2, 3 }, { 2, 5, 3 } };
		int[][] buildings = {{2, 9, 10},{3, 7, 15},{5, 12, 12}};
		 clz.getSkyline1(buildings);
//		clz.getSkyline(buildings);
		 
		 int[] rain = {0,1,0,2,1,0,1,3,2,1,2,1};
		 System.out.println(clz.trap(rain));
	}
}

class SkyNode {
	int x;
	int height;
	boolean left;

	public SkyNode(int x, int height, boolean isStart) {
		this.x = x;
		this.height = height;
		this.left = isStart;
	}
	
	public String toString() {
		return x + "," + height + left;
	}
}