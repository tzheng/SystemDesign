import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class InsertDeleteGetRandom {
	
	ArrayList<Integer> list;   //store list of keys
    HashMap<Integer, Integer> map;  // map  key -> value;
    HashMap<Integer, Integer> idxMap; // map  key -> key index in list;
    Random random = new Random();
    
    /** Initialize your data structure here. */
    public InsertDeleteGetRandom() {
        list = new ArrayList<Integer>();
        map = new HashMap<Integer, Integer>();
        idxMap = new HashMap<>();
        
    }
    
    public void insert(int key, int value) {
    	if (map.containsKey(key)) {
    		map.put(key, value);
    		return;
    	}
    	
    	map.put(key, value);
    	idxMap.put(key, list.size());
    	list.add(key);
    }
    
    public int deleteRandom() {
    	if (map.size() == 0) return -1;
    	
    	
    	int index = random.nextInt(list.size());
    	int deleteKey = list.get(index);
    	System.out.println("Delete " + deleteKey + " at " + index);
    	delete(deleteKey);
    	
    	return deleteKey;
    }
    
    public void delete(int key) {
    	if (!map.containsKey(key)) 
    		return;
    	
    	int index = idxMap.get(key);
    	System.out.println(Arrays.toString(list.toArray()));
    	if (index != list.size() - 1) {
    		int lastKey = list.get(list.size()-1);
    		list.set(index, lastKey);
    		idxMap.put(lastKey, index);
    	}
    	map.remove(key);
    	list.remove(list.size()-1);
    	System.out.println(Arrays.toString(list.toArray()));
    }
    
    
    public static void main(String[] args) {
    	InsertDeleteGetRandom clz = new InsertDeleteGetRandom();
    	
    	int[] p = new int[4];
    	for (int i = 0; i < 100000; i++) {
    		clz = new InsertDeleteGetRandom();
    		for (int j = 0; j < 4; j++) {
    			clz.insert(j, j*10);
    		}
    		for (int j = 0; j < 4; j++) {
    			p[clz.deleteRandom()]++;
    		}
    		
    	}
    	
    	clz = new InsertDeleteGetRandom();
		for (int j = 0; j < 4; j++) {
			clz.insert(j, j*10);
		}
    	
		clz.delete(3);
    	System.out.println(Arrays.toString(p));
    }
    
}
