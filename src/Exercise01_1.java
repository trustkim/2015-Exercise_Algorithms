import java.util.Scanner;

public class Exercise01_1 {
	public static double[] fibonacci;
	public static void main(String[] args) {
		try {

		int	n=(new Scanner(System.in)).nextInt();
		
		long start = System.currentTimeMillis();
		System.out.println(iterative_fibonacci(n));
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
		
		fibonacci = new double[n+1];
		start = System.currentTimeMillis();
		System.out.println(memorize_recursive_fibonacci(n));
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
		
		start = System.currentTimeMillis();
		System.out.println(recursive_fibonacci(n));
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
		}catch (Exception e){e.printStackTrace();}
	}
	public static double iterative_fibonacci(int n){
		double n1=0;
		double n2=1;
		if(n>1){
			for(int i=2;i<=n;i+=2){
				n1 = n1+n2;
				n2 = n1+n2;
			}
			return n%2==0?n1:n2;
		}else return n;
	}
	public static double recursive_fibonacci(int n) {
		if(n<2) return n;
		else
			return recursive_fibonacci(n-1) + recursive_fibonacci(n-2);
	}
	public static double memorize_recursive_fibonacci(int n) {
		if(n<2) {
			fibonacci[n] = n;
			return n;
		}
		else {
			fibonacci[n] = (fibonacci[n-1]==0?memorize_recursive_fibonacci(n-1):fibonacci[n-1]) + (fibonacci[n-2]==0?memorize_recursive_fibonacci(n-2):fibonacci[n-2]);
			return fibonacci[n];
		}
	}
}
