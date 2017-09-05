import java.util.ArrayList;
import java.util.List;

public class InsertInterval {
	
	/**
	 * 57. Insert Interval
	 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
	 * You may assume that the intervals were initially SORTED according to their start times.
	 * 
	 * O(n) time, O(1) space
	 */
	public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new ArrayList<Interval>();
        
        // if the input is unsorted, add: (which will make this algo O(nlogn) time)
        // Collections.sort(intervals, new Comparator<Interval>(){
        //     public int compare(Interval a, Interval b) {
        //         return a.start - b.start;
        //     }
        // });
        
        int i = 0;
        boolean added = false;
        while (i < intervals.size()) {
        	Interval curr = intervals.get(i);
        	if (!added && curr.start < newInterval.start) {
        		addToResult(result, curr);
        		i++;
        	} else {
        		addToResult(result, newInterval);
        	}
        }
        if (!added) {
            addToResult(result, newInterval);
        }
        return result;
	}
	
	//因为不能确定新加了一个interval之后，后面有几个interval重叠了，所以每一个都要检查
	private void addToResult(List<Interval> result, Interval toAdd) {
        if (result.size() == 0) {
            result.add(toAdd);
            return;
        }
        
        Interval last = result.get(result.size() - 1);
        if (last.end >= toAdd.start) {
            last.end = Math.max(last.end, toAdd.end);
        } else {
            result.add(toAdd);
        }
    }
	
	/**
	 * 解法2，思路更好理解
	 */
	public List<Interval> insert2(List<Interval> intervals, Interval newInterval) {
        List<Interval> res = new ArrayList<>();
        int i = 0;
        while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
            res.add(intervals.get(i++));//add all intervals before the insert position of the new interval
            //totalTime += intervals.get(i).end - intervals.get(i).start;
        }//then we try to merge all intervals that can be merged
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {//这里的等号特别容易忘
            newInterval.start = Math.min(newInterval.start, intervals.get(i).start);//remember to update the start !!!
            newInterval.end = Math.max(newInterval.end, intervals.get(i++).end);
           
        }
        //totalTime += intervals.get(i).end - intervals.get(i).start;
        res.add(newInterval);//add the newInterval when no overlap exists
        while (i < intervals.size()) {
            res.add(intervals.get(i++));//add the rest of the non overlapping intervals
            //totalTime += intervals.get(i).end - intervals.get(i).start;
        }
        return res;
    }
	
	

	/**
	 *  input: unsorted, no overlaps; 
	 *  output: can be unsorted, return total time; 
	 *  O(n) time, O(n) space
	 */
    public int insertUnsorted(List<Interval> intervals, Interval newInterval) {
        if (newInterval == null) {
            return 0;
        }
        int totalTime = 0;
        //if you need to insert intervals while calculating time,add:
        //List<Interval> res = new ArrayList<>();
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).start <= newInterval.end || intervals.get(i).end >= newInterval.start) {
            	//merge overlaps
                newInterval.start = Math.min(newInterval.start, intervals.get(i).start);//remember to update the start!!!
                newInterval.end = Math.max(newInterval.end, intervals.get(i).end);
            } else {
                totalTime += intervals.get(i).end - intervals.get(i).start;
                //res.add(intervals.get(i));//add all non-overlapping intervals
            }
        }
        totalTime += newInterval.end - newInterval.start;//we add the time of merged newInterval here
        //if you need to insert intervals while calculating time,add:
        //res.add(newInterval);//add the newInterval when all intervals have been checked so that no overlap exists
        return totalTime;
    }

	// input: unsorted, no overlaps; 
    // output: can be unsorted; O(n) time, O(1) space
    public List<Interval> insertUnsorted2(List<Interval> intervals, Interval newInterval) {
        if (newInterval == null) {
            return intervals;
        }
        List<Interval> res = new ArrayList<>();
        for (int i = 0; i < intervals.size(); i++) {
            if (intervals.get(i).start <= newInterval.end || intervals.get(i).end >= newInterval.start) {//merge overlaps
                newInterval.start = Math.min(newInterval.start, intervals.get(i).start);//remember to update the start too!!!
                newInterval.end = Math.max(newInterval.end, intervals.get(i).end);
            } else {
                res.add(intervals.get(i));//add all non-overlapping intervals
            }
        }
        res.add(newInterval);//add the newInterval when all intervals have been checked so that no overlap exists
        return res;
    }

}
