import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrickWalls {
	
	public int leastBricks(List<List<Integer>> wall) {
		if (wall == null || wall.size() == 0) 
            return 0;
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (List<Integer> list : wall) {
            int sum = 0;
            for (int i = 0; i < list.size()-1; i++) {
                sum += list.get(i);
                map.put(sum, map.get(sum) == null?1:map.get(sum) + 1);
                max = Math.max(max, map.get(sum));
            }
        }
        return wall.size() - max;
    }
	
	/**
	 * 如果高度不高，怎么办？ Line Sweep
		那就可以从左到右扫描过去解啦，假设两行，砖头宽度为：. 鍥磋鎴戜滑@1point 3 acres
		8, 8,    4, 7,    5, 3
		4, 8, 4, 2, 5, 4, 4, 3, 1
		维护两个变量row1和row2分别记录两行的当前最大宽度值, 初始化row1=8, row2=4;
		enumerate i->[0, 最右边]
		当i等于4的时候就跨了一块砖头了，更新row2=4+8=12，就这样更新下去。
		时间复杂度是行数*列数大概，m*n, 空间复杂度因为维护了一列的值就是O(n)
	 */
	public void brick(List<List<Integer>> wall) {
		int width = 0;
		for (int b : wall.get(0)) {
			width += b;
		}
		
		Pair[] row = new Pair[wall.size()];
		for (int i = 0; i < wall.size(); i++) {
			row[i] = new Pair(0, wall.get(i).get(0));
		}
		int res = 0;
		for (int i = 0; i < width; i++) {
			int count = 0;
			for (int j = 0; j < row.length; j++) {
				if (i == row[j].val) {
					count++;
					//if (row[j].index + 1 < wall.get(j).size()) {
						row[j].index += 1;
						row[j].val += wall.get(j).get(row[j].index);
				//	}
					
					System.out.println("Row: " + j + " has break at: " + i);
				}
			}
			if (count >= res) {
				System.out.println("at " + i + " line through:" + count);
				res = count;
			}
		} 
	}
	
	class Pair {
		int index;
		int val;
		public Pair(int i, int v) { index = i; val = v;}
	}
	
	public static void main(String[] args) {
		BrickWalls clz = new BrickWalls();
		List<List<Integer>> wall = new ArrayList<>();
//		wall.add(Arrays.asList(1,2,2,1));
//		wall.add(Arrays.asList(3,1,2));
//		wall.add(Arrays.asList(1,3,2));
//		wall.add(Arrays.asList(2,4));
//		wall.add(Arrays.asList(3,1,2));
//		wall.add(Arrays.asList(1,3,1,1));
		
		wall.add(Arrays.asList(8, 8,    4, 7,    5, 3));
		wall.add(Arrays.asList(4, 8, 4, 2, 5, 4, 4, 3, 1));
		clz.brick(wall);
	}
}
