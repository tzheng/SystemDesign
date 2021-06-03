##### 146. LRU Cache
经典的双链表 + 哈希表，所有操作复杂度都是 O(1)
```
class LRUCache {
    Node head, tail;
    int capacity;
    HashMap<Integer, Node> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        
        Node node = map.get(key);
        // move the node to tail, 
        removeNode(node);
        moveToTail(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            // remove node from LinkedList
            node.value = value;
            removeNode(node);
        } else {
            node = new Node(key, value);
        }
        map.put(key, node);
        // put node to the tail
        moveToTail(node);
        // check the size of map
        if (map.size() > capacity) {
            // remove head.next 
            Node tobeRemove = head.next;
            map.remove(tobeRemove.key);
            removeNode(tobeRemove);
        }
    }
    
    private void removeNode(Node node) {
        Node prev = node.prev, next = node.next;
        prev.next = next;
        next.prev = prev;
        node.prev = null;
        node.next = null;
    }
    
    private void moveToTail(Node node) {
        Node last = tail.prev;
        last.next = node;
        node.prev = last;
        tail.prev = node;
        node.next = tail;
    }
    
    class Node {
        int key, value;
        Node prev, next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            prev = null;
            next = null;
        }
    }
}
```

##### 460. LFU Cache
双链表 + 双哈希，一个哈希存key-value，一个哈希存频率 + 双链表
```
class LFUCache {

    int capacity, size, min;
    Map<Integer, DList> countMap;
    Map<Integer, Node> map;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        size = 0; min = 0;
        countMap = new HashMap<>();
        map = new HashMap<>();
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        incrementCount(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;
        Node node = map.get(key);
        if (node != null) {
            node.val = value;
            incrementCount(node);
            return;
        }
        
        if (size == capacity) {
            evit();
            size--;
        }
        
        node = new Node(key, value);
        map.put(key, node);
        node.count = 1;
        min = 1;
        countMap.putIfAbsent(node.count, new DList());
        DList newList = countMap.get(node.count);
        newList.addToTail(node);
        size++;
        
    }
    
    private void evit() {
        DList list = countMap.get(min);
        Node tobeRemoved = list.removeFirst();
        if (list.isEmpty()) {
            countMap.remove(min);
        }
        map.remove(tobeRemoved.key);
    }
    
    private void incrementCount(Node node) {
        DList original = countMap.get(node.count);
        // remove node from DList
        original.remove(node);
        // remove Dlist if Dlist is empty 
        if (original.isEmpty()) {
            countMap.remove(node.count);
             // update min
            if (min == node.count) {
               min++;
            }
        }
       
        node.count += 1;
        countMap.putIfAbsent(node.count, new DList());
        DList newList = countMap.get(node.count);
        // add node to tail of the list;
        newList.addToTail(node);
    }
    
    class Node {
        int key, val, count;
        Node prev, next;
        
        public Node(int k, int v) {
            key = k;  val = v;
            prev = next = null;
            count = 0;
        }
    }
    
    class DList {
        Node head, tail;
        
        public DList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }
        
        public void remove(Node node) {
            Node prev = node.prev, next = node.next;
            prev.next = next;
            next.prev = prev;
            node.prev = null;
            node.next = null;
        }
        
        public void addToTail(Node node) {
            Node prev = tail.prev;
            prev.next = node;
            node.prev = prev;
            tail.prev = node;
            node.next = tail;
        }
        
        public Node removeFirst() {
            Node first = head.next;
            head.next = first.next;
            first.next.prev = head;
            first.prev = first.next = null;
            return first;
        }
        
        public boolean isEmpty() {
            return head.next == tail;
        }
    }
}
```