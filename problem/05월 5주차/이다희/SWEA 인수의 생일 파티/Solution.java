import java.io.*;
import java.util.*;

class Solution { // SWEA 1795. 인수의 생일 파티 : Dijkstra
	
	static class Node implements Comparable<Node> {
		
		int idx, dist;

		public Node(int idx, int dist) {
			super();
			this.idx = idx;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.dist, o.dist);
		}
		
	}
	
	static int N, M, X;
	static int[] total;
	
	public static void main(String args[]) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++) {
			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			
			ArrayList<Node>[] out = new ArrayList[N+1]; // 정방향
			ArrayList<Node>[] in = new ArrayList[N+1];  // 역방향
			for (int i = 1; i <= N; i++) {
				out[i] = new ArrayList<>();
				in[i] = new ArrayList<>();
			}
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				out[x].add(new Node(y, c));
				in[y].add(new Node(x, c));
			}
			
			total = new int[N+1]; // 왕복 거리
			dijkstra(out);
			dijkstra(in);
			
			int answer = 0;
			for (int i = 1; i <= N; i++) {
				answer = Math.max(answer, total[i]);
			}
			
			sb.append("#").append(test_case).append(" ").append(answer).append("\n");
			
		}
		
		System.out.println(sb);
		
	}

	private static void dijkstra(ArrayList<Node>[] list) {
		
		int[] distance = new int[N+1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[X] = 0;
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(X, 0));
		
		while (!pq.isEmpty()) {
			Node pop = pq.poll();
			if (pop.dist > distance[pop.idx]) continue;
			for (Node node : list[pop.idx]) {
				if (distance[node.idx] > distance[pop.idx] + node.dist) {
					distance[node.idx] = distance[pop.idx] + node.dist;
					pq.add(new Node(node.idx, distance[node.idx]));
				}
			}
		}
		
		for (int i = 1; i <= N; i++) {
			total[i] += distance[i];
		}
		
	}
	
}