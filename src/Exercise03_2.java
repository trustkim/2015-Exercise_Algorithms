/*
 * N=1,2,...,15에 대해서 N-queens 문제의 해의 개수를 카운트하는 프로그램을 작성하라.
 */
public class Exercise03_2 {
	private static int N;
	private static int[] cols;	// 각 행별로 몇 번째 열에 퀸을 놓는지 저장할 배열. cols[1] = 2는 1번 행, 2번열에 퀸을 놓았다는 뜻. 이로써 이차원 배열을 쓸 필요가 없어진다.
	private static int cnt;
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		for(N=1;N<=15;N++) {
			cols = new int[N+1];
			cnt = 0;
			nqueens(0);
			System.out.println(cnt);	// 왜 0부터 시작할까? 0부터 시작해야 1행 1열부터 cols[level+1] = i로 검사할 수 있음.
		}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static void nqueens(int level) {
		if(!promising(level))
			return ;
		else if(level==N) {
			cnt++;
			return ;
		}
		//if(level==0) index = N/2;	// if(level==0) cols[level+1]=1~N/2 만 할 방법이 없을까? N이 짝수이면 1~N/2만 검사하고 해의 개수의 두 배를 해준게 답.
		for(int i=1;i<=N;i++) {
			cols[level+1] = i; // 현재 레벨에서 다음에 검사할 cols 인덱스를 지정함
			nqueens(level+1);
		}
	}
	public static boolean promising(int level) {
		for(int i=1;i<level;i++) {
			if(cols[i]==cols[level])	// 같은 열에 있나? 
				return false;
			if(level-i==Math.abs(cols[level]-cols[i]))	// 대각선 상에 있나?
				return false;
		}
		return true;
	}
}
