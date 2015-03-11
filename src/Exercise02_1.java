
public class Exercise02_1 {
	public static void main(String [] args) {
		int n=10;	// 전역 변수로 선언하면 굳이 명시하지 않아도 됨
		int [] data = {12, 1, 0, 3, 7, 4, 9, 5, 1, 15};
		System.out.println(evenSum(0, n, data));
	}
	public static int evenSum(int begin, int end, int [] data) {
		if(end<=data.length){
			if(begin>end-1) return 0;
			else
				return (data[begin]%2==0?data[begin]:0)+evenSum(begin+1, end, data);
		}else return -1;
	}
}