import java.util.Scanner;


public class Exercise02_3 {
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(System.in);
			String str = sc.next();
			System.out.println(isPalindrome(str, 0, str.length()-1));
			sc.close();
		} catch(Exception e) {e.printStackTrace();}
	}
	public static boolean isPalindrome(String str, int begin, int end) {
		if(begin>end) return true;
		else if(str.charAt(begin)!=str.charAt(end)) return false;
		else
			return isPalindrome(str, begin+1, end-1);
	}
}
