import java.util.Scanner;

/*
 * 1에서 N까지의 정수들 중 홀수들만의 합을 구하는 프로그램을 recursion으로 작성하라. N은 입력으로 받는다.
 */

public class Exercise02 {
	public static int n;
	
	public static void main(String [] args) {
		// 작성한 메서드를 호출하여 답을 구한 후 출력하라.
		try {
		n=(new Scanner(System.in)).nextInt();
		System.out.println(oddSum(1));
		}catch(Exception e){e.printStackTrace();}
	}
	/*
    / 1에서 n개의 정수들 중 홀수들만의 합을 구하여 반환하는 메서드를
    / 여기에 recursion으로 작성하라.
	 */
	public static int oddSum(int begin) {
		if(begin>n) return 0;
		return (begin%2==1?begin:0)+oddSum(begin+1);
	}
}