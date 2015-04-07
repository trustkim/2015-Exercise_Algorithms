import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/*
 * 이진검색트리에 대해서 search, insert, delete, maximum, minimum, successor, predecessor 함수를
 * 구현하라. 트리의 각 노드가 왼쪽 자식 노드, 오른쪽 자식, 그리고 부모 노드의 주소를 저장하도록 구현하라.
 * 트리에 저장되는 데이터는 하나의 정수이다. 또한 지난 시간에 구현한 4가지 트리 순회 알고리즘들도 모두 포함하라.
 * delete 함수는 delete할 데이터를 주면 그 데이터를 저장한 노드를 찾아서 delete하도록 구현하라.
 * N개의 정수를 랜덤으로 생성하여 생성한 순서대로 (1) 먼저 이미 이진검색트리에 있는지 검사한다.
 * (2) 만약 있으면 그 값을 트리로 부터 삭제한다. (3) 없으면 그 값을 트리에 insert한다.
 * (4) 마지막으로 트리를 inorder traverse 하면서 방문된 순서대로 정수들을 출력한다.
 */
public class Exercise09_1 {
	public static void main(String[] args) {		
		int N = 10;						// 추가 삭제할 샘플 수
		//int[] sample = new int[N];			// 추가 삭제할 샘플 배열
		
		int[] initKey = {15,6,18,3,7,17,20,2,4};	// 초기 트리에 넣을 키 값, initData는 초기 데이터
		String[] initData = {"fox", "bear", "goose", "ant", "dog", "hippo", "cat", "eagle", "iguana"};
		
		Random rd = new Random();
		//Scanner sc = new Scanner(System.in);
		BinarySearchTree bst = new BinarySearchTree();	// BSF
		bst = makeSampleTree(initKey, initData);		// 초기 키, 데이터로 초기화
		bst.inorderTraversal(bst.root);
		System.out.println();
		
		// (1) 먼저 이미 이진검색트리에 있는지 검사한다.
		for(int i=0;i<N;i++) {
			int sample = rd.nextInt(N);
			System.out.println("now check the key is "+sample);
			if(bst.search(bst.root, sample)==null) {	// 이진검색트리에 없으면,
				bst.insert(bst, new Node(sample));						// (3) 없으면 그 값을 트리에 insert한다.
				System.out.println("insert!");
			}else {
				bst.delete(bst, bst.search(bst.root, sample));
				System.out.println("delete!");
			}	// (2) 만약 있으면 그 값을 트리로 부터 삭제한다.
			bst.inorderTraversal(bst.root);
			System.out.println();
			// (4) 마지막으로 트리를 inorder traverse 하면서 방문된 순서대로 정수들을 출력한다.
		}
		
		
		
		//sc.close();
	}
	private static BinarySearchTree makeSampleTree(int[] samples, String[] data_samples) {
		BinarySearchTree T = new BinarySearchTree();
		System.out.print("Sample key: ");
		for(int i=0;i<samples.length;i++) {
			Node temp = new Node();
			temp.key = samples[i];
			temp.data = String.copyValueOf(data_samples[i].toCharArray());
			T.insert(T,temp);
			System.out.print(samples[i]+", ");
		}
		System.out.println("\nSample data: ");
		for(int i=0;i<samples.length;i++)
			System.out.print(data_samples[i]+", ");
		System.out.println();
		return T;
	}
}

class Node {
	int key;
	String data;
	Node left,right,parent;
	Node() {
		data = ""; left=null;right=null;parent=null;
	}
	Node(int key, String data) { this.key=key; this.data=String.copyValueOf(data.toCharArray()); left=right=parent=null; }
	Node(int key) { this.key = key; this.data=""; left=right=parent=null; }
	
}

class BinarySearchTree {
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
	public void insert(BinarySearchTree T, Node z) {
		Node y = null;		// 추가할 노드의 parent 위치를 추적
		Node x = T.root;
		while(x!=null) {
			y = x;			// 이전 반복문에서의 현재 노드가 이번 반복문에서의 부모 노드
			if(z.key < x.key)
				x = x.left;	// 키가 작으면 왼쪽
			else x = x.right;	// 크면 오른쪽
		}	// 추가할 노드의 위치 찾기 끝
		z.parent = y;	// 추가할 노드의 부모 지정
		if(y==null)
			T.root = z;	// Tree T was empty
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
		return y;	// 삭제된 노트
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