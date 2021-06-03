# Shortest Path

##### 787. Cheapest Flights Within K Stops
暴力解法，使用DFS加上剪枝，最坏情况需要每个边都遍历 O(V+E)
```
 public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] cost = new int[n][n];
        for (int[] c : cost) {
            Arrays.fill(c, Integer.MAX_VALUE);
        }
        for (int[] f : flights) {
            cost[f[0]][f[1]] = f[2];
        }
        dfs(src, dst, K, cost, 0);
        return min == Integer.MAX_VALUE ? -1 : min;
    }
    
    int min = Integer.MAX_VALUE;
    private void dfs(int curr, int dst, int k, int[][] cost, int currSum) {
        if (curr == dst) {
            min = Math.min(min, currSum);
        }
        if (k < 0) {
            return;
        }
        for (int i = 0; i < cost.length; i++) {
            if (i == curr || cost[curr][i] == Integer.MAX_VALUE || currSum + cost[curr][i] > min) continue;
            int tmp = cost[curr][i];
            cost[curr][i] = Integer.MAX_VALUE;
            dfs(i, dst, k-1, cost, currSum + tmp);
            cost[curr][i] = tmp;
        }
    }
```
在DFS 上做一些优化，存储搜索结果，时间复杂度 V^2*K， vk是recrusion的复杂度，每个recrursion都有一个循环，把每个v循环一遍
空间复杂度 VK + V^2,  VK 存结果，V^2 存邻接矩阵。
```
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] cost = new int[n][n];
        for (int[] f : flights) {
            cost[f[0]][f[1]] = f[2];
        }
        Integer[][] memo = new Integer[n][K+1];
        int min = dfs(src, dst, K, cost, memo);
        return min == Integer.MAX_VALUE ? -1 : min;
    }
    
    private int dfs(int curr, int dst, int k, int[][] cost, Integer[][] memo) {
        if (curr == dst) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        if (k < 0) {
            return res;
        }
        if (memo[curr][k] != null) {
            return memo[curr][k];
        }
        for (int i = 0; i < cost.length; i++) {
            if (cost[curr][i] == 0) continue;
            int nextVal = dfs(i, dst, k-1, cost, memo);
            if (nextVal != Integer.MAX_VALUE) {
                res = Math.min(res, nextVal + cost[curr][i]);
            }
        }
        memo[curr][k] = res;
        return res;
    }
 ```   
 使用 PriorityQueue找最短路径，Dijstra。
 对于每一步，检查当前节点是否是终点，如果是则返回。如果不是，则找到当前节点连接的其他所有点，把花费加上，同时stops+1，直至 stop > k 为止。
 时间复杂度 v^2*logV - v = num of cities
 ```
 public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] cost = new int[n][n];
        for (int[] f : flights) {
            cost[f[0]][f[1]] = f[2];
        }
        // a[city, cost, stop]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{src, 0, 0});
        while (!pq.isEmpty()) {
            int size = pq.size();
            for (int i = 0; i < size; i++) {
                int[] curr = pq.poll();
                int node = curr[0], ticket = curr[1], stop = curr[2];
                if (node == dst) {
                    return ticket;
                }
                if (stop == K + 1) continue;
                for (int next = 0; next < n; next++) {
                    if (cost[node][next] > 0) {
                        pq.offer(new int[]{next, cost[node][next] + ticket, stop + 1});
                    }
                }
            }
        }
        return -1;
    }
 ```