package assignment2;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class MinimumHeap<T extends Comparable> {
	private ArrayList<T> completeBinaryTree;
	
	@SuppressWarnings("unchecked")
	private void minHeapify(ArrayList data, int i, int size){
		if (i*2+1>size-1) {	// 자식 노드가 없다?
			return;
		}
		int k;
		if(i*2+2<=size-1) {	// 자식 노드가 두 개인 경우
			k = ((T) data.get(i*2+1)).compareTo(data.get(i*2+2))>0?i*2+2:i*2+1;
		}else {				// 한 개인 경우
			k = i*2+1;
		}
		if(((T) data.get(i)).compareTo(data.get(k))<=0)
			return;
		T temp = (T) data.get(i);
		data.set(i, data.get(k));
		data.set(k, temp);
		minHeapify(data, k, size);
	}
	private void buildMinHeap() {
		int heap_size = completeBinaryTree.size();
		for(int i=(heap_size-1)/2;i>=0;i--){
			minHeapify(completeBinaryTree, i, heap_size);
		}
	}
	private void insert(T p)
	{
		completeBinaryTree.add(p);
	}
	public void add(T p)
	{
		insert(p);
		buildMinHeap();
	}
	public T extractMin()
	{
		T min = completeBinaryTree.get(0);
		int size = completeBinaryTree.size();
		T temp = completeBinaryTree.get(size-1);
		completeBinaryTree.set(size-1, min);
		completeBinaryTree.set(0,temp);
		completeBinaryTree.remove(size-1);
		minHeapify(completeBinaryTree, 0, completeBinaryTree.size());
		return min;
	}
	
	/* getter */
	public T getRoot() {
		// TODO Auto-generated method stub
		return completeBinaryTree.get(0);
	}
	public int getSize()
	{
		return completeBinaryTree.size();
	}
	
	/* creator */
	public MinimumHeap(int initSize)
	{
		completeBinaryTree = new ArrayList<T>();
	}
	
	public void initMinHeap(ArrayList<T> runs) {
		for(T i:runs)
			insert(i);
		buildMinHeap();
		PrintMinHeap();// test Print
	}
	
	/* test Print code */
	public void PrintMinHeap()
	{
		for(T i:completeBinaryTree)
		{
			System.out.print(i.toString()+" ");
		}
		System.out.println();
	}
	
}