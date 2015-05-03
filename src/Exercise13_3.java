import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
/*
 * 예를 들어서 아래와 같은 방식으로 정의된 변수들이 있다. 각 변수들은 수식의 왼쪽에는 오직 한 번만 등장한
 * 다고 가정하자. 모든 변수들의 값이 정해지는가?
 * 	A = B + C
 * 	B = F + D
 * 	F = 5
 *  D = S + 1
 *  S = 3
 *  C = F + S
 * 위의 수식들을 다음과 같은 방향그래프로 표현했을 때 indegree가 0인 노드들은 모두 상수를 값으로 가진 노드
 * 여야 하고, 이 그래프는 DAG여야 한다. 이 그래프의 노드들을 위상정렬한 순서대로 계산하면 모든 변수들의 값
 * 을 계산할 수 있다. 입력으로 위와 같은 수식들을 받아서 모든 변수들의 값을 계산하는 프로그램을 작성하라. 먼
 * 저 수식의 개수가 입력으로 주어지고, 한 줄에 하나씩 수식들이 주어진다. 모든 수식들은 한 번의 덧셈이거나 혹
 * 은 상수값이다.  
 */
public class Exercise13_3 {
	private static final int L_VALUE = 0;
	private static HashMap<String, Integer> hsm;
	private static Node[] g;
	private static int[] visited;	// 위상정렬 알고리즘2를 위한 배열.
	private static Node R;			// 위상정렬 알고리즘2를 위한 연결리스트.
	private static class Node {
		String data;
		int outdeg;
		Node next;
		Node(String x) {
			data = String.copyValueOf(x.toCharArray());
			outdeg=0;	next=null;
		}
	}
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("input13_3.txt"));
			int N = sc.nextInt(); sc.nextLine();
			hsm = new HashMap<String, Integer>();
			String[] notsBuffer = new String[N];
			int i=0, id=1; String[] nots;
			while(sc.hasNextLine()){
				notsBuffer[i] = sc.nextLine();
				nots = notsBuffer[i].split(" ");
				if(!hsm.containsKey(nots[L_VALUE]))
					hsm.put(nots[L_VALUE], id++);
				for(int j=2;j<nots.length;j+=2) {	// 수식의 오른쪽 부분을 읽어 처리.
					if(!hsm.containsKey(nots[j])) {
						hsm.put(nots[j], id++);
					}
				}
				i++;
			} // file read complete
			
			// 인접리스트 만들기.
			g = new Node[hsm.size()+1];	// [0]은 사용하지 않는다.
			constructAdjList(notsBuffer,g);
			testPrintAdj(g);
			
			// 위상 정렬 수행.
			String[] calcOrder = new String[hsm.size()+1];
			topologicalSort2(calcOrder);
			PrintTopol(calcOrder);
			
			// 위상 정렬한 순서대로 연산 수행.
			System.out.println("res = " + calcTopolOrder(calcOrder));
			
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
	
	/* 인접리스트 만들기 */
	private static void add(Node r, String var) {
		Node cur = new Node(var);		// 추가할 노드.
		Node p = r;						// 추가할 위치.
		while(p.next!=null) {
			p = p.next;
		}
		p.next = cur;
		// 인접리스트의 헤드 노드들에 outdegree를 저장 해 둠. 나머지는 모두 0이다.
		r.outdeg++;
	}
	private static void constructAdjList(String[] buf, Node[] g) {
		String[] nots;
		for(int i=0;i<buf.length;i++) {
			nots = buf[i].split(" ");	// 어쩔 수 없이 두 번 토크나이징 함.
			String leftValue = nots[L_VALUE];
			int leftIndex = hsm.get(leftValue);
			if(g[leftIndex]==null)
				g[leftIndex] = new Node(leftValue);
			for(int j=2;j<nots.length;j+=2) {	// 수식의 오른쪽 부분을 읽어 처리.
				int rightIndex = hsm.get(nots[j]);
				if(g[rightIndex]==null)
					g[rightIndex] = new Node(nots[j]);
				add(g[rightIndex],leftValue);
			}
		}
	}
	
	/* 위상정렬 알고리즘 2 */
	private static boolean DFS_TS(String v) {
		boolean isDAG = true;
		visited[hsm.get(v)] = 1;
		Node x = g[hsm.get(v)].next;
		while(x!=null) {
			if(visited[hsm.get(x.data)]==0)
				isDAG = DFS_TS(x.data);
			else if(R==null && g[hsm.get(x.data)].outdeg>0) {
				return false;	// 위상 정렬시 마지막에 와야 하는 노드의 outdegree가 0이 아닌경우 DAG가 아님.
			}
			x = x.next;
		}
		x = new Node(v);
		x.next = R;
		R = x;
		return isDAG;
	}
	private static boolean topologicalSort2(String[] A) {
		visited = new int[g.length];	// [0]은 사용하지 않음.
		R = null;
		for(int i=1;i<g.length;i++) {
			if(visited[i]==0) {
				if(!DFS_TS(g[i].data)) return false;
			}
		}
		for(int i=1;i<A.length;i++) {
			A[i] = R.data;
			R = R.next;
		}
		return true;
	}
	
	/* 위상 정렬된 배열을 이용하여 수식 계산 */
	private static boolean isVar(String v) {	// 입력 스트링이 변수인지 상수인지 판단하는 함수.
		return (v.compareTo("A")>=0&&v.compareTo("Z")<=0);
	} // 변수이면 true, 상수이면 false를 반환.
	private static int calcTopolOrder(String[] A) {
		int[] varTable = new int[hsm.size()+1];	// 변수 테이블 생성.
		for(int i=1;i<A.length;i++) {	// 위상 정렬된 배열에서 순서대로 데이터를 읽음.
			Node ld = g[hsm.get(A[i])];	
			Node st = ld.next;			// 더한 결과를 저장할 주소 노드.
			while(st!=null) {	// ld의 인접한 모든 노드 st에 ld의 값을 더함.
				if(isVar(A[i]))	// 변수이면 변수 테이블에서 값을 불러 옮.
					varTable[hsm.get(st.data)] += varTable[hsm.get(ld.data)];
				else			// 상수이면 위상정렬 배열의 문자열을 int형 값으로 읽음.
					varTable[hsm.get(st.data)] += Integer.parseInt(A[i]);
				st = st.next;
			}
		}
		return varTable[hsm.get(A[A.length-1])];
	}
	
	/* 테스트 및 출력을 위한 함수 */
	private static void testPrintAdj(Node[] list) {
		System.out.println("====== TEST PRINT : AdjList ======");
		for(int i=1;i<list.length;i++) {
			Node cur = list[i];
			System.out.print(cur.data+"("+cur.outdeg+"): ");
			cur = cur.next;
			while(cur!=null) {
				System.out.print(cur.data+", ");
				cur = cur.next;
			}
			System.out.println();
		}
		System.out.println("====== TEST PRINT : AdjList ======");
	}
	private static void PrintTopol(String[] A) {
		System.out.print("topolOrder: ");
		for(int i=1;i<A.length;i++)
			System.out.print(A[i]+" ");
		System.out.println();
	}
}
