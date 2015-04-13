import java.util.Random;
/*
 * �����Ʈ���� ���ؼ� insert�� delete �Լ��� �����϶�.
 */
public class Exercise10_1 {
	public static void main(String[] args) {		
		int N = 10000;						// �߰� ������ ���� ��
		Random rd = new Random();
		RedBlackTree rbt = new RedBlackTree();

		long start = System.currentTimeMillis();
		// (1) ���� �̹� �����˻�Ʈ���� �ִ��� �˻��Ѵ�.
		for(int i=0;i<N;i++) {
			int sample = rd.nextInt(N);
			//System.out.print("now check the key is "+sample);
			if(rbt.search(rbt.root, sample)==null) {	// �����˻�Ʈ���� ������,
				//System.out.print(" insert!\n");
				rbt.insert(sample);						// (3) ������ �� ���� Ʈ���� insert�Ѵ�.
			}else {
				//System.out.print(" delete!\n");
				try{
					rbt.delete(rbt.search(rbt.root, sample));
					System.out.println("done");
				}catch(NullPointerException e) {
					e.printStackTrace();
					//rbt.inorderTraversal(rbt.root);
					//System.out.println();
					System.exit(1);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}	// (2) ���� ������ �� ���� Ʈ���� ���� �����Ѵ�.
			// (4) ���������� Ʈ���� inorder traverse �ϸ鼭 �湮�� ������� �������� ����Ѵ�.
		}
		rbt.inorderTraversal(rbt.root);
		System.out.println("\n"+((long)System.currentTimeMillis()-start)/1000.0);
	}
}

class RedBlackTree {
	public Node root;
	private static final int RED = 1;
	private static final int BLACK = 0;
	private class Node {
		private int key;
		private Node left,right,parent;
		private int color;
		Node(int key) { this.key = key; color = RED; left=right=parent=null; }
	}
	/* private methods
	 *  - Node minimum(Node x)
	 *  - Node successor(Node x)
	 *  - void leftRotate(Node x)
	 *  - void rightRotate(Node y)
	 *  - void rbInsertFixup(Node z)
	 *  - void rbDeleteFixup(Node z)
	 */
	private Node minimum(Node x) {
		while(x.left != null)
			x=x.left;
		return x;
	}
	private Node successor(Node x) {
		if(x.right!=null)
			return minimum(x.right);
		Node y = x.parent;
		while(y!=null && x==y.right) {
			x = y;
			y = y.parent;
		}
		return y;
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
			if(z.parent == z.parent.parent.left) {		// z�� �θ� �Ҿƹ����� ���� �ڽ��� ���
				Node y = z.parent.parent.right;			// y�� z�� ����
				if(y!=null && y.color==RED) {			// Case1: ������ RED (������ null�̸� �ȵȴ�)
					z.parent.color = BLACK;				// z�� �θ� BLACK����
					y.color = BLACK;					// z�� ���̵� BLACK����
					z.parent.parent.color = RED;		// z�� �Ҿƹ����� RED��
					z = z.parent.parent;				// �ٽ� z�� �Ҿƹ����� ���Ͽ� ���1 �˻� �ݺ�
				}else {	// ���2~3�� ������ BLACK�� ���
					if(z == z.parent.right) {			// Case2: z�� ������ �ڽ�
						z = z.parent;
						leftRotate(z);					// �θ� ���Ͽ� leftRotate ���� (������ ����� �θ�� �׻� �����ϹǷ� null���θ� üũ ������ �ʾƵ� ��)
					}	// ���2�� �׻� ���3���� ����			// Case3: z�� ���� �ڽ�
					z.parent.color = BLACK;				// �θ� BLACK����
					// ������ ����� �Ҿƹ��� ���� null�̸� �ȵ�(��, ������ ��尡 root�� �ڽ����� �߰��ȴ� ���. �׷��� �� ���� ���̽�3���� ����� �� ���� ���� üũ ������ �ʾƵ� ��)
					z.parent.parent.color = RED;	// �Ҿƹ����� RED��
					rightRotate(z.parent.parent);	// �Ҿƹ����� ���Ͽ� rightRotate ����
				}
			}else {										// z�� �θ� �Ҿƹ����� ������ �ڽ��� ���
				Node y = z.parent.parent.left;			// y�� z�� ����
				if(y!=null && y.color==RED) {			// Case 4
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}else {
					if(z == z.parent.left) {			// Case 5
						z = z.parent;
						rightRotate(z);					// �θ� ���Ͽ� rightRotate ����
					}
					z.parent.color = BLACK;				// Case 6
					z.parent.parent.color = RED;
					leftRotate(z.parent.parent);	// �Ҿƹ����� ���Ͽ� leftRotate ����
				}
			}
		}
		root.color = BLACK;
	}	// O(logN)
	private void rbDeleteFixup(Node x) {	// x�� ������ ��带 ����Ͽ� ������ ��� �ڸ��� ���� ����̴�(double black�̸� NIL�� ���� �ִ�)
		while(x!=null && x!=root && x.color==BLACK) { 
			if(x==x.parent.left) {									// ��� 1~4: x�� �θ��� ���� �ڽ�. x�� double black�� ���
				Node w = x.parent.right;	// w�� z�� ����(������ �ݵ�� ����. ����� Ʈ�������� w�� NIL�� �ƴϴ�. �ڽ� ��嵵 NIL�� �ƴϴ�.)
				if(w!=null) {
					if(w.color == RED) {								// ���1: ������ RED�� ���
						System.out.println("case1");
						w.color = BLACK;								// ������ BLACK����
						x.parent.color = RED;							// �θ� RED��
						leftRotate(x.parent);							// �θ� �������� leftRotate
						w = x.parent.right;								// leftRotate�� �ٲ� ������ ����. �� ���� ������ ���� �ڽ�(BLACK)�� ���ο� ������ ��.
					}	// x�� �� ���� �Ʒ��� ������ ���·� ���2~3�� ����				// ���2~4�� ������ BLACK�� ���, x�� �θ�� Unknown.
					if(w!=null&&((w.left==null&&w.right==null)||
							(w.left==null&&w.right!=null&&w.right.color==BLACK)||
							(w.left!=null&&w.left.color==BLACK&&w.right==null)||
							(w.left!=null&&w.left.color==BLACK&&w.right!=null&&w.right.color==BLACK))) {	// ���2: ������ �ڽĵ鵵 BLACK�� ���
						System.out.println("case2");
						w.color = RED;								// ���� ��带 RED��
						x = x.parent;								// x�� black �ϳ��� �Ѱ� ����. �� �θ� ��忡 ���� double black ���� �˻�.
					}else {
						if(w!=null&&(w.right==null||(w.right!=null&&w.right.color == BLACK))) {					// ���3: ������ �����ڽ��� RED�� ���
							System.out.println("case3");
							if(w.left!=null)w.left.color = BLACK;						// w�� ���� �ڽ��� BLACK����
							w.color = RED;								// w�� RED��
							rightRotate(w);								// w�� ���ؼ� rightRotate
							w = x.parent.right;							// x�� ���ο� ���� w����. ���ο� w�� ������ �ڽ��� �׻� RED�̴�
						}	// ���4�� ����									// ���4: ������ �����ڽ��� Unknown, �������ڽ��� RED�� ���
						if(w!=null) {
							System.out.println("case4");
							w.color = x.parent.color;						// w�� ���� x�� �θ��� ������
							x.parent.color = BLACK;							// x�� �θ� BLACK����
							w.right.color = BLACK;							// w�� ������ �ڽ��� BLACK����
							leftRotate(x.parent);							// x�� �θ� ���ؼ� leftRotate
							x = root;										// x�� root�� �Ǹ� while���� Ż���ϰ� ��
						}
					}
				}
			}else {													// ��� 5~8: x�� �θ��� ������ �ڽ�
				Node w = x.parent.left;		// w�� z�� ����
				if(w!=null) {
					if(w.color == RED) {								// ���5: ������ RED�� ���
						System.out.println("case5");
						w.color = BLACK;
						x.parent.color = RED;
						rightRotate(x.parent);
						w = x.parent.left;
					}
					if(w!=null&&((w.left==null&&w.right==null)||
							(w.left==null&&w.right!=null&&w.right.color==BLACK)||
							(w.left!=null&&w.left.color==BLACK&&w.right==null)||
							(w.left!=null&&w.left.color==BLACK&&w.right!=null&&w.right.color==BLACK))) {	// ���6
						System.out.println("case6");
						w.color = RED;
						x = x.parent;
					}else {
						if(w!=null&&(w.left==null||(w.left!=null&&w.left.color == BLACK))) {						// ���7
							if(w.right!=null)w.right.color = BLACK;
							System.out.println("case7");
							w.color = RED;
							leftRotate(w);
							w = x.parent.left;
						}	// ���8�� ����
						if(w!=null){
							System.out.println("case8");
							w.color = x.parent.color;						// ���8
							x.parent.color = BLACK;
							w.left.color = BLACK;
							rightRotate(x.parent);
							x = root;
						}
					}
				}
			}
		}
		if(x!=null)
			x.color = BLACK;
	}	// O(logN)
	/* public methods
	 *   - Node search(Node x, int k)
	 *   - void insert(int k)
	 *   - Node delete(Node z)
	 *   - void inorderTraversal(Node z)
	 */
	public Node search(Node x, int k) {
		if(x==null || k==x.key)
			return x;
		if(k<x.key)
			return search(x.left,k);
		else return search(x.right, k);
	}
	public void insert(int k) {
		Node z = new Node(k);
		Node y = null;		// �߰��� ����� parent ��ġ�� ����
		Node x = root;
		while(x!=null) {
			y = x;			// ���� �ݺ��������� ���� ��尡 �̹� �ݺ��������� �θ� ���
			if(z.key < x.key)
				x = x.left;	// Ű�� ������ ����
			else x = x.right;	// ũ�� ������
		}	// �߰��� ����� ��ġ ã�� ��
		z.parent = y;	// �߰��� ����� �θ� ����
		if(y==null)
			root = z;	// Tree T was empty
		else if(z.key < y.key)
			y.left = z;
		else y.right = z;
		// z����� �ڽ��� NIL�� �����ϴ� �Ͱ� RED �÷��� �ϴ� ���� Node�� �����ڿ��� ���
		rbInsertFixup(z);
	}	// O(logN)
	public Node delete(Node z) {				// z�� ������ ���
		Node y;
		if(z.left== null || z.right==null) {
			y = z;
		}else y = successor(z);
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
		}
		if(y.color == BLACK)	// ������ ��尡 BLACK�ΰ�� ������ �߻�
			rbDeleteFixup(x);
		return y;	// ������ ���
	}	// O(logN)
	public void inorderTraversal(Node x) {
		if(x!=null){
			inorderTraversal(x.left);
			System.out.print(x.key+", ");
			inorderTraversal(x.right);	
		}
	}
}