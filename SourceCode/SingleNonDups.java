
public class SingleNonDups {
	public int singleNonDuplicate(int[] nums) {
        if (nums == null) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        // while (start < end) {
        //     int mid = start + (end - start)/2;
        //     if (nums[mid] != nums[mid+1] && nums[mid] != nums[mid-1]) {
        //         return nums[mid];
        //     }
        //     if (nums[mid] == nums[mid+1] && mid %2 ==0) {
        //         start = mid + 1;
        //     } else if (nums[mid] == nums[mid-1] && mid %2 == 1) {
        //         start = mid + 1;
        //     } else {
        //         end = mid - 1;
        //     }
        // }
        
        // return nums[start];
        
        while (start + 1< end) {
            int mid = start + (end - start)/2;
            if (nums[mid] != nums[mid+1] && nums[mid] != nums[mid-1]) {
                return nums[mid];
            }
            if (nums[mid] == nums[mid+1] && mid %2 ==0) {
                start = mid;
            } else if (nums[mid] == nums[mid-1] && mid %2 == 1) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if (start> 0 && nums[start] == nums[start-1] ) {
            return end;
        } else {
            return start;
        }
    }
	
	public static void main(String[] args) {
		SingleNonDups sg = new SingleNonDups();
		sg.singleNonDuplicate(new int[]{1,1,2,2,4,4,5,5,9});
	}
}
