import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniqueBST {
	/**
	 * 96. Unique Binary Search Trees
	 * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
	 * 先来看DFS 理解思路，表示从 start - end 一共有几种方法构成树，开始是1,n
	 *  
	 */
	public int numTreesDFS(int start, int end) {
		if (start >= end) return 1;
        int sum = 0;  
        //从start 到 end，分别做root， 算左右子树有多少组合
        for (int i = start; i <= end; i++) {
        	int left = numTreesDFS(start, i-1);
        	int right = numTreesDFS(i+1, end);
        	sum += left * right;
        }
        return sum;
    }
	
	/**
	 * DFS太多重复计算了。显然可以memorize
	 * 如以1为节点，则left subtree只能有0个节点，而right subtree有2, 3两个节点。
	 * 			 所以left/right subtree一共的combination数量为：f(0) * f(2) = 2
	 * 以2为节点，则left subtree只能为1，right subtree只能为2：1 * 1 = 1
	 * 以3为节点，则left subtree有1, 2两个节点，right subtree有0个节点：f(2)*f(0) = 2
	 *  f(0) = 1
		f(n) = f(0)*f(n-1) + f(1)*f(n-2) + ... + f(n-2)*f(1) + f(n-1)*f(0)
	 */
	public int numTrees(int n) {
		int[] f = new int[n+1];
		f[0] = 1;
		f[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int root = 1;  root <= i; root++) {
				f[i] += f[root-1] * f[i-root];
			}
		}
		return f[n];
    }
	
	/**
	 * 95. Unique Binary Search Trees II
	 * 现在要构建了
	 */
	public List<TreeNode> generateTrees(int n) {
		if (n == 0) {
            return new ArrayList<TreeNode>();
        }
        return generate(1, n);
    }
    
    private List<TreeNode> generate(int start, int end) {
    	List<TreeNode> list = new ArrayList<TreeNode>();
    	if (start > end) {
    		list.add(null);
    		return list;
    	}
        if (start == end) {
            TreeNode node = new TreeNode(start);
            list.add(node);
            return list;
        }
        
        for (int i = start; i <= end; i++) {
        	List<TreeNode> left = generate(start, i-1);
        	List<TreeNode> right = generate(i+1, end);
        	for (TreeNode l : left) {
        		for (TreeNode r : right) {
        			TreeNode root = new TreeNode(i);
        			root.left = l;
        			root.right = r;
        			list.add(root);
        		}
        	}
        }
        return list;
    }
    
	
	public static void main(String[] args) {
		UniqueBST clz = new UniqueBST();
		
		System.out.println(clz.numTreesDFS(1, 4));
		
		List<TreeNode> list = clz.generateTrees(3);
		for (TreeNode n : list) {
			System.out.println(n.toString());
		}
		
	}
}
