
public class HIndex {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int ans = 0;
        int start = 0, end = citations.length - 1;
        int n = citations.length;
        while (start <= end) {
            int mid = start + (end - start) /2;
            if (citations[mid] >= n - mid) {
                end = mid - 1;
            } else {
                 //don't have enought citations after mid
                start = mid + 1;
            }
        }
        //after this,  start >= end
        // end is the last element that available, h = len - start
        return n - start;
    }
    
    public static void main(String[] args) {
    	HIndex clz = new HIndex();
    	System.out.println(clz.hIndex(new int[]{0,1,3,5,6}));
    }
}
