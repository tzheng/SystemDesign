
public class ExcelColumn {
	public String convertToTitle(int n) {
        StringBuilder column = new StringBuilder();
        while (n != 0) {
        	n--;
            int remain = n % 26;
            char c = (char) ('A' + remain);
            column.insert(0, c);
            
            n = n/26;
        }
        
        return column.toString();
    }
	
	public static void main(String[] args) {
		ExcelColumn clz = new ExcelColumn();
		System.out.println(clz.convertToTitle(28));
	}
}
