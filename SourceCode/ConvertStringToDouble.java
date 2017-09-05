import java.util.Random;

public class ConvertStringToDouble {
	
	/**
	 * 65. Valid Number(H)
	 * 先看一个判断是否
	 */
	public boolean isNumber(String s) {
		s = s.trim();
		int i = 0, n = s.length();
		if (n == 0) return false;
		int signCount = 0;
		boolean metP = false, metE = false, hasNum = false;
		
		for (; i < n; i++) {
			char c = s.charAt(i);
			
			if (!isValid(c)) return false;
			 
			if (Character.isDigit(c)) {
				hasNum = true;
				continue;
			}
			if (c == '.' ) {
				if (i == n-1 && !hasNum) return false;  // ".1" is valid,  "." is invalid
				if (metP || metE) return false;
				metP = true;
			}
			
			if (c == 'e' || c == 'E') {
				if (i == n-1) return false;   //  10e are invalid, must be 2e10
				if (metE || !hasNum) return false; // e is invalid,  ee is invalid
				metE = true;
			}
			
			if (c == '-' || c == '+') {
				if (signCount == 2) return false;
				if (i == n-1) return false;
				if (i > 0 && !(s.charAt(i-1) == 'E' || s.charAt(i-1)=='e')) 
					return false;
				signCount++;
			}
		}
		return true;
	}
	
	boolean isValid(char c) {
        return c == '.' || c == '+' || c == '-' || c == 'e' || c == 'E' || c >= '0' && c <= '9';
    }
	
	/**
	 * Follow up: 把string 转化成double ，假设string都是合法的。
	 */
	public double convert(String str) {
		double ret = 0.0;
		int sign = 1;
		int i = 0;
		if (str.charAt(0) == '-') {
			sign = -1;
			i = 1;
		}
		
		boolean metPoint = false;
		boolean metE = false;
		int decimal = 10;
		while (i < str.length()) {
			char c = str.charAt(i);
			if (c == '.') {
				metPoint = true;
				i++;
			} else if (c == 'e' || c == 'E') {
				i++;
				break;
			} else {
				if (!metPoint) {
					ret = ret * 10 + (int)(c - '0');
				} else {
					double tmp = (double)(c - '0');
					ret += tmp / decimal;
					decimal = decimal * 10;
				}
				i++;
			}
		}
		
		boolean negE = false;
		int power = 0;
		while (i < str.length()) {
			if (str.charAt(i) == '-') {
				negE = true;
			} else {
				power = power * 10 + (str.charAt(i) - '0');
			}
			i++;
		}
		
		for (int j = 0; j < power; j++) {
			if (negE) {
				ret /= 10;
			} else {
				ret *= 10;
			}
		}
		
		return ret * sign;
	}
	
	
	
	public static void main(String[] args) {
		ConvertStringToDouble clz = new ConvertStringToDouble();
		System.out.println(clz.convert("12.35e2"));
	}
}
