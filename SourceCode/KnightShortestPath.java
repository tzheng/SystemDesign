import java.util.LinkedList;
import java.util.Queue;

public class KnightShortestPath {
    /**
     * @param grid a chessboard included 0 (false) and 1 (true)
     * @param source, destination a point
     * @return the shortest path 
     */
    public int shortestPath(int[][] grid, Point source, Point destination) {
        // Write your code here
        int[] dx = {-1, -2, -2, -1, 1, 2,  2,  1};
        int[] dy = {-2, -1,  1,  2, 2, 1, -1, -2};
        
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        
        int count = 0;
        Queue<Point> queue = new LinkedList<Point>();
        queue.offer(source);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int j = 0; j < size; j++ ) {
                Point curr = queue.poll();
                if (destination.x == curr.x && destination.y == curr.y) {
                   return count;
                }
                for (int i = 0; i < 8; i++) {
                    int x = curr.x + dx[i];
                    int y = curr.y + dy[i];
                    if (valid(x, y, grid)) {
                        queue.offer(new Point(x, y));
                         // mark the point not accessible
                        grid[x][y] = 1;
                    }
                }
            }
            count++;
        }
        
        return -1;
    }
    
    private boolean valid(int x, int y, int[][] grid) {
        return x >=0 && x< grid.length && y >=0 && y < grid[0].length
                && grid[x][y] == 0;
    }
    
    public static void main(String[] args) {
    	KnightShortestPath clz = new KnightShortestPath();
    	int[][] grid = {{0,0,0},{0,0,0},{0,0,0}};
    	System.out.println(clz.shortestPath(grid, 
    										new Point(2,0), 
    										new Point(1,1)));
    }
    
}