import java.util.Arrays;
import java.util.Random;

public class ShuffleArray {
	public static void main(String[] args) {
		int n = 5;
		int[] arr = new int[n];
		for (int i = 1; i <= n; i++) {
			arr[i-1] = i;
		}
		
		//shuffle array
		System.out.println("Before Shuffle: " + Arrays.toString(arr));
		
		Random random = new Random();
		for (int i = n-1; i > 0; i--) {
			int j = random.nextInt(i+1);
			int tmp = arr[j];
			arr[j] = arr[i];
			arr[i] = tmp;
		}
		
		System.out.println("After Shuffle: " + Arrays.toString(arr));
		
		System.out.println();
		n = 3;
		arr = new int[]{1,2,3};
		int[] weight = {3,2,1};
		
		int totalWeight = 0;
		for (int i = 0; i < weight.length; i++) totalWeight += weight[i];
		
		System.out.println("Before Shuffle: " + Arrays.toString(arr));
		System.out.println("Weights: " + Arrays.toString(weight) + ", Total: " + totalWeight);
		
		
		for (int i = n-1; i > 0; i--) {
			//因为加权了，在选随机位置的时候要考虑权重 Refer to RandomNumber.java
			int rand = random.nextInt(totalWeight) + 1;
			int j = -1;
			for (int k = 0; k < weight.length; k++) {
				rand -= weight[k];
				if (rand <= 0) {
					j = k;
					break;
				}
			}
			
			int tmp = arr[j];
			arr[j] = arr[i];
			arr[i] = tmp;
		}
		System.out.println("After Shuffle: " + Arrays.toString(arr));
	}
}
