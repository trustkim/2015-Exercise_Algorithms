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
		int x,y;					// x,y��ǥ ������
		Node left,right,parent;
		Node(int k){key=k; y=1; left=right=parent=null;}	// y�� 1���� �����ϴ� ������ ����
	}
	
	private Node root;				// Ʈ���� ��Ʈ ���
	private int size;			// Ʈ���� ��ü ��� ����
	private int[][] nodeTable;		// �Է� ���Ͽ� ����� ��带 ������ �迭. [0]: üũ���� �÷���, [1]: �θ�, [2]: ���� Ű��, [3]: ��/���� �ڽ� �÷���
	private int[][] posTable;		// �׷��� ó���� �Ѱ��� �迭. [0]: Ű��, [1]: x��ǥ, [2]: y��ǥ, [3]: �θ��� x��ǥ, [4]: �θ��� y��ǥ
	private int travCnt;		// x,y ��ǥ�� ���� ī����
	public BinaryTree(int[][] nodes) {			// nodeTable�� �о� Ʈ���� �ٷ� ����.
		size = nodes.length;				// Ʈ���� ��� ���� ����.
		nodeTable = nodes;
		posTable = new int[size][5];
		Queue<Node> queue = new LinkedList<Node>();
		root = new Node(ROOT);						// root�� �׻� 1
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
		inorderTraversal(root);// inorderTraversal�� x��ǥ ����
		levelorderTraversal(root);// posTable�� levelorderTraversal�� ������
	}
	private int[][] findChilds(int key) {	// key�� �θ�� �ϴ� ����� ���ڵ� ���� ��ȯ
		int[][] childs = new int[2][4];
		int foundCnt = 0;					// 0,1,2�� �ڽ��� ã�� �� ����
		for(int i=0;i<size;i++) {
			if(nodeTable[i][CHECKED_FLAG]==0 && key == nodeTable[i][1]) {
				if(nodeTable[i][3]==0) {	// ���� �ڽ� ã��
					childs[LEFT_CHILD] = nodeTable[i]; childs[LEFT_CHILD][NULL_FLAG] = 1; // null flag ��ȯ
					nodeTable[i][CHECKED_FLAG] = 1;	// check
					foundCnt++;
				}else {						// ������ �ڽ� ã��
					childs[RIGHT_CHILD] = nodeTable[i]; childs[RIGHT_CHILD][NULL_FLAG] = 1; // null flag ��ȯ
					nodeTable[i][CHECKED_FLAG] = 1;	// check
					foundCnt++;
				}
			}
			if(foundCnt==2) return childs;
		}
		return childs; 
	}
	private Node insert(Node parent, int key, int flag) {
		Node z = new Node(key);		// ���� �߰��� ���
		if(flag==0) {
			parent.left = z;		// �θ��� ���� �ڽ����� �߰�
		}else {
			parent.right = z;		// �θ��� ������ �ڽ����� �߰�
		}
		z.parent = parent;			// ���� �߰��� ����� �θ� ����
		z.y=parent.y+1;				// y��ǥ ����
		return z;
	}
	private void inorderTraversal(Node x) {
		if(x!=null){
			inorderTraversal(x.left);
			x.x = travCnt++;						// inorderTraversal ������ x��ǥ�� ����
			inorderTraversal(x.right);	
		}
	}
	private int table_index = 0;
	private void levelorderTraversal(Node root) {	// posTable�� ����
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			posTable[table_index][0] = node.key;
			posTable[table_index][1] = node.x;
			posTable[table_index][2] = node.y;
			posTable[table_index][3] = node.key==ROOT?-1:node.parent.x;		// ��Ʈ�� �θ� x��ǥ�� -1
			posTable[table_index++][4] = node.key==ROOT?-1:node.parent.y;	// ��Ʈ�� �θ� y��ǥ�� -1. �׸��� ĵ���� ��ǥ��� 1,1���� �׷�����. ���� ������ �ֵ� ��� ����.
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