import java.util.List;
import java.util.Set;

public class GraphNode {
	int val;
	List<Integer> children;
	
	List<GraphNode> friends;
	
	public GraphNode(int v, List<Integer> c) {
		val = v;
		children = c;
	}
}
