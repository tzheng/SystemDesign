# Path with min max value - 类似最短路径问题

##### 1102. Path With Maximum Minimum Value
```
    // time complexity O(mnlog(mn)) - visited every node once at max. takes up space of maximum O(mn) 
    public int maximumMinimumPath(int[][] A) {
        int m = A.length, n = A[0].length;
         int[][] dirs = {{-1, 0}, {1,0}, {0,-1}, {0,1}};
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            // {x, y, minValue}
            return b[2] - a[2];
        });
        
        boolean[][] visited = new boolean[m][n];
        pq.offer(new int[]{0, 0, A[0][0]});
        visited[0][0] = true;
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], val = curr[2];
            if (x == m-1 && y == n-1) {
                return val;
            } 
            
            for (int[] d : dirs) {
                int dx = x + d[0], dy = y + d[1];
                if (dx < 0 || dx >= m || dy < 0 || dy >= n || visited[dx][dy]) continue;
                visited[dx][dy] = true;
                pq.offer(new int[]{dx, dy, Math.min(val, A[dx][dy])});
            }
        }
        return -1;
    }
```    

##### 1631. Path With Minimum Effort
```
 // almost identical to 1102
 public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, 0});
        boolean[][] visited = new boolean[m][n];
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], diff = curr[2];
            visited[x][y] = true;
            if (x == m -1 && y == n -1) {
                return diff;
            }
            for (int[] d : dirs) {
                int dx = x + d[0], dy = y + d[1];
                if (dx < 0 || dx >= m || dy < 0 || dy >= n || visited[dx][dy]) continue;
                pq.offer(new int[]{dx, dy, Math.max(diff, Math.abs(heights[dx][dy] - heights[x][y]))});    
            }
        }
        return -1;
    }
 ```   