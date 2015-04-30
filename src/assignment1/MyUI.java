package assignment1;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
/*
 * UI �Ŵ���
 */
@SuppressWarnings("serial")
public class MyUI extends JFrame implements ActionListener {
	private static JMenuBar menuBar;
	private static JMenu menu;
	private static JMenuItem item;
	private static DrawCanvas drawCanvas;
	private static JFileChooser fc = new JFileChooser(".\\");		// ���� Ž�� Ŭ����
	private static JFrame fileExplorerWindow = new JFrame();	// ���� Ž�� ��ȭ���� ������
	private static boolean isLoad = false;
	private static boolean isEditing = false;
	private static JFrame editFrame;
	MyUI(){
		/* main Frame */
		super("BinaryTreeDrawing");
		setMinimumSize(new Dimension(700, 550));	// �ּ� ������ ũ�� ����. pack�� ���ؼ�
		setSize(700,550); 							// ������ũ��
		setLayout(new BorderLayout()); 				// ������ ����

		/* menuBar */
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menu = new JMenu("File");
		menuBar.add(menu);
		item = new JMenuItem("File Load");
		menu.add(item);
		item.addActionListener(this);
		menu = new JMenu("Edit");
		menuBar.add(menu);
		item = new JMenuItem("open Edit window");
		menu.add(item);
		item.addActionListener(this);

		/* DrawPanel */
		Container cp = getContentPane();
		drawCanvas = new DrawCanvas();
		cp.add(drawCanvas);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	// ���α׷� ����� ���� ����.
	}
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if (cmd.equals("File Load")) {
			int result = fc.showOpenDialog(fileExplorerWindow);
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					Scanner sc = new Scanner(new File(fc.getSelectedFile().getPath()));
					buildTree(sc);
					sc.close();
				}catch(FileNotFoundException e) {e.printStackTrace();}
			}
		}
		if(cmd.equals("open Edit window")){
			if(isLoad && !isEditing) {
				msgbox("Ʈ���� �����Ͽ� �����ϸ� ������ Ʈ���� �׷� �ݴϴ�.\n������ ���Ͽ��� ������ �����Ƿ� �ٽ� ����ȭ���� ���� ������� ���ư��ϴ�.");
				isEditing = true;
				editFrame = new JFrame("Edit Tree");
				Container edcp = editFrame.getContentPane();
				JTextPane treeEdit = new JTextPane();
				try{
					Scanner sc = new Scanner(new File(fc.getSelectedFile().getPath()));
					StyledDocument text = treeEdit.getStyledDocument();
					while(sc.hasNextLine()){
						try {
							text.insertString(text.getLength(), sc.nextLine()+"\n",text.getStyle("regular"));
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
					sc.close();
				}catch(FileNotFoundException e) {e.printStackTrace();}
				edcp.add(treeEdit);
				editFrame.setSize(400, 400);
				editFrame.setVisible(true);
				editFrame.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						isEditing = false;
						Scanner sc = new Scanner(treeEdit.getText());
						buildTree(sc);
						sc.close();
					}
				});
			}else {
				if(!isLoad) {
					msgbox("������ �ε���� �ʾҽ��ϴ�! File Load�� �����ϼ���.");
				}
				if(isEditing) {
					msgbox("���� ���Դϴ�. ���� ���� â�� �ݰ� �����ϼ���.");
				}
			}
		}
	}
	private void buildTree(Scanner sc) {
		try {
			int N = sc.nextInt();	// ��ü ��� ������ ����
			int[][] nodeTable = new int[N][4];	// �� ���ξ� ������� ������ �迭.
			for(int i=1;i<N;i++){
				int[] k = new int[4];
				k[1] = sc.nextInt();
				k[2] = sc.nextInt();
				k[3] = sc.nextInt();
				nodeTable[i] = k;
			}// file read complete

			BinaryTree myTree = new BinaryTree(nodeTable);	// �� ���� �迭�� Ʈ���� ����
			drawCanvas.repaint(myTree);

			this.pack();
		}catch(Exception e) {
			msgbox("Ʈ�� ���� ����! ���� �߸��ƴ��� Ȯ���ϼ���.\n ������ ���� ���α׷��� �����մϴ� :D");
			System.exit(1);
		}
	}
	private void msgbox(String s){
		JOptionPane.showMessageDialog(null, s);
	}

	public void init() {

	}

	class DrawCanvas extends Canvas {
		private int n;		// ��ü Ʈ���� ��� ����
		private int level;	// ��ü Ʈ���� �ִ� ����
		int xlength = 680, ylength = 480;	// ĵ���� ũ��
		int[][] posTable;
		DrawCanvas(){
			setSize(xlength, ylength); //ĵ���� ũ��
			setBackground(Color.YELLOW);
		}
		private void repaint(BinaryTree tree) {
			isLoad = true;
			n = tree.getSize();
			posTable = new int[n][5];
			posTable = tree.getPosTable(posTable);
			for(int i=0;i<posTable.length;i++)
				for(int j=0;j<5;j++)
					posTable[i][j] = posTable[i][j];
			level = findMaxLV(posTable);
			paint(this.getGraphics());
		}
		private int findMaxLV(int[][] Arr) {
			int res = Arr[0][2];
			for(int i=1;i<Arr.length;i++) {
				res = Math.max(res, Arr[i][2]);
			}
			return res;
		}

		public void paint(Graphics g){
			g.clearRect(0, 0, xlength, ylength);	// �׻� ���� �׸�
			if(isLoad){
				int xnum = xlength/(n+1);
				int ynum = ylength/(level+1);

				/* �׸��� �׷� �ֱ� */
				g.setColor(Color.white);
				for(int a = 0; a <= n; a++){   g.drawLine(xnum*a, 0, xnum*a, ylength); }
				for(int b = 0; b <= level; b++){ g.drawLine(0, ynum*b, xlength, ynum*b); }

				for(int i = 0; i < posTable.length; i++){
					if(posTable[i][3] == -1){
						g.setColor(Color.white);
						g.fillOval(xnum*posTable[i][1]-20, ynum-10, 40, 20);
						g.setColor(Color.black);
						g.drawOval(xnum*posTable[i][1]-20, ynum-10, 40, 20);
						g.drawString(Integer.toString(posTable[i][0]), xnum*(posTable[i][1])-3, ynum+5);
					}
					else{
						g.setColor(Color.BLACK);
						g.drawLine(xnum*posTable[i][1], ynum*posTable[i][2]-10, xnum*posTable[i][3], ynum*posTable[i][4]+10);

						g.setColor(Color.white);
						g.fillOval(xnum*posTable[i][1]-20, ynum*posTable[i][2]-10, 40, 20);
						g.setColor(Color.black);
						g.drawOval(xnum*posTable[i][1]-20, ynum*posTable[i][2]-10, 40, 20);
						g.drawString(Integer.toString(posTable[i][0]), xnum*(posTable[i][1])-3, ynum*(posTable[i][2])+5);
					}
				}
			}
		}
	}

}
