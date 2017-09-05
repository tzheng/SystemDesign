
public class RectangleArea {
	
	public boolean hasOverlap(Point p1, Point p2, Point p3, Point p4) {
		boolean flag = false;
		
		//p1, p2 is top left and bottom right point for square1
		//p3, p3 is for square 2
		
		int lower_x = Math.max(p1.x, p3.x);
		int upper_x = Math.min(p2.x, p4.x);
		
		int lower_y = Math.max(p2.y, p4.y);
		int upper_y = Math.min(p1.y, p3.y);
		
		if (lower_x > upper_x || lower_y > upper_y) {
			return false;
		}
		System.out.println("overlap area: " + (upper_x - lower_x) * (upper_y - lower_y));
		return true;
	}
	
	public static void main(String[] args) {
		RectangleArea clz = new RectangleArea();
		
		Point p1 = new Point(0,2);
		Point p2 = new Point(2,0);
		
		Point p3 = new Point(0,4);
		Point p4 = new Point(2,3);
		clz.hasOverlap(p1, p2, p3, p4);
	}
	
}
