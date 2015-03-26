import java.util.Random;


public class Exercise06_1 {
	public static void main(String [] args) {
		int N=10;
		Random rd = new Random();
		int [] data = new int[N];//{2, 8 , 6, 1, 10, 15, 3, 12, 11};
		for(int i=0;i<N;i++) {
			data[i] = rd.nextInt(N);
		}
		
		System.out.println("data: ");
		for(int i=0;i<N;i++)
			System.out.print(data[i]+", ");
		System.out.println();
		
		heapSort(data, data.length);
		
		System.out.println("sorted data: ");
		for(int i=0;i<N;i++)
			System.out.print(data[i]+", ");
		System.out.println();
	}
	private static void heapSort(int[] data, int size) {
		buildMaxHeap(data);
		int temp;
		
		while(size>0) {
			temp = data[size-1];
			data[size-1] = data[0];
			data[0] = temp;
			size--;
			maxHeapify(data, 0, size);
		}
	}
	private static void buildMaxHeap(int[] data) {
		int heap_size = data.length;
		for(int i=(heap_size-1)/2;i>=0;i--){
			maxHeapify(data, i, heap_size);
		}
	}
	private static void maxHeapify(int[] data, int i, int size){
		if (i*2+1>size-1) {	// 자식 노드가 없다?
			return;
		}
		int k;
		if(i*2+2<=size-1) {	// 자식 노드가 두 개인 경우
			k = data[i*2+1]<data[i*2+2]?i*2+2:i*2+1;
		}else {				// 한 개인 경우
			k = i*2+1;
		}
		if(data[i]>=data[k])
			return ;
		int temp = data[i];
		data[i] = data[k];
		data[k] = temp;
		maxHeapify(data, k, size);
	}
}