
public class RotatedSortArray {
	/**
	 * 153. Find Minimum in Rotated Sorted Array
	 * 简单版，没有duplicates
	 *
	 * 思路就是二分法，如果是被旋转了，肯定在中间某个点，他比左边小
	 * 这个点左边单调递增，右边也是单调递增。所以取中点之后，有以下几种情况：
	 * 
	 * 原数组： 0 1 2 4 5 6 7
	 * 情况1：  6 7 0 1 2 4 5
	 * 情况2：  2 4 5 6 7 0 1
	 * 1. a[mid] < a[end]:  从mid到end 都是排序好的，min不会在mid+1 -> end 之间, 搜索A[start : mid]
	 * 2. a[mid] > a[end]:  说明start到mid时排序号的，m+1id到end中间肯定有最小，搜 A[mid+1, end]
	 * 
	 */
	 public int findMin(int[] nums) {
		int start = 0, end = nums.length - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if ((mid == 0 || nums[mid] < nums[mid - 1]) 
					&& (mid == nums.length - 1 || nums[mid] < nums[mid + 1])) {
				return nums[mid];
			} else if (nums[mid] < nums[end]) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		return -1;
	 }
	 
	 /**
	  * 154. Find Minimum in Rotated Sorted Array II
	  * 如果可以有duplicate, 这样就很难确定到底是在左边还是右边
	  * 比如  1，1，2，3，1，1，1，1，1
	  * 在mid = end的时候，并不知道最小值到底在左边还是右边，都有可能。
	  * 只能把右边指针减一，这样最坏复杂度是O(n)
	  * 数组没有重复的话是 logn
	  */
	 public int findMinDups(int[] nums) {
		 int start = 0, end = nums.length - 1;
		 while (start + 1 < end) {
			 int mid = start + (end-start)/2;
			 if (nums[mid] < nums[end]) {
				 end = mid;
			 } else if (nums[mid] > nums[end]) {
				 start = mid;
			 } else {
				 end--;  //和没有重复的区别就在这一句判断。
			 }
		 }
		 
		 if (nums[start] <= nums[end]) {
			 return nums[start];
		 } else {
			 return nums[end];
		 }
		
//		int start = 0, end = nums.length - 1, mid = 0;
//		while (start <= end) {
//			mid = start + (end - start) / 2;
//			if (nums[mid] < nums[end]) {
//			    end = mid;
//			} else if (nums[mid] > nums[end]) {
//			    start = mid+1;
//			} else {
//			    end--;
//			}
//		}
//		return nums[start];
	 }
	 
	 /**
	  * 33. Search in Rotated Sorted Array
	  * 看完找最小值，再来看查找某个数字，先是没有重复的array
	  */
	 public int search(int[] nums, int target) {
		 int start = 0, end = nums.length - 1;
		 while (start <= end) {
			 int mid = start + (end-start)/2;
			 if (nums[mid] == target) 
				 return mid;
			 if (nums[mid] < nums[end]) {
				 if (target < nums[mid]) {
					 end = mid-1;
				 } else if (target <= nums[end]) {
					 start = mid+1;
				 } else {
					 end = mid-1;
				 }
			 } else {
				 if (target > nums[mid]) {
					 start = mid+1;
				 } else if (target >= nums[start]) {
					 end = mid-1;
				 } else {
					 start = mid+1;
				 }
			 }
		 }
		 
		return -1;
	 }
	 
	 /**
	  * 81. Search in Rotated Sorted Array II
	  * 变成有重复的了。和找min一样，有重复了以后，就会出现左右都
	  */
	 public boolean searchDups(int[] nums, int target) {
		 int start = 0, end = nums.length - 1;
		 while (start <= end) {
			 int mid = start + (end-start)/2;
			 if (nums[mid] == target) 
				 return true;
			 if (nums[mid] < nums[end]) {
				 if (target < nums[mid]) {
					 end = mid-1;
				 } else if (target <= nums[end]) {
					 start = mid+1;
				 } else {
					 end = mid-1;
				 }
			 } else if (nums[mid] > nums[end]) {
				 if (target > nums[mid]) {
					 start = mid+1;
				 } else if (target >= nums[start]) {
					 end = mid-1;
				 } else {
					 start = mid+1;
				 }
			 } else {
				 end--;
			 }
		 }
		 return false;
	 }
	 
	 /**
	  * I opened up a dictionary to a page in the middle and started flipping through, looking for words I didn't know. I put each word 
	  * I didn't know at increasing indices in a huge array I created in memory. When I reached the end of the dictionary, I started from
	  *  the beginning and did the same thing until I reached the page I started at. 
	  * Now I have an array of words that are mostly alphabetical, except they start somewhere in the middle of the alphabet, reach the end,
	  *  and then start from the beginning of the alphabet. In other words, this is an alphabetically ordered array that has been "rotated." 
	  *  For example: 

			String[] words = new String[]{ 
			"ptolemaic", 
			"retrograde", 
			"supplant", 
			"undulate", 
			"xenoepist", 
			"asymptote", // <-- rotates here! 
			"babka", 
			"banoffee", 
			"engender", 
			"karpatka", 
			"othellolagkage", 
			}; 
			
	  * https://www.careercup.com/question?id=5173759511101440
	  * 
	  * 实际上问题转化成 find min in rotated sorted array with dups
	  */
	 
	 
	 public static void main(String[] args) {
		 RotatedSortArray clz = new RotatedSortArray();
		 int[] nums1 = {3,3,3,1};
		 System.out.println(clz.findMinDups(nums1));
		 
		 int[] nums2 = {2,2, 4, 5, 6, 7, 0, 1, 2};
		 System.out.println(clz.searchDups(nums2, 2));
	 }
	 
}
