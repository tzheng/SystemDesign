import java.util.ArrayList;
import java.util.List;

public class BinarySearchRange {
	
	public static void count(Integer[] arr) {
		
		int i = 0;
		while (i < arr.length) {
			int num = arr[i];
			int upper = search(arr, i, arr.length-1, num);
			System.out.println("Number: " + num + ", " + (upper-i+1));
			i = upper+1;
		}
		
	}

	public static int search(Integer[] arr, int start, int end, int target) {
		int res = start;
		while (start <= end) {
			int mid = start + (end - start)/2;
			if (arr[mid] <= target) {
				res = mid;
				start = mid + 1;
			} else {
				end = mid-1;
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		
		List<Integer> arr = new ArrayList<Integer>();
		for (int i = 0; i < 200; i++) {
			arr.add(1);
		}
		for (int i = 0; i < 50; i++) {
			arr.add(2);
		}
		arr.add(3);
		for (int i = 0; i < 10; i++) {
			arr.add(4);
		}
		
		Integer[] nums = new Integer[arr.size()];
		count(arr.toArray(nums));
	}
}
