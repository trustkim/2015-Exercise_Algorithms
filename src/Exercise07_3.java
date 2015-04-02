import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/*
 * 주소록 파일이 있다. 파일의 한 라인은 한 사람에 대해서 이름, 주소, 전화번호, 그리고 이메일 주소 필드로
 * 구성된다. 각 필드는 공백이 없는 ㅎ나ㅏ의 문자열이며, 필드와 필드는 하나의 공백으로 구분되어 있다.
 * 이 주소록 파일을 읽은 후 각각의 사람을 하나의 객체로 저장한 후 다음과 같이 사용자의 명령을 수행하는 프로그램을 작성하라.
 * 단, 반드시 Java API, 혹은 C, C++ 표준 라이브러리가 제공하는 정렬 기능을 사용하라.
 * 즉 정렬 알고리즘을 직접 구현해서는 안된다.
 */
public class Exercise07_3 {
	public static class Person {
		public String name, address, phone, mail;
		Person(String n, String a, String p, String m) {name=n;address=a;phone=p;mail=m;}
		String Print() {
			return name+" "+address+" "+phone+" "+mail;
		}
		public static Comparator<Person> nameComparator = new Comparator<Person>() {
			public int compare(Person A, Person B) {
				return A.name.compareTo(B.name);
			}
		};
		public static Comparator<Person> addressComparator = new Comparator<Person>() {
			public int compare(Person A, Person B) {
				return A.address.compareTo(B.address);
			}
		};
		public static Comparator<Person> phoneComparator = new Comparator<Person>() {
			public int compare(Person A, Person B) {
				return A.name.compareTo(B.phone);
			}
		};
		public static Comparator<Person> mailComparator = new Comparator<Person>() {
			public int compare(Person A, Person B) {
				return A.name.compareTo(B.mail);
			}
		};
	}
	
	private static final int MAX = 1000;
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(new File("input07_3.txt"));
			Person[] people = new Person[MAX];		// people 공간을 여유 있게 확보 
			int size = 0, i=0;
			while(sc.hasNext()) {
				people[i] = new Person(sc.next().trim(),sc.next().trim(),sc.next().trim(),sc.next().trim());
				i++;
				size++;
			}	// file read complete
			sc.close();
			sc = new Scanner(System.in);
			boolean isExit = false;
			while(!isExit) {
				String cmd = sc.nextLine();
				if(cmd.equals("sort by name")) {		// sort by name
					Arrays.sort(people, 0, size, Person.nameComparator);	// 여유가 있으므로 반드시 범위를 지정해 줘야 함
				}else if(cmd.equals("sort by address")) {
					Arrays.sort(people, 0, size, Person.addressComparator);	// sort by address
				}else if(cmd.equals("sort by phone")) {
					Arrays.sort(people, 0, size, Person.phoneComparator);	// sort by phone
				}else if(cmd.equals("sort by email")) {
					Arrays.sort(people, 0, size, Person.mailComparator);	// sort by mail
				}else if(cmd.equals("print")) {
					Print(people, size);		// print
				}else if(cmd.equals("exit")) {
					isExit = true;//System.exit(0);				// exit
				}	
			}
			sc.close();
		} catch(FileNotFoundException e) { System.out.println("file not found...");}
	}
	private static void Print(Person[] people, int size) {
		for(int i=0;i<size;i++)
			System.out.println(people[i].Print());
		System.out.println();
	}
}
