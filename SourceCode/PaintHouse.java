
public class PaintHouse {
	/**
	 * 276. Paint Fence
	 * k 种颜色，相邻的两个不能同色
	 * We divided it into two cases.
	 * the last two fence have the same color, the number of ways to paint in this case is sameColorCounts.
	 * the last two fence have different colors, and the number of ways in this case is diffColorCounts.
	 * 
	 * 要注意的就是每次计算， 当前的结果应该等于sameColor和differentColor的和，
	 * 而differentColor只能在k - 1种color里选，等于之前结果 * (k - 1)， 
	 * sameColor等于之前的differentColor，之后进行下一次计算。
	 */
	public int numWays(int n, int k) {
		if (n == 0) return 0;
        if (n == 1) return k;
        
        //for the second one, paint same color as first , k choices
        //paint different color,  has k-1 choices, so in total is  k + k*k-1
        int same = k, diff = k * k-1;
        for (int i = 2; i < n; i++) {
        	int tmp = (k-1) * (same + diff);
        	same = diff;
        	diff = tmp;
        }
        return same + diff;
	}
	
	/**
	 * 256. Paint House
	 * 三种颜色可以选，每个颜色有自己的花费，求花费最小。
	 * f[i][j] 是 第i个房子，涂j颜色的花费。 
	 *   f[i][0] = min(f[i-1][1], f[i-1][2]) + costs[i-1][0] ...
	 * 这里我们发现由于是从前往后推，实际上可以重复利用 cost数组。
	 */
	public int minCost(int[][] costs) {
        if(costs==null||costs.length==0){
            return 0;
        }
        int n = costs.length;
        // if color 0, then f[i][0] = min(f[i-1][1], f[i-1][2])
        // if color 1, then f[i][1] = min(f[i-1][0], f[i-1][2])
        //if color 2, then f[i][2] = min(f[i-1][0], f[i-1][1])
        for (int i = 1; i < n; i++) {
            costs[i][0] = Math.min(costs[i-1][1], costs[i-1][2]) + costs[i][0];
            costs[i][1] = Math.min(costs[i-1][0], costs[i-1][2]) + costs[i][1];
            costs[i][2] = Math.min(costs[i-1][0], costs[i-1][1]) + costs[i][2];
        }
        return Math.min(costs[n-1][0], Math.min(costs[n-1][1], costs[n-1][2]));
    }
	
	/**
	 * 265. Paint House II
	 * 现在有k个颜色了，不是3个， 其实逻辑都一样啊，只不过是换成循环
	 * 在getMin这里可以优化，不一定都要做循环，可以存prevMin，prevSecondMin，这样复杂度
	 * 变成 O(nk)
	 */
	public int minCostII(int[][] costs) {
        if(costs==null||costs.length==0){
            return 0;
        }
        int n = costs.length;
        int k = costs[0].length;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
            	costs[i][j] = getMin(costs[i-1], j) + costs[i][j];
            }
        }
        return getMin(costs[n-1], -1);
    }
	
	public int getMin(int[] cost, int j) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < cost.length; i++) {
            if (i == j) continue;
            min = Math.min(cost[i], min);
        }
        return min;
    }
	
	
	public int minCostIIOpt(int[][] costs) {
	    if(costs == null || costs.length == 0 || costs[0].length == 0) return 0;
	    
	    int n = costs.length, k = costs[0].length;
	    if(k == 1) return (n==1? costs[0][0] : -1);
	    
	    int prevMin = 0, prevMinInd = -1, prevSecMin = 0;//prevSecMin always >= prevMin
	    for(int i = 0; i<n; i++) {
	        int min = Integer.MAX_VALUE, minInd = -1, secMin = Integer.MAX_VALUE;
	        for(int j = 0; j<k;  j++) {
	            int val = costs[i][j] + (j == prevMinInd? prevSecMin : prevMin);
	            if(val < min) {//when val < min, 
	                secMin = min;
	                min = val;
	                minInd = j;
	            } else if(val < secMin) { //when min<=val< secMin
	                secMin = val;
	            }
	        }
	        prevMin = min;
	        prevMinInd = minInd;
	        prevSecMin = secMin;
	    }
	    return prevMin;
	}
	
	
}
