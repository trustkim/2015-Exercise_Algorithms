package assignment1;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
	private static final int ROOT = 1;
	private static final int LEFT_CHILD = 0;
	private static final int RIGHT_CHILD = 1;
	private static final int NULL_FLAG = 0;
	private static final int CHECKED_FLAG = 0;
	
	private class Node {
		int key;
		int x,y;					// x,y좌표 넣을거
		Node left,right,parent;
		Node(int k){key=k; y=1; left=right=parent=null;}	// y는 1부터 시작하는 것으로 가정
	}
	
	private Node root;				// 트리의 루트 노드
	private int size;			// 트리의 전체 노드 개수
	private int[][] nodeTable;		// 입력 파일에 기술된 노드를 저장할 배열. [0]: 체크여부 플래그, [1]: 부모, [2]: 나의 키값, [3]: 왼/오른 자식 플래그
	private int[][] posTable;		// 그래픽 처리에 넘겨줄 배열. [0]: 키값, [1]: x좌표, [2]: y좌표, [3]: 부모의 x좌표, [4]: 부모이 y좌표
	private int travCnt;		// x,y 좌표를 위한 카운터
	public BinaryTree(int[][] nodes) {			// nodeTable을 읽어 트리를 바로 생성.
		size = nodes.length;				// 트리의 노드 개수 지정.
		nodeTable = nodes;
		posTable = new int[size][5];
		Queue<Node> queue = new LinkedList<Node>();
		root = new Node(ROOT);						// root는 항상 1
		queue.offer(root);
		while(!queue.isEmpty()) {
			Node cur = queue.poll();
			int[][] childs = findChilds(cur.key);
			if(childs[LEFT_CHILD][NULL_FLAG]==1) {
				queue.offer(insert(cur,childs[LEFT_CHILD][2],childs[LEFT_CHILD][3]));
			}
			if(childs[RIGHT_CHILD][NULL_FLAG]==1) {
				queue.offer(insert(cur,childs[RIGHT_CHILD][2],childs[RIGHT_CHILD][3]));
			}
		}
		travCnt=1;
		inorderTraversal(root);// inorderTraversal로 x좌표 구함
		levelorderTraversal(root);// posTable을 levelorderTraversal로 생성함
	}
	private int[][] findChilds(int key) {	// key를 부모로 하는 노드의 레코드 셋을 반환
		int[][] childs = new int[2][4];
		int foundCnt = 0;					// 0,1,2개 자식을 찾을 수 있음
		for(int i=0;i<size;i++) {
			if(nodeTable[i][CHECKED_FLAG]==0 && key == nodeTable[i][1]) {
				if(nodeTable[i][3]==0) {	// 왼쪽 자식 찾음
					childs[LEFT_CHILD] = nodeTable[i]; childs[LEFT_CHILD][NULL_FLAG] = 1; // null flag 전환
					nodeTable[i][CHECKED_FLAG] = 1;	// check
					foundCnt++;
				}else {						// 오른쪽 자식 찾음
					childs[RIGHT_CHILD] = nodeTable[i]; childs[RIGHT_CHILD][NULL_FLAG] = 1; // null flag 전환
					nodeTable[i][CHECKED_FLAG] = 1;	// check
					foundCnt++;
				}
			}
			if(foundCnt==2) return childs;
		}
		return childs; 
	}
	private Node insert(Node parent, int key, int flag) {
		Node z = new Node(key);		// 새로 추가할 노드
		if(flag==0) {
			parent.left = z;		// 부모의 왼쪽 자식으로 추가
		}else {
			parent.right = z;		// 부모의 오른쪽 자식으로 추가
		}
		z.parent = parent;			// 새로 추가할 노드의 부모를 지정
		z.y=parent.y+1;				// y좌표 지정
		return z;
	}
	private void inorderTraversal(Node x) {
		if(x!=null){
			inorderTraversal(x.left);
			x.x = travCnt++;						// inorderTraversal 순으로 x좌표를 지정
			inorderTraversal(x.right);	
		}
	}
	private int table_index = 0;
	private void levelorderTraversal(Node root) {	// posTable을 생성
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			posTable[table_index][0] = node.key;
			posTable[table_index][1] = node.x;
			posTable[table_index][2] = node.y;
			posTable[table_index][3] = node.key==ROOT?-1:node.parent.x;		// 루트의 부모 x좌표에 -1
			posTable[table_index++][4] = node.key==ROOT?-1:node.parent.y;	// 루트의 부모 y좌표는 -1. 그리는 캔버스 좌표계는 1,1부터 그려진다. 따라서 음수로 둬도 상관 없음.
			if(node.left!=null) {
				queue.offer(node.left);
			}
			if(node.right!=null) {
				queue.offer(node.right);
			}
		}
	}
	public int[][] getPosTable(int[][] res) {
		return posTable;
	}
	public int getSize() {
		return size;
	}
}