import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    List<TreeNode> children;
    TreeNode next;
    TreeNode parent;
    
    public TreeNode(int x) { 
    	val = x; 
    	children = new ArrayList<TreeNode>();
    }
    
    public String toString() {
    	return "" + val;
    }
}