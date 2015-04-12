import java.util.Random;

/*
 * 레드블랙트리에 대해서 insert와 delete 함수를 구현하라.
 */
public class Exercise10_1 {
	public static void main(String[] args) {		
		int N = 15;						// 추가 삭제할 샘플 수
		Random rd = new Random();
		RedBlackTree rbt = new RedBlackTree();
		
		// (1) 먼저 이미 이진검색트리에 있는지 검사한다.
		for(int i=0;i<N;i++) {
			int sample = rd.nextInt(N);
			System.out.print("now check the key is "+sample);
			if(rbt.search(rbt.root, sample)==null) {	// 이진검색트리에 없으면,
				rbt.insert(sample);						// (3) 없으면 그 값을 트리에 insert한다.
				System.out.print(" insert!\n");
			}else {
				rbt.delete(rbt, rbt.search(rbt.root, sample));
				System.out.print(" delete!\n");
			}	// (2) 만약 있으면 그 값을 트리로 부터 삭제한다.
			rbt.inorderTraversal(rbt.root);
			System.out.println();
			// (4) 마지막으로 트리를 inorder traverse 하면서 방문된 순서대로 정수들을 출력한다.
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