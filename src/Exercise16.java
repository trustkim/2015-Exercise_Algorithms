import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * ���� �� �� ��κ� ���� ���񽺴� ������� �������� �����ϸ� �� �������� �ִ� �̵�
 * ��� Ȥ�� �ּ� �̵� �ð��� �˷��ִ� ���񽺸� �����Ѵ�.
 * �ִ� ��� �˰����� �̿��Ͽ� �̷� ���񽺸� �����ϴ� ���α׷��� �ۼ��غ���.
 */
public class Exercise16 {
	private class Edge	
	{// ��������Ʈ�� �� ���� ������ �� ������ ���� ǥ���̹Ƿ� ȥ�� ������ �� �̸����� ��.
		int id;			// �ش� ����� �迭 �ε���
		double dist;	// �̵� �Ÿ�
		double speed;	// �̵� �ð�?
		Edge next;
		Edge(int id, double dist, double speed){this.id=id;this.dist=dist;this.speed=speed;} // ��������Ʈ�� �߰��� ��� ����
		Edge(int id){this.id=id;next=null;}	// ��� ����Ʈ R�� �߰��� ��� ����
	}
	private int N;				// ��ü �׷����� ������ ����
	private int M;				// ��ü �׷����� ���� ����
	private int T;				// ��� ����� ����
	private String[] locations;	// ��ü �׷����� ������ ����ε� �� �̸��� ������ �迭
	private Edge[] vertices;	// �׷����� ��������Ʈ�� ǥ��
	private int[][] trips;		// ��δ� �����,������,���Ÿ��-�Ÿ�,�ҿ�ð�-���� ǥ��
	private double[] key;		// d[i]
	private int[] pi;			// pi[i]
	private boolean[] include;	// ���� S. �ش� �������κ��� ����ϴ� ��� ������ �ִ� ��θ� ������

	private void init()
	{
		key = new double[N];	// ��ü �������� key���� �Ź� ���Ž��� ����. ���� N��
		pi = new int[N];		// �굵 N��
		include = new boolean[N];
		for(int i=0;i<N;i++)
		{
			key[i] = -1;		// key�� ��������
		}
	}
	private int findMinKey()
	{
		// S�� ���� ���� ���� ��� �� �Ÿ��� ���� ����� ����� �ε����� ��ȯ
		double minKey=-1;		// ���� ���� ����� �������� �Ÿ��� ����
		int minIndex=-1;		// ���� ����ġ�� ���� ���� ���� �ε����� ��ȯ
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
	{	// Prim�� �˰���� ���� �����ϴ�!
		init();
		key[start] = 0;		// ���� ������ ����.
		int cnt = 0;
		while(cnt<N)		// n-1�� �ݺ�. ���� dest���� �����Ǹ� ���ߵ��� ���� ����.
		{
			int u = findMinKey();			// S�� ������ �ʰ� key�� ���� ���� ������ ã��
			include[u] = true; cnt++;		// �� u�� S�� ���� ��Ŵ
			Edge p = vertices[u];
			while(p!=null)					// u�� ��� ������ ���� �� S�� ���� ���� ���� v�� key[v], pi[v]�� ����
			{
				int v = p.id;
				if(include[v])
					;// S�� ���Ե� key[v]�� �������� ����
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
	{	// ������ ���� �� �����̳� '#'���� �����ϴ� ���ڿ��� �����ϱ� ���� �Լ�. �����ؾ��ϴ� ���ڿ��̸� true�� ��ȯ 
		return (line.equals("")||line.charAt(0)=='#');
	}
	private void add(int v, int u, double dist, double speed)
	{	// ��������Ʈ�� ��带 �߰��ϴ� �Լ�
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
			int status=0;	// 0: N �б�, 1: N���� ��� �̸� �б�, 2: M �б�, 3: M���� ��� �б�, 4: T �б�, 5: T���� ��� �б�
			int cnt=0;
			while(sc.hasNextLine())
			{
				a_line = sc.nextLine();	// ���Ͽ��� �� ���� ����
				//System.out.println(a_line);
				if(!isPassLine(a_line))	// '#'�� ''���� �����ϴ� ���ڿ��� �Ѿ
				{
					switch(status)
					{
						case 0:
						{
							N=Integer.parseInt(a_line);	// N���� ���� �ȵ������� N���� ����. ���Ͽ��� �׻� N�� ���� ���ϱ�� �����Ƿ�
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
			int[] l = trips[i];	// ���� ��� ���
			int start=l[0], dest=l[1]; char type = (char)l[2];
			dijkstra(start,dest,type);	// �ش� ��η� dijkstra algoritm ����	
			Print(start,dest,type);		// �ش� ����� �ִ� ��θ� ���
		}
	}
	private void Print(int start, int dest, char type)
	
	{
		String unit = type=='D'?" miles":" hours";
//		System.out.println("pi test");
//		for(int i=0;i<N;i++)
//			System.out.println(i+" <- "+pi[i]);
		
		// �ϼ��� pi�迭�� dest���� start���� �Ųٷ� �� �� �о� ����Ѵ�.
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
