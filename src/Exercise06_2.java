
public class Exercise06_2 {
	public static void main(String[] args) {
		int [] data = new int[100000];
	}
	
	private static void buildMaxHeap(int [] data) {
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