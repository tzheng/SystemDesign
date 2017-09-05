import java.util.ArrayList;

public class StringSubset {

	public void subset1(String str) {
		ArrayList<String> set = new ArrayList<String>();
		helper(0, set, str);
	}
	
	private void helper(int pos, ArrayList<String> set, String str) {
		if (pos == str.length()) {
			for (int i = 0; i < set.size(); i++) {
				System.out.print(set.get(i) + " ");
			}
			System.out.println();
		}
		
		for (int i = pos; i < str.length(); i++) {
			String curr = str.substring(pos, i+1);
			set.add(curr);
			helper(i+1, set, str);
			set.remove(set.size() - 1);
		}
	}
	
	public void subset2(String str) {
		ArrayList<String> set = new ArrayList<String>();
		helper2(0, set, str);
	}
	
	private void helper2(int pos, ArrayList<String> set, String str) {
		if (pos == str.length()) {
			return;
		}
		
		for (int i = pos; i < str.length(); i++) {
			set.add(String.valueOf(str.charAt(i)));
			for (int j = 0; j < set.size(); j++) {
				System.out.print(set.get(j) + " ");
			}
			System.out.println();
			helper2(i+1, set, str);
			set.remove(set.size() - 1);
		}
	}
	
	String[] symbols = {"+", "-", "*"};
	public void combination(int len) {
		ArrayList<String> comb = new ArrayList<String>();
		helper3(0, comb, len);
	}
	
	private void helper3(int pos, ArrayList<String> comb, int len) {
		if (pos == len) {
			int res = 0;
			for (int i = 0; i < comb.size(); i++) {
				
				System.out.print(comb.get(i) + " ");
			}
			System.out.println();
			return;
		}
		
		for (int i = 0; i < symbols.length; i++) {
			comb.add(symbols[i]);
			helper3(pos+1, comb, len);
			comb.remove(comb.size() - 1);
		}
	}
	
	
	public static void main(String[] args) {
		StringSubset clz = new StringSubset();
		clz.subset1("abc");
		clz.subset2("123");
		clz.combination(2);
	}
}
