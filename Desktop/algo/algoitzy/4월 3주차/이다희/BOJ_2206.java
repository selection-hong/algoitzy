import java.io.*;
import java.util.*;

public class BOJ_2206 {

	static class Node implements Comparable<Node> {
		
		int r, c, distance, broken;
		
		public Node(int r, int c, int distance, int broken) {
			super();
			this.r = r;
			this.c = c;
			this.distance = distance;
			this.broken = broken;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.distance, o.distance);
		}
		
	}
	static int N, M, ans;
	static int[][] map, use;
	static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static Queue<Node> pq = new PriorityQueue<>();
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		ans = -1;
		map = new int[N][M];
		use = new int[N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j)-'0';
				if (map[i][j] == 1) map[i][j] = -1;
				use[i][j] = map[i][j];
			}
		}
		if (N == 1 && M == 1) {
			if (map[0][0] == 0) ans = 1;
			else ans = -1;
		} else {
			pq.add(new Node(0, 0, 1, 0));
			while (!pq.isEmpty() && ans == -1) {
				Node pop = pq.poll();
				for (int[] d : dir) {
					int r = pop.r + d[0];
					int c = pop.c + d[1];
					if (r < 0 || r >= N || c < 0 || c >= M) continue;
					if (r == N-1 && c == M-1) {
						ans = pop.distance+1;
						break;
					}
					if (map[r][c] == -1 && pop.broken == 0) {
						use[r][c] = pop.distance+1;
						pq.add(new Node(r, c, use[r][c], 1));
					} else if ((map[r][c] == 0)) {
						if (pop.broken == 1) {
							if (use[r][c] != 0) continue;
			                use[r][c] = pop.distance + 1;
			                pq.add(new Node(r, c, use[r][c], 1));
						} else {
							map[r][c] = pop.distance+1;
							pq.add(new Node(r, c, map[r][c], 0));
						}
					}
				}
			}
		}
		System.out.println(ans);
		
	}

}
