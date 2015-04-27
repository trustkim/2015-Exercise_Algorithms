/*
 * Java의 HashSet과 유사한 기능을 제공하는 MyHashSet<E> 클래스를 구현한다.
 * 다음과 같은 생성하지 public 메서드를 제공한다.
 * MyHashSet(int initialCapacity, float loadFactor)
 * boolean add(E entry)
 * void clear()
 * boolean isEmpty()
 * boolean remove(E entry)
 * int size()
 */
public class Exercise11 {
	public static void main(String[] args) {
//		MyHashSet<Integer> hs = new MyHashSet<Integer>(10, (float) 0.8);
//		System.out.println(hs.isEmpty());
//		System.out.println(hs.add(1));
//		System.out.println(hs.add(1));
//		System.out.println(hs.contains(1));
//		System.out.println(hs.add(10));
//		for(int i=0;i<50;i++){
//			//int e = rd.nextInt(100);
//			hs.add(i);
//			//System.out.println(i+" is added "+hs.add(i));
//		}
//		System.out.println(199+" is in there? "+hs.contains(199));
//		System.out.println(10+" is in there? "+hs.contains(10));
		//hs.testPrint();
		MyHashSet<String> hs_S = new MyHashSet<String>(20, (float) 1);
		System.out.println(hs_S.remove("Hello World"));
		System.out.println(hs_S.isEmpty());
		hs_S.add("Hello Wolrd");
		hs_S.add("hi");
		hs_S.add("Hello");
		hs_S.add("Computer");
		hs_S.add("Programming");
		hs_S.add("Fun");
		hs_S.add("Good");
		hs_S.add("Fine");
		hs_S.add("Happy");
		hs_S.add("Dog");
		hs_S.add("Frog");
		hs_S.add("Baby");
		hs_S.add("Next");
		hs_S.add("Last");
		hs_S.testPrint();
		System.out.println(hs_S.contains("Hi"));
		System.out.println(hs_S.contains("Hello"));
		System.out.println(hs_S.remove("Hi"));
		System.out.println(hs_S.remove("Hello"));
		hs_S.testPrint();
		System.out.println(hs_S.remove("Dog"));
		hs_S.testPrint();
		System.out.println(hs_S.remove("Computer"));
		hs_S.testPrint();
	}
}

class MyHashSet<E> {
	@SuppressWarnings("hiding")
	private class Node<E> {	// for chaining
		public E element;
		public Node<E> next;
		public Node(E e) {element=e; next=null;}
		public Node(Node<E> node) {
			element = node.element;
			next=null;
		}
	}
	private Node<E> [] table;
	private int capacity;			// size of the table
	private float loadFactor;		// load factor
	private int size;
	@SuppressWarnings("unchecked")
	public MyHashSet(int initCap, float load) {
		capacity = initCap;
		loadFactor = load;
		size = 0;
		table = new Node[capacity];
	}
	public boolean contains(E entry) {
		int index = hash(entry);
		return search(index, entry);
	}
	private boolean search(int index, E entry) {
		MyHashSet<E>.Node<E> cur = table[index];
		while(cur!=null) {
			if(cur.element.equals(entry))
				return true;
			cur = cur.next;
		}
		return false;
	}
	public boolean add(E entry) {
		int tableIndex = hash(entry);
		if(!search(tableIndex,entry)){
			Node<E> cur = new Node<E>(entry);		// 새로 삽입할 노드
			insert(table,tableIndex,cur);
			size++;
			if(size==capacity*loadFactor) {	// 초기 설정한 capacity를 초과하면 전체 테이블을 늘려 주고 새로 해슁 해둠
				expHashSet(cur);
			}
			return true;
		}else	return false;				// 리스트에 중복된 값이 있다면 false를 반환
	}
	private void insert(Node<E>[] t, int index, Node<E> node) {
		node.next = t[index];	// 새로 추가할 노드의 next를 table[index]의 맨 앞 노드로 지정
		t[index] = node;			// 새로 추가한 노드가 이제 맨 앞 노드가 됨
	}
	private void expHashSet(Node<E> node) {
		System.out.println("!!!!!	TableExpandEvent	!!!!!");
		int oldCap = capacity;
		capacity *= 2;
		@SuppressWarnings("unchecked")
		Node<E>[] expTable = new Node[capacity];
		for(int i=0;i<oldCap;i++) {
			node = table[i];
			while(node!=null) {
				insert(expTable,hash(node.element),new Node<E>(node));
				node = node.next;
			}
		}
		table = expTable.clone();
	}
	
	public boolean remove(E entry) {
		int tableIndex = hash(entry);
		/* 교수님 코드 */
		Node<E> p=table[tableIndex];
		Node<E> q = null;
		while(p!=null && !p.element.equals(entry)) {
			q = p;
			p = p.next;
		}	// while문을 탈출하면 q가 p의 바로 직전 노드, p가 삭제할 노드가 됨.
		if (p==null)		// p가 null이면 삭제할 노드가 원래 없었다.
			return false;
		else if (q==null) 	// q가 null이면 삭제할 노드가 리스트의 맨 앞 노드.
			table[tableIndex] = p.next;
		else
			q.next = p.next;
		size--;
		return true;
		/* 교수님 빙의 끝 */
//		Node<E> cur = getNode(tableIndex,entry);
//		if(cur!=null){	// entry가 있으면 제거하고 true를 반환. 없으면 false를 반환
//			delete(tableIndex, cur);
//			return true;
//		}else return false;
	}
	// remove를 위해 doubly linked list로 쓰는 건 좀 과하다고 지적해 주심.
//	private Node<E> getNode(int index, E entry) {	// table[index]의 리스트에서 entry를 갖는 노드를 찾아 반환. 없으면 null을 반환
//		Node<E> node = table[index];
//		while(node!=null) {
//			if(node.element.equals(entry)) return node;
//			node = node.next;
//		}
//		return node;
//	}
//	private void delete(int index, Node<E> node) {
//		if(node.last==null)
//			table[index] = node.next;  
//		else
//			node.last.next = node.next;
//		if(node.next!=null) node.next.last = node.last;
//	}
	public void clear() {
		table = null;
		size = 0;
	}
	public boolean isEmpty() {
		return size==0?true:false;
	}
	private int hash(E entry) {
		return (entry.hashCode() & 0x7fffffff) % capacity;
	}
	
	public void testPrint() {
		for(int i=0;i<capacity;i++) {
			Node<E> cur = table[i];
			System.out.print("table["+i+"] : ");
			while(cur!=null) {
				System.out.print(cur.element.toString()+", ");
				cur = cur.next;
			}
			System.out.println();
		}
	}
}