import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/*
 * 지난 연습문제9의 2번에서 구현한 TreeMap 프로그램을 이진탐색트리 대신 레드블랙트리를 사용하도록 수정하라.
 * 동일한 테스트 데이터로 동일한 작업을 수행한 후 수행시간을 비교하라.
 */
public class Exercise10_2 {
	public static void main(String[] args){
		MyTreeMap<String, Integer> treeMap = new MyTreeMap<String, Integer>();
		MyRBTreeMap<String, Integer> rbTreeMap = new MyRBTreeMap<String, Integer>();
		String[] strbf = new String[100000];	// 버퍼
		int linecnt = 0;						// 라인넘버 카운터
		try {
			Scanner sc = new Scanner(new File("input09_2.txt"));
			while(sc.hasNextLine()) {
				strbf[linecnt] = sc.nextLine();	// 한 라인씩 버퍼에 저장. 한 권이 통째로 저장됨.
				linecnt++;						// 라인넘버 카운팅
			}
			System.out.println("file read complete! "+linecnt+" lines");
			long start = System.currentTimeMillis();
			for(int i=0;i<linecnt;i++) {
				String[] temp = strbf[i].split(" ");	// 한 줄에서 공백문자를 기준으로 단어를 나눔. 
				for(int j=0;j<temp.length;j++) {		// 해당 라인의 모든 단어 검사
					temp[j] = myTrim(temp[j].toLowerCase());	// 소문자로 바꿔서 특수문자 제거하는 myTrim을 수행.
					if(temp[j]!=null)					
						treeMap.put(temp[j], i+1);		// 알파벳 단어이면 MyTreeMap에 put함
				}
			}
			System.out.println("MyTreeMap's size is "+treeMap.size()+" nodes");
			System.out.println("treeMap Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
			
			start = System.currentTimeMillis();
			for(int i=0;i<linecnt;i++) {
				String[] temp = strbf[i].split(" ");	// 한 줄에서 공백문자를 기준으로 단어를 나눔. 
				for(int j=0;j<temp.length;j++) {		// 해당 라인의 모든 단어 검사
					temp[j] = myTrim(temp[j].toLowerCase());	// 소문자로 바꿔서 특수문자 제거하는 myTrim을 수행.
					if(temp[j]!=null)					
						rbTreeMap.put(temp[j], i+1);		// 알파벳 단어이면 MyTreeMap에 put함
				}
			}
			System.out.println("MyRBTreeMap's size is "+rbTreeMap.size()+" nodes");
			System.out.println("rbTreeMap Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
			sc.close();
			try {
				PrintStream ps = new PrintStream(new File("output10_2.txt"));
				rbTreeMap.Print(ps);
				ps.close();
				System.out.println("file write complete!");
			}catch(IOException e) {e.printStackTrace();}
		}catch(FileNotFoundException e) { System.out.println("file not found...");}
	}
	private static String myTrim(String str) {	// 단어의 앞, 뒤를 검사하여 소문자 이외의 문자를 제거하는 함수.
		if(str.length()==0) return null;		// 길이가 0인 단어를 검사하면 null을 반환
		char s = str.charAt(0);					// 검사하는 단어의 첫글자
		char e = str.charAt(str.length()-1);	// 마지막 글자
		if(!isLetter(s))						// 첫글자가 알파벳 소문자가 아니면
			return myTrim(str.substring(1));	// 첫글자를 빼고 myTrim검사
		else if(!isLetter(e))					// 첫글자가 알파벳 소문자이고, 끝 글자가 알파벳 소문자가 아니면
			return myTrim(str.substring(0, str.length()-1));	// 끝 글자 빼고 myTrim검사
		else // isLetter(s) && isLetter(e): 첫 글자, 끝 글자가 알파벳이면
			return str;	// str 반환
	}
	private static boolean isLetter(char c) {	// 단어가 알파벳 소문자인지 검사하는 함수.
		return (c>='a' && c<='z');
	}
}

class MyRBTreeMap<K extends Comparable<K>, V> {
	/* 멤버 변수, 객체는 모두 private으로 외부에서 접근 못하게 함 */
	private static final int RED = 1;
	private static final int BLACK = 0;
	private class Node {	// 노드 클래스는 외부에 만들어 놓고 상속 받게 할 수도 있다.
		public K key;
		public V value;
		public int color;
		Node left,right,parent;
		Node(K key, V value) {
			this.key=key; this.value=value; left=right=parent=null;
			color = RED;
		}
	}
	private Node root;				// 루트 노드
	private int nbrNodes = 0;		// 트리의 노드 개수

	
	/* MyTreeMap이 HashMap적인 기능을 위한 public 메소드들 */
	public MyRBTreeMap() {
		root = null;
	}
	public boolean containsKey(K key) {
		/* 키값이 key인 순서쌍이 있으면 true, 없으면 false를 반환  */
		return search(root, key)!=null?true:false;
	}
	public boolean isEmpty() {
		/* empty이면 true를 반환 */
		return nbrNodes==0?true:false;
	}
	public V get(K key) {
		/* 키값이 key인 순서쌍을 찾아서 그 value를 반환, 없으면 null을 반환  */
		return search(root, key).value;
	}
	public V put(K key, V value) {
		Node node = search(root, key);
		V oldValue = null;
		if(node==null) {					// 키가 존재하지 않았으면
			insert(new Node(key, value));	// (key,value)를 삽입한다
		}else {
			oldValue = node.value;
			node.value = value;	// 키가 이미 있으면 새로운 값으로 갱신
		}
		return oldValue;	// 원래 값 반환
	}
	public int size() { return nbrNodes; }
	public V remove(K key) {
		Node node = search(root,key);
		V deletedValue = null;
		if(node!=null) {
			deletedValue=delete(node).value;
		}
		return deletedValue;
	}
	public void Print(PrintStream out) {	// out으로 출력하는 함수
		inorderTraversal(root, out);
	}
	public void clear() { root = null; }

	/* 레드 블랙 트리에 추가된 private 메소드들  */
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
			if(z.parent == z.parent.parent.left) {	// z의 부모가 할아버지의 왼쪽 자식인 경우
				Node y = z.parent.parent.right;		// y는 z의 삼촌
				if(y!=null && y.color==RED) {					// Case1: 삼촌이 RED
					z.parent.color = BLACK;			// z의 부모를 BLACK으로
					y.color = BLACK;				// z의 삼촌도 BLACK으로
					z.parent.parent.color = RED;	// z의 할아버지를 RED로
					z = z.parent.parent;			// 다시 z의 할아버지에 대하여 경우1 검사 반복
				}else {	// 경우2~3은 삼촌이 BLACK인 경우
					if(z == z.parent.right) {		// Case2: z가 오른쪽 자식
						z = z.parent;
						leftRotate(z);				// 부모에 대하여 leftRotate 진행
					}	// 경우2는 항상 경우3으로 진행		// Case3: z가 왼쪽 자식
					z.parent.color = BLACK;			// 부모를 BLACK으로
					if(z.parent.parent!=null) {
						z.parent.parent.color = RED;	// 할아버지를 RED로
						rightRotate(z.parent.parent);	// 할아버지에 대하여 rightRotate 진행
					}
				}
			}else {									// z의 부모가 할아버지의 오른쪽 자식인 경우
				Node y = z.parent.parent.left;		// y는 z의 삼촌
				if(y!=null && y.color==RED) {					// Case 4
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}else {
					if(z == z.parent.left) {		// Case 5
						z = z.parent;
						rightRotate(z);				// 부모에 대하여 rightRotate 진행
					}
					z.parent.color = BLACK;			// Case 6
					if(z.parent.parent!=null) {
						z.parent.parent.color = RED;
						leftRotate(z.parent.parent);	// 할아버지에 대하여 leftRotate 진행
					}
				}
			}
		}
		root.color = BLACK;
	}	// O(logN)
	private void rbDeleteFixup(Node x) {
		while(x!=null && x!=root && x.color==BLACK) {
			if(x==x.parent.left) {	// 경우 1~4: x가 부모의 왼쪽 자식
				Node w = x.parent.right;// w는 z의 형제
				if(w!=null){
					if(w.color == RED) {								// 경우1: 형제가 RED인 경우
						w.color = BLACK;
						x.parent.color = RED;
						leftRotate(x.parent);
						w = x.parent.right;
					}
					if(w.left!=null && w.left.color==BLACK && w.right!=null && w.right.color==BLACK) {	// 경우2
						w.color = RED;
						x = x.parent;
					}else {
						if(w.right!=null&&w.right.color == BLACK) {					// 경우3
							if(w.left!=null)
								w.left.color = BLACK;
							w.color = RED;
							rightRotate(w);
							w = x.parent.right;
						}	// 경우4로 진행
						w.color = x.parent.color;						// 경우4
						x.parent.color = BLACK;
						if(w.right!=null)
							w.right.color = BLACK;
						leftRotate(x.parent);
						x = root;
					}
				}
			}else {					// 경우 5~8: x가 부모의 오른쪽 자식
				Node w = x.parent.left;// w는 z의 형제
				if(w!=null){
					if(w.color == RED) {								// 경우5: 형제가 RED인 경우
						w.color = BLACK;
						x.parent.color = RED;
						rightRotate(x.parent);
						w = x.parent.left;
					}
					if(w.left!=null && w.left.color==BLACK && w.right!=null && w.right.color==BLACK) {	// 경우6
						w.color = RED;
						x = x.parent;
					}else {
						if(w.left!=null&&w.left.color == BLACK) {						// 경우7
							if(w.right!=null)
								w.right.color = BLACK;
							w.color = RED;
							leftRotate(w);
							w = x.parent.left;
						}	// 경우8로 진행
						w.color = x.parent.color;						// 경우8
						x.parent.color = BLACK;
						if(w.left!=null)
							w.left.color = BLACK;
						rightRotate(x.parent);
						x = root;
					}
				}
			}
		}
		if(x!=null)
			x.color = BLACK;
	}	// O(logN)
	/* HashMap과는 상관 없이 Tree 내부적인 처리를 위한 private 메소드들 */
	private Node search(Node x, K k) {
		if(x==null || k.compareTo(x.key)==0)
			return x;
		if(k.compareTo(x.key)<0)
			return search(x.left, k);
		else
			return search(x.right, k);
	}
	private Node minimum() {
		Node x = root;
		while(x.left != null)
			x=x.left;
		return x;
	}
	private Node successor(Node x) {
		if(x.right!=null)
			return minimum();
		Node y = x.parent;
		while(y!=null && x==y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}
	private void insert(Node z) {
		Node y = null;		// 추가할 노드의 parent 위치를 추적
		Node x = root;
		while(x!=null) {
			y = x;			// 이전 반복문에서의 현재 노드가 이번 반복문에서의 부모 노드
			if(z.key.compareTo(x.key)<0)
				x = x.left;	// 키가 작으면 왼쪽
			else x = x.right;	// 크면 오른쪽
		}	// 추가할 노드의 위치 찾기 끝
		z.parent = y;	// 추가할 노드의 부모 지정
		if(y==null)
			root = z;	// Tree T was empty
		else if(z.key.compareTo(y.key)<0)
			y.left = z;
		else y.right = z;
		// z노드의 자식을 NIL로 설정하는 것과 RED 컬러링 하는 것은 Node의 생성자에서 담당
		rbInsertFixup(z);
		nbrNodes++;	// 전체 노드 개수 1증가
	}
	private Node delete(Node z) {
		Node y;
		if(z.left== null || z.right==null) {
			y = z;
		}else y = successor(z);			// 삭제할 노드의 왼쪽, 오른쪽 자식이 존재 하므로 z는 현재 트리의 MAX일 수 없다.
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
			z.value = y.value;
		}
		if(y.color == BLACK)	// 삭제한 노드가 BLACK인경우 문제가 발생
			rbDeleteFixup(x);
		if(nbrNodes>0) nbrNodes--;	// 전체 노드 개수 1 감소
		return y;	// 삭제된 노트
	}
	private void inorderTraversal(Node x, PrintStream out) {
		if(x!=null){
			inorderTraversal(x.left, out);
			out.append((String.format("%-45s", x.key.toString())+x.value.toString()+" lines\n"));	// key랑 value가 String이 아닐 경우도 문제 없을까? 
			inorderTraversal(x.right, out);
		}
	}
}