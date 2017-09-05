import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SurroundedRegions {
	public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int n = board.length;
        int m = board[0].length;
        
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (board[i][0] == 'O') {
                flag = true;
                bfs(i, 0, board);
            }
            if (board[i][m-1] == 'O') {
                flag = true;
                bfs(i, m-1, board);
            }
        }
        
        Map.Entry<Integer, Integer> map = null;
        
        for (int i = 0; i < m; i++) {
            if (board[0][i] == 'O') {
                flag = true;
                bfs(0, i, board);
            }
            if (board[n-1][i] == 'O') {
                flag = true;
                bfs(n-1, i, board);
            }
        }
        
       
             for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                	if (board[i][j] == '+') {
                		board[i][j] = 'O';
                	} else {
                		board[i][j] = 'X';     
                	}
                }
             }
   
    }
    
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    
    private void bfs(int x, int y, char[][] board) {
        Queue<int[]> queue = new LinkedList<int[]>();
        
        // int[][] visited = new int[board.length][board[0].length];
        queue.offer(new int[]{x, y});
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            board[curr[0]][curr[1]] = '+';
            for (int i = 0; i < 4; i++) {
                int[] next = new int[]{curr[0] + dx[i], curr[1] + dy[i]};
                if (inRange(next, board)) {
                    queue.offer(next);
                }
            }
        }
        
    }
    
    private boolean inRange(int[] a, char[][] board) {
        return a[0] >= 0 && a[0] < board.length && a[1] >= 0 && a[1] < board[0].length 
                && board[a[0]][a[1]] == 'O';
    }
    
    
    public static void main(String[] args) {
    	String[] strs = {"XXXX","XOOX","XXOX","XOXX"};
    	char[][] board = new char[4][4];
    	
    	for (int i = 0; i < strs.length; i++) {
    		board[i] = strs[i].toCharArray();
    	}
    	
    	SurroundedRegions clz = new SurroundedRegions();
    	clz.solve(board);
    }
}
