import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class LFUCache {

	/**
	 * 要实现PUT, GET 都是 O(1),
	 * 需要有三个map，一个是存数据的。key-value, 一个是存count的，key-count, 最后是每个 count对应的key，
	 * 每次更新，都要对上面三个做出更新。
	 */
	private int min;

	private final int capacity;
	private final HashMap<Integer, Integer> keyToVal;
	private final HashMap<Integer, Integer> keyToCount;
	private final HashMap<Integer, LinkedHashSet<Integer>> countToKeys;

	public LFUCache(int capacity) {
		this.min = -1;
		this.capacity = capacity;
		this.keyToVal = new HashMap<>();
		this.keyToCount = new HashMap<>();
		this.countToKeys = new HashMap<>();
	}

	public int get(int key) {
		if (!keyToVal.containsKey(key))
			return -1;

		int count = keyToCount.get(key);
		countToKeys.get(count).remove(key); // remove key from current count
												// (since we will inc count)
		if (count == min && countToKeys.get(count).size() == 0)
			min = count + 1; // nothing in the current min bucket

		putCount(key, count + 1);
		return keyToVal.get(key);
	}

	public void put(int key, int value) {
		if (capacity <= 0)
			return;

		if (keyToVal.containsKey(key)) {
			keyToVal.put(key, value); // update key's value
			get(key); // update key's count
			return;
		}

		if (keyToVal.size() >= capacity)
			evict(countToKeys.get(min).iterator().next()); // evict LRU from
																// this min
																// count bucket

		min = 1;  //因为新加了一个，min肯定就是1了
		putCount(key, min); // adding new key and count
		keyToVal.put(key, value); // adding new key and value
	}

	private void evict(int key) {
		countToKeys.get(min).remove(key);
		keyToVal.remove(key);
	}

	private void putCount(int key, int count) {
		keyToCount.put(key, count);
		if (!countToKeys.containsKey(count)) {
			countToKeys.put(count, new LinkedHashSet<Integer>());
		}
		countToKeys.get(count).add(key);
	}

	public static void main(String[] args) {
		LFUCache cache = new LFUCache(3);

		cache.put(2, 2);
		cache.put(1, 1);
		System.out.println(cache.get(2));
		System.out.println(cache.get(1));
		System.out.println(cache.get(2));
		cache.put(3, 3); // evicts key 2
		cache.put(4, 4);
		System.out.println(cache.get(3)); // returns -1 (not found)
		System.out.println(cache.get(2)); // returns 3.
		System.out.println(cache.get(1)); // returns 3
		System.out.println(cache.get(4)); // returns 4
	}
}
