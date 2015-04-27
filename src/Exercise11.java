/*
 * Java�� HashSet�� ������ ����� �����ϴ� MyHashSet<E> Ŭ������ �����Ѵ�.
 * ������ ���� �������� public �޼��带 �����Ѵ�.
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
			Node<E> cur = new Node<E>(entry);		// ���� ������ ���
			insert(table,tableIndex,cur);
			size++;
			if(size==capacity*loadFactor) {	// �ʱ� ������ capacity�� �ʰ��ϸ� ��ü ���̺��� �÷� �ְ� ���� �ؽ� �ص�
				expHashSet(cur);
			}
			return true;
		}else	return false;				// ����Ʈ�� �ߺ��� ���� �ִٸ� false�� ��ȯ
	}
	private void insert(Node<E>[] t, int index, Node<E> node) {
		node.next = t[index];	// ���� �߰��� ����� next�� table[index]�� �� �� ���� ����
		t[index] = node;			// ���� �߰��� ��尡 ���� �� �� ��尡 ��
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
		/* ������ �ڵ� */
		Node<E> p=table[tableIndex];
		Node<E> q = null;
		while(p!=null && !p.element.equals(entry)) {
			q = p;
			p = p.next;
		}	// while���� Ż���ϸ� q�� p�� �ٷ� ���� ���, p�� ������ ��尡 ��.
		if (p==null)		// p�� null�̸� ������ ��尡 ���� ������.
			return false;
		else if (q==null) 	// q�� null�̸� ������ ��尡 ����Ʈ�� �� �� ���.
			table[tableIndex] = p.next;
		else
			q.next = p.next;
		size--;
		return true;
		/* ������ ���� �� */
//		Node<E> cur = getNode(tableIndex,entry);
//		if(cur!=null){	// entry�� ������ �����ϰ� true�� ��ȯ. ������ false�� ��ȯ
//			delete(tableIndex, cur);
//			return true;
//		}else return false;
	}
	// remove�� ���� doubly linked list�� ���� �� �� ���ϴٰ� ������ �ֽ�.
//	private Node<E> getNode(int index, E entry) {	// table[index]�� ����Ʈ���� entry�� ���� ��带 ã�� ��ȯ. ������ null�� ��ȯ
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