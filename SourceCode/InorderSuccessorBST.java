

public class InorderSuccessorBST {
    private TreeNode prev = null;
    private TreeNode succ = null;
    
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null || p == null) {
            return null;
        }
    
        helper(root, p);
        return prev;
    }
    
    private void helper(TreeNode root, TreeNode p) {
        if (root == null) {
            return;
        }
        
        helper(root.left, p);
        if (p == prev) {
            succ = root;
        }
        prev = root;
        helper(root.right, p);
        
    }
    
    public static void main(String[] args) {
    	InorderSuccessorBST clz = new InorderSuccessorBST();
    	TreeNode n = new TreeNode(0);
    	clz.inorderSuccessor(n, n);
    }
}
 