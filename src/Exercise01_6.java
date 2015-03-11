import java.util.Scanner;

/*
 * 아래 그림과 같이 공을 이용하여 밑면의 한쪽 변의 길이가 n인 정사면체를 만들 때 사용된 공의 개수를 계산 하는 프로그램을 리커전으로 작성하라?
 */
public class Exercise01_6 {
	public static int N;
	public static void main(String [] args) {
		try {
			Scanner kb = new Scanner(System.in);
			N=kb.nextInt(); 
			
			
			long start = System.currentTimeMillis();
			System.out.println(tetrahedron(N));
			System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
			}catch(Exception e){e.printStackTrace();}
	}
	public static int tetrahedron(int n) {
		if(n==2) {
			return 4;
		}
		return inner_func(n)+tetrahedron(n-1);
	}
	public static int inner_func(int n) {
		if(n==1) return n;
		return n+inner_func(n-1);
	}
}
