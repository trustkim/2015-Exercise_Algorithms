import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Kruskal의 알고리즘을 구현하라. WUPC 알고리즘을 이용하여 union-find 연산을 구현해야한다. 입력은 먼
 * 저 정정의 개수 N과 에지의 개수 M이 주어지고, 이어진 M 라인에는 한  줄에 하나씩 에지에 관한 정보가 주어진다.
 * 각 에지에 관한 정보로는 에지의 양 끝 정점 번호와 가중치가 주어진다. 출력은 MST에 속한 에지들을 한 줄에 하나씩
 * 출력한다.
 */
public class Exercise14_1 {
	private int N;			// 정점의 개수
	private int M;			// 에지의 개수
	private Edge [] edges;	// Kruskal을 구현하기에는 Edge의 배열이면 충분하다. 
	
	int [] parent;			// Union Find를 위한 배열.
	int [] size;			// Union Find를 위한 배열. 각 Union의 사이즈.
	
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
		Arrays.sort(edges);	// edges를 weight를 기준으로 오름차순 정렬한다.
		//edgePrint();
		// 에지들을 그 순서대로 하나씩 선택해간다. 단, 이미 선택된 에지들과 사이클(cycle)을 형성하면 선택하지 않는다.
		for(int i=0;i<edges.length;i++) {
			int u = edges[i].start;
			int v = edges[i].end;
			if(findSet(u) != findSet(v)){	// 노드 v가 속한 집합을 찾아라.
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
