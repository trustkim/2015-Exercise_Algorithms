/*
 *  n�ڸ� 2���� �߿��� 0�� �����ؼ� ������ �ʴ� ���� ������? ������ ��ȯ������ ���� �� recursion���� ������ ����ϴ� ���α׷��� �ۼ��϶�.
 */
public class Exercise01_5 {
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