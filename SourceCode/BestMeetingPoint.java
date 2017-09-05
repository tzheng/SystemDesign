import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BestMeetingPoint {
	/**
	 * 296. Best Meeting Point
	 * 思路就是把每个是 1 的坐标记下来，时间 O(MN), 空间O(MN)，存为homes
	 * 然后遍历矩阵，对于每个位置，算他们到 homes的距离。时间 O((MN)^2)
	 * 效率比较低
	 */
	public int minTotalDistance(int[][] grid) {
		if (grid == null || grid.length == 0 || grid[0].length == 0) {
			return 0;
		}

		ArrayList<int[]> homes = new ArrayList<int[]>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1) {
					homes.add(new int[] { i, j });
				}
			}
		}
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int distance = calcDistance(i, j, homes, min);
				min = Math.min(min, distance);
			}
		}

		return min;
	}

	private int calcDistance(int x, int y, ArrayList<int[]> homes, int min) {
		int sum = 0;
		for (int[] home : homes) {
			sum += Math.abs(home[0] - x) + Math.abs(home[1] - y);
			if (sum > min) {
				return Integer.MAX_VALUE;
			}
		}
		return sum;
	}
	
	/**
	 * 考虑到距离计算，实际上是算x的差值和y的差值之和，这样x，y其实可以分开计算，互不相干。
	 * 要找到最合适的点，那么他肯定要是中位数。所以对x，y分开来求中位数就好。
	 */
	public int minTotalDistance1(int[][] grid) {
		int m = grid.length, n = grid[0].length;
		List<Integer> x = new ArrayList<>();
		List<Integer> y = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == 1) {
					x.add(i);
					y.add(j);
				}
			}
		}
		return getMin(x) + getMin(y);
	}
	
	private int getMin(List<Integer> list) {
		int sum = 0;
		Collections.sort(list);
		int i = 0, j = list.size() - 1;
		while (i < j) {
			sum += list.get(j--) - list.get(i++);
		}
		return sum;
	}
	        
}
