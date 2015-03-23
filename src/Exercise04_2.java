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
		int [] temp;
		long begin; long end;
		double selectionSum;
		double bubbleSum;
		double insertionSum;
		double mergeSum;
		double table[][] = new double[4][4];

		System.out.println("N\tSelection sort\tBubble sort\tInsertion sort\tMerge sort");
		
		for(int t=0;t<cases.length;t++){
			N = cases[t];
			data = new int[N];
			temp = new int[N];
			selectionSum = 0;
			bubbleSum = 0;
			insertionSum = 0;
			mergeSum=0;
			for(int i=0;i<10;i++) {
				for(int j=0;j<N;j++)
					data[j] = rd.nextInt(N);
				
				temp = Arrays.copyOf(data, N);
				begin = System.currentTimeMillis();
				selectionSort(temp);
				end = System.currentTimeMillis();
				selectionSum+=(end-begin)/1000.0;
				
				temp = Arrays.copyOf(data, N);
				begin = System.currentTimeMillis();
				bubbleSort(temp);
				end = System.currentTimeMillis();
				bubbleSum+=(end-begin)/1000.0;
				
				temp = Arrays.copyOf(data, N);
				begin = System.currentTimeMillis();
				insertionSort(temp);
				end = System.currentTimeMillis();
				insertionSum+=(end-begin)/1000.0;
				
				temp = Arrays.copyOf(data, N);
				begin = System.currentTimeMillis();
				mergeSort(temp,0,N-1);
				end = System.currentTimeMillis();
				mergeSum+=(end-begin)/1000.0;
			}
			table[t][0]=selectionSum/10;
			table[t][1]=bubbleSum/10;
			table[t][2]=insertionSum/10;
			table[t][3]=mergeSum/10;
			
			System.out.print(cases[t]+"\t");
			for(int j=0;j<4;j++)
				System.out.print(String.format("%.4f", table[t][j])+"\t\t");
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
			}
			max = data[tempIndex];			// swap을 한 번만 함
			data[tempIndex] = data[last];
			data[last] = max;
			
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
	private static int swp;
	private static void insertionSort(int[] data) {
		for(int i=1;i<data.length;i++) {	// i는 오름차순으로 진행
			int j = i-1; int temp = data[i];
			while(j>=0&&data[j]>temp) {	// j는 내림차순으로 진행
				swp = data[j]; // swap(data[j], data[j+1]) not swap(data[j], data[i])!
				data[j] = data[j+1];
				data[j+1] = swp;	// 매번 스왑하지 않고 한번씩만 쓰기 할 순 없을까? 스왑을 한 번만 해도 쓰기를 세 번하는게 더 영향이 큰듯?
				j--;
			}
			data[j+1] = temp;	// data[j] < temp인 경우 while문 탈출하면서 j-- 되있으므로 j+1에 둬야함.
		}
	}
	
	private static void mergeSort(int[] data, int p, int r) {
		if(p<r) {
			int q = (p+r)/2;
			mergeSort(data, p, q);
			mergeSort(data, q+1, r);
			merge(data, p, q, r);
		}
	}
	public static int [] tmp = new int[100000];
	private static void merge(int[] data, int p, int q, int r) {
		int i=p, j=q+1, k=p;
		while(i<=q&&j<=r) {
			if(data[i]<=data[j])
				tmp[k++]=data[i++];
			else
				tmp[k++]=data[j++];
		}
		while(i<=q)
			tmp[k++]=data[i++];
		while(j<=r)
			tmp[k++]=data[j++];
		for(i=p;i<=r;i++)
			data[i]=tmp[i];
	}
}
