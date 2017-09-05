import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StackAndQueue {
	/**
	 * 232. Implement Queue using Stacks
	 * 简单题，用stack来实现queue
	 * 两个 stack来解决，只有out出现空的时候，把in的都移过去
	 */
	class MyQueue {
	    Stack<Integer> in, out;
		public MyQueue() {
	        in = new Stack<Integer>();
	        out = new Stack<Integer>();
	    }
	    
	    public void push(int x) {
	        in.push(x);
	    }
	    
	    public int pop() {
	        peek();
	        return out.pop();
	    }
	    
	    public int peek() {
	    	if (out.empty()) {
	    		while (!in.isEmpty()) {
	    			out.push(in.pop());
	    		}
	    	}
	      
	        return out.peek();
	    }
	    
	    public boolean empty() {
	    	return in.empty() && out.empty();
	    }
	}
	
	/**
	 * 225. Implement Stack using Queues
	 * 反过来, 用queue来实现stack
	 * 方法1： 简单的实现就是用一个linkedList，当插入元素的时候，把新元素放到末尾
	 * 然后把之前的所有元素都取出来，加到新元素后面，这样push的时间复杂度是O(N)，其他O(1)
	 * 
	 * 方法2： 用两个Queue，一个保持为空，插入的时候插入到非空的那个queue，
	 * pop的时候，把非空的queue出了最后一个元素之外的所有元素移到另一个queue，
	 * 剩下的一个就是最新加入的
	 * 这样交替进行，插入是 O(1), 但是其他是 O(N)
	 *
	 */
	class MyStack1 {
		Queue<Integer> q = new LinkedList<Integer>();
	    public void push(int x) {
	        q.add(x);
	        //rotate the queue to make the tail be the head
	        for (int i = 0; i < q.size() - 1; i++) {
	        	q.add(q.poll());
	        }
	    }
	    
	    public int pop() {
	        return q.poll();
	    }
	    
	    public int top() {
	        return q.peek();
	    }
	    
	    public boolean empty() {
	        return q.isEmpty();
	    }
	}
	
	class MyStack2 {
		Queue<Integer> q1 = new LinkedList<Integer>();
		Queue<Integer> q2 = new LinkedList<Integer>();
	    public void push(int x) {
	    	if (!q1.isEmpty()) {
	    		q1.add(x);
	    	} else {
	    		q2.add(x);
	    	}
	    }
	    
	    public int pop() {
	    	int res = -1;
	        if (!q1.isEmpty()) {
	        	int size = q1.size();
	        	for (int i = 0; i < size-1; i++) {
	        		q2.add(q1.poll());
	        	}
	        	res = q1.poll();
	        } else {
	        	int size = q2.size();
	        	for (int i = 0; i < size-1; i++) {
	        		q1.add(q2.poll());
	        	}
	        	res = q2.poll();
	        }
	        return res;
	    }
	    
	    public int top() {
			int res;
			if (q1.isEmpty()) {
				int size = q2.size();
				for (int i = 0; i < size-1; i++) {
					q1.add(q2.poll());
				}
				res = q2.poll();
				q1.add(res);  //和pop()的区别就是要加到新的queue里面
			} else {
				int size = q1.size();
				for (int i = 0; i < size-1; i++) {
					q2.add(q1.poll());
				}
				res = q1.poll();
				q2.add(res);
			}
			return res;
	    }
	    
	    public boolean empty() {
	        return q1.isEmpty() && q2.isEmpty();
	    }
	}
	
	/**
	 * 155. Min Stack
	 * 还是实现一个stack，不同的是要用O(1)时间实现一个
	 * 		getMin() -- Retrieve the minimum element in the stack.
	 */
	class MinStack {
		Stack<Integer> stack = new Stack<Integer>();
		Stack<Integer> min = new Stack<Integer>();
	    public void push(int x) {
	        stack.push(x);
	        if (min.isEmpty() || x <= min.peek()) {
	        	min.push(x);
	        }
	    }
	    
	    public void pop() {
	        int top = stack.pop();
	        if (top == min.peek()) {
	        	min.pop();
	        }
	    }
	    
	    public int top() {
	        return stack.peek();
	    }
	    
	    public int getMin() {
	        return min.peek();
	    }
	}
	
	/**
	 * 更简单的办法，空间变成O(1)
	 * 如果 x 小于 min, 就把当前min push进去，然后更新min
	 */
	class MinStack2 {
		Stack<Integer> stack = new Stack<Integer>();
		int min=Integer.MAX_VALUE;
	    public void push(int x) {
	    	if (x <= min) {
	    		stack.push(min); 
	    		min = x;
	    	}
	        stack.push(x);
	    }
	    
	    public void pop() {
	    	if (stack.peek()==min) { 
	    		stack.pop(); 
	    		min=stack.pop(); 
	    	} else 
	    		stack.pop();
	    }
	    
	    public int top() {
	        return stack.peek();
	    }
	    
	    public int getMin() {
	        return min;
	    }
	}
	
	/**
	 * Min Queue, 跟Min Stack类似， 实现一个Queue， 然后O（1）复杂度获得这个Queue里最小的元素。
	 */
	class MinQueue {
		Queue<Integer> q = new LinkedList<Integer>();
		Deque<Integer> min = new LinkedList<Integer>();
		
	    public void push(int x) {
	    	q.add(x);
	    	while (!min.isEmpty() && min.peekLast() > x) {
	    		min.removeLast();
	    	}
	    	min.add(x);
	    }
	    
	    public void pop() {
	    	int x = q.poll();
	    	if (x == min.peek()) {
	    		min.poll();
	    	}
	    }
	    
	    public int top() {
	    	return q.peek();
	    }
	    
	    public int getMin() {
	    	return min.peek();
	    }
	}
	private void testMinQueue() {
		MinQueue m = new MinQueue();
		m.push(1);
		m.push(2);
		m.push(-1);
		System.out.println(m.getMin());
		m.pop();
		m.pop();
		System.out.println(m.getMin());
		m.push(2);
		m.pop();
		System.out.println(m.getMin());
		m.push(1);
		System.out.println(m.getMin());
	}
	
	
	public static void main(String[] args) {
		StackAndQueue clz = new StackAndQueue();
		
		clz.testMinQueue();
	}
}
