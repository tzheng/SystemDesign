
public class WildCardMatching {

	 public boolean isMatch(String s, String p) {
	       
	        
       return helper(s, p, 0, 0);
   }
   
   private boolean helper(String s, String p, int i, int j) {
      if (j >= p.length()) {
          return i >= s.length();
      }
      
      if (j == p.length() - 1) {
          return (i == s.length()-1) && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?');
      }
      
      //next is not "*"
      if (j+1 < p.length() && p.charAt(j+1) != '*') {
          if (i >= s.length()) {
              return false;
          }
          if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
              return helper(s, p, i+1, j+1);
          } else {
              return false;
          }
      }
      
      //next is *,  compare i , i+1，i+2 ... s.length - 1 with j
      while (i < s.length() && j < p.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
          if (helper(s, p, i, j+2)) {
              return true;
          }
          i++;
      }
      
      //next is *, current character doesn't match, go to next pattern
      return helper(s, p, i, j+2);
   }
   
   
	public static void main(String[] args) {
		RegularExpressionMatching clz = new RegularExpressionMatching();
		System.out.println(clz.isMatch("ababab", "ab*"));
	}
}
