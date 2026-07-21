import java.io.*;
import java.util.*;

public class Solution {

	// 전역 변수
    final static int BIT_SHIFT = 7;
    final static int SIZE = 100;	// 맛있는 정도 1 ~ 100
    final static int[][] DICT = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    static int[][] map = new int[SIZE][SIZE];	// 치즈가 있는 칸
    static int[][] visited = new int[SIZE][SIZE]; // 방문 체크
    static int[] cheezeCnt = new int[SIZE + 1]; // 치즈 분류
    static int[] dequeY = new int[SIZE * SIZE];	// custom Deque
    static int[] dequeX = new int[SIZE * SIZE];	// custom Deque
    static int c, n;    
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();
        int T = readInt();    
        
        for(int t = 1; t <= T; t++) {
            init();	// 값 초기화
            inputArray(n); // 맵 정보 저장
            
            // 문제 풀기 - simulation
            sb.append('#').append(t).append(' ').append(count()).append('\n');
        }
        System.out.print(sb);
    }
    
    // 변수 초기화 
    private static void init() throws IOException {
        n = readInt();
        Arrays.fill(cheezeCnt, 0); // 치즈 분류 초기화
        for(int i = 0; i < SIZE; i++) {
            Arrays.fill(visited[i], 0); // 방문 배열 초기화    
        }
    }
    
    // map 정보 저장
    private static void inputArray(int n) throws IOException {
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                int num = readInt();	// 맛있는 정도
                map[y][x] = num;
                cheezeCnt[num]++;	// 맛있는 정도에 따른 치즈 분류
            }
        }
    }
    
    // simulation
    private static int count() {
        int cnt = 1, total = n * n;
        
        // 맛있는 정도는 1 ~ 100, 치즈가 모두 사라졌다면 break
        for(int i = 1; i <= 100 && total > 0; i++) {
            if(cheezeCnt[i] == 0) continue; // 현재 먹을 수 있는 치즈가 없는 경우 
            total -= cheezeCnt[i];	// 먹은 치즈 개수 제거
            cnt = Math.max(cnt, countSize(i));	// 덩어리 수 구하기
        }
        return cnt;
    }
    
    // 덩어리 수 측정
    private static int countSize(int marking) {
        int cnt = 0;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                if(map[y][x] > marking && visited[y][x] != marking) {
                    bfs(y, x, marking);
                    cnt++;
                }
            }
        }
        return cnt;
    }
    
    // 덩어리 확인
    private static void bfs(int y, int x, int mark) {
        int head = 0, tail = 0;
        visited[y][x] = mark;
        dequeY[tail] = y;
        dequeX[tail] = x;
        tail++;        

        while(head < tail) {
            int cy = dequeY[head];
            int cx = dequeX[head];
            head++;

            for(int[] d : DICT) {
                int ny = cy + d[0];
                int nx = cx + d[1];
                
                if(check(ny, nx) && map[ny][nx] > mark && visited[ny][nx] != mark) {
                    visited[ny][nx] = mark;
                    dequeY[tail] = ny;
                    dequeX[tail] = nx;
                    tail++;
                }
            }
        }
    }
    
    // 범위 체크
    private static boolean check(int y, int x) {
        return y >= 0 && y < n && x >= 0 && x < n;
    }

    // fastIO
    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c - '0');
            c = System.in.read();
        }
        return n;
    }
}
