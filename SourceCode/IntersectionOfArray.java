import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntersectionOfArray {
	/**
	 * 349. Intersection of Two Arrays
	 */
	public int[] intersection(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
       
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0, index = 0;
        
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                if (index == 0 || nums1[i] != result[index-1] ) {
                    result[index++] = nums1[i];
                }
                i++;
                j++;
            }
        }
        
        int[] ret = new int[index];
        for (int k = 0; k < index; k++) {
            ret[k] = result[k];
        }
        
        return ret;
    }
	
	/**
	 * 350. Intersection of Two Arrays II
	 * What if the given array is already sorted? How would you optimize your algorithm?
	 *		用binary search
	 * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
	 *
	 */
	public static int[] intersect(int[] nums1, int[] nums2) {
		if (nums1.length > nums2.length) {
			return intersect(nums2, nums1);
		}
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		
		//num1 shorter than num2, for each element in num1, find 
		int last = 0, index = 0;
		int[] result = new int[nums1.length];
		
		for (int n : nums1) {
			int i = search(nums2, n, last);
			if (i != -1) {
				result[index++] = n;
				last = i+1;
			}
		}
		
		int[] ret = new int[index];
        for (int k = 0; k < index; k++) {
            ret[k] = result[k];
        }
        
        return ret;
	}
	
	public static int search(int[] a, int k, int start) {
		int end = a.length - 1;
		int res = -1;
		while (start <= end) {
			int mid = start + (end-start)/2;
			if (a[mid] >= k) {
				if (a[mid] == k) res = mid;
				end = mid-1;
			} else {
				start = mid+1;
			}
		}
		return res;
	}
	
	/**
	 * 如果num1实在太大，硬盘装不下，就建立索引
	 */
	public int[] intersection2(int[] nums1, int[] nums2) {
        // Write your code here
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums1.length; ++i) {
            if (map.containsKey(nums1[i]))
                map.put(nums1[i], map.get(nums1[i]) + 1); 
            else
                map.put(nums1[i], 1);
        }

        List<Integer> results = new ArrayList<Integer>();

        for (int i = 0; i < nums2.length; ++i)
            if (map.containsKey(nums2[i]) &&
                map.get(nums2[i]) > 0) {
                results.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i]) - 1); 
            }

        int result[] = new int[results.size()];
        for(int i = 0; i < results.size(); ++i)
            result[i] = results.get(i);

        return result;
    }

	
	public static void main(String[] args) {
		int[] a = {3,1,2};
		int[] b = {1,1};
		intersect(a, b);
	}
	
}
