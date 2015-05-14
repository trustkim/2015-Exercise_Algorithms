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
	 * C(N-1+M-1,N-1)�� ������
	 * N*M �׸��忡�� [0][0]���� [N-1][M-1]���� �ִܰŸ��� �̵��� ���� ��μ��� ���ϴ� �����̴�.
	 * �Ʒ��� �̵��� D, ���������� �̵��� R�̶� �� ��
	 * N-1���� D, M-1���� R�� �ʿ��ϴ�.
	 * D�� R�� N-1+M-1���� ��ĭ�� ä��� ��츦 �����ϸ� ���,
	 * N-1+M-1���� ��ĭ �� D�� R�� �� �ڸ��� ���� ���̴�.
	 * D�� �� �ڸ��� �� �� -> C(N+M-2,N-1) == C(N+M-2,M-1) <- R�� �� �ڸ��� �� ��
	 * �����Բ��� �׸��尡 ���� �ƴϹǷ� N-1�� M-1���� �������� ���� ������ �̴ּ�.
	 */
	
	/*
	 * ������ ��ȯ��
	 * f(i, j) = f(i+1, j) + f(i, j+1)�� ������ ���̽� ���̽�
	 * ��ü ������ ��μ��� �Ʒ��� �� ĭ �� �� ������ ��μ��� ���������� �� ĭ �� �� ������ ��μ��� ���� �Ͱ� ����.
	 */
}