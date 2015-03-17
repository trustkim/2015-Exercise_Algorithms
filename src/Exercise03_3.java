/*
 * N super-queen problem은 N*N 체스 보드에 N개의 super-queen을 놓는 문제이다.
 * N queen문제와 마찬가지로 어떤 두 말도 같은 행. 같은 열, 혹은 같은 대각선상에 놓일 수 없다.
 * 거기에 더해서 어떤 두 말도 장기의 "마"가 한 번에 움직일 수 있는 위치에 놓여서는 안된다.
 * 즉 (가로 2칸, 세로 1칸) 혹은 (세로 2칸, 가로 1칸) 떨어진 위치에 두 말이 놓여서는 안된다.
 * 이런 조건을 만족하도록 N개의 말을 놓을 수 있는 N>1의 최소 값을 찾아라.
 */
public class Exercise03_3 {
	private static int N;
	private static int[] cols;	// 각 행별로 몇 번째 열에 퀸을 놓는지 저장할 배열. cols[1] = 2는 1번 행, 2번열에 퀸을 놓았다는 뜻. 이로써 이차원 배열을 쓸 필요가 없어진다.

	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		N=2;	// 문제에서 2 이상의 N에 대하여 구하라고 요구하였음.
		cols = new int[N+1];
		while(!superQueens(0)) {
			// 왜 0부터 시작할까? 0부터 시작해야 1행 1열부터 cols[level+1] = i로 검사할 수 있음.
			
			// 다음 N에 대한 superQueens를 검사할 준비 단계
			N++;
			cols = new int[N+1];
		}
		System.out.println(N);
		for(int i=1;i<=N;i++)
			System.out.println("("+i+", "+cols[i]+")");
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static boolean superQueens(int level) {
		if(!promising(level))
			return false;
		else if(level==N) {
			return true;
		}
		for(int i=1;i<=N;i++) {
			cols[level+1] = i;	// 현재 레벨에서 다음에 검사할 cols 인덱스를 지정함
			if(superQueens(level+1))
				return true;
		}
		return false;
	}
	public static boolean promising(int level) {
		for(int i=1;i<level;i++) {
			if(cols[i]==cols[level])	// 같은 열에 있나? 
				return false;
			if(level-i==Math.abs(cols[level]-cols[i]))	// 대각선 상에 있나?
				return false;
			if(level-i==1&&Math.abs(cols[level-1]-cols[level])==2)	// KNGIHT는 8가지 셀로 이동 가능 하지만 cols를 사용하는 본 접근에선 위쪽으로만 검사하면 된다.
					return false;
			if(level-i==2&&Math.abs(cols[level-2]-cols[level])==1)
					return false;
		}
		return true;
	}
}
