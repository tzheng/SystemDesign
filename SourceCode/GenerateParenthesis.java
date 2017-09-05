import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GenerateParenthesis {
	public List<String> generateParenthesis(int n) {
        List<String> result = new LinkedList<String>();
        if (n == 0) {
            return result;
        }
        
        Set<String> set = new HashSet<String>();
        result.add("");
        
        for (int i = 0; i < n; i++) {
            int size = result.size();
            for (int j = 0; j < size; j++) {
                String str = result.remove(0);
                
                for (int k = 0; k <= str.length(); k++) {
                    String newStr = str.substring(0, k) + "()" + str.substring(k, str.length());
                    if (set.contains(newStr)) continue;
                    set.add(newStr);
                    result.add(newStr);
                }
                
            }
        }
        
        // for (String str : set) {
        //     result.add(str);
        // }
        
        return result;
    }
	
	public static void main(String[] args) {
		GenerateParenthesis clz = new GenerateParenthesis();
		List<String> list = clz.generateParenthesis(3);
		for (String s : list) {
			System.out.println(s);
		}
	}
}
