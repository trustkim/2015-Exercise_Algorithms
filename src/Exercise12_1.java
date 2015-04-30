import java.util.LinkedList;

/*
 * 하나의 그래프에 대한 인접행렬이 입력으로 주어질 때 그 그래프에 대한 인접리스트를 만드는 프로그램을
 * 작성하라. 입력으로 먼저 정점의 개수 n이 주어지고, 이어서 n*n크기의 행렬이 주어진다. 인접리스트를
 * 만든 후 각 정점에 대해서 인접한 정점들을 출력하라. 정점의 번호는 1번에서 n번까지이다. 아래의 입출력
 * 예는 다음 그림의 그래프의 경우이다. (출력만 해서는 안되며 실제로 연력리스트를 만들어야 한다.)
 */
public class Exercise12_1 {
	public static void main(String [] args){
		final int N = 5;	// number of vertex
		//int[][] adjArr = new int[N][N];
		int[][] adjArr = {{0,1,0,0,1},
						{1,0,1,1,1},
						{0,1,0,1,0},
						{0,1,1,0,1},
						{1,1,0,1,0}};
		
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adjList = new LinkedList[N];	// 인접 리스트 생성
		
		transAdjArr(adjArr,adjList);						// 인접 행렬을 인접 리스트로 변환
		PrintAdjList(adjList);								// 인접 리스트를 출력
	}
	private static void transAdjArr(int[][] arr, LinkedList<Integer>[] list) {
		final int CHECKED = 0;
		for(int i=0;i<arr.length;i++) {
			if(list[i]==null) list[i] = new LinkedList<Integer>();
			for(int j=0;j<arr[i].length;j++) {
				if(arr[i][j]==1) {
					list[i].add(j+1); arr[i][j] = CHECKED;
					if(list[j]==null) list[j] = new LinkedList<Integer>();
					list[j].add(i+1); arr[j][i] = CHECKED;	
					// 무방향 그래프에서는 인접행렬이 대칭행렬이다.
				}
			}
		}
	}
//	public void constructAdjList() {
//		adjList = new Node[N+1];
//		for(int i=0;i<N;i++) {
//			for(int j=0;j<N;j++) {
//				if(adjMatrix[i][j]==1) {
//					Node node = new Node(j+1);
//					// insert to adjList[i+1]
//					
//					
//				}
//			}
//		}
//	}	// 교수님 코드
	private static void PrintAdjList(LinkedList<Integer>[] list) {
		for(int i=0;i<list.length;i++) {
			System.out.print(i+1+": ");
			for(int j:list[i]) {
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}
}
