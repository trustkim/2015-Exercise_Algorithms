import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/*
 * Map Ȥ�� �ɺ� ���̺�(symbol table)�̶�� �θ��� �߻�����Ÿ��(abstract data type)�� �������� <key, value> �ֵ���
 * �����ϰ� key�� ���� �˻� ����� �����Ѵ�. ���� �� � ����� "�̸�", "����", "����", "�ּ�" ���� �����͸� ������ �� �̰���
 * <"name", "HongGilDong">, <"age", "24">, <"gender", "male">, <"address", "Busan">�� ���� ������ 
 * <key, value>�� ���������� ǥ���Ͽ� �����Ѵ�.
 * ���� �˻�Ʈ���� �̿��Ͽ� �̷��� ����� �����ϴ� Ŭ���� MyTreeMap�� �ۼ��϶�. Ʈ���� �� ���� �� ���� ������ �ʵ带 ������ key��
 * value�� ���� ������ �� �ֵ��� �Ѵ�. Java�� ������ ��� �Ʒ��� �����ϰ� generics�� �����Ͽ� ������ ��ü�� key�� value�� �� ��
 * �ֵ��� �����϶�. �������Ͽ� �����ϴ� ��� �ܾ ������ MyTreeMap�� ������ ���� ��Ģ���� �����Ѵ�. ���Ͽ� ������ �ܾ���� �������
 * �о �ҹ��ڷ� �ٲ۴�. �ܾ��� ���̳� �ڿ� ���� Ư�����ڳ� ���ڵ��� ������ �� �� �ܾ�� �� �ܾ ������ ���ι�ȣ�� ���� �ʿ� put�Լ���
 * ȣ���Ͽ� �߰��Ѵ�. ��������� �ʿ��� ���Ͽ� ������ ��� �ܾ��� �� �ܾ ���� ���������� ������ ���ι�ȣ�� �ֵ��� ����Ǿ� �־�� �Ѵ�.
 */
public class Exercise09_2 {
	public static void main(String[] args){
		MyTreeMap<String, Integer> treeMap = new MyTreeMap<String, Integer>();
		String[] strbf = new String[100000];	// ����
		int linecnt = 0;						// ���γѹ� ī����
		try {
			Scanner sc = new Scanner(new File("input09_2.txt"));
			while(sc.hasNextLine()) {
				strbf[linecnt] = sc.nextLine();	// �� ���ξ� ���ۿ� ����. �� ���� ��°�� �����.
				linecnt++;						// ���γѹ� ī����
			}
			System.out.println("file read complete! "+linecnt+" lines");
			for(int i=0;i<linecnt;i++) {
				String[] temp = strbf[i].split(" ");	// �� �ٿ��� ���鹮�ڸ� �������� �ܾ ����. 
				for(int j=0;j<temp.length;j++) {		// �ش� ������ ��� �ܾ� �˻�
					temp[j] = myTrim(temp[j].toLowerCase());	// �ҹ��ڷ� �ٲ㼭 Ư������ �����ϴ� myTrim�� ����.
					if(temp[j]!=null)					
						treeMap.put(temp[j], i+1);		// ���ĺ� �ܾ��̸� MyTreeMap�� put��
				}
			}
			System.out.println("MyTreeMap's size is "+treeMap.size()+" nodes");
			sc.close();
			try {
				PrintStream ps = new PrintStream(new File("output09_2.txt"));
				treeMap.Print(ps);
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

class MyTreeMap<K extends Comparable<K>, V> {
	/* ��� ����, ��ü�� ��� private���� �ܺο��� ���� ���ϰ� �� */
	private class Node {	// ��� Ŭ������ �ܺο� ����� ���� ��� �ް� �� ���� �ִ�.
		public K key;
		public V value;
		Node left,right,parent;
		Node(K key, V value) { this.key=key; this.value=value; left=right=parent=null;}
	}
	private Node root;				// ��Ʈ ���
	private int nbrNodes = 0;		// Ʈ���� ��� ����
	
	/* MyTreeMap�� HashMap���� ����� ���� public �޼ҵ�� */
	public MyTreeMap() {
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
//	private Node maximum() {
//	Node x = root;
//	while(x.right != null)
//		x = x.right;
//	return x;
//}
//	private Node predecessor(Node x) {
//	if(x.left!=null)
//		return maximum(x.left);
//	Node y = x.parent;
//	while(y!=null && x==y.left) {
//		x = y;
//		y = y.parent;
//	}
//	return y;
//}
//	private void preorderTraversal(Node x) {
//		if(x!=null){
//			System.out.print(x.key+", ");
//			preorderTraversal(x.left);
//			preorderTraversal(x.right);	
//		}
//	}
//	private void postorderTraversal(Node x) {
//		if(x!=null){
//			postorderTraversal(x.left);
//			postorderTraversal(x.right);
//			System.out.print(x.key+", ");	
//		}
//	}
//	private void levelorderTraversal(Node root) {
//		Queue<Node> queue = new LinkedList<Node>();
//		queue.offer(root);
//		while(!queue.isEmpty()) {
//			Node node = queue.poll();
//			System.out.print(node.key+", ");
//			if(node.left!=null) queue.offer(node.left);
//			if(node.right!=null) queue.offer(node.right);
//		}
//	}
}