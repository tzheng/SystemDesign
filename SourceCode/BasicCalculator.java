import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BasicCalculator {
	
	/**
	 * 224. Basic Calculator
	 * 带括号的加减法
	 */
	public int calculate1(String s) {
		int result = 0;
		char[] arr = s.toCharArray();
		Stack<Integer> stack = new Stack(); 
		
		int currNum = 0;
		int sign = 1;
		for (char c : arr) {
			if (Character.isDigit(c)) {
				currNum = currNum * 10 + (int)(c - '0');
			} else if (c == '+') {
				result += sign * currNum;
				currNum = 0;
				sign = 1;
			} else if (c == '-') {
				result += sign * currNum;
				currNum = 0;
				sign = -1;
			} else if (c == '(') {
				stack.push(result);
				stack.push(sign);
				sign = 1;
				result = 0;
			} else if (c == ')') {
				result += sign * currNum;
				currNum = 0;
				result *= stack.pop();
				result += stack.pop();
			}
		}
		
		
		if (currNum != 0) result += sign * currNum;
		return result;
    }
	
	public int calculateRec(String s) {
		if (s == null || s.length() == 0) return 0;
		
		int result = 0, sign = 1, num = 0;
		int i = 0;
		while (i < s.length()) {
		    char c = s.charAt(i);
		    if (c == '(') {
		        int j =  findClose(s, i);
		        result += calculateRec(s.substring(i+1, j)) * sign;
		        i = j;
		    } else if (c == '+' || c == '-') {
		    	result += num * sign;
		    	num = 0;
		    	sign = c == '+' ? 1 : -1;
		    } else {
		    	num = num*10 + (int)(c-'0');
		    }
		    i++;
		}
		result += num * sign;
		return result;
    }
	private int findClose(String s, int i) {
		int j = i, count = 0;
		while (j < s.length()) {
			if (s.charAt(j) == '(') count++;
			if (s.charAt(j) == ')') count--;
			if (count == 0) return j;
			j++;
		}
		return s.length()-1;
	}
	
	/**
	 * 227. Basic Calculator II
	 * 没有括号，但是有乘除法。
	 */
	public int calculate2(String s) {
		int len;
	    if(s==null || (len = s.length())==0) return 0;
	    Stack<Integer> stack = new Stack<Integer>();
	    int num = 0;
	    char sign = '+';
	    for(int i=0;i<len;i++){
	        if(Character.isDigit(s.charAt(i))){
	            num = num*10+s.charAt(i)-'0';
	        }
	        if((!Character.isDigit(s.charAt(i)) &&' '!=s.charAt(i)) || i==len-1){
	        	switch (sign) {
	        		case '-': stack.push(-num); break;
	        		case '+': stack.push(num); break;
	        		case '*': stack.push(stack.pop() * num); break;
	        		case '/': stack.push(stack.pop() / num); break;
	        	}
	            sign = s.charAt(i);
	            num = 0;
	        }
	    }

	    int re = 0;
	    for(int i:stack){
	        re += i;
	    }
	    return re;
    }
	
	public int calculate3(String s) {
		int len;
	    if(s==null || (len = s.length())==0) return 0;
	    Stack<Integer> stack = new Stack<Integer>();
	    int num = 0;
	    char sign = '+';
	    for(int i=0;i<len;i++){
	        if(Character.isDigit(s.charAt(i))){
	            num = num*10+s.charAt(i)-'0';
	        }
	        if (!Character.isDigit(s.charAt(i)) && s.charAt(i) == '(') {
	        	int count = 1, j = i+1;
	        	while (j < s.length()) {
	        		if (s.charAt(j) == '(') count++;
	        		if (s.charAt(j) == ')') count--;
	        		if (count == 0) break;
	        		j++;
	        	}
	        	num = calculate3(s.substring(i+1, j));
	        	i = j+1;
	        }
	        if(i >= len-1 || (!Character.isDigit(s.charAt(i)) &&' '!=s.charAt(i))){
	        	switch (sign) {
	        		case '-': stack.push(-num); break;
	        		case '+': stack.push(num); break;
	        		case '*': stack.push(stack.pop() * num); break;
	        		case '/': stack.push(stack.pop() / num); break;
	        	}
	        	if (i < s.length())
	        		sign = s.charAt(i);
	            num = 0;
	        }
	    }

	    int re = 0;
	    for(int i:stack){
	        re += i;
	    }
	    return re;
    }
	

	public int calculate2ConstanceSpace(String s) {
		int result = 0;
        int currNum = 0, prevNum = 0;
        
        char sign = '+';
        int i = 0;
        
        while (i < s.length()) {
        	currNum = 0;
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                currNum = currNum * 10 + (int)(s.charAt(i) - '0');
                i++;
            }
            if (sign == '+' ) {
                result += prevNum;
                prevNum = currNum;
            } else if (sign == '-') { 
                result += prevNum;
                prevNum = -currNum;
            } else if (sign == '*') {
                prevNum = prevNum * currNum;
            } else if (sign == '/') {
               prevNum = prevNum / currNum;
            }
            
            if (i < s.length()) {
            	sign = s.charAt(i);
            	i++;
            }
        }
        return result + prevNum;
    }
	
	
	
	/**
	 * 282. Expression Add Operators
	 *  "123", 6 -> ["1+2+3", "1*2*3"] 
		"232", 8 -> ["2*3+2", "2+3*2"]
		"105", 5 -> ["1*0+5","10-5"]
		"00", 0 -> ["0+0", "0-0", "0*0"]
		"3456237490", 9191 -> []
		
	 * 关键问题就在于怎么处理 * ，我们看 1+2*3, 实际上可以先算加 1+2 = 3, 
	 * 然后到了乘法的时候，先把当前结果减去前一位加的数字, 3-2 =1, 然后用前一位数字2 去乘以当前数字 
	 * 
	 * 1. 乘号之前是加号或减号，例如2+3*4，我们在2那里算出来的结果，到3的时候会加上3，计算结果变为5。
	 *    在到4的时候，因为4之前我们选择的是乘号，这里3就应该和4相乘，而不是和2相加，所以在计算结果时，
	 *    要将5先减去刚才加的3得到2，然后再加上3乘以4，得到2+12=14，这样14就是到4为止时的计算结果。
	 *    
	 * 2. 另外一种情况是乘号之前也是乘号，如果2+3*4*5，这里我们到4为止计算的结果是14了，然后我们到5的
	 * 	  时候又是乘号，这时候我们要把刚才加的3*4给去掉，然后再加上3*4*5，也就是14-3*4+3*4*5=62。
	 *    这样5的计算结果就是62。
	 * 
	 * Each digit has four different options: +, -, *, nothing.
	 * Time Complexity - (4n)， Space Complexity - (4n)
	 */
	public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<String>();
        helper(num, 0, target, (long)0, (long)0, result, "");
        return result;
	}
	
	private void helper(String str, int pos, int target, long sum, long prevNum, List<String> result, String path) {
		if (pos == str.length()) {
			if (sum == target) {
				result.add(path);
			}
			return;
		}
		
		for (int i = pos; i < str.length(); i++) {
			if (str.charAt(pos) == '0' && i != pos) break;
			long num = Long.parseLong(str.substring(pos, i+1));
			if (pos == 0) {
				helper(str, i+1, target, sum + num, num, result, path + num);
			} else {
				helper(str, i+1, target, sum + num, num, result, path + "+" + num);
				helper(str, i+1, target, sum - num, num, result, path + "-" + num);
				helper(str, i+1, target, sum-prevNum + prevNum*num , prevNum*num, result, path + "*" + num);
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		BasicCalculator clz = new BasicCalculator();
//		System.out.println(clz.calculateRec("(1+(4+5+2)-3)+(6+8)"));
		//System.out.println(clz.calculate2ConstanceSpace("4+2*3"));
		clz.addOperators("105", 5);
		
		System.out.println(clz.calculate3("2*(2+(3-1))-4"));
	}
}
