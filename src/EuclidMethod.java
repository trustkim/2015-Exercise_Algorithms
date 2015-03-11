public class EuclidMethod {
	public static void main(String [] args) {
		System.out.println(gcd(8, 64));
	}
	public static int gcd(int p, int q){
		if(q==0) return p;	// p%q == 1 이면 다음 호출 때 q%1이 되므로 0이 되어 base case로 수렴한다
		else return gcd(q, p%q);	// p<q가 되어도 p%q == p 이므로 swap이 일어난다
	}
}
