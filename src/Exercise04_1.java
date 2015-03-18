import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
 * Bubble sort, selection sort, insertion sort 알고리즘을 구현하라.
 * 크기가 각각 100, 1000, 10000, 100000개인 data를 생성하여 각 알고리즘의 실행시간을 측정하라.
 * 반드시 동일한 데이터로 세가지 알고리즘의 실행시간을 측정해야 한다.
 */
public class Exercise04_1 {
	public static void main(String [] args) {
		Random rd = new Random();
		int[] cases = {100,1000,10000,100000};
		int N;
		int [] data;
		long begin;
		long end;
		
		for(int t=0;t<cases.length;t++) {
			N = cases[t];
			data = new int[N];
			
			System.out.println("<"+N+" data case>");
			for(int i=0;i<N;i++)
				data[i] = rd.nextInt(N);
			
			
			begin = System.currentTimeMillis();
			selectionSort(Arrays.copyOf(data, N));
			end = System.currentTimeMillis();
			System.out.println("Selection Sort: "+(end-begin)/1000.0);
			
			begin = System.currentTimeMillis();
			bubbleSort(Arrays.copyOf(data, N));
			end = System.currentTimeMillis();
			System.out.println("Bubble Sort: "+(end-begin)/1000.0);
			
			begin = System.currentTimeMillis();
			insertionSort(Arrays.copyOf(data, N));
			end = System.currentTimeMillis();
			System.out.println("insertion Sort: "+(end-begin)/1000.0);
			
			begin = System.currentTimeMillis();
			mergeSort(Arrays.copyOf(data, N),0,N-1);
			end = System.currentTimeMillis();
			System.out.println("insertion Sort: "+(end-begin)/1000.0);
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
	
	private static void mergeSort(int[] data, int p, int r) {
		if(p<r) {
			int q = (p+r)/2;
			mergeSort(data, p, q);
			mergeSort(data, q+1, r);
			merge(data, p, q, r);
		}
	}
	private static void merge(int[] data, int p, int q, int r) {
		int i=p, j=q+1, k=p;
		int[] tmp = new int[data.length];
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
