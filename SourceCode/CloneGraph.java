import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CloneGraph {
	/**
	 * 133	Clone Graph   
	 * 简单的BFS， O(V+E)
	 */
	public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
		if (node == null) return null;
		HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
		Set<UndirectedGraphNode> visited = new HashSet<>();
		Queue<UndirectedGraphNode> queue = new LinkedList<UndirectedGraphNode>();
		queue.offer(node);
		visited.add(node);
		UndirectedGraphNode newNode = new UndirectedGraphNode(node.label);
		map.put(node, newNode);
		
		while (!queue.isEmpty()) {
			UndirectedGraphNode curr = queue.poll();
			for (UndirectedGraphNode child : curr.neighbors) {
				
				UndirectedGraphNode cloneChild = map.get(child);
				if (!map.containsKey(child)) {
					cloneChild = new UndirectedGraphNode(child.label);
					map.put(child, cloneChild);
				} 
				map.get(curr).neighbors.add(cloneChild);
				if (visited.contains(child))
					continue;
				
				queue.offer(child);
				visited.add(child);
			}
			
		}
		return map.get(node);
	}
	
	/**
	 * 138. Copy List with Random Pointer
	 * 这题比上面的clone graph简单。考虑到只是一个linkedlist，一个节点只有两个指针。
	 * 所以可以先把整个list 到结尾复制一遍，不管next, random node
	 * 然后复制完了之后，再从头到尾走一遍，把next, random 指针补上。
	 */
	 public RandomListNode copyRandomList(RandomListNode head) {
         if (head == null) return null;
         Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
         // loop 1. copy all the nodes
         RandomListNode node = head;
         while (node != null) {
           map.put(node, new RandomListNode(node.label));
           node = node.next;
         }
         // loop 2. assign next and random pointers
         node = head;
         while (node != null) {
           map.get(node).next = map.get(node.next);
           map.get(node).random = map.get(node.random);
           node = node.next;
         }
         
         return map.get(head);
	 }
	
	 
	public RandomListNode copyRandomListLinear(RandomListNode head) {
		//1 ,simple way
        RandomListNode node = head, next;

		// First round: make copy of each node,
		// and link them together side-by-side in a single list.
		while (node != null) {
			next = node.next;
			node.next = new RandomListNode(node.label);
			node.next.next = next;
			node = next;
		}

		// Second round: assign random pointers for the copy nodes.
		node = head;
		while (node != null) {
			if (node.random != null) {
				node.next.random = node.random.next;
			}
			node = node.next.next;
		}

		// Third round: restore the original list, and extract the copy list.
		node = head;
		RandomListNode dummy = new RandomListNode(0);
		RandomListNode copyIter = dummy;

		while (node != null) {
			copyIter.next = node.next;
            copyIter = copyIter.next;
            node.next = node.next.next;
            node = node.next;
		}

		return dummy.next;
	}
}

class RandomListNode {
	int label;
	RandomListNode next, random;
	RandomListNode(int x) { this.label = x; }
};
 

	 
class UndirectedGraphNode {
	int label;
	List<UndirectedGraphNode> neighbors;
 	UndirectedGraphNode(int x) { 
 		label = x; 
 		neighbors = new ArrayList<UndirectedGraphNode>(); 
 	}
};