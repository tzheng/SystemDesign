import java.util.HashSet;
import java.util.Set;

public class MoveRobots {
	/**
	 * 一个机器人在一个二维的地图上，对地图信息一无所知，包括长和宽。只有一个move命令可以控制机器人，
	 * 机器人一旦MOVE了，位置就变了，即使是障碍，位置也变到了障碍上，只是返回false
	 * 这里给你一个move method : boolean move(int direction), direction: 0,1,2,3 
	 * 表示四个方向。能移动就返回true,不能移动表示false
	 */
	
	int[] dx = {-1, 1, 0, 0};
	int[] dy = {0, 0, -1, 1};
	/** 
	 * 另外一种DFS ,
	 **/
	public void solution() {
		Set<String> visited = new HashSet<>();
		Set<String> obs = new HashSet<>(); // obstacles
		visited.add(encode(0,0));
		dfs(0, 0, visited, obs);
		//output visited.size(), obs.size()...
	}
	public void dfs(int x, int y, Set<String> visited, Set<String> obs) {
		for (int i = 0; i < 4; i++) {
			int next_x = x + dx[i], next_y = y + dy[i];
			String str = encode(next_x, next_y);
			if (visited.contains(str) || obs.contains(str)) continue;
			boolean canMove = move(i);
			if (canMove) { //如果不是障碍，就继续移动
				visited.add(str);
				dfs(next_x, next_y, visited, obs);
			} else {
				obs.add(str);
			}
			move(moveBack(i)); // move back
		}
	}
	
	private int moveBack(int i) {
		if (i%2 == 0) return i+1;
		else return i-1;
	}
	
	private boolean move(int dir) {
		return true;
	}
	
	
	public String encode(int i, int j) {
		return i + "," + j;
	}

}

