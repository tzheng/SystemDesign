

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class ReservoirSample {
	/**
	 *  水塘抽样，思路理解以后就非常简单了。
	 * 398. Random Pick Index
	 * 
	 * 在数组里面随机抽取某个数字，不能用额外空间存储。
	 * int[] nums = new int[] {1,2,3,3,3};
		// pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
	 */
    public int pick(int[] nums, int target) {
    	Random random = new Random();
    	int res = -1, count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                count++;
                int r = random.nextInt(count);
                if (r == count-1) res = i;
            }
        }
        return res;
    }
	
    /**
     * 382. Linked List Random Node Add to List
     */
    public int getRandom(ListNode head) {
    	int res = -1, count = 0;
    	Random random = new Random();
    	while (head != null) {
    		count++;
    		int r = random.nextInt(count);
    		if (r == count - 1) 
    			res = head.val;
    		head = head.next;
    	}
    	return res;
    }
    
    /**
     * 上面两个是水塘抽样，下面来看洗牌算法。
     * 首先看简单的洗牌算法，就是每张牌概率相等
     * 384. Shuffle an Array
     * 
     * 思路就是数组从后往前遍历，对于每个坐标j，获取nextInt(j+1), 然后和这个位置的数字交换。
     * 
     * 来计算一下概率。如果某个元素被放入第i（1≤i≤n1≤i≤n ）个位置，
     * 对于nums[i],洗牌后在第n-1个位置的概率是1/n（第一次交换的随机数为i）
		在n-2个位置概率是[(n-1)/n] * [1/(n-1)] = 1/n，（第一次交换的随机数不为i，第二次为nums[i]所在的位置（注意，若i=n-1，第一交换nums[n-1]会被换到一个随机的位置））
		在第n-k个位置的概率是[(n-1)/n] * [(n-2)/(n-1)] *...* [(n-k+1)/(n-k+2)] *[1/(n-k+1)] = 1/n
		第一个随机数不要为i，第二次不为nums[i]所在的位置(随着交换有可能会变)……第n-k次为nums[i]所在的位置
     */
    public int[] shuffle(int[] nums) {
        int[] shuffle = nums.clone();
        Random random = new Random();
        for (int i = shuffle.length - 1;  i > 0; i--) {
            int r = random.nextInt(i+1);
            int tmp = shuffle[i];
            shuffle[i] = shuffle[r];
            shuffle[r] = tmp;
        }
        return shuffle;
    }
    
    /**
     * 来看一个加了权重的随机选择。
     * 给一个数组，每个元素有一个概率，写一个函数按照每个元素的概率每次返回一个元素。
     * 比如1：0.2，2：0.3，3：0.5    返回1的概率是0.2，返回3的概率是0.5. 
     * 这里我们用一个 NumberElement 来存数和他们的概率
     * 
     * 思路就是先把所有的权重都加起来，获得一个sum，然后每次在 0 -> sum 之间选取一个随机数 randam.nextDouble()*sum;
     * 然后遍历权重数组，当 sum > 当前元素的权重 w[i],  sum = sum - w[i]
     * 如果 sum < 当前元素的权重, 那么说明当前元素就是随机到的元素。
     */
    class NumberElement {
		int num;
		double p;
		public NumberElement(int n, double p) {
			this.num = n;
			this.p = p;
		}
	}

	public int testDouble() {
		ArrayList<NumberElement> list = new ArrayList<NumberElement>();
		list.add(new NumberElement(1, 0.2));  list.add(new NumberElement(2, 0.3));  list.add(new NumberElement(3, 0.5));
		double sum = 0;
		for (NumberElement elem : list) {
			sum += elem.p;
		}
		Random random = new Random();
		double r = random.nextDouble() * sum;
		int ret = -1;
		for (int i = 0; i < list.size(); i++) {
			r -= list.get(i).p;
			if (r < 0) {
				ret = list.get(i).num;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * 如果是加权的洗牌呢，就是上面几个问题的结合
	 * 大概思路如下
	 *   1. 对于所有的权重求和
	 *   2. 遍历数组，从后往前，在（0 ~ j+1） 之间产生随机坐标。
	 *   3. 随机数的产生过程：
	 *   	a. 0 - sum 之间取随机数r
	 *   	b. 循环权重数组，如果 sum - w[i] < 0, 返回i, 否则继续
	 *   4. 随机坐标的数字和j的数字交换。
	 */
	
	
	/**
	 * 如果是从n里面随机k个数
	 * http://www.geeksforgeeks.org/reservoir-sampling/
	 * 时间复杂度o(n)
	 * 
	 * For last n-k stream items, i.e., for stream[i] where k <= i < n 
	 * 		The probability that the last item is in final reservoir = The probability that one of the 
	 * 		first k indexes is picked for last item = k/n 
	 * 		For the second last item: [k/(n-1)]*[(n-1)/n] = k/n.
	 *
	 * For the fir k items
	 * 		The probability that an item from stream[0..k-1] is in final array = 
	 * 		Probability that the item is not picked when items stream[k], stream[k+1], …. stream[n-1] are considered
	 * 		= [k/(k+1)] x [(k+1)/(k+2)] x [(k+2)/(k+3)] x … x [(n-1)/n] = k/n
	 */
	public void selectKItems(int[] nums, int n, int k) {
		int i = 0;
		int[] reservoir = new int[k];
		for ( i = 0; i < k; i++) {
			reservoir[i] = nums[i];
		}
		
		Random random = new Random();
		
		for (; i < n; i++) {
			 int j = random.nextInt(i+1);
			 if (j < k) reservoir[j] = nums[i];
		}
		
	}
	
	
	/**
	 * 381. Insert Delete GetRandom O(1) - Duplicates allowed
	 */
	class RandomizedCollection {
	    
	    ArrayList<Integer> nums;
		HashMap<Integer, Set<Integer>> locs;
		java.util.Random rand = new java.util.Random();
	    
	    /** Initialize your data structure here. */
	    public RandomizedCollection() {
	        nums = new ArrayList<Integer>();
		    locs = new HashMap<Integer, Set<Integer>>();
	    }
	    
	    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
	    public boolean insert(int val) {
	        boolean contain = locs.containsKey(val);
		    if (!contain) {
		        locs.put(val, new LinkedHashSet<Integer>());
		    } 
		    locs.get(val).add(nums.size());        
		    nums.add(val);
		    return !contain;
	    }
	    
	    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
	    public boolean remove(int val) {
	        if (!locs.containsKey(val)) {
	            return false;
	        }
	        int loc = locs.get(val).iterator().next();
	        locs.get(val).remove(loc);
	        if (loc != nums.size()-1) {
	            int lastElem = nums.get(nums.size()-1);
	            nums.set(loc, lastElem);
	            locs.get(lastElem).remove(nums.size()-1);
	            locs.get(lastElem).add(loc);
	        }
	        nums.remove(nums.size() - 1);
	        if (locs.get(val).size() == 0) 
	            locs.remove(val);
	        
	        return true;
	    }
	    
	    /** Get a random element from the collection. */
	    public int getRandom() {
	        return nums.get(rand.nextInt(nums.size()));
	    }
	}
	
	
	/**
	 * Update a set of (upto) K elements, when you see a new element from an incoming stream,
 to ensure that every element seen so far, has an equal chance of making it into the set of K elements that we are maintaining.
	 */
	public int[] ksample(Iterator<Integer> iter, int k) {
		int[] res = new int[k];
		int idx = 0;
		Random random = new Random();
		while (iter.hasNext()) {
			if (idx < k) {
				//bucket are not full
				res[idx++] = iter.next();
				continue;
			} 
			
			// bucket are full
			int num = iter.next();
			int r = random.nextInt(idx+1);
			if (r < k) {
				res[r] = num;
			}
		}
		return res;
	}
	
	
	
	public static void main(String[] args) {
		ReservoirSample r = new ReservoirSample();
		int n1 = 0, n2 = 0, n3 = 0;
		for (int i = 0; i < 100000; i++) {
			int ret = r.testDouble();
			if (ret == 1) n1++;
			else if (ret == 2) n2++;
			else if (ret == 3) n3++;
			else System.out.println("Err");
		}
		System.out.println(n1);
		System.out.println(n2);
		System.out.println(n3);
	}
    
}
