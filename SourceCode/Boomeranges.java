import java.util.HashMap;
import java.util.Map;

public class Boomeranges {
	public int count(int[][] points) {
		int res = 0;
		for (int i = 0; i < points.length; i++) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int j = 0; j < points.length; j++) {
				if (i == j) continue;
				int dist = getDist(points[i], points[j]);
				int count = map.get(dist) == null ? 0 : map.get(dist);
				map.put(dist, count+1);
			}
			for (int count : map.values()) {
				res = Math.max(res, count * (count-1));
			}
		}
		
		
		return res;
	}
	
	private int getDist(int[] a, int[] b) {
		int dx = a[0] - b[0];
		int dy = a[1] - b[1];
		return dx*dx + dy*dy;
	}
}
