import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * 평면상의 n개의 점의 좌표가 주어진다. 각 점을 그래프의 정점으로 생각하고, 모든 점들 간에는 에지가 있는
 * 것으로 간주한다. 각 에지의 가중치는 그 에지가 연결하는 두 점 간의 거리이다. 이 그래프의 최소신장트리를
 * Prim의 알고리즘으로 구하는 프로그램을 작성하라. 우선순위큐를 사용하여 구현할 필요는 없다. 시간복잡도
 * 가 O(n*n)이 되도록 구현하라. 입력 형식은 먼저 점의 개수 n이 주어지고 이어진 n줄에는 각 줄마다 하나의
 * 점의 x좌표와 y좌표가 주어진다. 점점의 번호는 점들이 입력된 순서대로 0번에서 n-1번까지이다. 최소신장
 * 트리에 포함된 에지들을 출력하라.
 */
public class Exercise15_1 {
	class Point
	{
		int x,y;
		Point(int x, int y) {this.x=x;this.y=y;}
	}
	private Point[] g;			// 그래프는 간단히 각 점들의 배열로 표현
	private int N;				// 전체 그래프를 이루는 정점의 개수
	private int[] key;			// 이미 Va에 속한 노드와 자신을 연결하는 에지들 중 가중치가 최소인 에지 (u,v)의 가중치
	private int[] pi;			// 해당 에지 (u,v)의 끝 점 u
	private boolean[] include;	// Va에 속할 때 true를 표시하는 배열
	private int findDist(int a, int b)
	{
		int ax=g[a].x, ay=g[a].y, bx=g[b].x, by=g[b].y;
		return (int)(Math.pow(ax-bx, 2)+Math.pow(ay-by,2));	// 두 점 간의 거리를 구하여 반환
	}
	private void init()
	{
		key = new int[N];		// 전체 정점들의 key값을 매번 갱신시켜 나감. 따라서 N개
		pi = new int[N];		// 얘도 N개
		include = new boolean[N];
		for(int i=0;i<N;i++)
		{
			key[i] = -1;	// key를 무한으로
		}
	}
	private int findMinKey()
	{
		// 트리에 넣지 않은 인접 노드 중 거리가 가장 가까운 노드의 인덱스를 반환
		int minKey=-1;		// 현재 가장 가까운 정점과의 거리가 저장
		int minIndex=-1;	// 현재 가중치가 가장 낮은 정점 인덱스를 반환
		for(int i=0;i<N;i++)
		{
			if(!include[i]){
				int temp = key[i];
				if(minKey == -1 || minKey > temp)
				{
					minKey = temp;
					minIndex=i;
				}
			}
		}
		return minIndex;
	}
	private void mstPrim()
	{
		init();
		key[0] = 0;			// 시작 노드를 정함. 항상 g[0] 노드의 인접한 에지부터 MST에 추가함.
		int mstSize = 0;
		while(mstSize<N)
		{
			int u = findMinKey();			// Va에 속하지 않고 key가 가장 낮은 정점을 찾음
			include[u] = true; mstSize++;	// 그 u를 Va에 포함 시킴
			for(int v=0;v<N;v++)			// u의 모든 인접한 정점 중 Va에 포함 되지 않은 v의 key[v], pi[v]를 갱신
			{
				if(include[v])
					continue;				// Va에 포함된 key[v]는 갱신하지 않음
				if(key[v] == -1 || key[v] > findDist(u,v))
				{
					key[v] = findDist(u,v);
					pi[v] = u;
				}
			}
		}
	}
	private void readFile()
	{
		try
		{
			Scanner sc = new Scanner(new File("input15_1.txt"));
			N = sc.nextInt();
			g = new Point[N];
			for(int i=0;i<N;i++)
			{
				g[i] = new Point(sc.nextInt(),sc.nextInt());
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
	public static void main(String[] args)
	{
		Exercise15_1 test = new Exercise15_1();
		test.readFile();
		test.mstPrim();
		test.showMST();
	}
//	private void testPrint()
//	{
//		for(int i=0;i<g.length;i++)
//		{
//			System.out.println(g[i].x+", "+g[i].y);
//		}
//	}
	private void showMST()
	{
		for(int i=0;i<N;i++)
		{
			System.out.println("("+i+"->"+pi[i]+")");
		}
	}
}
