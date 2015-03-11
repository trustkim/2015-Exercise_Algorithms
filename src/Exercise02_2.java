
public class Exercise02_2 {
	public static void main(String [] args) {
		int n=10;
		int [] data = {12, 1, 0, 3, 7, 4, 9, 5, 1, 15};
		System.out.println(findMin(0,n,data));
	}
	public static int findMin(int begin, int end, int[] data){
		if(begin==end-1) return data[begin]; 
		return Math.min(data[begin], findMin(begin+1, end, data));
	}
}
