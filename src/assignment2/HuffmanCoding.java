package assignment2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class HuffmanCoding
{
	/* create ArrayList for storing runs detected */
	private ArrayList<Run> runs;

	private void collectRuns(RandomAccessFile fIn) throws IOException
	{
		/* Store every runs red in data file, and frequency of each runs, */
		/* to ArrayList runs */

		/* first. read a byte. this byte is a Number of Integer type in 0~255 */
		/* return -1, if got EOF */
		int ch = fIn.read();	// read first byte
		int pre_ch=ch;			// Strat of File
		/* if you need store casting by byte */
		Run temp = new Run((byte) ch);	// init explicit
		while(pre_ch != -1)	// loop for read a byte until EOF
		{
			ch = fIn.read();
			if(pre_ch == ch)
			{
				temp.runLen++;
			}else {
				if(runs.contains(temp)){
					runs.get(runs.indexOf(temp)).freq++;
				}else {
					runs.add(temp);
				}
				temp = new Run((byte)ch);
			}
			pre_ch = ch;	// read next byte
		}
	}

//	private void readFile(String fileName)
//	{
//		/* open file for read */
//		RandomAccessFile fIn = null;
//		try {
//			fIn = new RandomAccessFile(fileName, "r");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		/* first. read a byte. this byte is a Number of Integer type in 0~255 */
//		/* return -1, if got EOF */
//		int ch=-1;
//		try {
//			ch = fIn.read();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		/* if you need store casting by byte */
//		byte symbol = (byte) ch;
//	}

	public HuffmanCoding()
	{
		runs = new ArrayList<Run>();
		//readFile(fileName);
	}

	public static void main(String[] args)
	{
		HuffmanCoding app = new HuffmanCoding();
		String fileName = "./src/assignment2/test_input.txt";

		/* open file for read */
		RandomAccessFile fIn = null;
		try {
			fIn = new RandomAccessFile(fileName, "r");
			app.collectRuns(fIn);
			app.PrintRuns();
			fIn.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Cannot open " + fileName);
		}
	}
	private void PrintRuns()
	{
		for(Run i:runs)
		{
			i.PrintRun();
		}
	}
}

class Run extends Object
{
	public byte symbol;	// byte symbol
	public int runLen;	// how many continue the sybol
	public int freq;	// freq in whole file

	/* creator */
	public Run(byte initSymbol)
	{
		symbol = initSymbol;
		runLen = 1;
		freq = 1;
	}

	@Override
	public boolean equals(Object other)
	{
		Run otherRun = (Run) other;
		return ((symbol == otherRun.symbol) && (runLen==otherRun.runLen));
	}

	public void PrintRun()
	{
		System.out.println(runLen+""+(char)symbol+"("+freq+")");
	}
}
