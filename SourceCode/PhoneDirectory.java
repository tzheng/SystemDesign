import java.util.HashMap;

public class PhoneDirectory {
	HashMap<Integer, Integer> map;
    int size;
    int max;
    
    /** Initialize your data structure here
        @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory(int maxNumbers) {
        max = maxNumbers;
        size = 0;
        map = new HashMap<>();
    }
    
    /** Provide a number which is not assigned to anyone.
        @return - Return an available number. Return -1 if none is available. */
    public int get() {
        
        if (size == max) {
            return -1;
        }
        
        int newNumber = 0;
        for (int i = 0; i < max; i++) {
            if (map.containsKey(i)) {
                continue;
            }
            newNumber = i;
            break;
        }
        size++;
        map.put(newNumber, newNumber);
        return newNumber;
    }
    
    /** Check if a number is available or not. */
    public boolean check(int number) {
        return !map.containsKey(number);
    }
    
    /** Recycle or release a number. */
    public void release(int number) {
        map.remove(number);
        size--;
    }
    
    public static void main(String[] args) {
    	PhoneDirectory clz = new PhoneDirectory(3);
    	System.out.println(clz.get());
    	System.out.println(clz.get());
    	System.out.println(clz.check(2));
    	System.out.println(clz.get());
    }
}
