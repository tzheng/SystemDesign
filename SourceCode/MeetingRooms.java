import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MeetingRooms {
	/**
	 * 252. Meeting Rooms
	 * 简单版，判断是否，按照开始时间排序即可。
	 */
	public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return true;
        }
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            } 
        });
        
        int end = intervals[0].end;
        for (int i = 0; i < intervals.length; i++) {
        	if (end > intervals[i].start) {
        		return false;
        	}
        	end = intervals[i].end;
        }
        return true;
	}
	
	/**
	 * 253. Meeting Rooms II
	 * 进阶版，我们要算几个meeting room合适
	 * 实际上就是贪心算法，做循环，把所有能安排在一起的都先安排到一起去。
	 * 然后重新循环，直到所有的会议室都安排完成了。
	 * 
	 * 这样的时间复杂度是 O(n^2)
	 */
	 public int minMeetingRooms(Interval[] intervals) {
		 Arrays.sort(intervals, new Comparator<Interval>(){
			 public int compare(Interval a, Interval b) {
				 return a.start - b.start;
			 }
		 });
		 
		 int count = 0;
		 boolean[] visited = new boolean[intervals.length];
		 for (int i = 0; i < intervals.length; i++) {
			 if (!visited[i]) {
				 int end = intervals[i].end;
				 for (int j = i+1; j < intervals.length; j++) {
					 if (!visited[j] && intervals[j].start >= end) { //注意 j要没有被安排过
						 visited[j] = true;
						 end = intervals[j].end;
					 }
				 }
				 count++;  //count的位置
			 }
		 }
		 return count;
	 }
	 
	 /**
	  * 上面的解法能不能再优化？实际上只需要用一个minHeap来存时间段，遍历整个数组，如果当前interval 和 minHeap顶点能合并，那么
	  * 就把它们合并起来，否则就直接放进heap，最后heap的大小就是不能合并的时间段的大小，也就是需要的会议室。
	  * 时间复杂度是O(nlogn)
	  */
	public int minMeetingRoomsHeap(Interval[] intervals) {
		Arrays.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return a.start - b.start;
			}
		});

		PriorityQueue<Interval> heap = new PriorityQueue<>(intervals.length, new Comparator<Interval>() {
			public int compare(Interval o1, Interval o2) {
				return o1.end - o2.end;
			}
		});

		// start with the first meeting, put it to a meeting room
		heap.offer(intervals[0]);
		for (int i = 1; i < intervals.length; i++) {
			Interval interval = heap.poll();  // get the meeting room that finishes earliest
			if (intervals[i].start >= interval.end) {
				// if the current meeting starts right after 
	            // there's no need for a new room, merge the interval
				interval.end = intervals[i].end;
			} else {
				// otherwise, this meeting needs a new room
				heap.offer(intervals[i]);
			}
			//put current meeting room back
			heap.offer(interval);
		}
		return heap.size();
	}
	
	/** 
	 * 实际上连合并都不用。 只需要存endtime就好了。
	 */
	public int minMeetingRoomsEndTime(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
        
        Queue<Integer> q = new PriorityQueue<>();
        for (Interval curr :intervals) {
            if (!q.isEmpty() && curr.start >= q.peek()) {
                q.poll();
            }
            q.offer(curr.end);
        }
        
        return q.size();
    }

	
	/**
	 * Followup 1 输出最大overlap的数量。跟原题不同的是，[3,5]和[5,5]这两个interval也算overlap，应该输出2，而不是1。
	 * 所以只要把原题的intervals.start >= heap.peek()改成>就可以了。
		面试官画的图解释：
		1 2 3 4 5
		    - - - overlap 
		        - overlap
		这个5的地方算overlap。
	 */
	
	/**
	 * Followup 2:  返回最大房间数时时间 
	 * return the exact time that has max num of room used (any valid time)
	 *  O(nlogn) time, O(n) space
	 *
	 * 或者：Given an interval list which are flying and landing time of the flight. 
	 * How many airplanes are on the sky at most?
	 * For interval list [[1,10],[2,3], [5,8], [4,7]],  Return 3
	 * 
	 * 第一个问题，返回最多的时候一共有多少个飞机，可以对于各个飞行时间段按照start时间进行排序（附加start，end的flag，如果time相同时，end在start前）
	 *          那么遍历这个排序过的链表时，也就是相当于在时间线上从前向后顺序移动，遇到start就+1，遇到end就-1，记录其中的最大值max即可。具体参考followup 5
	 *          
	 * 第二个问题，返回飞机最多的时间点，还是按照会议室问题来做，当我们看到一个 start时间大于一个end的时间的时候，我们知道，这两个飞机不会同时出现。
	 *           如果发现重叠。
	 */
	public int countOfAirplanes(List<Interval> airplanes) { 
        Collections.sort(airplanes, new Comparator<Interval>(){
        	public int compare(Interval a, Interval b) {
        		return a.start - b.start;
        	}
        });
        
        //no need to store interval, we can just store the end time
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.offer(airplanes.get(0).end);
        int overlapStart = -1;
        int overlapEnd = -1;
        for (int i = 1; i < airplanes.size(); i++) {
        	if (airplanes.get(i).start >= heap.peek()) {
        		heap.poll();
        	} else {
        		overlapStart = airplanes.get(i).start;
                overlapEnd = Math.min(heap.peek(), airplanes.get(i).end);
        	}
        	heap.offer(airplanes.get(i).end);
        }
        
        return overlapStart; //anytime before start and end
    }
	
	/**
	 * Followup 3,返回每个房间在哪些时间段被使用
	 * print each room's usage time intervals: Room 1:[2, 6],[7, 9]; Room 2:[3, 5],[8, 10]; etc.
	 * 
	 * 思路还是一样，只是这时候我们heap里面要存一个list，就是可以共用一个会议室的时间段组成的list，每次跟list最末尾的元素的结束时间相比较
	 * 如果开始时间大于结束时间，说明当前时间段可以放到这个list里面，共用会议室。
	 * 如果开始时间小于结束时间，说明要新开一个会议室，就把他加到list里面。
	 * 最后把list再放回queue
	 * 
	 * O(nlogn) time, O(n) space
	 */
	public void meetingRoomsTimes(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) {
            return;
        }
        Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
        
        //store linkedlists of intervals,each list is a room,the last element in list is the meeting that's using the room
        Queue<LinkedList<Interval>> rooms = new PriorityQueue<>(1, new Comparator<LinkedList<Interval>>(){
            public int compare(LinkedList<Interval> a, LinkedList<Interval> b) {
                return a.getLast().end - b.getLast().end;
            }
        });
        
        rooms.offer(new LinkedList<Interval>());
        rooms.peek().offer(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
        	LinkedList<Interval> list = null;
        	if (intervals[i].start >= rooms.peek().getLast().end) { 
        		//can use same meeting room
        		list = rooms.poll();
        	} else {
        		//can't use same room, open a new room
        		list = new LinkedList<>();
        	}
        	list.offer(intervals[i]);
        	rooms.offer(list);
        }
        
        while (!rooms.isEmpty()) {
            List<Interval> room = rooms.poll();
            //you can maintain a roomNum and System.out.print("Room " + roomNum + ":");
            for (Interval i : room) {//print each meeting in a same room
                System.out.print(i + ", ");
            }
            System.out.println();
        }
    }
	
	/**
	 * 4,输出空闲时间段
	 * return the time ranges of free time between startTime and endTime (time ranges that have no meetings):
	 *  O(nlogn) time, O(1) space
	 */
	public void meetingRoomsSpareTime(Interval[] intervals, int start, int end) {
		Arrays.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
		
		int begin = start;
//		for (Interval i : intervals) {
//			if (begin >= end) break;
//			if (i.start > begin) {
//				System.out.println(begin + "->" + Math.min(i.start, end));
//			}
//			begin = Math.max(begin, i.end);
//		}
//		if (begin < end)
//			System.out.println(begin + "->" + end);
		
		for (Interval i : intervals) {
			if (begin >= end) break;
			if (i.start <= begin) {
				begin = Math.max(begin, i.end);
			} else {
				System.out.println(begin + "->" + Math.min(i.start, end));
				begin = i.end;
			}
		}
		if (begin < end)
			System.out.println(begin + "->" + end);
	}
	
	/**
	 * 5，每个时段用了几个房间 
	 *    和followup 2类似，就是先排序(如果time相同时，end在start前)，后计算, 
	 *  O(nlogn) time, O(n) space
	 */
	class TimeSlot {
        int time;
        boolean isStart;
        public TimeSlot(int t, boolean i) {
            time = t;
            isStart = i;
        }
        public String toString() { return time + (isStart?"s":"e"); }
    }
    
	public void meetingRoomsUsages(Interval[] intervals) {
		List<TimeSlot> times = new ArrayList<>();
        for (Interval i : intervals) {//spilt the start time and end time, then sort them
            times.add(new TimeSlot(i.start, true));//use the boolean to regconize if it's a start or end time
            times.add(new TimeSlot(i.end, false));
        }

        Collections.sort(times, new Comparator<TimeSlot>(){
			public int compare(TimeSlot o1, TimeSlot o2) {
				if (o1.time == o2.time) {
					return o1.isStart ? 1 : -1;
				}
				return o1.time - o2.time;
			}
        });
        System.out.println(Arrays.toString(times.toArray()));
        int count = 1;
        int begin = 0;
        for (int i = 1; i < times.size(); i++) {
        	if (times.get(i).time != times.get(i-1).time) {
        		System.out.println(times.get(begin) + "->" + times.get(i) + " : " + count);
        		begin = i;
        	}
        	if (times.get(i).isStart) {
        		count++;
        	} else {
        		count--;
        	}
        }
	}
	
	/**
	 * Followup 6: 支持跨天， 比如 [23,3]，解法是先做一遍遍历 if (curr.end < curr.start) curr.end += 24;
	 */
	
	 
	 public static void main(String[] args) {
		 MeetingRooms clz = new MeetingRooms();
		 Interval[] intervals = {new Interval(2, 3), new Interval(5, 8), new Interval(4, 7)};
		 System.out.println(clz.countOfAirplanes(Arrays.asList(intervals)));
		 
//		 clz.meetingRoomsTimes(intervals);
		 
		 clz.meetingRoomsSpareTime(intervals, 0, 10);
		 
		 clz.meetingRoomsUsages(intervals);
	 }
}
