package assignment2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

public class HuffmanCoding
{
	private MinimumHeap<Run> heap;		/* minimum heap */
	private Run theRoot = null;			/* root of the Huffman tree */
	private ArrayList<Run> runs;		/* create ArrayList for storing runs detected */
	private HashMap<Run,Run> map;
	private long charCnt;
	
	/* step6: Decoding */
	private void decode(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException{
		int buffer = 0;
		Run temp = theRoot;

		while((buffer=fIn.read())!=-1)
		{
			for(int i=8;i>0 && fOut.getFilePointer() < charCnt;i--)
			{
				int codeword = (buffer - ((buffer >> i) << i)) >> (i-1);	// parse a bit
				if(codeword == 0)
					temp = temp.left;
				else if(codeword == 1)
					temp = temp.right;
				if(temp.left == null && temp.right == null)
				{
					for(int j=0;j<temp.runLen;j++)
						fOut.write(temp.symbol);
					temp = theRoot;
				}
			}
		}
	}
	private void inputFrequencies(RandomAccessFile fIn) throws IOException {
		int dataIndex = fIn.readInt();
		charCnt = fIn.readLong();
		runs.ensureCapacity(dataIndex);
		for(int j=0;j<dataIndex;j++)
		{
			Run r = new Run();
			r.symbol = (byte) fIn.read();
			r.runLen = fIn.readInt();
			r.freq = fIn.readInt();
			runs.add(r);
		}
	}
	public void decompressFile(String inFileName, RandomAccessFile fIn) throws IOException
	{
		String outFileName = new String(inFileName + ".dec");
		RandomAccessFile fOut = new RandomAccessFile(outFileName, "rw");
		inputFrequencies(fIn);
		createHuffmanTree();
		assignCodewords(theRoot, 0, 0);
		//PrintHuffmanTree();
		decode(fIn,fOut);
		System.out.println("decompressFile complete.");
	}
	
	/* step5: Encoding */
	private void encode(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException
	{
		int ch = fIn.read();
		int count = 1;
		int ch2;
		int buffer = 0;
		int bufferSize = 0;
		while(ch!=-1)
		{
			/* recognize a run */
			while((ch2=fIn.read())!=-1 && ch2==ch)
			{
				count++;
			}
			
			/* find the codeword for the run(symbol, runLen) */
			Run temp = map.get(new Run((byte)ch, count));
			
			/* pack the codeword into the buffer */
			for(int i=temp.codewordLen;i>0;i--){
				int codeword = (temp.codeword - ((temp.codeword >> i) << i)) >> (i-1);	// parse a bit
				buffer = (buffer << 1) | codeword;
				bufferSize++;
				if(bufferSize == 32)	/* if the buffer becomes full */
				{
					fOut.writeInt(buffer);		/* write the buffer into the compressed file */
					buffer = 0;
					bufferSize = 0;
				}
			}
			ch = ch2; count = 1;
		}
		
		if(buffer < Integer.MAX_VALUE && buffer > 0)	/* if buffer is not empty */
		{
			buffer = (buffer << (32 - bufferSize));
			fOut.writeInt(buffer);
		}
	}
	private void outputFrequencies(RandomAccessFile fIn, RandomAccessFile fOut) throws IOException
	{
		fOut.writeInt(runs.size());	/* at first, write number of all runs */
		fOut.writeLong(fIn.getFilePointer());
		
		for(int j=0;j<runs.size();j++)
		{
			Run r = runs.get(j);
			fOut.write(r.symbol);	// write a byte
			fOut.writeInt(r.runLen);
			fOut.writeInt(r.freq);
		}
	}
	public void compressFile(String inFileName, RandomAccessFile fIn) throws IOException
	{
		String outFileName = new String(inFileName + ".z");
		RandomAccessFile fOut = new RandomAccessFile(outFileName, "rw");
		
		long start = System.currentTimeMillis();
		bufferedCollectRuns(fIn);
		//PrintRuns();
		outputFrequencies(fIn,fOut);
		createHuffmanTree();
		//PrintHuffmanTree();
		assignCodewords(theRoot,0,0);
		//PrintRuns();
		storeRunsIntoHashMap(theRoot);
		fIn.seek(0);
		encode(fIn,fOut);
		System.out.println("compressFile complete. output file size is " + fOut.getFilePointer() + " bytes. Elapsed: "+(((long)System.currentTimeMillis()-start)/1000));
	}
	
	/* step4: searching codeword */
	private void storeRunsIntoHashMap(Run p)
	{
		/* recursive put all leaf-nodes in the huffmanTree to map */
		if(p.left == null && p.right == null)	// if p is leaf-node,
		{
			map.put((new Run(p.symbol,p.runLen)), p);
		}else {
			storeRunsIntoHashMap(p.left);
			storeRunsIntoHashMap(p.right);
		}
	}
	
	/* step3: assign codewords */
	private void assignCodewords(Run p, int codeword, int length)
	{
		if(p.left==null && p.right == null)	// if p is leaf-node,
		{	// then assign codeword
			p.codeword = codeword;
			p.codewordLen = length;
		}
		else {
			assignCodewords(p.left,(codeword << 1),length+1);
			assignCodewords(p.right,(codeword << 1) + 1,length+1);
		}
	}
	
	/* step2: createHuffmanTree */
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
		theRoot = heap.getRoot();
	}
	
	/* step1: collectRuns */
	private void bufferedCollectRuns(RandomAccessFile fIn) throws IOException
	{
		byte[] buffer = new byte[100*1024*1024];	// use 100MB buffer
		int size = fIn.read(buffer);
		int ch = buffer[0];
		while(size!=-1)
		{
			ch = collectRuns(ch, buffer,size);
			size = fIn.read(buffer);
		}
	}
	private int collectRuns(int init, byte[] buffer, int size) throws IOException
	{
		int ch = init;	// read first byte
		int count = 1;
		int ch2=0;
		int i=0;
		while(i+count<size)
		{
			while(i+count<size && (ch2=buffer[i+count])==ch)
			{
				count++;
			}
			addRun((byte)ch,count);
			ch = ch2; 
			i = i+count;
			count = 1;
		}

		return buffer[size-1];
	}
	@SuppressWarnings("unused")
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
//			if(ch==37 && count == 1)
//				System.out.println(true);
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

	/* main methods */
	public static void main(String[] args)
	{
		HuffmanCoding app = new HuffmanCoding();
		String fileName = "./src/assignment2/test_input.txt";

		/* open file for read */
		RandomAccessFile fIn = null;
		try {
			fIn = new RandomAccessFile(fileName, "r");
			app.compressFile(fileName, fIn);
			fIn.close();			
		} catch (IOException e) {
			System.err.println("Cannot open " + fileName);
			e.printStackTrace();
		}
	}
	
	/* creator */
	public HuffmanCoding()
	{
		runs = new ArrayList<Run>();
		heap = null;
		map =  new HashMap<Run,Run>();
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