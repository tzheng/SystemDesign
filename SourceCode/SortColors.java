import java.util.Arrays;

public class SortColors {
	
	/**
	 * 75. Sort Colors
	 * 先看两个简单解法，就是partition array的办法
	 * 这样的解法是 2n 的时间，不需要额外空间。
	 */
	public void sortColorsPartition(int[] nums) {
        partition(nums, 2);
        partition(nums, 1);
    }
	
	private void partition(int[] nums, int pivot) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            while (i <= j && nums[i] < pivot) i++;
            while (i <= j && nums[j] >= pivot) j--;
            if (i <= j) {
                swap(nums, i, j);
                i++;
                j--;
            }
        }
    }
	
	private void swap(int[] nums, int i, int j) {
		 int tmp = nums[i];
         nums[i] = nums[j];
         nums[j] = tmp;
	}
	
	/**
	 * 但是由于数字只有三个，实际上可以一遍解决
	 * 思路就是如果是0，换到左边，如果是2，换到右边，不然就不动
	 */
	public void sortColorsOnepass(int[] nums) {
		int i = 0, left = 0, right = nums.length - 1;
		// i <= right, not i < nums.length !!!eg.[2, 2]; not i < right !!!eg.[1,0];
		while (i <= right) {
			if (nums[i] == 0) {
				swap(nums, i, left);
				left++;  //left side of left pointer are all 0, between left & i are all 1
				i++; //i++ cuz we know what we swap from left pointer is either 0 or 1
			} else if (nums[i] == 1) {
				i++;
			} else {
				swap(nums, i, right);
				right--;  ////we can't i++ cuz we don't know what we swapped from right pointer, so we still need to check later
			}
		}
	}
	
	/**
	 * 变种，数字不定了，对于每一个数字，有个API getCategory(int n)， return {L| M| H} 三种category.
	 * 解法无非是在数字比较的时候，换成category比较就好了。
	 */
	public void sortCatagory(int[] nums) {
		int i = 0, left = 0, right = nums.length - 1;
		// i <= right, not i < nums.length !!!eg.[2, 2]; not i < right !!!eg.[1,0];
		while (i <= right) {
			if (isLow(nums[i])) {
				swap(nums, i, left);
				left++;  //left side of left pointer are all 0, between left & i are all 1
				i++; //i++ cuz we know what we swap from left pointer is either 0 or 1
			} else if (isMid(nums[i])) {
				i++;
			} else {
				swap(nums, i, right);
				right--;  ////we can't i++ cuz we don't know what we swapped from right pointer, so we still need to check later
			}
		}
	}
	
	/**
	 * 然后把问题扩展为k种颜色，就是lintcode上面的 Sort Colors II
	 * Example
	 * Given colors=[3, 2, 2, 1, 4], k=4, your code should sort colors in-place to [1, 2, 2, 3, 4].
	 * 
	 * 如果继续用partition来做，最后复杂度是 O(kn), 每一种颜色都要partition一次。为了下降复杂度，有两种做法
	 * 1. in place的做法，彩虹排序。 复杂度稍微高 O(nlogk), 思路就是二分，找一个mid，先按照mid大小分
	 * 2. 需要额外空间的做法，counting sort。
	 */
	public void sortColorsRainbow(int[] nums, int k) {
        if (nums == null || nums.length  == 0) {
            return;
        }
        rainbow(nums, 0, nums.length- 1, 1, k);
	}
	
	private void rainbow(int[] nums, int left, int right, int colorFrom, int colorTo) {
		if (colorFrom == colorTo) return;
		if (left >= right) return;
		
		int colorMid = colorFrom + (colorTo - colorFrom)/2;
		int start = left, end = right;
		while (start <= end) {
			while (start <= end && nums[start] < colorMid) {
				start++;
			}
			while (start <= end && nums[end] >= colorMid) {
				end--;
			}
			if (start <= end) {
				swap(nums, start, end);
				start++;
				end--;
			}
		}
		
		rainbow(nums, left, end, colorFrom, colorMid);
		rainbow(nums, start, right, colorMid+1, colorTo);
	}
	
	/**
	 * 计数排序。O(n)的时间， O(k)的空间
	 */
	public void sortColorsCounting(int[] nums, int k) {
        if (nums == null || nums.length  == 0) {
            return;
        }
        int[] count = new int[k];
        int[] tmp = new int[nums.length];
        
        for (int num : nums) {
        	count[num]++;
        }
       //计算每个元素的的终止填充位置，也就是算元素和他之前一共有多少个元素
        for (int i = 1; i < k; i++) {
        	count[i] += count[i-1];
        }
        
        for (int j = nums.length - 1; j >= 0; j--) {
        	int num = nums[j];
        	tmp[count[num] - 1] = num;
        	count[num]--;
        }
        
        System.arraycopy(tmp, 0, nums, 0, tmp.length);
        System.out.println(Arrays.toString(nums));
	}
	
	/**
	 * 最后看一个比较模板化的解法，把任何问题都转化为三种颜色的问题 O(nk)
	 * 一般来说不需要用到这样的办法。效率不是很高，仅仅留作参考。
	 */
	public void sortColors2(int[] colors, int k) {
        if (colors == null || colors.length <= 1 || k == 1) {
            return;
        }
        int left = 0;
        int right = colors.length - 1;
        while (left < right) {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (int i = left; i <= right; i++) {
                max = Math.max(max, colors[i]);
                min = Math.min(min, colors[i]);
            }
            int i = left;
            while (i <= right) {
                if (colors[i] == min) {
                    swap(colors, i, left);
                    left++;
                    i++;
                } else if (colors[i] > min && colors[i] < max) {
                    i++;
                } else {
                    swap(colors, i, right);
                    right--;
                }
            }
        }
    }


	public static void main(String[] args) {
		SortColors clz = new SortColors();
		
		int[] nums = {1,2,0,0,2,1,1,0,0,0,2};
//		clz.sortColorsOnepass(nums);
		clz.sortColorsCounting(nums, 3);
	}
	
	
	
	private boolean isLow(int x) {return true;};
	private boolean isMid(int x) {return true;};
	private boolean isHigh(int x) {return true;};
}
