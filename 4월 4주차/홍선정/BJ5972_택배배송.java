package BJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ5972_택배배송 {
	static class Edge implements Comparable<Edge> {
		int to, cost;

		public Edge(int to, int cost) {
			super();
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			//Qorl
			return Integer.compare(this.cost, o.cost);
		}

	}

	static final int INF = Integer.MAX_VALUE;
	static int V, E;
	static List<Edge>[] adj;
	static int[] dist;
	private static boolean b;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		adj = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++) {
			adj[i] = new ArrayList<>();
		}

		dist = new int[V + 1];
		Arrays.fill(dist, INF);

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			adj[u].add(new Edge(v, cost));
			adj[v].add(new Edge(u, cost));
		}
		dijkstra(1);

		System.out.println(dist[V]);

	}

	private static void dijkstra(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		boolean[] visited = new boolean[V+1];

		dist[start] = 0;

		pq.add(new Edge(start, 0));

		while (!pq.isEmpty()) {
			Edge cur = pq.poll();

			if (visited[cur.to])
				continue;

			visited[cur.to] = true;

			for (Edge node : adj[cur.to]) {
				if (!visited[node.to] && dist[node.to] > dist[cur.to] + node.cost) {
					dist[node.to] = dist[cur.to] + node.cost;
					pq.add(new Edge(node.to, dist[node.to]));

				}
			}

		}

	}

}
