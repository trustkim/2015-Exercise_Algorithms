import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Exercise15_1에서 구한 최소신장트리를 화면에 그리는 프로그램을 작성하라.
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
    private int N;				// 전체 그래프를 이루는 정점의 개수
    private int minX=Integer.MAX_VALUE, minY=Integer.MAX_VALUE, maxX=-Integer.MAX_VALUE, maxY=-Integer.MAX_VALUE;
	private int[] key;			// 이미 Va에 속한 노드와 자신을 연결하는 에지들 중 가중치가 최소인 에지 (u,v)의 가중치
	private int[] pi;			// 해당 에지 (u,v)의 끝 점 u
	private boolean[] include;	// Va에 속할 때 true를 표시하는 배열
	private int findDist(int a, int b)
	{
		int ax=vertices[a].x, ay=vertices[a].y, bx=vertices[b].x, by=vertices[b].y;
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
			if(u==99) ;
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
    private int convertToPixelX(int d) {
        return d*300/((maxX+minX)/2)+100;
    }

    private int convertToPixelY(int d) {
        return d*300/((maxY+minY)/2)+100;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for (int i=0; i<vertices.length; i++) {	// 정점을 먼저 그림
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
