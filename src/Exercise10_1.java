import java.util.Random;
/*
 * 레드블랙트리에 대해서 insert와 delete 함수를 구현하라.
 */
public class Exercise10_1 {
	public static void main(String[] args) {		
		int N = 10000;						// 추가 삭제할 샘플 수
		Random rd = new Random();
		RedBlackTree rbt = new RedBlackTree();

		long start = System.currentTimeMillis();
		// (1) 먼저 이미 이진검색트리에 있는지 검사한다.
		for(int i=0;i<N;i++) {
			int sample = rd.nextInt(N);
			//System.out.print("now check the key is "+sample);
			if(rbt.search(rbt.root, sample)==null) {	// 이진검색트리에 없으면,
				//System.out.print(" insert!\n");
				rbt.insert(sample);						// (3) 없으면 그 값을 트리에 insert한다.
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
			}	// (2) 만약 있으면 그 값을 트리로 부터 삭제한다.
			// (4) 마지막으로 트리를 inorder traverse 하면서 방문된 순서대로 정수들을 출력한다.
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
	private void leftRotate(Node x) {	// y != null이라고 가정
		Node y = x.right;
		x.right = y.left;
		if(y.left!=null)		// 베타가 null이 아니면
			y.left.parent = x;
		// 여기까지 자식 관계 정리
		// 부모 관계 정리 시작
		y.parent = x.parent;	// x의 부모를 y의 부모로 바꿈
		if(x.parent == null) {
			root = y;
		}else if(x==x.parent.left) {
			x.parent.left = y;
		}else x.parent.right = y;
		// x와 y 관계 정리 시작
		y.left = x;
		x.parent = y;
	} // O(1)
	private void rightRotate(Node y) {
		Node x = y.left;
		y.left = x.right;
		if(x.right!=null)			// 베타가 null이 아니면
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
		while(z.parent!=null && z.parent.color==RED) {	// z의 부모가 RED인 경우(RED-RED violation). 할아버지는 반드시 BLACK
			if(z.parent == z.parent.parent.left) {		// z의 부모가 할아버지의 왼쪽 자식인 경우
				Node y = z.parent.parent.right;			// y는 z의 삼촌
				if(y!=null && y.color==RED) {			// Case1: 삼촌이 RED (삼촌이 null이면 안된다)
					z.parent.color = BLACK;				// z의 부모를 BLACK으로
					y.color = BLACK;					// z의 삼촌도 BLACK으로
					z.parent.parent.color = RED;		// z의 할아버지를 RED로
					z = z.parent.parent;				// 다시 z의 할아버지에 대하여 경우1 검사 반복
				}else {	// 경우2~3은 삼촌이 BLACK인 경우
					if(z == z.parent.right) {			// Case2: z가 오른쪽 자식
						z = z.parent;
						leftRotate(z);					// 부모에 대하여 leftRotate 진행 (삽입할 노드의 부모는 항상 존재하므로 null여부를 체크 해주지 않아도 됨)
					}	// 경우2는 항상 경우3으로 진행			// Case3: z가 왼쪽 자식
					z.parent.color = BLACK;				// 부모를 BLACK으로
					// 삽입할 노드의 할아버지 노드는 null이면 안됨(즉, 삽입할 노드가 root의 자식으로 추가된는 경우. 그러나 이 경우는 케이스3으로 진행될 수 없어 따로 체크 해주지 않아도 됨)
					z.parent.parent.color = RED;	// 할아버지를 RED로
					rightRotate(z.parent.parent);	// 할아버지에 대하여 rightRotate 진행
				}
			}else {										// z의 부모가 할아버지의 오른쪽 자식인 경우
				Node y = z.parent.parent.left;			// y는 z의 삼촌
				if(y!=null && y.color==RED) {			// Case 4
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}else {
					if(z == z.parent.left) {			// Case 5
						z = z.parent;
						rightRotate(z);					// 부모에 대하여 rightRotate 진행
					}
					z.parent.color = BLACK;				// Case 6
					z.parent.parent.color = RED;
					leftRotate(z.parent.parent);	// 할아버지에 대하여 leftRotate 진행
				}
			}
		}
		root.color = BLACK;
	}	// O(logN)
	private void rbDeleteFixup(Node x) {	// x는 삭제한 노드를 대신하여 삭제한 노드 자리에 들어가는 노드이다(double black이며 NIL일 수도 있다)
		while(x!=null && x!=root && x.color==BLACK) { 
			if(x==x.parent.left) {									// 경우 1~4: x가 부모의 왼쪽 자식. x가 double black인 경우
				Node w = x.parent.right;	// w는 z의 형제(형제가 반드시 존재. 레드블랙 트리에서는 w가 NIL이 아니다. 자식 노드도 NIL이 아니다.)
				if(w!=null) {
					if(w.color == RED) {								// 경우1: 형제가 RED인 경우
						System.out.println("case1");
						w.color = BLACK;								// 형제를 BLACK으로
						x.parent.color = RED;							// 부모를 RED로
						leftRotate(x.parent);							// 부모를 기준으로 leftRotate
						w = x.parent.right;								// leftRotate로 바뀐 형제를 갱신. 즉 원래 형제의 왼쪽 자식(BLACK)이 새로운 형제가 됨.
					}	// x가 한 레벨 아래로 내려간 상태로 경우2~3로 진행				// 경우2~4는 형제가 BLACK인 경우, x의 부모는 Unknown.
					if(w!=null&&((w.left==null&&w.right==null)||
							(w.left==null&&w.right!=null&&w.right.color==BLACK)||
							(w.left!=null&&w.left.color==BLACK&&w.right==null)||
							(w.left!=null&&w.left.color==BLACK&&w.right!=null&&w.right.color==BLACK))) {	// 경우2: 형제의 자식들도 BLACK인 경우
						System.out.println("case2");
						w.color = RED;								// 형제 노드를 RED로
						x = x.parent;								// x의 black 하나를 넘겨 받음. 즉 부모 노드에 대해 double black 인지 검사.
					}else {
						if(w!=null&&(w.right==null||(w.right!=null&&w.right.color == BLACK))) {					// 경우3: 형제의 왼쪽자식이 RED인 경우
							System.out.println("case3");
							if(w.left!=null)w.left.color = BLACK;						// w의 왼쪽 자식을 BLACK으로
							w.color = RED;								// w를 RED로
							rightRotate(w);								// w에 대해서 rightRotate
							w = x.parent.right;							// x의 새로운 형제 w갱신. 새로운 w의 오른쪽 자식은 항상 RED이다
						}	// 경우4로 진행									// 경우4: 형제의 왼족자식은 Unknown, 오른쪽자식이 RED인 경우
						if(w!=null) {
							System.out.println("case4");
							w.color = x.parent.color;						// w의 색을 x의 부모의 색으로
							x.parent.color = BLACK;							// x의 부모를 BLACK으로
							w.right.color = BLACK;							// w의 오른쪽 자식을 BLACK으로
							leftRotate(x.parent);							// x의 부모에 대해서 leftRotate
							x = root;										// x가 root가 되면 while문을 탈출하게 됨
						}
					}
				}
			}else {													// 경우 5~8: x가 부모의 오른쪽 자식
				Node w = x.parent.left;		// w는 z의 형제
				if(w!=null) {
					if(w.color == RED) {								// 경우5: 형제가 RED인 경우
						System.out.println("case5");
						w.color = BLACK;
						x.parent.color = RED;
						rightRotate(x.parent);
						w = x.parent.left;
					}
					if(w!=null&&((w.left==null&&w.right==null)||
							(w.left==null&&w.right!=null&&w.right.color==BLACK)||
							(w.left!=null&&w.left.color==BLACK&&w.right==null)||
							(w.left!=null&&w.left.color==BLACK&&w.right!=null&&w.right.color==BLACK))) {	// 경우6
						System.out.println("case6");
						w.color = RED;
						x = x.parent;
					}else {
						if(w!=null&&(w.left==null||(w.left!=null&&w.left.color == BLACK))) {						// 경우7
							if(w.right!=null)w.right.color = BLACK;
							System.out.println("case7");
							w.color = RED;
							leftRotate(w);
							w = x.parent.left;
						}	// 경우8로 진행
						if(w!=null){
							System.out.println("case8");
							w.color = x.parent.color;						// 경우8
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
		Node y = null;		// 추가할 노드의 parent 위치를 추적
		Node x = root;
		while(x!=null) {
			y = x;			// 이전 반복문에서의 현재 노드가 이번 반복문에서의 부모 노드
			if(z.key < x.key)
				x = x.left;	// 키가 작으면 왼쪽
			else x = x.right;	// 크면 오른쪽
		}	// 추가할 노드의 위치 찾기 끝
		z.parent = y;	// 추가할 노드의 부모 지정
		if(y==null)
			root = z;	// Tree T was empty
		else if(z.key < y.key)
			y.left = z;
		else y.right = z;
		// z노드의 자식을 NIL로 설정하는 것과 RED 컬러링 하는 것은 Node의 생성자에서 담당
		rbInsertFixup(z);
	}	// O(logN)
	public Node delete(Node z) {				// z가 삭제할 노드
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
		if(y.color == BLACK)	// 삭제한 노드가 BLACK인경우 문제가 발생
			rbDeleteFixup(x);
		return y;	// 삭제된 노드
	}	// O(logN)
	public void inorderTraversal(Node x) {
		if(x!=null){
			inorderTraversal(x.left);
			System.out.print(x.key+", ");
			inorderTraversal(x.right);	
		}
	}
}