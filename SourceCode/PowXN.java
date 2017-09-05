
public class PowXN {
	 public double myPow(double x, int n) {
        if (n < 0) {
            return 1/pow(x, -n);
        } else {
            return pow(x, n);
        }
    }
    
    public double pow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        
        double val = pow(x, n/2);
        if (n %2 == 0) {
            return val * val;
        } else {
            return val * val * x;
        }
        
    }
	    
  public static void main(String[] args) {
	  PowXN clz = new PowXN();
	  System.out.println(clz.myPow(2, 3));
  }
}
