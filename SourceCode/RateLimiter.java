import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RateLimiter {
	/**
	 * 359. Logger Rate Limiter
	 * Design a logger system that receive stream of messages along with its timestamps, 
	 * each message should be printed if and only if it is not printed in the last 10 seconds.
	 */
	HashMap<String, Integer> map = new HashMap<>();
	
	public boolean shouldPrintMessage(int timestamp, String message) {
		if (!map.containsKey(message) || timestamp - map.get(message) >= 10) {
			map.put(message, timestamp);
			return true;
		}
		return false;
	}
	
	/**
	 * Leetcode 上面的题目很简单，就是同意一下就好了。实际应用中，有一个token bucket的办法
	 * 一个thread按照一定的速率往bucket里面放token，但有request需要处理的时候，从bucket里面拿出
	 * token，如果此时bucket是空的，就让request等待。如果bucket满了，就不继续添加token了。
	 */
	
	private final long fillPeriod;
	private final BlockingQueue<Object> queue;
	private long timer;
	
	public RateLimiter(int capacity, double rate) {
		if (rate <= 0) {
			this.fillPeriod = Long.MAX_VALUE;
		} else {
			this.fillPeriod = (long) (1000000000L / rate);
		}
		this.queue = new ArrayBlockingQueue<Object>(capacity);
		this.timer = System.nanoTime();
	}
	
	
	public synchronized void tick() {
		long elapsedTime = System.nanoTime() - timer;
		int numToRemove = (int) (elapsedTime / fillPeriod);

		// advance timer
		timer += fillPeriod * numToRemove;

		List<Object> discardedObjects = new ArrayList<Object>(numToRemove);
		queue.drainTo(discardedObjects, numToRemove);
	}
	
	public boolean limit() {
		try {
			queue.put(new Object());
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}
	
}
