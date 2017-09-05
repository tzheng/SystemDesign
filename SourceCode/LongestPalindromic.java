
public class LongestPalindromic {
	public String longestPalindrome(String s) {
        int len = s.length();
        boolean[][] p = new boolean[len][len];
        
        for (int i = 0; i < len; i++) {
        	p[i][i] = true;
        }
        
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (s.charAt(j) == s.charAt(i) && (i - j < 2 || p[j+1][i-1])) {
                    p[j][i] = true;
                }
            }
        }
        
        int maxL=0, start=0, end=0;  
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (p[j][i] && maxL < i-j+1) {
                    maxL = i-j+1;
                    start = j;
                    end = i;
                }
            }
        }
        
        return s.substring(start, end+1);
    }
	
	public int minCut(String s) {
        int n = s.length();
        if(n<=1) return 0;
        
        boolean[][] p = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            p[i][i] = true;
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j < 2 || p[j+1][i-1])) {
                    p[j][i] = true;
                }
            }
        }
        
        p = getIsPalindrome(s);
        
        int[] f = new int[n+1];
        for (int i = 1; i <= n; i++) {
            f[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (p[j][i-1]) {
                    f[i] = Math.min(f[i], f[j] + 1);
                }
            }
        }
        return f[n];
    }
	
	 private boolean[][] getIsPalindrome(String s) {
	        boolean[][] isPalindrome = new boolean[s.length()][s.length()];

	        for (int i = 0; i < s.length(); i++) {
	            isPalindrome[i][i] = true;
	        }
	        for (int i = 0; i < s.length() - 1; i++) {
	            isPalindrome[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
	        }

	        for (int length = 2; length < s.length(); length++) {
	            for (int start = 0; start + length < s.length(); start++) {
	                isPalindrome[start][start + length]
	                    = isPalindrome[start + 1][start + length - 1] && s.charAt(start) == s.charAt(start + length);
	            }
	        }

	        return isPalindrome;
	    }
	 
	public static void main(String[] args) {
		LongestPalindromic clz = new LongestPalindromic();
		clz.minCut("aab");
	}
}
