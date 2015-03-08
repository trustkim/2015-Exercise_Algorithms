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
}
