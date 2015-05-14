import java.util.Scanner;

/*
 * �Ʒ� �׸��� ���� ���� �̿��Ͽ� �ظ��� ���� ���� ���̰� n�� �����ü�� ���� �� ���� ���� ������ ��� �ϴ� ���α׷��� ��Ŀ������ �ۼ��϶�?
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
			kb.close();
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
