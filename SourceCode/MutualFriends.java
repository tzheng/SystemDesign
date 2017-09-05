import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MutualFriends {

	class MutalFriend {
		public List<GraphNode> findMutalFriends(GraphNode me) {
			Queue<GraphNode> queue = new LinkedList<>();
			Set<GraphNode> friends = new HashSet<>();
			HashMap<GraphNode, Integer> mutalToCount = new HashMap<>();
			int level = 0;
			queue.offer(me);
			friends.add(me);
			while (!queue.isEmpty() && level <= 2) {
				level++;
				int size = queue.size();
				for (int i = 0; i < size; i++) {
					GraphNode node = queue.poll();
					for (GraphNode friend : node.friends) {
						if (level == 1) {
							friends.add(node);
							queue.offer(node);
							continue;
						}
						if (friends.contains(friend)) {
							continue;
						}
						if (!mutalToCount.containsKey(friend)) {
							mutalToCount.put(friend, 1);
						} else {
							mutalToCount.put(friend, mutalToCount.get(friend) + 1);
						}
					}

				}
			}
			List<GraphNode> result = new ArrayList<>();
			for (GraphNode node : mutalToCount.keySet()) {
				result.add(node);
			}
			Collections.sort(result, new NodeComparator(mutalToCount));
			return result;
		}

		class NodeComparator implements Comparator<GraphNode> {
			private HashMap<GraphNode, Integer> map;

			public NodeComparator(HashMap<GraphNode, Integer> map) {
				this.map = map;
			}

			@Override
			public int compare(GraphNode node1, GraphNode node2) {
				return map.get(node2) - map.get(node1);
			}
		}
	}

}
