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
		}
	}
	
	private static void selectionSort(int[] data) {
		
		
	}
	
	private static void bubbleSort(int[] data) {
		
		
	}
	
	private static void insertionSort(int[] data) {
		
		
	}




}
