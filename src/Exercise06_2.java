import java.util.Random;

/*
 * 2. 우선순위 큐를 구현하는 naive 한 방법의 하나는 그냥 정렬되지 않은 배열을 사용하는 것이다.
 * 새로운 원소는 항상 배열의 맨 끝에 삽입하고, 최대값 삭제는 최대값을 O(N) 시간에 찾아서 삭제하고 배열의 마지막
 * 원소를 최대값이 있던 위치로 이동하여 배열의 중간에 빈 칸이 없도록 처리한다. heap을 이용하여 우선순위큐를
 * 구현한 후 이렇게 단순하게 배열로 구현된 경우와 실행속도를 비교하라. 성능의 비교는 다음과 같이 한다.
 * 우선 N개의 0에서 N사이의 정수를 랜덤하게 생성하여 우선순위큐에 삽입한다. 그런 다음 M번의 삽입 혹은
 * 최대값 삭제 연산을 연속하여 실행하는데 걸리는 총 시간을 측정한다. M번의 연산 각각은 우선 1/2의 확률로
 * 삽입 연산인지 혹은 최대값 삭제 연산인지를 결정한 후, 삽입 연산인 경우에 다시 0에서 N사이의 정수를 랜덤하게 
 * 생성하여 삽입할 데이터를 결정한다.
 */
public class Exercise06_2 {
	static class PQueue {
		int [] data;
		PQueue() { data = new int[100000];}
	}
	private static int heapSize = 0;
	public static void main(String[] args) {
		PQueue pqueue = new PQueue();	// 힙을 이용한 우선순위 큐
		PQueue naive = new PQueue();	// naive한 우선순위 큐
		
		test(pqueue, 10000, 10000);
		heapSize=0;
		naive_test(naive, 10000, 10000);
	}

	private static void naive_test(PQueue queue, int N, int M) {
		Random rd = new Random();
		for(int i=0;i<N;i++) {
			queue.data[i] = rd.nextInt(N);
			heapSize++;
		}	// 테스트를 위한 초기 우선순위 큐를 세팅 해 둔다
		
		long start = System.currentTimeMillis();
		for(int i=0;i<M;i++) {
			if(rd.nextInt(2)==0) {
				queue.data[heapSize++] = rd.nextInt(N);	// 인큐
				//System.out.println(heapSize);
			}
			else if(heapSize>=1) {
				naive_extractMax(queue.data, heapSize);						// 디큐
				//System.out.println(heapSize);
			}
		}
		System.out.println("Naive test Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
	}
	private static int naive_extractMax(int data[], int size) {
		if(heapSize<1) {
			System.out.println("heap underflow");
			return -1;
		}

		int max = data[0];	// 루트 노드가 항상 max
		int max_index = 0;
		for(int i=1;i<size;i++) {
			if(max<data[i]){	// O(N) 시간에 최대값 찾기
				max = data[i];
				max_index=i;
			}	
		}
		data[max_index] = data[heapSize-1];
		heapSize--;	// 힙 사이즈 1감소
	
		return max;
	}
	private static void test(PQueue queue, int N, int M) {
		Random rd = new Random();
		for(int i=0;i<N;i++) {
			queue.data[i] = rd.nextInt(N);
			heapSize++;
		}	// 테스트를 위한 초기 우선순위 큐를 세팅 해 둔다
		buildMaxHeap(queue.data, heapSize); // 초기 큐를 힙으로 만든다
		long start = System.currentTimeMillis();
		for(int i=0;i<M;i++) {
			if(rd.nextInt(2)==0) {
				maxHeapInsert(queue.data, rd.nextInt(N));	// 인큐
				//System.out.println(heapSize);
			}
			else if(heapSize>=1) {
				extractMax(queue.data);						// 디큐
				//System.out.println(heapSize);
			}
		}
		System.out.println("Using Heap Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
	}
	
	private static void maxHeapInsert(int [] data, int key){
		heapSize++;					// 힙 사이즈를 증가시킴
		data[heapSize-1] = key;
		int i = heapSize-1;
		while(i>0 && data[(i-1)/2]<data[i]) {
			int temp = data[i];
			data[i] = data[(i-1)/2];
			data[(i-1)/2] = temp;
			i = (i-1)/2;
		}	// 인서트 할 때마다 모든 노드에 대해 빌드 힙을 할 필요가 없다
	}
	private static int extractMax(int[] data) {
		if(heapSize<1) {
			System.out.println("heap underflow");
			return -1;
		}
		
		int max = data[0];	// 루트 노드가 항상 max
		data[0] = data[heapSize-1];	// 마지막 노드를 루트 노드에 덮어 씀
		heapSize--;	// 힙 사이즈 1감소
		maxHeapify(data, 0, heapSize);
		return max;
	}
	private static void buildMaxHeap(int [] data, int size) {
		for(int i=(size-1)/2;i>=0;i--){
			maxHeapify(data, i, size);
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