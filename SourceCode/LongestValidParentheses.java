
public class LongestValidParentheses {
	 public int longestValidParentheses(String s) {
	        if (s == null || s.length() < 2) {
	            return 0;
	        }    
	        
	        int[] f = new int[s.length() + 1];
	        f[0] = 0;
	        for (int i = 1; i <= s.length(); i++) {
	            for (int j = 0; j < i; j++) {
	                String str = s.substring(j, i);
	                if (isValid(str)) {
	                    f[i] = Math.max(f[i], f[j] + i-j);
	                }
	            }
	        }
	        
	        return f[s.length()];
	    }
	    
	    private boolean isValid(String str) {
	        int count = 0;
	        for (int i = 0; i < str.length(); i++) {
	            if (str.charAt(i) == '(' ) {
	                count++;
	            } else {
	                if (count == 0) return false;
	                count--;
	            }
	        }
	        
	        return count == 0;
	    }
	    
	    public static void main(String[] args) {
	    	LongestValidParentheses clz = new LongestValidParentheses();
	    	clz.longestValidParentheses("())");
	    }
}
