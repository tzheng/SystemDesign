import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {
	public static void main(String[] args) {
		
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(10, new Comparator<Integer>(){
			public int compare(Integer a, Integer b) {
				return b - a;
			}
		});
		
		for (int i = 0; i < 10; i++) {
			minHeap.offer(i);
		}
		
		for (int i = 0; i < 10; i++) {
			System.out.println(minHeap.poll());
		}
		
		for (int i = 0; i < 10; i++) {
			minHeap.offer(i);
			if (minHeap.size() > 3) {
				minHeap.poll();
			}
		}
		
		for (int i = 0; i < 3; i++) {
			System.out.println(minHeap.poll());
		}
		
	}
}
