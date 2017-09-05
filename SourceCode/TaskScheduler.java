import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class TaskScheduler {
	
	/**
	 * 3 实现一个蜜汁TaskScheduler. 实现getTask的function。每个task都有prerequisite。
		所以把graph 建好 跑一次topologicla sort 每次getTask 就输出一个task这样? thanks
		其实我也没完全搞懂那题，大概是每次getTask就打印当前运行的task
		补充内容 (2017-5-24 07:11):
		task运行完会有一个callback。只有当那个callback被调用才能把task从那个运行列表里去掉。
	 */
	Map<Task, List<Task>> map = new HashMap<>();
	Map<Task, Integer> indegree = new HashMap<>();
	
	Queue<Task> queue = new LinkedList<>();
	
	public TaskScheduler(Task[][] prereq) {
		for (Task[] p : prereq) {
			if (!map.containsKey(p[0])) {
				map.put(p[0], new ArrayList<Task>());
			}
			if (!map.containsKey(p[1])) {
				map.put(p[1], new ArrayList<Task>());
			}
			map.get(p[0]).add(p[1]);
			if (!indegree.containsKey(p[1])) {
				indegree.put(p[1], 1);
			} else {
				indegree.put(p[1], indegree.get(p[1]) + 1);
			}
			p[0].callback = this;
			p[1].callback = this;
		}
		// for entry in indegeree,  if val == 0, add key to queue
	}
	
	public void release(Task t) {
		List<Task> children = map.get(t);
		for (Task child : children) {
			int count = indegree.get(child);
			count--;
			if (count == 0) {
				queue.offer(child);
			} else if (count > 0) {
				indegree.put(child, count);
			}
		}
	}
	
	public Object[] getTask() {
		return queue.toArray();
	}
}


class Task {
	int id;
	TaskScheduler callback;
	
	public Task(int i) {
		id = i;
	}
	
	public void finish() {
		callback.release(this);
	}
}