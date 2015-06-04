package assignment2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class HuffmanDecoder {
	@SuppressWarnings("resource")
	public static void main(String [] args)
	{
		String fileName = "";
		HuffmanCoding app = new HuffmanCoding();
		RandomAccessFile fIn;
		Scanner kb = new Scanner(System.in);
		try {
			System.out.println("Enter a file name: ");
			fileName = kb.next();
			fIn = new RandomAccessFile(fileName, "r");
			app.decompressFile(fileName, fIn);
			fIn.close();
		}catch (IOException io)
		{
			System.err.println("Cannot open " + fileName);
			io.printStackTrace();
		}
	}
}