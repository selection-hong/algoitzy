import java.io.*;
import java.util.*;

public class Main {
    
	static class Node implements Comparable<Node> {
		int idx;
		long dist;
		public Node(int idx, long dist) {
			super();
			this.idx = idx;
			this.dist = dist;
		}
		@Override
		public int compareTo(Node o) {
			return Long.compare(this.dist, o.dist);
		}
	}
	static int N, M, K;
	static long[] distance;
	static boolean[] visit;
	static ArrayList<Node>[] list;
	static PriorityQueue<Node> pq;
	
    public static void main(String[] args) throws Exception {
        
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        distance = new long[N];
        Arrays.fill(distance, Long.MAX_VALUE);
        visit = new boolean[N];
        list = new ArrayList[N];
        pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
        	list[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	int U = Integer.parseInt(st.nextToken()) - 1;
        	int V = Integer.parseInt(st.nextToken()) - 1;
        	int C = Integer.parseInt(st.nextToken());
        	list[V].add(new Node(U, C)); // 역방향 저장
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
        	int K = Integer.parseInt(st.nextToken()) - 1;
        	distance[K] = 0;
        	pq.add(new Node(K, 0)); // 면접장 -> 도시 최소 거리 계산
        }
        dijkstra();
        int idx = 0;
        long dist = distance[0];
        for (int i = 1; i < N; i++) {
        	if (distance[i] > dist) {
        		idx = i;
        		dist = distance[i];
        	}
        }
        System.out.println(idx+1);
        System.out.println(dist);
        
    }

	private static void dijkstra() { // 다중 시작점 다익스트라

		while (!pq.isEmpty()) {
			Node pop = pq.poll();
			if (visit[pop.idx]) continue;
			visit[pop.idx] = true;
			for (Node next : list[pop.idx]) {
				if (distance[next.idx] > distance[pop.idx] + next.dist) {
					distance[next.idx] = distance[pop.idx] + next.dist;
					pq.add(new Node(next.idx, distance[next.idx]));
				}
			}
		}
		
	}
}
