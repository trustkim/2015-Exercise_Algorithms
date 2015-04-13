import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/*
 * ���� ��������9�� 2������ ������ TreeMap ���α׷��� ����Ž��Ʈ�� ��� �����Ʈ���� ����ϵ��� �����϶�.
 * ������ �׽�Ʈ �����ͷ� ������ �۾��� ������ �� ����ð��� ���϶�.
 */
public class Exercise10_2 {
	public static void main(String[] args){
		MyTreeMap<String, Integer> treeMap = new MyTreeMap<String, Integer>();
		MyRBTreeMap<String, Integer> rbTreeMap = new MyRBTreeMap<String, Integer>();
		String[] strbf = new String[100000];	// ����
		int linecnt = 0;						// ���γѹ� ī����
		try {
			Scanner sc = new Scanner(new File("input09_2.txt"));
			while(sc.hasNextLine()) {
				strbf[linecnt] = sc.nextLine();	// �� ���ξ� ���ۿ� ����. �� ���� ��°�� �����.
				linecnt++;						// ���γѹ� ī����
			}
			System.out.println("file read complete! "+linecnt+" lines");
			long start = System.currentTimeMillis();
			for(int i=0;i<linecnt;i++) {
				String[] temp = strbf[i].split(" ");	// �� �ٿ��� ���鹮�ڸ� �������� �ܾ ����. 
				for(int j=0;j<temp.length;j++) {		// �ش� ������ ��� �ܾ� �˻�
					temp[j] = myTrim(temp[j].toLowerCase());	// �ҹ��ڷ� �ٲ㼭 Ư������ �����ϴ� myTrim�� ����.
					if(temp[j]!=null)					
						treeMap.put(temp[j], i+1);		// ���ĺ� �ܾ��̸� MyTreeMap�� put��
				}
			}
			System.out.println("MyTreeMap's size is "+treeMap.size()+" nodes");
			System.out.println("treeMap Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
			
			start = System.currentTimeMillis();
			for(int i=0;i<linecnt;i++) {
				String[] temp = strbf[i].split(" ");	// �� �ٿ��� ���鹮�ڸ� �������� �ܾ ����. 
				for(int j=0;j<temp.length;j++) {		// �ش� ������ ��� �ܾ� �˻�
					temp[j] = myTrim(temp[j].toLowerCase());	// �ҹ��ڷ� �ٲ㼭 Ư������ �����ϴ� myTrim�� ����.
					if(temp[j]!=null)					
						rbTreeMap.put(temp[j], i+1);		// ���ĺ� �ܾ��̸� MyTreeMap�� put��
				}
			}
			System.out.println("MyRBTreeMap's size is "+rbTreeMap.size()+" nodes");
			System.out.println("rbTreeMap Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
			sc.close();
			try {
				PrintStream ps = new PrintStream(new File("output10_2.txt"));
				rbTreeMap.Print(ps);
				ps.close();
				System.out.println("file write complete!");
			}catch(IOException e) {e.printStackTrace();}
		}catch(FileNotFoundException e) { System.out.println("file not found...");}
	}
	private static String myTrim(String str) {	// �ܾ��� ��, �ڸ� �˻��Ͽ� �ҹ��� �̿��� ���ڸ� �����ϴ� �Լ�.
		if(str.length()==0) return null;		// ���̰� 0�� �ܾ �˻��ϸ� null�� ��ȯ
		char s = str.charAt(0);					// �˻��ϴ� �ܾ��� ù����
		char e = str.charAt(str.length()-1);	// ������ ����
		if(!isLetter(s))						// ù���ڰ� ���ĺ� �ҹ��ڰ� �ƴϸ�
			return myTrim(str.substring(1));	// ù���ڸ� ���� myTrim�˻�
		else if(!isLetter(e))					// ù���ڰ� ���ĺ� �ҹ����̰�, �� ���ڰ� ���ĺ� �ҹ��ڰ� �ƴϸ�
			return myTrim(str.substring(0, str.length()-1));	// �� ���� ���� myTrim�˻�
		else // isLetter(s) && isLetter(e): ù ����, �� ���ڰ� ���ĺ��̸�
			return str;	// str ��ȯ
	}
	private static boolean isLetter(char c) {	// �ܾ ���ĺ� �ҹ������� �˻��ϴ� �Լ�.
		return (c>='a' && c<='z');
	}
}

class MyRBTreeMap<K extends Comparable<K>, V> {
	/* ��� ����, ��ü�� ��� private���� �ܺο��� ���� ���ϰ� �� */
	private static final int RED = 1;
	private static final int BLACK = 0;
	private class Node {	// ��� Ŭ������ �ܺο� ����� ���� ��� �ް� �� ���� �ִ�.
		public K key;
		public V value;
		public int color;
		Node left,right,parent;
		Node(K key, V value) {
			this.key=key; this.value=value; left=right=parent=null;
			color = RED;
		}
	}
	private Node root;				// ��Ʈ ���
	private int nbrNodes = 0;		// Ʈ���� ��� ����

	
	/* MyTreeMap�� HashMap���� ����� ���� public �޼ҵ�� */
	public MyRBTreeMap() {
		root = null;
	}
	public boolean containsKey(K key) {
		/* Ű���� key�� �������� ������ true, ������ false�� ��ȯ  */
		return search(root, key)!=null?true:false;
	}
	public boolean isEmpty() {
		/* empty�̸� true�� ��ȯ */
		return nbrNodes==0?true:false;
	}
	public V get(K key) {
		/* Ű���� key�� �������� ã�Ƽ� �� value�� ��ȯ, ������ null�� ��ȯ  */
		return search(root, key).value;
	}
	public V put(K key, V value) {
		Node node = search(root, key);
		V oldValue = null;
		if(node==null) {					// Ű�� �������� �ʾ�����
			insert(new Node(key, value));	// (key,value)�� �����Ѵ�
		}else {
			oldValue = node.value;
			node.value = value;	// Ű�� �̹� ������ ���ο� ������ ����
		}
		return oldValue;	// ���� �� ��ȯ
	}
	public int size() { return nbrNodes; }
	public V remove(K key) {
		Node node = search(root,key);
		V deletedValue = null;
		if(node!=null) {
			deletedValue=delete(node).value;
		}
		return deletedValue;
	}
	public void Print(PrintStream out) {	// out���� ����ϴ� �Լ�
		inorderTraversal(root, out);
	}
	public void clear() { root = null; }

	/* ���� �� Ʈ���� �߰��� private �޼ҵ��  */
	private void leftRotate(Node x) {	// y != null�̶�� ����
		Node y = x.right;
		x.right = y.left;
		if(y.left!=null)		// ��Ÿ�� null�� �ƴϸ�
			y.left.parent = x;
		// ������� �ڽ� ���� ����
		// �θ� ���� ���� ����
		y.parent = x.parent;	// x�� �θ� y�� �θ�� �ٲ�
		if(x.parent == null) {
			root = y;
		}else if(x==x.parent.left) {
			x.parent.left = y;
		}else x.parent.right = y;
		// x�� y ���� ���� ����
		y.left = x;
		x.parent = y;
	} // O(1)
	private void rightRotate(Node y) {
		Node x = y.left;
		y.left = x.right;
		if(x.right!=null)			// ��Ÿ�� null�� �ƴϸ�
			x.right.parent = y;
		x.parent = y.parent;
		if(y.parent == null) {
			root = x;
		}else if(y==y.parent.left) {
			y.parent.left = x;
		}else y.parent.right = x;
		x.right = y;
		y.parent = x;
	} // O(1)
	private void rbInsertFixup(Node z) {
		while(z.parent!=null && z.parent.color==RED) {	// z�� �θ� RED�� ���(RED-RED violation). �Ҿƹ����� �ݵ�� BLACK
			if(z.parent == z.parent.parent.left) {	// z�� �θ� �Ҿƹ����� ���� �ڽ��� ���
				Node y = z.parent.parent.right;		// y�� z�� ����
				if(y!=null && y.color==RED) {					// Case1: ������ RED
					z.parent.color = BLACK;			// z�� �θ� BLACK����
					y.color = BLACK;				// z�� ���̵� BLACK����
					z.parent.parent.color = RED;	// z�� �Ҿƹ����� RED��
					z = z.parent.parent;			// �ٽ� z�� �Ҿƹ����� ���Ͽ� ���1 �˻� �ݺ�
				}else {	// ���2~3�� ������ BLACK�� ���
					if(z == z.parent.right) {		// Case2: z�� ������ �ڽ�
						z = z.parent;
						leftRotate(z);				// �θ� ���Ͽ� leftRotate ����
					}	// ���2�� �׻� ���3���� ����		// Case3: z�� ���� �ڽ�
					z.parent.color = BLACK;			// �θ� BLACK����
					if(z.parent.parent!=null) {
						z.parent.parent.color = RED;	// �Ҿƹ����� RED��
						rightRotate(z.parent.parent);	// �Ҿƹ����� ���Ͽ� rightRotate ����
					}
				}
			}else {									// z�� �θ� �Ҿƹ����� ������ �ڽ��� ���
				Node y = z.parent.parent.left;		// y�� z�� ����
				if(y!=null && y.color==RED) {					// Case 4
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}else {
					if(z == z.parent.left) {		// Case 5
						z = z.parent;
						rightRotate(z);				// �θ� ���Ͽ� rightRotate ����
					}
					z.parent.color = BLACK;			// Case 6
					if(z.parent.parent!=null) {
						z.parent.parent.color = RED;
						leftRotate(z.parent.parent);	// �Ҿƹ����� ���Ͽ� leftRotate ����
					}
				}
			}
		}
		root.color = BLACK;
	}	// O(logN)
	private void rbDeleteFixup(Node x) {
		while(x!=null && x!=root && x.color==BLACK) {
			if(x==x.parent.left) {	// ��� 1~4: x�� �θ��� ���� �ڽ�
				Node w = x.parent.right;// w�� z�� ����
				if(w!=null){
					if(w.color == RED) {								// ���1: ������ RED�� ���
						w.color = BLACK;
						x.parent.color = RED;
						leftRotate(x.parent);
						w = x.parent.right;
					}
					if(w.left!=null && w.left.color==BLACK && w.right!=null && w.right.color==BLACK) {	// ���2
						w.color = RED;
						x = x.parent;
					}else {
						if(w.right!=null&&w.right.color == BLACK) {					// ���3
							if(w.left!=null)
								w.left.color = BLACK;
							w.color = RED;
							rightRotate(w);
							w = x.parent.right;
						}	// ���4�� ����
						w.color = x.parent.color;						// ���4
						x.parent.color = BLACK;
						if(w.right!=null)
							w.right.color = BLACK;
						leftRotate(x.parent);
						x = root;
					}
				}
			}else {					// ��� 5~8: x�� �θ��� ������ �ڽ�
				Node w = x.parent.left;// w�� z�� ����
				if(w!=null){
					if(w.color == RED) {								// ���5: ������ RED�� ���
						w.color = BLACK;
						x.parent.color = RED;
						rightRotate(x.parent);
						w = x.parent.left;
					}
					if(w.left!=null && w.left.color==BLACK && w.right!=null && w.right.color==BLACK) {	// ���6
						w.color = RED;
						x = x.parent;
					}else {
						if(w.left!=null&&w.left.color == BLACK) {						// ���7
							if(w.right!=null)
								w.right.color = BLACK;
							w.color = RED;
							leftRotate(w);
							w = x.parent.left;
						}	// ���8�� ����
						w.color = x.parent.color;						// ���8
						x.parent.color = BLACK;
						if(w.left!=null)
							w.left.color = BLACK;
						rightRotate(x.parent);
						x = root;
					}
				}
			}
		}
		if(x!=null)
			x.color = BLACK;
	}	// O(logN)
	/* HashMap���� ��� ���� Tree �������� ó���� ���� private �޼ҵ�� */
	private Node search(Node x, K k) {
		if(x==null || k.compareTo(x.key)==0)
			return x;
		if(k.compareTo(x.key)<0)
			return search(x.left, k);
		else
			return search(x.right, k);
	}
	private Node minimum() {
		Node x = root;
		while(x.left != null)
			x=x.left;
		return x;
	}
	private Node successor(Node x) {
		if(x.right!=null)
			return minimum();
		Node y = x.parent;
		while(y!=null && x==y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}
	private void insert(Node z) {
		Node y = null;		// �߰��� ����� parent ��ġ�� ����
		Node x = root;
		while(x!=null) {
			y = x;			// ���� �ݺ��������� ���� ��尡 �̹� �ݺ��������� �θ� ���
			if(z.key.compareTo(x.key)<0)
				x = x.left;	// Ű�� ������ ����
			else x = x.right;	// ũ�� ������
		}	// �߰��� ����� ��ġ ã�� ��
		z.parent = y;	// �߰��� ����� �θ� ����
		if(y==null)
			root = z;	// Tree T was empty
		else if(z.key.compareTo(y.key)<0)
			y.left = z;
		else y.right = z;
		// z����� �ڽ��� NIL�� �����ϴ� �Ͱ� RED �÷��� �ϴ� ���� Node�� �����ڿ��� ���
		rbInsertFixup(z);
		nbrNodes++;	// ��ü ��� ���� 1����
	}
	private Node delete(Node z) {
		Node y;
		if(z.left== null || z.right==null) {
			y = z;
		}else y = successor(z);			// ������ ����� ����, ������ �ڽ��� ���� �ϹǷ� z�� ���� Ʈ���� MAX�� �� ����.
		Node x;
		if(y.left!=null)
			x=y.left;
		else x=y.right;
		if(x!=null)
			x.parent=y.parent;
		if(y.parent==null) {
			root = x;
		}else if(y==y.parent.left) {
			y.parent.left = x;
		}else y.parent.right = x;
		if(y!=z) {
			z.key = y.key;
			z.value = y.value;
		}
		if(y.color == BLACK)	// ������ ��尡 BLACK�ΰ�� ������ �߻�
			rbDeleteFixup(x);
		if(nbrNodes>0) nbrNodes--;	// ��ü ��� ���� 1 ����
		return y;	// ������ ��Ʈ
	}
	private void inorderTraversal(Node x, PrintStream out) {
		if(x!=null){
			inorderTraversal(x.left, out);
			out.append((String.format("%-45s", x.key.toString())+x.value.toString()+" lines\n"));	// key�� value�� String�� �ƴ� ��쵵 ���� ������? 
			inorderTraversal(x.right, out);
		}
	}
}