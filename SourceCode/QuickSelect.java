import java.util.Arrays;

public class QuickSelect {
	
	public void sort(int[] arr, int left, int right) {
		if (left >= right) {
			return;
		}
		int i = left, j = right;
		int pivot = arr[left];
		swap(arr, left, right);
		while (i < j) {
			while (i < j && arr[i] < pivot) {
				i++;
			}
			while (i < j && arr[j] >= pivot) {
				j--;
			}
			if (i < j) {
				swap(arr, i, j);
			}
		}
		swap(arr, right, j);
		sort(arr, left, j-1);
		sort(arr, i+1, right);
	}
	
	 public void swap(int[] nums, int n1, int n2) {
	    	int tmp = nums[n1];
	    	nums[n1] = nums[n2];
	    	nums[n2] = tmp;
	 }
	    
	
	public void quickSelect(int[] arr, int left, int right, int k) {
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
	
	/**
	 * 给出未排序的数组，rearrange 数组，使得奇数位置的num
	 * 比偶数位置的num都要大比如，给出1,2,3,4,5, 期望结果1,4,2,5,3
	 */
	public void rearrange(int[] nums) {
		System.out.println("Before rearrange " + Arrays.toString(nums));
		
		//with extra space
		int[] res = new int[nums.length];
		int left = 0, right = nums.length-1;
		for (int i = 0; i < nums.length; i++) {
			if (i % 2 == 1) {
				res[i] = nums[right--];
			} else {
				res[i] = nums[left++];
			}
		}
		nums = res;
		System.out.println("After rearrange " + Arrays.toString(res));
		
//		int left = 0, right = nums.length-1;
//		int pivot = nums[nums.length % 2 == 0? nums.length/2 : nums.length/2 + 1];
//		while (left < right) {
//			while (left < right) {
//				if (left %2 == 1 && nums[left] < pivot) break;
//				left++;
//			}
//			
//			while (left < right) {
//				if (right %2 == 0 && nums[right] >= pivot) break;
//				right--;
//			}
//			if (left < right) {
//				int tmp = nums[left];
//				nums[left] = nums[right];
//				nums[right] = tmp;
//			}
//		}
		
//		System.out.println("After rearrange " + Arrays.toString(nums));
	}
	
	public int[] rearrange1(int[] nums) {
		this.quickSelect(nums, 0, nums.length-1, nums.length/2+1);
		this.rearrange(nums);
		return nums;
	}
	
	public static void main(String[] args) {
		QuickSelect clz = new QuickSelect();
		int[] arr = {5,1,6,4,3,7,9,2,0,8,10};
		int k = 9;
		
//		clz.sort(arr, 0, arr.length -1);
//		System.out.println(Arrays.toString(arr));
//		
//		arr = new int[]{5,7,10,4,6,2,3,1,15};
//		clz.quickSelect(arr, 0, arr.length-1, arr.length/2);
//		System.out.println(Arrays.toString(arr));
//		
//		clz.rearrange(arr);
		
		
		 System.out.println(Arrays.toString(clz.rearrange1(new int[]{2,3,0,1})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{5,2,3,4,1})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{1,2,3,4,5})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{1,1,1,3,3})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{1,0,1,3,3})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{2,2,0,1,3})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{5,1,5,8,7,5,6,2,3})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{7,3,8,2,5,6,1,5,5})));
	        System.out.println(Arrays.toString(clz.rearrange1(new int[]{0,2,2,2,3})));
	}
}
