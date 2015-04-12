import java.util.Random;

/*
 * �����Ʈ���� ���ؼ� insert�� delete �Լ��� �����϶�.
 */
public class Exercise10_1 {
	public static void main(String[] args) {		
		int N = 15;						// �߰� ������ ���� ��
		Random rd = new Random();
		RedBlackTree rbt = new RedBlackTree();
		
		// (1) ���� �̹� �����˻�Ʈ���� �ִ��� �˻��Ѵ�.
		for(int i=0;i<N;i++) {
			int sample = rd.nextInt(N);
			System.out.print("now check the key is "+sample);
			if(rbt.search(rbt.root, sample)==null) {	// �����˻�Ʈ���� ������,
				rbt.insert(sample);						// (3) ������ �� ���� Ʈ���� insert�Ѵ�.
				System.out.print(" insert!\n");
			}else {
				rbt.delete(rbt, rbt.search(rbt.root, sample));
				System.out.print(" delete!\n");
			}	// (2) ���� ������ �� ���� Ʈ���� ���� �����Ѵ�.
			rbt.inorderTraversal(rbt.root);
			System.out.println();
			// (4) ���������� Ʈ���� inorder traverse �ϸ鼭 �湮�� ������� �������� ����Ѵ�.
		}
	}
}

class RedBlackTree extends BinarySearchTree {
	private class Node {
		private int key;
		private Node left,right,parent;
		private int color;
		Node(int key) { this.key = key; left=right=parent=null; }
	}
	public void insert() {
		
	}
	public void delete() {
		
	}
}