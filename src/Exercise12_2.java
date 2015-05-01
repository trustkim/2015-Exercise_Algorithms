import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/*
 * Ķ�����Ͼ� ���� ��� ����(?)���� �̸��� ���� �� �浵�� ��õ� ���� ������������ �־�����. �� ���Ͽ�
 * �����ϴ� ��� ���õ��� �������� �ϰ� �� ���ð��� �Ÿ��� 10km �����̸� ������ �����ϴ� �׷����� ����
 * ����Ʈ�� ������ ��, ����ڰ� ������ �̸��� �ϳ��� ���� k�� �Է��ϸ� �� ���÷κ��� k ȩ(hop) �̳���
 * �ִ� ��� ���õ��� �̸��� ����ϴ� ���α׷��� �ۼ��϶�. ���⼭ kȩ �̳���� ���� �׷������� �� ��带
 * �����ϴ� ����� ����(������ ����)�� k ���϶�� �ǹ��̴�. ��������Ʈ�� �����ϱ� ���ؼ��� ������ �̸�
 * �� �Ϸù�ȣ�� �ο��ؾ� �Ѵ�. �� ��ȣ�� ������ ���Ͽ� ������ ������� �ο��϶�. ���� ���� �̸��� �ο���
 * �Ϸ� ��ȣ�� ������ �˻��� �� �־�� �Ѵ�. �̸� ���� 4�忡�� �ۼ��� MyTreeMap Ŭ������ �̿��϶�. ��
 * �� 4�忡�� MyTreeMap Ŭ������ �ϼ����� ���ߴٸ� Java�� �����ϴ� HashMap�̳� TreeMap�� �̿��϶�.
 * �� �����̸��� key��, �Ϸú�ȣ�� value�� �ؼ� <�����̸�, �Ϸù�ȣ> ���� TreeMap�̳� HashMap��
 * ������ �� �����̸����� �˻��ϸ� �Ϸù�ȣ�� �� �� �ִ�. ������ ���Ͽ��� �ʵ���� tab���ڷ� �и��Ǿ� �ְ�,
 * ù ���� ���̺� �ش��̹Ƿ� �����ϸ� �ȴ�. ������ ���� �̸��� �ߺ��ؼ� �����ϱ⵵ �Ѵ�. �� ��� �̸� �ڿ�
 * �ٽ� 1, 2, ... ������ ��ȣ�� �߰��Ͽ� �����Ѵ�. ������ �浵 ������ ���� �Ÿ��� ������ִ� �Լ��̴�.
 * ���ð��� �Ÿ��� ����� �� �� �Լ��� �̿��϶�. �� �Լ��� ��ȯ�ϴ� �Ÿ��� ������ m�̴�.
 */
public class Exercise12_2 {
	private static final int MAXCITY = 200000;				// �ִ�� ���� �� �ִ� ���� ��.
	private static final int UNVISITED = -1;
	private static final int NAME = 1;
	private static final int LATITUDE = 3;					// ����.
	private static final int LONGITUDE = 2;					// �浵.
	static class City {
		int id;
		String name;
		double lat,lon;
		City(int id, String name, double lat, double lon) {
			this.id = id;
			this.name = String.copyValueOf(name.toCharArray());
			this.lat= lat;	// ����
			this.lon=lon;	// �浵
		}
	}
	private static MyRBTreeMap<String, Integer> rbm;		// <�����̸�, �Ϸù�ȣ> �� ��. �̰� ���� ��������Ʈ���� �����̸��� Ű�� �ش� ������ �Ϸù�ȣ�� �� ���� ã�� �� �ִ�.
	private static LinkedList<Integer>[] adjList;			// �� ���ø� �����ϴ� ���� ����Ʈ�� �ߴٰ� ���� �ް����� ������ ������ id�� ����Ǵ� LinkedList�� �迭�� �ٲ�.
	private static City[] tableBuffer = new City[MAXCITY];	// ���Ͽ��� �� ���� ������ �� Ŭ������ �����Ͽ� ������ ���̺�� ����� �Ԥ��̤�.
	@SuppressWarnings("unchecked")
	public static void main(String [] args){	
		rbm = new MyRBTreeMap<String, Integer>();
		int cityID = 0;	// ���� �Ϸ� ��ȣ. ��������Ʈ�� �ε����� ���� ���� 0���� ����.
		try {
			Scanner sc = new Scanner(new File("California_CA_Distances.txt"));
			sc.nextLine();	// ���̺� �ش� ����.
			String[] temp;	// ���� �� ���� ����.
			City city;		// �� ���� ��ü �ӽ� ����.
			while(sc.hasNextLine()){
				temp = sc.nextLine().split("\\t");				// ���Ͽ��� �� ������ �о� "\\t"���� �ڸ�. �齽���ð� �� ��!
				addCityName(temp[NAME],temp[NAME],cityID,0);			// Ʈ���ʿ� <�����̸�, �Ϸù�ȣ> ���� �߰���.
				city = new City(cityID, temp[NAME], Double.parseDouble(temp[LATITUDE]), Double.parseDouble(temp[LONGITUDE]));
				tableBuffer[cityID] = city;						// �ϴ� ��������Ʈ�� ũ�⸦ �𸣹Ƿ� �� ������ �� ���� ���� ��� ���� ������ ���̺� �����ص�.
				cityID++;
			}	// file read complete
			sc.close();
		}catch(FileNotFoundException e) {e.printStackTrace();}
		System.out.println("the Map is constructed! there is "+cityID+" cities");

		adjList = new LinkedList[cityID];
		constructAdjList();											// ��������Ʈ�� ����
		System.out.println("adjList is constructed!");

		int k=5;													// �ִ� �� �� �ִ� ȩ
		PrintByHop("\"University of California, Los Angeles\"",k);	// ȩ ������ BFS�ϸ� ���� �̸� ���.
	}
	public static double calDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta, dist;
		theta = lon1 - lon2;

		dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1))
				* Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);

		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;			// ���� mile ���� km ��ȯ.
		dist = dist * 1000.0;			// ���� km ���� m �� ��ȯ.

		return dist;
	}

	// �־��� ��(degree) ���� �������� ��ȯ
	private static double deg2rad(double deg) {
		return (double) (deg * Math.PI / (double)180d);
	}
	// �־��� ����(radian) ���� ��(degree) ������ ��ȯ
	private static double rad2deg(double rad) {
		return (double) (rad * (double)180d / Math.PI);
	}

	// <�����̸�, �Ϸù�ȣ> �������� �ʿ� �߰�. ���� �̸��� �ߺ��Ǵ� ��� linear probing ������� �����̸��� �ѹ��� �ٿ� �߰���.
	private static void addCityName(String city, String name, int id, int tag) {
		if(!rbm.containsKey(name))	// ���� ���� �̸��̸� �׳� �߰�.
			rbm.put(name, id);
		else {
			addCityName(city,city+(++tag),id,tag);	// tag�� �ߺ��Ǵ� ���� �̸��� �߰��� �ִ� ����
		}
	}

	// ��� ���ð� �Ÿ��� ���Ͽ� 10km �̸��� ���õ��� ��������Ʈ�� �߰��ϴ� �Լ�
	private static void constructAdjList() {
		for(int i=0;i<adjList.length;i++) {
			adjList[i] = new LinkedList<Integer>();	// �� ��������Ʈ �ε����� �ʱ�ȭ
		}
		for(int i=0;i<adjList.length;i++) {
			for(int j=0;j<adjList.length;j++) {
				if(i!=j) {
					if(calDistance(tableBuffer[i].lat,tableBuffer[i].lon,tableBuffer[j].lat,tableBuffer[j].lon)<=10*1000) {	// 10km ���ϴ� 10*1000m������ ��... Ȯ�� ������...
						//System.out.println("add AdjList!");
						adjList[rbm.get(tableBuffer[i].name)].add(tableBuffer[j].id);
					}
				}
			}
		}	// O(N*N)
	}

	// k ȩ �̳��� �ִ� ���ø� ����ϴ� BFS �Լ�
	private static void PrintByHop(String start, final int k) {
		System.out.println("PrintByHop from "+start+"...");
		if(rbm.containsKey(start)) {  
			int[] checkTable = new int[adjList.length];			// BFS���� �� �� ���� ��带 �ٽ� �湮���� �ʰ� �ϴ� üũ ���̺�. ���ÿ� hop�� ī��Ʈ�ϴ� �迭�̴�.
			for(int i=0;i<checkTable.length;i++) {
				checkTable[i] = UNVISITED;						// -1�� �ʱ�ȭ
			}
			Queue<Integer> queue = new LinkedList<Integer>();	// BFS�� ���� ť. ť���� �� ���� �Ϸù�ȣ�� �����Ͽ� BFS�� ������ ������ �ε����� ��ȯ�ϰ� �Ѵ�.
			int startIndex = rbm.get(start);
			queue.offer(startIndex);							// ���� id�� ���ÿ� ���� ��������Ʈ�� ��ť.
			checkTable[startIndex] = 0;							// ���� ��� üũ. �̷� �� ��������� ������ִ°� ���ٰ� �Ͻ�.
			int curIndex;										// BFS �� ���ܿ� ��ť���� ����.
			while(!queue.isEmpty()) {
				curIndex = queue.poll();						// �̹��� �湮�� id�� ���� ��������Ʈ�� ��ť.
				for(int i:adjList[curIndex]) {					// �� ��������Ʈ �ε����� ���� ��ȸ��. �� ���� �湮�� ����� ��� �湮���� �ʾҴ� ���� ��带 �湮.
					int nextIndex = rbm.get(tableBuffer[i].name);			// ���ͷ����� i�� name���� id�� ã��.
					if(checkTable[nextIndex]==UNVISITED && checkTable[curIndex]<k) {
						checkTable[nextIndex] = checkTable[curIndex]+1;		// �湮�� �ε��� üũ
						System.out.println(checkTable[nextIndex]+": "+tableBuffer[i].name);
						queue.offer(nextIndex);					// ������ ��� ��带 ��ť.
					}
				}
			}
		}
	}
}
