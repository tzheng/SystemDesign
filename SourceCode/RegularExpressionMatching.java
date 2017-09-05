
public class RegularExpressionMatching {

	//time complexity  2^min(s,p) 
	// http://stackoverflow.com/questions/25671967/whats-time-complexity-of-this-algorithm-for-wildcard-matching
	// worse case  *  match abcd
	 public boolean isMatch(String s, String p) {
	       
	        
        return helper(s, p, 0, 0);
    }
    
    private boolean helper(String s, String p, int i, int j) {
    	if (j >= p.length()) {
			return i == s.length();
		}
		
		if (j == p.length() - 1) {
			return (i == s.length() - 1) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
		}
		
		if (p.charAt(j+1) != '*') {
			if (i >= s.length()) return false;
			if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') 
				return helper(s, p, i+1, j+1);
			return false;
		}
		
		//p.charAt(j+1) == '*', next is *, means current char can appear 0 times to n times
		while (i < s.length() && j < p.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.')) {
			//如果正好s的当前字母和p的当前字母匹配，我们就可以看，s后面的字母是不是也匹配
			if (helper(s, p, i, j+2)) {
				return true;
			}
			i++;
		}
		
		//完全不和p的当前字母匹配。
		return helper(s, p, i, j+2); 
    }
    
    
	public static void main(String[] args) {
		RegularExpressionMatching clz = new RegularExpressionMatching();
		System.out.println(clz.isMatch("ababab", "ab*"));
	}
}
