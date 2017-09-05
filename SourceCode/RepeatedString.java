
public class RepeatedString {
	public boolean repeatedSubstringPattern(String str) {
		int len = str.length();
		for (int i = len/2; i >= 1; i--) {
			if (len % i != 0) 
				continue;
			int m = len/i;
			String sub = str.substring(0, i);
			
			int j = 0;
			for (;  j < m; j++) {
				if (!sub.equals(str.substring(i*j, i*(j+1)))) {
					break;
				}
			}
			if (j == m) return true;
		}
		
		return false;
	}
	
	/**
	 * 给一个字符串，如果是相邻字母是一样的话就称为一个桥，把桥之间的字符替换成加号。这个题看似简单，很多corner case，不好写，
	 * 到最后也没写出一个bug free的版本
	 * 
	 *  --a-d*-d-a-b 变成 --a-++++-a-b
	 */
	public static void bridge(String str) {
		
		int prev = 0;
		char prevChar = 0;
		char[] arr = str.toCharArray();
		
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(arr[i])) {
				if (prevChar == arr[i]) {
					for (int j = prev; j <= i; j++) {
						arr[j] = '+';
					}
				} else {
					prev = i;
					prevChar = arr[i];
				}
			} 
		}
		
		System.out.println(new String(arr));
	}
	
	
	public static void main(String[] args) {
		bridge("--a-d*-d-a-b");
		bridge("--a-d*-d-a-d");
		bridge("--a-d*-d-)(da-d");
	}
}
