
public class Factorial {
	/**
	 * 172. Factorial Trailing Zeroes
	 * 核心就是算多少个5, 有多少个0在后面，取决于多少个10， 10 = 2*5
	 */
	public int trailingZeroes(int n) {
        int r = 0;
        while (n > 0) {
            n /= 5;
            r += n;
        }
        return r;
    }
	
	/**
	 * nth fibonacci mod 10   -  nth fibonacci mod m are periodic
	 * 
	 * https://medium.com/competitive/huge-fibonacci-number-modulo-m-6b4926a5c836#.7vwhcm736	
	 */
	public int getPisanoPeriod(int n, int m) {
		int a = 0, b = 1, c = a+b;
		int i = 0;
		for (; i < n; i++) {
			c = (a+b) % m;
			a = b;
			b = c;
			if (a == 0 && b == 1) return i+1;
		}
		return i;
	}
	
	public int getFibonacciModN(int n, int m) {
		int remainder = n % getPisanoPeriod(n, m);
		
		int first = 0, second = 1;
		int res = remainder;
		for (int i = 0; i < remainder-1; i++) {
			res = (first + second) % m;
			first = second;
			second = res;
		}
		return res % m;
	}
	
	public static void main(String[] args) {
		Factorial clz = new Factorial();
		System.out.println(clz.getPisanoPeriod(15, 2));
	}
}
