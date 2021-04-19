# Random - 随机数
## 模板
#### 蓄水池抽样
给定一个数据流，数据流长度N可以是无限大，在保证只遍历一遍数据的情况下，能够随机选取出m个不重复的数据，每个选取的概率是m/N。
算法核心是首先把所有数据都放入m大小的蓄水池，当接受到arr[i] (i >= m)的时候，在[0...i]之间抽样，如果抽样 r < m， 则替换蓄水池中第r 个数字。  
##### 398. Random Pick Index
```
// reservoir sampling
    public int pick(int target) {
        int n = nums.length, count = 0, index = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == target) {
                count++;
                if (rand.nextInt(count) == 0) {
                    index = i;
                }
            }
        }
        return index;
    } 

// another solution is to use hash map
    private HashMap<Integer, List<Integer>> map;
    private Random rand;
    public Solution(int[] nums) {
        rand = new Random();
        map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], new ArrayList<>());
            }
            map.get(nums[i]).add(i);
        }
    }
    public int pick(int target) {
        int len = map.get(target).size();
        int r = map.get(target).get(rand.nextInt(len));
        return r;
    }
```
##### 382. Linked List Random Node
```
 public int getRandom() {
        int count = 1, choose = 0;
        ListNode node = head;
        while (node != null) {
            if (Math.random() < 1.0 / count) {
                choose = node.val;
            }
            count++;
            node = node.next;
        }
        return choose;
    }
```
#### 加权抽样
##### 528. Random Pick with Weight
```
    int[] prefixSums;
    int totalSum;
    public Solution(int[] w) {
        prefixSums = new int[w.length];
        totalSum = 0;
        for (int i = 0; i < w.length; i++) {
            totalSum += w[i];
            prefixSums[i] = totalSum;
        }
    }
    
    public int pickIndex() {
        double random = totalSum * Math.random();
        int i = 0;
        for (; i < prefixSums.length; i++) {
            if (random < prefixSums[i]) {
                return i;
            }
        }
        return i-1;
    }
```