
public class Dearrangement {
	/**
	 * A "derangement" of a sequence is a permutation where no element appears in its original position. 
	 * For example ECABD is a derangement of ABCDE, given a string, may contain duplicate char, please out put all the derangement 
	 */
	
	public static void helper(char[] a, boolean[] used, StringBuilder sb) {
		if (sb.length() == a.length) {
			System.out.println(sb.toString());
			return;
		}
		
		for (int i = 0; i < a.length; i++) {
			if (used[i] || i == sb.length()) continue;
			sb.append(a[i]);
			used[i] = true;
			helper(a, used, sb);
			sb.deleteCharAt(sb.length()-1);
			used[i] = false;
		}
	}
	
	/**
	 * Leetcode 634 Find the Derangement of an array 
	 */
	public int findDerangement(int n) {
        if (n == 1) return 0;
        if (n == 2) return 1;
        long[] array = new long[n];
        array[0] = 0;
        array[1] = 1;

        for (int i = 2; i < n; i++) 
            array[i] = (array[i - 1] + array[i - 2])%1000000007 ;

        return (int)array[n - 1];

    }
	
	public static void main(String[] args) {
		char[] a = "ABCD".toCharArray();
		boolean[] used = new boolean[a.length];
		helper(a, used, new StringBuilder());
	}
}
