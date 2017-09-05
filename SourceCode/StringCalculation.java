import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class StringCalculation {
	/**
	 * 67. Add Binary
	 * Follow up: 如果改成其他进制怎么办？
	 * 1. 把题目中的base修改了
	 * 2. 注意如果大于10进制，要把 sum % base的结果对应成字母。
	 */
	public String addBinary(String a, String b) {
        if (a == null || a.length() == 0) return b;
		if (b == null || b.length() == 0) return a;
		int i = a.length() - 1, j = b.length() - 1;
		int carry = 0, base = 2;
		StringBuilder sb = new StringBuilder();
		while (i >= 0 || j >= 0 || carry != 0) {
			if (j >= 0) carry += b.charAt(j--) - '0';
			if (i >= 0) carry += a.charAt(i--) - '0';
			sb.append(carry % base);
			carry = carry / base;
		}
		return sb.reverse().toString();
    }
	
	/**
	 * Followup 2: k个binary相加, 二分就好
	 */
	public String addBinary(String[] strs, int start, int end) {
		if (start > end) return "";
		if (start == end) return strs[start];
		int mid = start + (end-start)/2;
		String left = addBinary(strs, start, mid);
		String right = addBinary(strs, mid+1, end);
		return addBinary(left, right);
	}
	
	/**
	 * 43. Multiply Strings
	 */
	public String multiply(String nums1, String nums2) {
		StringBuilder sb = new StringBuilder();
		int n = nums1.length(), m = nums2.length();
		int[] res = new int[n+m];
		
		for (int i = n-1; i >= 0; i--) {
			for (int j = m-1; j >= 0; j--) {
				int mul = (nums1.charAt(i) - '0') * (nums2.charAt(j) - '0');
				int p1 = i + j + 1, p2 = i + j; //这里是关键，乘法只会更新这两个位置。
				mul = res[p1] + mul; //第一个位置要加上原有的值
				res[p1] = mul % 10;  
				res[p2] += mul / 10;
			}
		}
		
		for (int r : res) {
			if (sb.length() == 0 && r == 0) 
				continue;
			sb.append(r);
		}
		
		return sb.length() == 0 ? "0" : sb.toString();
	}
	
	/**
	 * 2. Add Two Numbers
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode prev = new ListNode(0);
        ListNode head = prev;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            ListNode cur = new ListNode(0);
            int sum = ((l2 == null) ? 0 : l2.val) + ((l1 == null) ? 0 : l1.val) + carry;
            cur.val = sum % 10;
            carry = sum / 10;
            prev.next = cur;
            prev = cur;
            
            l1 = (l1 == null) ? l1 : l1.next;
            l2 = (l2 == null) ? l2 : l2.next;
        }
        return head.next;
	}
	
	/**
	 * 445. Add Two Numbers II  
	 * 和1反过来，现在list的第一个是最大的
	 */
	public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
       Stack<Integer> s1 = new Stack<Integer>();
       Stack<Integer> s2 = new Stack<Integer>();
       while(l1 != null) {
           s1.push(l1.val);
           l1 = l1.next;
       };
       while(l2 != null) {
           s2.push(l2.val);
           l2 = l2.next;
       }
       
       ListNode head = null;
       int carry = 0;
       while (!s1.empty() || !s2.empty() || carry > 0) {
           if (!s1.empty()) carry += s1.pop();
           if (!s2.empty()) carry += s2.pop();
           ListNode curr = new ListNode(carry%10);
           curr.next = head;
           head = curr;
           carry = carry / 10;
       }
       return head;
   }
	
	
	/**
	 * 66. Plus One
	 */
	public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) 
        	return new int[]{1};
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
        	int sum = digits[i] + carry;
        	digits[i] = sum % 10;
        	carry = sum / 10;
        }
        
        if (carry != 0) {
        	int[] res = new int[digits.length + 1];
        	res[0] = carry;
        	System.arraycopy(digits, 0, res, 1, digits.length);
        	return res;
        } else {
        	return digits;
        }
    }
	
	/**
	 * 415. Add Strings
	 */
	public String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() -1, carry = 0;
        while (i >= 0 || j >= 0 || carry != 0) {
            int n1 = i >= 0?num1.charAt(i) - '0' : 0;
            int n2 = j >= 0?num2.charAt(j) - '0' : 0;
            int sum = n1 + n2 + carry;
            carry = sum/10;
            sum = sum % 10;
            res.append(sum);
            i--;
            j--;
        }
        
        return res.reverse().toString();
    }
	
	/**
	 * 166. Fraction to Recurring Decimal
	 * 要考虑负数的情况，要考虑重复数字不是从小数点后面第一位开始的情况  比如 1/6
	 */
	public String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) return "0";
        
        StringBuilder res = new StringBuilder();
        if ((numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0)) {
           res.append("-");
       }
       long num = Math.abs((long)numerator);
       long den = Math.abs((long)denominator);
       
       
       // integral part
       res.append(num / den);
       num %= den;
       if (num == 0) {
           return res.toString();
       }
       
       // fractional part
       res.append(".");
       HashMap<Long, Integer> map = new HashMap<Long, Integer>();
       map.put(num, res.length());
       while (num != 0) {
           num *= 10;
           res.append(num / den);
           num %= den;
           if (map.containsKey(num)) {
               int index = map.get(num);
               res.insert(index, "(");
               res.append(")");
               break;
           }
           map.put(num, res.length());
       }
       return res.toString();
	}
	
	
	/**
	 *  29. Divide Two Integers
	 */
	
	public int divide(int dividend, int divisor) {
		long result = divideLong(dividend, divisor);
		return result > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)result;
	}

	// It's easy to handle edge cases when
	// operate with long numbers rather than int
	public long divideLong(long dividend, long divisor) {
//		// Remember the sign
//		boolean negative = dividend < 0 != divisor < 0;
//		
//		// Make dividend and divisor unsign
//		if (dividend < 0) dividend = -dividend;
//		if (divisor < 0) divisor = -divisor;
//		
//		// Return if nothing to divide
//		if (dividend < divisor) return 0;
//		
//		// Sum divisor 2, 4, 8, 16, 32 .... times
//	    long sum = divisor;
//	    long divide = 1;
//	    while ((sum+sum) <= dividend) {
//	    	sum += sum;
//	    	divide += divide;
//	    }
//	    
//	    // Make a recursive call for (devided-sum) and add it to the result
//	    return negative ? -(divide + divideLong((dividend-sum), divisor)) :
//	    	(divide + divideLong((dividend-sum), divisor));
		
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
       
       int i = 0;
       //找到a使x*2^a <= y < x*2^(a+1)，这一步对应程序里的while(dvs<<(i+1) <= dvd) i++;
       while (dvs << (i+1) <= dvd) i++;
       int res = 0;
       
       while (dvd >= dvs) {
           if (dvd >= dvs << i) {
               dvd -= dvs << i;
               res += 1 << i;
           }
           i--;
       }
     
       
       return isNegative ? -res : res;
	}
	
	
	public static void main(String[] args) {
		StringCalculation clz = new StringCalculation();
//		System.out.println(clz.addBinary("11", "1"));
		
		String[] strs = {"1", "11", "10", "11", "100"};
//		System.out.println(clz.addBinary(strs, 0, strs.length-1));
//		
//		System.out.println(Arrays.toString(clz.plusOne(new int[]{9,8})));
//		
		System.out.println(clz.fractionToDecimal(1, 7));
//		System.out.println(clz.fractionToDecimal(2, 3));
//		System.out.println(clz.fractionToDecimal(2, 1));
//		System.out.println(clz.fractionToDecimal(1, 2));
		System.out.println(clz.fractionToDecimal(1, 6));
		
		System.out.println(clz.divide(123, 2));
		
		ListNode l1 = new ListNode(7);
		l1.next = new ListNode(2);
		l1.next.next = new ListNode(4);
		l1.next.next.next = new ListNode(3);
		
		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(4);
		
		clz.addTwoNumbers2(l1, l2);
	}
}
