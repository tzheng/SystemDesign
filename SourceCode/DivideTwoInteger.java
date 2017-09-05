
public class DivideTwoInteger {
	public int divide(int dividend, int divisor) {
        if (divisor == 0) {
             return dividend >= 0? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        boolean isNegative = (dividend < 0 && divisor > 0) || 
                             (dividend > 0 && divisor < 0);
        long dvs = Math.abs((long)divisor);
        long dvd = Math.abs((long)dividend);
        long dvs_original = dvs;
        
        int i = 0;
        //找到a使x*2^a <= y < x*2^(a+1)，这一步对应程序里的while(dvs<<(i+1) <= dvd) i++;
        while (dvs << (i+1) <= dvd) i++;
        int res = 0;
        
        
        while (dvd >= dvs_original) {
            if (dvd >= dvs << i) {
                dvd -= dvs << i;
                res += 1 << i;
            }
            i--;
        }
        
        return isNegative ? -res : res;
    }
	
	
	public static void main(String[] args) {
		DivideTwoInteger clz = new DivideTwoInteger();
		clz.divide(44, 3);
	}
}
