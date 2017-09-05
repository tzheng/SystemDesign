
public class FindPeak {
	/**
	 * 162. Find Peak Element
	 * 先来看基础题目，几个重点:
	 * 		1. num[i] ≠ num[i+1]
	 * 		2. 有多个peak，返回一个
	 */
	public int findPeakElement(int[] a) {
		int start = 0, end = a.length - 1;
		while (start < end) {
			int mid = start + (end - start)/2;
			if ((mid == 0 || a[mid-1] < a[mid]) 
					&& (mid == a.length-1 || a[mid] > a[mid+1])) {
				return mid;
			} else if (mid > 0 && a[mid] < a[mid-1]) {
				end = mid-1;
			} else {
				start = mid+1;
			}
		}
		return end;
	}
	
	public int findValleyElement(int[] a) {
		int start = 0, end = a.length - 1;
		while (start < end) {
			int mid = start + (end - start)/2;
			if ((mid == 0 || a[mid-1] > a[mid]) 
					&& (mid == a.length-1 || a[mid] < a[mid+1])) {
				return mid;
			} else if (mid > 0 && a[mid] > a[mid-1]) {
				end = mid-1;
			} else {
				start = mid+1;
			}
		}
		return end;
	}
	
	
	
	/**
	 * 有一个巧妙的变体
	 * Given an array will have either a valley or a mountain, only one, not one of each, find out the index of the valley or peak element
		And with one more assumption: array[i - 1] = array[i] + 1 or array[i - 1] = array[i] - 1.
		Example 1: [1,2,3,4,3,2,1] --> return 3
		Example 2: [6,5,4,3,2,3,4] --> return 4
		
		首先判断isUp or not，仅以isUp为例, 假设A[x] 是最高点，则有：
		(因为题目有个假设  array[i - 1] = array[i] + 1 or array[i - 1] = array[i] - 1， and only one peak/valley)
		A[x] - A[i] = x - i
		A[x] - A[j] = j - x
		
		由此可以解出 x = 1/ 2 * (A[j] - A[i] + i + j)，isdown同理可求
	 */
	public int findPeakOrValleyConstant(int[] a) {
		boolean isUp = a[0] < a[1];
		if (isUp) {
			return (a[a.length-1] - a[0] + a.length - 1) /2;
		} else {
			return (a[0] - a[a.length-1] + a.length - 1) / 2;
		}
	}
	
	/**
	 * 如果没有假设a[i-1] = a[i] +- 1, 但是only one peek/valley
	 *  1,1,2,3,4,3,2,1,1;
	 * 
	 * 就应该正常的二分。找peak，找不到再找valley
	 */
	public void findPeakValley(int[] a) {
	}
	
	/**
	 * 这里一个followup，如果有多个peak，把它们全部找出来。
	 * 二分没意义了，直接O(n)
	 */
	public void findPeakALL(int[] a) {
		for (int i = 0; i < a.length; i++) {
			if ((i == 0 || a[i-1] < a[i]) 
					&& (i == a.length-1 || a[i] > a[i+1])) {
				System.out.println(i);
			} 
		}
	}
	
	public static void main(String[] args) {
		FindPeak clz = new FindPeak();
		int[] a = {2,5,3,6,4,2,1};
		clz.findPeakALL(a);
	}
	
}
