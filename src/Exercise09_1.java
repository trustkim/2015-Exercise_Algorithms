import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/*
 * �����˻�Ʈ���� ���ؼ� search, insert, delete, maximum, minimum, successor, predecessor �Լ���
 * �����϶�. Ʈ���� �� ��尡 ���� �ڽ� ���, ������ �ڽ�, �׸��� �θ� ����� �ּҸ� �����ϵ��� �����϶�.
 * Ʈ���� ����Ǵ� �����ʹ� �ϳ��� �����̴�. ���� ���� �ð��� ������ 4���� Ʈ�� ��ȸ �˰���鵵 ��� �����϶�.
 * delete �Լ��� delete�� �����͸� �ָ� �� �����͸� ������ ��带 ã�Ƽ� delete�ϵ��� �����϶�.
 * N���� ������ �������� �����Ͽ� ������ ������� (1) ���� �̹� �����˻�Ʈ���� �ִ��� �˻��Ѵ�.
 * (2) ���� ������ �� ���� Ʈ���� ���� �����Ѵ�. (3) ������ �� ���� Ʈ���� insert�Ѵ�.
 * (4) ���������� Ʈ���� inorder traverse �ϸ鼭 �湮�� ������� �������� ����Ѵ�.
 */
public class Exercise09_1 {
	public static void main(String[] args) {		
		int N = 15;						// �߰� ������ ���� ��
		Random rd = new Random();
		BinarySearchTree bst = new BinarySearchTree();
		
		// (1) ���� �̹� �����˻�Ʈ���� �ִ��� �˻��Ѵ�.
		for(int i=0;i<N;i++) {
			int sample = rd.nextInt(N);
			System.out.print("now check the key is "+sample);
			if(bst.search(bst.root, sample)==null) {	// �����˻�Ʈ���� ������,
				bst.insert(sample);						// (3) ������ �� ���� Ʈ���� insert�Ѵ�.
				System.out.print(" insert!\n");
			}else {
				bst.delete(bst, bst.search(bst.root, sample));
				System.out.print(" delete!\n");
			}	// (2) ���� ������ �� ���� Ʈ���� ���� �����Ѵ�.
			bst.inorderTraversal(bst.root);
			System.out.println();
			// (4) ���������� Ʈ���� inorder traverse �ϸ鼭 �湮�� ������� �������� ����Ѵ�.
		}
	}
}

class BinarySearchTree {
	private class Node {
		int key;
		String data;
		Node left,right,parent;
		Node(int key) { this.key = key; this.data=""; left=right=parent=null; }	
	}
	public Node root;
	public BinarySearchTree() {
		root = null;
	}
	public Node search(Node x, int k) {
		if(x==null || k==x.key)
			return x;
		if(k<x.key)
			return search(x.left,k);
		else return search(x.right, k);
	}
	public Node maximum(Node x) {
		while(x.right != null)
			x = x.right;
		return x;
	}
	public Node minimum(Node x) {
		while(x.left != null)
			x=x.left;
		return x;
	}
	public Node successor(Node x) {
		if(x.key==maximum(root).key) {
			System.out.println("key("+x.key+") is MAX in the Tree!");
		}	// alert NullPointerException
		if(x.right!=null)
			return minimum(x.right);
		Node y = x.parent;
		while(y!=null && x==y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}
	public Node predecessor(Node x) {
		if(x.key==minimum(root).key) {
			System.out.println("key("+x.key+") is MIN in the Tree!");
		}	// alert NullPointerException
		if(x.left!=null)
			return maximum(x.left);
		Node y = x.parent;
		while(y!=null && x==y.left) {
			x = y;
			y = y.parent;
		}
		return y;
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
	}
	public Node delete(BinarySearchTree T, Node z) {
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
			T.root = x;
		}else if(y==y.parent.left) {
			y.parent.left = x;
		}else y.parent.right = x;
		if(y!=z) {
			z.key = y.key;
			z.data = String.copyValueOf(y.data.toCharArray());
		}
		return y;	// ������ ��Ʈ
	}
	public void inorderTraversal(Node x) {
		if(x!=null){
			inorderTraversal(x.left);
			System.out.print(x.key+", ");
			inorderTraversal(x.right);	
		}
	}
	public void preorderTraversal(Node x) {
		if(x!=null){
			System.out.print(x.key+", ");
			preorderTraversal(x.left);
			preorderTraversal(x.right);	
		}
	}
	public void postorderTraversal(Node x) {
		if(x!=null){
			postorderTraversal(x.left);
			postorderTraversal(x.right);
			System.out.print(x.key+", ");	
		}
	}
	public void levelorderTraversal(Node root) {
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			System.out.print(node.key+", ");
			if(node.left!=null) queue.offer(node.left);
			if(node.right!=null) queue.offer(node.right);
		}
	}
}