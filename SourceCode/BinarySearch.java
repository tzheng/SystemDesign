
public class BinarySearch {
	/**
	 * 278. First Bad Version
	 * 简单题不用说了，假如不知道n大小怎么办
	 */
	public int firstBadVersion() {
		int n = 1;
		while (!isBadVersion(n))
			n *= 2;

		int start = n / 2, end = n;
		while (start + 1 < end) {
			int mid = start + (end - start) / 2;
			if (isBadVersion(mid)) {
				end = mid;
			} else {
				start = mid;
			}
		}

		if (isBadVersion(start)) return start;
		else return end;
	}
	
	/**
	 * 165. Compare Version Numbers
	 * 虽然不是binary search，但是因为也是version比较，就放在一起
	 */
	 public int compareVersion(String version1, String version2) {
		 String[] v1 = version1.split("\\.");
		 String[] v2 = version2.split("\\.");
		 
		 int i = 0, j = 0;
		 while (i < v1.length || j < v2.length) {
			 int num1 = i >= v1.length ? 0 : Integer.valueOf(v1[i++]);
			 int num2 = j >= v2.length ? 0 : Integer.valueOf(v2[j++]);
			 if (num1 != num2) {
				 return num1 - num2 > 0 ? 1 : -1;
			 }
		 }
		 return 0;
	 }
	
	
	/**
	 * 34. Search for a Range
	 */
	public int[] searchRange(int[] nums, int target) {
		int[] ret = new int[2];
		ret[0] = findOccurance(nums, target, true);
		ret[1] = findOccurance(nums, target, false);
		return ret;
	}
	
	private int findOccurance(int[] nums, int target, boolean isFirst) {
		int start = 0, end = nums.length-1, res = -1;
		while (start <= end) {
			int mid = start + (end - start)/2;
			if (nums[mid] == target) {
				res = mid;
				if (isFirst) end = mid-1;
				else start = mid+1;
			} else if (nums[mid] > target) {
				end = mid-1;
			} else {
				start = mid+1;
			}
		}
		return res;
	}
	
	/**
	 * 35. Search Insert Position
	 * 实际上就是找第一个大于target的位置，注意最后的处理，有可能全部都不大于 ，那就要end+1
	 */
	public int searchInsert(int[] nums, int target) {
		if (nums.length == 0) return 0;
        int start = 0, end = nums.length - 1;
        //find first number greater than target
        while (start + 1 < end) {
            int mid = start + (end - start)/2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target){
                end = mid;
            } else {
                start = mid;
            }
        }
       if (nums[start] >= target) {
            return start;
        } else if (nums[end] >= target) {
            return end;
        } else {
            return end + 1;
        }
    }

	private boolean isBadVersion(int x) {
		return true;
	}
	
	
	public static void main(String[] args) {
		
	}
}
