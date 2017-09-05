import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class OtherProblems {

	/**
	 * pascal里面字符串是以[5, 'a', 'b', 'c', 'd', 'e']的形式存储，现在要你用C++将这样两个东西
	 * 连接起来。。。比如[3,'a',b','c']和[2,'x', 'y']变成[5, 'a', b', 'c', 'x', 'y']
	 */
	public void combine(char[] a, char[] b) {
		int n = a[0] - '0';
		int m = b[0] - '0';
		String sum = String.valueOf(m + n);
		char[] res = new char[sum.length() + m + n];
		System.arraycopy(sum.toCharArray(), 0, res, 0, sum.length());
		int index = sum.length();
		for (int i = 1; i < n + 1; i++) {
			res[index++] = a[i];
		}
		for (int j = 1; j < m + 1; j++) {
			res[index++] = b[j];
		}
		
		System.out.println(Arrays.toString(res));
	}
	
	/**
	 * void putInteger( Int x) {} 这个就是打印一个整数 ，必须用下面的程序。
	 * using  putc( char );  这个function就是打印char， 输入是什么char打印就是什么char
	 * 相当于实现 String.valueOf(x).toCharArray()
	 * @param x
	 */
	public void putInteger(int x) {
		if (x < 0) {
			putChar('-');
			putInteger(-x);
		} else if (x < 10) {
			putChar((char)(x + '0'));
		} else {
			putInteger(x/10);
			putChar((char)(x%10 + '0'));
		}
	}
	
	public void putChar(char c) {
		System.out.print(c);
	}
	
	
	/**
	 * We're given a sorted array of integers: [-3, -1, 0, 1, 2]. 
	 * We want to generate a sorted array of their squares: [0, 1, 1, 4, 9]。
	 */
	public void sortSquares(int[] nums) {
		int[] res = new int[nums.length];
		int left = 0, right = nums.length - 1;
		int index = nums.length - 1;
		while (left < right) {
			if (Math.abs(nums[left]) > Math.abs(nums[right])) {
				res[index--] = nums[left] * nums[left];
				left++;
			} else {
				res[index--] = nums[right] * nums[right];
				right--;
			}
		}
		System.out.println(Arrays.toString(res));
	}
	
	
	/**
	 * 277. Find the Celebrity
	 * 两遍 第一遍找出一个candidate 就是假如a 认识 b，b就是candidate，a 
	 * 肯定不是， 每次把一个人和candidate比较 然后根据情况更新candidate
	 */
	public int findCelebrity(int n) {
		int i = 0, j = n-1;
		while (i < j) {  //注意是 i < j, 没必要
			if (knows(i, j)) {
				i++;
			} else {
				j--;
			}
		}
		
		for (i = 0; i < n; i++) {
			if (i != j && (!knows(i,j) || knows(j,i))) {
				return -1;
			}
		}
		return j;
	}
	
	private boolean knows(int a, int b) {
		return true;
	}
	
	/**
	 * 38. Count and Say
	 * O(n ^ n)
	 */
	public String countAndSay(int n) {
		int i = 1;
		String res = "1";
		while (i < n) {
			int count = 1;
			char prev = res.charAt(0);
			StringBuilder sb = new StringBuilder();
			for (int j = 1; j < res.length(); j++) {
				if (res.charAt(j) == prev) {
					count++;
				} else {
					sb.append(count).append(prev);
					count = 1;
					prev = res.charAt(j);
				}
			}
			sb.append(count).append(prev);
			res = sb.toString();
			System.out.println(i + ", " + res);
			i++;
		}
		return res;
	}
	
	

	
	/**
	 * 调转句子里面的词
	 */
	public char[] reverseOrder(char[] wordsArr){
		StringBuffer currentString = new StringBuffer();
		StringBuffer resultString = new StringBuffer();
		
		for(int i=wordsArr.length-1;i>=0;i--){
			if(wordsArr[i]!=' ')
				currentString.append(wordsArr[i]);
				
			if(wordsArr[i]==' ' || i==0)
			{
				resultString.append(currentString.reverse());
				if (wordsArr[i] == ' ') 
					resultString.append(" ");
				currentString = new StringBuffer();
			}
		}
		return resultString.toString().toCharArray();
	}
	
	/**
	 * copy-on-write string class
   		e.g. stringclass s("abc");.
         stringclass s1 = s;  //no copy
         s = "bcd"; // copy
	 */
	
	
	/**
	 * 给两个数 n1, n2. n2 is n1 without 1 digit, e.g. n1 = 123, then n2 can be 12, 13 or 23. 
	 * 现在知道n1+n2的和是多少， 求n1 和 n2 的可能值
	 * 
	 * 110 + 10 = 120
	 */
	public void breakdownSum(int n) {
		for (int i = n; i > 10; i--) {
			int n1 = i;
			String s1 = String.valueOf(n1);
			for (int j = 0; j < s1.length(); j++) {
				String s2 = s1.substring(0, j) + s1.substring(j+1, s1.length());
				int n2 = Integer.valueOf(s2);
				if (n1 + n2 == n) {
					System.out.println(n1 + " + " + n2);
				}
			}
			
		}
	}
	
	
	/**
	 * /From the input array, output a subset array with numbers part of a Fibonacci series. 
	 * input: [4,2,8,5,20,1,40,13,23]
	 *  output: [2,5,1,8,13] 
	 *  
	 *  A number is Fibonacci if and only if one or both of (5*n2 + 4) or (5*n2 – 4) is a perfect square
	 */
	public void getFibNumbers(int[] nums){
		for (int num : nums) {
			if (isPerfectSquare(5*num*num + 4) && isPerfectSquare(5 * num * num -4)) {
				System.out.println(num);
			}
		}
	}
	
	private boolean isPerfectSquare(int num) {
		int s = (int)Math.sqrt(num);
		return s*s == num;
	}
	
	public static void extract(int sum){
        int splitter = 1;
        while(sum>splitter){
                int right = sum/splitter;
                int left = sum%splitter;
                if(left%2==1) break;
                
                int extractedLeft = left/2;
                Integer extractedRight = extractIfLast(right);
                if(extractedRight!=null){
                        System.out.print(extractedRight*splitter+extractedLeft + ", ");
                        System.out.println((right-extractedRight)*splitter+extractedLeft);
                }
                if(splitter>1){
                        extractedRight = extractIfLast(right-1);
                        if(extractedRight!=null){
                                System.out.print(extractedRight*splitter+extractedLeft+splitter/2+ ",     ");
                                System.out.println((right-1-extractedRight)*splitter+extractedLeft+splitter/2);
                        }
                }
                splitter *= 10;
        }
        
}
	//如果是去掉了最后一位
	public static Integer extractIfLast(int sum){
	        int small = sum/11;
	        int large = sum-small;
	        if(large/10==small) return large;
	        else return null;
	}
	
	
	public static void main(String[] args) {
		OtherProblems clz = new OtherProblems();
		clz.combine(new char[]{'3', 'a', 'b','c'},  new char[]{'2', 'x', 'y'});
		clz.putInteger(1234);
		System.out.println();
		
		int[] nums = {-3, -2, -1, 0};
		clz.sortSquares(nums);
		
		clz.countAndSay(5);
		
		
		clz.breakdownSum(1246);
		
		extract(1246);
		
	}
	
}
