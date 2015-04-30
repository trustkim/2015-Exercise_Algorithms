import java.util.LinkedList;

/*
 * �ϳ��� �׷����� ���� ��������� �Է����� �־��� �� �� �׷����� ���� ��������Ʈ�� ����� ���α׷���
 * �ۼ��϶�. �Է����� ���� ������ ���� n�� �־�����, �̾ n*nũ���� ����� �־�����. ��������Ʈ��
 * ���� �� �� ������ ���ؼ� ������ �������� ����϶�. ������ ��ȣ�� 1������ n�������̴�. �Ʒ��� �����
 * ���� ���� �׸��� �׷����� ����̴�. (��¸� �ؼ��� �ȵǸ� ������ ���¸���Ʈ�� ������ �Ѵ�.)
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
		LinkedList<Integer>[] adjList = new LinkedList[N];	// ���� ����Ʈ ����
		
		transAdjArr(adjArr,adjList);						// ���� ����� ���� ����Ʈ�� ��ȯ
		PrintAdjList(adjList);								// ���� ����Ʈ�� ���
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
					// ������ �׷��������� ��������� ��Ī����̴�.
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
//	}	// ������ �ڵ�
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
