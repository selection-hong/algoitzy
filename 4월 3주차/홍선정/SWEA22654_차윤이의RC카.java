package SWEA_D2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA22654_차윤이의RC카 {
	// 방향 배열 선언 - 전역 변수
	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };

	// 메인 함수
	public static void main(String[] args) throws IOException {

		// 테케 및 전체 수 입력 받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			int[][] map = new int[N][N];
			// 시작점, 도착점 초기화(garbageok) 및 입력받는 동시에 시작점, 도착점인지 파악해 변수 대입
			int startR = 0;
			int startC = 0;
			int endR = 0;
			int endC = 0;

			for (int i = 0; i < N; i++) {
				String line = br.readLine();
				for (int j = 0; j < N; j++) {
					map[i][j] = line.charAt(j);

					if (map[i][j] == 'X') {
						startR = i;
						startC = j;
					}else if (map[i][j] == 'Y') {
						endR = i;
						endC = j;

					}
				}
			}

			// 커맨드 입력 및 출력문 형식
			int Q = Integer.parseInt(br.readLine());
			StringTokenizer st;
			
			sb.append("#").append(tc);
			


			// 반복문으로 커맨드 쿼리 처리
			for (int q = 0; q < Q; q++) {
				st = new StringTokenizer(br.readLine());

				int C = Integer.parseInt(st.nextToken());
				String commands = st.nextToken();

				// 매 쿼리마다 RC카 상태(시작점, 방향) 초기화 필요!!!
				int nr=0;
				int nc=0;
				int cr=startR; // 입력 받은 시작점부터 시작!!★★★★★★★★★★
				int cc=startC;
				int dir = 0;
				

				// 반복문 커맨드 시뮬레이션 -
				for (int c = 0; c < C; c++) {
					int cur = commands.charAt(c);

					if (cur == 'A') {
						nr = cr + dr[dir];
						nc = cc + dc[dir];
						
						//트리인 지 아닌지 확인 로직 추가★★★★★
						if (nr >= 0 && nc >= 0 && nr < N && nc < N&&map[nr][nc]!='T') {
							cr = nr;
							cc = nc;
						}

					} else if (cur == 'L') {
						dir = (dir + 3) % 4;

					} else if (cur == 'R') {
						dir = (dir + 1) % 4;
					}

				}
				// 커맨드 종료 후 목적지 도달 여부 확인해 StringBuilder에 쌓기
				if(cr==endR&&cc==endC) {
					sb.append(" ").append(1);
				}else {
					sb.append(" ").append(0);
				}
			}
			sb.append("\n");

		}
		System.out.println(sb.toString());

	}

}
