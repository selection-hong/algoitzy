import java.util.*;
import java.io.*;

class Main {

    static int c;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int t = readInt();
        while(t-- > 0) {
            int n = readInt();

            List<Integer>[] list = new ArrayList[n + 1];
            int[][] arr = new int[n][3];
            int[] parents = new int[n + 1];
            int[] indegree = new int[n + 1];

            for(int i = 1; i <= n; i++) {
                list[i] = new ArrayList<>();
                arr[i - 1][0] = i;
                parents[i] = i;
            }

            inputArray(arr, 1, n);
            inputArray(arr, 2, n);

            Arrays.sort(arr, (a, b) -> {
                return b[2] - a[2];
            });

            dsu(list, arr, parents, indegree);

            bfs(sb, list, indegree, n);
            sb.append('\n');
        }

        System.out.println(sb);
    }

    private static void inputArray(int[][] arr, int idx, int n) throws IOException {
        for(int i = 0; i < n; i++) {
            arr[i][idx] = readInt();
        }
    }

    private static void dsu(List<Integer>[] list, int[][] arr, int[] parents, int[] indegree) {
        for(int[] cur : arr) {
            int pA = find(parents, cur[0]);
            int pB = find(parents, cur[1]);

            if(pA == pB) continue;

            list[cur[0]].add(cur[1]);
            indegree[cur[1]]++;

            parents[pB] = pA;
        }
    }

    private static int find(int[] parents, int p) {
        if(parents[p] == p) return p;
        return parents[p] = find(parents, parents[p]);
    }

    private static void bfs(StringBuilder sb, List<Integer>[] list, int[] indegree, int n) {
        int[] que = new int[n + 1];
        int head = 0, tail = 0;

        for(int i = 1; i <= n; i++) {
            if(indegree[i] == 0) {
                que[tail++] = i;
            }
        }

        while(head < tail) {
            int cur = que[head++];
            sb.append(cur).append(' ');

            for(int next : list[cur]) {
                if(--indegree[next] == 0) {
                    que[tail++] = next;
                }
            }
        }
    }

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