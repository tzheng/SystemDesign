import java.util.ArrayList;

public class LargestDivisibleSubset {
	ArrayList<Integer> result = new ArrayList<Integer>();
	/**
     * @param nums a set of distinct positive integers
     * @return the largest subset 
     */
    public ArrayList<Integer> largestDivisibleSubset(int[] nums) {
        // Write your code here
    	
    	ArrayList<Integer> subset = new ArrayList<Integer>();
        
        helper(nums, 0, subset);
        
        return result;
    }
    
    private void helper(int[] nums, int pos, 
    					ArrayList<Integer> subset) {
        if (subset.size() > result.size()) {
            result = new ArrayList<Integer>(subset);
        }
        for (int i = pos; i < nums.length; i++) {
            if (!validSubset(subset, nums[i]) ) {
                continue;
            }
            subset.add(nums[i]);
           
            helper(nums, i+1, subset);
            subset.remove(subset.size() -1);
        }
    }
    
    private boolean validSubset(ArrayList<Integer> subset, int num) {
        for (Integer elem : subset) {
            if (elem % num == 0 || num % elem == 0) {
                continue;
            }
            return false;
        }
        return true;
    }
    
    
    public static void main(String[] args) {
    	LargestDivisibleSubset clz = new LargestDivisibleSubset();
    	int[] nums = {1,2,4, 8};
    	ArrayList<Integer> ret = clz.largestDivisibleSubset(nums);
    	System.out.println(ret.size());
    }
    
}
