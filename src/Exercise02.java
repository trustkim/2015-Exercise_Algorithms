public class Exercise02 {
	public static void main(String [] args) {
		int n=10;
		int [] data = {12, 1, 0, 3, 7, 4, 9, 5, 1, 15};
		// 작성한 메서드를 호출하여 답을 구한 후 출력하라.
		System.out.println(evenSum(data,0));
		System.out.println(oddSum(data,0));
	}
	/*
    / 배열 data에 저장된 n개의 정수들 중 짝수들만의 합을 구하여 반환하는 메서드를
    / 여기에 recursion으로 작성하라.
	 */
	public static int evenSum(int [] data, int begin) {
		if(begin>data.length-1) return 0;
		int temp = data[begin];
		return (temp%2==0?temp:0)+evenSum(data, begin+1);
	}
	public static int oddSum(int [] data, int begin) {
		if(begin>data.length-1) return 0;
		int temp = data[begin];
		return (temp%2==1?temp:0)+evenSum(data, begin+1);
	}

}