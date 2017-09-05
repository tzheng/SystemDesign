import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryTreeBoundry {
	
	/**
	 * 199. Binary Tree Right Side View
	 */
	
	public void rightSideView(TreeNode root, int depth, List<Integer> result) {
        if (root == null) {
            return;
        }
        
        if (result.size() == depth) {
        	result.add(root.val);
        }
        
        rightSideView(root.right, depth+1, result);
        rightSideView(root.left, depth+1, result);
    }
	
	/**
	 * Followup 1: 打印二叉树的轮廓
	 * 很简单，左边打印，右边打印，
	 * 然后中间的节点只打印叶子节点。
	 * @param root
	 */
	public static void printBoundary(TreeNode root) {
		if (root == null) {
			return;
		}
		
		printBoundaryLeft(root);
		printLeaves(root.left);
		printLeaves(root.right);
		printBoundaryRight(root);
	}
	
	public static void printBoundaryLeft(TreeNode node) {
		if (node != null) {
			if (node.left != null) {
				System.out.print(node.val + " ");
				printBoundaryLeft(node.left);
			} else if (node.right != null) {
				System.out.print(node.val + " ");
				printBoundaryLeft(node.right);
			}
			 // do nothing if it is a leaf node, this way we avoid
            // duplicates in output
		}
	}
	
	public static void printLeaves(TreeNode node) {
		if (node == null) {
			return;
		}
		if (node.left == null && node.right == null) {
			System.out.print(node.val + "  ");
			return;
		}
		
		printLeaves(node.left);
		printLeaves(node.right);
	}
	
	public static void printBoundaryRight(TreeNode node) {
		if (node != null) {
			if (node.right != null) {
				printBoundaryRight(node.right);
				System.out.print(node.val + " ");
			} else if (node.left != null) {
				printBoundaryRight(node.left);
				System.out.print(node.val +  " ");
			}
			 // do nothing if it is a leaf node, this way we avoid
            // duplicates in output
		}
	}
	
	public static void main(String args[]) 
    {
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(8);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(12);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(14);
        root.right = new TreeNode(22);
        root.right.right = new TreeNode(25);
//        
        printBoundary(root);
  
        
//        
//        root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(3);
//        root.left.right = new TreeNode(4);
//        BinaryTreeBoundry clz = new BinaryTreeBoundry();
//        List<Integer> result = new ArrayList<Integer>();
//        clz.rightSideView(root, 0, result);
//        System.out.println(Arrays.toString(result.toArray()));
    }
}
