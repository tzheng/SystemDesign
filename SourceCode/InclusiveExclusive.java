import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class InclusiveExclusive {
/**
 * time（函数总运行时间）和exclusive time（函数总运行时间-自函数运行时间）。
例如
fun_name enter/exit time
f1             enter         1
f2             enter         2
f2             exit           3
f1             exit           4

f1: inclusive time=4-1=3 exclusive time=3-1=2
f2: inclusive time=3-2=1 exclusive time=1
看见题有点懵，思考了几分钟觉得用栈做行。把sub function的时间记录在栈顶。函数exit时，inclusive time好算，直接减。
exclusive time就用inclusive time-栈顶时间。
 */
	public static void calc(List<Log> list) {
		Stack<Log> stack = new Stack<>();
		
		Map<Integer, Integer> inclusive = new HashMap<>();
		Map<Integer, Integer> exclusive = new HashMap<>();
		
		for (Log log : list) {
			if (log.enter) {
				stack.push(log);
			} else {
				Log top = stack.pop();
				int in = log.time - top.time;
				int ex = in - top.exclusive;
				inclusive.put(top.id, in);
				exclusive.put(top.id, ex);
				if (!stack.isEmpty()) {
					stack.peek().exclusive += in;
				}
			}
		}
		
		for (int key : inclusive.keySet()) {
			System.out.println("Task " + key + " Include: " + inclusive.get(key) 
			+ ", exclude: " + exclusive.get(key));
		}
	}
	
	public static void main(String[] args) {
		List<Log> list = new ArrayList<>();
		list.add(new Log(1, 1, true));
		list.add(new Log(2, 2, true));
		list.add(new Log(2, 3, false));
		list.add(new Log(1, 4, false));
		calc(list);
		
		System.out.println();
		
		list = new ArrayList<>();
		list.add(new Log(1, 1, true));
		list.add(new Log(2, 2, true));
		list.add(new Log(2, 3, false));
		list.add(new Log(3, 4, true));
		list.add(new Log(3, 5, false));
		list.add(new Log(1, 6, false));
		calc(list);
		System.out.println();
		
		list = new ArrayList<>();
		list.add(new Log(1, 1, true));
		list.add(new Log(2, 2, true));
		list.add(new Log(3, 3, true));
		list.add(new Log(3, 4, false));
		list.add(new Log(2, 5, false));
		list.add(new Log(1, 6, false));
		calc(list);
	}
	
    
    /**
     * 636. Exclusive Time of Functions My SubmissionsBack to Contest
     User Accepted: 0
     User Tried: 0
     Total Accepted: 0
     Total Submissions: 0
     Difficulty: Medium
     Given the running logs of n functions that are executed in a nonpreemptive single threaded CPU, find the exclusive time of these functions.
     
     Each function has a unique id, start from 0 to n-1. A function may be called recursively or by another function.
     
     A log is a string has this format : function_id:start_or_end:timestamp. For example, "0:start:0" means function 0 starts from the very beginning of time 0. "0:end:0" means function 0 ends to the very end of time 0.
     
     Exclusive time of a function is defined as the time spent within this function, the time spent by calling other functions should not be considered as this function's exclusive time. You should return the exclusive time of each function sorted by their function id.
     * @param args
     */
    public int[] exclusiveTime(int n, List<String> logs) {
        if (logs == null || logs.size() == 0)
            return new int[]{};
        
        List<Log> list = new ArrayList<>();
        for (String str : logs) {
            String[] split = str.split(":");
            int id = Integer.valueOf(split[0]);
            boolean enter = split[1].equals("start");
            int time = Integer.valueOf(split[2]);
            Log log = new Log(id, time, enter);
            list.add(log);
        }
        
        
        Stack<Log> stack = new Stack<>();
        
        Map<Integer, Integer> inclusive = new HashMap<>();
        Map<Integer, Integer> exclusive = new HashMap<>();
        
        for (Log log : list) {
            if (log.enter) {
                stack.push(log);
            } else {
                Log top = stack.pop();
                int in = log.time - top.time + 1;
                int ex = in - top.exclusive;
                inclusive.put(top.id, in + (inclusive.get(top.id) == null ? 0 : inclusive.get(top.id)));
                exclusive.put(top.id, ex + (exclusive.get(top.id) == null ? 0 : exclusive.get(top.id)));
                if (!stack.isEmpty()) {
                    stack.peek().exclusive += in;
                }
            }
        }
        
        int[] res = new int[n];
        
        for (int key : exclusive.keySet()) {
            res[key] = exclusive.get(key);
        }
        
        return res;
    }
    
   
	
	static class Log {
		int id;
		int time;
		boolean enter;
		int exclusive = 0;
		public Log(int i, int t, boolean f) {
			id = i; time = t; enter = f;
		}
	}
	
}

