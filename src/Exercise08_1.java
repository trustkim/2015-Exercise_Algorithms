import java.util.LinkedList;
import java.util.Queue;

/*
 * 아래 그림과 같이 9개의 노드를 가진이진트리를만든 후 inorder, preorder, postorder, 그리고 level-order로
 * 순회하면서 각 노드에 저장된 데이터를 출력하는 프로그램을 작성하라.
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
				if(P.data.compareTo(Cur.data)>=0){	// 현재 노드와 추가할 노드의 순서를 비교
					if(P.left==null) {				// 왼쪽 자식이 비었으면
						P.left = Cur; sw=0;			// 왼쪽 자식에 추가할 노드를 추가
					}else {
						P = P.left;					// 왼쪽 자식 노드로 가서 검사
					}
				}else {
					if(P.right==null){				// 오른쪽 자식이 비었으면
						P.right = Cur; sw=0;		// 오른쪽 자식에 추가할 노드를 추가
					}else {
						P = P.right;				// 오른쪽 자식 노드로 가서 검사
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