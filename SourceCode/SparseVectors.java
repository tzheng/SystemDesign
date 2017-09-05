import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given two sparse Vectors, compute the Dot Product. 
	Input Format : The first line will contain two numbers(k and n), which are the number 
	of entries for the two vectors respectively. 
	The next k lines are the entries for the first vector, of the form : x y 
	where x is the position and y is the value at that position in the vector. 
	The n lines are the entries of the second vector. 
	Any entries not specified indicate zero at that position. 
	The two vectors will always be of the same length 

	Example input: 
	3 3 
	1 4 
	4 2 
	5 3 
	1 7 
	2 6 
	5 1 

	Sample Answer: Dot Product = 4*7+3*1 = 31 (only print 31)
	https://www.careercup.com/question?id=5678837729853440	
	
	follow up: what if the number of nonzero elements of one vector is 10^10 and the other is 10^2,
 	how can you make it faster?
	可以用自己喜欢的数据结构，最简单的就是用一个map存啦，这样每次找value 只要O(1),
	然后就问说如果有很多0怎么办，那就只存不为0的值，遍历A和B里面非0值比较少的，这样
	时间复杂度就是非0值的个数的较小值。但是map存的话空间不够优化，就有说到两个指针的
	做法，用两个list来存。还问如果一个很长，一个很短怎么办，极端情况就是B的非0值只有
	一个，A有10000个，那就从B开始乘，用二分找A的value list里面对应于B的index。
	反正多沟通最重要，说出每种方法的优缺点，然后面试官让你写你再写。
	
	给出了hashmap 和 two pointers的解法，问为什么hashmap有overhead
	
	
	这个应该是FB的经典高频题了，地里有很多面经。就是先让自己设计一个class Vec 
	表示欧式空间的N维向量v = [v1, v2, ..vN]，然后再写个函数 
	int dotProd(Vec& v, Vec& u)来计算向量内积。
	会设计到data structure怎么选取，是否非零元素稀疏，根据不同data structure
	计算内积的时间花费，binary search vs two pointers，等等一系列问题。个人觉得是一个非常好的面试题，虽然不难但考察全面

	
	设向量A长度为m，向量B长度为n.
	于是第一个优化的想法是将向量变为这样的模式
	向量A:{<x1',loc1'>,<x2',loc2'>...<xn',locn'>}
	向量B:{<y1',loc1'>,<y2',loc2'>...<yn',locn'>},这里locx表示元素的位置信
	息。
	每个向量都不含零元素，或者接近于零的浮点数。显然向量数量远小于n，且向
	量A，B的长度取决于各自的非零元，不妨设向量A长度为m，向量B长度为n.
	
	那么计算A*B，可以采用在向量A中循环，在向量B中二分的方法，例如找到向量A
	的首原素，<x1',loc1'>,将其位置loc1'在向量B中折半查找，直到找到，或者失
	败。
	这样计算代价为mlogn + min(m,n),前部为查找代价，后部为加法代价，加法代
	价必然比min(m,n)还要小,最大情况下为min(m,n)-1。
	
	进一步来看，如果在计算向量A和向量B乘法时，我们已经知道了它们各自的长
	度，那么可以在小的向量上循环，在大向量上二分，这样代价为min(m,n)log(max
	(m，n))+min(m,n)
	
	当然也可以不使用二分，采用哈希，假定认为哈希的代价为O(1),那么总代价为
	2min(m,n)这里创建哈希也需要代价。
 *
 */
public class SparseVectors {
	//http://introcs.cs.princeton.edu/java/44st/SparseVector.java.html
	
	class Vector {
		int index;
		int value;
		public Vector(int i, int v) {
			index = i; value = v;
		}
	}
	
	/**
	 * 最简单的办法用hash 存 index-> value  pair, 然后遍历
	 * 时间 O(M+N)， k较小的向量里面非0的个数 , 空间 O(M+N)
	 * 当然也要考虑题目情况， 如果把向量都存在hashMap里面了，那么
	 * 就不能保持原有的顺序了，不能做有顺序的iteration，这时候可以
	 * 用 List<Vector> 或者 Vector[] 来解决 
	 * 
	 */
	public void docProductHash2(int[] v1, int[] v2) {
		// <Index, Value>
		Map<Integer, Integer> map1 = convertVectorToMap(v1);
		Map<Integer, Integer> map2 = convertVectorToMap(v2);
		
		if (map1.size() > map2.size()) {
			Map<Integer, Integer> tmp = map1;
			map1 = map2;
			map2 = tmp;
		}
		int sum = 0;
		for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
			int index = entry.getKey();
			if (map2.containsKey(index)) {
				sum += entry.getValue() * map2.get(index);
			}
		}
		System.out.println("Dot product is: " + sum);
	}
	
	/**
	 * 即使是用hash，也能做一些时间空间上的优化，只要存一个hash就好了
	 * 对于第二个向量，遍历然后看在map里面能不能找到相应的值
	 * 时间 O(M+N)， 空间 O(M)
	 */
	public void docProductHash1(int[] v1, int[] v2) {
		// <Index, Value>
		Map<Integer, Integer> map = convertVectorToMap(v1);
		int sum = 0;
		for (int i = 0; i < v2.length; i++) {
			if (v2[i] != 0 && map.containsKey(i) ) {
				sum += v2[i] * map.get(i);
			}
		}
		
		System.out.println("Dot product is: " + sum);
	}
	
	/**
	 * Only process non-zero items, the time complexity is O(n)
	 * 	n is the total length of vector
	 */
	private Map<Integer, Integer> convertVectorToMap(int[] v) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < v.length; i++) {
			if (v[i] != 0) {
				map.put(i, v[i]);
			}
		}
		return map;
	}
	
	/**
	 * Follow up: 
	 * 如果length(B) >>> length(A)，即AB长度一样，但是A比B稀疏很多，
	 * 怎么做能减少时间复杂度。()
	 * 对A里面的每个数，用binary search找B中相对应的值，
	 * 这样时间复杂度是O(nlogm) (n = len(A), m =len(B))
	 * 空间 O(1)
	 * 
	 * input A=[[1, a1], [300, a300], [5000, a5000]]
          	 B=[[100, b100], [300, b300], [1000, b1000]]
	 */
	public void docProductBinary(int[][] v1, int[][] v2) {
		int[][] shortv, longv;
		if (v1.length > v2.length) {
			shortv = v2; longv = v1;
		} else {
			shortv = v1; longv = v2;
		}
		
		int sum = 0;
		for (int i = 0; i < shortv.length; i++) {
			int index = binarySearch(shortv[i][0], longv);
			if (index != -1) {
				sum += shortv[i][1] * longv[index][1];
			}
		}
		System.out.println("Dot product is: " + sum);
	}
	
	private int binarySearch(int i, int[][] v) {
		int start = 0, end = v.length - 1;
		
		while (start + 1 < end) {
			int mid = start + (end - start)/2;
			if (v[mid][0] == i) {
				return mid;
			} else if (v[mid][0] > i) {
				end = mid;
			} else {
				start = mid;
			}
		}
		
		if (v[start][0] == i) {
			return start;
		} else if (v[end][0] == i){
			return end;
		} else {
			return -1;
		}
	}
	
	
	/**
	 * Followup:
	 * 上面用 HashMap的解法，时间 O(M), 空间 O(M) M = max(lenA, lenB)
	 * 用 Binary Search的解法，时间 O(MlogN)， 空间 O(1)
	 * 
	 * 有没有更好，提示divide and conquer，我就说先取一个向量的中间元素，然后搜索他在另一个向
	 * 量中对应元素的位置，这样就把两个矩阵都分别分为两半。他问复杂度，我说我要算一
	 * 下才知道，然后他说他也不知道，不过平均情况应该比前面的好。
	 */
	public void dotProductDivide(int[][] v1, int[][] v2) {
	}
	
	
	/**
	 * 311. Sparse Matrix Multiplication
	 * 矩阵乘法 结果 = A的rows * B的column 简单暴力的做法如下
	 *       |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
		AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
                          | 0 0 1 |
	 */
	 public int[][] multiplySimple(int[][] A, int[][] B) {
		int m = A.length, n = A[0].length, nB = B[0].length;
		int[][] C = new int[m][nB];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (A[i][j] != 0) {  //找好对应关系，才能减少时间复杂度
					for (int k = 0; k < nB; k++) {
						if (B[j][k] != 0)
							C[i][k] += A[i][j] * B[j][k];
					}
				}
			}
		}
		return C;
	 }
	 
	 /**
	  * 其实可以参照sparse vector dot product来做。把非0的点都记录下来。
	  * 这样复杂度就下降了一维，规则就是  A[x1,y1] * B[x2, y2] =  C[x1, y2]
	  */
	class Node {
		int x, y;
		Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public int[][] multiply(int[][] A, int[][] B) {
		int n = A.length, m = A[0].length, mb = B[0].length;
		int[][] result = new int[n][mb];
		List<Node> listA = new ArrayList<>();
        List<Node> listB = new ArrayList<>();
        for (int i=0;i<A.length;i++) {
            for (int j=0; j<A[0].length; j++) {
                if (A[i][j]!=0) listA.add(new Node(i,j));
            }
        }
        
        for (int i=0;i<B.length;i++) {
            for (int j=0;j<B[0].length;j++) {
                if (B[i][j]!=0) listB.add(new Node(i,j));
            }
        }
		
        for (Node nodeA : listA) {
        	for (Node nodeB : listB) {
        		if (nodeA.y == nodeB.x) {
        			result[nodeA.x][nodeB.y] += A[nodeA.x][nodeA.y] * B[nodeB.x][nodeB.y];
        		}
        	}
        }
		return result;
	}
	
	
	public static void main(String[] args) {
		int[] v1 = {0, 4, 0, 0, 2, 3};
		int[] v2 = {0, 7, 6, 0, 0, 1};
		
		SparseVectors clz = new SparseVectors();
		clz.docProductHash1(v1, v2);
		
		int[][] A = {{1, 1},{300, 30},{5000, 50}};
	    int[][] B = {{100, 10},{300, 30},{1000, 100}};
		clz.docProductBinary(A, B);
		
		int[][] C = {{1, 4},{4, 2},{5, 3}};
	    int[][] D = {{1, 7},{2, 5},{5, 1}};
	    clz.docProductBinary(C, D);
	}
}
