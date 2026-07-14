import java.io.*;
import java.util.*;
 
public class SWEA_1230 { // 암호문3 : 단방향 연결 리스트 직접 구현
	
	static class Node {
		
		int value;
		Node next;
		
		public Node(int value, Node next) {
			super();
			this.value = value;
			this.next = next;
		}
		
	}
	
	static class MyLinkedList {
		
		int size;
		Node head;
		Node tail;
		
		public Node search(int idx) {
			if (idx == size - 1) {
				return tail;
			}
			Node current = head;
			for (int i = 0; i < idx; i++) {
				current = current.next;
			}
			return current;
		}
		
		public void insert(int idx, int cnt) {
			if (idx == size - 1) {
				add(cnt);
			} else {
				Node current = search(idx);
				Node next = current.next;
				for (int i = 0; i < cnt; i++) {
					current.next = new Node(Integer.parseInt(st.nextToken()), null);
					current = current.next;
				}
				current.next = next;
			}
			size += cnt;
		}
		
		public void delete(int idx, int cnt) {
			Node current = search(idx);
			for (int i = 0; i < cnt; i++) {
				current.next = current.next.next;
			}
			if (current.next == null) {
				tail = current;
			}
			size -= cnt;
		}

		public void add(int cnt) {
			for (int i = 0; i < cnt; i++) {
				tail.next = new Node(Integer.parseInt(st.nextToken()), null);
				tail = tail.next;
			}
			size += cnt;
		}
		
		public void get(int idx, int cnt) {
			Node current = search(idx+1);
			for (int i = 0; i < cnt; i++) {
				sb.append(" ").append(current.value);
				current = current.next;
			}
		}
		
	}
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
	
    public static void main(String[] args) throws Exception {
         
        int T = 10;
         
        for (int tc = 1; tc <= T; tc++) {
        	
        	MyLinkedList list = new MyLinkedList();
        	list.head = list.tail = new Node(-1, null);
        	int N = Integer.parseInt(br.readLine());
        	st = new StringTokenizer(br.readLine());
        	list.add(N);
        	String command;
        	int x, y;
        	int M = Integer.parseInt(br.readLine());
        	st = new StringTokenizer(br.readLine());
        	for (int i = 0; i < M; i++) {
        		command = st.nextToken();
        		switch (command) {
        		case "I":
        			x = Integer.parseInt(st.nextToken());
        			y = Integer.parseInt(st.nextToken());
        			list.insert(x + 0, y);
        			break;
        		case "D":
        			x = Integer.parseInt(st.nextToken());
        			y = Integer.parseInt(st.nextToken());
        			list.delete(x, y);
        			break;
        		case "A":
        			y = Integer.parseInt(st.nextToken());
        			list.add(y);
        			break;
        		}
        	}
        	sb.append("#").append(tc);
        	list.get(0, 10);
        	sb.append("\n");
             
        }
         
        System.out.println(sb);
         
    }
     
}