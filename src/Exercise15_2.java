import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Exercise15_1���� ���� �ּҽ���Ʈ���� ȭ�鿡 �׸��� ���α׷��� �ۼ��϶�.
 */
@SuppressWarnings("serial")
public class Exercise15_2 extends JPanel
{
    private class Vertex {       // represents a vertex
        int x;
        int y;
        public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private Vertex[] vertices;
    private int N;				// ��ü �׷����� �̷�� ������ ����
    private int minX=Integer.MAX_VALUE, minY=Integer.MAX_VALUE, maxX=-Integer.MAX_VALUE, maxY=-Integer.MAX_VALUE;
	private int[] key;			// �̹� Va�� ���� ���� �ڽ��� �����ϴ� ������ �� ����ġ�� �ּ��� ���� (u,v)�� ����ġ
	private int[] pi;			// �ش� ���� (u,v)�� �� �� u
	private boolean[] include;	// Va�� ���� �� true�� ǥ���ϴ� �迭
	private int findDist(int a, int b)
	{
		int ax=vertices[a].x, ay=vertices[a].y, bx=vertices[b].x, by=vertices[b].y;
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
			if(u==99) ;
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
    private int convertToPixelX(int d) {
        return d*300/((maxX+minX)/2)+100;
    }

    private int convertToPixelY(int d) {
        return d*300/((maxY+minY)/2)+100;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for (int i=0; i<vertices.length; i++) {	// ������ ���� �׸�
        	Vertex v = vertices[i];
        	Vertex u = vertices[pi[i]];
        	g.setColor(Color.blue);
        	g.drawLine(convertToPixelX(v.x)+1, convertToPixelY(v.y)+1, convertToPixelX(u.x)+1, convertToPixelX(u.y)+1);
        	System.out.println("("+i+"->"+pi[i]+")");
            if(i==0) g.setColor(Color.RED);
            else
            	g.setColor(Color.white);
			g.fillOval(convertToPixelX(v.x)-4, convertToPixelY(v.y)-4, 8, 8);
			g.setColor(Color.black);
			g.drawOval(convertToPixelX(v.x)-4, convertToPixelY(v.y)-4, 8, 8);
			g.drawString(Integer.toString(i), convertToPixelX(v.x)-4, convertToPixelY(v.y)-4);
            //g.fillOval(convertToPixelX(v.x)-4, convertToPixelY(v.y)-4, 8, 8);
        }
    }
    private void getGraph() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("input15_1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        N = sc.nextInt();
        vertices = new Vertex[N];
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            if (x<minX) minX = x;
            if (y<minY) minY = y;
            if (x>maxX) maxX = x;
            if (y>maxY) maxY = y;

            vertices[i] = new Vertex(x, y);
        }
        sc.close();
    }
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
        frame.setTitle("MST Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Exercise15_2 theApp = new Exercise15_2();
        theApp.getGraph();
        theApp.mstPrim();
        frame.add(theApp);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
	}
	
	public Exercise15_2() {
        setPreferredSize(new Dimension(800, 800));
    }
}
