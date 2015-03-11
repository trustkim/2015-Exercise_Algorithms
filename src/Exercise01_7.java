/*
 * 두 문자열 str1과 str2가 동일한 검사하는 함수를 리커전으로 작성하라.
 */
public class Exercise01_7 {
	public static void main(String [] args){
		String str1="i was born to love you";
		String str2="i was born to love U";
		
		System.out.println(isEqual(str1,str2));
		
	}
	public static boolean isEqual(String A, String B) {
		if(A.length()==B.length()){
			if(A.length()==0&&B.length()==0) return true;
			
			if(A.charAt(0)!=B.charAt(0)) return false;
			else return isEqual(A.substring(1), B.substring(1));

		}else return false;
	}
}
