import java.util.HashMap;

public class LRUCache {
	/**
	 * Least Recently Used (LRU) cache. 
	 * 
	 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
	 * 
	 * put(key, value) - Set or insert the value if the key is not already
	 * 			present. When the cache reached its capacity, it should invalidate the
	 * 			least recently used item before inserting a new item.
	 */
	class Node {
        Node prev;
        Node next;
        int val;
        int key;
        public Node(int k, int v) {
        	key = k;
            val = v;
        }
    }
    
    HashMap<Integer, Node> map = new HashMap<>();
    int capacity;
    Node head, tail;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        
        Node res = map.get(key);
        res.prev.next = res.next;
        res.next.prev = res.prev;
        insertNodeToLast(res);
        return res.val;
    }
    
    public void put(int key, int value) {
        if (get(key) != -1) {
            map.get(key).val = value;
            return;
        }
        
        Node newNode = new Node(key, value);
        if (map.size() >= capacity) {
            map.remove(head.next.key);
            head.next = head.next.next;
            head.next.prev = head;
        } 
        insertNodeToLast(newNode);
        map.put(key, newNode);
    }
    
    
    private void insertNodeToLast(Node node) {
        node.prev = tail.prev;
		tail.prev.next = node;
		tail.prev = node;
		node.next = tail;
    }
	
	
}
