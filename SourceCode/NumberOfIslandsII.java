import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberOfIslandsII {

	
	int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    int count = 0;
    int[] id;
    
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> result = new ArrayList<>();
        id = new int[m*n];
        for (int i = 0; i < m*n; i++) {
            id[i] = i;
        }
        int[][] matrix = new int[m][n];
        
        for (int[] p : positions) {
            matrix[p[0]][p[1]] = 1;
            count++;
            for (int i = 0; i < 4; i++) {
                int x = p[0] + dx[i];
                int y = p[1] + dy[i];
                if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] == 1) {
                    connect(p[0]*n + p[1], x*n + y);
                }
            }
            result.add(count);
        }
        
        return result;
    }
    
    private int find(int p) {
        while (p != id[p]) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }
    
    private void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        id[j] = i;
        count--;
    }
    
    public static void main(String[] args) {
    	int[][] pos = {{0,0},{7,1},{6,1},{3,3},{4,1}};
    	NumberOfIslandsII clz = new NumberOfIslandsII();
    	
    	List<Integer> ret = clz.numIslands2(8, 4, pos);
    	System.out.println(Arrays.toString(ret.toArray()));
    }
}
