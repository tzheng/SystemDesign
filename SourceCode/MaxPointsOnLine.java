import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaxPointsOnLine {
	
	/**
	 * 149. Max Points on a Line
	 * 乍看一下很简单，但是在leetcode里面是难题，
	 * 对于每个点p出发，计算该点到所有其他点qi的斜率，对每个斜率统计有多少个点符合。其中最多的个数加1（出发点本身）即为最多的共线点。
	 * 
	 * 1. 要考虑斜率无穷大的情况，当x1 = x2，y1!=y2时，为垂直连线。计算斜率时分母为0会出错。
	 * 2. 要考虑多个点坐标相同的情况， 当x1 = x2，y1 = y2时，两点重合。则(x2, y2)和所有(x1, y1)的连线共线。
	 * 
	 * LEETCODE 增加了新的test case， [[0,0],[94911151,94911150],[94911152,94911151]] 这个数据没有办法通过。
	 * 主要是因为使用 double 作为 key，这两个数据算出来结果一致。
	 */
	public int maxPoints(Point[] points) {
        if (points.length <= 2) return points.length;
        
        int result = 0;
        for (int i = 0; i < points.length; i++) {
        	//因为是从每一个点出发，计算其他点到该点的斜率，所以每次map要清空
        	HashMap<Double, Integer> map = new HashMap<>();
        	int samex = 1, samep = 0;
        	for (int j = 0; j < points.length; j++) {
        		if (j == i) continue;
        		if (points[j].x == points[i].x && points[j].y == points[i].y) {
        			samep++;
        		}
        		if (points[j].x == points[i].x) {
        			samex++;
        			continue;  //x都相同，就不要计算斜率了
        		}
        		double slope = (double)(points[j].y - points[i].y) / (double)(points[j].x - points[i].x);
        		if (map.containsKey(slope)) {
        			map.put(slope, map.get(slope) + 1);
        		} else {
        			map.put(slope, 2);
        		}
        		result = Math.max(result, map.get(slope) + samep);
        	}
        	result = Math.max(result, samex); //最后不能忘了计算samex
        }
        return result;
    }
	
	/**
	 * 改进的办法是不用double来做key，用x，y的最大公约数
	 */
	public int maxPointsEnhance(Point[] points) {
		if (points.length <= 2) return points.length;
        
        int result = 0;
        for (int i = 0; i < points.length; i++) {
        	//因为是从每一个点出发，计算其他点到该点的斜率，所以每次map要清空
        	HashMap<String, Integer> map = new HashMap<>();
        	int samex = 1, samep = 0;
        	for (int j = 0; j < points.length; j++) {
        		if (j == i) continue;
        		if (points[j].x == points[i].x && points[j].y == points[i].y) {
        			samep++;
        		}
        		if (points[j].x == points[i].x) {
        			samex++;
        			continue;  //x都相同，就不要计算斜率了
        		}
        		int x = points[j].x - points[i].x, y = points[j].y - points[i].y;
        		int gcd = generateGCD(x, y);
        		String key = String.valueOf(x/gcd) + String.valueOf(y/gcd);
        		if (map.containsKey(key)) {
        			map.put(key, map.get(key) + 1);
        		} else {
        			map.put(key, 2);
        		}
        		result = Math.max(result, map.get(key) + samep);
        	}
        	result = Math.max(result, samex); //最后不能忘了计算samex
        }
        return result;
	}

	private int generateGCD(int a, int b) {
		while (b != 0) {
			int c = a % b;
			a = b;
			b = c;
		}
		return a;
	}
    
	
	public int max(Point[] points) {
		if (points == null || points.length < 2) return 0;
		int max = 0;
		for (int i = 0; i < points.length; i++) {
			Map<String, Integer> map = new HashMap<>();
			int samep  = 0;
			for (int j = 0; j < points.length; j++) {
				if (j == i) continue;
				if (points[j].x == points[i].x && points[j].y == points[i].y) {
					samep++;
					continue;
				}
				String slope = null;
				if (points[j].x == points[i].x) {
					slope = "INF";
				} else {
					slope = getSlope(points[i], points[j]);
				}
				if (map.containsKey(slope)) {
					map.put(slope, map.get(slope) + 1);
				} else {
					map.put(slope, 2);
				}
				max = Math.max(max, map.get(slope) + samep);
			}
			max = Math.max(max, samep+1);
		}
		return max;
	}
	
	private String getSlope(Point a, Point b) {
		int dx = b.x - a.x, dy = b.y - a.y;
		int x = dx, y = dy, z;
		while (y != 0) {
			z = x % y;
			x = y;
			y = z;
		}
		
		return dx/x + "_" + dy/x;
	}
	
	
	public static void main(String[] args) {
	}
}
