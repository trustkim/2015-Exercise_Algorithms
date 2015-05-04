import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * �� ���� �������� �˰����� ���� �����϶�. �Է��� ù �ٿ��� ����� ���� n�� ������ ���� m�� �־�����.
 * �̾��� m�ٿ��� �� �� ���� �ϳ��� ������ �־�����. �׷����� ��������Ʈ�� ǥ���϶�. �Է����� �־��� ��
 * ������ DAG�� �ƴϸ� NOT DAG��� ����ϰ� DAG�̸� ���� ���ĵ� ������ ������ ��ȣ�� ����϶�.
 */
public class Exercise13_2 {
	private static Node[] adjList;	// �׷����� ǥ���ϴ� ��������Ʈ.
	private static int[] indegree;	// indegree�� ���� �迭.
	private static int[] outdegree;	// outdegree�� ���� �迭.

	private static int[] visited;	// �������� �˰���2�� ���� �迭.
	private static Node R;			// �������� �˰���2�� ���� ���Ḯ��Ʈ. �������� �����ִ� DFS�� ����� ������ �ȵȴ�.
	private static class Node {
		int data;
		Node next;
		Node(int data) {
			this.data = data; next = null;
		}
	}
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(new File("input13_2.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				int n = sc.nextInt();				// ������ ����.
				int m = sc.nextInt();				// ������ ����.
				adjList = new Node[n+1];
				for(int i=1;i<adjList.length;i++) {
					adjList[i] = new Node(i);		// ��������Ʈ �ʱ�ȭ. adjList[0]�� ���� ����.
				}	// ������ 1~n���� �׻� �����ȴٰ� ����
				indegree = new int[n+1];			// [0]�� ��� ����.
				outdegree = new int[n+1];			// [0]�� ��� ����.
				
				for(int i=0;i<m;i++) {
					int outcoming = sc.nextInt();	// ������ ����.
					int incoming = sc.nextInt();	// ������ ��.
					add(outcoming,incoming);
					outdegree[outcoming]++;
					indegree[incoming]++;
				} // file read complete. ��������Ʈ ����.
				testPrintAdj(adjList);

				// �������� ����.
				int[] A = new int[n+1];
				if(topologicalSort1(adjList.clone(),A)) {
					System.out.print("topologicalSort1: ");
					PrintTopol(A);
				}else System.out.println("NOT DAG");

				int[] B = new int[n+1];
				if(topologicalSort2(adjList.clone(),B)) {
					System.out.print("topolgicalSort2: ");
					PrintTopol(B);
				}else System.out.println("NOT DAG");
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
	private static void add(int outcoming, int incoming) {
		Node cur = new Node(incoming);		// �߰��� ���.
		Node p = adjList[outcoming];		// �߰��� ��ġ.
		while(p.next!=null) {
			p = p.next;
		}
		p.next = cur;
	} // �̰� Exercise12_1���� ��������Ʈ Ŭ������ ����� ��� �Լ��� ���� �� ����. ������ ����.

	private static boolean topologicalSort1(Node[] g, int[] A) {
		Queue<Integer> queue = new LinkedList<Integer>();	// indegree�� 0�� ��带 ������ ť.
		for(int i=1;i<indegree.length;i++) {
			if(indegree[i]==0) queue.offer(i);				// �ʱ� indegree�� 0�� ��带 ��ť�ϰ� ����.
		}
		
		int u; int cnt=0;		// u�� �湮�� ���, cnt�� �湮�� Ƚ��. 
		while(!queue.isEmpty()) {
			cnt++;
			u = queue.poll();					// ���� ������ ���� ������ ���� u�� ����.
			A[cnt] = u;							// �������� ������ ���
			Node v = adjList[u];				// u�� ���� ���.
			if(v!=null) v = v.next;				// ��������Ʈ�� ù ���� �Ѿ�� ���� ������ üũ�ؾ���.
			while(v!=null) {					// ���� u�� ����.
				indegree[v.data]--;				// ���� u�� ������ ��� ����� indegree�� 1 ����.
				if(indegree[v.data]==0)
					queue.offer(v.data);		// �������� �� 0�̸� ��ť
				v = v.next;
			}
		}										// ��ü ��带 �湮���� �ʰ� while���� ���� �Ǹ� DAG�� �ƴ�.
		if(cnt<g.length-1) return false;		// g.length�� ��ü ���+1 [0]�� �Ⱦ��Ƿ�.
		return true;
	}
	private static boolean DFS_TS(int v) {
		boolean isDAG = true;
		visited[v]++;
		Node x = adjList[v].next;
		while(x!=null) {
			if(visited[x.data]==0)
				isDAG = DFS_TS(x.data);
			else if(visited[x.data]==1) {
				return false;	// ���� ���Ľ� �������� �;� �ϴ� ����� outdegree�� 0�� �ƴѰ�� DAG�� �ƴ�.
			}
			x = x.next;
		}
		
		x = new Node(adjList[v].data);
		x.next = R;
		R = x;
		visited[x.data]++;	// DFS�� �Ž��� �ö󰡸鼭 �ι�° üũ.
		return isDAG;
	}
	private static boolean topologicalSort2(Node[] g, int[] A) {
		visited = new int[g.length];	// [0]�� ������� ����. DFS ���� �� �ִ� �� ������ üũ��.
		R = null;
		for(int i=1;i<g.length;i++) {
			if(visited[i]==0) {
				if(!DFS_TS(g[i].data)) return false;
			}
		}

		// R�� A�� �ٲ㼭 ��ȯ��. ���� �����൵ ��� ����.
		for(int i=1;i<A.length;i++) {
			A[i] = R.data;
			R = R.next;
		}
		return true;
	}
	private static void testPrintAdj(Node[] list) {
		System.out.println("====== TEST PRINT : AdjList ======");
		for(int i=1;i<list.length;i++) {
			Node cur = list[i];
			System.out.print(cur.data+"("+outdegree[cur.data]+"): ");
			cur = cur.next;
			while(cur!=null) {
				System.out.print(cur.data+", ");
				cur = cur.next;
			}
			System.out.println();
		}
		System.out.println("====== TEST PRINT : AdjList ======");
	}
	private static void PrintTopol(int[] A) {
		for(int i=1;i<A.length;i++)
			System.out.print(A[i]+" ");
		System.out.println();
	}
}
