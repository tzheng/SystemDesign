import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class ConstructBrinayTree {
	
	/**
	 * 297. Serialize and Deserialize Binary Tree
	 * 这是一道非常经典的题目，虽然是Hard难度，但是思想其实很简单。
	 * 1. 序列化就是做BFS，然后把末尾的 # 去除就好了
	 * 2. 反序列化
	 */
	public String serialize(TreeNode root) {
		if (root == null) return "{}";
		List<TreeNode> queue = new ArrayList<TreeNode>();
		queue.add(root);
		for (int i = 0; i < queue.size(); i++) {
			TreeNode node = queue.get(i);
			if (node != null) {
				queue.add(node.left);
				queue.add(node.right);
			}
		}
		
		while (queue.get(queue.size() - 1) == null) { 
			queue.remove(queue.size() - 1);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < queue.size()-1; i++) {
			if (queue.get(i) == null) {
				sb.append("#,");
			} else {
				sb.append(queue.get(i).val + ",");
			}
		}
		sb.append(queue.get(queue.size()-1).val);
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * Deserialize的思路就是两个指针往后走，左指针走一步，右指针走两步，代表左右孩子。
	 * 只有node不为空的时候才加入到queue里面。
	 */
	public TreeNode deserialize(String data) {
		if (data == null || data.length() <= 2) return null;
		data = data.substring(1, data.length()-1);
		String[] strs = data.split(",");
		if (strs[0].equals("") || strs[0].equals("#")) {
            return null;
        }
        
        ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
        queue.add(new TreeNode(Integer.parseInt(strs[0])));
        int index = 0;
        boolean isLeft = true;
        for (int i = 1; i < strs.length; i++) {
            TreeNode newNode = null;
            if (!strs[i].equals("#")) {
                newNode = new TreeNode(Integer.parseInt(strs[i]));
                queue.add(newNode);  //don't forget add to list
            }
            if (isLeft) {
                queue.get(index).left = newNode;
            } else {
                queue.get(index++).right = newNode;
            }
            isLeft = !isLeft;
        }
        return queue.get(0);
	}
	
	/**
	 * 449. Serialize and Deserialize BST
	 * 考虑到是BST，不用像Binary Tree那样，加很多#来表示空，这样可以压缩更多空间。
	 * 1. 序列化的时候，做inorder的遍历， 可以有  10 5 3 8 15 12, 
	 * 2. 这样我们就知道， 5，3，8 都比10小，肯定都在10左边，15 12比10 大，肯定都在10右边
	 * 	  然后就可以通过一个recursive的办法来构造bst
	 */
    public String serializeBST(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) return "";
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
        	TreeNode top = stack.pop();
        	sb.append(top.val + ",");
        	if (top.right != null) stack.push(top.right);
        	if (top.left != null) stack.push(top.left);
        }
        
        return sb.toString();
    }

    public TreeNode deserializeBST(String data) {
        if (data == null || data.equals("")) {
        	return null;
        }
        String[] strs = data.split(",");
        Queue<Integer> q = new LinkedList<>();
        for (String str : strs) {
        	q.offer(Integer.valueOf(str));
        }
        return constructBST(q);
    }
    
    private TreeNode constructBST(Queue<Integer> q) {
    	if (q.isEmpty()) 
    		return null;
    	TreeNode root = new TreeNode(q.poll());
    	Queue<Integer> smaller = new LinkedList<>();
    	while (!q.isEmpty() && q.peek() < root.val) {
    		smaller.offer(q.poll());
    	}
    	root.left = constructBST(smaller);
    	root.right = constructBST(q);
    	return root;
    }
	
	
	/**
	 * 105. Construct Binary Tree from Preorder and Inorder Traversal
	 */
	public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length || preorder.length == 0 ) {
            return null;
        }
        return helper(preorder, 0, preorder.length-1, inorder, 0, inorder.length -1);
    }
	
	private TreeNode helper(int[] pre, int pLeft, int pRight, int[] in, int iLeft, int iRight) {
        if (pLeft > pRight || iLeft > iRight || pLeft > pre.length - 1) {
            return null;
        }
        
        
        int val = pre[pLeft];
        TreeNode root = new TreeNode(val);
        int index = findValArray(val, in, iLeft, iRight);
        int len = index - iLeft;
        root.left = helper(pre, pLeft+1, pLeft + len, in, iLeft, iLeft + len -1);
        root.right = helper(pre, pLeft + index - iLeft + 1, pRight, in, index+1, iRight);
        
        return root;
    }
	
	private int findValArray(int val, int[] in, int l, int r) {
        for (int i = l; i <= r; i++) {
            if (in[i] == val) {
                return i;
            }
        }
        return -1;
    }
	
	/**
	 * 106. Construct Binary Tree from Inorder and Postorder Traversal
	 */
	public TreeNode buildTree2(int[] inorder, int[] postorder) {
		if (postorder == null || inorder == null || postorder.length != inorder.length || postorder.length == 0 ) {
            return null;
        }
		return helper2(inorder, 0, inorder.length - 1, postorder, 0, postorder.length-1);
    }
	
	private TreeNode helper2(int[] in, int inStart, int inEnd, int[] post, int poStart, int poEnd) {
		if (inStart > inEnd || poStart > poEnd || poStart < 0) {
			return null;
		}
		
		TreeNode root = new TreeNode(post[poEnd]);
		int index = findValArray(post[poEnd], in, inStart, inEnd);
		int len = index - inStart;
		root.left = helper2(in, inStart, index-1, post, poStart, poStart + len - 1);
		root.right = helper2(in, index + 1, inEnd, post, poStart+len, poEnd-1);
		return root;
	}
	
	/**
	 * 108. Convert Sorted Array to Binary Search Tree
	 */
	public TreeNode sortedArrayToBST(int[] nums) {
		if (nums == null || nums.length == 0) {
            return null;
        }
        return helper(nums, 0, nums.length -1); 
	}
	
	private TreeNode helper(int[] nums, int start, int end) {
		if (start > end) return null;
		if (start == end) return new TreeNode(nums[start]);
		
		int mid = start + (end-start)/2;
		TreeNode root = new TreeNode(nums[mid]);
		root.left = helper(nums, start, mid-1);
		root.right = helper(nums, mid+1, end);
		return root;
		
	}
	
	/**
	 * 109. Convert Sorted List to Binary Search Tree
	 */
	public TreeNode sortedListToBST(ListNode head) {
		if (head == null) return null;
		if (head.next == null) return new TreeNode(head.val);
		
		ListNode slow = head, fast = head, prev = null;
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			prev = slow;
			slow = slow.next;
		}
		
		TreeNode root = new TreeNode(slow.val);
		prev.next = null;
		root.left = sortedListToBST(head);
		root.right = sortedListToBST(slow.next);
		return root;
    }
	
	/**
	 * 255. Verify Preorder Sequence in Binary Search Tree
	 */
	public boolean verifyPreorder(int[] preorder) {
        int low = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int p : preorder) {
            if (p < low) {
                return false;
            }
            while (!stack.isEmpty() && p > stack.peek()) {
                low = stack.pop();
            }
            stack.push(p);
        }
        return true;
    }
	
	public boolean verifyPreorderNoExtraSpace(int[] preorder) {
        int low = Integer.MIN_VALUE, i = -1;
        for (int p : preorder) {
        	if (p < low) {
        		return false;
        	}
        	while (i >= 0 && p > preorder[i]) {
        		low = preorder[i--];
        	}
        	preorder[++i] = p;
        }
        return true;
    }
	
	public boolean verifyPreorder(int[] preorder, int start, int end, int min, int max) {
	    if (start > end) {
	        return true;
	    }
	    int root = preorder[start];
	    if (root > max || root < min) {
	        return false;
	    }
	    
	    int rightIndex = start;
	    while (rightIndex <= end && preorder[rightIndex] <= root) {
	        rightIndex++;
	    }
	    return verifyPreorder(preorder, start + 1, rightIndex - 1, min, root) && verifyPreorder(preorder, rightIndex, end, root, max);
	}
	
	
	/**
	 * 536. Construct Binary Tree from String
	 * 要注意，数字可以是负数
	 */
	public TreeNode str2tree(String s) {
        if (s == null || s.length() == 0) return null;
        if (!s.contains("(") && !s.contains(")")) {
        	int sign = 1;
        	if (s.charAt(0) == '-') {
        		sign = -1;
        		s = s.substring(1);
        	}
            return new TreeNode(sign * Integer.valueOf(s));
        }
        
        int i = 0;
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '-') ) {
            i++;
        }
        TreeNode root = new TreeNode(Integer.valueOf(s.substring(0, i)));
        
        int j = i+1, count = 1;
        while (j < s.length() && count != 0) {
            if (s.charAt(j) == ')') count--;
            if (s.charAt(j) == '(') count++;
            j++;
        }
        
        root.left = str2tree(s.substring(i+1, j-1));
        if (j+1 < s.length()-1)
        	root.right = str2tree(s.substring(j+1, s.length()-1));
        return root;
    }
	
	public TreeNode str2treeClean(String s) {
	    if (s == null || s.length() == 0) return null;
	    int firstParen = s.indexOf("(");
	    int val = firstParen == -1 ? Integer.parseInt(s) : Integer.parseInt(s.substring(0, firstParen));
	    TreeNode cur = new TreeNode(val);
	    if (firstParen == -1) return cur;
	    int start = firstParen, leftParenCount = 0;
	    for (int i=start;i<s.length();i++) {
	        if (s.charAt(i) == '(') leftParenCount++;
	        else if (s.charAt(i) == ')') leftParenCount--;
	        
	        if (leftParenCount == 0 && start == firstParen) {
	        	cur.left = str2tree(s.substring(start+1,i)); 
	        	start = i+1;
	        } else if (leftParenCount == 0) {
	        	cur.right = str2tree(s.substring(start+1,i));
	        }
	    }
	    return cur;
	}
	
	
	public TreeNode constructBST(String preOrder) {
		if (preOrder == null || preOrder.length() == 0) 
			return null;
		int num = (int)preOrder.charAt(0) - '0';
		TreeNode root = new TreeNode(num);
		
		int right = 1;
		while (right < preOrder.length()) {
			if (preOrder.charAt(right) - '0' > num) break;
			right++;
		}
		
		root.left = constructBST(preOrder.substring(1, right));
		root.right = constructBST(preOrder.substring(right));
		
		return root;
	}
	
	/**
	 * Deep copy of Binary Tree
	 */
	Map<TreeNode, TreeNode> map = new HashMap<>();
	public TreeNode deepCopy(TreeNode root) {
		if (root == null) return null;
		
		deepCopy(null, root, true);
		
		BinaryTreeTraversal p = new BinaryTreeTraversal();
		System.out.println("Original Tree: ");
		p.inOrderRecursive(root);
		System.out.println(this.serialize(root));
		System.out.println("Copy Tree: ");
		p.inOrderRecursive(map.get(root));
		System.out.println(this.serialize(map.get(root)));
		
		return map.get(root);
	}
	
	public void deepCopy(TreeNode parent, TreeNode root, boolean isLeft) {
		if (root == null) {
			return;
		}
		TreeNode copy = new TreeNode(root.val);
		map.put(root, copy);
		if (parent != null) {
			if (isLeft) map.get(parent).left = copy;
			else map.get(parent).right = copy;
		}
		
		deepCopy(root, root.left, true);
		deepCopy(root, root.right, false);
	}
	
	public static void main(String[] args) {
		ConstructBrinayTree clz = new ConstructBrinayTree();
		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		n1.right = n2;
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		n2.left = n3; n2.right = n4;
		
		clz.deepCopy(n1);
		
		String ser = clz.serialize(n1);
		System.out.println(ser);
		
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(3);
		clz.sortedListToBST(l1);
		
		TreeNode tr = clz.str2tree("4(2(3)(1))(6(5))");
		
		clz.verifyPreorderNoExtraSpace(new int[]{5,6,2,3,8});
		
		BinaryTreeTraversal p = new BinaryTreeTraversal();
		TreeNode root = clz.constructBST("53869");
		p.inOrderRecursive(root);
		
		
		
	}
	
}
