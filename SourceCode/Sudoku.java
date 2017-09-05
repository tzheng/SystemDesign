import java.util.HashSet;

public class Sudoku {
	public boolean isValidSudoku(char[][] board) {
		for (int i = 0; i < 9; i++) {
			HashSet<Character> rows = new HashSet<Character>();
			HashSet<Character> cols = new HashSet<Character>();
			HashSet<Character> cube = new HashSet<Character>();
			for (int j = 0; j < 9; j++) {
				//检查第i行
				if (board[i][j] != '.' && !rows.add(board[i][j]))
					return false;
				//注意，这里是 j,i, 表明检查第 i 列
				if (board[j][i] != '.' && !cols.add(board[j][i]))
					return false;
				
				//看这里怎么把i,j对应到每个小方格，因为有9个小方格，所以i = 0 -9 要分别对应这九个
				// j = 0 - 9 则对应每个小方格里面的九个数字。
				int rIdx = 3 * (i / 3), cIdx = 3 * (i % 3);
				if (board[rIdx + j / 3][cIdx + j % 3] != '.' && !cube.add(board[rIdx + j / 3][cIdx + j % 3])) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 37. Sudoku Solver
	 * 解决就是一行一行试字母，关键问题在于 isValid(), 可以简化，不用像LC 36那样，因为只要检查当前行列就好了
	 */
	public void solveSudoku(char[][] board) {
        if (board == null || board.length != 9 || board[0] == null || board[0].length != 9) return;
        solve(board, 0, 0);
    }
    
    private boolean solve(char[][] board, int i, int j) {
        if (i == 9) return true;
        if (j >= 9) return solve(board, i+1, 0);
        
        if (board[i][j] == '.') {
            for (int k = 1; k <= 9; k++) {
                board[i][j] = (char)(k + '0');
                if (isValid(board, i, j)) {
                    if (solve(board, i, j+1)) return true;
                }
                board[i][j] = '.';
            }
        } else {
            return solve(board, i, j+1);
        }
        return false;
    }
    
    private boolean isValid(char[][] board, int i, int j) {
        for (int col = 0; col < 9; col++) {
            if (col != j && board[i][j] == board[i][col]) return false;
        }
        for (int row = 0; row < 9; row++) {
            if (row != i && board[i][j] == board[row][j]) return false;
        }
        for (int row = i / 3 * 3; row < i / 3 * 3 + 3; row++) {//see how to iterate the submatrix
            for (int col = j / 3 * 3; col < j / 3 * 3 + 3; col++) {
                //col < j / 3 * 3 + 3, not col < i / 3 * 3 + 3 !!!!!!!!!!!!
                if ((row != i || col != j) && board[i][j] == board[row][col]) return false;//use ||, not && !!!!!!!!!!!!!!
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
    	
    }
}
