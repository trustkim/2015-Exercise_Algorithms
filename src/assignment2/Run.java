package assignment2;

public class Run implements Comparable<Run>
{
	public byte symbol;	// byte symbol
	public int runLen;	// how many continue the sybol
	public int freq;	// freq in whole file
	public Run left;	// root of left child tree in a minimum heap
	public Run right;	// root of right child tree in a minimum heap
	public int codeword;	// store 32bit integer assigned codeword
	public int codewordLen;	// actually length of assigned codeword

	/* creator */
//	public Run(byte initSymbol)
//	{	/* creator for old version collectRuns */
//		symbol = initSymbol;
//		runLen = 1;
//		freq = 1;
//		left=right=null;
//	}
	public Run(byte symbol, int count)
	{	/* creator for professor version collectRuns */
		this.symbol = symbol;
		runLen = count;
		freq = 1;
		left=right=null;	
	}
	public Run(int left, int right)
	{
		this.symbol = 0;
		runLen = 0;
		freq = left + right;
		this.left = this.right = null;
	}

	public Run() {
		symbol = 0;
		runLen = 0;
		freq = 0;
		left = right = null;
	}
	@Override
	public boolean equals(Object other)
	{
		Run otherRun = (Run) other;
		return ((symbol == otherRun.symbol) && (runLen==otherRun.runLen));
	}
	
	@Override
	public int compareTo(Run other)
	{
		return Integer.compare(freq, other.freq);
	}
	
	public int hashCode()	/* depend on symbol, runLen */ 
	{
		return (int) symbol + runLen;
	}
	
	/* methods for test or debugging */
	public String toString()
	{
		return (runLen+""+(char)symbol+"("+freq+"): ["+codeword+"("+codewordLen+")]");
	}
}

