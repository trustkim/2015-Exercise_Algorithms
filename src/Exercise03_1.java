/*
 * Counting cells in blob �������� ��� blob��� �׵��� ũ�⸦ ã�� ����ϵ��� ���α׷��� �����϶�.
 * �Ʒ� �׸��� �������� ������ ���� ��µǾ�� �Ѵ�.
 */
public class Exercise03_1 {
	private static int N=8;
	private static int [][] grid={
		{1, 0, 0, 0, 0, 0, 0, 1},
		{0, 1, 1, 0, 0, 1, 0, 0},
		{1, 1, 0, 0, 1, 0, 1, 0},
		{0, 0, 0, 0, 0, 1, 0, 0},
		{0, 1, 0, 1, 0, 1, 0, 0},
		{0, 1, 0, 1, 0, 1, 0, 0},
		{1, 0, 0, 0, 1, 0, 0, 1},
		{0, 1, 1, 0, 0, 1, 1, 1}
	};
	
	private static final int IMAGE = 1;
	private static final int CHECKED = 2;
	public static void main(String [] args) {
		int[] blobs = new int[N*N];
		int k = 0; int cnt=0;
		for(int i=0;i<N;i++)
			for(int j=0;j<N;j++)
				if(grid[i][j]==IMAGE) {
					blobs[k++]=countCells(i,j);
					cnt++;
				} // 2�� for�� �ȿ��� ȣ��ǵ� �˻��� cell�� CHECKED ó���ϹǷ� �ִ� N*N cell�� �˻��� �� �ִ�.
		System.out.println("The number of blobs is "+cnt+".");
		k = 0;
		while(blobs[k]!=0)
			System.out.println(blobs[k++]);
	}
	public static int countCells(int x, int y) {
		if(x<0||y<0||x>=N||y>=N)
			return 0;
		else if(grid[x][y]!=IMAGE)
			return 0;
		else {
			grid[x][y] = CHECKED;
			return 1 + countCells(x-1,y) + countCells(x-1,y+1)
					+ countCells(x,y+1) + countCells(x+1,y+1)
					+ countCells(x+1,y) + countCells(x+1,y-1)
					+ countCells(x,y-1) + countCells(x-1,y-1);
		}
	}
}
