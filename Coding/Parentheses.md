# Generate Parentheses  - 括号问题

大致思路就是扫描一遍字符串，统计左括号和右括号的出现次数，或者用栈来存左右括号，遇到左括号入栈，遇到右括号出栈。然后根据题目情况做出相应操作。比如判断左右括号是不是匹配。

## 模板
如果长度不重要，只需要判断是否符合条件，只需要存一个open变量即可。 有的时候时候需要从左到右扫描，找出右括号多于左括号的情况，然后从右到左扫描，找出左括号多于右括号的情况。
```
// 从左到右
  for (char c : s.toCharArray()) {
     if (c == '(') {
         open++;
     } else if (c == ')') {
         if (open == 0) {
             // it means more close than open, return based on the question
         }
         open--;
     }
  }
// 从右到左和上面类似  
```

##### 20. Valid Parentheses
```
    public boolean isValid(String s) {
        if (s.length() % 2 == 1) return false;
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            int open = getOpen(c);
            if (open == c) {
                stack.push(c);
            } else {
                if (stack.isEmpty() || open != stack.peek()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
    
    private char getOpen(char c) {
        if (c == ')') {
            return '(';
        } else if (c == ']') {
                return '[';
        } else if (c == '}') {
               return '{';
        } else {
            return c;
        }
    }
```

或者单纯删除最外层括号。
##### 1021. Remove Outermost Parentheses
```
   public String removeOuterParentheses(String S) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : S.toCharArray()) {
            if (c == '(') {
                count++;
                if (count == 1) continue; 
            } else {
                count--;
                if (count == 0) continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
```


##### 1249. Minimum Remove to Make Valid Parentheses
```
// 从左到右扫描一边，解决）多出来的问题，然后反过来解决（多出来的问题
    public String minRemoveToMakeValid(String s) {
        String open = helper(s, '(', ')');
        String close = helper(new StringBuilder(open).reverse().toString(), ')', '(');
        return new StringBuilder(close).reverse().toString();
    }
    
    private String helper(String str, char open, char close) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == open) {
                count++;
            } else if (c == close) {
                if (count == 0) continue;
                count--;
            } 
            sb.append(c);
        }
        
        return sb.toString();
    }
```

##### 921. Minimum Add to Make Parentheses Valid
```
// 和remove 相反，需要加括号使其完整，求最小增加数，但是原理类似，只要左右扫描一次，算多出来多少即可
    public int minAddToMakeValid(String s) {
        int open = 0, close = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
            } else {
                if (open > 0) {
                    open--;
                } else {
                    close++;
                }
            }
        }
        return open + close;
    }

```

##### 32. Longest Valid Parentheses
```
// 同样是左右扫描，统计括号数量，当为0的时候做操作
public int longestValidParentheses(String s) {
        int open = 0, close = 0, max = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
            } else {
                close++;
            }
            if (open == close) {
                max = Math.max(open + close, max);
            } else if (open < close) {
                open = 0;  close = 0;            
            }
        }
        
        open = 0; close = 0;
        for (int i = s.length() -1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                close++;
            } else {
                open++;   
            }
            if (open == close) {
                max = Math.max(open + close, max);
            } else if (open > close) {
                open = 0;  close = 0;               
            }
        }
        return max;
    }

```

##### 22. Generate Parentheses
```
// time complexity O(4^n/sqrt(n)) , less than O(2^n)
   public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        helper(res, n, n, new StringBuilder());
        return res;
    }
    private void helper(List<String> res, int left, int right, StringBuilder sb) {
        if (left == 0 && right == 0) {
            res.add(sb.toString());
        }
        if (left > 0) {
            sb.append("(");
            helper(res, left-1, right, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (right > left) {
            sb.append(")");
            helper(res, left, right-1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
```    

##### 32. Longest Valid Parentheses
```
// 思路依然是左右各自扫描一边，然后统计左右括号出现的次数，如果左右相等，则找到一个解，如果右边大于左边，则说明当前位置之前的字串不可能有解，那么就要重置open/close count
    public int longestValidParentheses(String s) {
        int open = 0, close = 0, max = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                open++;
            } else {
                close++;
            }
            if (open == close) {
                max = Math.max(max, open + close);
            } else if (close > open) {
                open = 0; close = 0;
            }
        }
        open = 0; close = 0;
        for (int i = s.length() -1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')') {
                close++;
            } else {
                open++;
            }
            if (open == close) {
                max = Math.max(max, open + close);
            } else if (open > close) {
                open = 0; close = 0;
            }
        }
        return max;
    }

```


##### 241. Different Ways to Add Parentheses
```
// 把字符串根据运算符分组，直到不能分为止。
// time complexity O(2^n) exponential, for n is the number of operators in the input. 
// That's because each function call calls itself twice unless it has been recursed n times.
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '+' || c == '-' || c == '*') {
                String left = expression.substring(0, i);
                String right = expression.substring(i+1, expression.length());
                List<Integer> leftVal = diffWaysToCompute(left);
                List<Integer> rightVal = diffWaysToCompute(right);
                for (int l : leftVal) {
                    for (int r : rightVal) {
                        int ans = 0;
                        switch (c) {
                            case '+': ans = l + r;
                                break;
                            case '-': ans = l - r;
                                break;
                            case '*': ans = l * r;
                                break;
                        }
                        res.add(ans);
                    }
                }
            }
        }
        if (res.size() == 0) {
            res.add(Integer.valueOf(expression));
        }
        return res;
    }
```

##### 678. Valid Parenthesis String
```
    public boolean checkValidString(String s) {
        Stack<Integer> left = new Stack<>(), star = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                left.push(i);
            } else if (c == ')') {
                if (left.isEmpty() && star.isEmpty()) {
                    return false;
                } 
                if (!left.isEmpty()) {
                    left.pop();
                } else {
                    star.pop();
                }
            } else {
                star.push(i);
            }
        }
        
        while (!left.isEmpty() && !star.isEmpty()) {
            if (left.pop() > star.pop()) return false;
        }
        return left.isEmpty();
    }
```

##### 301. Remove Invalid Parentheses
```
 // 考虑到要求所有解，只能使用搜索来解决问题。
 // Time complexity: you have a length n string, every character have 2 states "keep/remove",
 // that is 2^n states and check valid is O(n). All together O(n*2^n).
     public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(s);
        visited.add(s);
        boolean found = false;
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (isValid(curr)) {
                res.add(curr);
                found = true;
            }
            if (found) continue;
            for (int i = 0; i < curr.length(); i++) {
                if (curr.charAt(i) != '(' && curr.charAt(i) != ')') continue;
                String next = curr.substring(0, i) + curr.substring(i+1, curr.length());
                if (visited.add(next)) {
                    queue.offer(next);
                }
            }
        }
        return res;
    }
    
    private boolean isValid(String s) {
        int open = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                open++;
            } else if (c == ')') {
                if (open == 0) return false;
                open--;
            }
        }
        return open == 0;
    }
// 使用dfs 比 bfs更快，事先统计好需要移除多少括号， 参考Minimum Remove to Make Valid Parentheses
// time complexity O(2^n) 
  public List<String> removeInvalidParentheses(String s) {
        int removeLeft = 0, removeRight = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                removeLeft++;
            } else if (c == ')') {
                if (removeLeft > 0) {
                    removeLeft--;
                } else {
                    removeRight++;
                }
            }
        }
        
        Set<String> res = new HashSet<>();
        helper(s, 0, res, new StringBuilder(), removeLeft, removeRight, 0);
        return new ArrayList<>(res);
    }
    
    private void helper(String s, int i, Set<String> res, StringBuilder sb, int left, int right, int open) {
        if (left < 0 || right < 0 || open < 0) 
            return;
        
        if (i == s.length()) {
            if (left == 0 && right == 0 && open == 0) {
                res.add(sb.toString());
            }
            return;
        }
        
        char c = s.charAt(i);
        if (c == '(') {
             helper(s, i+1, res, sb, left-1, right, open);    
            // append 
            sb.append(c);
            helper(s, i+1, res, sb, left, right, open+1);
            sb.deleteCharAt(sb.length() - 1);
        } else if (c == ')') {
            helper(s, i+1, res, sb, left, right - 1, open); 
            // append 
            sb.append(c);
            helper(s, i+1, res, sb, left, right, open-1);
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.append(c);
            helper(s, i+1, res, sb, left, right, open);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
```