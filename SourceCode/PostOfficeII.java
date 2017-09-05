import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PostOfficeII {
	
	private int calc(int[][] grid, int x, int y, int house) {
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0 , 1, -1};
        int step = 0;
        int sum = 0;
        int v_house = 0;
        
        Coordinate start = new Coordinate(x, y);
        Queue<Coordinate> queue = new LinkedList<Coordinate>();
        int[][] visited = new int[grid.length][grid[0].length];
        
        queue.offer(start);
        visited[x][y] = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            step++;
            for (int i = 0; i < size; i++) {
                Coordinate curr = queue.poll();
                for (int j = 0; j < 4; j++) {
                    Coordinate next_step = new Coordinate(curr.x+dx[j], curr.y+dy[j]);
                    if (!inBound(grid, next_step) 
                        || visited[next_step.x][next_step.y] == 1 ) {
                        continue;
                    }
                    visited[next_step.x][next_step.y] = 1;
                    if (grid[next_step.x][next_step.y] == 1) {
                        v_house++;
                        sum+= step;
                    } else {
                    	queue.offer(next_step);
                    }
                }
            }
        }
        
        if (v_house != house) {
            return -1;
        }
        return sum;
    }
    
	private boolean inBound(int[][] grid, Coordinate c) {
        return c.x >= 0 && c.x < grid.length && c.y >= 0 && c.y < grid[0].length && grid[c.x][c.y] == 0;
    }
    
    class Coordinate {
        int x, y;
        public Coordinate(int a, int b) {
            x = a;
            y = b;
        }
    }
    
    /**
     * @param grid a 2D grid
     * @return an integer
     */
    public int shortestDistance(int[][] grid) {
        // Write your code here
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        
        int house = 0;
        for (int i  = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] ==1) {
                    house++;
                }
            }
        }
        
        
        int sum = -1;
        for (int i  = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 0) {
                    int dist = calc(grid, i, j, house);
                    if (dist == -1) {
                        continue;
                    }
                    if (dist < sum || sum == -1) {
                        sum = dist;
                    }
                }
            }
        }
        
        return sum;
    }
    
    
//	public static void main(String[] args) {
//		PostOfficeII clz = new PostOfficeII();
//    	int[][] grid = {{0,1,0,0,0},{1,0,0,2,1},{0,1,0,0,0}};
//    	int house = 0;
//        for (int i  = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[0].length; j++) {
//                if (grid[i][j] ==1) {
//                    house++;
//                }
//            }
//        }
//    	int sum = clz.calc(grid, 1, 1, house);
////        int sum = clz.shortestDistance(grid);
//    	System.out.println(sum);
//    }
//	

}
	