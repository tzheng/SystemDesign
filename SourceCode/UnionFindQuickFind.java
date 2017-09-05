
public class UnionFindQuickFind {
	
	int[] id;
	public UnionFindQuickFind(int n) {
		id = new int[n];
		for (int i = 0; i < n; i++) {
			id[i] = i;
		}
	}
	
	/**
	 * union(int p, int q) 意味着连接 p 、 q 两点，本文默认规则将 p 点的 id 改成 q 的的 id 。
	 * 该操作的时间复杂度为 O(n)O(n) ，因为需要遍历数组找到属于 p 所在 component 内的所有点，并将这些点的 id 全部改变。
	 */
	public void union(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		
		for (int i = 0; i < id.length; i++) {
			if (id[i] == pid) {
				id[i] = qid;
			}
		}
	}
	
	public boolean connected(int p, int q) {
		return id[p] == id[q];
//		return false;
	}
	
	//某一点所在的 component 即该点的 id ，时间成本为 O(1)O(1) 。
	public int find(int p) {
		return id[p];
	}
	
	//component 数量为数组中不同 id 的个数，需要遍历 component ，时间成本为 O(n)O(n)
	public int count() {
		return -1;
	}
	
	
}
