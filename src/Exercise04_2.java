import java.util.Arrays;
import java.util.Random;

/*
 * 1번에서 작성한 프로그램을 수정/이용하여 N=100, 1000, 10000, 100000에 대해서 각각 100개씩의 랜덤
 * 데이터를 생성하여 3가지 정렬 알고리즘으로 정렬할 때 평균 실행시간을 측정하여 아래와 같은 형식의 테이블로 출력하는
 * 프로그램을 작성하라.
 */
public class Exercise04_2 {
	public static void main(String [] args) {
		Random rd = new Random();
		int[] cases = {100, 1000, 10000, 100000};
		int N;
		int [] data;
		long begin; long end;
		long selectionSum;
		long bubbleSum;
		long insertionSum;
		double table[][] = new double[4][3];

		System.out.println("N\tSelection sort\tBubble sort\tInsertion sort");
		
		for(int t=0;t<cases.length;t++){
			N = cases[t];
			data = new int[N];
			selectionSum = 0;
			bubbleSum = 0;
			insertionSum = 0;
			for(int i=0;i<100;i++) {
				for(int j=0;j<N;j++)
					data[j] = rd.nextInt(N);
				
				begin = System.currentTimeMillis();
				selectionSort(Arrays.copyOf(data, N));
				end = System.currentTimeMillis();
				selectionSum+=end-begin;
				
				begin = System.currentTimeMillis();
				bubbleSort(Arrays.copyOf(data, N));
				end = System.currentTimeMillis();
				bubbleSum+=end-begin;
				
				begin = System.currentTimeMillis();
				insertionSort(Arrays.copyOf(data, N));
				end = System.currentTimeMillis();
				insertionSum+=end-begin;
			}
			table[t][0]=selectionSum/1000.0/N;
			table[t][1]=bubbleSum/1000.0/N;
			table[t][2]=insertionSum/1000.0/N;
		}

		for(int i=0;i<cases.length;i++){
			System.out.print(cases[i]+"\t");
			for(int j=0;j<3;j++)
				System.out.print(table[i][j]+"\t\t");
			System.out.println();
		}

	}
	
	private static void selectionSort(int[] data) {
		int max; int tempIndex;
		for(int last=data.length-1;last>0;last--) {
			tempIndex=0;
			for(int i=1;i<=last;i++) {
				if(data[tempIndex]<data[i]) {
					tempIndex=i;
				}
				max = data[tempIndex];
				data[tempIndex] = data[last];
				data[last] = max;
			}
			
		}
		
	}
	
	private static void bubbleSort(int[] data) {
		int temp;
		for(int last=data.length-1;last>0;last--) {
			for(int i=0;i<last;i++) {
				if(data[i]>data[i+1]) {
					temp = data[i];
					data[i] = data[i+1];
					data[i+1] = temp;
				}
			}
		}
		
	}
	
	private static void insertionSort(int[] data) {
		for(int i=1;i<data.length;i++) {
			int j=i-1;
			while(j>=0&&data[i]<data[j]) {
				data[j+1]=data[j];
				j--;
			}
			data[j+1]=data[i];
		}
	}
}
