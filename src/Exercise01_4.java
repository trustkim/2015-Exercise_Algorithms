import java.util.Scanner;


public class Exercise01_4 {
	public static double[][] combinations;
	public static void main(String [] args){
		try {
			Scanner kb = new Scanner(System.in);
			int N=kb.nextInt(); 
			int M=kb.nextInt();
			combinations = new double[N+M+1][];
			for(int i=0;i<=N+M;i++) combinations[i] = new double[N+1];
			
			long start = System.currentTimeMillis();
			System.out.println(memorize_combination(N+M-2,N-1));
			System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
			
			start = System.currentTimeMillis();
			System.out.println(solve(0,0,N,M));
			System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
			kb.close();
			}catch(Exception e){e.printStackTrace();}
	}
	public static double memorize_combination(int n, int k){
		if(k==0) {combinations[n][k]=1;return 1;}
		else if(k==n) {combinations[n][k]=1;return 1;}
		else{
			combinations[n][k] = (combinations[n-1][k]==0?memorize_combination(n-1,k):combinations[n-1][k])+(combinations[n-1][k-1]==0?memorize_combination(n-1,k-1):combinations[n-1][k-1]);
			return combinations[n][k];
		}
	}
	public static double solve(int i, int j, int N, int M) {
		if(i==N-1) return 1;
		if(j==M-1) return 1;
		return solve(i+1, j, N, M) + solve(i,j+1,N,M);
	}
	/*
	 * C(N-1+M-1,N-1)인 이유는
	 * N*M 그리드에서 [0][0]에서 [N-1][M-1]까지 최단거리로 이동할 때의 경로수를 구하는 문제이다.
	 * 아래로 이동을 D, 오른쪽으로 이동을 R이라 할 때
	 * N-1개의 D, M-1개의 R이 필요하다.
	 * D와 R을 N-1+M-1개의 빈칸에 채우는 경우를 생각하면 쉬운데,
	 * N-1+M-1개의 빈칸 중 D나 R이 들어갈 자리를 고르는 것이다.
	 * D가 들어갈 자리를 고를 때 -> C(N+M-2,N-1) == C(N+M-2,M-1) <- R이 들어갈 자리를 고를 때
	 * 교수님께서 그리드가 점이 아니므로 N-1번 M-1번만 움직여야 함을 지적해 주셨다.
	 */
	
	/*
	 * 교수님 순환식
	 * f(i, j) = f(i+1, j) + f(i, j+1)과 적절한 베이스 케이스
	 * 전체 가능한 경로수는 아래로 한 칸 간 뒤 나머지 경로수와 오른쪽으로 한 칸 간 뒤 나머지 경로수를 더한 것과 같다.
	 */
}