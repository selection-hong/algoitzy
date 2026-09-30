import java.io.*;
import java.util.*;

public class SWEA_1248 { // 공통조상 : LCA(Lowest Common Ancestor) + DFS
	
	static int size;
	static int[] parents;
	static int[][] children;
	static boolean[] visit;
	
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			st = new StringTokenizer(br.readLine());
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			parents = new int[V+1];
			children = new int[V+1][2];
			visit = new boolean[V+1];
			for (int i = 0; i < E; i++) {
				int p = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				parents[c] = p;
				if (children[p][0] > 0) children[p][1] = c;
				else children[p][0] = c;
			}
			
			// [1] 가까운 공통 조상 찾기 : LCA(Lowest Common Ancestor)
			int idx = v1;
			while (idx > 0) {
				visit[idx] = true;
				idx = parents[idx];
			}
			idx = v2;
			while (idx > 0) {
				if (visit[idx]) break;
				idx = parents[idx];
			}
			
			// [2] 서브 트리의 크기 구하기 : DFS
			size = 0;
			Arrays.fill(visit, false);
			count(idx);
			
			sb.append("#").append(tc).append(" ").append(idx).append(" ").append(size).append("\n");
			
		}
		
		System.out.println(sb);

	}

	private static void count(int idx) {
		if (idx == 0) return;
		size++;
		count(children[idx][0]);
		count(children[idx][1]);
	}

}
