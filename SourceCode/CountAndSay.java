import java.util.ArrayList;
import java.util.Arrays;

public class CountAndSay {
	
	public void countAndSay(int n) {
		String str = "1";
        for (int i = 1; i < n; i++) {
           
            int count = 1;
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j < str.length(); j++) {
                if ((int)(str.charAt(j) - '0') != (int)(str.charAt(j-1) - '0')) {
                    count++;
                } else {
                    sb.append(String.valueOf(count) + String.valueOf(str.charAt(j-1)));
                    count = 1;
                }
            }
            sb.append(String.valueOf(count) + String.valueOf(str.charAt(str.length()-1)));
            str = sb.toString();
        }
         
        System.out.println(str);
	}
	
	/**
	 * FB 面经
	 * Findall characters that have most continuous appearances (longest sequence). 
	 * 比如： “this send meet” -> [s, e] 再比如：“this is pea” ->[t,h,i,s,i,s,p,e,a] 
	 * 这个题他只给出input和output让猜程序是干什么的。没有见过的话，可能需要稍微分析一下。是个新题？
	 * 我没在面经见过。 Tricky part: 注意skip空格
	 */
	public void continuousChar(String str) {
		str = str.trim();
		if (str == null || str.length() == 0) 
			return;
		
		char[] arr = str.toCharArray();
		int max = 1, count = 1;
		char prev = arr[0];
		ArrayList<Character> result = new ArrayList<>();
		
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] == ' ') continue;
			if (arr[i] == prev) {
				count++;
			} else {
				if (count > max) {
					result.clear();
					result.add(prev);
					max = count;
				} else if (count == max) {
					result.add(prev);
				}
				prev = arr[i];
				count = 1;
			}
		}
		
		System.out.println(Arrays.toString(result.toArray()));
	}
	
	public static void main(String[] args) {
        
		CountAndSay clz = new CountAndSay();
		clz.continuousChar("this send meet");
		clz.continuousChar("this is pea");
	}
}
