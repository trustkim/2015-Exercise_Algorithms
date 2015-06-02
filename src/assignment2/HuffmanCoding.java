package assignment2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class HuffmanCoding
{
	private MinimumHeap<Run> heap;		/* minimum heap */
	private Run theRoot = null;			/* root of the Huffman tree */
	/* create ArrayList for storing runs detected */
	private ArrayList<Run> runs;
	
	private void createHuffmanTree()
	{
		heap = new MinimumHeap<Run>(runs.size());
		heap.initMinHeap(runs);	/* store all runs into the heap. */

		while(heap.getSize()>1)
		{
			/* perform extractMin two times */
			Run left = heap.extractMin();
			Run right = heap.extractMin();
			
			/* make a combined tree */
			Run temp = new Run(left.freq,right.freq);
			temp.left = left;
			temp.right = right;
			
			/* insert the combined tree into the heap. */
			heap.add(temp);
		}
		//heap.buildMinHeap();
		theRoot = heap.getRoot();
	}
//	private void collectRuns(RandomAccessFile fIn) throws IOException
//	{	// TK version
//		/* Store every runs red in data file, and frequency of each runs, */
//		/* to ArrayList runs */
//
//		/* first. read a byte. this byte is a Number of Integer type in 0~255 */
//		/* return -1, if got EOF */
//		int ch = fIn.read();	// read first byte
//		int pre_ch=ch;			// Strat of File
//		/* if you need store casting by byte */
//		Run temp = new Run((byte) ch);	// init explicit
//		while(pre_ch != -1)	// loop for read a byte until EOF
//		{
//			ch = fIn.read();
//			if(pre_ch == ch)
//			{
//				temp.runLen++;
//			}else {
//				if(runs.contains(temp)){
//					runs.get(runs.indexOf(temp)).freq++;
//				}else {
//					runs.add(temp);
//				}
//				temp = new Run((byte)ch);
//			}
//			pre_ch = ch;	// read next byte
//		}
//	}
	private void collectRuns(RandomAccessFile fIn) throws IOException
	{	// collectRuns professor version
		int ch = fIn.read();	// read first byte
		int count = 1;
		int ch2;
		while(ch!=-1)
		{
			while((ch2=fIn.read())!=-1 && ch2==ch)
			{
				count++;
			}
			addRun((byte)ch,count);
			ch = ch2; count = 1;
		}
	}
	private void addRun(byte symbol, int count)
	{
		Run temp = new Run(symbol, count);
		if(runs.contains(temp)){
			runs.get(runs.indexOf(temp)).freq++;
		}else {
			runs.add(temp);
		}
	}


	public HuffmanCoding()
	{
		runs = new ArrayList<Run>();
		heap = null;
	}

	private void assignCodewords(Run p, int codeword, int length)
	{
		if(p.left==null && p.right == null)	// if p is reaf node,
		{
			p.codeword = codeword;
			p.codewordLen = length;
		}
		else {
			assignCodewords(p.left,codeword,length+1);
			assignCodewords(p.right,codeword,length+1);
		}
	}
	public void compressFile(RandomAccessFile fIn) throws IOException
	{
		collectRuns(fIn);
		//PrintRuns();
		createHuffmanTree();
		//PrintHuffmanTree();
		assignCodewords(theRoot,0,0);
	}
	public static void main(String[] args)
	{
		HuffmanCoding app = new HuffmanCoding();
		String fileName = "./src/assignment2/test_input.txt";

		/* open file for read */
		RandomAccessFile fIn = null;
		try {
			fIn = new RandomAccessFile(fileName, "r");
			app.compressFile(fIn);
			fIn.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Cannot open " + fileName);
		}
	}
	
	/* Print test methods */
	@SuppressWarnings("unused")
	private void PrintHuffmanTree()
	{
		preOrderTraverse(theRoot,0);
	}
	private void preOrderTraverse(Run node, int depth)
	{
		for(int i=0;i<depth;i++)
		{
			System.out.print(" ");
		}
		if(node==null)
		{
			System.out.println("null");
		}else {
			System.out.println(node.toString());
			preOrderTraverse(node.left,depth + 1);
			preOrderTraverse(node.right,depth + 1);
		}
	}
	@SuppressWarnings("unused")
	private void PrintRuns()
	{
		for(Run i:runs)
		{
			System.out.println(i.toString());
		}
	}
}