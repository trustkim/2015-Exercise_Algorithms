import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/*
 * �����Ʈ���� ���ؼ� insert�� delete �Լ��� �����϶�.
 */
public class Exercise10_1 {
	public static void main(String[] args) {		
		int N = 10000000;						// �߰� ������ ���� ��
		Random rd = new Random();
		RedBlackTree rbt = new RedBlackTree();
		/*
		 * rbDeleteFixup(x)���� x�� null�� ���̽� �׽�Ʈ
		 */
		//		rbt.insert(2);
		//		rbt.insert(0);
		//		rbt.insert(3);
		//		rbt.insert(4);
		//		rbt.inorderTraversal(rbt.root);
		//		rbt.delete(rbt.search(rbt.root,4));
		//		rbt.delete(rbt.search(rbt.root,3));
		//		rbt.inorderTraversal(rbt.root);
		//		System.out.println();
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
					//System.out.println("done");
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
		if(!rbt.isRedBlack()){
			System.out.println();
			rbt.levelorderTraversal();
			System.exit(0);
		}else System.out.println("true");
		//rbt.inorderTraversal(rbt.root);
		System.out.println("\n"+((long)System.currentTimeMillis()-start)/1000.0);
	}
}

class RedBlackTree {
	public boolean isRedBlack(){
		Node tmp;
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while(!queue.isEmpty()){
			tmp = queue.poll();
			if(tmp!=null) {
				queue.offer(tmp.left);
				queue.offer(tmp.right);				
				if(colorOf(tmp)==RED) {
					if(colorOf(leftOf(tmp))==RED || colorOf(rightOf(tmp))==RED) {
						System.out.println("red-red violation!");
						levelorderTraversal();
						return false;
					}
				}	
			}
		}
		//System.out.println("red-red violation checked TRUE!");
		if(colorOf(root)==BLACK && BlackHeight(root) != -1)
			return true;

		return false;

	}

	public int BlackHeight(Node x){
		boolean bo = true;

		if(x == null)
			return 0;

		int left = BlackHeight(x.left);
		int right = BlackHeight(x.right);

		if(left == -1 || right == -1)
			return -1;
		else{
			if(left == right)
				bo = true;
			else
				bo = false;
		}
		if(bo == true && colorOf(x)==BLACK)
			return left+1;
		else if(bo == true && colorOf(x)==RED)
			return left;
		else
			return -1;
	}
	public Node root;
	private static final int BLACK = 0;
	private static final int RED = 1;
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
	 *  - int colorOf(Node x)
	 *  - Node parentOf(Node x)
	 *  - void setColor(Node x, int c)
	 *	- Node leftOf(Node x)
	 *  - Node rightOf(Node x)
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
	private static int colorOf(Node x) {
		return (x==null?BLACK:x.color);
	}
	private static Node parentOf(Node x) {
		return (x==null?null:x.parent);
	}
	private static void setColor(Node x, int c) {
		if(x!=null)
			x.color=c;
	}
	private static Node leftOf(Node x) {
		return (x==null?null:x.left);
	}
	private static Node rightOf(Node x) {
		return (x==null?null:x.right);
	}
	private void rbInsertFixup(Node z) {
		while(z.parent!=null && z.parent.color==RED) {	// z�� �θ� RED�� ���(RED-RED violation). �Ҿƹ����� �ݵ�� BLACK
			if(z.parent == z.parent.parent.left) {		// z�� �θ� �Ҿƹ����� ���� �ڽ��� ���
				Node y = z.parent.parent.right;			// y�� z�� ����
				if(y!=null && y.color==RED) {			// Case1: ������ RED (������ null�̸� �ȵȴ�)
					//System.out.println("insert case1");
					z.parent.color = BLACK;				// z�� �θ� BLACK����
					y.color = BLACK;					// z�� ���̵� BLACK����
					z.parent.parent.color = RED;		// z�� �Ҿƹ����� RED��
					z = z.parent.parent;				// �ٽ� z�� �Ҿƹ����� ���Ͽ� �˻� �ݺ�
				}else {	// ���2~3�� ������ BLACK�� ���
					if(z == z.parent.right) {			// Case2: z�� ������ �ڽ�
						//System.out.println("insert case2");
						z = z.parent;
						leftRotate(z);					// �θ� ���Ͽ� leftRotate ���� (������ ����� �θ�� �׻� �����ϹǷ� null���θ� üũ ������ �ʾƵ� ��)
					}	// ���2�� �׻� ���3���� ����			// Case3: z�� ���� �ڽ�
					//System.out.println("insert case3");
					z.parent.color = BLACK;				// �θ� BLACK����
					// ������ ����� �Ҿƹ��� ���� null�̸� �ȵ�(��, ������ ��尡 root�� �ڽ����� �߰��ȴ� ���. �׷��� �� ���� ���̽�3���� ����� �� ���� ���� üũ ������ �ʾƵ� ��)
					z.parent.parent.color = RED;	// �Ҿƹ����� RED��
					rightRotate(z.parent.parent);	// �Ҿƹ����� ���Ͽ� rightRotate ����
				}
			}else {										// z�� �θ� �Ҿƹ����� ������ �ڽ��� ���
				Node y = z.parent.parent.left;			// y�� z�� ����
				if(y!=null && y.color==RED) {			// Case 4
					//System.out.println("insert case4");
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}else {
					if(z == z.parent.left) {			// Case 5
						//System.out.println("insert case5");
						z = z.parent;
						rightRotate(z);					// �θ� ���Ͽ� rightRotate ����
					}
					//System.out.println("insert case6");
					z.parent.color = BLACK;				// Case 6
					z.parent.parent.color = RED;
					leftRotate(z.parent.parent);	// �Ҿƹ����� ���Ͽ� leftRotate ����
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
					//System.out.println("case1"); 
					setColor(w,BLACK);								// ������ BLACK����
					setColor(p_of_x,RED);						// �θ� RED��
					leftRotate(p_of_x);							// �θ� �������� leftRotate
					w = rightOf(p_of_x);								// leftRotate�� �ٲ� ������ ����. �� ���� ������ ���� �ڽ�(BLACK)�� ���ο� ������ ��.
				}	// x�� �� ���� �Ʒ��� ������ ���·� ���2~3�� ����				// ���2~4�� ������ BLACK�� ���, x�� �θ�� Unknown.
				if(colorOf(leftOf(w))==BLACK&&colorOf(rightOf(w))==BLACK) {	// ���2: ������ �ڽĵ鵵 BLACK�� ���
					//System.out.println("case2");
					setColor(w,RED);								// ���� ��带 RED��
					x = p_of_x;								// x�� black �ϳ��� �Ѱ� ����. �� �θ� ��忡 ���� double black ���� �˻�.
					p_of_x = parentOf(x);					// ���� �Ѱ��� p_of_x�� ���� ����� �� �κп����� loop�� ������ �� �ִ�
				}else {
					if(colorOf(rightOf(w)) == BLACK) {					// ���3: ������ �����ڽ��� RED�� ���
						//System.out.println("case3");
						setColor(leftOf(w),BLACK);						// w�� ���� �ڽ��� BLACK����
						setColor(w,RED);								// w�� RED��
						rightRotate(w);								// w�� ���ؼ� rightRotate
						w = rightOf(p_of_x);							// x�� ���ο� ���� w����. ���ο� w�� ������ �ڽ��� �׻� RED�̴�
					}	// ���4�� ����									// ���4: ������ �����ڽ��� Unknown, �������ڽ��� RED�� ���
					//System.out.println("case4");
					setColor(w,colorOf(p_of_x));						// w�� ���� x�� �θ��� ������
					setColor(p_of_x,BLACK);							// x�� �θ� BLACK����
					setColor(rightOf(w),BLACK);							// w�� ������ �ڽ��� BLACK����
					leftRotate(p_of_x);							// x�� �θ� ���ؼ� leftRotate
					x = root;										// x�� root�� �Ǹ� while���� Ż���ϰ� ��	
				}
			}else {													// ��� 5~8: x�� �θ��� ������ �ڽ�
				Node w = leftOf(p_of_x);		// w�� z�� ����
				if(colorOf(w) == RED) {								// ���5: ������ RED�� ���
					//System.out.println("case5");
					setColor(w,BLACK);
					setColor(p_of_x,RED);
					rightRotate(p_of_x);
					w = leftOf(p_of_x);
				}
				if(colorOf(leftOf(w))==BLACK&&colorOf(rightOf(w))==BLACK) {	// ���6
					//System.out.println("case6");
					setColor(w,RED);
					x = p_of_x;
					p_of_x = parentOf(x);
				}else {
					if(colorOf(leftOf(w)) == BLACK) {						// ���7
						setColor(rightOf(w),BLACK);
						//System.out.println("case7");
						setColor(w,RED);
						leftRotate(w);
						w = leftOf(p_of_x);
					}	// ���8�� ����
					//System.out.println("case8");
					setColor(w,colorOf(p_of_x));						// ���8
					setColor(p_of_x, BLACK);
					setColor(leftOf(w),BLACK);
					rightRotate(p_of_x);
					x = root;
				}
			}
		}
		setColor(x,BLACK);
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
		Node x,y;
		if(z.left== null || z.right==null) {
			y = z;
		}else y = successor(z);			// ������ ����� ����, ������ �ڽ��� ���� �ϹǷ� z�� ���� Ʈ���� MAX�� �� ����.
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
		if(x==null) {
			//System.out.println("IT SHOULD BE ERROR!");
		}
		if(y.color == BLACK)				// ������ ��尡 BLACK�ΰ�� ������ �߻�
			rbDeleteFixup(x,parentOf(y));	// ���� �����Ǵ� ����� �θ� �Բ� �Ѱ� �ش�.
		return y;	// ������ ���
	}	// O(logN)
	public void inorderTraversal(Node x) {
		if(x!=null){
			inorderTraversal(x.left);
			System.out.print(x.key+", ");
			inorderTraversal(x.right);	
		}
	}
	public void levelorderTraversal(){
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