
public class Rehashing {

	
	 public ListNode[] rehashing(ListNode[] hashTable) {
	        // write your code here
	        int size = hashTable.length;
	        int capacity = size * 2;
	        
	        ListNode[] hash = new ListNode[capacity];
	        
	        for (ListNode elem : hashTable) {
	            ListNode tmp = elem;
	            while (tmp != null) {
	                ListNode newNode = new ListNode(tmp.val);
	                
	                int hashCode = hashcode(tmp.val, capacity);
	                if (hash[hashCode] == null) {
	                    hash[hashCode] = newNode;
	                } else {
	                    ListNode prev = tmp;
	                    while (prev.next != null) {
	                        prev = prev.next;
	                    }
	                    prev.next = newNode;
	                }
	                tmp = tmp.next;
	            }
	        }
	        
	        return hash;
	        
	    }
	 
	 public int hashcode(int key, int capacity) {
	        return key % capacity;
	    }
	 
	 public static void main(String[] args) {
		 Rehashing clz = new Rehashing();
		 ListNode[] list = new ListNode[3];
		 list[0] = null;
		 list[1] = null;
		 ListNode t29 = new ListNode(29);
		 t29.next = new ListNode(5);
		 list[2] = t29;
		 
		 clz.rehashing(list);

	 }
}
