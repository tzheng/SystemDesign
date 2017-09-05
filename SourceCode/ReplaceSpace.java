
public class ReplaceSpace {
	
	public String encode(String str) {
		int space = 0;
		for (char c : str.toCharArray()) {
			if (c == ' ') space++;
		}
		
		char[] arr = new char[str.length() + space * 2];
		int index = arr.length - 1;
		for (int i = str.length()-1; i >= 0; i--) {
			if (str.charAt(i) == ' ') {
				arr[index--] = '0';
				arr[index--] = '2';
				arr[index--] = '%';
			} else {
				arr[index--] = str.charAt(i);
			}
		}
		return new String(arr);
	}
	
	public String decode(String str) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < str.length()) {
			if (str.charAt(i) == '%' && i+2 < str.length()) {
				if (str.charAt(i+1) == '2' && str.charAt(i+2) == '0') { 
					sb.append(' ');
					i += 3;
				} else {
					sb.append(str.charAt(i++));
				}
			} else {
				sb.append(str.charAt(i++));
			}
		}
		return sb.toString();
	}
	
	
	
	public static void main(String[] args) {
		ReplaceSpace clz = new ReplaceSpace();
		String encode = clz.encode("abc%20%20%2");
		System.out.println(encode);
		System.out.println(clz.decode(encode));
	}

}
