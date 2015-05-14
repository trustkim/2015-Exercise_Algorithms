import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Exercise02_5 {
	private static int N;
	private static int [][] maze;
	
	private static final int PATHWAY = 0;
	private static final int WALL = 1;
	private static final int BLOCKED = 2;
	private static final int PATH = 3;
	
	public static void main(String [] args) {
	
		long start = System.currentTimeMillis();
		try {
			Scanner input = new Scanner(new File("input02_5.txt"));
			
			for(int T=input.nextInt();T>0;T--) {
				N = input.nextInt();
				int x0 = input.nextInt();
				int y0 = input.nextInt();
				int x1 = input.nextInt();
				int y1 = input.nextInt();
				maze = new int[N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++)
						maze[i][j] = input.nextInt();
				System.out.println(N+" "+x0+" "+y0+" "+x1+" "+y1);
				Print(maze);
				
				System.out.println(findHorsePath(x0, y0, x1, y1));
			}
			
			input.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static boolean findHorsePath(int x0, int y0, int x1, int y1) {
		if(x0<0 || y0<0 || x0>N-1 || y0>N-1){
			return false;	// ���� �Ѿ�� �ȵ�
		}else if(maze[x0][y0] != PATHWAY)
			return false;	// ���� ��� �ȵ�. �ٸ� ���� �ְų� �� �� ���� ��� �Ǹ� ���̰ų�
		else if (x0==x1 && y0==y1) {
			maze[x0][y0] = PATH;	// �̰� �����൵ ���� �ʳ�? ���� ��θ� ǥ���ϱ� ���� �ʿ���
			return true;
		}
		else {
			maze[x0][y0] = PATH;
			if(x0-1>=0&&y0>=0&&x0-1<N&&y0<N&&maze[x0-1][y0]!=WALL)	// maze[x0-1][y0]==PATHWAY�� �ƴ� ������? PATHWAY ���� BLOCKED�� PATH���� ��� ����.
				if(findHorsePath(x0-2,y0-1,x1,y1) || findHorsePath(x0-2, y0+1,x1,y1)) return true;
			if (x0>=0&&y0+1>=0&&x0<N&&y0+1<N&&maze[x0][y0+1]!=WALL)
				if(findHorsePath(x0-1,y0+2,x1,y1) || findHorsePath(x0+1,y0+2,x1,y1)) return true;
			if(x0+1>=0&&y0>=0&&x0+1<N&&y0<N&&maze[x0+1][y0]!=WALL)
				if(findHorsePath(x0+2,y0+1,x1,y1) || findHorsePath(x0+2,y0-1,x1,y1)) return true;
			if(x0>=0&&y0-1>=0&&x0<N&&y0-1<N&&maze[x0][y0-1]!=WALL)
				if(findHorsePath(x0+1,y0-2,x1,y1) || findHorsePath(x0-1,y0-2,x1,y1)) return true;

			maze[x0][y0] = BLOCKED;	// findHorsePath ���� �� maze�� ������ ��θ� ǥ���ϱ� ���� �ʿ���
			return false;
		}
	}
	
	public static void Print(int [][] grid) {
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++)
				System.out.print(grid[i][j]+" ");
			System.out.println();
		}
	}
}