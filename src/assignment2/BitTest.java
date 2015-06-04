package assignment2;

public class BitTest {
	public static void main(String[] args)
	{
		int a = 60;	/* 60 = 0011 1100 */
		int b = 13;	/* 13 = 0000 1101 */
		int c = 0;
		
		c = a & b;	/* 12 = 0000 1100 */
		System.out.println("a & b = " + c);
		
		c = a | b;	/* 61 = 0011 1101 */
		System.out.println("a | b = " + c);
		
		c = a ^ b;	/* 49 = 0011 0001 */
		System.out.println("a ^ b = " + c);
		
		c = ~a;		/* -61 = 1100 0011 */
		System.out.println("~a = " + c);
		
		c = a << 1;	/* 120 = 0111 1000 */
		System.out.println("a << 2 = " + c);
		
		c = (a << 1) + 1;	/* 121 = 0111 1001 */
		System.out.println("a >>> 2 = " + c);
		
		c = a << 2;			/* 240 = 1111 0000 */
		System.out.println("a >> 2 = "  + c);
		
		int codeword = 6;
		for(int i=3;i>0;i--){
			System.out.println((codeword - ((codeword >> i) << i))>>(i-1));
		}
			
	}
}
