import java.util.LinkedList;
import java.util.Queue;

/*
 * �Ʒ� �׸��� ���� 9���� ��带 ��������Ʈ�������� �� inorder, preorder, postorder, �׸��� level-order��
 * ��ȸ�ϸ鼭 �� ��忡 ����� �����͸� ����ϴ� ���α׷��� �ۼ��϶�.
 */
public class Exercise08_1 {
	public static void main(String[] args) {
		String[] samples = {"fox", "bear", "goose", "ant", "dog", "hippo", "cat", "eagle", "iguana"};
		BinaryTree binaryTree_test = new BinaryTree();
		binaryTree_test.makeSampleTree(samples);
		
		System.out.print("inorder traversal: ");
		binaryTree_test.inorderTraversal(binaryTree_test.root);
		
		System.out.print("\npreorder traversal: ");
		binaryTree_test.preorderTraversal(binaryTree_test.root);
		
		System.out.print("\npostorder traversal: ");
		binaryTree_test.postorderTraversal(binaryTree_test.root);
		
		System.out.print("\nlevel-order traversal: ");
		binaryTree_test.levelorderTraversal(binaryTree_test.root);
	}
}

class BinaryTree {
	public Node root;
	public BinaryTree() {
		root = null;
	}
	private class Node {
		String data;
		Node left,right;
		Node() {
			data = ""; left=null;right=null;
		}
	}
	public Node append_branch(Node root, String data) {
		Node Cur = new Node();
		Cur.data = String.copyValueOf(data.toCharArray());
		//System.out.println(Cur.data);
		
		if(root==null) {
			root = Cur;
		}else {
			Node P = root; int sw = 1;
			while(sw==1) {
				if(P.data.compareTo(Cur.data)>=0){	// ���� ���� �߰��� ����� ������ ��
					if(P.left==null) {				// ���� �ڽ��� �������
						P.left = Cur; sw=0;			// ���� �ڽĿ� �߰��� ��带 �߰�
					}else {
						P = P.left;					// ���� �ڽ� ���� ���� �˻�
					}
				}else {
					if(P.right==null){				// ������ �ڽ��� �������
						P.right = Cur; sw=0;		// ������ �ڽĿ� �߰��� ��带 �߰�
					}else {
						P = P.right;				// ������ �ڽ� ���� ���� �˻�
					}
				}
			}
		}
		return root;
	}
	public void makeSampleTree(String[] samples) {
		System.out.print("Sample data: ");
		for(int i=0;i<samples.length;i++) {
			root = append_branch(root, samples[i]);
			System.out.print(samples[i]+", ");
		}
		System.out.println();
	}
	public void inorderTraversal(Node x) {
		if(x!=null){
			inorderTraversal(x.left);
			System.out.print(x.data+", ");
			inorderTraversal(x.right);	
		}
	}
	public void preorderTraversal(Node x) {
		if(x!=null){
			System.out.print(x.data+", ");
			preorderTraversal(x.left);
			preorderTraversal(x.right);	
		}
	}
	public void postorderTraversal(Node x) {
		if(x!=null){
			postorderTraversal(x.left);
			postorderTraversal(x.right);
			System.out.print(x.data+", ");	
		}
	}
	public void levelorderTraversal(Node root) {
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			System.out.print(node.data+", ");
			if(node.left!=null) queue.offer(node.left);
			if(node.right!=null) queue.offer(node.right);
		}
	}
}