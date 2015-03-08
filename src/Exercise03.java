import java.util.Scanner;


public class Exercise03 {
	public static double[][] combinations;
	public static void main(String [] args){
		try {
			Scanner kb = new Scanner(System.in);
			int n=kb.nextInt(); combinations = new double[n+1][];
			int k=kb.nextInt(); for(int i=0;i<=n;i++) combinations[i] = new double[k+1];
			
			long start = System.currentTimeMillis();
			System.out.println(memorize_combination(n,k));
			System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
			}catch(Exception e){e.printStackTrace();}
	}
	public static double combination(int n, int k){
		if(k==0) {return 1;}
		else if(k==n) {return 1;}
		else{
			return combinations[n][k] = combination(n-1,k)+combination(n-1,k-1);
		}
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
