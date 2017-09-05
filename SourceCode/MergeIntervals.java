import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeIntervals {
	/**
	* Sorting intervals by their staring pointers 
	* Take the first interval and compare its end with next intervals starts
	* Case 1 : If we find overlap, we update the end to be the max end of overlapping intervals
	* Case 2 : If we find no overlapping interval, we add it to result and start over
	1，正常题O(nlogn) time, O(1)
	**/
	public List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1) {//size(), not length !!!
            return intervals;
        }
        //O(nlogn)
        List<Interval> result = new ArrayList<Interval>();
        Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
        
        Interval prev = intervals.get(0);
        for (int i = 1; i < intervals.size(); i++) {
            Interval curr = intervals.get(i);
            if (curr.start <= prev.end) {
                prev.end = Math.max(prev.end, curr.end);
            } else {
                result.add(prev);
                prev = curr;
            }
        }
        result.add(prev);//remember to add the last interval
        return result;
    }
	
	/** 
	 * Followup 1:
	 * input is unsorted and has some overlapping intervals, output is the total non-overlapping time; 
	 *  O(nlogn) time, O(1) space.
	 *  (1,3),  (2,4)  return 2, because non-overlapping is 1-2, 3-4
	 *  (1,4),  (2,3)  return 2, becuaes non-overlapping is 1-3, 3-4
	 *  
	 *  解法分三种情况，1.没有overlap，把当前都加进去。 
	 *   2. 有overlap，但是curr.end <= prev.end， 就把当前的长度加进去，然后减去重合部分
	 *   3. 有overlap，而且curr.end > prev.end, 就把当前的长度减掉，因为当前的interval被prev完全包裹住了
	 */
	public int totalNonOverlapTime(List<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1) {//size(), not length !!!
            return 0;
        }
        Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
      
        int total = 0;
        Interval prev = new Interval(0, 0);
        for (Interval curr : intervals) {
            if (prev.end < curr.start) {
                total += curr.end - curr.start;//add the whole part(non-overlapping)
                prev = curr;
            } else if (curr.end > prev.end) {
                total += curr.end - prev.end; //only add the non overlapping part after prev.end
                total -= prev.end - curr.start; //deduct overlapping area from curr end to prev end (1,3)  (2, 4)
                prev = curr;
            } else if (curr.end <= prev.end) {
            	total -= curr.end - curr.start;  //deduct overlapping area,  (1,4),  (2,3)
            }
        }
        System.out.println(total);
        return total;
    }
	
	/** 
	 * Followup 2:
	 * input is unsorted and has some overlapping intervals,  output is the total covered time
	 *	(1,3), (2,4)   return 4-1 = 3
	 *
	**/
	public int totalCoveredTime(List<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1) {//size(), not length !!!
            return 0;
        }
        Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            }
        });
      
        int total = 0;
        Interval prev = new Interval(0, 0);
        for (Interval curr : intervals) {
            if (prev.end < curr.start) {
                total += curr.end - curr.start;//add the whole part(non-overlapping)
                prev = curr;
            } else if (curr.end > prev.end) {
                total += curr.end - prev.end; //only add the non overlapping part after prev.end
                prev = curr;
            } 
            //else if (curr.end <= prev.end) {
            	//dont do anything,  because  (1,4),  (2,3),  2-3 is inside 1-4.
            //}
        }
        System.out.println(total);
        return total;
    }
	
	
	/**
	 * Followup 3:
	 * 这些interval是在一个圆环上。比如当环是[1, 40]时，
	 * 如果Interval分别是[1,10], [11,20], [15, 30], [35, 5], Merge最后的结果是[35,10], [11,30]
	 * 如果Interval分别是[1,10], [11,20], [15, 30], [35, 5], [38, 7] Merge最后的结果是[35,10], [11,30]
	 */
	public void mergeCycleIntervals(List<Interval> intervals, int range) {
		Collections.sort(intervals, new Comparator<Interval>(){
			public int compare(Interval a, Interval b) {
				return a.start - b.start;
			}
		});
		
		for (int i = intervals.size() - 1; i >=0; i--) {
			if (intervals.get(i).start <= intervals.get(i).end) break;
			Interval curr = intervals.get(i);
			intervals.remove(curr);
			intervals.add(i, new Interval(curr.start, range));
			intervals.add(0, new Interval(0, curr.end));
			i++;
		}
		
		System.out.println("After Breakdown: " + Arrays.toString(intervals.toArray()));
		intervals = this.merge(intervals);
		System.out.println("After Merge: " + Arrays.toString(intervals.toArray()));
		if (intervals.get(intervals.size()-1).end == range && intervals.get(0).start == 0) {
			Interval last = intervals.remove(intervals.size()-1);
			intervals.get(0).start = last.start;
		}
		System.out.println("After Combine: " + Arrays.toString(intervals.toArray()));
	}
	
	/**
	 * Follow 4:
	 * Interval is in the form of March, 2014
	 */
	public void mergeStringIntervals(List<String[]> intervals) {
		
		List<Interval> list = new ArrayList<>();
		for (String[] strs : intervals) {
			list.add(new Interval(convert(strs[0]), convert(strs[1])));
		}
		System.out.println("After Convert: " + Arrays.toString(list.toArray()));
		list = this.merge(list);
		System.out.println("After Merge: " + Arrays.toString(list.toArray()));
	}
	
	private int convert(String str) {
		Map<String, String> month = new HashMap<>();
		month.put("Jan", "01");
		month.put("Feb", "02");
		month.put("Mar", "03");
		month.put("Dec", "12");
		month.put("Nov", "11");
		String[] split = str.split(",");
		String newStr = split[1].trim() + month.get(split[0]);
		return Integer.valueOf(newStr);
	}
	
	/**
	 * 1）给一个n大小的list，这个list全是interval，interval是包括首尾的。返回一个同样为n大小的array A，其中A的值是i在这些intervals里出现的次数比如
		n= 5
		L = [(1, 3), (2, 4)]
		返回[0, 1, 2, 2, 1]
		follow up是如果区间的end可以无限大怎么办。
		Google 面经。
		
	 *  思路就是打乱了排序，1-3, 2-4 拆开， 排成 1s, 2s, 3e, 4e
	 *  遇到s 就增加count，遇到e就减少count
	 *  详细参见 MeetingRooms.java
	 */
	public void intervalCount(int n, List<Interval> intervals) {
		
	}
	
	
	/**
	 * Followup :
	 * 一维intervals 拓展到2维，每个interval由起点和终点组成，问怎么merge
	 *  是merge二维平面上的线段，只有完全重合才能merge
	 *  思路: 按照 slop + 截距 y-intercept 来分组，分到hashmap里面
	 *  然后对于hashmap里面每一组，按照start x来排序，然后线性时间合并即可。
	 */
	public void merge2DIntervals(List<LineInterval> intervals) {
		
	}
	
	/**2. 印度小哥 Coding. Merge Intervals 写两个函数 addInterval, getTotalBusyTime。写出两种不同的实现 分析trade off(基本就是根据两个函数的调用频率决定)
	(1) LinkedList 插入使得每次插入后start保持有序并保持所有的节点都是disjointed 同时计算totalbusy。 O(N)的add时间和O(1)get时间
	(2) Binary search tree. 也就是map，treemap这种。 保持插入后有序，O(logN) add O(N) get时间
	follow up 如果需要remove interval 用哪种方式？
	http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=144989&extra=page%3D7%26filter%3Dsortid%26sortid%3D311%26searchoption%5B3046%5D%5Bvalue%5D%3D6%26searchoption%5B3046%5D%5Btype%5D%3Dradio%26sortid%3D311
**/
	// time complexity: call O(n), get O(1)
	class MergeIntervalsClz {
	    
		int totalTime = 0;
		List<Interval> intervals = new ArrayList<>();
	    public void add(Interval newInterval) {
	    	List<Interval> res = new ArrayList<>();
	        int i = 0;
	        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
	            res.add(intervals.get(i++));//add all intervals before the insert position of the new interval
	            totalTime += intervals.get(i).end - intervals.get(i).start;
	        }//then we try to merge all intervals that can be merged
	        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {//这里的等号特别容易忘
	            newInterval.start = Math.min(newInterval.start, intervals.get(i).start);//remember to update the start !!!
	            newInterval.end = Math.max(newInterval.end, intervals.get(i).end);
	            i++;
	        }
	        totalTime += intervals.get(i).end - intervals.get(i).start;
	        res.add(newInterval);//add the newInterval when no overlap exists
	        while (i < intervals.size()) {
	            res.add(intervals.get(i++));//add the rest of the non overlapping intervals
	            totalTime += intervals.get(i).end - intervals.get(i).start;
	        }
	        intervals = res;
	    }

	    public int getTotalLength() {
	        return totalTime;
	    }
	}

	// time complexity: call O(1), get O(nlgn)
	class MergeIntervalWithLessGetCall {
	    List<Interval> intervals = new ArrayList<>();
	    public void add(int start, int end) {
	        if (start >= end) {
	            return;
	        }
	        intervals.add(new Interval(start, end));
	    }

	    public int getTotalLength() {
	        Collections.sort(intervals, new Comparator<Interval>() {
	            @Override
	            public int compare(Interval inter1, Interval inter2) {
	                return inter1.start - inter2.start;
	            }
	        });
	        int start = intervals.get(0).start;
	        int end = intervals.get(0).end;
	        int totalLen = 0;
	        for (Interval inter : intervals) {
	            if (end >= inter.start) {
	                end = Math.max(inter.end, end);
	            }
	            else {
	                totalLen += end - start;
	                end = inter.end;
	                start = inter.start;
	            }
	        }
	        totalLen += end - start;
	        return totalLen;
	    }
	}

	
	 /**  
	 *  
	 *  Followup ：
	 *  我的interval 带高度，最后merge时候也要考虑高度
	 */
	
	
	/**
	 * Find least number of intervals from A that can fully cover B
	 * For example:
		Given A=[[0,3],[3,4],[4,6],[2,7]] B=[0,6] return 2 since we can use [0,3] [2,7] to cover the B
		Given A=[[0,3],[4,7]] B=[0,6] return 0 since we cannot find any interval combination from A to cover the B
	 */
	public int minNumberToCover(Interval[] list, Interval interval) {
		Arrays.sort(list, new Comparator<Interval>(){
			public int compare(Interval a, Interval b) {
				if (a.start != b.start) {
					return a.start - b.start;
				}
				return a.end - b.end;
			}
		});
		
		int num = 0, i = 0, start = interval.start, end = -1;
		while (i < list.length) {
			if (list[i].end <= start) {
				i++;
			} else {
				if (list[i].start > start) break;
				while (i < list.length && list[i].start < start) {
					end = Math.max(end, list[i].end);
					i++;
				}
				if (start != end) {
					num++;
					start = end;
				}
			}
		}
		
		if (end < interval.end)
			return 0;
		return num;
	}
	
	
	/**
	 * interval [startTime, stoptime)   ----integral  time stamps. more info on 1point3acres.com
	给这样的一串区间 I1, I2......In  
	找出 一个 time stamp  出现在interval的次数最多。
	startTime <= t< stopTime 代表这个数在区间里面出现过。
	
	example：  [1,3),  [2, 7),   [4,  8),   [5, 9)
	5和6各出现了三次， 所以答案返回5，6。
	
	class MaxOverpal {
	    public List<Integer> findMaxOverLapTime (Interval[] intervals) {
	        List<Integer> result = new ArrayList<>();
	        if (intervals.length == 0) {
	            return result;
	        }
	        List<Point> points = new ArrayList<>();
	        for (Interval interval : intervals) {
	            points.add(new Point(interval.start, true));
	            points.add(new Point(interval.end, false));
	        }
	        Collections.sort(points, new Comparator<Point>() {
	            @Override
	            public int compare(Point p1, Point p2) {
	                if (p1.time == p2.time) {
	                    return p1.isStart ? 1 : 0;
	                }
	                return p1.time - p2.time;
	            }
	        });
	        int max = 0;
	        int number = 0;
	        int start = 0;
	        int end = 0;
	        for (Point point : points) {
	            if (point.isStart) {
	                number++;
	                if (number > max) {
	                    max = number;
	                    start = point.time;
	                    end = point.time;
	                }
	            }
	            else {
	                if (number == max) {
	                    end = point.time;
	                }
	                number--;
	            }
	        }
	        for (int i = start; i < end; i++) {
	            result.add(i);
	        }
	        return result;
	    }
	
	    private class Point {
	        public int time;
	        public boolean isStart;
	        public Point(int time, boolean isStart) {
	            this.time = time;
	            this.isStart = isStart;
	        }
	    }
	}

	 * @param args
	 */
	
	
	/**
	 * Merge two sorted intervals
	 * Two Pointer
	 */
	public List<Interval> mergeList(List<Interval> l1, List<Interval> l2) {
		//null check
		
		Collections.sort(l1, new IntervalComparator());
		Collections.sort(l2, new IntervalComparator());
		
		List<Interval> res = new ArrayList<>();
		Interval prev = null;
		
		int i = 0, j = 0;
		if (l1.get(0).start < l2.get(0).start) {
			prev = l1.get(0); i++;
		} else {
			prev = l2.get(0); j++;
		}
		
		while (i < l1.size() || j < l2.size()) {
			Interval curr = null;
			if (j == l2.size() || (i < l1.size() && l1.get(i).start < l2.get(j).start)) {
				curr = l1.get(i);
				i++;
			} else {
				curr = l2.get(j);
				j++;
			}
			
			if (curr.start > prev.end) {
				res.add(prev);
				prev = curr;
			} else {
				prev.end = Math.max(prev.end, curr.end);
			}
		}
		res.add(prev);
		return res;
	}
	
	class IntervalComparator implements Comparator<Interval> {
		@Override
		public int compare(Interval a, Interval b) {
			return a.start - b.start;
		}
	}
	
	
	public static void main(String[] args) {
		MergeIntervals clz = new MergeIntervals();
		List<Interval> list = new ArrayList<>();
		list.add(new Interval(1,3));
		list.add(new Interval(2,4));
		list.add(new Interval(5,7));
//		clz.totalNonOverlapTime(list);
		clz.totalCoveredTime(list);
		
		
		List<LineInterval> lineIntervals = new ArrayList<LineInterval>();
		lineIntervals.add(new LineInterval(new Point(0,0), new Point(4,4)));
//		lineIntervals.add(new LineInterval(new Point(0,2), new Point(4,6)));
		lineIntervals.add(new LineInterval(new Point(2,2), new Point(5,5)));
		lineIntervals.add(new LineInterval(new Point(3,5), new Point(5,7)));
		
//		clz.merge2DIntervals(lineIntervals);
		
		//如果Interval分别是[1,10], [11,20], [15, 30], [35, 5], Merge最后的结果是[35,10], [11,30]
		List<Interval> clist = new ArrayList<>();
		clist.add(new Interval(1,10));
		clist.add(new Interval(11,20));
		clist.add(new Interval(15, 30));
		clist.add(new Interval(35, 5));
		clist.add(new Interval(38, 12));
		clz.mergeCycleIntervals(clist, 40);
		
		
		List<String[]> slist = new ArrayList<>();
		slist.add(new String[]{"Mar, 2014", "Nov, 2014"});
		slist.add(new String[]{"Dec, 2014", "Feb, 2015"});
		slist.add(new String[]{"Jan, 2015", "Mar, 2015"});
		clz.mergeStringIntervals(slist);
		
		
		List<Interval> l1 = new ArrayList<>();
		l1.add(new Interval(1, 5));
		l1.add(new Interval(10, 14));
		l1.add(new Interval(16, 18));
		l1.add(new Interval(20, 24));
		l1.add(new Interval(30, 38));
		List<Interval> l2 = new ArrayList<>();
		l2.add(new Interval(2, 6));
		l2.add(new Interval(8, 10));
		l2.add(new Interval(11, 20));
		clz.mergeList(l1, l2);
		List<Interval> result = clz.mergeList(l1, l2);
		for (Interval i1: result) {
			System.out.println(i1.start + ", " + i1.end);
		}
		
	}
	
	

}
