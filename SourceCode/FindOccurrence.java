
public class FindOccurrence {
	public static void main(String[] args) {
		int[] arr = {1, 1, 2, 2, 2, 2, 3};
		int x = 4;
		
		System.out.println(find(arr, x, true));
		System.out.println(find(arr, x, false));
		System.out.println(find1(arr, x, true));
		System.out.println(find1(arr, x, false));
	}
	
	public static int find(int[] arr, int x, boolean first) {
		//find first occur
		int start = 0, end = arr.length-1;
		int res = -1;
		while (start <= end) {
			int mid = start + (end - start)/2;
			if (arr[mid] == x) {
				res = mid;
				if (first) {
					end = mid-1;
				} else {
					start = mid+1;
				}
			} else if (arr[mid] < x) {
				start = mid+1;
			} else {
				end = mid-1;
			}
		}
		return res;
	}
	
	public static int find1(int[] arr, int x, boolean first) {
		//find first occur
		int start = 0, end = arr.length-1;
		while (start +1 < end) {
			int mid = start + (end - start)/2;
			if (arr[mid] == x) {
				if (first) {
					end = mid;
				} else {
					start = mid;
				}
			} else if (arr[mid] < x) {
				start = mid;
			} else {
				end = mid;
			}
		}
		if (first) {
			if (arr[start] == x) {
				return start;
			} else {
				return end;
			}
		} else {
			if (arr[end] == x) {
				return end;
			} else {
				return start;
			}
		}
//		return res;
	}
}
