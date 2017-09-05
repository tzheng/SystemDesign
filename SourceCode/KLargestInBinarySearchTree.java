import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class KLargestInBinarySearchTree {
	/**
	 * 230. Kth Smallest Element in a BST
	 * 复杂度是O(n), 
	 */
	Integer ret = null;
    int k;
	public int kthSmallestIterative(TreeNode root, int k) {
        this.k = k;
        // inorder(root);
        //return ret;
        
        Stack<TreeNode> stack = new Stack<TreeNode>();
        
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            
            TreeNode top = stack.pop();
            k--;
            if (k == 0) return top.val;
            root = top.right;
        }
        return -1;
    }
	
	/**
	 * 分治法, 复杂度是 O(n)， 
	 */
	public int kthSmallestDivide(TreeNode root, int k) {
        int left = nodeCount(root.left);
        if (left + 1 == k) {
        	return root.val;
        } else if (left + 1 < k) {
        	return kthSmallestDivide(root.right, k - left - 1);
        } else {
        	return kthSmallestDivide(root.left, k);
        }
    }
	
	private int nodeCount(TreeNode root) {
		if (root == null) 
			return 0;
		return nodeCount(root.left) + nodeCount(root.right) + 1;
	}
	
	
	/**
	 * Followup 1: What if the BST is modified (insert/delete operations) 
	 *  often and you need to find the kth smallest frequently? How would
	 *  you optimize the kthSmallest routine?
	 *  
	 *  1. add previous reference to each node. Save kth element's reference.
	 *     when adding a new elem, if elem is greater then kth elem, do nothing.
	 *     if elem is small then kth elem.  then move kth elem to it's previous
	 *     node.
	 *     
	 *  2. add count to each node, it's the count number of elements on it's
	 *     left side, if bst gets updated, update count as well. 
	 */
	
	/**
	 * Followup 2: K Largest Element， 不是求第k个，而是所以的k个。
	 */
	public List<Integer> getLargestK(TreeNode root, int k) {
		List<Integer> result= new ArrayList<Integer>();
		helper(root, k, result);
		return result;
	}
	
	private void helper(TreeNode root, int k, List<Integer> result) {
		if (root == null) {
			return;
		}

		helper(root.right, k, result); 
		if (result.size() == k) {
			return;
		}
		result.add(root.val);
		helper(root.left, k, result);
	}
	
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(4);
		root.right = new TreeNode(8);
		root.right.left = new TreeNode(7);
		root.right.right = new TreeNode(9);
		
		KLargestInBinarySearchTree clz = new KLargestInBinarySearchTree();
		List<Integer> list = clz.getLargestK(root, 2);
		for (int num : list) {
			System.out.println(num);
		}
	}
}
