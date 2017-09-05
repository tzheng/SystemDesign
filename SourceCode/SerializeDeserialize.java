import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SerializeDeserialize {
	 // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
    	if (root == null) {
            return "";
        }
        
        ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
        queue.add(root);
        
        for (int i = 0; i < queue.size(); i++) {
            TreeNode node = queue.get(i);
            if (node == null) {
                continue;
            }
            queue.add(node.left);
            queue.add(node.right);
        }
        
        while (queue.get(queue.size() -1) == null) {
            queue.remove(queue.size() -1);
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(queue.get(0).val);
        for (int i = 1; i < queue.size(); i++) {
        	if (queue.get(i) == null) {
        		sb.append(",#");
        	} else {
        		sb.append("," + queue.get(i).val);
        	}
        }
        return sb.toString();
 
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String str) {
    	if (str == null || "".equals(str)) return null;
    	String[] split = str.split(",");
    	if (split.length == 0) return null;

    	List<TreeNode> list = new ArrayList<>();
    	list.add(new TreeNode(Integer.valueOf(split[0])));
    	int index = 0;
    	boolean isLeft = true;
    	for (int i = 1; i < split.length; i++) {
    		TreeNode newNode = null;
    		if (!"#".equals(split[i])) {
    			newNode = new TreeNode(Integer.valueOf(split[i]));
    			list.add(newNode);
    		}
    		if (isLeft) {
    			list.get(index).left = newNode;
    		} else {
    			list.get(index).right = newNode;
    			index++;
    		}
    		isLeft = !isLeft;
    	}

    	return list.get(0);
    }
    
    
    /**
     * 变体，和序列化成string 不一样，这里需要序列化成list
     * 核心思路是一样的，不过是null替代了# 而已
     * Given a binary tree of integers, write code to store the tree into a list of integers and recreate the original tree from a list of integers. 

			Here's what your method signatures should look like (in Java): 
			
			List<Integer> store(Node root) 
			Node restore(List<Integer> list) 
			
			Example Tree: 
			
			 5 
			/ \ 
		   3   2 
		   /  / \ 
		  1  6   1
     * @param args
     */
    public List<Integer> store(TreeNode root) {
    	List<Integer> res = new ArrayList<>();
    	if (root == null) 
    		return res;
    	
    	Queue<TreeNode> q = new LinkedList<>();
    	q.offer(root);
    	
    	while (!q.isEmpty()) {
    		TreeNode top = q.poll();
    		res.add(top == null ? null : top.val);
    		if (top != null) {
    			q.offer(top.left);
    			q.offer(top.right);
    		}
    	}
    	while (res.get(res.size()-1) == null) {
    		res.remove(res.size()-1);
    	}
    	System.out.println(Arrays.toString(res.toArray()));
    	return res;
    }
    
    class DoubleNode {
    	Integer val;
    	DoubleNode prev = null, next = null;
    	public DoubleNode(Integer v) { val = v;};
    }
    
    public DoubleNode storeToDoublyLinkedList(TreeNode root) {
    	if (root == null) 
    		return null;
    	
    	Queue<TreeNode> q = new LinkedList<>();
    	q.offer(root);
    	DoubleNode head = new DoubleNode(-1);
    	DoubleNode node = head;
    	
    	while (!q.isEmpty()) {
    		TreeNode top = q.poll();
    		DoubleNode curr = new DoubleNode(top == null ? null : top.val);
    		node.next = curr;
    		curr.prev = node;
    		node = curr;
    		if (top != null) {
    			q.offer(top.left);
    			q.offer(top.right);
    		}
    	}
    	System.out.print("\nTo DLL: ");
    	node = head.next;
    	while (node.next != null) {
    		System.out.print(node.val + "->");
    		node = node.next;
    	}
    	System.out.println();
    	while (node.prev != null) {
    		System.out.print(node.val + "->");
    		node = node.prev;
    	}
    	System.out.println();
    	return head;
    }
    
    
    
    public TreeNode restore(List<Integer> list) {
    	if (list == null || list.size() == 0 || list.get(0) == null)
    		return null;
    	
    	List<TreeNode> res = new ArrayList<>();
    	TreeNode root = new TreeNode(list.get(0));
    	res.add(root);
    	
    	int i = 0, j = 1;
    	boolean isLeft = true;
    	while (j < list.size()) {
    		root = res.get(i);
    		TreeNode newNode = null;
    		if (list.get(j) != null) {
    			newNode = new TreeNode(list.get(j));
    			res.add(newNode);
    		}
    		if (isLeft) {
    			root.left = newNode;
    		} else {
    			root.right = newNode;
    			i++;
    		}
    		isLeft = !isLeft;
    		j++;
    	}
    	
    	
    	return res.get(0);
    }
    
    
    
    public static void main(String[] args) {
    	TreeNode n1 = new TreeNode(1);
    	TreeNode n2 = new TreeNode(2);
    	TreeNode n3 = new TreeNode(3);
    	TreeNode n4 = new TreeNode(4);
    	TreeNode n5 = new TreeNode(5);
    	TreeNode n6 = new TreeNode(6);
    	
    	n5.left = n3; n5.right = n2;
    	n3.left = n1; 
    	n2.left = n6; n2.right = n1;
    	
    	
    	
    	
    	SerializeDeserialize clz = new SerializeDeserialize();
    	List<Integer> list = clz.store(n5);
    	
    	TreeNode restore = clz.restore(list);
    	clz.preorder(restore);
    	System.out.println();
    	clz.inorder(restore);
    	
    	clz.storeToDoublyLinkedList(n5);
    }
    
    public void preorder(TreeNode root) {
    	if (root == null) return;
    	System.out.print(root + ",");
    	preorder(root.left);
    	preorder(root.right);
    }
    
    public void inorder(TreeNode root) {
    	if (root == null) return;
    	
    	inorder(root.left);
    	System.out.print(root + ",");
    	inorder(root.right);
    }
}
