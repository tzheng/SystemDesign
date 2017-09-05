import java.util.HashMap;
import java.util.Map;

public class IntegerToEnglish {
	
	/**
	 * 273. Integer to English Words
	 * 这种题目完全是无聊，考察细节。注意一些corner case,  101, 100,000,  10,001 ...
	 */
	String[] units = {"", "Thousand", "Million", "Billion"};
	String[] numbers = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
			               "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
			               "Eighteen", "Nineteen"};
	String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    public String numberToWords(int tmp) {
        String res = "";
    	int level = 0;
    	while (tmp > 0) { 
    		int remainder = tmp % 1000;
    		if (remainder > 0) { //只有 >0 才加，否则会多加空格
    			res = convertThousand(remainder) + " " + units[level] + (res.length() == 0 ? "": " ") + res;
    		}
    		tmp = tmp / 1000;
    		level++;
    	}
    	
    	//注意要trim()
    	return res.trim().equals("")? "Zero" : res.trim();
    }
    
    private String convertThousand(int num) {
        String str = "";
        int h = num / 100;
        int ten = num % 100;
        if (ten > 0 && ten < 20) {
            str = numbers[ten];
        } else {
            int digit_ten = ten / 10;
            int digit_one = ten % 10;
            if (digit_ten > 0) {
                str += tens[digit_ten];
            }
            if (digit_one > 0) {
                str += " " + numbers[digit_one];
            }
        }
        
        if (h > 0) {
            str = numbers[h] + " " + "Hundred" + (str.length() == 0 ? "" : " ") + str;
        }
        return str;
    }
    
    /**
     * Followup: 中间加入了逗号和and。因为时间不多了，就没有让写转换3位数的function，不知道这里有没有扣分。
     * 每3位加一个逗号, 然后百位和十位或个位之间加and
     */
    public String numberToWordsComma(String input) {
        String res = "";
    	int level = 0;
    	String[] split = input.split(",");
    	
    	for (int i = split.length-1; i >= 0; i--) {
    		int remainder = Integer.valueOf(split[i]);
    		if (remainder > 0) { //只有 >0 才加，否则会多加空格
    			res = convertThousandAnd(remainder) + " " + units[level] + (res.length() == 0 ? "": " ") + res;
    		}
    		level++;
    	}
    	
    	//注意要trim()
    	return res.trim().equals("")? "Zero" : res.trim();
    }
    private String convertThousandAnd(int num) {
        String str = "";
        int h = num / 100;
        int ten = num % 100;
        if (ten > 0 && ten < 20) {
            str = numbers[ten];
        } else {
            int digit_ten = ten / 10;
            int digit_one = ten % 10;
            if (digit_ten > 0) {
                str += tens[digit_ten];
            }
            if (digit_one > 0) {
                str += " " + numbers[digit_one];
            }
        }
        
        if (h > 0) {
            str = numbers[h] + " " + "Hundred" + (str.length() == 0 ? "" : " AND ") + str;
        }
        return str;
    }
    
    
    
    /**
     * 13. Roman to Integer
     * 思路就是从倒数第二位开始，从后往前走，如果当前数字比后面数字大或者相等， 比如 VII， 说明要加上当前数字。，
     * 如果当前数字比后面数字小，比如 IIV， 说明要减去当前数字。
     */
    public int romanToInt(String s) {
        if (s == null || s.length()==0) return 0;
        Map<Character, Integer> m = new HashMap<Character, Integer>();
        m.put('I', 1);
        m.put('V', 5);
        m.put('X', 10);
        m.put('L', 50);
	    m.put('C', 100);
	    m.put('D', 500);
	    m.put('M', 1000);
	    
	    int len = s.length();
	    int res = m.get(s.charAt(s.length()-1));
	    for (int i = len-2; i >= 0; i--) {
	    	if (m.get(s.charAt(i+1)) <= m.get(s.charAt(i))) {
	    		res += m.get(s.charAt(i));
	    	} else {
	    		res -= m.get(s.charAt(i));
	    	}
	    }
	    return res;
    }
    
    /**
     * 12. Integer to Roman
     * 建立一个数字对应字母的表格，注意要包括 4，9 这种特殊情况
     */
    public String intToRoman(int num) {
	    int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
	    String[] strs = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
	    StringBuilder sb = new StringBuilder();
	    
	    for (int i = 0; i < values.length; i++) {
	    	while (num >= values[i]) {
	    		num -= values[i];
	    		sb.append(strs[i]);
	    	}
	    }
	    
	    return sb.toString();
    }
   
    public static void main(String[] args) {
    	IntegerToEnglish clz = new IntegerToEnglish();
    	clz.numberToWords(100000);
    	
    	System.out.println(clz.numberToWordsComma("12,345"));
    	System.out.println(clz.numberToWordsComma("10,005"));
    	System.out.println(clz.numberToWordsComma("2,305"));
    }
}
