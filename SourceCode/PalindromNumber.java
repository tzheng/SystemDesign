
public class PalindromNumber {
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int div = 1;
        while (div <= x/10) {
            div *= 10;
        }
        System.out.println("Number is: " + x + " DIV is: " + div);
        
        while (x != 0) {
            int left = x / div;
            int right = x % 10;
            System.out.println("left, right are: " + left + "  " + right);
            if (left != right) {
                return false;
            }
            x = (x % div) / 10;
            div = div / 100;
            System.out.println("Number is: " + x + " DIV is: " + div);
        }
        return true;
    }
    
    public static void main(String[] args) {
//    	isPalindrome(1410110141);
    	System.out.println(PalindromNumber.class.hashCode());
    }
}
