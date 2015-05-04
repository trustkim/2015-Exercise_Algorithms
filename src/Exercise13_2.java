import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * 두 가지 위상정렬 알고리즘을 각각 구현하라. 입력의 첫 줄에는 노드의 개수 n과 에지의 개수 m이 주어진다.
 * 이어진 m줄에는 각 줄 마다 하나의 에지가 주어진다. 그래프는 인접리스트로 표현하라. 입력으로 주어진 그
 * 래프가 DAG가 아니면 NOT DAG라고 출력하고 DAG이면 위상 정렬된 순서로 노드들의 번호를 출력하라.
 */
public class Exercise13_2 {
	private static Node[] adjList;	// 그래프를 표현하는 인접리스트.
	private static int[] indegree;	// indegree를 담을 배열.
	private static int[] outdegree;	// outdegree를 담을 배열.

	private static int[] visited;	// 위상정렬 알고리즘2를 위한 배열.
	private static Node R;			// 위상정렬 알고리즘2를 위한 연결리스트.
	private static class Node {
		int data;
		int outdegree;				// 노드 내부에서 하는건 부자연 스러우므로 바꾸기로 한다.
		Node next;
		Node(int data) {
			outdegree=0;			// 별도의 배열로 만들 것.
			this.data = data; next = null;
		}
	}
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(new File("input13_2.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				int n = sc.nextInt();				// 정점의 개수.
				int m = sc.nextInt();				// 에지의 개수.
				adjList = new Node[n+1];
				for(int i=1;i<adjList.length;i++) {
					adjList[i] = new Node(i);		// 인접리스트 초기화. adjList[0]은 쓰지 않음.
				}	// 정점은 1~n까지 항상 생성된다고 가정
				indegree = new int[n+1];			// [0]은 사용 안함.
				outdegree = new int[n+1];			// [0]은 사용 안함.
				
				for(int i=0;i<m;i++) {
					int outcoming = sc.nextInt();	// 에지의 시작.
					int incoming = sc.nextInt();	// 에지의 끝.
					add(outcoming,incoming);
					outdegree[outcoming]++;
					indegree[incoming]++;
				} // file read complete. 인접리스트 생성.
				testPrintAdj(adjList);

				// 위상정렬 수행.
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
		Node cur = new Node(incoming);		// 추가할 노드.
		Node p = adjList[outcoming];		// 추가할 위치.
		while(p.next!=null) {
			p = p.next;
		}
		p.next = cur;
	}
	private static boolean topologicalSort1(Node[] g, int[] A) {
		Queue<Integer> queue = new LinkedList<Integer>();	// indegree가 0인 노드를 저장할 큐.
		for(int i=1;i<indegree.length;i++) {
			if(indegree[i]==0) queue.offer(i);				// 초기 indegree가 0인 노드를 인큐하고 시작.
		}
		
		int u; int cnt=0;		// u는 방문할 노드, cnt는 방문한 횟수. 
		while(!queue.isEmpty()) {
			cnt++;
			u = queue.poll();					// 진입 간선이 없는 임의의 정점 u를 선택.
			A[cnt] = u;							// 위상정렬 순으로 출력
			Node v = adjList[u];				// u의 인접 노드.
			if(v!=null) v = v.next;				// 인접리스트의 첫 노드는 넘어가서 다음 노드부터 체크해야함.
			while(v!=null) {					// 정점 u를 제거.
				indegree[v.data]--;				// 정점 u에 인접한 모든 노드의 indegree를 1 감소.
				if(indegree[v.data]==0)
					queue.offer(v.data);		// 감소했을 때 0이면 인큐
				v = v.next;
			}
		}										// 전체 노드를 방문하지 않고 while문이 종료 되면 DAG가 아님.
		if(cnt<g.length-1) return false;		// g.length는 전체 노드+1 [0]을 안쓰므로.
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
