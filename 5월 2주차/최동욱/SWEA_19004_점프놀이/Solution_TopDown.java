import java.io.*;
import java.util.*;

/**
 * [Java 8]
 * 색상 1번부터 K번까지 순서대로 방문할 때의 최단 거리를 구하는 프로그램
 */
class Solution_TopDown {

    // 타일 맵의 최대 크기 설정
    final static int SIZE = 50;

    // 각 색상별(1~K) 타일들의 좌표 정보를 담는 리스트 배열
    static ArrayList<int[]>[] colors = new ArrayList[SIZE * SIZE + 1];
    
    // 리스트 배열 객체 초기 생성
    static {
        for(int i = 1; i <= SIZE * SIZE; i++) {
            colors[i] = new ArrayList<>();
        }
    }

    // dp[y][x]: (y, x) 좌표에서 시작하여 최종 색상(K)까지 도달하는 데 필요한 최소 비용 저장
    static long[][] dp = new long[SIZE][SIZE];
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read(); // 입력 처리를 위한 초기 바이트 읽기

        StringBuilder sb = new StringBuilder();
        int T = readInt(); // 테스트 케이스 수
        
        for(int t = 1; t <= T; t++) {
            int n = readInt(); // 맵 크기
            int k = readInt(); // 마지막 색상 번호

            // 1. 데이터 초기화 및 맵 정보 입력
            init(n, k);
            
            // 2. 문제 해결 및 결과 저장
            sb.append('#').append(t).append(' ').append(solve(n, k)).append('\n');
        }
        // 전체 결과 한 번에 출력
        System.out.print(sb);
    }

    /**
     * 입력 데이터를 기반으로 색상별 좌표를 분류하고 DP 테이블을 초기화합니다.
     */
    private static void init(int n, int k) throws IOException {
        // 기존 색상 좌표 리스트 비우기
        for(int i = 1; i <= k; i++) {
            colors[i].clear();
        }

        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                dp[y][x] = -1; // 미방문 상태로 초기화

                int color = readInt();
                // 해당 색상의 좌표 리스트에 추가 (y, x)
                colors[color].add(new int[]{y, x});                
            }
        }
    }

    /**
     * 모든 색상이 존재하는지 확인 후, 1번 색상부터 탐색을 시작합니다.
     */
    private static long solve(int n, int k) {
        // 1부터 K까지 중간에 빠진 색상이 있다면 도달 불가능하므로 -1 반환
        for(int i = 1; i <= k; i++) {
            if(colors[i].isEmpty()) return -1;
        }

        long res = Long.MAX_VALUE;
        // 1번 색상의 모든 시작점에서 DFS 탐색 시작
        for(int[] cur : colors[1]) {
            res = Math.min(res, dfs(n, k, 2, cur[0], cur[1]));
        }
        return res;
    }

    /**
     * DFS와 메모이제이션을 사용하여 다음 색상으로의 최소 이동 거리를 계산합니다.
     * @param idx 찾고자 하는 다음 색상 번호
     * @param cy 현재 위치 Y
     * @param cx 현재 위치 X
     */
    private static long dfs(int n, int k, int idx, int cy, int cx) {
        // 기저 사례: 모든 색상을 순서대로 다 방문한 경우 (K번 색상 이후)
        if(idx == k + 1) return dp[cy][cx] = 0;
        
        long res = Long.MAX_VALUE;
        
        // 다음 순서(idx)의 색상을 가진 모든 좌표를 확인
        for(int[] next : colors[idx]) {
            int ny = next[0];
            int nx = next[1];

            // 현재 위치에서 다음 위치까지의 맨해튼 거리 계산
            long dist = getDistance(cy, cx, ny, nx);
            
            // 다음 지점의 최단 거리가 계산되어 있다면(dp != -1) 활용, 아니면 재귀 호출
            long nextPath = (dp[ny][nx] == -1) ? dfs(n, k, idx + 1, ny, nx) : dp[ny][nx];
            
            // 경로가 유효하다면(MAX_VALUE가 아님) 최소값 갱신
            if(nextPath != Long.MAX_VALUE) {
                res = Math.min(res, dist + nextPath);
            }
        }

        // 계산된 최소 거리를 DP 테이블에 기록 (Memoization)
        return dp[cy][cx] = res;
    }

    /**
     * 맨해튼 거리 계산 공식: |y1 - y2| + |x1 - x2|
     */
    private static long getDistance(int cy, int cx, int ny, int nx) {
        return (long)Math.abs(cy - ny) + Math.abs(cx - nx);
    }

    /**
     * 정수 입력을 빠르게 받기 위한 커스텀 ReadInt 메서드
     */
    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}