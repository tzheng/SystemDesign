import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class RemoveInvalidParentheses {
	public ArrayList<String> removeInvalidParentheses(String s) {
        // A BFS solution
		ArrayList<String> result = new ArrayList<String>();
		Queue<String> queue = new LinkedList<String>();
		Set<String> visited = new HashSet<String>();
        
        queue.offer(s);
        visited.add(s);
        
        boolean found = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (isValid(curr)) {
                    result.add(curr);
                    found = true;
                    continue;
                }
                //
                if (found) continue;
                for (int j = 0; j < curr.length(); j++) {
                    // if (j != 0 && curr.charAt(j) == curr.charAt(j-1)) continue;
                    if (curr.charAt(j) != '(' && curr.charAt(j) != ')') continue;
                    
                    String nextStr = curr.substring(0, j) + curr.substring(j+1);
                    // if (j < curr.length()) {
                    //     nextStr += curr.substring(j+1, curr.length());
                    // }
                    if (visited.contains(nextStr)) {
                        continue;
                    }
                    queue.offer(nextStr);
                    visited.add(nextStr);
                }
            }
        }
        
        return result;
        
    }
    
    private boolean isValid(String str) {
        Stack<Character> stack = new Stack<Character>();
        int i = 0;
        while (i < str.length()) {
            char c = str.charAt(i);
            if ( c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
            i++;
        }
        return stack.isEmpty();
    }
    
    public static void main(String[] args) {
    	RemoveInvalidParentheses cls = new RemoveInvalidParentheses();
    	cls.removeInvalidParentheses("()())()");
    }
}
