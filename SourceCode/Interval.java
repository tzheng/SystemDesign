
public class Interval {
	public int start;
	public int end;
	public Interval(int s, int e) {
		start = s;
		end = e;
	}
	
	public String toString() {
		return "[" + start + "," + end +"]";
	}
}
