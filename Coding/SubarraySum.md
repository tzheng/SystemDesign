# Subarray Sum  - 子数组和问题
通常需要使用prefix sum，如果是一维数组，prefix sum计算如下
```
    int n = nums.length;
    int[] sum = new int[n+1];
    for (int i = 0; i < n; i++) {
        sum[i+1] = sum[i] + nums[i];
    }
```

如果是二维数组，prefix sum计算如下
```
  for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
          sum[i+1][j+1] = matrix[i][j] + sum[i][j+1] + sum[i+1][j] - sum[i][j];
      }
  }
```
从数组（i，j）到（p，q）之间的和为 `sum[p+1][q+1]- sum[p+1][j] - sum[i][q+1] + sum[i][j]`

##### 1074. Number of Submatrices That Sum to Target 
```
// 把问题升级成二维数组
// 解法1 - 二维prefix sum， time complexity o(m^2*n^2)
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int[][] sum = new int[m+1][n+1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum[i+1][j+1] = matrix[i][j] + sum[i][j+1] + sum[i+1][j] - sum[i][j];
            }
        }
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int p = i; p < m; p++) {
                    for (int q = j; q < n; q++) {
                        int val = sum[p+1][q+1]- sum[p+1][j] - sum[i][q+1] + sum[i][j];
                        if (val == target) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }
// 解法2 - 降维成一维问题，对于每一行都计算一个prefix sum，然后对于每两个列，对每行prefix sum求和，问题转化为类似560的问题
// time complexity O(n^2 * m)
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int[][] sum = new int[m][n+1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum[i][j+1] = sum[i][j] + matrix[i][j];
            }
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                Map<Integer, Integer> map = new HashMap<>();
                map.put(0, 1);
                int currSum = 0;
                for (int k = 0; k < m; k++) {
                    currSum += sum[k][j+1] - sum[k][i];
                    count += map.getOrDefault(currSum - target, 0);
                    map.put(currSum, map.getOrDefault(currSum, 0) + 1);
                }       
            }
        }
        return count;
    }

```

##### 560. Subarray Sum Equals K
```
// 使用 prefix sum 解法，复杂度O(n^2)
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int[] sum = new int[n+1];
        for (int i = 0; i < n; i++) {
            sum[i+1] = sum[i] + nums[i];
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <= n; j++) {
                if (sum[j] - sum[i] == k) {
                    count++;
                }
            }
        }
        return count;
    }

 // 使用哈希表减少复杂度，原理类似2 sum，计算当前sum，
 // 寻找map里面是否存在 x = sum-k，如果存在说明 sum - x = k
    public int subarraySum(int[] nums, int k) {
        int sum = 0, count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int n : nums) {
            sum += n;
            int diff = sum - k;
            count += map.getOrDefault(diff, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    } 
```

##### 523. Continuous Subarray Sum
```
// 和560类似，但是增加了一些条件，返回是boolean，可以把map改为存index
    public boolean checkSubarraySum(int[] nums, int k) {
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int mod = sum % k;
            int index = map.getOrDefault(mod, i);
            if (i - index >= 2) {
                return true;
            }
            map.putIfAbsent(mod, i);
        }
        return false;
    }
```

##### 525. Contiguous Array
把题目条件换一换，把0换成-1，然后最长和为0的子串
```

```
