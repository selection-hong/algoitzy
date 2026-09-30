import java.io.*;
import java.util.*;
public class BOJ_14502 {
    static final int K = 3;
    static int N, M, ans;
    static int[][] map, use, temp;
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static ArrayList<Integer> zero = new ArrayList<>();
    static ArrayList<Integer> two = new ArrayList<>();
    static Queue<Integer> queue = new ArrayDeque<>();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        ans = 0;
        map = new int[N][M];
        use = new int[K][2];
        temp = new int[N][M];
        zero.clear();
        two.clear();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) two.add(i*M+j);
                else if (map[i][j] == 0) zero.add(i*M+j);
            }
        }
        com(0, 0);
        System.out.println(ans);
    }
    public static void com(int idx, int cnt) {
        if (cnt == K) {
            bfs();
            return;
        }
        for (int i = idx; i < zero.size(); i++) {
            int get = zero.get(i);
            use[cnt][0] = get / M;
            use[cnt][1] = get % M;
            com(i + 1, cnt + 1);
        }
    }
    private static void bfs() {
        for (int i = 0; i < N; i++) {
            temp[i] = Arrays.copyOf(map[i], M);
        }
        for (int i = 0; i < K; i++) {
            temp[use[i][0]][use[i][1]] = 1;
        }
        queue.clear();
        for (Integer i : two) {
            queue.add(i);
        }
        int cnt = zero.size() - K;
        while (!queue.isEmpty()) {
            int pop = queue.poll();
            for (int[] d : dir) {
                int rd = pop / M + d[0];
                int cd = pop % M + d[1];
                if (rd < 0 || rd >= N || cd < 0 || cd >= M) continue;
                if (temp[rd][cd] == 0) {
                    temp[rd][cd] = 2;
                    queue.add(rd * M + cd);
                    cnt--;
                }
            }
        }
        ans = Math.max(ans, cnt);
    }
}