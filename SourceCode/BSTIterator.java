import java.util.Stack;

public class BSTIterator {
	Stack<TreeNode> stack;
    TreeNode curr = null;
    public BSTIterator(TreeNode root) {
        stack = new Stack<TreeNode>();
        pushToStack(stack, root);
    }
    
      /** @return whether we have a next smallest number */
    public boolean hasNext() {
        if (stack.isEmpty() && curr == null) {
            return false;
        }
        
        if (!stack.isEmpty()) {
            curr = stack.pop();
            pushToStack(stack, curr.right);
        }
        
        return true;
    }

    /** @return the next smallest number */
    public int next() {
        if (hasNext()) {
            return curr.val;
        } else {
            return -1;
        }
    }
       

  
   private void pushToStack(Stack<TreeNode> stack, TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }
   
   
   public static void main(String[] args) {
	   TreeNode root = new TreeNode(1);
	   BSTIterator iter = new BSTIterator(root);
	   iter.hasNext();
	   iter.next();
   }
}
