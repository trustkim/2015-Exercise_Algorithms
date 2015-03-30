import java.util.Random;

/*
 * 두 양의 정수 N과 K를 입력받은
 */
public class Exercise07_2 {
	private static int N;
	private static int K;
	
	public static void main(String[] args) {
		N = 10;
		K = 5;
		
		Random rd = new Random();
		int [] data = new int[N];
		for(int i=0;i<N;i++) {
			data[i] = rd.nextInt((int)Math.pow(10, K));
		}
		int[] data_B = new int[N]; 
		
		System.out.print("sourceData: ");Print(data);
		radixSort(data,K);
	}
	
	private static void countingSort(int[] A, int[] B, int k, int w) {
		int [] C = new int[k];
		for(int i=0;i<k;i++) {
			int d = (A[i]/(int)Math.pow(10, w))%10;	// 이번에 검사하는 자리 수를 뽑아 냄
			C[d]++;					// 카운터에 카운트
		}
		//Print(C);
		for(int i=1;i<k;i++) {
			C[i]=C[i]+C[i-1];			// 누적합
		}
		System.out.print("sum test: ");Print(C);
		for(int j=A.length-1;j>=0;j--) {
			int d = (A[j]/(int)Math.pow(10, w))%10;
			B[C[d]-1] = A[j];
			C[d] = C[d] - 1;
		}
		System.out.print("countingSort test: ");Print(B);
		
		for(int i=0;i<N;i++)
			A[i] = B[i];
	}
	
	private static void radixSort(int[] data, int k) {
		int[] data_B = new int[N];
		for(int t=0;t<k;t++) {
			countingSort(data, data_B, 10, t);
		}
	}
	
	private static void Print(int[] data) {
		for(int i=0;i<data.length;i++) {
			System.out.print(data[i]+", ");
		}
		System.out.println();
	}
}
