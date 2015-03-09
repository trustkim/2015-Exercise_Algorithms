import java.util.Scanner;

/*
 *  n자리 2진수 중에서 0이 연속해서 나오지 않는 것의 개수는? 적절한 순환식으로 세운 후 recursion으로 개수를 계산하는 프로그램을 작성하라.
 */
public class Exercise05 {
	public static void main(String [] args){
		try {
			//int N = (new Scanner(System.in)).nextInt();
			for(int N=1;N<11;N++)
			System.out.println(count_nonserial_zero(N));
		}catch (Exception e) {e.printStackTrace();}	
	}
	public static int count_nonserial_zero(int n) {
		if(n==1) return 2;
		else if(n==2) return 3;
		return count_nonserial_zero(n-1) + count_nonserial_zero(n-2);
	}
}