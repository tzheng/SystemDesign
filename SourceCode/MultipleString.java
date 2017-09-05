
public class MultipleString {
	
	 public String multiply(String nums1, String nums2) {
		 StringBuilder sb = new StringBuilder();
		 
		 int n = nums1.length();
		 int m = nums2.length();
		 int[] res = new int[n+m];
		 
		 for (int i = n - 1; i >= 0; i--) {
			 for (int j = m - 1; j >= 0; j--) {
				 int mul = (nums1.charAt(i) - '0') * (nums2.charAt(j) - '0');
				 int p1 = i+j, p2 = i + j + 1;
				 
				 int sum = res[p2] + mul;
				 res[p2] = sum % 10;
				 res[p1] += sum / 10;
			 }
		 }
		 
		 for (int r : res) {
			 if (sb.length() == 0 && r == 0) {
				 continue;
			 }
			 sb.append(r);
		 }
		 
		 return sb.length() == 0 ? "0" : sb.toString();
	 }
	
	 public String multiply1(String nums1, String nums2) {
        int offset = 0;
        String result = "";
        for (int i = nums1.length() - 1; i>= 0; i--) {
            int digit_i = (int)(nums1.charAt(i) - '0');
            
            String curr = "";
            int carry = 0;
            for (int j = nums2.length() - 1; j >= 0; j--) {
                int digit_j = (int)(nums2.charAt(j) - '0');
                int res = digit_i * digit_j + carry;
//                if (res > 0) {
	                carry = res/10;
	                int mod = res % 10;
	                curr = String.valueOf(mod) + curr; 
//                }
            }
            if (carry != 0) {
                curr = carry + curr;
            }
            
            for (int k = 0; k < offset; k++) {
                curr = curr + "0";
            }
            result = addString(curr, result);
            offset++;
        }
        return result;
    }
	 
	public String addString(String s1, String s2) {
		int i = s1.length() - 1, j = s2.length() - 1;
		int carry = 0;
		String res = "";
		while (i >= 0 && j >= 0) {
			int tmp = (int)(s1.charAt(i) - '0') + (int)(s2.charAt(j) - '0') + carry;
			carry = tmp/10;
			int digit = tmp % 10;
			res = digit + res;
			i--;
			j--;
		}
		
		while (i >= 0) {
			int tmp = (int)(s1.charAt(i) - '0') + carry;
			carry = tmp/10;
			int digit = tmp % 10;
			res = digit + res;
			i--;
		}
		
		while (j >= 0) {
			int tmp = (int)(s2.charAt(j) - '0') + carry;
			carry = tmp/10;
			int digit = tmp % 10;
			res = digit + res;
			j--;
		}
		
		if (carry != 0) {
            res = carry + res;
        }
		
		return res;
	}
	 
	public static void main(String[] args) {
		MultipleString clz = new MultipleString();
//		System.out.println(Integer.valueOf(clz.addString("99", "129")) == 129+99);
//		System.out.println(Integer.valueOf(clz.addString("1234", "1")) == 1234+1);
//		System.out.println(Integer.valueOf(clz.addString("99", "0")) == 99);
//		System.out.println(Integer.valueOf(clz.addString("1234", "100")) == 1234+100);
//		
//		
//		System.out.println(Integer.valueOf(clz.multiply("99", "129")) == 129*99);
//		System.out.println(Integer.valueOf(clz.multiply("1234", "1")) == 1234*1);
//		System.out.println(Integer.valueOf(clz.multiply("99", "0")) == 0*99);
		System.out.println(Integer.valueOf(clz.multiply("129", "9")) == 129*9);
	}
	 
	 
}
