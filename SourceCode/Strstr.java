
public class Strstr {
	/**
	 * 28. Implement strStr()
	 * 简单的 n^2 做法
	 * 
	 */
	public int strStr(String haystack, String needle) {
		if (haystack == null || needle == null) return -1;
		
		for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
			int j = 0;
			while (j < needle.length() && needle.charAt(j) == haystack.charAt(i+j))
				j++;
			if (j == needle.length()) 
				return i;
		}
		return -1;
	}
	
	/**
	 * 459. Repeated Substring Pattern
	 */
	public boolean repeatedSubstringPattern(String s) {
        int len = s.length();
		for (int i = 1; i <= len/2; i++) {
			if (len % i != 0) continue;
			int m = len / i;
			String sub = s.substring(0, i);
			int j = 1;
			for (; j < m; j++) {
				if (!sub.equals(s.substring(j*i, j*i + i))) {
					break;
				}
			}
			if (j == m) 
				return true;
		}
		
		return false;
    }
	
	/**
	 * 上面两题都可以用 n^2 的解法通过测试。但是还有O(N)的解法，就是KMP算法。
	 */
	public int kmp(String haystack, String needle) {
		int[] next = getNext(needle);
		int i = 0;
		while (i <= haystack.length() - needle.length()) {
			int success = 1;
			for (int j = 0; j < needle.length(); j++) {
				if (needle.charAt(0) != haystack.charAt(i)) {
					success = 0;
					i++;
					break;
				} else if (needle.charAt(j) != haystack.charAt(i+j)) {
					success = 0;
					i = i+j - next[j-1];
					break;
				}
			}
			if (success == 1) {
				return i;
			}
		}
		return -1;
	}
	
	public int[] getNext(String needle) {
		int[] next = new int[needle.length()];
		next[0] = 0;
	 
		for (int i = 1; i < needle.length(); i++) {
			int index = next[i - 1];
			while (index > 0 && needle.charAt(index) != needle.charAt(i)) {
				index = next[index - 1];
			}
	 
			if (needle.charAt(index) == needle.charAt(i)) {
				next[i] = next[i - 1] + 1;
			} else {
				next[i] = 0;
			}
		}
	 
		return next;
	}
	
	/**
	 * 214. Shortest Palindrome
	 * 0. 暴力破解，最长的palindrome就是 s 直接反过来s', 然后连起来  s's，然后慢慢缩短
	 * 1. 使用kmp解法。
	 * 2. 用下面的recursive
	 * 主要使用两个指针从前后对向遍历，就跟我们判断String是否是Palindrome一样，假如s.charAt(i) == s.charAt(j)，则j++。
	 * 走完之后的结果j所在假如是s.length() - 1，则整个String为Palindrome，返回s，否则，j所在的位置及其以后的部分肯定不是
	 * Palindrome，这是我们要把这部分翻转并且加到结果的前面。至于 substring(0, j)这部分，我们仍需要使用递归的方法继续判断。
	 * 3. 参考Palindromes.java里面的解法
	 */
	public String shortestPalindromeTLE(String s) {  
	    StringBuilder sb = new StringBuilder(s).reverse();  
	    for(int i=0; i<s.length(); i++) {  
	        if(s.startsWith(sb.substring(i))) {  
	            return sb.substring(0, i)+s;  
	        }  
	    }  
	    return s;  
	}  
	
	public String shortestPalindrome(String s) {
		int j = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == s.charAt(j)) {
				j += 1;
			}
		}
		if (j == s.length()) {
			return s;
		}
		String suffix = s.substring(j);
		return new StringBuffer(suffix).reverse().toString() + shortestPalindrome(s.substring(0, j)) + suffix;
	}
	
    public static void main(String[] args) {
    	Strstr clz = new Strstr();
    	clz.repeatedSubstringPattern("bb");
    	System.out.println(clz.shortestPalindrome("cbbd"));
    }
} 
