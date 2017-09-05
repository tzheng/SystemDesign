import java.util.HashMap;
import java.util.Map;

public class FrogJump {
	public boolean canCross(int[] stones) {
        if (stones.length == 0) {
        	return true;
        }
        Map<String, Boolean> map = new HashMap<>();
        return helper(stones, 0, 0, map);
    }
	
	private boolean helper(int[] stones, int pos, int step, Map<String, Boolean> map) {
		int n = stones.length;
		if (pos >= n-1) 
			return true;
		
		String key = pos + "_" + step;
		if (map.containsKey(key)) {
			return map.get(key);
		}
		
		boolean flag = false;
		for (int i = pos + 1; i < n && !flag; i++) {
			int dist = stones[i] - stones[pos];
			if (dist < step -1) continue;
			if (dist > step + 1) break;
			flag = helper(stones, i, dist, map); 
		}
		map.put(key, flag);
		return flag;
	}
	
	
	
	public static void main(String[] args) {
		
		FrogJump clz = new FrogJump();
		
		int[] stones = {0,1,3,5,6,8,12,17};
		System.out.println(clz.canCross(stones));
		
		int[] s2 = {0,1,2,3,4,8,9,11};
		System.out.println(clz.canCross(s2));
	}
}
