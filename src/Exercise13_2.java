import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * �� ���� �������� �˰����� ���� �����϶�. �Է��� ù �ٿ��� ����� ���� n�� ������ ���� m�� �־�����.
 * �̾��� m�ٿ��� �� �� ���� �ϳ��� ������ �־�����. �׷����� ��������Ʈ�� ǥ���϶�. �Է����� �־��� ��
 * ������ DAG�� �ƴϸ� NOT DAG��� ����ϰ� DAG�̸� ���� ���ĵ� ������ ������ ��ȣ�� ����϶�.
 */
public class Exercise13_2 {
	private static final int OUT = 1;
	private static final int IN = 2;
	private static int[] visited;	// �������� �˰���2�� ���� �迭.
	private static Node R;			// �������� �˰���2�� ���� ���Ḯ��Ʈ.
	private static class Node {
		int data,indegree,outdegree;
		Node next;
		Node(int data) {
			indegree=outdegree=0;
			this.data = data; next = null;
		}
	}
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(new File("input13_2.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				int n = sc.nextInt();	// ������ ����.
				int m = sc.nextInt();	// ������ ����.
				int[][]  edges = new int[m][3];	// m���� ������ ������ �迭.
				for(int i=0;i<m;i++) {
					edges[i][OUT] = sc.nextInt();	// ������ ����.
					edges[i][IN] = sc.nextInt();	// ������ ��.
				} // file read complete.

				// ��������Ʈ ����.
				Node[] adjList = new Node[n+1];
				constructAdjList(adjList,edges);
				testPrintAdj(adjList);

				// �������� ����.
				int[] A = new int[n+1];
				if(topologicalSort1(adjList.clone(),edges.clone(),A)) {
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
	private static void constructAdjList(Node[] list, int[][] edges) {
		for(int i=1;i<list.length;i++) {
			list[i] = new Node(i);	// adjList[0]�� ���� ����.
		}	// ������ 1~n���� �׻� �����ȴٰ� ����
		for(int i=0;i<edges.length;i++) {
			Node cur = new Node(edges[i][IN]);	// �߰��� ���.
			Node p = list[edges[i][OUT]];		// �߰��� ��ġ.
			while(p.next!=null) {
				p = p.next;
			}
			p.next = cur;
			// ��������Ʈ�� ��� ���鿡 indegree, outdegree�� ���� �� ��. �������� ��� 0�̴�.
			list[edges[i][IN]].indegree++;
			list[edges[i][OUT]].outdegree++;
		}
	}
	private static int getZeroIndegree(Node[] g, int[][] e) {
		// ���� ������ ���� ������ ��带 ã��. ������ -1�� ��ȯ.
		for(int i=1;i<g.length;i++) {
			if(g[i]!=null && g[i].indegree==0)
				return i;
		}
		return -1;
	}
	private static boolean topologicalSort1(Node[] g, int[][] e, int[] A) {
		int u;
		for(int i=1;i<A.length;i++) {
			u = getZeroIndegree(g,e);			// ���� ������ ���� ������ ���� u�� ����.
			if(u<0) {
				return false;					// �׷� ������ ������ DAG�� �ƴϴ�.
			}
			A[i] = g[u].data;					// �������ĵ� �迭�� �߰�.
			g[u] = null;						// ���� u�� ����.
			for(int j=0;j<e.length;j++) {
				if(e[j][OUT] == u) {
					g[e[j][IN]].indegree--;		// ���� u�� ���� ���� ����.
				}
			}
		}
		return true;
	}
	private static boolean DFS_TS(Node[] g, int v) {
		boolean isDAG = true;
		visited[g[v].data] = 1;
		Node x = g[v].next;
		while(x!=null) {
			if(visited[x.data]==0)
				isDAG = DFS_TS(g,x.data);
			else if(R==null && g[x.data].outdegree>0) {
				return false;	// ���� ���Ľ� �������� �;� �ϴ� ����� outdegree�� 0�� �ƴѰ�� DAG�� �ƴ�.
			}
			x = x.next;
		}
		x = new Node(g[v].data);
		x.next = R;
		R = x;
		return isDAG;
	}
	private static boolean topologicalSort2(Node[] g, int[] A) {
		visited = new int[g.length];	// [0]�� ������� ����.
		R = null;
		for(int i=1;i<g.length;i++) {
			if(visited[i]==0) {
				if(!DFS_TS(g, i)) return false;
			}
		}
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
			System.out.print(cur.data+"("+cur.outdegree+"): ");
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
