
public class SuperPower {
	 public int superPow(int a, int[] b) {
	        long res = 1;
	        
	        for (int i = 0; i < b.length; i++) {
	            //(a * b) % c = (a % c * b) * c
	            res = pow(res, 10) * pow((long)a, b[i]) % 1337;
	        }
	        return (int)res;
	    }
	    
	    public long pow(long x, int n) {
	        if (n == 0) {
	            return 1;
	        }
	        
	        if (n == 1) {
	            return x;
	        }
	        
	        return pow(x, n/2) * pow(x, n - n/2) % 1337;
	    }
}
