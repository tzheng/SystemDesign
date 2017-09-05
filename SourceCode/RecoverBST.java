
public class RecoverBST {
	
	TreeNode prev = null;
	
	/**
	 * 首先来看一道简单的题目 Leetcode 98. Validate Binary Search Tree
	 * 这题的思路很简单，就是做一个中序遍历，看看前一个值是不是比现在的值大，因为
	 * BST是永远有序的。
	 * Time: O(n)
	 */
	public boolean isValidBST(TreeNode root) {
		if (root == null) {
			return true;
		}
		
		boolean left = isValidBST(root.left);
		if (!left) {
			return false;
		}
		
		if (prev != null && prev.val >= root.val) {
			return false;
		}
		
		prev = root;
		return isValidBST(root.right);
	}
	
	/**
	 * 另外一种做法，通过范围来检查
	 */
	class ResultWrapper {
		boolean isValid;
		int min, max;
		public ResultWrapper(boolean v, int min, int max) {
			this.min = min; this.max = max;
			this.isValid = v;
		}
	}
	
	public boolean isValidBSTRangeCheck(TreeNode root) {
		if (root == null) {
			return true;
		}
		ResultWrapper res = rangeCheck(root);
		return res.isValid;
	}
	
	private ResultWrapper rangeCheck(TreeNode root) {
		if (root == null) {
			//be careful!! min set to max,  and max set to min;
			return new ResultWrapper(true, Integer.MAX_VALUE, Integer.MIN_VALUE);
		}
		ResultWrapper left = rangeCheck(root.left);
		ResultWrapper right = rangeCheck(root.right);
		if (!left.isValid || !right.isValid) {  
			return new ResultWrapper(false, 0, 0);
		}
		
		if (root.left != null && left.max >= root.val || 
				root.right != null && right.min <= root.val) {
			return new ResultWrapper(false, 0, 0);
		}
		
		return new ResultWrapper(true, Math.min(root.val, left.min), Math.max(root.val, right.max));
	}
	
	/**
	 * Leetcode 99. Recover Binary Search Tree
	 * 现在不但要判断是不是BST， 题目还告诉我们，只有两个node被交换了。
	 * 在不改变树结构的情况下，让找出这两个node, 恢复BST
	 * 
	 * 思路和，判断valid BST差不多，如果我们找到一个点，他的前序节点
	 * 大于或者等于自己，说明前序节出了问题，因为他的值太大了。然后继续往后遍历
	 * 如果还能找到一个节点，他的前序节点大于或者等于自己，说明这个节点是
	 * 被交换的，导致他的值太小。这样我们就找到两个节点了。
	 * 
	 */
	//TreeNode prev = null;
	TreeNode first = null;
	TreeNode second = null;
	private void recoverBST(TreeNode root) {
        if (root == null) {
            return;
        }
        recoverBST(root.left);
        if (prev != null && prev.val >= root.val) {
            if (first == null) first = prev;
            second = root;
        }
        prev = root;
        recoverBST(root.right);
    }
	
	class ReturnType {
		TreeNode firstNode;
		TreeNode firstParent;
		
		TreeNode secondNode;
		TreeNode secondParent;
	}
	
	public static void main(String[] args) {
		TreeNode n1 = new TreeNode(1);
		n1.left = new TreeNode(1);
		RecoverBST clz = new RecoverBST();
		
	}
}
