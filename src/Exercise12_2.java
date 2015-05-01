import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/*
 * 캘리포니아 주의 모든 도시(?)들의 이름과 위도 및 경도가 명시된 샘플 데이터파일이 주어진다. 이 파일에
 * 등장하는 모든 도시들을 정점으로 하고 두 도시간의 거리가 10km 이하이면 에지로 연결하는 그래프의 인접
 * 리스트를 구성한 후, 사용자가 도시의 이름과 하나의 정수 k를 입력하면 그 도시로부터 k 홉(hop) 이내에
 * 있는 모든 도시들의 이름을 출력하는 프로그램을 작성하라. 여기서 k홉 이내라는 말은 그래프에서 두 노드를
 * 연결하는 경로의 길이(에지의 개수)가 k 이하라는 의미이다. 인접리스트를 구성하기 위해서는 도시의 이름
 * 에 일련번호를 부여해야 한다. 이 번호는 데이터 파일에 나오는 순서대로 부여하라. 또한 도시 이름에 부여된
 * 일련 번호를 빠르게 검색할 수 있어야 한다. 이를 위해 4장에서 작성한 MyTreeMap 클래스를 이용하라. 만
 * 약 4장에서 MyTreeMap 클래스를 완성하지 못했다면 Java가 제공하는 HashMap이나 TreeMap을 이용하라.
 * 즉 도시이름을 key로, 일련변호를 value로 해서 <도시이름, 일련번호> 쌍을 TreeMap이나 HashMap에
 * 저장한 후 도시이름으로 검색하면 일련번호를 알 수 있다. 데이터 파일에서 필드들은 tab문자로 분리되어 있고,
 * 첫 줄은 테이블 해더이므로 무시하면 된다. 동일한 도시 이름이 중복해서 등장하기도 한다. 이 경우 이름 뒤에
 * 다시 1, 2, ... 식으로 번호를 추가하여 구분한다. 다음은 경도 값으로 부터 거리를 계산해주는 함수이다.
 * 도시간의 거리를 계산할 때 이 함수를 이용하라. 이 함수가 반환하는 거리의 단위는 m이다.
 */
public class Exercise12_2 {
	private static final int MAXCITY = 200000;				// 최대로 읽을 수 있는 도시 수.
	private static final int UNVISITED = -1;
	private static final int NAME = 1;
	private static final int LATITUDE = 3;					// 위도.
	private static final int LONGITUDE = 2;					// 경도.
	static class City {
		int id;
		String name;
		double lat,lon;
		City(int id, String name, double lat, double lon) {
			this.id = id;
			this.name = String.copyValueOf(name.toCharArray());
			this.lat= lat;	// 위도
			this.lon=lon;	// 경도
		}
	}
	private static MyRBTreeMap<String, Integer> rbm;		// <도시이름, 일련번호> 쌍 맵. 이걸 쓰면 인접리스트에서 도시이름을 키로 해당 도시의 일련번호를 더 빨리 찾을 수 있다.
	private static LinkedList<Integer>[] adjList;			// 각 도시를 노드로하는 인접 리스트로 했다가 많이 햇갈려서 교수님 조언대로 id가 저장되는 LinkedList의 배열로 바꿈.
	private static City[] tableBuffer = new City[MAXCITY];	// 파일에서 각 도시 정보를 한 클래스로 생성하여 별도의 테이블로 만들어 ㅤㄷㅜㄻ.
	@SuppressWarnings("unchecked")
	public static void main(String [] args){	
		rbm = new MyRBTreeMap<String, Integer>();
		int cityID = 0;	// 도시 일련 번호. 인접리스트의 인덱스로 쓰기 위해 0부터 시작.
		try {
			Scanner sc = new Scanner(new File("California_CA_Distances.txt"));
			sc.nextLine();	// 테이블 해더 무시.
			String[] temp;	// 파일 한 라인 버퍼.
			City city;		// 한 도시 객체 임시 변수.
			while(sc.hasNextLine()){
				temp = sc.nextLine().split("\\t");				// 파일에서 한 라인을 읽어 "\\t"으로 자름. 백슬래시가 두 개!
				addCityName(temp[NAME],temp[NAME],cityID,0);			// 트리맵에 <도시이름, 일련번호> 쌍을 추가함.
				city = new City(cityID, temp[NAME], Double.parseDouble(temp[LATITUDE]), Double.parseDouble(temp[LONGITUDE]));
				tableBuffer[cityID] = city;						// 일단 인접리스트의 크기를 모르므로 한 파일을 다 읽을 동안 모든 도시 정보를 테이블에 저장해둠.
				cityID++;
			}	// file read complete
			sc.close();
		}catch(FileNotFoundException e) {e.printStackTrace();}
		System.out.println("the Map is constructed! there is "+cityID+" cities");

		adjList = new LinkedList[cityID];
		constructAdjList();											// 인접리스트를 생성
		System.out.println("adjList is constructed!");

		int k=5;													// 최대 갈 수 있는 홉
		PrintByHop("\"University of California, Los Angeles\"",k);	// 홉 단위로 BFS하며 도시 이름 출력.
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
		dist = dist * 1.609344;			// 단위 mile 에서 km 변환.
		dist = dist * 1000.0;			// 단위 km 에서 m 로 변환.

		return dist;
	}

	// 주어진 도(degree) 값을 라디언으로 변환
	private static double deg2rad(double deg) {
		return (double) (deg * Math.PI / (double)180d);
	}
	// 주어진 라디언(radian) 값을 도(degree) 값으로 변환
	private static double rad2deg(double rad) {
		return (double) (rad * (double)180d / Math.PI);
	}

	// <도시이름, 일련번호> 순서쌍을 맵에 추가. 도시 이름이 중복되는 경우 linear probing 방식으로 도시이름에 넘버를 붙여 추가함.
	private static void addCityName(String city, String name, int id, int tag) {
		if(!rbm.containsKey(name))	// 없는 도시 이름이면 그냥 추가.
			rbm.put(name, id);
		else {
			addCityName(city,city+(++tag),id,tag);	// tag는 중복되는 도시 이름에 추가해 주는 숫자
		}
	}

	// 모든 도시간 거리를 구하여 10km 미만인 도시들을 인접리스트에 추가하는 함수
	private static void constructAdjList() {
		for(int i=0;i<adjList.length;i++) {
			adjList[i] = new LinkedList<Integer>();	// 각 인접리스트 인덱스를 초기화
		}
		for(int i=0;i<adjList.length;i++) {
			for(int j=0;j<adjList.length;j++) {
				if(i!=j) {
					if(calDistance(tableBuffer[i].lat,tableBuffer[i].lon,tableBuffer[j].lat,tableBuffer[j].lon)<=10*1000) {	// 10km 이하는 10*1000m이하인 거... 확인 잘하자...
						//System.out.println("add AdjList!");
						adjList[rbm.get(tableBuffer[i].name)].add(tableBuffer[j].id);
					}
				}
			}
		}	// O(N*N)
	}

	// k 홉 이내에 있는 도시를 출력하는 BFS 함수
	private static void PrintByHop(String start, final int k) {
		System.out.println("PrintByHop from "+start+"...");
		if(rbm.containsKey(start)) {  
			int[] checkTable = new int[adjList.length];			// BFS에서 한 번 갔던 노드를 다시 방문하지 않게 하는 체크 테이블. 동시에 hop을 카운트하는 배열이다.
			for(int i=0;i<checkTable.length;i++) {
				checkTable[i] = UNVISITED;						// -1로 초기화
			}
			Queue<Integer> queue = new LinkedList<Integer>();	// BFS를 위한 큐. 큐에는 각 도시 일련번호를 저장하여 BFS시 접근할 도시의 인덱스를 반환하게 한다.
			int startIndex = rbm.get(start);
			queue.offer(startIndex);							// 시작 id의 도시에 대한 인접리스트를 인큐.
			checkTable[startIndex] = 0;							// 시작 노드 체크. 이런 건 명시적으로 언급해주는게 좋다고 하심.
			int curIndex;										// BFS 한 스텝에 디큐받을 변수.
			while(!queue.isEmpty()) {
				curIndex = queue.poll();						// 이번에 방문한 id의 도시 인접리스트를 디큐.
				for(int i:adjList[curIndex]) {					// 한 인접리스트 인덱스를 전부 순회함. 즉 현재 방문한 노드의 모든 방문하지 않았던 인접 노드를 방문.
					int nextIndex = rbm.get(tableBuffer[i].name);			// 이터래이터 i의 name으로 id를 찾음.
					if(checkTable[nextIndex]==UNVISITED && checkTable[curIndex]<k) {
						checkTable[nextIndex] = checkTable[curIndex]+1;		// 방문한 인덱스 체크
						System.out.println(checkTable[nextIndex]+": "+tableBuffer[i].name);
						queue.offer(nextIndex);					// 인접한 모든 노드를 인큐.
					}
				}
			}
		}
	}
}
