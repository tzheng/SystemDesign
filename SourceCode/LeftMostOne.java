
public class LeftMostOne {
	
	
	/**
	 * .给一个2d matrix，每个里面值要么是1要么是0， 假如出现1，后面的数都是1.
		找出最左边是1的列
		[[0, 0, 1, 1, 1],
		[0, 1, 1, 1, 1],
		[0, 0, 1, 1, 1],
		[0, 0, 0, 0, 0]]

	 */
	public static void find(int[][] a) {
		int range = a[0].length-1;
		
		for (int i = 0; i < a.length; i++) {
			int leftMost = binarySearch(a[i], 0, range);
			if (leftMost < range) {
				range = leftMost;
			}
			System.out.println("Row : " + i + ", " + leftMost);
		}
		
		System.out.println("Left most column is: " + range);
	}
	
	private static int binarySearch(int[] a, int start, int end) {
		if (a[end] == 0) {
			return end;
		}
		
		int res = end;
		while (start <= end) {
			int mid = start + (end-start)/2;
			if (a[mid] == 1) {
				res = mid;
				end = mid-1;
			} else {
				start = mid+1;
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		int[][] a = {{0, 0, 0, 0, 0},
		     		 {0, 0, 0, 0, 0},
		    		 {0, 0, 0, 0, 0},
		    		 {0, 0, 0, 0, 0}};
		find(a);
	}
}
