# Palindrome Problems - 回文相关问题

## 模版
给一个字符串s，p[i][j] 表示字符串从 i -> j 是不是回文
 		p[i][i] = true;
 		p[i][j] = p[i+1][j-1] && s[i] == s[j] (i < j)
 		对于i,j来说，如果s[i] == s[j]， 而且他们围成的子串p[i+1][j-1]是回文那么他们也是回文 (注意 i-j <2, 也就是i,j 相邻的时候也是）

```
    boolean[][] p = new boolean[n][n];
    // 注意 i要从大到小， 因为 i+1要比i先计算出来
    for (int i = n-1; i >= 0; i --) {
        for (int j = i+1; j < n; j++) {
            p[i][j] = (p[i+1][j-1] || j - i <= 2) && s.charAt(i) == s.charAt(j);
        }
    }
```
有了 p[i][j] 之后，很多题目就可以直接利用它来判断一个区间是不是回文。 同时，如果把boolean换成int，还可以用作统计数据，比如Leetcode 1216. Valid Palindrome III，可以用来统计长度， 比如 516. Longest Palindromic Subsequence。



## 题解

##### 5. Longest Palindromic Substring 直接套用模版
```
  // time O(n^2)
    public String longestPalindrome(String s) {
        int max = 1, n = s.length(), maxIndex = 0;
        boolean[][] p = new boolean[n][n];
        for (int i = n-1; i >= 0; i--) {
            for (int j = i+1; j < n; j++) {
                p[i][j] = (p[i+1][j-1] || j - i <= 2) && s.charAt(i) == s.charAt(j);
                if (p[i][j]) {
                    int len = j - i + 1;
                    if (len > max) {
                        max = len;
                        maxIndex = i;
                    }
                }
            }
        } 
        return s.substring(maxIndex, maxIndex + max);
    }
```   

##### 516. Longest Palindromic Subsequence
```
// 同样可以利用模板
public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] p = new int[n][n];
        for (int i = n-1; i >= 0; i--) {
            p[i][i] = 1;
            for (int j = i+1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    p[i][j] = p[i+1][j-1] + 2;
                } else {
                    p[i][j] = Math.max(p[i+1][j], p[i][j-1]);
                }
            }   
        }
        return p[0][n-1];
    }
```


##### 125. Valid Palindrome

```
// time complexity O(n), space O(1)
    public boolean isPalindrome(String s) {
        if (s == null || s.length() < 2) return true;
        int i = 0, j = s.length() - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) i++;
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) j--;
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) {
                return false;
            }
            i++;  j--;
        }
        return true;
    }
```

##### 680. Valid Palindrome II

```
// time complexity O(n), space O(1)
public boolean validPalindrome(String s) {
        if (s == null || s.length() < 2) return true;
        
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return isPalindrome(s, i+1, j) || isPalindrome(s, i, j-1);
            } 
            i++; j--;
        }
        return true;
    }
    
    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }
```

##### 1216. Valid Palindrome III
```
// 1. brute force idea, time complexity
// f(n) = 2f(n-1) = 2 * 2 * f(n-2) = ... = 2^kf(n-k)
private boolean helperTLE(String s, int l, int r, int k) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                if (k == 0) {
                    return false;
                }
                return helperTLE(s, l+1, r, k-1) || helperTLE(s, l, r-1, k-1);
            }
            l++;
            r--;
        }
        return true;
    }

// 2. convert the problem to find min number of deletion to make it palindrome, and check min <= k?
// now the recursion looks like below. time complexity O(n^2)
    Integer[][] memo;
    private int helper(String s, int l, int r) {
        if (l == r) 
            return 0;
        if (l == r - 1) 
            return s.charAt(l) == s.charAt(r) ? 0 : 1;
        
        if (memo[l][r] != null) {
            return memo[l][r];
        } 
        int moves = 0;
        if (s.charAt(l) == s.charAt(r)) {
            moves = helper(s, l+1, r-1);
        } else {
            moves = Math.min(helper(s, l+1, r), helper(s, l, r-1)) + 1;
        }
        memo[l][r] = moves;
        return moves;
    }

// 3. 利用上面的模板, 只不过是把boolean 换成统计次数。
public boolean isValidPalindrome(String s, int k) {
        int n = s.length();
        int[][] memo = new int[n][n];
        for (int i = n-1; i >= 0; i--) {
            for (int j = i+1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    memo[i][j] = memo[i+1][j-1];
                } else {
                    memo[i][j] = Math.min(memo[i+1][j], memo[i][j-1]) + 1;
                }
            }
        }
        return memo[0][n-1] <= k;
    }
```



##### 131. Palindrome Partitioning
```
 // 套用模板 + recursion找所有解，
 // time complexity, consider worse case "aaaaaa", for each position it has two choice
 // 1. become a palindrome itself, and go next level, 2. become "aa", so total is 2^n
 // and substring take n,  overall it's O(n * 2^n)
 public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        int n = s.length();
        boolean[][] p = new boolean[n][n];
        for (int i = n-1; i >= 0; i--) {
            p[i][i] = true;
            for (int j = i+1; j < n; j++) {
                p[i][j] = (j-i <= 2 || p[i+1][j-1]) && s.charAt(i) == s.charAt(j);
            }
        }
        helper(p, s, 0, new ArrayList<>(), res);
        return res;
    }
    
    private void helper(boolean[][] p, String s, int start, List<String> path, List<List<String>> res) {
        if (start == p.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < p.length; i++) {
            if (p[start][i]) {
                path.add(s.substring(start, i+1));
                helper(p, s, i+1, path, res);
                path.remove(path.size() - 1);
            }
        }
    }
```



##### 9. Palindrome Number
```
// similar to 2 pointer, move on both side
    public boolean isPalindrome(int x) {
       if (x<0 || (x!=0 && x%10==0)) return false;

       int rev = 0;
       while (x>rev){
           rev = rev*10 + x%10;
           x = x/10;
       }
       return (x==rev || x==rev/10);
    }

```

##### 647. Palindromic Substrings
```
//1. 套用模板，需要额外空间
    public int countSubstrings(String s) {
        int count = 0, n = s.length();
        boolean[][] p = new boolean[n][n];
        for (int i = n-1; i >= 0; i--) {
            for (int j = i+1; j < n; j++) {
                p[i][j] = (j - i <= 2 || p[i+1][j-1]) && s.charAt(i) == s.charAt(j);
                if (p[i][j]) {
                    count++;
                }
            }
        }
        count += s.length();
        return count;
    }

//2. 对于每一个位置，直接从中心向两边展开，不需要额外空间
    public int countSubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            count += expand(s, i, i);
            count += expand(s, i, i+1);
        }
        return count;
    }
    private int expand(String s, int l, int r) {
        int count = 0;
        while (l >= 0 && r < s.length()) {
            if (s.charAt(l) != s.charAt(r)) break;
            l--; r++;
            count++;
        }
        return count;
    }   
```

##### 336. Palindrome Pairs
```
// brute force, but TLE, O(n^2 * m)  n = len of array, m = avg len of each word
    public List<List<Integer>> palindromePairs(String[] words) {
         List<List<Integer>> pairs = new ArrayList<>();
        // brute force
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i == j) continue;
                String combine = words[i] + words[j];
                String reverse = new StringBuilder(combine).reverse().toString();
                if (combine.equals(reverse)) {
                    pairs.add(Arrays.asList(i, j));
                }
            }
        }
        return pairs;
    }

// change a different way , O(m^2 * n)
  public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        for (int i = 0; i < words.length; i++) {
            String str = words[i];
            for (int j = 0; j <= str.length(); j++) {
                String left = str.substring(0, j);
                String right = str.substring(j);
                if (isPalindrome(left)) {
                    // check remaining of right in map or not
                    String rev = new StringBuilder(right).reverse().toString();
                    if (map.getOrDefault(rev, i) != i) {
                        result.add(Arrays.asList( map.get(rev), i));
                    }
                }
                if (isPalindrome(right)) {
                    String rev = new StringBuilder(left).reverse().toString();
                    if (map.getOrDefault(rev, i) != i && right.length() != 0) {
                        result.add(Arrays.asList(i, map.get(rev)));
                    }
                }
            }
        }
        return result;
    }

```