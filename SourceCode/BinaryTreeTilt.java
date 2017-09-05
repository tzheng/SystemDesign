import java.util.Arrays;

public class BinaryTreeTilt {
	int sum = 0;
    public int findTilt(TreeNode root) {
        if (root == null) {
            return 0;
        }
        helper(root);
        return sum;
    }
    
    public int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int left = findTilt(root.left);
        int right = findTilt(root.right);
        
        sum += Math.abs(left-right);
        return left + right + root.val;
    }
    
    
    public int arrayPairSum(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int sum = 0;
        Arrays.sort(nums);
        for (int i = 1; i <nums.length; i = i+2) {
            sum += nums[i] - nums[i-1];
        } 
        return sum;
    }
    
    public static void main(String[] args) {
    	TreeNode n1 = new TreeNode(1);
    	TreeNode n2 = new TreeNode(2);
    	TreeNode n3 = new TreeNode(3);
    	n1.left = n2; n1.right = n3;
    	
    	BinaryTreeTilt clz = new BinaryTreeTilt();
    	System.out.println(clz.findTilt(n1));
    }
}
