import java.util.Scanner;


public class Exercise04 {
	public static double[][] combinations;
	public static void main(String [] args){
		try {
			Scanner kb = new Scanner(System.in);
			int N=kb.nextInt(); 
			int M=kb.nextInt();
			combinations = new double[N+M+1][];
			for(int i=0;i<=N+M;i++) combinations[i] = new double[N+1];
			
			long start = System.currentTimeMillis();
			System.out.println(memorize_combination(N+M,N));
			System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
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
	/*
	 * C(N+M,N)인 이유는
	 * N*M 그리드에서 [0][0]에서 [N-1][M-1]까지 최단거리로 이동할 때의 경로수를 구하는 문제이다.
	 * 아래로 이동을 D, 오른쪽으로 이동을 R이라 할 때
	 * N개의 D, M개의 R이 필요하다.
	 * D와 R을 N+M개의 빈칸에 채우는 경우를 생각하면 쉬운데,
	 * N+M개의 빈칸 중 D나 R이 들어갈 자리를 고르는 것이다.
	 * D가 들어갈 자리를 고를 때 -> C(N+M,N) == C(N+M,M) <- R이 들어갈 자리를 고를 때
	 */
}