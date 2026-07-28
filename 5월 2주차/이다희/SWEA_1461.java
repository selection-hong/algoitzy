import java.io.*;
import java.util.*;

class SWEA_1461 { // 프로세서 연결하기 : 완전탐색(DFS) + 백트래킹 + 가지치기
	
	static int N, M, count, length;
	static int[][] map, core;
	static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	
	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++) {
			
			N = Integer.parseInt(br.readLine());
			M = 0;
			count = 0;
			length = Integer.MAX_VALUE;
			map = new int[N][N];
			core = new int[13][2];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if (map[i][j] == 1) {
						// 가장자리 Core 설치
						if (i == 0 || i == N-1 || j == 0 || j == N-1) {
							map[i][j] = 13;
						} else {
							map[i][j] = ++M;
							core[M][0] = i;
							core[M][1] = j;
						}
					}
				}
			}
			
			combination(1, 0, 0);
			
			sb.append("#").append(test_case).append(" ").append(length).append("\n");
			
		}
		
		System.out.println(sb);
		
	}
	
	public static void combination(int idx, int cnt, int len) { // 2^M 경우의 수
		if (cnt + (M - idx + 1) < count) return; // 남은 Core 다 합쳐도 count보다 적은 경우
		if (idx == M + 1) {
			if (cnt > count || (cnt == count && len < length)) {
				count = cnt;
				length = len;
			}
			return;
		}
		// 설치  O
		for (int i = 0; i < 4; i++) { // 상하좌우
			int sum = install(i, idx, 0, idx);
			if (sum > 0) combination(idx + 1, cnt + 1, len + sum);
			install(i, idx, idx, 0);
		}
		// 설치 X
		combination(idx + 1, cnt, len);
	}
	
	public static int install(int look, int idx, int check, int fill) {
		int r = core[idx][0];
		int c = core[idx][1];
		int sum = 0;
		while (true) {
			r += dir[look][0];
			c += dir[look][1];
			if (r < 0 || r >= N || c < 0 || c >= N) break;
			if (map[r][c] != check) return 0;
			map[r][c] = fill;
			sum++;
		}
		return sum;
	}
	
}
