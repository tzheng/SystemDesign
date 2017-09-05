
public class LineInterval {
	Point start;
	Point end;
	double slop;
	public LineInterval(Point s, Point e) {
		start = s;
		end = e;
		slop = (double)(e.y - s.y) /(double)(e.x - s.x);
	}
	
	public String toString() {
		return "Slop: " + slop + "(" + start.x + "," + start.y + ") , (" + end.x + "," + end.y +")"; 
	}
	
	public static int compare(LineInterval a, LineInterval b) {
		if (a.slop != b.slop) {
			return Double.valueOf(a.slop).compareTo(b.slop);
		}
		
		// 斜率一样，也有可能不在一条线, 比较起点的高度
		if (a.start.y != b.start.y) {
			return a.start.y - b.start.y;
		}

		// 如果起点y一样，斜率一样， 就看起点x 排序
		if (a.start.x != b.start.x) {
			return a.start.x - b.start.x;
		}

		// 如果起点 x,y 都一样，斜率还一样，那么他两肯定有重合
		// 这时候随便按终点的x 排序就好了
		//return 0 让他们两在一起
//		return a.end.x - b.end.x;
		return 0;
	}
}
