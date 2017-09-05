import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeTraversal {
	/**
	 * 二叉树的遍历属于基本功，前序、中序、后序都要会两种办法：递归和遍历
	 */
	public void preOrderRecursive(TreeNode root) {
		if (root == null) {
			return;
		}
		System.out.print(root.val + ", ");
		preOrderRecursive(root.left);
		preOrderRecursive(root.right);
	}

	public void preOrderIterative(TreeNode root) {
		if (root == null) {
			return;
		}
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);

		while (!stack.isEmpty()) {
			TreeNode top = stack.pop();
			System.out.print(top.val + ", ");
			if (top.right != null) {
				stack.push(top.right);
			}
			if (top.left != null) {
				stack.push(top.left);
			}
		}
	}

	public void inOrderRecursive(TreeNode root) {
		if (root == null) {
			return;
		}
		inOrderRecursive(root.left);
		System.out.print(root.val + ", ");
		inOrderRecursive(root.right);
	}

	public void inOrderIterative(TreeNode root) {
		if (root == null) {
			return;
		}
		TreeNode node = root;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		while (node != null || !stack.isEmpty()) {
			while (node != null) {
				stack.push(node);
				node = node.left;
			}
			TreeNode top = stack.pop();
			System.out.print(top.val + ", ");
			node = top.right;
		}
	}

	public void postOrderRecursive(TreeNode root) {
		if (root == null) {
			return;
		}
		postOrderRecursive(root.left);
		postOrderRecursive(root.right);
		System.out.print(root.val + ", ");
	}

	public void postOrderIterative(TreeNode root) {
		if (root == null) {
			return;
		}

		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);

		TreeNode prev = null; // previously traversed node
		TreeNode curr = root;
		while (!stack.empty()) {
			curr = stack.peek();
			if (prev == null || prev.left == curr || prev.right == curr) { // traverse
																			// down
																			// the
																			// tree
				if (curr.left != null) {
					stack.push(curr.left);
				} else if (curr.right != null) {
					stack.push(curr.right);
				}
			} else if (curr.left == prev) { // traverse up the tree from the
											// left
				if (curr.right != null) {
					stack.push(curr.right);
				}
			} else { // traverse up the tree from the right
				System.out.print(curr.val + ", ");
				stack.pop();
			}
			prev = curr;
		}
	}

	/**
	 * given preorder traversal [5,3,2,4,8,7,9] of a BST, how do we identify the
	 * leaf nodes without building the tree ?
	 */
	public void findLeaves(int[] arr) {
		Stack<Integer> stack = new Stack<>();
		for (int n = 1, c = 0; n < arr.length; n++, c++) {
			if (arr[c] > arr[n]) {
				stack.push(arr[c]);
			} else {
				boolean found = false;
				while (!stack.isEmpty()) {
					if (arr[n] > stack.peek()) {
						stack.pop();
						found = true;
					} else {
						break;
					}
				}
				if (found) {
					System.out.println(arr[c]);
				}
			}
		}
		System.out.println(arr[arr.length - 1]);
	}

	/**
	 * 给个Tree 不一定是平衡的， 要求 把所有路径排序后 按字符串那样的比较大小方法 找出最小的路径 时间要求线性的。 比如 
	 * 		5  
	 *     /  \
	 *   10    3 
	 *  / \   / 
	 * 1   7 8
	 * 
	 * 路径有 5 10 1 ； 5 10 7 ； 5 3 8 排序后 1 5 10 ； 5 7 10 ； 3 5 8； 所以按字符串类型排序 为 1 5
	 * 10 < 3 5 8 < 5 7 10；
	 **/
	public List<Integer> findSmallPath(TreeNode root) {
		Result result = helper(root);
		return result.path;
	}

	private Result helper(TreeNode root) {
		if (root == null) {
			Result result = new Result();
			result.path.add(Integer.MAX_VALUE);
			return result;
		}
		if (root.left == null && root.right == null) {
			Result result = new Result();
			result.min = root.val;
			result.path.add(root.val);
			return result;
		}
		Result left = helper(root.left);
		Result right = helper(root.right);
		Result result = new Result();
		result.path.add(root.val);
		if (left.min > right.min) {
			result.path.addAll(right.path);
		} else {
			result.path.addAll(left.path);
		}
		result.min = Math.min(root.val, Math.min(left.min, right.min));
		return result;
	}

	class Result {
		public int min = Integer.MAX_VALUE;
		public List<Integer> path = new ArrayList<>();

		public Result() {
		}
	}
	
	/**
	 * 285. Inorder Successor in BST
	 */
	public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
		if (p == null) return null;
		
		TreeNode succ = null;
		while (root != null) {
			if (root.val <= p.val) {
				root = root.right;
			} else {
				succ = root;
				root = root.left;
			}
		}
		
		/**
		 * 如果反过来，求inorder 前驱。
		 *  TreeNode prev = null;
			while (root != null) {
				if (root.val >= p.val) {
					root = root.left;
				} else {
					prev = root;
					root = root.right;
				}
			}
		 */
		
		return succ;
	}
	
	/**
	 * 450. Delete Node in a BST
	 */
	public TreeNode deleteNode(TreeNode root, int key) {
		TreeNode curr = root;
		TreeNode prev = null;
		while (curr != null && curr.val != key) {
			prev = curr;
			if (curr.val  > key) {
				curr = curr.left;
			} else {
				curr = curr.right;
			}
		}
		
		if (prev == null) 
			return deleteRoot(root);
		if (prev.left == curr) {
			prev.left = deleteRoot(curr);
		} else {
			prev.right = deleteRoot(curr);
		}
		return root;
	}
	
	private TreeNode deleteRoot(TreeNode root) {
		if (root == null)
			return null;
		if (root.left == null)
			return root.right;
		if (root.right == null)
			return root.left;

		TreeNode leftMost = root.right;
		TreeNode pre = null;
		
		while (leftMost.left != null) {
			pre = leftMost;
			leftMost = leftMost.left;
		}
		// 这里是关键
		leftMost.left = root.left;
		if (root.right != leftMost) {
			pre.left = leftMost.right;
			leftMost.right = root.right;
		}
		return leftMost;
	}
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);

		BinaryTreeTraversal clz = new BinaryTreeTraversal();
//		clz.preOrderRecursive(root);
//		System.out.println();
//		clz.preOrderIterative(root);
//		System.out.println();
//
//		clz.inOrderRecursive(root);
//		System.out.println();
//		clz.inOrderIterative(root);
		System.out.println();

//		clz.postOrderRecursive(root);
//		System.out.println();
//		clz.postOrderIterative(root);
		System.out.println();

		clz.findLeaves(new int[] { 5, 3, 2,4, 8,7, 9 });
	}

}
