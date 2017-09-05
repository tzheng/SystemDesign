
public class FriendCircle {
	int[] id;
    int count;

    public int findCircleNum(int[][] M) {
        int n = M.length;
        int count = n;
        id = new int[n];
        
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (M[i][j] == 1) {
                    connect(i, j);
                }
            }
        }
        System.out.println(count);
        return count;
    }
    
    private int root(int p) {
        while (p != id[p]) {
            id[p] = id[id[p]];  //path compression for better performance
            p = id[p];
        }
        return p;
    }
    
    private void connect(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        id[j] = i;
        count--;
    }
    
    public static void main(String[] args) {
    	FriendCircle clz = new FriendCircle();
    	int[][] M = {{1,1,0}, {1,1,0}, {0,0,1}};
    	clz.findCircleNum(M);
    }
}
