
public class BitPalindrome {
	public static void main(String args[]) {

		int x = 5;
		int m = 0, k = x;

		while (k != 0) {
			m = m << 1;
			m = m | (k & 1);
			k = k >> 1;
		}
		System.out.println((m ^ x) == 0 ? true : false);
	}
}
