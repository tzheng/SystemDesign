
public class BuyAndSellStock {

	/**
	 * 121. Best Time to Buy and Sell Stock 先看初级版，初级版就是循环，不断更新min
	 * price，然后看当前价格和min price的差。 注意：不能保存max price，因为max
	 * price很可能出现在min之前，所以只能用当前价格来
	 */
	public int maxProfit(int[] prices) {
		int minp = Integer.MAX_VALUE, profit = 0;
		for (int p : prices) {
			minp = Math.min(minp, p);
			profit = Math.max(profit, p - minp);
		}
		return profit;
	}

	/**
	 * 122. Best Time to Buy and Sell Stock II Design an algorithm to find the
	 * maximum profit. You may complete as many transactions as you like
	 * 不限交易次数的话，我们可以画一个股票走势图，然后发现，每当价格出现下降的时候，就卖出再买入，这样利润是最大的。
	 */
	public int maxProfit2(int[] prices) {
		int profit = 0;
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] > prices[i - 1]) {
				profit += prices[i] - prices[i - 1];
			} else {
				// do nothing, price decease, means sell.
			}
		}
		return profit;
		/**
		 *  while i <n:
            money = prices[i]
            while i+1 < n and prices[i] <= prices[i+1]:
                i+=1
	            ans+=prices[i]-money
	            i+=1
	        return ans
		 */
	}

	/**
	 * 123. Best Time to Buy and Sell Stock III
	 * 只能最多完成两次交易，找利润最大。思路就是先算从左到右，对于第i个数，0-i的范围内的最大利润是多少，记为left[i] 然后从右到左，计算
	 * i-n 的范围内最大利润是多少，记为right[i]。
	 * 有了这两个数组以后，问题就转为，找一个分界点 分界点左边利润 left[i] + 右边利润right[i]最大。
	 * 				 7 1 5 3 6 4 
	 * 		   left  0 0 4 4 5 5
     *         right 5 5 3 3 0 0 max = 4+3
	 */
	public int maxProfit3(int[] prices) {
		if (prices == null || prices.length == 0) return 0;
        int n = prices.length, profit = 0;
        int[] left = new int[n], right = new int[n];
        
        int min = prices[0];
        for (int i = 1; i < n; i++) {
            min = Math.min(min, prices[i]);
            profit = Math.max(profit, prices[i] - min);
            left[i] = profit;
        }
        
        int max = prices[n-1];
        profit = 0;
        int res = 0;
        for (int i = n-2; i >= 0; i--) {
            max = Math.max(max, prices[i]);
            profit = Math.max(profit, max - prices[i]);
            right[i] = profit;
            res = Math.max(res, left[i] + right[i]);
        }
        
        return res;
        /**
         * O(1) SPACE solution
         * 
         * int sell1 = 0, sell2 = 0, buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
			for (int i = 0; i < prices.length; i++) {
				buy1 = Math.max(buy1, -prices[i]);
				sell1 = Math.max(sell1, buy1 + prices[i]);
				buy2 = Math.max(buy2, sell1 - prices[i]);
				sell2 = Math.max(sell2, buy2 + prices[i]);
			}
			return sell2;
         */
	}

	/**
	 * 188. Best Time to Buy and Sell Stock IV
	 * 再进阶，如果能完成至多 K 次交易
	 * 	1. 如果k > n/2，那么问题就转化为 2, as many transactions as possbile.
	 *  2. 否则需要动态规划来解决
	 *  我们定义local[i][j]为在到达第i天时最多可进行j次交易并且最后一次交易在最后一天卖出的最大利润，此为局部最优
	 *  	local[i][j] = max(local[i-1][j-1] + max(diff, 0),  local[i-1][j] + diff)  
	 *  		
	 *  			local[i-1][j-1] + max(diff, 0) 第一个是全局到i-1天进行j-1次交易，然后加上今天的交易，如果今天是赚钱的话（也就是前面只要j-1次交易，最后一次交易取当前天）
	 *  			local[i-1][j] + diff 第二个量则是取local第i-1天j次交易，然后加上今天的差值（这里因为local[i-1][j]比如包含第i-1天卖出的交易，
	 *  								 所以现在变成第i天卖出，并不会增加交易次数，而且这里无论diff是不是大于0都一定要加上，因为否则就不满足local[i][j]必须在最后一天卖出的条件了）
	 *  
	 *  然后我们定义global[i][j]为在到达第i天时最多可进行j次交易的最大利润，此为全局最优。它们的递推式为：
	 * 		global[i][j] = (local[i][j], global[i-1][j])
	 *  也就是取当前局部最好的，和过往全局最好的中大的那个（因为最后一次交易如果包含当前天一定在局部最好的里面，否则一定在过往全局最优的里面）
	 */
	public int maxProfit4(int k, int[] prices) {
		if (k > prices.length / 2) {
			return maxProfit2(prices);
		}
		
		int days = prices.length;
        int[][] local = new int[days][k+1];
        int[][] global = new int[days][k+1];
        
        for (int i = 1; i < days; i++) {
            int diff = prices[i] - prices[i-1]; //sell day i, buy in day i-1
            for (int j = 1; j <= k; j++) {
                local[i][j] = Math.max(global[i-1][j-1] + Math.max(0, diff), local[i-1][j] + diff);
                global[i][j] = Math.max(local[i][j], global[i-1][j]);
            }
        }
        
        return global[days-1][k];
	}
	
	/**
	 * 上面用global 和 local的方式不太好理解，可以看一个更加直接的动态转移方程
	 * https://discuss.leetcode.com/topic/24079/easy-understanding-and-can-be-easily-modified-to-different-situations-java-solution
	 * 
	 * The basic idea is to create two tables. hold and unhold.
	 * hold[i][j] means the maximum profit with at most j transaction for 0 to i-th day. hold means you have a stock in your hand.
	 * unhold[i][j] means the maximum profit with at most j transaction for 0 to i-th day. unhold means you don't have a stock in your hand.
	 * 
	 * The equation is
	 * 		hold[i][j] = Math.max(unhold[i-1][j]-prices[i],hold[i-1][j]);
	 * 		unhold[i][j] = Math.max(hold[i-1][j-1]+prices[i],unhold[i-1][j]);
	 * 
	 * when you sell your stock this is a transaction but when you buy a stock, it is not considered as a full transaction. 
	 * so this is why the two equation look a little different.
	 * 
	 * And we have to initiate hold table when k = 0.
	 * 
	 * When the situation is you can not buy a new stock at the same day when you sell it. 
	 * For example you can only buy a new stock after one day you sell it. The same idea. 
	 * 
	 * Another situation is when you have to pay a transaction fee for each transaction, just make a modification when you sell it, 
	 * So just change the unhold equation a little
	 */
	public int maxProfit4General(int k, int[] prices) {
		if (k > prices.length / 2) {
			return maxProfit2(prices);
		}
		
		int[][] hold = new int[prices.length][k+1], unhold = new int[prices.length][k+1];
		hold[0][0] = -prices[0];
		for (int i = 1; i < prices.length; i++) 
			hold[i][0] = Math.max(hold[i-1][0], -prices[i]);
		for (int j = 1; j <= k; j++) 
			hold[0][j] = -prices[0];
		
		for (int i = 1; i < prices.length; i++) {
			for (int j = 1; j <= k; j++) {
				//如果持有有两种情况，第一种是我前一天还没持有，但是今天买了，那之前获得的利润unhold要减去prices[i]，因为花钱了
				// 2 是我之前就买了股票，继续持有
				hold[i][j] = Math.max(unhold[i-1][j] - prices[i], hold[i-1][j]);
				//不持有有两种情况，1是我前一天还持有，今天卖了，那么就要加上今天的价格作为利润。 2是我前一天没有持有，我保持不持有状态。
				unhold[i][j] = Math.max(hold[i-1][j-1] + prices[i], unhold[i-1][j]);
				//unhold[i][j] = Math.max(hold[i-1][j-1] + prices[i] - transactionFee, unhold[i-1][j]);
			}
		}
		//最后你不能持有，所以 Unhold的就是最大的。
		return unhold[prices.length-1][k];
		
		/**
		 * 进一步简化可以变成这样
		 * for (int i = 0; i <= k; i++) {
	            buyN[i] = Integer.MIN_VALUE;
	        }
	        for (int i = 0; i < prices.length; i++) {
	            for (int j = 1; j <= k; j++) {
	                buyN[j] = Math.max(buyN[j], soldN[j - 1] - prices[i]);
	                soldN[j] = Math.max(soldN[j], buyN[j] + prices[i]);
	            }
	        }
	        return soldN[k];
		 */
	}
	
	/**
	 * 309. Best Time to Buy and Sell Stock with Cooldown
	 * 
	 * buy sell ii 的升级版，不限次数，但是需要cooldown 一天
	 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
	 * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
	 * 
	 * 还是动态规划，
	 *   hold[i] 表示 第i天持有股票，那么要么前两天卖了，中间cooldown一天，要么一直持有
	 *   	hold[i] = max(unhold[i-2] - prices[i],  hold[i-1])
	 *   
	 *   unhold[i] 表示第i天不持有股票，要么原来持有但是第i天卖了， 要么一直不持有
	 *   	unhold[i] = max(hold[i-1] + prices[i], unhold[i-1])
	 *   
	 *   Let b1, b0 represent hold[i - 1], hold[i]
	 *   Let s2, s1, s0 represent unhold[i - 2], unhold[i - 1], unhold[i]
	 */
	public int maxProfitCooldown(int[] prices) {
        if (prices == null || prices.length == 0) {
        	return 0;
        }
//        int[] hold = new int[prices.length+1], unhold = new int[prices.length+1];
//        hold[0] = -prices[0];
//        hold[1] = -prices[0];
//        for (int i = 2; i <= prices.length; i++) {
//        	hold[i] = Math.max(unhold[i-2] - prices[i-1], hold[i-1]);
//        	unhold[i] = Math.max(hold[i-1] + prices[i-1], unhold[i-1]);
//        }
//        return unhold[prices.length];
       
        //constant space
        int b0 = -prices[0], b1 = -prices[0];
        int s0 = 0, s1 = 0, s2 = 0;
        for (int i = 1; i < prices.length; i++) {
        	b0 = Math.max(b1, s2 - prices[i]);
        	s0 = Math.max(b1 + prices[i], s1);
        	
        	s2 = s1; s1 = s0; b1 = b0;
        }
        
        return s0;
    }
	
	/**
	 * 可以随便交易很多次，可以同时买很多股票，但是一旦卖就要把手里的股票全部卖了，问怎样最大化收益。比如[1, 2,3], 前2天都买，第三天全部卖，收益就是(3-1)+(3-2).
	 */
	public int maxProfitMultipleBuy(int[] prices) {
	    int max = 0;
	    int profit = 0;
	    for (int i = prices.length - 1; i >= 0; i--) {
	        if (max > prices[i]) {
	            profit += max - prices[i];
	            System.out.println(profit);
	        }
	        else {
	            max = prices[i];
	        }
	    }
	    return profit;
	}
	
	/**
	 * 如果有交易费用
	 * hold[i] = Math.max(hold[i - 1], unhold[i - 1] - prices[i] - buyCost)
	 * unhold[i] = Math.max(unhold[i - 1], hold[i - 1] + prices[i] - sellCost)
	 * 
	 */
	public int maxProfitTxnFee(int[] prices, int fee) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int[] hold = new int[prices.length + 1], unhold = new int[prices.length + 1];
		hold[0] = -prices[0];
		hold[1] = -prices[0];
		for (int i = 2; i <= prices.length; i++) {
			hold[i] = Math.max(unhold[i - 1] - prices[i - 1] - fee, hold[i - 1]);
			unhold[i] = Math.max(hold[i - 1] + prices[i - 1] - fee, unhold[i - 1]);
		}
		
		return unhold[prices.length];
	}

	// maintain a variable which is the profit we make 
	// when the prices is continuously ascending,
	// when today's prices is lower than yesterday,
	// which means we finish one transaction, 
	// calculate the final profit with charge, if > 0 add to result
	public int maxProfitWithCharge(int[] prices, int charge) {
	    int profit = 0;
	    int localProfit = 0;
	    boolean yesterdaySold = false;
	    for (int i = 1; i < prices.length; i++) {
	        if (prices[i] > prices[i - 1]) {
	            localProfit += prices[i] - prices[i - 1];
	            if (!yesterdaySold) {
	                localProfit -= charge;
	            }
	            yesterdaySold = true;
	        }
	        else if (yesterdaySold) {
	            profit += localProfit > 0 ? localProfit : 0;
	            localProfit = 0;
	            yesterdaySold = false;
	        }
	    }
	    if (localProfit > 0) {
	        profit += localProfit;
	    }
	    return profit;
	}


	public static void main(String[] args) {
		BuyAndSellStock clz = new BuyAndSellStock();
		int[] p = {1,3,5,2,4,9};
		System.out.println(clz.maxProfitTxnFee(p, 3));
		System.out.println(clz.maxProfitWithCharge(p, 3));
	}
}
