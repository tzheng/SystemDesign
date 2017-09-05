import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinaryTreeVerticalOrder {
	
	class Result {
		TreeNode node;
		int pos;

		public Result(TreeNode n, int p) {
			node = n;
			pos = p;
		}
	}

	public ArrayList<ArrayList<Integer>> verticalOrder(TreeNode root) {
		ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
		if (root == null) {
			return ret;
		}

		Queue<Result> queue = new LinkedList<Result>();
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		Result rootRes = new Result(root, 0);
		queue.offer(rootRes);

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

		while (!queue.isEmpty()) {
			// int size = queue.size();
			// for (int i = 0; i < size; i++) {
			Result curr = queue.poll();
			if (!map.containsKey(curr.pos)) {
				map.put(curr.pos, new ArrayList<Integer>());
			}
			ArrayList<Integer> currList = map.get(curr.pos);
			currList.add(curr.node.val);

			if (curr.node.left != null) {
				Result left = new Result(curr.node.left, curr.pos - 1);
				queue.offer(left);
				min = Math.min(min, curr.pos - 1);
			}
			if (curr.node.right != null) {
				Result right = new Result(curr.node.right, curr.pos + 1);
				queue.offer(right);
				max = Math.max(max, curr.pos + 1);
			}
			// }
		}

		for (int i = min; i <= max; i++) {
			ret.add(map.get(i));
		}

		return ret;
	}
	
	/**
	 * Followup 1: What if it's general tree  
	 * 考虑一个问题：有odd个孩子，还是even个孩子，解法是不一样的。比如一个节点
	 * 有三个孩子（奇数个），是不是中间的孩子要和父节点在同一个column，如果是
	 * 偶数个，就不需要这样了。 
	 * 
	 */
	public List<List<Integer>> verticalOrderGeneral(TreeNode root) {
		List<List<Integer>> ret = new ArrayList<>();
		if (root == null) {
			return ret;
		}

		Queue<Result> queue = new LinkedList<Result>();
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		Result rootRes = new Result(root, 0);
		queue.offer(rootRes);

		int min = 0, max = 0;

		while (!queue.isEmpty()) {
			Result curr = queue.poll();
			if (!map.containsKey(curr.pos)) {
				map.put(curr.pos, new ArrayList<Integer>());
			}
			map.get(curr.pos).add(curr.node.val);
			
			List<TreeNode> children = curr.node.children;
			int size = children.size();
			int childIndex = 0;
			for (int i = -size/2; i <= size/2; i++) {
				//如果有偶数个，跳过0，只有奇数个孩子的时候，
				//中间孩子才会和父节点在一个column
				if (i == 0 && size%2 == 0) continue;  
				Result childNode = new Result(children.get(childIndex++), i);
				queue.offer(childNode);
			}
			min = Math.min(min, -size/2);
			max = Math.max(max, size/2);
		}

		for (int i = min; i <= max; i++) {
			ret.add(map.get(i));
		}

		return ret;
	}
	
	
	/**
	 * Binary Tree Level Order Traversal
	 */
	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		
		Queue<TreeNode> queue = new LinkedList<>();
		
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			int size = queue.size();
			List<Integer> level = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode curr = queue.poll();
				level.add(curr.val);
				if (curr.left!= null) {
					queue.offer(curr.left);
				}
				if (curr.right != null) {
					queue.offer(curr.right);
				}
			}
					
			result.add(level);
		}
		return result;
	}

	/**
	 * Binary Tree ZigZag Order Traversal
	 * @param args
	 */
	public List<List<Integer>> zigzagOrder(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		boolean left = true;
		while (!stack.isEmpty()) {
			Stack<TreeNode> next = new Stack<>();
			List<Integer> level = new ArrayList<>();
			while (!stack.isEmpty()) {
				TreeNode top = stack.pop();
				level.add(top.val);
				if (left) {
					if (top.left != null) stack.push(top.left);
					if (top.right != null) stack.push(top.right);
				} else {
					if (top.right != null) stack.push(top.right);
					if (top.left != null) stack.push(top.left);
				}
			}
			left = !left;
			stack = next;
			result.add(level);
		}
		return result;
	}
	
	public static void main(String[] args) {
		BinaryTreeVerticalOrder clz = new BinaryTreeVerticalOrder();
		TreeNode tr = new TreeNode(1);
		clz.verticalOrder(tr);
	}
	
	
	

}

