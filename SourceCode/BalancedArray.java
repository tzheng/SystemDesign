import java.util.HashMap;
import java.util.Map.Entry;

public class BalancedArray {
	//四等分 {2，5，1，1，1，1，4，7，5，2，1，7}可以四等分成{2,5} {1,1,1,4} {5,2} {7}
    public static boolean resolve(int[] A) {
    	if (A.length < 7)
    		return false;
    	HashMap<Integer, Integer> map = generateMap(A);
    	for (Entry<Integer, Integer> e : map.entrySet()) {
    		System.out.print(e.getKey() + ": ");
    		System.out.println(e.getValue());
    	}
    	int i = 1, j = A.length - 2;
    	int sum1 = A[0], sum2 = A[A.length - 1];
    	while (i < j - 3) {
    		if (sum1 < sum2) 
    			sum1 += A[i++];
    		else if (sum1 > sum2)
    			sum2 += A[j--];
    		else {
				int target = sum1 * 2 + A[i];
				if (map.containsKey(target) && map.get(target) == (sum2 * 2 + A[j])) {
					System.out.println("i: " + i);
					System.out.println("j: " + j);
					return true;
				}
				sum1 += A[i++];
				sum2 += A[j--];
    		}
    	}
    	return false;
    }
    
    //precond: A.length >= 7
    private static HashMap<Integer, Integer> generateMap(int[] A) {
    	int sum1 = A[0], sum2 = 0;
    	for (int i = 2; i < A.length; i++) {
    		sum2 += A[i];
    	}
    	HashMap<Integer, Integer> map = new HashMap<>();
    	for (int i = 1; i < A.length - 1; i++) {
    		map.put(sum1, sum2);
    		sum1 += A[i];
    		sum2 -= A[i + 1];
    	}
    	return map;
    }
    //2,5,1,1,1,1,4,7,5,2,1,7
    //3, 3, 5, 2, 1, 1, 1, 1, 3
    //1,7,4,2,6,5,4,2,2,9,8
	public static void main(String[] args) {
		int[] A = new int[]{2,5,1,1,1,1,4,7,5,2,1,7};
		
		//Solution driver = new Solution();
		System.out.println(resolve(A));
	}
}
