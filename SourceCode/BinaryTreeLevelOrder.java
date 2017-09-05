import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BinaryTreeLevelOrder {
	/**
	 * 102. Binary Tree Level Order Traversal
	 */
	public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();
                level.add(curr.val);
                System.out.print(curr.val + " ");
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            res.add(level);
            System.out.println();
        }
        return res;
    }
	
	/**
	 *  107. Binary Tree Level Order Traversal II
	 *  
	 *  BFS same as before, just minor change. So here we do DFS
	 */
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		dfs(res, root, 0);
		return res;
    }
	
	private void dfs(List<List<Integer>> res, TreeNode root, int level) {
		if (root == null) return;
		if (level >= res.size()) {
			res.add(0, new ArrayList<Integer>());
		}
		dfs(res, root.left, level + 1);
		dfs(res, root.right, level + 1);
		res.get(res.size() - level - 1).add(root.val);
	}
	
	
	
	/**
	 * follow up 是带间距的输出
	* Sample input:
	*
	*          1
	*         / \
	*        3   5
	*       /   / \
	*      2   4   7
	*     / \   \
	*    9   6   8
	*
	* follow up expected output
	*          1
	*        3   5
	*      2   4   7
	*    9   6   8
	*   when building the list , make Hash Map TreeNode->column value, just like vertical traversal, 
	*   then Max Col - Min Col +1 will be the length of each row.
	*   offset = -min col, just using stringbuilder for each row, sb.setCharAt(offset+col val，node.val+'0');
	* **/
	public void levelOrderSpaces(TreeNode root) {
		if (root == null) return;
		
		Map<TreeNode, Integer> map = new HashMap<>();
		List<List<TreeNode>> levelOrder = new ArrayList<>();
		map.put(root, 0);
		Queue<TreeNode> queue = new LinkedList<>();
		int min, max;
		min = max = 0;
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			int size = queue.size();
			List<TreeNode> level = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode curr = queue.poll();
				level.add(curr);
				int pos = map.get(curr);
				if (curr.left != null) {
					queue.offer(curr.left);
					map.put(curr.left, pos - 1);
					min = Math.min(min, pos - 1);
				}
				if (curr.right != null) {
					queue.offer(curr.right);
					map.put(curr.right, pos + 1);
					max = Math.max(max, pos + 1);
				}
			}
			levelOrder.add(level);
		}
		
		int len = max - min + 1, offset = -min;
		char[] line;
		for (int i = 0; i < levelOrder.size(); i++) {
			line = new char[len];
			Arrays.fill(line, ' ');
			for (int j = 0; j < levelOrder.get(i).size(); j++) {
				TreeNode curr = levelOrder.get(i).get(j);
				int pos = offset + map.get(curr);
				line[pos] = (char)(curr.val + '0');
			}
			System.out.println(new String(line));
		}
	}
	
	
	public static void main(String[] args) {
		BinaryTreeLevelOrder clz = new BinaryTreeLevelOrder();
		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(5);
		TreeNode n6 = new TreeNode(6);
		TreeNode n7 = new TreeNode(7);
		TreeNode n8 = new TreeNode(8);
		TreeNode n9 = new TreeNode(9);
		
		n1.left = n3; n1.right = n5;
		n3.left = n2; 
		n2.left = n9; n2.right = n6;
		n5.left = n4; n5.right = n7;
		n4.right = n8;
		
		clz.levelOrder(n1);
		clz.levelOrderSpaces(n1);
	}
	
	  
}
