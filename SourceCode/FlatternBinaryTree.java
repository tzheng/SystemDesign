
public class FlatternBinaryTree {

	/**
	 * 114. Flatten Binary Tree to Linked List
	 * 先看简单的，转化成单链表。对于这类二叉树的问题，原理都是先假设只有三个点，根节点和左右两个叶子
	 * 然后看三个点怎么转化成结果，理清楚关系之后，把转化过程稍微修改一下，左右节点换成递归左右节点的返回值得
	 * recursive(root.left), recursive(right), 这样就可以了。
	 */
	public TreeNode flattenSingle(TreeNode root) {
		if (root == null || (root.left == null && root.right == null)) {
			return root;
		}

		TreeNode right = root.right;
		root.right = flattenSingle(root.left);

		TreeNode tmp = root;
		while (tmp.right != null) {
			tmp = tmp.right;
		}
		tmp.right = flattenSingle(right);
		return root;
	}

	/**
	 * 然后看一个follow up，如果要转化成双链表，右指针指向孩子，左指针指向父亲。
	 */
	public TreeNode flattenDouble(TreeNode root) {
		if (root == null || (root.left == null && root.right == null)) {
			return root;
		}

		TreeNode right = root.right;
		root.right = flattenDouble(root.left);
		root.left = null;
		if (root.right != null) {
			root.right.left = root;
		}

		TreeNode tmp = root;
		while (tmp.right != null) {
			tmp = tmp.right;
		}
		tmp.right = flattenDouble(right);
		if (tmp.right != null) {
			tmp.right.left = tmp;
		}

		return root;
	}
	
	/**
	 * 转化成双链表，头尾相接
	 */
	TreeNode first = null;
	TreeNode prev = null;
	public TreeNode flattenDoubleConnectHead(TreeNode root) {
		if (root == null) {
			return root;
		}
		
		flattenDoubleConnectHead(root.left);

		if (prev != null) {
			prev.right = root;
			root.left = prev;
		} else {
			first = root;
		}
		prev = root;		
		flattenDoubleConnectHead(root.right);
		return root;
	}
	
	/**
	 * 和上面反过来，把doubly linked list弄成balanced binary tree
	 */
	public TreeNode listToTree(TreeNode head) {
		this.head = head;
		int count = 0;
		TreeNode node = head;
		while (node != null) {
			count++;
			node = node.right;
		}
		
		return helper(count);
	}
	
	TreeNode head;
	private TreeNode helper(int n) {
		if (n == 0) 
			return null;
		
		TreeNode left = helper(n/2);
		TreeNode root = head;
		head = head.right;
		
		root.left = left;
		root.right = helper(n - n/2 - 1);
		return root;
	}
	
	public static void main(String[] args) {
		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(5);
		TreeNode n6 = new TreeNode(6);

		n6.left = n5; n5.left = n4; n4.left = n3; n3.left = n2; n2.left = n1;

		FlatternBinaryTree clz = new FlatternBinaryTree();
		TreeNode root = n6;
		clz.flattenDoubleConnectHead(root);
		
		TreeNode node = clz.first;
		while (node != null) {
			System.out.print(node.val + "->");
			node = node.right;
		}

		System.out.println();
		
		
		root = clz.listToTree(clz.first);
		
		BinaryTreeLevelOrder level = new BinaryTreeLevelOrder();
		level.levelOrder(root);
		
		BinaryTreeTraversal t = new BinaryTreeTraversal();
		t.inOrderIterative(root);
	}
}
