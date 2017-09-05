
public class LinkedListProblems {
	/**
	 * 206. Reverse Linked List
	 * 两种办法 recursive， iterative
	 */
	public ListNode reverseList(ListNode head) {
		ListNode prev = null;
		while (head != null) {
			ListNode next = head.next;
			head.next = prev;
			prev = head;
			head = next;
		}
		return prev;
	}
	
	public ListNode reverseListRec(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode next = head.next;
		ListNode newHead = reverseListRec(next);
		next.next = head;
		head.next = null;
		return newHead;
	}
	
	/**
	 * 92. Reverse Linked List II
	 * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
	 * return 1->4->3->2->5->NULL.
	 * 
	 * 两种办法 recursive， iterative
	 */
	public ListNode reverseBetweenRec(ListNode head, int m, int n) {
		return reverseHelper(head, 1, m, n);
	}
	
	private ListNode reverseHelper(ListNode head, int count, int m, int n) {
		if (head == null) {
			return head;
		}
		
		ListNode next = head.next;
		ListNode newHead = reverseHelper(next, count+1, m, n);
		if (count >= m && count < n) {  //注意边界，m <= count < n  
			head.next = next.next;
			next.next = head;
			return newHead;
		} else {
			head.next = newHead;
			return head;
		}
	}
	/**
	 * iterative 
	 */
	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode prev = dummy;
		for (int i = 0; i < m-1; i++) 
			prev = prev.next;
		
		ListNode start = prev.next;
		ListNode next = start.next;
		for (int i = m; i < n; i++) {
			start.next = next.next;
			next.next = prev.next;
			prev.next = next;
			next = start.next;
		}
		
		return dummy.next;
	}
	
	
	
	/**
	 * 234. Palindrome Linked List
	 * 如果要O(n) 的空间，那么直接用stack和2pointer就好了。
	 * 如果要 O(1) space.  那么就在快慢指针往后走的时候，同时把前半部分reverse了
	 */

	/**
	 * 86. Partition List
	 */
	public ListNode partition(ListNode head, int x) {
		ListNode dummys = new ListNode(-1);
        ListNode dummyg = new ListNode(-1);
        
        ListNode head1 = dummys, head2 = dummyg;
        while (head != null) {
        	ListNode next = head.next;
        	head.next = null;
            if (head.val >= x) {
                head2.next = head;
                head2 = head2.next;
            } else {
                head1.next = head;
                head1 = head1.next;
            }
            head = next;
        }
        
        head2.next = null; //important!!, avoid cycle linked list
        head1.next = dummyg.next;
        return dummys.next;
    }
	
	/**
	 * 61. Rotate List
	 * Given a list, rotate the list to the right by k places, where k is non-negative.
	 * For example:
	 * Given 1->2->3->4->5->NULL and k = 2,
	 * return 4->5->1->2->3->NULL.
	 * 
	 * Ex: {1,2,3} k=2 Move the list after the 1st node to the front
	 * Ex: {1,2,3} k=5, In this case Move the list after (3-5%3=1)st node to the front.
	 */
	public ListNode rotateRight(ListNode head, int k) {
		if (head == null || head.next == null)
			return head;
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode fast = dummy, slow = dummy;

		int i;
		for (i = 0; fast.next != null; i++)// Get the total length
			fast = fast.next;

		for (int j = i - k % i; j > 0; j--) // Get the i-n%i th node
			slow = slow.next;

		fast.next = dummy.next; // Do the rotation
		dummy.next = slow.next;
		slow.next = null;

		return dummy.next;
	}
	
	/**
	 *  143. Reorder List
	 */
	public void reorderList(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        if (fast != null) {
            slow = slow.next;
        }
        
        
        fast = head;
        slow = reverse(slow);
        while (slow != null) {
            ListNode nextSlow = slow.next;
            slow.next = fast.next;
            fast.next = slow;
            fast = slow.next;
            slow = nextSlow;
        }
        //这个很重要，奇数的时候把环断开
        if (fast != null) {
        	fast.next = null;
        }
    }
	
	private ListNode reverse(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
	
	
	/**
	 *  82. Remove Duplicates from Sorted List II
	 */
	public ListNode deleteDuplicates(ListNode head) {
		ListNode dummy, node;
        dummy = node = new ListNode(-1);
        
        ListNode prev = head, runner = head.next;
        int count = 1;
        while (runner != null) {
            if (runner.val != prev.val) {
                if (count == 1) {
                    node.next = prev;
                    node = node.next;
                    node.next = null;
                }
                prev = runner;
                count = 1;
            } else {
                count++;
            }
            runner = runner.next;
        }
        
        if (count == 1) {
            node.next = prev;
        }
        
        while (dummy != null) {
        	System.out.print(dummy.val + "->");
        	dummy = dummy.next;
        }
        
        return dummy.next;
	}
	
	public static void main(String[] args) {
		ListNode root = new ListNode(1);
		root.next = new ListNode(2);
		root.next.next = new ListNode(3);
		root.next.next.next = new ListNode(4);
//		root.next.next.next.next = new ListNode(5);
		
		LinkedListProblems clz = new LinkedListProblems();
//		ListNode after = clz.reverseBetween(root, 2, 4);
//		ListNode after = clz.rotateRight(root, 2);
//		ListNode after = root;
//		clz.reorderList(after);
//		while (after != null) {
//			System.out.print(after.val + "->");
//			after = after.next;
//		}
		
		
		ListNode n1 = new ListNode(1);
		n1.next = new ListNode(2);
		n1.next.next = new ListNode(2);
		clz.deleteDuplicates(n1);
	}
}
