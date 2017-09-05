import java.util.Arrays;

public class RingBuffer {
	
	int capacity;
	int readPos;
	int writePos;
	int count;
	
	Object[] buffer;
	
	/**
	 * 
	 *    1 2 3 _ _ _ _ _ _ _ 7 8 
	 *          |             |
	 *         writePos      readPos
	 */
	public RingBuffer(int c) {
		this.capacity = c;
		buffer = new Object[capacity];
	}
	
	public boolean offer(Object obj) {
		if (count == capacity) {
			return false;
		}
		buffer[writePos++] = obj;
		writePos = writePos % capacity;
		count++;
		return true;
	}
	
	public Object poll() {
		if (count == 0) {
			return null;
		}
		
		Object ret = buffer[readPos];
		buffer[readPos++] = null;
		count--;
		readPos = readPos % capacity;
		return ret;
	}
	
	public boolean isEmpty() {
		return count == 0;
	}
	
	public static void main(String[] args) {
		RingBuffer buffer = new RingBuffer(5);
		for (int i = 0; i < 5; i++) {
			buffer.offer(i);
			System.out.println(Arrays.toString(buffer.buffer));
		}
		
		buffer.poll();
		buffer.poll();
		System.out.println(Arrays.toString(buffer.buffer));
		
		buffer.offer(5);
		System.out.println(Arrays.toString(buffer.buffer));
		
	}
}
