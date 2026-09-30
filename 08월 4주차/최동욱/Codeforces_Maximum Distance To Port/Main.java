import java.util.*;
import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int n = readInt();
        int m = readInt();
        int k = readInt();

        ArrayList<Integer>[] list = new ArrayList[n + 1];
        int[] items = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
            items[i] = readInt();
        }

        while(m-- > 0) {
            int u = readInt();
            int v = readInt();

            list[u].add(v);
            list[v].add(u);
        }

        Queue<int[]> que = new ArrayDeque<>();
        que.add(new int[]{1, 0});
        
        boolean[] visited = new boolean[n + 1];
        visited[1] = true;

        int[] res = new int[k + 1];
        while(!que.isEmpty()) {
            int[] cur = que.poll();
            int from = cur[0];
            int cnt = cur[1];

            for(int next : list[from]) {
                if(!visited[next]) {
                    que.add(new int[]{next, cnt + 1});
                    visited[next] = true;
                    res[items[next]] = cnt + 1;
                }
            }
        }

        for(int i = 1; i <= k; i++) {
            sb.append(res[i]).append(' ');
        }
        System.out.println(sb);
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