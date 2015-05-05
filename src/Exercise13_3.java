import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
/*
 * ���� �� �Ʒ��� ���� ������� ���ǵ� �������� �ִ�. �� �������� ������ ���ʿ��� ���� �� ���� ������
 * �ٰ� ��������. ��� �������� ���� �������°�?
 * 	A = B + C
 * 	B = F + D
 * 	F = 5
 *  D = S + 1
 *  S = 3
 *  C = F + S
 * ���� ���ĵ��� ������ ���� ����׷����� ǥ������ �� indegree�� 0�� ������ ��� ����� ������ ���� ���
 * ���� �ϰ�, �� �׷����� DAG���� �Ѵ�. �� �׷����� ������ ���������� ������� ����ϸ� ��� �������� ��
 * �� ����� �� �ִ�. �Է����� ���� ���� ���ĵ��� �޾Ƽ� ��� �������� ���� ����ϴ� ���α׷��� �ۼ��϶�. ��
 * �� ������ ������ �Է����� �־�����, �� �ٿ� �ϳ��� ���ĵ��� �־�����. ��� ���ĵ��� �� ���� �����̰ų� Ȥ
 * �� ������̴�.  
 */
public class Exercise13_3 {
	private final static int L_VALUE = 0;
	private static HashMap<String, Integer> hsm;	// �� ���� ���� ���� �ؽ���
	private static Node[] adjList;
	private int[] outdegree;
	private int[] visited;							// �������� �˰���2�� ���� �迭.
	private Node R;									// �������� �˰���2�� ���� ���Ḯ��Ʈ.
	private class Node {
		String data;
		Node next;
		Node(String x) {
			data = String.copyValueOf(x.toCharArray());
			next=null;
		}
	}
	
	private static String[] fileRead(String filename) {
		String[] notsBuffer=null;
		try {
			Scanner sc = new Scanner(new File("input13_3.txt"));
			int N = sc.nextInt(); sc.nextLine();
			hsm = new HashMap<String, Integer>();
			notsBuffer = new String[N];
			int i=0, id=1; String[] nots;
			while(sc.hasNextLine()){
				notsBuffer[i] = sc.nextLine();
				nots = notsBuffer[i].split(" ");
				if(!hsm.containsKey(nots[L_VALUE]))
					hsm.put(nots[L_VALUE], id++);
				for(int j=2;j<nots.length;j+=2) {	// ������ ������ �κ��� �о� ó��.
					if(!hsm.containsKey(nots[j])) {
						hsm.put(nots[j], id++);
					}
				}
				i++;
			} // file read complete
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
		return notsBuffer.clone();
	}
	
	/* ��������Ʈ ����� */
	private void add(Node r, String var) {
		Node cur = new Node(var);		// �߰��� ���.
		Node p = r;						// �߰��� ��ġ.
		while(p.next!=null) {
			p = p.next;
		}
		p.next = cur;
		// ��������Ʈ�� ��� ���鿡 outdegree�� ���� �� ��. �������� ��� 0�̴�.
		outdegree[hsm.get(r.data)]++;
	}
	private void constructAdjList(String[] buf) {
		adjList = new Node[hsm.size()+1];	// [0]�� ������� �ʴ´�.
		outdegree = new int[hsm.size()+1];
		String[] nots;
		for(int i=0;i<buf.length;i++) {
			nots = buf[i].split(" ");	// ��¿ �� ���� �� �� ��ũ����¡ ��.
			String leftValue = nots[L_VALUE];
			int leftIndex = hsm.get(leftValue);
			if(adjList[leftIndex]==null)
				adjList[leftIndex] = new Node(leftValue);
			for(int j=2;j<nots.length;j+=2) {	// ������ ������ �κ��� �о� ó��.
				int rightIndex = hsm.get(nots[j]);
				if(adjList[rightIndex]==null)
					adjList[rightIndex] = new Node(nots[j]);
				add(adjList[rightIndex],leftValue);
			}
		}
	}
	
	/* �������� �˰��� 2 */
	private boolean DFS_TS(Node[] adjList, String v) {
		boolean isDAG = true;
		visited[hsm.get(v)]++;
		Node x = adjList[hsm.get(v)].next;
		while(x!=null && isDAG) {
			if(visited[hsm.get(x.data)]==0)
				isDAG = DFS_TS(adjList, x.data);
			else if(visited[hsm.get(x.data)]==1) {
				return false;	// ���� ���Ľ� �������� �;� �ϴ� ����� outdegree�� 0�� �ƴѰ�� DAG�� �ƴ�.
			}
			x = x.next;
		}
		
		// R�� �տ� �߰�.
		x = new Node(adjList[hsm.get(v)].data);
		x.next = R;
		R = x;
		visited[hsm.get(v)]++;	// DFS�� �Ž��� �ö󰡸鼭 �ι�° üũ.
		return isDAG;
	}
	private boolean topologicalSort2(String[] A) {
		visited = new int[adjList.length];	// [0]�� ������� ����. DFS ���� �� �ִ� �� ������ üũ��.
		R = null;
		for(int i=1;i<adjList.length;i++) {
			if(visited[i]==0) {
				if(!DFS_TS(adjList, adjList[i].data)) return false;
			}
		}

		// R�� A�� �ٲ㼭 ��ȯ��. ���� �����൵ ��� ����.
		for(int i=1;i<A.length;i++) {
			A[i] = R.data;
			R = R.next;
		}
		return true;
	}
	
	/* ���� ���ĵ� �迭�� �̿��Ͽ� ���� ��� */
	private boolean isVar(String v) {	// �Է� ��Ʈ���� �������� ������� �Ǵ��ϴ� �Լ�.
		return (v.compareTo("A")>=0&&v.compareTo("Z")<=0);
	} // �����̸� true, ����̸� false�� ��ȯ.
	private int calcTopolOrder(String[] A) {
		int[] varTable = new int[hsm.size()+1];	// ���� ���̺� ����.
		for(int i=1;i<A.length;i++) {	// ���� ���ĵ� �迭���� ������� �����͸� ����.
			Node ld = adjList[hsm.get(A[i])];	
			Node st = ld.next;			// ���� ����� ������ �ּ� ���.
			while(st!=null) {	// ld�� ������ ��� ��� st�� ld�� ���� ����.
				if(isVar(A[i]))	// �����̸� ���� ���̺��� ���� �ҷ� ��.
					varTable[hsm.get(st.data)] += varTable[hsm.get(ld.data)];
				else			// ����̸� �������� �迭�� ���ڿ��� int�� ������ ����.
					varTable[hsm.get(st.data)] += Integer.parseInt(A[i]);
				st = st.next;
			}
		}
		return varTable[hsm.get(A[A.length-1])];
	}
	
	/* �׽�Ʈ �� ����� ���� �Լ� */
	private void testPrintAdj(Node[] list) {
		System.out.println("====== TEST PRINT : AdjList ======");
		for(int i=1;i<list.length;i++) {
			Node cur = list[i];
			System.out.print(cur.data+"("+outdegree[hsm.get(cur.data)]+"): ");
			cur = cur.next;
			while(cur!=null) {
				System.out.print(cur.data+", ");
				cur = cur.next;
			}
			System.out.println();
		}
		System.out.println("====== TEST PRINT : AdjList ======");
	}
	private void PrintTopol(String[] A) {
		System.out.print("topolOrder: ");
		for(int i=1;i<A.length;i++)
			System.out.print(A[i]+" ");
		System.out.println();
	}
	
	public static void main(String[] args) {
		Exercise13_3 test = new Exercise13_3();

		// ��������Ʈ �����.
		test.constructAdjList(fileRead("input13_2.txt"));
		test.testPrintAdj(adjList);

		// ���� ���� ����.
		String[] calcOrder = new String[hsm.size()+1];
		test.topologicalSort2(calcOrder);
		test.PrintTopol(calcOrder);

		// ���� ������ ������� ���� ����.
		System.out.println("res = " + test.calcTopolOrder(calcOrder));
	}
}
