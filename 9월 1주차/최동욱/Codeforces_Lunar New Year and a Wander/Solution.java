import java.util.*;
import java.io.*;

class Main {

    static List<Integer>[] list;
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        int n = readInt();
        int m = readInt();

        list = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }

        while(m-- > 0) {
            int u = readInt();
            int v = readInt();

            list[u].add(v);
            list[v].add(u);
        }

        boolean[] visited = new boolean[n + 1];
        visited[1] = true;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);

        StringBuilder sb = new StringBuilder();
        while(!pq.isEmpty()) {
            int cur = pq.poll();
            sb.append(cur).append(' ');
            for(int next : list[cur]) {
                if(!visited[next]) {
                    pq.add(next);
                    visited[next] = true;
                }
            }
        }

        System.out.println(sb);
    }

    private static int readInt() throws IOException {
        while(c <= ' ') c = System.in.read();
        int n = 0;
        while(c >= '0' &&  c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}