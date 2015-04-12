import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/*
 * Map 혹은 심볼 테이블(symbol table)이라고 부르는 추상데이터타입(abstract data type)은 여러개의 <key, value> 쌍들을
 * 저장하고 key에 대한 검색 기능을 제공한다. 예를 들어서 어떤 사람의 "이름", "나이", "성별", "주소" 등의 데이터를 저장할 때 이것을
 * <"name", "HongGilDong">, <"age", "24">, <"gender", "male">, <"address", "Busan">과 같이 각각을 
 * <key, value>의 순서쌍으로 표현하여 저장한다.
 * 이진 검색트리를 이용하여 이러한 기능을 제공하는 클래스 MyTreeMap을 작성하라. 트리의 각 노드는 두 개의 데이터 필드를 가져서 key와
 * value의 쌍을 저장할 수 있도록 한다. Java로 구현할 경우 아래와 유사하게 generics로 구현하여 임의의 객체가 key와 value가 될 수
 * 있도록 구현하라. 샘플파일에 등장하는 모든 단어를 구현한 MyTreeMap에 다음과 같은 규칙으로 저장한다. 파일에 나오는 단어들을 순서대로
 * 읽어서 소문자로 바꾼다. 단어의 앞이나 뒤에 붙은 특수문자나 숫자들은 제거한 후 그 단어와 그 단어가 등장한 라인번호의 쌍을 맵에 put함수를
 * 호출하여 추가한다. 결과적으로 맵에는 파일에 등장한 모든 단어들과 그 단어가 가장 마지막으로 등장한 라인번호의 쌍들이 저장되어 있어야 한다.
 */
public class Exercise09_2 {
	public static void main(String[] args){
		MyTreeMap<String, Integer> treeMap = new MyTreeMap<String, Integer>();
		String[] strbf = new String[100000];	// 버퍼
		int linecnt = 0;						// 라인넘버 카운터
		try {
			Scanner sc = new Scanner(new File("input09_2.txt"));
			while(sc.hasNextLine()) {
				strbf[linecnt] = sc.nextLine();	// 한 라인씩 버퍼에 저장. 한 권이 통째로 저장됨.
				linecnt++;						// 라인넘버 카운팅
			}
			System.out.println("file read complete! "+linecnt+" lines");
			for(int i=0;i<linecnt;i++) {
				String[] temp = strbf[i].split(" ");	// 한 줄에서 공백문자를 기준으로 단어를 나눔. 
				for(int j=0;j<temp.length;j++) {		// 해당 라인의 모든 단어 검사
					temp[j] = myTrim(temp[j].toLowerCase());	// 소문자로 바꿔서 특수문자 제거하는 myTrim을 수행.
					if(temp[j]!=null)					
						treeMap.put(temp[j], i+1);		// 알파벳 단어이면 MyTreeMap에 put함
				}
			}
			System.out.println("MyTreeMap's size is "+treeMap.size()+" nodes");
			sc.close();
			try {
				PrintStream ps = new PrintStream(new File("output09_2.txt"));
				treeMap.Print(ps);
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

class MyTreeMap<K extends Comparable<K>, V> {
	/* 멤버 변수, 객체는 모두 private으로 외부에서 접근 못하게 함 */
	private class Node {	// 노드 클래스는 외부에 만들어 놓고 상속 받게 할 수도 있다.
		public K key;
		public V value;
		Node left,right,parent;
		Node(K key, V value) { this.key=key; this.value=value; left=right=parent=null;}
	}
	private Node root;				// 루트 노드
	private int nbrNodes = 0;		// 트리의 노드 개수
	
	/* MyTreeMap이 HashMap적인 기능을 위한 public 메소드들 */
	public MyTreeMap() {
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
//	private Node maximum() {
//	Node x = root;
//	while(x.right != null)
//		x = x.right;
//	return x;
//}
//	private Node predecessor(Node x) {
//	if(x.left!=null)
//		return maximum(x.left);
//	Node y = x.parent;
//	while(y!=null && x==y.left) {
//		x = y;
//		y = y.parent;
//	}
//	return y;
//}
//	private void preorderTraversal(Node x) {
//		if(x!=null){
//			System.out.print(x.key+", ");
//			preorderTraversal(x.left);
//			preorderTraversal(x.right);	
//		}
//	}
//	private void postorderTraversal(Node x) {
//		if(x!=null){
//			postorderTraversal(x.left);
//			postorderTraversal(x.right);
//			System.out.print(x.key+", ");	
//		}
//	}
//	private void levelorderTraversal(Node root) {
//		Queue<Node> queue = new LinkedList<Node>();
//		queue.offer(root);
//		while(!queue.isEmpty()) {
//			Node node = queue.poll();
//			System.out.print(node.key+", ");
//			if(node.left!=null) queue.offer(node.left);
//			if(node.right!=null) queue.offer(node.right);
//		}
//	}
}