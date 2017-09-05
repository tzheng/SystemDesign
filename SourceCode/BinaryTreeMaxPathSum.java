
public class BinaryTreeMaxPathSum {
	 int max = Integer.MIN_VALUE;
	    
	    public int maxPathSum(TreeNode root) {
	        helper(root);
	        return max;
	    }
	    
	    // helper returns the max branch 
	    // plus current node's value
	    Result helper(TreeNode root) {
	        if (root == null) return new Result(Integer.MIN_VALUE, Integer.MIN_VALUE);
	        
	        Result left = helper(root.left);
	        Result right = helper(root.right);
	        
	        int pathLeft = Math.max(left.path, 0);
	        int pathRight = Math.max(right.path, 0);
	        int path = Math.max(pathLeft, pathRight) + root.val;
	        int sum = Math.max(Math.max(left.sum, right.sum), pathLeft + pathRight + root.val);
	        
	        return new Result(path, sum);
	    }
	    
	    class Result {
	        int path;
	        int sum;
	        public Result(int p, int s) {
	            this.path = p;
	            this.sum = s;
	        }
	    }
	    
	    public static void main(String[] args) {
	    	TreeNode node = new TreeNode(1);
	    	node.left = new TreeNode(2);
	    	node.right = new TreeNode(3);
	    	
	    	 
	    }
}
