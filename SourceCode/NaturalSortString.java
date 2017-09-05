import java.util.Comparator;

public class NaturalSortString {
//	https://github.com/paour/natorder/blob/master/NaturalOrderComparator.java
		
	class NaturalOrderComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			
			int ia = 0, ib = 0;
			
			while (ia < a.length() || ib < b.length()) {
				char ca = charAt(a, ia);
	            char cb = charAt(b, ib);
	            while (Character.isSpaceChar(ca) || ca == '0') {
	            	 ca = charAt(a, ++ia);
	            }
	            while (Character.isSpaceChar(cb) || cb == '0') {
	            	 cb = charAt(b, ++ib);
	            }
	           
				if (Character.isDigit(ca) && Character.isDigit(cb)) {
					int compareNum = compareRight(a.substring(ca), b.substring(cb));
					if (compareNum != 0) {
						return compareNum;
					}
				}
				
				if (ca != cb) {
		            return ca - cb;
		        }
		            
				ia++;
				ib++;
			}
			
			return a.length() - b.length();
		}
		
	}
	
	char charAt(String s, int i) {
        return i >= s.length() ? 0 : s.charAt(i);
    }
	
	public int compareRight(String a, String b) {
		 int bias = 0, ia = 0, ib = 0;

	        // The longest run of digits wins. That aside, the greatest
	        // value wins, but we can't know that it will until we've scanned
	        // both numbers to know that they have the same magnitude, so we
	        // remember it in BIAS.
	        for (;; ia++, ib++)
	        {
	            char ca = charAt(a, ia);
	            char cb = charAt(b, ib);

	            if (!Character.isDigit(ca) && !Character.isDigit(cb)) {
	                return bias;
	            }
	            if (!Character.isDigit(ca)) {
	                return -1;
	            }
	            if (!Character.isDigit(cb)) {
	                return +1;
	            }
	            if (ca == 0 && cb == 0) {
	                return bias;
	            }

	            if (bias == 0) {
	                if (ca < cb) {
	                    bias = -1;
	                } else if (ca > cb) {
	                    bias = +1;
	                }
	            }
	        }
	}
	
	
	
	
	/**
	 *    循环往后走，
	 * 	    如果（两个字符相等）而且（都不是数字），继续往后走。
	 * 		else 如果（都是数字）如果两个字符都是数字，往后走计算数字大小，如果数字大小不同，返回差值，
	 * 		else 如果（两个字符不同）如果有一个是数字，那另外一个就不是数字，那么有数字的比较小。如果都不是数字，说明两个字母不同，返回差值。
	 * 	  如果以上条件都通过了还没有返回，那么说明有字符串已经走到结尾了，而且当前都匹配。
	 * 		如果两个都走到结尾了，返回0
	 * 		else 比较长的那个放后面。
	 */
	public static Comparator<String> NaturalComparator = new Comparator<String>() {
	       @Override
	       public int compare(String str1, String str2) {
	           int index1 = 0;
	           int index2 = 0;
	           
	           while (index1 < str1.length() && index2 < str2.length()) {
	               char letter1 = str1.charAt(index1);
	               char letter2 = str2.charAt(index2);
	               if (letter1 == letter2 &&  !Character.isDigit(letter2)) { // both are char and equals, skip
	                   index1++;
	                   index2++;
	               } else if (Character.isDigit(letter1) && Character.isDigit(letter2)){ //both are number
	                   int number1 = 0, number2 = 0;
	                   while (index1 < str1.length() && Character.isDigit(str1.charAt(index1))) {//avoid overflow? add count < 10 here
	                       number1 = number1 * 10 + str1.charAt(index1++) - '0';
	                   }
	                   while (index2 < str2.length() && Character.isDigit(str2.charAt(index2))) {//avoid overflow? add count < 10 here
	                       number2 = number2 * 10 + str2.charAt(index2++) - '0';
	                   }
	                   
	                   if (number1 > number2) {//number in str2 is smaller, so it should be before str1
	                       return 1;
	                   } else if (number1 < number2) {
	                       return -1;
	                   }
	               } else { // not equals or one is number
	                   if (Character.isDigit(letter2)) {//number should be before letter
	                       return 1;
	                   } else if (Character.isDigit(letter1)) {
	                       return -1;
	                   }
	                   return letter1 - letter2;
	               }
	           }
	           
	           
	           if (index1 == str1.length() && index2 == str2.length()) {//check all the char already and all the same
	               return 0;
	           }
	           else if (index1 < str1.length()) {//str2 is shorter, so it should be before str1
	               return 1;
	           }
	           return -1;
	       }
	   };

}
