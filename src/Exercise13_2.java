import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * 두 가지 위상정렬 알고리즘을 각각 구현하라. 입력의 첫 줄에는 노드의 개수 n과 에지의 개수 m이 주어진다.
 * 이어진 m줄에는 각 줄 마다 하나의 에지가 주어진다. 그래프는 인접리스트로 표현하라. 입력으로 주어진 그
 * 래프가 DAG가 아니면 NOT DAG라고 출력하고 DAG이면 위상 정렬된 순서로 노드들의 번호를 출력하라.
 */
public class Exercise13_2 {
	private static final int OUT = 1;
	private static final int IN = 2;
	private static int[] visited;	// 위상정렬 알고리즘2를 위한 배열.
	private static Node R;			// 위상정렬 알고리즘2를 위한 연결리스트.
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
				int n = sc.nextInt();	// 정점의 개수.
				int m = sc.nextInt();	// 에지의 개수.
				int[][]  edges = new int[m][3];	// m개의 에지를 저장할 배열.
				for(int i=0;i<m;i++) {
					edges[i][OUT] = sc.nextInt();	// 에지의 시작.
					edges[i][IN] = sc.nextInt();	// 에지의 끝.
				} // file read complete.

				// 인접리스트 생성.
				Node[] adjList = new Node[n+1];
				constructAdjList(adjList,edges);
				testPrintAdj(adjList);

				// 위상정렬 수행.
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
			list[i] = new Node(i);	// adjList[0]은 쓰지 않음.
		}	// 정점은 1~n까지 항상 생성된다고 가정
		for(int i=0;i<edges.length;i++) {
			Node cur = new Node(edges[i][IN]);	// 추가할 노드.
			Node p = list[edges[i][OUT]];		// 추가할 위치.
			while(p.next!=null) {
				p = p.next;
			}
			p.next = cur;
			// 인접리스트의 헤드 노드들에 indegree, outdegree를 저장 해 둠. 나머지는 모두 0이다.
			list[edges[i][IN]].indegree++;
			list[edges[i][OUT]].outdegree++;
		}
	}
	private static int getZeroIndegree(Node[] g, int[][] e) {
		// 진입 간선이 없는 임의의 노드를 찾음. 없으면 -1를 반환.
		for(int i=1;i<g.length;i++) {
			if(g[i]!=null && g[i].indegree==0)
				return i;
		}
		return -1;
	}
	private static boolean topologicalSort1(Node[] g, int[][] e, int[] A) {
		int u;
		for(int i=1;i<A.length;i++) {
			u = getZeroIndegree(g,e);			// 진입 간선이 없는 임의의 정점 u를 선택.
			if(u<0) {
				return false;					// 그런 정점이 없으면 DAG가 아니다.
			}
			A[i] = g[u].data;					// 위상정렬될 배열에 추가.
			g[u] = null;						// 정점 u를 제거.
			for(int j=0;j<e.length;j++) {
				if(e[j][OUT] == u) {
					g[e[j][IN]].indegree--;		// 정점 u의 진출 간선 제거.
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
				return false;	// 위상 정렬시 마지막에 와야 하는 노드의 outdegree가 0이 아닌경우 DAG가 아님.
			}
			x = x.next;
		}
		x = new Node(g[v].data);
		x.next = R;
		R = x;
		return isDAG;
	}
	private static boolean topologicalSort2(Node[] g, int[] A) {
		visited = new int[g.length];	// [0]은 사용하지 않음.
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
