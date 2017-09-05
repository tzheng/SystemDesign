import java.util.Random;

/**
 * Sorted insert for circular linked list
 *	http://www.geeksforgeeks.org/sorted-insert-for-circular-linked-list/
 */
public class SortedInsertCircularLinkedList {
	
	ListNode head = null;
	
	public void insert(int num) {
		//case 1: list is empty;
		if (head == null) {
			head = new ListNode(num);
			head.next = head;
			return;
		}
		
		//case 2: if value smaller then head, insert to head
		ListNode newNode = new ListNode(num);
		if (head.val >= num) {
			//find last node
			ListNode curr = head;
			while (curr.next != head) {
				curr = curr.next;
			}
			curr.next = newNode;
			newNode.next = head;
			head = newNode;
			return;
		}
		
		//case 3: insert somewhere after ehad
		ListNode curr = head;
		while (curr.next != head && curr.next.val < num) {
			curr = curr.next;
		}
		
		newNode.next = curr.next;
		curr.next = newNode;
	}
	
	public void print() {
		if (head == null) return;
		ListNode node = head;
		while (node != null && node.next != head) {
			System.out.print(node.val + "->");
			node = node.next;
		}
		System.out.println(node.val + " |-->| " + node.next.val);
	}
	
	public static void main(String[] args) {
		SortedInsertCircularLinkedList clz = new SortedInsertCircularLinkedList();
		clz.insert(5);
		clz.print();
		
		clz.insert(6);
		clz.print();
		
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			clz.insert(r.nextInt(20));
			clz.print();
		}
	}
}
