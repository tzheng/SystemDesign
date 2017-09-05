import java.util.Arrays;

public class RotateArray {
	public void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) return;
        int[] res = new int[nums.length];
        int len = nums.length;
        for (int i = 0; i < nums.length; i++) {
            res[(i+k)%len] = nums[i];
        }
        nums = res;
        System.out.println(Arrays.toString(nums));
    }
	
	public static void main(String[] args) {
		RotateArray clz = new RotateArray();
		int[] nums = {1,2};
		clz.rotate(nums, 1);
	}
}
