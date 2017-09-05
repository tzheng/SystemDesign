
public class BinarySearchPowSqrt {
	
	/**
	 * 326. Power of Three
	 * 231. Power of Two
	 * 342. Power of Four
	 * It has bit manimuplation version, but here we do logN version
	 * 
	 */
	public boolean isPowerOfK(int n, int k) {
        if (n > 1) {
        	while (n % k == 0) n /= k;
        }
        return n == 1;
    }
	
		
	/**
	 * 50. Pow(x, n)
	 * 
	 * if n < 0, we should return 1/pow(x, -n)
	 * 为什么要两个method，因为要处理n < 0, n = Integer.MIN_VALUE 的情况，不能就简单的 -n
	 */
	public double myPow(double x, int n) {
        if (n < 0) {
           return 1.0 / pow(x, n);
       } else {
           return pow(x, n);
       }
        
       //iterative, be careful N< 0
//        double res = 1;
//        int c = n;
//        while (n != 0) {
//     	   if (n %2 != 0) {
//     		   res *= x;
//     	   }
//     	   x *= x;
//     	   n /= 2;
//        }
//        return c < 0? 1/res : res;
    }
   
   private double pow(double x, int n) {
       if (n == 0) {
           return 1;
       }
       double y = pow(x, n / 2);
       if (n % 2 == 0) {
           return y * y;
       } else {//this includes cases like n % 2 == 1 && n % 2 == -1 !!! so the order of conditionals cannot be changed
           return y * y * x;
       }
   }
   
   
   
   public double myPowIter(double x, int n) { 
	   double res = 1;
       long absN = Math.abs((long)n);
       while(absN > 0) {
           if(absN % 2 != 0) 
               res *= x;
           absN = absN/2;
           x *= x;
       }
       return n < 0 ?  1/res : res;
   }
	
	/**
	 * 372. Super Pow
	 * Your task is to calculate a^b mod 1337 where a is a positive integer 
	 * and b is an extremely large positive integer given in the form of an array.
	 */
	public int superPow(int a, int[] b) {
		
		long res = 1;
		for (int i = 0; i < b.length; i++) {
			res = powWithMod(res, 10) * powWithMod((long)a, b[i]) % 1337;
		}
		return (int)res;
	}
	
	public long powWithMod(long x, int n) {
		if (n == 0) {
			return 1;
		}
		long val = powWithMod(x, n/2);
		if (n % 2 == 0) {
			return val * val % 1337;
		} else {
			return val * val * x % 1337;
		}
	}
	
	/**
	 * 69. Sqrt(x)
	 */
	public static int sqrt(int x) {
		long start = 1, end = x, res = 0;
		
		while (start <= end) {
			long mid = start + (end-start)/2;
			if (mid <= x/mid) {
				//注意，考虑到运算是整数，实际上我们是找最大的那个mid, mid*mid <= x,
				//比如 10 的sqrt 是 3， 下面那题isPerfectSquer() 就不一样了。
				res = mid; 
				start = mid+1;
			} else {
				end = mid-1;
			}
		}
		return (int) res;
		
	}
	
	public double sqrtDouble(double x) {
		if (x <= 0) {
            return 0;
        }
		double start = 1, end = x;
        if (x < 1.0) {
        	start = x; end = 1;
        }
        double e = 1e-15, res = 0;
        while (true) {
            double mid = start + (end - start) / 2.0;
            if (Math.abs(mid * mid - x) <= e) {
                return mid;
            } else if (mid * mid > x) {
                end = mid;
            } else {
                start = mid;
            }
        }
	}
	
	//复杂度  log(n)*f(n),  f(n) 和精度有关
	public double sqrtNewton(double x)  {  
        if(x<0) 
        	return Double.NaN; //NaN: not a number  
        
        double err = 1e-15; //极小值  
        
        double t = x;  
        while (Math.abs(t-x/t) > err*t) //t^2接近c, 防止小数  
            t = (x/t + t)/2.0;  
        return t;  
    }  
	
	
	/**
	 * 367. Valid Perfect Square
	 */
	public boolean isPerfectSquare(int num) {
        int low = 1, high = num;
       while (low <= high) {
           long mid = (low + high)/2;
           if (mid * mid == num) {
               return true;
           } else if (mid * mid < num) {
               low = (int)mid + 1;
           } else {
               high = (int)mid - 1;
           }
       }
       return false;
   }
	
	
	public static double findCubic(double x) {
        double start = Math.min(x, 0);
        double end = Math.max(x, 0);
        double e = 0.001;
        while (end - start > e) {
            double mid = start + (end - start) / 2.0;
            if (Math.abs(mid * mid * mid - x) < e*mid) {
                return mid;
            }
            else if (mid * mid * mid > x) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (end*end*end < x) 
        	return end;
        return start;
    }
	
	public static double findCubic2(double x) {
        double start = Math.min(x, 0);
        double end = Math.max(x, 0);
        double e = 0.0000000000000001;
        while (true) {
            double mid = start + (end - start) / 2.0;
            if (Math.abs(mid * mid * mid - x) <= e) {
                return mid;
            }
            else if (mid * mid * mid > x) {
                end = mid;
            }
            else {
                start = mid;
            }
        }
    }
	

	
	
	public static void main(String[] args) {
		sqrt(5);
		
		
		BinarySearchPowSqrt clz = new BinarySearchPowSqrt();
		System.out.println(clz.sqrtDouble(0.01));
		System.out.println(clz.sqrtNewton(0.01));
		System.out.println(clz.sqrtDouble(9));
		System.out.println(clz.sqrtNewton(9));
	}
}
