import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * 구글 맵 등 대부분 지도 서비스는 출발점과 목적지를 지정하면 두 지점간의 최단 이동
 * 경로 혹은 최소 이동 시간을 알려주는 서비스를 제공한다.
 * 최단 경로 알고리즘을 이용하여 이런 서비스를 제공하는 프로그램을 작성해보자.
 */
public class Exercise16 {
	private class Edge	
	{// 인접리스트의 한 노드는 실제로 각 에지에 대한 표현이므로 혼동 방지로 이 이름으로 함.
		int id;			// 해당 노드의 배열 인덱스
		double dist;	// 이동 거리
		double speed;	// 이동 시간?
		Edge next;
		Edge(int id, double dist, double speed){this.id=id;this.dist=dist;this.speed=speed;} // 인접리스트에 추가할 노드 생성
		Edge(int id){this.id=id;next=null;}	// 결과 리스트 R에 추가할 노드 생성
	}
	private int N;				// 전체 그래프의 정점의 개수
	private int M;				// 전체 그래프의 에지 개수
	private int T;				// 물어볼 경로의 개수
	private String[] locations;	// 전체 그래프의 정점이 장소인데 그 이름을 저장한 배열
	private Edge[] vertices;	// 그래프는 인접리스트로 표현
	private int[][] trips;		// 경로는 출발지,도착지,출력타입-거리,소요시간-으로 표시
	private double[] key;		// d[i]
	private int[] pi;			// pi[i]
	private boolean[] include;	// 집합 S. 해당 정점으로부터 출발하는 모든 에지의 최단 경로를 결정함

	private void init()
	{
		key = new double[N];	// 전체 정점들의 key값을 매번 갱신시켜 나감. 따라서 N개
		pi = new int[N];		// 얘도 N개
		include = new boolean[N];
		for(int i=0;i<N;i++)
		{
			key[i] = -1;		// key를 무한으로
		}
	}
	private int findMinKey()
	{
		// S에 넣지 않은 인접 노드 중 거리가 가장 가까운 노드의 인덱스를 반환
		double minKey=-1;		// 현재 가장 가까운 정점과의 거리가 저장
		int minIndex=-1;		// 현재 가중치가 가장 낮은 정점 인덱스를 반환
		for(int i=0;i<N;i++)
		{
			if(!include[i]){
				double temp = key[i];
				if(minKey == -1 || (temp!=-1&&minKey > temp))
				{
					minKey = temp;
					minIndex=i;
				}
			}
		}
		return minIndex;
	}
	private double findWeight(Edge p, char type)
	{
		return (type=='D'?p.dist:p.speed);
	}
	private void dijkstra(int start, int dest, char type)
	{	// Prim의 알고리즘과 거의 동일하다!
		init();
		key[start] = 0;		// 시작 정점을 정함.
		int cnt = 0;
		while(cnt<N)		// n-1번 반복. 따로 dest까지 결정되면 멈추도록 하지 않음.
		{
			int u = findMinKey();			// S에 속하지 않고 key가 가장 낮은 정점을 찾음
			include[u] = true; cnt++;		// 그 u를 S에 포함 시킴
			Edge p = vertices[u];
			while(p!=null)					// u의 모든 인접한 정점 중 S에 포함 되지 않은 v의 key[v], pi[v]를 갱신
			{
				int v = p.id;
				if(include[v])
					;// S에 포함된 key[v]는 갱신하지 않음
				double weight = findWeight(p,type);
				if(key[v]==-1 || key[v] > key[u]+weight)
				{
					key[v] = key[u]+weight;
					pi[v] = u;
				}
				p = p.next;
			}
		}
		//System.out.println("dijkstra compelete");
	}
	private boolean isPassLine(String line)
	{	// 파일을 읽을 때 공백이나 '#'으로 시작하는 문자열은 무시하기 위한 함수. 무시해야하는 문자열이면 true를 반환 
		return (line.equals("")||line.charAt(0)=='#');
	}
	private void add(int v, int u, double dist, double speed)
	{	// 인접리스트에 노드를 추가하는 함수
		if(vertices[v]==null)
		{
			vertices[v] = new Edge(u,dist,speed);
		}
		else
		{
			Edge p = new Edge(u,dist,speed);
			p.next = vertices[v];
			vertices[v] = p;
		}
	}
	private void readFile()
	{
		try
		{
			Scanner sc = new Scanner(new File("input16.txt"));
			String a_line;
			int status=0;	// 0: N 읽기, 1: N개의 장소 이름 읽기, 2: M 읽기, 3: M개의 노드 읽기, 4: T 읽기, 5: T개의 경로 읽기
			int cnt=0;
			while(sc.hasNextLine())
			{
				a_line = sc.nextLine();	// 파일에서 한 줄을 읽음
				//System.out.println(a_line);
				if(!isPassLine(a_line))	// '#'과 ''으로 시작하는 문자열을 넘어감
				{
					switch(status)
					{
						case 0:
						{
							N=Integer.parseInt(a_line);	// N값이 정의 안되있으면 N값을 정함. 파일에서 항상 N을 먼저 정하기로 했으므로
							locations = new String[N];
							vertices = new Edge[N];
							//System.out.println("N read complete");
							status++; break;
						}
						case 1:
						{
							locations[cnt++] = a_line.trim();
							
							if(cnt==N) {
								cnt=0;
								status++;
								//System.out.println("locs read complete");
							}
							break;
						}
						case 2:
						{
							M=Integer.parseInt(a_line);
							//System.out.println("M read complete");
							status++; break;
						}
						case 3:
						{
							String[] temp = a_line.split(" ");
							//System.out.println(temp[1]);
							add(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Double.parseDouble(temp[2]),Double.parseDouble(temp[3]));
							cnt++;

							if(cnt==M){
								cnt=0;
								status++;
								//System.out.println("vertices read complete");
							}
							break;
						}
						case 4:
						{
							T = Integer.parseInt(a_line);
							trips = new int[T][3];
							//System.out.println("T read complete");
							status++; break;
							
						}
						case 5:
						{
							String[] temp = a_line.split(" ");
							trips[cnt][0] = Integer.parseInt(temp[0]);
							trips[cnt][1] = Integer.parseInt(temp[1]);
							trips[cnt++][2] = temp[2].trim().toCharArray()[0];
							//System.out.println((char)trips[cnt-1][2]);
							if(cnt==T)
							{
								//System.out.println("trips read complete");
								break;
							}
							
						}
					}
				}
			}
			//System.out.println("file read complete");
			sc.close();
		}catch(FileNotFoundException e)
		{
			System.out.println("file not found...");
		}
	}
	public static void main(String [] args)
	{
		Exercise16 theApp = new Exercise16();
		theApp.readFile();
		theApp.solve();
		
	}
	private void solve()
	{
		for(int i=0;i<T;i++)
		{
			int[] l = trips[i];	// 현재 물어볼 경로
			int start=l[0], dest=l[1]; char type = (char)l[2];
			dijkstra(start,dest,type);	// 해당 경로로 dijkstra algoritm 수행	
			Print(start,dest,type);		// 해당 경로의 최단 경로를 출력
		}
	}
	private void Print(int start, int dest, char type)
	
	{
		String unit = type=='D'?" miles":" hours";
//		System.out.println("pi test");
//		for(int i=0;i<N;i++)
//			System.out.println(i+" <- "+pi[i]);
		
		// 완성된 pi배열을 dest부터 start까지 거꾸로 한 번 읽어 출력한다.
		int d = dest, s = pi[d];
		Edge R = new Edge(d);
		Edge p=null;
		while(s!=start)
		{
			p = new Edge(s,key[s],type);
			p.next = R;
			R = p;
			d = s; s = pi[d];
		}
		p = new Edge(s,key[s],type);
		p.next=R;
		R = p;
		
		double sum=0;
		System.out.print("Result: ");
		while(R!=null)
		{
			System.out.print("\n"+"["+R.id+"] "+locations[R.id]+"("+key[R.id]+unit+")"+" -> ");
			sum += key[R.id];
			R = R.next;
		}
		System.out.println("finish!! ("+sum+unit+")");
	}
}
