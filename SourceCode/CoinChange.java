import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinChange {
	
	/**
	 * 494. Target Sum  - 给一堆数字， 只有加减号可以用
	 * 1. 暴力DFS， 很明显超时，如果数字都是1就很惨了
	 */
	public int findTargetSumWaysDFS(int[] nums, int pos, int target) {
		if (pos == nums.length) {
			if (target == 0) return 1;
			else return 0;
		}
		
		int count = 0;
		count += findTargetSumWaysDFS(nums, pos+1, target-nums[pos]);
		count += findTargetSumWaysDFS(nums, pos-1, target+nums[pos]);
		return count;
	}
	
	/**
	 * 因为暴力解法做了很多不必要的计算，所以是指数级的复杂度，
	 * 我们就可以用动态规划来优化。
	 * sum(P) - sum(N) = target
	 * sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N)
	 * 2 * sum(P) = target + sum(nums)
	 */
	public int findTargetSumWaysDP(int[] nums, int s) {
		int sum = 0;
        for (int n : nums)
            sum += n;
        if (sum < s || (s + sum) % 2 > 0) {
            return 0;
        }
        return subsetSum(nums, (s + sum) /2); 
	}
    public int subsetSum(int[] nums, int s) {
        int[] f = new int[s + 1]; 
        f[0] = 1;
        for (int n : nums)    //特别要注意这里循环，应该是循环每一个数字, 和下面的coin changes不一样，因为每个数字只能用一次。
            for (int i = s; i >= n; i--)  // 从大到小才行。如果从小到大，变成了coin changes II了。
                f[i] += f[i - n]; 
        System.out.println(Arrays.toString(f));
        return f[s];
    }
    
    /**
     * 还有一种Hash的解法，就是把每个组合得到的值，都存在hash里面。
     * 但是复杂度还是指数级的，考虑到第一个数字要放 + - 进入map，然后第二个数字，要把第一个数字的两个结果拿出来，分别+-第二个数字，
     * 第三个数字要把四个结果拿出来，再加减，这样就是2^n , 空间也是。
     */
	public int findTargetSumWays1(int[] nums, int s) {
		if (nums.length == 0)
			return 0;
		Map<Integer, Integer> prev = new HashMap<Integer, Integer>();
		Map<Integer, Integer> cur;
		for (int i = 0; i < nums.length; i++) {
			// for nums[0]
			if (i == 0) {
				prev.put(nums[0], 1);
				if (!prev.containsKey(-nums[0])) {
					prev.put(-nums[0], 1);
				} else {
					prev.put(-nums[0], 2); // if nums[0] is 0.
				}
			} else {
				cur = new HashMap<Integer, Integer>();
				for (Map.Entry<Integer, Integer> entry : prev.entrySet()) {
					int sumSoFar = entry.getKey();
					int count = entry.getValue();
					// add num[i]
					if (!cur.containsKey(sumSoFar + nums[i])) {
						cur.put(sumSoFar + nums[i], count);
					} else {
						cur.put(sumSoFar + nums[i], cur.get(sumSoFar + nums[i]) + count);
					}
					// subtract num[i]
					if (!cur.containsKey(sumSoFar - nums[i])) {
						cur.put(sumSoFar - nums[i], count);
					} else {
						cur.put(sumSoFar - nums[i], cur.get(sumSoFar - nums[i]) + count);
					}
				}
				prev = cur;
			}
		}
		return prev.containsKey(s) ? prev.get(s) : 0;
	}
	
	/**
	 * Target sum 和 Expression Add Operator的合体，就是target sum可以加*
	 * 比如 1,2,4   target = 6, 有  1+2+3 = 1*2*3
	 */
	public void targetSumMultiple(int[] nums, int target) {
		List<String> res = new ArrayList<>();
		helper(0, nums, target, new StringBuilder(), res, 0, 0);
		System.out.println(Arrays.toString(res.toArray()));
	}
	
	private void helper(int pos, int[] nums, int target, StringBuilder path, List<String> res, int sum, int prevNum) {
		if (pos == nums.length) {
			if (target == sum) {
				res.add(path.toString());
			}
			return;
		}
		
		int len = path.length();
		
		int num = nums[pos];
		helper(pos+1, nums, target, path.append("+").append(num), res, sum + num, num);
		path.setLength(len);
		helper(pos+1, nums, target, path.append("-").append(num), res, sum - num, -num);
		path.setLength(len);
		if (pos > 0) {
			helper(pos+1, nums, target, path.append("*").append(num), res, sum - prevNum + prevNum * num, prevNum*num);
			path.setLength(len);
		}
		
	}
	
	
	/**
	 * 322. Coin Change
	 * Write a function to compute the fewest number of coins that you need to make up that amount.
	 * 找最小值，可以重复使用硬币
	 * 1. 还是用DFS做，就是标准的套模板，时间复杂度还是指数级的
	 * 2. DP， 背包问题。
	 * 		f[i] 表示凑够i的时候最少需要多少个硬币。
	 * 		f[i] = min(f[i - num[j]])  j = 0 - coins.length 
	 */
	 public int coinChangeDP(int[] coins, int amount) {
		 int[] f = new int[amount+1];
		 Arrays.sort(coins);
		 f[0] = 0; 
		 for (int i = 1; i <= amount; i++) {  //循环要从1开始，0 没有意义
			 f[i] = -1; 
			 for (int j = 0; j < coins.length; j++) {
				 if (i - coins[j] < 0) break;
				 if (f[i-coins[j]] == -1) continue;
				 f[i] = f[i] == -1 ? f[i-coins[j]] + 1 : Math.min(f[i], f[i-coins[j]] + 1);
			 }
		 }
		 return f[amount];
	 }
	 
	 /**
	  * 518. Coin Change 2
	  * 上一题是计算最少需要多少个， 这一题是计算一共有多少个
	  */
	 public int change(int amount, int[] coins) {
		 int[] f = new int[amount+1];
		 f[0] = 1;
		 Arrays.sort(coins);
		 
		 for (int coin : coins) {
			 for (int i = coin; i <= amount; i++) {
				 f[i] += f[i-coin];
			 }
		 }
		 
		 /**
		  * int[][] dp = new int[coins.length+1][amount+1];
	        dp[0][0] = 1;
	        for (int i = 1; i <= coins.length; i++) {
	            dp[i][0] = 1;
	            for (int j = 1; j <= amount; j++) {
	                dp[i][j] = dp[i-1][j] + (j >= coins[i-1] ? dp[i][j-coins[i-1]] : 0);
	            }
	        }
	        return dp[coins.length][amount];
		  */
		 
		 System.out.println(Arrays.toString(f));
		 return f[amount];
	 }
	 
	 /**
	  * Perfect Squares
	  * 12 = 4 + 4 + 4  return 3
	  * 13 = 4 + 9  return 2
	  */
	public int numSquares(int n) {
		if (n <= 1) {
			return n;
		}
		int[] f = new int[n + 1];

		Arrays.fill(f, Integer.MAX_VALUE);
		f[0] = 0;
		for (int i = 1; i * i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (j - i * i < 0)
					continue;
				f[j] = Math.min(f[j], f[j - i * i] + 1);
			}
		}

//		f[0] = 0;
//		f[1] = 1;
//		for (int i = 2; i <= n; i++) {
//			f[i] = Integer.MAX_VALUE;
//			for (int k = 1; k * k <= i; k++) {
//				f[i] = Math.min(f[i - k * k] + 1, f[i]);
//			}
//		}

		System.out.println(Arrays.toString(f));
		return f[n];
	}
	 
	 /**
	  * http://love-oriented.com/pack/
	  * 
	  * 01 背包问题，最简单的，有N件物品和一个容量为V的背包。第i件物品的费用是c[i]，价值是w[i]。
	  * 求解将哪些物品装入背包可使价值总和最大。特点是：每种物品仅有一件，可以选择放或不放。
	  * 对于硬币，或者target sum， w[i] 都是 1
	  * 如果是第一种问法，要求恰好装满背包，那么在初始化时除了f[0]为0其它f[1..V]均设为-∞，这样就可以保证最终得到的f[N]是一种恰好装满背包的最优解。
	  * 
	  * 如果并没有要求必须把背包装满，而是只希望价格尽量大，初始化时应该将f[0..V]全部设为0。
	  * 为什么呢？可以这样理解：初始化的f数组事实上就是在没有任何物品可以放入背包时的合法状态。如果要求背包恰好装满，
	  * 那么此时只有容量为0的背包可能被价值为0的nothing“恰好装满”，其它容量的背包均没有合法的解，属于未定义的状态，
	  * 它们的值就都应该是-∞了。如果背包并非必须被装满，那么任何容量的背包都有一个合法解“什么都不装”，这个解的价值为0，
	  * 所以初始时状态的值也就全部为0了。
	  * 
	  * 
	  * 
	  * for i=1..N
		    for v=V..0
		        f[v]=max{f[v],f[v-c[i]]+w[i]};
	  */

	 /**
	  * 完全背包问题
	  * 这个问题非常类似于01背包问题，所不同的是每种物品有无限件。也就是从每种物品的角度考虑，
	  * 与它相关的策略已并非取或不取两种，而是有取0件、取1件、取2件……等很多种。O(VN)的算法
	  * 这个算法使用一维数组，先看伪代码：

		for i=1..N
		    for v=0..V     <----
		        f[v]=max{f[v],f[v-cost]+weight}
		        
	  * 你会发现，这个伪代码与P01的伪代码只有v的循环次序不同而已!!!!为什么这样一改就可行呢？
	  * 首先想想为什么P01中要按照v=V..0的逆序来循环。这是因为要保证第i次循环中的状态
	  * f[i][v]是由状态f[i-1][v-c[i]]递推而来。换句话说，这正是为了保证每件物品只选一次，
	  * 保证在考虑“选入第i件物品”这件策略时，依据的是一个绝无已经选入第i件物品的子结果f[i-1][v-c[i]]。
	  * 而现在完全背包的特点恰是每种物品可选无限件，所以在考虑“加选一件第i种物品”这种策略时，
	  * 却正需要一个可能已选入第i种物品的子结果f[i][v-c[i]]，所以就可以并且必须采用v=0..V 的顺序循环。
	  * 这就是这个简单的程序为何成立的道理。这个算法也可以以另外的思路得出。例如，将基本思路中求解
	  * f[i][v-c[i]]的状态转移方程显式地写出来，代入原方程中，会发现该方程可以等价地变形成这种形式：
	  *    f[i][v]=max{f[i-1][v],f[i][v-c[i]]+w[i]}
	  * 
	  * 将这个方程用一维数组实现，便得到了上面的伪代码。
	  */
	 
	 /**
	  * 求方案总数
	  * 对于一个给定了背包容量、物品费用、物品间相互关系（分组、依赖等）的背包问题，除了再给定每个物品的价值后求可得到的最大价值外，还可以得到装满背包或将背包装至某一指定容量的方案总数。
	  * 对于这类改变问法的问题，一般只需将状态转移方程中的max改成sum即可。例如若每件物品均是完全背包中的物品，转移方程即为
	  * 
	  * f[i][v]=sum{f[i-1][v],f[i][v-c[i]]}
	  * 初始条件f[0][0]=1。
	  * 事实上，这样做可行的原因在于状态转移方程已经考察了所有可能的背包组成方案。
	  */
	 
	public static void main(String[] args) {
		CoinChange clz = new CoinChange();
		int[] coins = { 1, 2, 3 };
//		clz.subsetSum(coins, 4);
//		clz.change(4, coins);
		
		clz.numSquares(12);
		
		clz.targetSumMultiple(coins, 6);
	}
}
