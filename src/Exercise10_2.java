import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/*
 * ���� ��������9�� 2������ ������ TreeMap ���α׷��� ����Ž��Ʈ�� ��� �����Ʈ���� ����ϵ��� �����϶�.
 * ������ �׽�Ʈ �����ͷ� ������ �۾��� ������ �� ����ð��� ���϶�.
 */
public class Exercise10_2 {
	public static void main(String[] args){
		MyTreeMap<String, Integer> treeMap = new MyTreeMap<String, Integer>();
		MyRBTreeMap<String, Integer> rbTreeMap = new MyRBTreeMap<String, Integer>();
		
		/* �ǽ�10 ����3�� ���� �׽�Ʈ �ڵ� */
		MyRBTreeMap<Integer, Integer> test = new MyRBTreeMap<Integer, Integer>();
		test.put(21, 0);
		test.put(24, 0);
		test.put(20, 0);
		test.put(0, 0);
		test.put(19, 0);
		test.put(4, 0);
		test.test_Print();	// levelorderTraversal
		System.out.println("ceilingKey(-1) = "+test.getCeilingKey(-1));
		System.out.println("ceilingKey(3) = "+test.getCeilingKey(3));
		System.out.println("ceilingKey(4) = "+test.getCeilingKey(4));
		System.out.println("ceilingKey(5) = "+test.getCeilingKey(5));
		System.out.println("ceilingKey(21) = "+test.getCeilingKey(21));
		System.out.println("ceilingKey(25) = "+test.getCeilingKey(25));
		
		System.out.println("floorKey(25) = "+test.getFloorKey(25));
		System.out.println("floorKey(23) = "+test.getFloorKey(23));
		System.out.println("floorKey(21) = "+test.getFloorKey(21));
		System.out.println("floorKey(18) = "+test.getFloorKey(18));
		System.out.println("floorKey(1) = "+test.getFloorKey(1));
		System.out.println("floorKey(-1) = "+test.getFloorKey(-1));
		test.clear();
		
		/* �ǽ�10 ����2 �׽�Ʈ �ڵ� */
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
	public void Print(PrintStream out) {	// out���� ����ϴ� �Լ�. ��¼����� inorderTraversal��
		inorderTraversal(root, out);
	}
	public void test_Print() {
		levelorderTraversal();
	}
	public void clear() { root = null; }
	
	/* Exercise10_3�� ���� �߰��� �޼��� */
	private Node keepingNode = null;
	public K getCeilingKey(K key) {				// ceilingKey�� �ʿ��� root ������ �ʿ��� ���� �޼ҵ�
		keepingNode = null;
		return ceilingKey(root,key);
	}
	private K ceilingKey(Node x, K key) {		// private���� ���ȴ�.
		if(x==null) {							// ���� x�� null�̸� �� �̻� �˻��� ��尡 ����. keepingNode�� ��ȯ
			return keepingNode==null?null:keepingNode.key;
		}
		// x�� key�� �־��� key�� ����
		if(x.key==key) {						// key�� x.key�� ������ �� ���� ��ȯ
			return x.key;
		}
		else if(key.compareTo(x.key)<0) {
			keepingNode = x;
			return ceilingKey(x.left,key);	// key�� x.key���� ������ x.key�� ŵ�� �ΰ� �������� ����
		}else
			return ceilingKey(x.right,key);	// key�� x.key���� ũ�� x.key�� ŵ�� �ʿ� ���� �������� ����
	}
	
	public K getFloorKey(K key) {
		keepingNode = null;
		return floorKey(root,key);
	}
	private K floorKey(Node x, K key) {
		if(x==null) {							// ���� x�� null�̸� �� �̻� �˻��� ��尡 ����. keepingNode�� ��ȯ
			return keepingNode==null?null:keepingNode.key;
		}
		// x�� key�� �־��� key�� ����
		if(x.key==key) {						// key�� x.key�� ������ �� ���� ��ȯ
			return x.key;
		}
		else if(key.compareTo(x.key)<0) {
			return floorKey(x.left,key);	// key�� x.key���� ������ x.key�� ŵ���� �ʰ� �������� ����
		}else
			keepingNode = x;
			return floorKey(x.right,key);	// key�� x.key���� ũ�� x.key�� ŵ�ϰ� �������� ����
	}

	/* ���� �� Ʈ���� �߰��� private �޼ҵ��  */
	private int colorOf(Node x) {
		return (x==null?BLACK:x.color);
	}
	private Node parentOf(Node x) {
		return (x==null?null:x.parent);
	}
	private void setColor(Node x, int c) {
		if(x!=null)
			x.color=c;
	}
	private Node leftOf(Node x) {
		return (x==null?null:x.left);
	}
	private Node rightOf(Node x) {
		return (x==null?null:x.right);
	}
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
	private void rbDeleteFixup(Node x, Node p_of_x) {	// x�� ������ ��带 ����Ͽ� ������ ��� �ڸ��� ���� ����̴�(double black�̸� NIL�� ���� �ִ�)
		while(x!=root && colorOf(x)==BLACK) {
			if(x==leftOf(p_of_x)) {									// ��� 1~4: x�� �θ��� ���� �ڽ�. x�� double black�� ���
				Node w = rightOf(p_of_x);	// w�� z�� ����(������ �ݵ�� ����. ����� Ʈ�������� w�� NIL�� �ƴϴ�.)
				if(colorOf(w) == RED) {								// ���1: ������ RED�� ���
					setColor(w,BLACK);								// ������ BLACK����
					setColor(p_of_x,RED);							// �θ� RED��
					leftRotate(p_of_x);								// �θ� �������� leftRotate
					w = rightOf(p_of_x);							// leftRotate�� �ٲ� ������ ����. �� ���� ������ ���� �ڽ�(BLACK)�� ���ο� ������ ��.
				}	// x�� �� ���� �Ʒ��� ������ ���·� ���2~3�� ����			// ���2~4�� ������ BLACK�� ���, x�� �θ�� Unknown.
				if(colorOf(leftOf(w))==BLACK 
					&& colorOf(rightOf(w))==BLACK) {				// ���2: ������ �ڽĵ鵵 BLACK�� ���
					setColor(w,RED);								// ���� ��带 RED��
					x = p_of_x;										// x�� black �ϳ��� �Ѱ� ����. �� �θ� ��忡 ���� double black ���� �˻�.
					p_of_x = parentOf(x);							// ���� �Ѱ��� p_of_x�� ���� ����� �� �κп����� loop�� ������ �� �ִ�
				}else {
					if(colorOf(rightOf(w)) == BLACK) {				// ���3: ������ �����ڽ��� RED�� ���
						setColor(leftOf(w),BLACK);					// w�� ���� �ڽ��� BLACK����
						setColor(w,RED);							// w�� RED��
						rightRotate(w);								// w�� ���ؼ� rightRotate
						w = rightOf(p_of_x);						// x�� ���ο� ���� w����. ���ο� w�� ������ �ڽ��� �׻� RED�̴�
					}	// ���4�� ����									// ���4: ������ �����ڽ��� Unknown, �������ڽ��� RED�� ���
					setColor(w,colorOf(p_of_x));					// w�� ���� x�� �θ��� ������
					setColor(p_of_x,BLACK);							// x�� �θ� BLACK����
					setColor(rightOf(w),BLACK);						// w�� ������ �ڽ��� BLACK����
					leftRotate(p_of_x);								// x�� �θ� ���ؼ� leftRotate
					x = root;										// x�� root�� �Ǹ� while���� Ż���ϰ� ��	
				}
			}else {													// ��� 5~8: x�� �θ��� ������ �ڽ�
				Node w = leftOf(p_of_x);							// w�� z�� ����
				if(colorOf(w) == RED) {								// ���5: ������ RED�� ���
					setColor(w,BLACK);
					setColor(p_of_x,RED);
					rightRotate(p_of_x);
					w = leftOf(p_of_x);
				}
				if(colorOf(leftOf(w))==BLACK
					&& colorOf(rightOf(w))==BLACK) {				// ���6
					setColor(w,RED);
					x = p_of_x;
					p_of_x = parentOf(x);
				}else {
					if(colorOf(leftOf(w)) == BLACK) {				// ���7
						setColor(rightOf(w),BLACK);
						setColor(w,RED);
						leftRotate(w);
						w = leftOf(p_of_x);
					}	// ���8�� ����
					setColor(w,colorOf(p_of_x));					// ���8
					setColor(p_of_x, BLACK);
					setColor(leftOf(w),BLACK);
					rightRotate(p_of_x);
					x = root;
				}
			}
		}
		setColor(x,BLACK);
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
			rbDeleteFixup(x,parentOf(x));
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
	private void levelorderTraversal(){
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while(!queue.isEmpty()){
			Node p = queue.poll();
			if(p!=null) {
				queue.offer(leftOf(p));
				queue.offer(rightOf(p));
				System.out.println(p.key+"("+(colorOf(p)==0?"BLACK":"RED")+"), �θ� :"+(parentOf(p)!=null?parentOf(p).key:"null")+"("+(colorOf(parentOf(p))==0?"BLACK":"RED")+"), ���� �ڽ� : "+(leftOf(p)!=null?leftOf(p).key:"null")+"("+(colorOf(leftOf(p))==0?"BLACK":"RED")+"), ������ �ڽ� : "+(rightOf(p)!=null?rightOf(p).key:"null")+"("+(colorOf(rightOf(p))==0?"BLACK":"RED")+")");
			}			
		}
	}
	
}