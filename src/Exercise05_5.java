import java.util.Arrays;
import java.util.Random;

/*
 * Selection 문제는 n개이 정수들 중에서 k번째로 작은 정수를 찾는 문제이다. k는 입력으로 주어진다.
 * 먼저 정렬을 한 후 k번째 정수를 찾는다면 시간복잡도는 O(nlogn)이 된다.
 * quickselection이라고 부르는 다른 한 가지 방법은 quicksort의 partition 함수를 이용하는 것이다.
 * 즉 먼저 주어진 정수들을 quicksort에서 처럼 하나의 값을 pivot으로 선택하여 pivot보다 작은 값들과 큰 값들로 분할 한다.
 * pivot보다 작은 값들의 개수가 p개라고 가정하자. 만약 p>=k라면 그 들 중에서 k번째로 작은 수를 recursion으로 찾는다.
 * 만약 p=k-1라면 pivot이 바로 k번째로 작은 수이다. p<k-1라면 이제 pivot보다 큰 수들 중에서 k-p-1번째로 작은 수를 리커전으로 찾는다.
 * 이 방법의 최악의 경우 시간복잡도는 quicksort의 경우와 마찬가지로 O(n^2)이다.
 * 하지만 평균시간복잡도는 O(n)이다.
 * quickselection 알고리즘을 구현한 후 정렬 알고리즘과 마찬가지로 100000개의 데이터에 대해서 정렬을 한 후 k번째 값을 찾는 방법과 비교하라.
 * (입력에 동일한 정수들이 있는 경우: k번째로 작은 정수는 정렬을 했을 때 k번째에 위치하는 값을 의미함. 가령 정수들이 1, 2, 2, 2, 3, 4이고 k=3이면 2를 출력하면 됨). 
 */

public class Exercise05_5 {
	private static int k;
	public static void main(String [] args) {
		Random rd = new Random();
		int N = 100000;
		int [] data = new int[N];
		int [] temp = new int[N];
		for(int i=0;i<N;i++)
			data[i] = rd.nextInt(N);
		
//		System.out.println("input data:");
//		for(int i=0;i<N;i++)
//			System.out.print(data[i]+", ");
//		System.out.println();
		
		k = 55555;								// input k
		
		temp = Arrays.copyOf(data, N);
		long start = System.currentTimeMillis();
		quickSort(temp, 0, N-1);				// quickSort -> pick k-th data
		System.out.println("k-th data: "+temp[k-1]);
		System.out.println("Elapsed: "+((long) System.currentTimeMillis()-start)/1000.0);
//		System.out.println("sorted data:");
//		for(int i=0;i<N;i++)
//			System.out.print(temp[i]+", ");
//		System.out.println();
		
		temp = Arrays.copyOf(data, N);
		start = System.currentTimeMillis();
		quickSelection(temp, 0, N-1);
		System.out.println("Elapsed: "+((long) System.currentTimeMillis()-start)/1000.0);
	}
	
	private static void quickSort(int[] data, int p, int r) {
		if(p<r) {
			int q = partition(data, p, r);
			quickSort(data, p, q-1);
			quickSort(data, q+1, r);
		}
	}
	private static int partition(int[] data, int p, int r) {
		int median = data[(p+r)/2];
		int x,temp;
		int max = Math.max(Math.max(data[p], median), data[r]); 
		if(max==data[p]) {
			x = median<data[r]?data[r]:median;
			temp = median<data[r]?r:((p+r)/2);
		}else if (max==median) {
			x = data[p]<data[r]?data[r]:data[p];
			temp = data[p]<data[r]?r:p;
		}else {
			x = data[p]<median?median:data[p];
			temp = data[p]<median?((p+r)/2):p;
		}
		data[temp] = data[r];
		data[r] = x;
		
		int i=p-1;
		for(int j=p;j<r;j++) {
			if(data[j] < x) {
				i++;
				temp = data[j];
				data[j] = data[i];
				data[i] = temp;
			}
		}
		
		temp = data[i+1];
		data[i+1] = data[r];
		data[r] = temp;
		
		return i+1;
	}
	private static void quickSelection(int[] data, int p, int r) {
		if(p<r) {
			int q = partition(data, p, r);
			if(k-1==q)	{// 피벗보다 작은 값의 개수가 k-1인 경우
				System.out.println("k-th data: "+data[q]);
				return;
			}
			else if(k-1<q){	// 피벗보다 작은 값의 개수가 k보다 큰 경우
				quickSelection(data, p, q-1);
			}else if(k-1>q)	// 피벗보다 작은 값의 개수가 k보다 큰 경우
				quickSelection(data, q+1, r);
		}else
			System.out.println("k-th data: " + data[k-1]);		// 정렬을 결국 모두 마친 경우
	}
}
