
public class UnionFindQuickUnion {
	int[] id;
	
	public UnionFindQuickUnion(int n) {
		id = new int[n];
		for (int i = 0; i < n; i++) {
			id[i] = i;
		}
	}
	
	/**
	 * union(p, q),分别找到 p ， q 的 root ，
	 * 并将 p 的 root 改为 q 的 root ，
	 * 成本取决于 component 的高度，最坏时间成本为 O(n) 。
	 */
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		id[i] = j;
	}
	
	/**
	 * 新增 root 方法用于找到 i 的 root （与 find() 作用相同），成本取决于 component 的高度，最坏时间成本为 O(n)O(n) 。
	 */
	private int root(int i) {
		while (i != id[i]) {
			i = id[i];
		}
		return i;
	}
	
	//两点是否相连，只需判断是否有相同的 root 。成本取决于 component 的高度，最坏情况为 N 。
	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
	
	public int find(int p) {
		return root(p);
	}
	
	//component 数量为数组中不同 id 的个数，需要遍历 component ，时间成本为 O(n)O(n)
	public int count() {
		return -1;
	}
}
