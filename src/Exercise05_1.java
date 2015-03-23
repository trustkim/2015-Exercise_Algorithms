import java.util.Arrays;
import java.util.Random;

/*
 * 1번에서 작성한 프로그램을 수정/이용하여 N=100, 1000, 10000, 100000에 대해서 각각 100개씩의 랜덤
 * 데이터를 생성하여 3가지 정렬 알고리즘으로 정렬할 때 평균 실행시간을 측정하여 아래와 같은 형식의 테이블로 출력하는
 * 프로그램을 작성하라.
 */
public class Exercise05_1 {
	private static int N;
	public static void main(String [] args) {
		Random rd = new Random();
		int[] cases = {100, 1000, 10000, 100000};
		int [] data;
		int [] temp;
		long begin; long end;
		double selectionSum;
		double bubbleSum;
		double insertionSum;
		double mergeSum;
		double quickSum;
		double medQuickSum;
		double table[][] = new double[4][6];

		System.out.println((4+9)/2);
		System.out.println("Sort test");
		int[] test = {9,5,2,6,3,3,1,4,2,2};
//		for(int j=0;j<10;j++)
//			test[j] = rd.nextInt(10);
		for(int i=0;i<10;i++)
			System.out.print(test[i]+", ");
		System.out.println();
		medQuickSort(test, 0, 10-1);
		System.out.println("result: ");
		for(int i=0;i<10;i++)
			System.out.print(test[i]+", ");
		System.out.println();
		
		System.out.println("N\tSelection sort\tBubble sort\tInsertion sort\tMerge sort\tQuick sort\tQuick sort(Median of three)");
		
		for(int t=0;t<cases.length;t++){
			N = cases[t];
			data = new int[N];
			temp = new int[N];
			selectionSum = 0;
			bubbleSum = 0;
			insertionSum = 0;
			mergeSum=0;
			quickSum=0;
			medQuickSum=0;
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
				
				temp = Arrays.copyOf(data, N);
				begin = System.currentTimeMillis();
				quickSort(temp,0,N-1);
				end = System.currentTimeMillis();
				quickSum+=(end-begin)/1000.0;
				
				temp = Arrays.copyOf(data, N);
				begin = System.currentTimeMillis();
				medQuickSort(temp,0,N-1);
				end = System.currentTimeMillis();
				medQuickSum+=(end-begin)/1000.0;
			}
			table[t][0]=selectionSum/10;
			table[t][1]=bubbleSum/10;
			table[t][2]=insertionSum/10;
			table[t][3]=mergeSum/10;
			table[t][4]=quickSum/10;
			table[t][5]=medQuickSum/10;
			
			System.out.print(cases[t]+"\t");
			for(int j=0;j<6;j++)
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
	
	private static void quickSort(int [] data, int p, int r) {
		int q;	// 피벗
		if(p<r) {
			q = partition(data, p, r);
			quickSort(data, p, q-1);
			quickSort(data, q+1, r);
		}
	}
	private static int partition(int[] data, int p, int r) {
		int x=data[r];	// 피벗으로 마지막 값을 선택
		int i=p-1;		// 피벗보다 작은 파티션의 인덱스 (피벗보다 작은 값 중 가장 큰 값) 초기화
		int temp;
		
		for(int j=p; j<r;j++)	// r-1까지는 검사해야 함
			if(data[j]<=x) {
				i++;
				temp = data[i];
				data[i] = data[j];
				data[j] = temp;
			}
		temp = data[i+1];
		data[i+1] = data[r];
		data[r] = temp;
		return i+1;	// 피벗이 최소값인 경우에도 아웃바운드 익셉션이 발생 안된다!
	}
	
	private static void medQuickSort(int [] data, int p, int r) {
		int q;	// 피벗
		if(p<r) {
			q = medPartition(data, p, r);
			medQuickSort(data, p, q-1);
			medQuickSort(data, q+1, r);
		}
	}
	private static int medPartition(int[] data, int p, int r) {
		int median=data[(p+r)/2];	// 피벗으로 마지막 값을 선택
		int i=p-1;					// 피벗보다 작은 파티션의 인덱스 (피벗보다 작은 값 중 가장 큰 값) 초기화
		int temp;
		int x;
		
		int max =	Math.max(Math.max(data[p], median),data[r]);	// 같은 경우 같은 값을 반환. 최대값은 유일해야 하는 값은 아님
		if(max==data[p]) {		// 처음이 최대값인 경우
			x = median<data[r]?data[r]:median;	// 남은 것 중 작지 않은-중간값을 저장. 같으면 굳이 swap 할 필요 없음
			temp = median<data[r]?r:((p+r)/2);	// 중간값의 인덱스를 저장
		}else if(max==median) {	// 가운데가 최대값인 경우
			x = data[p]<data[r]?data[r]:data[p];
			temp = data[p]<data[r]?r:p;
		}else {					// 마지막이 최대값인 경우
			x = data[p]<median?median:data[p];
			temp = data[p]<median?((p+r)/2):p;
		}
		// 선택한 피벗을 마지막으로 옮김
		data[temp] = data[r];
		data[r] = x;
		
		for(int j=p; j<r;j++)	// r-1까지는 검사해야 함
			if(data[j]<=x) {		// 피벗이 크거나 같다? 크다? 차이 없음
				i++;
				temp = data[i];
				data[i] = data[j];
				data[j] = temp;
			}
		temp = data[i+1];
		data[i+1] = data[r];
		data[r] = temp;
		return i+1;	// 피벗이 최소값인 경우에도 아웃바운드 익셉션이 발생 안된다!
	}
}