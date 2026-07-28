import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA1953_탈주범검거2 {
   
    static int [] dr= {-1,0,1,0};
    static int [] dc= {0,1,0,-1};
    
    // type번 파이프가 dir 방향으로 열려 있는 지 -> pipe[type][dir] 
    static boolean[][] PIPE = {
        {false, false, false, false}, // 0 빈 공간
        {true, true, true, true},     // 1 상하좌우
        {true, false, true, false},   // 2 상하
        {false, true, false, true},   // 3 좌우
        {true, true, false, false},   // 4 상우
        {false, true, true, false},   // 5 하우
        {false, false, true, true},   // 6 하좌
        {true, false, false, true}    // 7 상좌
    };

    private static int N, M, R, C, L;
    private static int[][] map;
    private static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine().trim());
        
        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); //맵 행 크기 
            M = Integer.parseInt(st.nextToken()); //맵 열 크기
            R = Integer.parseInt(st.nextToken()); // 맨홀 행위치
            C = Integer.parseInt(st.nextToken()); // 맨홀 열위치
            L = Integer.parseInt(st.nextToken()); // 제한 시간
            
            map = new int[N][M];
            visited = new boolean[N][M];
            
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }         
            sb.append("#").append(t).append(" ").append(bfs()).append("\n");
        }
        
        System.out.print(sb);
    }

    private static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        
        q.offer(new int[]{R, C});
        visited[R][C] = true;
        
        int cnt = 1; // 맨홀 위치 1부터 시작
        int time = 1;  // 현재 경과 시간
        
        // 시간이 L에 도달 시 종료 -> 구현??
        while (!q.isEmpty() && time < L) {
            int size = q.size(); // 현재 시간에 탐색할 노드 수
            
            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int r = cur[0];
                int c = cur[1];
                int curp = map[r][c];
                
                for (int d = 0; d < 4; d++) {
                    if (!PIPE[curp][d]) continue;
                    
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    
                    if (nr < 0 || nr >= N || nc < 0 || nc >= M || visited[nr][nc]) continue;
                    
                    int nextp = map[nr][nc];
                    
                    if (nextp == 0 || !PIPE[nextp][(d + 2) % 4]) continue;
                    
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                    cnt++;
                }
            }
            time++;
        }
        
        return cnt;
    }
}