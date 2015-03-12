
public class Exercise02_4 {
	public static void main(String [] args) {
		String str1 = "abc";
		String str2 = "ab";
		System.out.println(compare(str1, str2));
		System.out.println(solution(str1,str2,0));
	}
	public static int compare(String str1, String str2){
		int temp = str2.charAt(0)-str1.charAt(0);	
		if(temp==0) {											// 현재 검사하는 문자가 같은 경우
			if(Math.min(str1.length(), str2.length())==1) {		// 검사할 문자가 이제 마지막인 경우
				if(str1.length()==str2.length()) {				// 두 문자 길이가 같은 경우는 동일할 때
					return 0;
				} else return str1.length()>str2.length()?1:-1;	// 더 긴쪽이 사전식 순서가 늦다
			}return compare(str1.substring(1), str2.substring(1));	// 검사할 문자가 남은 경우
		} else return temp>0?-1:1;	// 두 문자열의 첫번째 문자끼리 서로 비교하여 다르면 결과를 출력
	}
	
	public static int solution(String str1, String str2, int begin) {
		if(begin==Math.min(str1.length(), str2.length())){
			return str1.length()>str2.length()?1:str1.length()<str2.length()?-1:0;
		}else return str1.charAt(begin)>str2.charAt(begin)?1:str1.charAt(begin)<str2.charAt(begin)?-1:solution(str1,str2,begin+1);
	}
}