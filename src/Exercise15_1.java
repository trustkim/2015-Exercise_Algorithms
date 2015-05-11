import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * ������ n���� ���� ��ǥ�� �־�����. �� ���� �׷����� �������� �����ϰ�, ��� ���� ������ ������ �ִ�
 * ������ �����Ѵ�. �� ������ ����ġ�� �� ������ �����ϴ� �� �� ���� �Ÿ��̴�. �� �׷����� �ּҽ���Ʈ����
 * Prim�� �˰������� ���ϴ� ���α׷��� �ۼ��϶�. �켱����ť�� ����Ͽ� ������ �ʿ�� ����. �ð����⵵
 * �� O(n*n)�� �ǵ��� �����϶�. �Է� ������ ���� ���� ���� n�� �־����� �̾��� n�ٿ��� �� �ٸ��� �ϳ���
 * ���� x��ǥ�� y��ǥ�� �־�����. ������ ��ȣ�� ������ �Էµ� ������� 0������ n-1�������̴�. �ּҽ���
 * Ʈ���� ���Ե� �������� ����϶�.
 */
public class Exercise15_1 {
	class Point
	{
		int x,y;
		Point(int x, int y) {this.x=x;this.y=y;}
	}
	private Point[] g;			// �׷����� ������ �� ������ �迭�� ǥ��
	private int N;				// ��ü �׷����� �̷�� ������ ����
	private int[] key;			// �̹� Va�� ���� ���� �ڽ��� �����ϴ� ������ �� ����ġ�� �ּ��� ���� (u,v)�� ����ġ
	private int[] pi;			// �ش� ���� (u,v)�� �� �� u
	private boolean[] include;	// Va�� ���� �� true�� ǥ���ϴ� �迭
	private int findDist(int a, int b)
	{
		int ax=g[a].x, ay=g[a].y, bx=g[b].x, by=g[b].y;
		return (int)(Math.pow(ax-bx, 2)+Math.pow(ay-by,2));	// �� �� ���� �Ÿ��� ���Ͽ� ��ȯ
	}
	private void init()
	{
		key = new int[N];		// ��ü �������� key���� �Ź� ���Ž��� ����. ���� N��
		pi = new int[N];		// �굵 N��
		include = new boolean[N];
		for(int i=0;i<N;i++)
		{
			key[i] = -1;	// key�� ��������
		}
	}
	private int findMinKey()
	{
		// Ʈ���� ���� ���� ���� ��� �� �Ÿ��� ���� ����� ����� �ε����� ��ȯ
		int minKey=-1;		// ���� ���� ����� �������� �Ÿ��� ����
		int minIndex=-1;	// ���� ����ġ�� ���� ���� ���� �ε����� ��ȯ
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
		key[0] = 0;			// ���� ��带 ����. �׻� g[0] ����� ������ �������� MST�� �߰���.
		int mstSize = 0;
		while(mstSize<N)
		{
			int u = findMinKey();			// Va�� ������ �ʰ� key�� ���� ���� ������ ã��
			include[u] = true; mstSize++;	// �� u�� Va�� ���� ��Ŵ
			for(int v=0;v<N;v++)			// u�� ��� ������ ���� �� Va�� ���� ���� ���� v�� key[v], pi[v]�� ����
			{
				if(include[v])
					continue;				// Va�� ���Ե� key[v]�� �������� ����
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
