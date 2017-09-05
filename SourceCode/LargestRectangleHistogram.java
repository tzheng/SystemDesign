import java.util.Arrays;
import java.util.Stack;

public class LargestRectangleHistogram {
	public int largestRectangleAreaNaive(int[] heights) {
		if (heights.length == 0) {
			return 0;
		}
		
		int max = 0;
		
		//最暴力的解法是2次循环
		for (int i = 0; i < heights.length; i++) {
			//这里做一个小优化，如果前一个比现在的高，
			//那么从前一个矩形到末尾的最大值，肯定比现在这个矩形到末尾的最大值多，
			if (i > 0 && heights[i-1] >= heights[i]) {
		        continue;
		    }
			int minH = heights[i];
			int tmpMax = minH;
			for (int j = i+1; j < heights.length; j++) {
				minH = Math.min(minH, heights[j]);
				tmpMax = Math.max(tmpMax, minH * (j-i+1));
			}
			max = Math.max(tmpMax, max);
		}
		
		return max;
	}
	
	/**
	 * 我们要优化到 O(n)
	 * http://www.cnblogs.com/lichen782/p/leetcode_Largest_Rectangle_in_Histogram.html
	 * 
	 * 从暴力破解的小优化来看，如果前一个比现在的高，那么从前一个矩形到末尾的最大值，
	 * 肯定比现在这个矩形到末尾的最大值多，反过来说，如果当前矩形比前一个矩形高，那就是前一个矩形的高度制约了面积
	 * 
	 * stack里面只存放单调递增的索引，如果高度一直增加，不断push
	 * 如果高度下降了，说明最新这个是短板，就算之前能组成的高度并更新。
	 */
	public int largestRectangleArea(int[] heights) {
		if (heights.length == 0) {
			return 0;
		}
		
		int[] height = new int[heights.length + 1];
        height = Arrays.copyOf(heights, heights.length + 1);
        
        Stack<Integer> stack = new Stack<Integer>();
        int i = 0;
        int max = 0;
        while (i  < height.length) {
            if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
                stack.push(i++);
            } else {
                int top = stack.pop();
                int area = height[top] * (stack.isEmpty()? i : i - stack.peek() - 1);
                max = Math.max(max, area);
            }
        }
        
        return max;
	}
	
	
	public static void main(String[] args) {
		LargestRectangleHistogram clz = new LargestRectangleHistogram();
		int[] heights = {1,2};
		System.out.println(clz.largestRectangleAreaNaive(heights));
	}
}
