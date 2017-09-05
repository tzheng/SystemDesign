import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkLoadTask {
	/**Question1:
		这个稍微难， 说有一堆task，每个有不同时间完成， 然后有一堆worker， 说如何分配 task to worker，完成时间最短， task是独立的。.看起来像背包， DP
		task：  2，2，3，7， 1
		worker： 2
		解（2，2，3） （1， 7）注：task不一定是连续的，比如7，2，1，2，3 的解也是 （2，2，3） （1， 7）。应该是个多重背包
		**/
	
	/**
	 * 求问LZ分配任务的题是怎么做的。
	 * 我的思路是用一个min heap保存每个worker拥有的任务的时间总数。
	 * 然后将tasks按照倒序排列。从耗时最大的task开始遍历，每次将遍历倒的task加入min heap 
	 * 最顶部的那个worker。这样就保证每次都将目前未分配的任务中耗时最多的任务分配给工作时间最短的worker。达到平衡
	 * 
	 * 这是错的，还是要DFS
	 */
	
	//先看假如只有两个worker怎么办
	int min = Integer.MAX_VALUE;
	int totalSum = 0;
	public void calcTask(int[] tasks) {
		for (int t : tasks) {
			totalSum += t;
		}
		dfs(tasks, 0, 0, new ArrayList<Integer>());
	}
	
	private void dfs(int[] tasks, int pos, int sum, List<Integer> path) {
		
		if (Math.abs(totalSum - 2*sum) < min) {
			System.out.println(Arrays.toString(path.toArray()) + ", " + sum + " : " + (totalSum - sum));
			min = totalSum - 2*sum;
		}
		for (int i = pos; i < tasks.length; i++) {
			path.add(tasks[i]);
			dfs(tasks, i+1, sum + tasks[i], path);
			path.remove(path.size()-1);
		}
	}

	
	/**
	 * 如果有多个worker
	 */
	int best = Integer.MAX_VALUE;
	public int distribute(int[] tasks, int k) {
		int[] workers = new int[k];
		helper(tasks, workers, 0);
		System.out.println(best);
		return best;
	}
	
	private void helper(int[] tasks, int[] workers, int pos) {
		if (pos == tasks.length) {
			int curr = Integer.MIN_VALUE;
			for (int i = 0; i < workers.length; i++) {
				curr = Math.max(workers[i], curr);
			}
			best = Math.min(best, curr);
			System.out.println(Arrays.toString(workers));
			return;
		}
		
		for (int i = 0; i < workers.length; i++) {
			workers[i] += tasks[pos];
			helper(tasks, workers, pos+1);
			workers[i] -= tasks[pos];
		}
	}
	
	
	
	/**
	 * 如果每个任务都连续
	 * painter‘s partition problem
	 * You have to paint N boards of length {A0, A1, A2, A3 … AN-1}. 
	 * There are K painters available and you are also given how much time a painter takes 
	 * to paint 1 unit of board. You have to get this job done as soon as possible under the 
	 * constraints that any painter will only paint contiguous sections of board.
	 * 
	 * f[i, k] = min (max( f[j, k-1] + sum[j,i], sum[j,n-1]))
	 */
	public int partition(int[] a, int n, int k) {
		if (n <= 0 || k <= 0)
	        return -1;
		if (n == 1) 
			return a[0];
		if (k == 1) return getSum(a, 0, n-1);
		int best = Integer.MAX_VALUE;
		for (int j = 1; j < n; j++) {
			int part = partition(a, j, k-1);
			best = Math.min(best,  Math.max(part, getSum(a, j, n-1)));
		}
		return best;
	}
	
	int getSum(int[] a, int from, int to) {
		int sum = 0;
		for (int i = from; i <= to; i++) {
			sum += a[i];
		}
		return sum;
	}
	//Run time: O(kN*N), space complexity: O(kN).
	public int partitionDP(int[] a, int n, int k) {
		int[][] f = new int[n+1][k+1];
		int[] sum = new int[n+1];
		for (int i = 1; i <= n; i++) {
			sum[i] = sum[i-1] + a[i-1];
		}
		
		for (int i = 1; i <= n; i++) {
			f[i][1] = sum[i];
		}
		
		for (int i = 1; i <= k; i++) {
			f[1][i] = a[0];
		}
		
		for (int i = 2; i <= k; i++) {
			for (int j = 2; j <= n; j++) {
				int best = Integer.MAX_VALUE;
				for (int p = 1; p <= j; p++) {
					best = Math.min(best, Math.min(f[p][i-1], sum[j] - sum[p]));
				}
				f[j][i] = best;
			}
		}
		
		return f[n][k];
	}
	
	
	
	public static void main(String[] args) {
		WorkLoadTask clz = new WorkLoadTask();
		int[] tasks = {2,2,3,1,7};
		clz.calcTask(tasks);
		clz.distribute(tasks, 2);
	}
}
