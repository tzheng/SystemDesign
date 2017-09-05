import java.util.Arrays;

public class WiggleSort {

	/**
	 * Wiggle Sort 1
	 */
	public void wiggleSort(int[] nums) {
		System.out.println("Before: " + Arrays.toString(nums));
		if (nums.length <= 1)
			return;

		for (int i = 1; i < nums.length; i++) {

		}

		System.out.println("After: " + Arrays.toString(nums));
	}

	/**
	 * Wiggle Sort 2
	 */
	public void wiggleSort2(int[] nums) {
		System.out.println("Before: " + Arrays.toString(nums));
//		Arrays.sort(nums);
		quickSelect(nums, 0, nums.length-1, nums.length/2);
		System.out.println("After: " + Arrays.toString(nums));
		int n = nums.length, mid = n % 2 == 0 ? n / 2 - 1 : n / 2;
		int[] temp = Arrays.copyOf(nums, n);
		int index = 0;
		for (int i = 0; i <= mid; i++) {
			nums[index] = temp[mid - i];
			if (index + 1 < n)
				nums[index + 1] = temp[n - i - 1];
			index += 2;
		}
		
	}
	
	private void quickSelect(int[] nums, int start, int end, int k) {
		if (start >= end)
			return;
		int pivot = nums[start];
		swap(nums, start, end);
		int left = start, right = end;
		while (left < right) {
			while (left < right && nums[left] < pivot) left++;
			while (left < right && nums[right] >= pivot) right--;
			if (left < right) {
				swap(nums, left, right);
			}
		}
		swap(nums, right, end);
		if (right < k) {
			quickSelect(nums, left+1, end, k);
		} else {
			quickSelect(nums, start, right-1, k);
		}
	}
	
	public void quickSelect1(int[] arr, int left, int right, int k) {
		if (left >= right)
			return;
		int pivot = arr[left];
		swap(arr, left, right);
		int i = left, j = right;
		while (i < j) {
			while (i < j && arr[i] < pivot)
				i++;
			while (i < j && arr[j] >= pivot)
				j--;
			if (i < j) {
				swap(arr, i, j);
			}
		}

		swap(arr, j, right);
		if (k < i) {
			quickSelect(arr, left, i - 1, k);
		} else {
			quickSelect(arr, i + 1, right, k);
		}
	}
	
	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
	
	public static void main(String[] args) {
		WiggleSort clz = new WiggleSort();
		int[] nums = {1,5,1,1,6,4};
		clz.wiggleSort2(nums);
	}
	
}
