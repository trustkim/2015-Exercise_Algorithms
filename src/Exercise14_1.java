import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Kruskal�� �˰����� �����϶�. WUPC �˰����� �̿��Ͽ� union-find ������ �����ؾ��Ѵ�. �Է��� ��
 * �� ������ ���� N�� ������ ���� M�� �־�����, �̾��� M ���ο��� ��  �ٿ� �ϳ��� ������ ���� ������ �־�����.
 * �� ������ ���� �����δ� ������ �� �� ���� ��ȣ�� ����ġ�� �־�����. ����� MST�� ���� �������� �� �ٿ� �ϳ���
 * ����Ѵ�.
 */
public class Exercise14_1 {
	private int N;			// ������ ����
	private int M;			// ������ ����
	private Edge [] edges;	// Kruskal�� �����ϱ⿡�� Edge�� �迭�̸� ����ϴ�. 
	
	int [] parent;			// Union Find�� ���� �迭.
	int [] size;			// Union Find�� ���� �迭. �� Union�� ������.
	
	class Edge implements Comparable<Edge>{
		int start,end,weight;
		Edge(int s, int e, int w) {
			start = s; end = e; weight = w;
		}
		@Override
		public
		int compareTo(Edge o) {return weight-o.weight;}
	}
	private void readInput(String filename) {
		try {
			Scanner sc = new Scanner(new File(filename));
			N = sc.nextInt(); M = sc.nextInt();
			edges = new Edge[M]; int i=0;
			while(sc.hasNext()){
				edges[i++] = new Edge(sc.nextInt(),sc.nextInt(),sc.nextInt());
			}
			//edgePrint();
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
	
	private void initialize() {
		parent = new int[N+1];
		size = new int[N+1];
		for(int i=1;i<=N;i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}
	private int findSet(int v) {
		if(v!=parent[v]) {
			parent[v] = findSet(parent[v]);
		}
		return parent[v];
	}
	private void union(int u, int v) {
		int x=findSet(u);
		int y=findSet(v);
		if(size[x]>size[y]) {
			parent[x] = y;
			size[x] = size[x] + size[y];
		}else {
			parent[y] = x;
			size[y] = size[y] + size[x];
		}
	}
	private void kruskal(){
		System.out.println("MST-Kruskal Algoritm");
		initialize();
		Arrays.sort(edges);	// edges�� weight�� �������� �������� �����Ѵ�.
		//edgePrint();
		// �������� �� ������� �ϳ��� �����ذ���. ��, �̹� ���õ� ������� ����Ŭ(cycle)�� �����ϸ� �������� �ʴ´�.
		for(int i=0;i<edges.length;i++) {
			int u = edges[i].start;
			int v = edges[i].end;
			if(findSet(u) != findSet(v)){	// ��� v�� ���� ������ ã�ƶ�.
				System.out.println("("+u+", "+v+")");
				union(u,v);
			}
		}
	}
	public static void main(String [] args) {
		Exercise14_1 test = new Exercise14_1();
		test.readInput("input14_1.txt");
		test.kruskal();
	}
	
	private void edgePrint() {
		System.out.println("s\te\tw");
		for(int i=0;i<edges.length;i++) {
			System.out.println(edges[i].start+"\t"+edges[i].end+"\t"+edges[i].weight);
		}
		System.out.println();
	}
}
