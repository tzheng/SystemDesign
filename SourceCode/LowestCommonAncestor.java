import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class LowestCommonAncestor {
	
	/**
	 * LC 235 Lowest Common Ancestor of a Binary Search Tree 
	 * 
	 * Time Complexity: O(n)
	 * 
	 * 
	 */
	public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return null;
        }   
        if (p.val > q.val) {
        	return lowestCommonAncestor(root, q, p);
        }
        if (root == p || root == q) 
        	return root;
        
        if (root.val > q.val) {
        	return lowestCommonAncestor(root.left, p, q);
        } else if (root.val < p.val) {
        	return lowestCommonAncestor(root.right, p, q);
        } else {
        	return root;
        }
        
        /**
         * or iterative way
         *while (root != null) {
            if (root == p || root == q) return root;
            if (root.val > q.val) {
                root = root.left;
            } else if (root.val < p.val) {
                root = root.right;
            } else {
                return root;
            }
	      }
	      return root;
         */
	}
	
	
	/**
	 * 拓展到普通二叉树： LC 236 Lowest Common Ancestor of a Binary Tree 
	 * 
	 * Time Complexity: O(n)
	 * 
	 * 做法很简单，就是左右寻找，如果一个在左边，一个在右边，那么当前点就是LCA
	 * 如果在都在一侧，就继续往下找
	 * 
	 */
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p,TreeNode q) {
		if (root == null || root == p || root == q) {
			return root;
		}
		
		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);
		
		if (left!= null && right != null) {
			return root;
		}
		return left == null ? right : left;
	}
	
	public TreeNode lowestCommonAncestorIter(TreeNode root, TreeNode p,TreeNode q) {
		Map<TreeNode, TreeNode> parent = new HashMap<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        parent.put(root, null);
        stack.push(root);
        //就是通过iterative遍历，建立一个 root->p 和 root->q 的路径，然后找路径的交点。
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }
        Set<TreeNode> ancestors = new HashSet<>();
        while (p != null) {
            ancestors.add(p);
            p = parent.get(p);
        }
        while (!ancestors.contains(q))
            q = parent.get(q);
        return q;
	}
	
	/**
	 * Followup 1
	 * 如果是带有父节点信息的二叉树
	 * 复杂度O(h),h为树的高度
	 * https://www.hrwhisper.me/algorithm-lowest-common-ancestor-of-a-binary-tree/
	 * 
	 * 看上面的iterative的解法，实际上就是建立一个node到他的父亲节点的映射。这里如果题目给出父亲节点了
	 * 那就直接找他们哪儿相交就好了。
	 * 问题转化为 160. Intersection of Two Linked Lists Add to List
	 */
	public TreeNode lowestCommonAncestorParent(TreeNode root, TreeNode p,TreeNode q) {
		Set<TreeNode> path = new HashSet<>();
		while (p != null) {
			path.add(p);
			p = p.parent;
		}
		
		while (!path.contains(q)) {
			q = q.parent;
		}
		return q;
	}
	
	/**
	 * 160. Intersection of Two Linked Lists
	 * 如果不用额外空间，就算两个list的长度。
	 */
	public ListNode getIntersectionNode(ListNode p, ListNode q) {
        ListNode a = p, b = q;
        int len1 = 0, len2 = 0;
        while (a != null) {
        	len1++;
        	a = a.next;
        }
        while (b != null) {
        	len2++;
        	b = b.next;
        }
        
        while (len1 > len2) {
        	p = p.next;
        	len1--;
        }
        while (len2 > len1) {
        	q = q.next;
        	len2--;
        }
        
        while (p != q) {
        	p = p.next;
        	q = q.next;
        }
        return p;
    }
	
	
	/**
	 * Followup 2
	 * 如果一次性给出多组查询，解法能有什么改进，空间时间复杂度又是什么？
	 * 
	 * http://baozitraining.org/blog/binary-tree-common-anncestor/
	 * 首先，可以考虑预处理和缓存。也就是说，当某组查询发生过以后将其结果缓存起来，
	 * 如果后来的查询还有查到此数据的，可以将其从缓存里直接返回。
	 * 我们可以预处理一些数据事先缓存起来，以供查询时调用。
	 * 这种方法可以是每次查询时每一组数据的平均时间复杂度可以降低到
	 *  O(1) * k/n^2 + O(n) * (n^2 - k)/n^2 = O(n - ((n-1)k)/n^2) . 
	 *  其中k表示的缓存多少组数据。空间复杂度也就上升到了O(k). 这种思想是典型的空间换时间的方法，在是集中比较常用。
	 */
	
	
	
	/**
	 * Followup 3.1: find deepest node in the tree
	 * 这里tree不一定是 binary tree
	 */
	TreeNode deepestNode = null;
	int maxLevel = -1;
	public void findDeepestNode(TreeNode root, int level) {
		if (root == null) {
			return;
		}
		
		if (level > maxLevel) {
			deepestNode = root;
			maxLevel = level;
		}
		
		for (TreeNode child : root.children) {
			findDeepestNode(child, level+1);
		}
		
	}
	
	/**
	 * Followup 3.2: find LCA of all deepest leaves in a tree 
	 * 这里tree不一定是 binary tree
	 */
	public ResultType LCADeepestNode(TreeNode root) {
		if (root == null || root.children.size() == 0) {
			return new ResultType(root, root == null?0:1);
		}
		
		TreeNode retNode = root;
		int retMax = 0;
		
		for (TreeNode child : root.children) {
			ResultType tmp = LCADeepestNode(child);
			//如果找到一个孩子，他的最大深度比之前访问过所有的点的最大深度都深
			//那么说明这个孩子是LCA， 特别要注意的是，当左右子树height不同，而且较大的那个是1的时候，
			//公共祖先是root，其他情况，公共祖先是height比较大的那个孩子
			
			if (tmp.maxDepth > retMax) {
				retNode = tmp.maxDepth == 1 ? root : tmp.node;
				retMax = tmp.maxDepth;
			} else if (tmp.maxDepth == retMax) {
				//如果有个孩子，他的最大深度和之前某个孩子的最大深度一样，说明他们俩
				//的父节点，也就是 root 才是 LCA
				retNode = root;
			}
		}
		
		return new ResultType(retNode, retMax + 1);
	}
	
	
	/**
	 * 简化版 find LCA of all deepest leaves in a binary tree 
	 * 特别要注意的是，当左右子树height不同，而且较大的那个是1的时候，
	 * 公共祖先是root，其他情况，公共祖先是height比较大的那个孩子
	 */
	public ResultType LCADeepestNodeBinary(TreeNode root) {
		if (root == null || (root.left == null && root.right == null)) {
			return new ResultType(root, root == null?0:1);
		}
		
		TreeNode retNode = root;
		int retMax = 0;
		
		ResultType left = LCADeepestNodeBinary(root.left);
		ResultType right = LCADeepestNodeBinary(root.right);
		
		if (left.maxDepth == right.maxDepth) {
			retNode = root;
			retMax = left.maxDepth;
		} else {
			retMax = Math.max(left.maxDepth, right.maxDepth);
			retNode = left.maxDepth > right.maxDepth ? left.node : right.node;
			if (retMax == 1) {
				retNode = root;
			}
		}
		return new ResultType(retNode, retMax + 1);
	}
	
	/**
	 * 还是找二叉树最深节点的公共祖先，但是用iterative的方法来做。
	 * 这样的话，就需要hashmap 来存节点到父亲的路径。然后层序遍历，
	 * 最后一层的最左和最右节点的公共祖先就是。
	 */
	public TreeNode LCADeepestNodeBinaryIter(TreeNode root) {
		HashMap<TreeNode, TreeNode> map = new HashMap<>();
		TreeNode left = null, right = null;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				TreeNode curr = queue.poll();
				if (i == 0) left = curr;
				if (i == size - 1) right = curr;
				if (curr.left != null) {
					queue.offer(curr.left);
					map.put(curr.left, curr);
				}
				if (curr.right != null) {
					queue.offer(curr.right);
					map.put(curr.right, curr);
				}
			}
		}
		
		while (left != right) {
			left = map.get(left);
			right = map.get(right);
		}
		return left;
	}
	
	
	/**
	 *        1
	        /  \
	       2    3 
	           /  \
	          4    5
		比如这个例子，最深的节点是4 和 5. 他们的公共节点是3。所以答案是3。最深的节点可以只有一个，那就返回节点本身。
	 *
	 */
	
	class ResultType {
		TreeNode node;
		int maxDepth;
		public ResultType(TreeNode n, int d) {
			node = n;
			maxDepth = d;
		}
		public String toString() {
			return node.val + " : " + maxDepth;
		}
	}
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.right = new TreeNode(5);
		root.right.right.left = new TreeNode(6);
		root.right.left.right = new TreeNode(7);
		
		LowestCommonAncestor clz = new LowestCommonAncestor();
//		
//		TreeNode deepest = testData2();
//		clz.findDeepestNode(deepest, 0);
//		System.out.println(clz.deepestNode.val);
//		
//		ResultType lcaDeepest = clz.LCADeepestNode(deepest);
//		System.out.println(lcaDeepest);
		
		ResultType lcaBinary = clz.LCADeepestNodeBinary(root);
		System.out.println(lcaBinary);
	}
	
	/**
	 * class TreeNode {
		    int val;
		    TreeNode left;
		    TreeNode right;
		    List<TreeNode> children;
		    
		    TreeNode(int x) { val = x; }
		}
	 */
	
	public static TreeNode testData1() {
		 TreeNode n1 = new TreeNode(1);
         TreeNode n2 = new TreeNode(2);
         TreeNode n3 = new TreeNode(3);
         TreeNode n4 = new TreeNode(4);
         TreeNode n5 = new TreeNode(5);
         TreeNode n6 = new TreeNode(6);
         TreeNode n7 = new TreeNode(7);
         TreeNode n8 = new TreeNode(8);
         TreeNode n9 = new TreeNode(9);
         TreeNode n10 = new TreeNode(10);
         
         n1.children.add(n2);
         n1.children.add(n3);
         n1.children.add(n4);
         n2.children.add(n5);
         n2.children.add(n6);
         n4.children.add(n7);
         n5.children.add(n8);
         n5.children.add(n9);
//         n6.children.add(n10);
//         n7.children.add(new TreeNode(11));
         return n1;
	}
	
	public static TreeNode testData2() {
		TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);
        
        n1.children.add(n2);
        n1.children.add(n3);
        n1.children.add(n4);
        n2.children.add(n5);
        n5.children.add(n6);
        n4.children.add(n7);
        n7.children.add(n8);
        n8.children.add(n9);
        
        return n1;
	}
	
	public static TreeNode testData3() {
		TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);
        
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        
        return n1;
	}
}
