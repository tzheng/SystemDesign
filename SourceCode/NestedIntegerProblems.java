import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class NestedIntegerProblems {
	
	/**
	 * LC 339. Nested List Weight Sum
	 * 用DFS的做法，就是很简单的递归
	 */
	public int depthSumDFS(List<NestedInteger> nestedList, int level) {
        int sum = 0;
        for (NestedInteger node : nestedList) {
            if (node.isInteger()) {
            	sum += node.getInteger() * level;
            } else {
            	sum += depthSumDFS(node.getList(), level + 1);
            }
        }
        return sum;
    }
   
    /**
     * 还能用BFS做
     */
    public int depthSumBFS(List<NestedInteger> nestedList) {
        int level = 1, sum = 0;
        Queue<NestedInteger> queue = new LinkedList<NestedInteger>();
        for (NestedInteger elem : nestedList) {
        	queue.offer(elem);
        }
        while (!queue.isEmpty()) {
        	int size = queue.size();
        	for (int i = 0; i < size; i++) {
        		NestedInteger curr = queue.poll();
        		if (curr.isInteger()) {
        			sum += level * curr.getInteger();
        		} else {
        			for (NestedInteger child : curr.getList()) {
        				queue.offer(child);
        			}
        		}
        	}
        	level++;
        }
        return sum;
    }
    
    /**
     *  364. Nested List Weight Sum II
     * 题目稍微做个变化，和I 反过来，越深的node权重越小，最底层的叶子为1
     * 
     * 简单的做法，就是先一遍DFS，求深度，然后再来一遍求总数。这样是2N的复杂度
     */
    public int depthSumInverseDFS(List<NestedInteger> nestedList) {
    	int h = getDepth(nestedList);
    	int sum = getSum(nestedList, h);
    	return sum;
    }
    
    private int getDepth(List<NestedInteger> list) {
    	if (list == null || list.size() == 0) {
    		return 0;
    	}
    	
    	int max = 0;
    	for (NestedInteger elem : list) {
    		if (elem.isInteger()) max = Math.max(1, max);
    		else max = Math.max(max, getDepth(elem.getList()) + 1);
    	}
    	return max;
    }
    
    private int getSum(List<NestedInteger> list, int depth) {
    	int sum = 0;
    	for (NestedInteger elem : list) {
    		if (elem.isInteger()) {
    			sum += depth * elem.getInteger();
    		} else {
    			sum += getSum(elem.getList(), depth-1);
    		}
    	}
    	return sum;
    }
    
    /**
     * 上面的方法要做两次DFS， 能不能优化呢？答案是可以用BFS来做
     * 使用两个变量 unweighted, weighted， 每做一层循环，循环
     * 内部如果遇到数字，就加到 unweighted里面，循环做完了以后，
     * 说明这一层已经遍历完了，再把unweighted加到weighted里面。
     * 因为unweighted 是不断累加的，相当于层数低的数组被累加了更
     * 多次
     * 
     * 我们看 [1,[4,[6]]]，
     * 第一层  1，  第二层 1+4  第三层 1+4+6， 然后三层的和相加
     * 就是最后的结果
     */
    public int depthSumInverseBFS(List<NestedInteger> nestedList) {
    	int unweighted = 0, weighted = 0;
    	while (!nestedList.isEmpty()) {
    		List<NestedInteger> nextLevel = new ArrayList<NestedInteger>();
    		for (NestedInteger elem : nestedList) {
    			if (elem.isInteger()) { 
    				unweighted += elem.getInteger();
    			} else {
    				nextLevel.addAll(elem.getList());
    			}
    		}
    		weighted += unweighted;
    		nestedList = nextLevel;
    	}
    	return weighted;
    }
    
    /**
     * LC 341. Flatten Nested List Iterator
     * 不要求和了，现在要的是返回所有的数字, 写成一个 iterator
     */
    class NestedIterator implements Iterator<Integer> {
        Stack<NestedInteger> stack;
        
        public NestedIterator(List<NestedInteger> nestedList) {
        	stack = new Stack<NestedInteger>();
        	pushToStack(nestedList);
        }

        @Override
        public Integer next() {
        	if (hasNext()) {
        		return stack.pop().getInteger();
        	} else {
        		return null;
        	}
        }

        @Override
        public boolean hasNext() {
        	while (!stack.isEmpty() && !stack.peek().isInteger()) {
        		pushToStack(stack.pop().getList());
        	}
        	return !stack.isEmpty();
        }

		private void pushToStack(List<NestedInteger> list) {
			Stack<NestedInteger> tmp = new Stack<NestedInteger>();
			for (NestedInteger elem : list) {
				tmp.push(elem);
			}
			while (!tmp.isEmpty()) {
				stack.push(tmp.pop());
			}
		}
		
		@Override
		public void remove() {}
    }

	/**
	 * 面经 Followup： flattern nested interator题目的变体。不是让你实现iterator了，让实现那个接口。
	 * 就是 LC 341的拓展
	 * http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=249899&extra=page%3D1%26filter%3Dsortid%26sortid%3D311%26searchoption%5B3086%5D%5Bvalue%5D%3D7%26searchoption%5B3086%5D%5Btype%5D%3Dradio%26searchoption%5B3088%5D%5Bvalue%5D%3D1%26searchoption%5B3088%5D%5Btype%5D%3Dradio%26searchoption%5B3090%5D%5Bvalue%5D%3D1%26searchoption%5B3090%5D%5Btype%5D%3Dradio%26searchoption%5B3046%5D%5Bvalue%5D%3D4%26searchoption%5B3046%5D%5Btype%5D%3Dradio%26sortid%3D311
	 */
	class NestedIntegerImpl extends NestedInteger {
		boolean isInteger;
		Integer value;
		List<NestedInteger> list;
		
		public NestedIntegerImpl() {
			//depends on what interviewer need
		}

		public boolean isInteger() {
			return isInteger;
		}

		public Integer getInteger() {
			if (isInteger()) {
				return value;
			} else {
				//returen from list
			}
			return value;
		}
		
		public boolean setInteger(Integer i) {
			if (isInteger()) {
				value = i;
			} else {
				
			}
			return true;
		}

		public List<NestedInteger> getList() {
			if (isInteger()) return null;
			return list;
		}
		
		public boolean setList(List<NestedInteger> list) {
			if (isInteger()) return false;
			this.list = list;
			return true;
		}
		
	}
	
	
	/**
	 * 251. Flatten 2D Vector
	 * 还有一题极其类似
	 */
	class Vector2D implements Iterator<Integer> {
	    List<List<Integer>> list;
	    int row = 0, col = 0;
	    Integer next;
	    public Vector2D(List<List<Integer>> vec2d) {
	        list = vec2d;
	    }

	    public Integer next() {
	        return next;
	    }

	    public boolean hasNext() {
	        while (row < list.size()) {
	            if (col < list.get(row).size()) {
	                next = list.get(row).get(col++);
	                return true;
	            } else {
	                row++;
	                col = 0;
	            }
	        }
	        return false;
	    }

		public void remove() {}
	}
	
	
	/**
	 * Flattern Nested List
	 *  1 --> 4  -------> 9 
	 *  |     |
	 *  2     5 -> 6
	 *  |     |    |
	 *  3     7    8
	 */
	public void printFlattern(NestListNode root) {
		if (root == null) 
			return;
		
		while (root != null) {
			System.out.print(root.val + "->");
			printFlattern(root.down);
			root = root.next;
		}
	}
	
	public NestListNode flatternList(NestListNode root) {
		if (root == null) 
			return null;
//		NestListNode curr = root;
//		while (root != null) {
//			NestListNode next = root.next; 
//			root.next = flatternList(root.down);
//			NestListNode tail = root;
//			while (tail.next != null) {
//				tail = tail.next;
//			}
//			tail.next = next;
//			root = next;
//		}
//		return curr;
		
		NestListNode originalRoot = root;
		NestListNode next = root.next; 
		root.next = flatternList(root.down);
		NestListNode tail = root;
		while (tail.next != null) {
			tail = tail.next;
		}
		tail.next = flatternList(next);
		return originalRoot;
	}
	
	NestListNode prev = null;
	public void flattern(NestListNode root) {
		if (root == null) 
			return;
		flattern(root.next);
		flattern(root.down);
		root.next = prev;
		root.down = null;
		prev = root;
	}
	
	static class NestListNode {
		int val;
		NestListNode next = null;
		NestListNode down = null;
		
		public NestListNode(int v) { this.val = v; }
	}
	
	public static void main(String[] args) {
		NestListNode n1 = new NestListNode(1);
		NestListNode n2 = new NestListNode(2);
		NestListNode n3 = new NestListNode(3);
		NestListNode n4 = new NestListNode(4);
		NestListNode n5 = new NestListNode(5);
		NestListNode n6 = new NestListNode(6);
		NestListNode n7 = new NestListNode(7);
		NestListNode n8 = new NestListNode(8);
		NestListNode n9 = new NestListNode(9);
		
		n1.next = n4; n4.next = n9;
		n1.down = n2; n2.down = n3;
		n4.down = n5; n5.down = n7;
		n5.next = n6; n6.down = n8;
		
		NestedIntegerProblems clz = new NestedIntegerProblems();
		clz.printFlattern(n1);
		System.out.println();
//		NestListNode flat = clz.flatternList(n1);
		clz.flattern(n1);
		NestListNode flat = n1;
		while (flat != null) {
			System.out.print(flat.val + "->");
			flat = flat.next;
		}
	}
    
}


