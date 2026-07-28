import java.io.*;
import java.util.*;

public class BOJ_1941 {

	static int ans, start;
	static int N = 5, K = 7;
	static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static boolean[][] map, check, visit;
	static Queue<Integer> queue = new ArrayDeque<>();
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ans = 0;
		map = new boolean[N][N];
		check = new boolean[N][N];
		visit = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < N; j++) {
				if (str.charAt(j) == 'S') map[i][j] = true;
			}
		}
		com(0, 0, 0);
		System.out.println(ans);
	}
	
	public static void com(int cnt, int idx, int cntY) {
		if (cntY > K / 2) return;
		if (cnt == K) {
			if (bfs()) ans++;
			return;
		}
		for (int i = idx; i < N*N; i++) {
			if (cnt == 0) start = i;
			int row = i / N;
			int col = i % N;
			check[row][col] = true;
			if (map[row][col]) com(cnt+1, i+1, cntY);
			else com(cnt+1, i+1, cntY+1);
			check[row][col] = false;
		}
	}
	
	public static boolean bfs() {
		int cnt = 1;
		for (int i = 0; i < N; i++) {
			Arrays.fill(visit[i], false);
		}
		queue.clear();
		queue.add(start);
		visit[start/N][start%N] = true;
		while (!queue.isEmpty()) {
			int temp = queue.poll();
			for (int[] d : dir) {
				int dr = temp/N + d[0];
				int dc = temp%N + d[1];
				if (dr < 0 || dr >= N || dc < 0 || dc >= N) continue;
				if (visit[dr][dc] || !check[dr][dc]) continue;
				visit[dr][dc] = true;
				queue.add(dr*N+dc);
				cnt++;
			}
		}
		return cnt == K;
	}

}
