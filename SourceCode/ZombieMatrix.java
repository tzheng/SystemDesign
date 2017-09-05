import java.util.LinkedList;
import java.util.Queue;

public class ZombieMatrix {
	/**
     * 
     * seems like binary tree level order 
     * 
     * @param grid  a 2D integer grid
     * @return an integer
     */
    public int zombie(int[][] grid) {
        // Write your code here
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        
        Queue<Point> queue = new LinkedList<Point>();
        int n = grid.length;
        int m = grid[0].length;
        int human = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    human++;
                } else if (grid[i][j] == 1) {
                    queue.offer(new Point(i,j));
                }
                
            }
        }
        
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i<size; i++) {
                Point curr = queue.poll();
                for (int j = 0; j < 4; j++) {
                    Point next_p = new Point(curr.x + dx[j], curr.y + dy[j]);
                    if (!inBound(next_p, grid)) {
                        continue;   
                    }
                    grid[next_p.x][next_p.y] = 1;
                    human--;
                    queue.offer(next_p);
                }
            }
            step++;
        }
        
        if (human == 0) {
            return step;
        }
        
        return -1;
    }
    
    private boolean inBound(Point point, int[][] grid) {
        return point.x >= 0 && point.x < grid.length && point.y >= 0 && point.y < grid[0].length && grid[point.x][point.y] == 0;
    }
    
    public static void main(String[] args) {
    	ZombieMatrix clz = new ZombieMatrix();
    	int[][] grid = {{0,1,2,0,0},{1,0,0,2,1},{0,1,0,0,0}};
    	clz.zombie(grid);
    }
}
