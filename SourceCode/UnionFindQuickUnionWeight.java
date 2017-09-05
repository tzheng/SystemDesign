

public class UnionFindQuickUnionWeight {
	int[] id;
	int[] size;
	
	public UnionFindQuickUnionWeight(int n) {
		id = new int[n];
		size = new int[n];
		for (int i = 0; i < n; i++) {
			id[i] = i;
			size[i] = 1;
		}
	}
	
	/**
	 * union(p, q) ：比较 q ， p 所在 component 的 size ，将较小 component 的 root 改为较大 component 的 root 。
	 *  任意节点所在 component 的最大深度为 logNlogN
	 *	证明：
		假设有树 T1 与树 T2 ， T1.size < T2.size ， 因此若 union(T1, T2) ，连接后的树高度加 1 ，
		而数的 size 却至少是之前的两倍，因此最多可以 double logN 次，也就是树的高度最多可以增加 logN
	 */
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		if (i == j) return;
		if (size[i] < size[j]) {
			id[i] = j;
			size[j] += size[i];
		} else {
			id[j] = i;
			size[i] += size[j];
		}
	}
	
	private int root(int i) {
		while (i != id[i]) {
			i = id[i];
		}
		return i;
	}
	
	/**
	 * weighted quick union 解决了树太高的问题，但是每次操作还是需要重复的去计算 root ，如何减少重复运算次数？
		改进方式：每次 root 某节点后，将该节点的父节点设为新的 root ，
		从而使连接体的树 更加平滑，减少深度。
		这样一个 union find 里，最多有一个 logN 的查找，随着树深度的减少，
 		查找速度可以达到 Quick Find ，也就是 O(1)。 只需在 root 增加一步操作：
	 * @param i
	 * @return
	 */
	private int rootPathCompression(int i) {
		while (i != id[i]) {
			id[i] = id[id[i]];
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
