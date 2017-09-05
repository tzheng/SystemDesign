
/**
 * https://www.hackerrank.com/challenges/even-tree
	You are given a tree (a simple connected graph with no cycles). The tree has  nodes numbered from 
	to  and is rooted at node . Find the maximum number of edges you can remove from the tree to get a 
	forest such that each connected component of the forest contains an even number of vertices.

	        o
	  /    |   |   \
	o     o   o    o
	|      
	o
	
	比如可以删掉一个边变成：
	         o
	  x    |   |   \
	o     o   o    o
	|      
	o
	
	结果里有两个tree，分别有2个和四个node，符合条件，这就是答案，因为再删就不符合条件了
	return是一个list，里面是所有新生成的tree的root
 *
 */
public class EventTree {
		
	
	public static void main(String[] args) {
		
	}
}
