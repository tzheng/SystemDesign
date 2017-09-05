import java.util.LinkedList;
import java.util.Queue;



public class NumberOfIslands {
	class Coordinate {
        int x;
        int y;
        public Coordinate(int a, int b) {
            x = a;
            y = b;
        }
    }
    
    int[] dx = {-1, 0, 1,  0};
    int[] dy = {0,  1,  0, -1};
    
    public int numIslands(char[][] grid) {
    	if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    int area = flip(grid, i, j);
                    System.out.println("Area: " + area);
                }
            }
        }
        return count;
    }
    
    private int flip(char[][] grid, int i, int j) {
    	int area = 0;
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{i, j});
        grid[i][j] = '0';
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            area++;
            for (int k = 0; k < 4; k++) {
                int nextx = curr[0] + dx[k], nexty = curr[1] + dy[k];
                if (nextx < 0 || nextx >= grid.length || nexty < 0 || nexty >= grid[0].length 
                    || grid[nextx][nexty] == '0') 
                    continue;
                
                q.offer(new int[]{nextx, nexty});
                grid[nextx][nexty] = '0';
            }
        }
        return area;
    }

    
    /**
     * 后续 Follow Up 
     * Q:如何找湖的数量呢？湖的定义是某个0，其上下左右都是同一个岛屿的陆地。
     * A:我们可以先用Number of island的方法，把每个岛标记成不同的ID，
     * 然后过一遍整个地图的每个点，如果是0的话，就DFS看这块连通水域是否
     * 被同一块岛屿包围，如果出现了不同数字的陆地，则不是湖
     * 
     * @param args
     */
    
    
    public static void main(String[] args) {
    	NumberOfIslands clz = new NumberOfIslands();
    	char[][] grid = new char[4][5];
    	String[] strs = {"1100000",
    					 "1100000",
    					 "0011001",
    					 "0001001"};
    	for (int i = 0; i < strs.length; i++) {
    		grid[i] = strs[i].toCharArray();
    	}
    	
    	clz.numIslands(grid);
    	
//    	System.out.println(clz.numIslandsUF(grid));
    }
    
    
    
    /**
     * UNION FIND 
     */
    int[] id;
    int count;
    
    public int numIslandsUF(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int m = grid.length, n = grid[0].length;
        count = m*n;
        id = new int[count];
        for (int i = 0; i < count; i++) {
            id[i] = i;
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '0') continue;
                for (int k = 0; k < 4; k++) {
                    int x = i + dx[k], y = j + dy[k];
                    if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == '0') 
                        continue;
                    connect(i*n + j, x*n + y);
                }
            }
        }
        
        return count;
    }
    
    private void connect(int p , int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        id[j] = i;
        count--;
    }
    
    private int root(int p ) {
        while (p != id[p]) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }
   
}
