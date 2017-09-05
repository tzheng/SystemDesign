import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PrintTrees {
	/**
	 * 199. Binary Tree Right Side View
	 * 注意，如果有右边节点优先打印右边，但是左边仍然要继续遍历，因为左边有可能走到更深。
	 * 所以通过 result.size() == depth 来判断当前层是不是已经打印过了。
	 */
	//	rightSideView(root, 0, result);
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
	 * 545. Boundary of Binary Tree
	 * 先打印左边所有的非叶子节点，然后打印左右子树的叶子，然后打印右边的非叶子
	 * 打印左右非叶子节点的时候，和rightside view类似，要考虑一侧没有元素的时候
	 * 另一侧可能还有。
	 */
	List<Integer> boundry = new ArrayList<Integer>();
	public List<Integer> boundaryOfBinaryTree(TreeNode root) {
		if (root == null) return boundry;
		boundry.add(root.val);
		printLeftBoundry(root.left); //注意，是从left开始
		printLeaves(root.left);
		printLeaves(root.right);
		printRightBoundry(root.right); //从right开始
		return boundry;
	}
	
	private void printLeftBoundry(TreeNode root) {
		if (root == null) return;
		if (root.left == null && root.right == null) return;
		boundry.add(root.val);
		if (root.left != null) {
			printLeftBoundry(root.left);
		} else {
			printLeftBoundry(root.right);
		}
	}
	
	private void printRightBoundry(TreeNode root) {
		if (root == null) return;
		if (root.left == null && root.right == null) return;
		if (root.right != null) {
			printRightBoundry(root.right);
		} else  {
			printRightBoundry(root.left);
		}
		boundry.add(root.val);
	}
	
	private void printLeaves(TreeNode root) {
		if (root == null) return;
		if (root.left == null && root.right == null) {
			boundry.add(root.val);
			return;
		}
		printLeaves(root.left);
		printLeaves(root.right);
	}
	
	/**
	 * 543. Diameter of Binary Tree
	 * 注意，return path的长度，最后n个node组成的path长度n-1
	 * 
	 * follow up: 如果树很深怎么办？ 递归空间不够，只能用iterative来做。
	 * http://techieme.in/tree-diameter/
	 */
	int diameter = 0;
	public int diameterOfBinaryTree(TreeNode root) {
		if (root == null) return 0;
		diameterHelper(root);
		return diameter - 1;
	}
	
	private int diameterHelper(TreeNode root) {
		if (root == null) return 0;
		int left = diameterHelper(root.left);
		int right = diameterHelper(root.right);
		int maxHeight = Math.max(left, right) + 1;
		diameter = Math.max(diameter, Math.max(maxHeight, left + right + 1));
		return maxHeight;
	}
	
	/**
	 * 543. Diameter of Binary Tree 
	 * Follow up: print the longest path
	 * 要打印的话，那对于每个节点，都要记root到他的路径
	 */
	class RetType {
		int depth;
		ArrayList<Integer> path = new ArrayList<>();
		public RetType(int d) {
			depth = d;
		}
	}
	int max;
	String path;
	public void diameterOfBinaryTreePath(TreeNode root) {
		diameterPathHelper(root);
		System.out.println(max + " : " + path);
	}
	
	private RetType diameterPathHelper(TreeNode root) {
		if (root == null) return null;
		if (root.left == null && root.right == null) {
			RetType ret = new RetType(1);
			ret.path.add(root.val);
			return ret;
		}
		
		RetType left = diameterPathHelper(root.left);
		RetType right = diameterPathHelper(root.right);
		
		int depth = Math.max(left.depth, right.depth) + 1;
		if (left.depth + 1 + right.depth > max) {
			max = left.depth + 1 + right.depth;
			ArrayList<Integer> rightPath = new ArrayList<Integer>(right.path);
			Collections.reverse(rightPath);
			path = Arrays.toString(left.path.toArray()) 
					+ "," + root.val + ", "
					+ Arrays.toString(rightPath.toArray());
		}
		
		RetType curr = new RetType(depth);
		curr.path = left.depth > right.depth ? 
				new ArrayList<Integer>(left.path) : new ArrayList<Integer>(right.path);
//		curr.path = left.depth > right.depth ? left.path : right.path;
		curr.path.add(root.val);
		return curr;
	}

	
	/**
	 * 257. Binary Tree Paths
	 */
	public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<String>();
        if (root == null) {
            return result;
        }
        pathHelper(root, "" + root.val, result);
        return result;
    }
    //O(b^(h+1)-1) 空间 O(h) 递归栈空间 对于二叉树b=2
    private void pathHelper(TreeNode root, String path, List<String> result) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            result.add(path);
            return;
        }
        if (root.left != null) {
            pathHelper(root.left, path + "->" + String.valueOf(root.left.val), result);
        }
        if (root.right != null) {
            pathHelper(root.right, path + "->" + String.valueOf(root.right.val), result);
        }
    }
	
		
	/**
	 * 116. Populating Next Right Pointers in Each Node
	 * O(n) time,  O(n) space
	 */
    public void connect(TreeLinkNode root) {
    	if (root == null) {
            return;
        }
        
        Queue<TreeLinkNode> queue = new LinkedList<TreeLinkNode>();
        queue.offer(root);
        while (!queue.isEmpty())  {
            int size = queue.size();
            TreeLinkNode prev = null;
            for (int i = 0; i < size; i++) {
                TreeLinkNode curr = queue.poll();
                if (prev == null) {
                    prev = curr;
                } else {
                    prev.next = curr;
                    prev = prev.next;
                }
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
        }
    }
    /**
     * 如果是 perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
     * 可以用更简单的办法。
     * O(1) space, 
     */
	public void connect1(TreeLinkNode root) {
		TreeLinkNode level_start = root;
		while (level_start != null) {
			TreeLinkNode cur = level_start;
			while (cur != null) {
				if (cur.left != null)
					cur.left.next = cur.right;
				if (cur.right != null && cur.next != null)
					cur.right.next = cur.next.left;

				cur = cur.next;
			}
			level_start = level_start.left;
		}
	}
    
	
	/**
	 * 101. Symmetric Tree
	 * 还是属于遍历的范畴
	 */
	 public boolean isSymmetric(TreeNode root) {
		 if (root == null ) {
	         return true;
	     }
		 return helper(root.left, root.right);
	 }

	private boolean helper(TreeNode left, TreeNode right) {
		if (left == null || right == null) {
			return left == right;
		}
		if (left.val != right.val) {
			return false;
		}

		return helper(left.left, right.right) && helper(left.right, right.left);
	}
	
	
	public boolean isSymmetricIter(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        if(root == null) return true;
        q.add(root.left);
        q.add(root.right);
        while(q.size() > 1){
            TreeNode left = q.poll(),
                     right = q.poll();
            if(left== null&& right == null) continue;
            if(left == null ^ right == null) return false;
            if(left.val != right.val) return false;
            q.add(left.left);
            q.add(right.right);
            q.add(left.right);
            q.add(right.left);            
        }
        return true;
    }
	
	/**
	 * }
follow up是如果有left, mid, right三个child怎么办:
public class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        if(root==null) return res;
        Queue<TreeNode> queue1=new LinkedList<>();
        Queue<TreeNode> queue2=new LinkedList<>();
        queue1.offer(root);
        queue2.offer(root);
        while(!queue1.isEmpty()){
            int size=queue1.size();
            for(int i=0;i<size;i++){
                TreeNode cur1=queue1.poll();
                TreeNode cur2=queue2.poll();
                //这里就写了两种情况，面试时把所有的情况必须写全
                if(cur1.left!=null&&cur2.right==null) return false;
                if(cur1.right!=null&&cur2.left==null) return false;
                
                if(cur1.val!=cur2.val) return false;
                if(cur1.left!=null) queue1.offer(cur.left);
                if(cur1.mid!=null) queue1.offer(cur.mid);
                if(cur1.right!=null) queue1.offer(cur.right);
                if(cur2.right!=null) queue2.offer(cur.left);
                if(cur2.mid!=null) queue2.offer(cur.mid);
                if(cur2.left!=null) queue2.offer(cur.right);
            }
        }
        return true;
    }
}
**/
	
    class TreeLinkNode {
    	      int val;
    	      TreeLinkNode left, right, next;
    	      TreeLinkNode(int x) { val = x; }
    }
    
    /**
     * 156. Binary Tree Upside Down
     * 
     */
    public TreeNode upsideDownBinaryTree(TreeNode root) {
    	if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        TreeNode left = upsideDownBinaryTree(root.left);
        left.left = root.right;
        left.right = root;
        root.right = null;
        root.left = null; //这个不要忘了，不然会循环
        return left;
    }
    
    
    /**
     * 298. Binary Tree Longest Consecutive Sequence
     */
    public int longestConsecutive(TreeNode root) {
        if (root == null) return 0;
        helper(null, root, 0);
        return max;
    }
    private void helper(TreeNode parent, TreeNode node, int len) {
        if (node == null) return;
        
        if (parent == null || parent.val + 1 == node.val) {
            len++;
            max = Math.max(max, len);
        } else {
            len = 1;
        }
        
        helper(node, node.left, len);
        helper(node, node.right, len);
    }
    
    
    /**
     * Check a binary tree is FULL or not
     * 	full tree, ether leaf, or have 2 child
     */
    boolean isFull(TreeNode root) {
    	if (root == null) return true;
    	if (root.left == null && root.right == null) 
    		return true;
    	
    	if (root.left == null || root.right == null) 
    		return false;
    	
    	return isFull(root.left) && isFull(root.right);
    }
    
    
	public static void main(String[] args) {
		PrintTrees clz = new PrintTrees();
		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(5);
		n1.left = n2; n1.right = n3;
		n2.left = n4; n2.right = n5;
		
		clz.diameterOfBinaryTreePath(n1);
		
		TreeNode r1 = new TreeNode(1);
		TreeNode r2 = new TreeNode(2);
		r1.left = r2;
		clz.upsideDownBinaryTree(r1);
		
		int min = Integer.MIN_VALUE;
		System.out.println(-1*min);
	}
	
	
	
	
}
