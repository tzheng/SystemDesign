
public class HitCounter {
	
	int[] hits = new int[300];
	int[] times = new int[300];
	 /** Initialize your data structure here. */
    public HitCounter() {
        
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
    	int index = timestamp % 300;
    	if (times[index] == timestamp) {
    		hits[index]++;
    	} else {
    		times[index] = timestamp;
    		hits[index] = 1;
    	}
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
    	int sum = 0;
    	for (int i = 0; i < 300; i++) {
    		if (timestamp - times[i] < 300) {
    			sum += hits[i];
    		}
    	}
    	return sum;
    }
}

/**
 * scale version
class HitCounter {
    Deque<Second> q = new LinkedList<Second>();
    int hits = 0;
    public HitCounter() {
        
    }
    
    public void hit(int timestamp) {
        while(!q.isEmpty() && timestamp - q.peek().sec > 299){ //at most 299 cycles, therefore O(1)
            hits -= q.peek().count;
            q.poll();
        }
        if(!q.isEmpty() && q.peekLast().sec == timestamp){
            q.getLast().count++;
            hits++;
        }else{
            q.offer(new Second(timestamp));
            hits++;
        }
    }
    
    public int getHits(int timestamp) {
        while(!q.isEmpty() && timestamp - q.peek().sec > 299){ //at most 299 cycles, therefore O(1)
            hits -= q.peek().count;
            q.poll();
        }
        return hits;
    }
}
class Second{
    int sec, count;
    public Second(int sec){
        this.sec = sec;
        count = 1;
    }
}
**/