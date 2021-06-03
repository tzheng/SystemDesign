# Move Zero

##### 283	Move Zeroes
保持原来数组顺序
```
// Option 1: if there are lots of non-zeros in array, use this
// num of operations: nums.length, 
    public void moveZeroes(int[] nums) {
        int i = 0, j = 0;
        while (j < nums.length) {
            if (nums[j] != 0) {
                nums[i++] = nums[j];
            }
            j++;
        }
        while (i < nums.length) {
            nums[i++] = 0;
        }
    }
// Option 2: if there are a lot of 0, use this, bascially use two pointers, once find non-zero, swap with the one in the front
// num of operations: 2 * (num of non-zero)
    public void moveZeroes(int[] nums) {
        int i = 0, j = 0;
        while (j < nums.length) {
            if (nums[j] != 0) {
                int tmp = nums[j];
                nums[j] = nums[i];
                nums[i] = tmp;
                i++;
            }
            j++;
        }
    }   
```    

###### Follow up 1: 不要求保持顺序，要的是非0的个数 或者是不管后面的数字
```
public int moveZeroUnorder(int[] nums) {
    int l = 0, r = nums.length - 1;
    while (l < r) {
        while (l < r && nums[l] != 0) l++;
        while (l < r && nums[r] == 0) r--;
        if (l < r) {
            nums[l++] = nums[r--];
        } 
    }
    //!!!这里要注意，left和right 相遇的时候，有可能left是0
    if (nums[left] == 0 || left > right) { 
    	   return left;
    }
    //否则返回left + 1, e.g. {1,0,2,0,5,3,4}
    return left+1;
}
```

###### Follow up 2: 数组有多个数组，把所有0放到前面，所有1放到后面，其他在中间
```
public void moveZeroAndOne(int[] nums) {
    // first, move non-1 to the from
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != 1) {
            nums[index++] = nums[i];
        }
    }
    for (int i = index; i < nums.length; i++) {
        nums[i] = 1;
    }
    // move zero to the front
    index--;
    for (int i = index; i >= 0; i--) {
        if (nums[i] != 0) {
            nums[index--] = nums[i];
        }
    }
    while (index >= 0) {
        nums[index--] = 0;
    }
}
```