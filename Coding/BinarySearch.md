# Binary Search - 二分查找
## 模版
根据情况可以分为几个模板。
#### 找特定值
```
 int l = 0, r = nums.length - 1;
 while (l <= r) {
        int mid = l + (r-l) / 2;
        if (arr[mid] == target)  {
            return mid;
        } else if (arr[mid] > target) {
            r = mid - 1;
        } else {
            l = mid + 1;
        }
}

```

#### 找下界（第一次出现）
```
    int l = 0, r = nums.length - 1;
    while (l < r) {
        int mid = l + (r-l) / 2;
        if (nums[mid] >= target) {
            // 满足条件的时候，把右边界设成mid，这样右边不断向左逼近，直到第一次出现
            r = mid;
        } else {
            // 不满足的时候，移动左边
            l = mid + 1;
        }
    } 
```
#### 找上界（最后一次出现）
```
    int l = 0, r = nums.length - 1;
    while (l < r) {
        // 注意！向右逼近的时候， mid 后面要+1，不然会死循环
        int mid = l + (r-l) / 2 + 1; 
        if (nums[mid] <= target) {
            // 满足条件的时候，把左边界设成mid，这样不断向右逼近，直到最后一次出现
            l = mid;
        } else {
            // 不满足的时候，移动右边
            r = mid - 1;
        }
    } 
```

##### 模板题 34. Find First and Last Position of Element in Sorted Array
```
public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        
        // binary search to find first
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r-l) / 2;
            if (nums[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        } 
        int first = l;
        if (nums[first] != target) {
            return new int[]{-1, -1};
        }
        
        l = 0; r = nums.length - 1;
        while (l < r) {
            int mid = l + (r-l)/2 + 1;
            if (nums[mid] <= target) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return new int[]{first, l};
    }   
```


