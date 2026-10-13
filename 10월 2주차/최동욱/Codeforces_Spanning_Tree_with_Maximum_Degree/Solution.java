import java.util.*;
import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        int n = readInt();
        int m = readInt();

        ArrayList<Integer>[] list = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++) {
            int v = readInt();
            int u = readInt();

            list[v].add(u);
            list[u].add(v);
        }

        int root = searchMaxEdges(list, n);
        System.out.println(solve(list, n, root));
    }

    private static int searchMaxEdges(ArrayList<Integer>[] list, int n) {
        int edge = 0, cnt = 0;
        for(int i = 1; i <= n; i++) {
            if(cnt < list[i].size()) {
                edge = i;
                cnt = list[i].size();
            }
        }
        return edge;
    }

    private static String solve(ArrayList<Integer>[] list, int n, int root) {
        StringBuilder sb = new StringBuilder();
        
        boolean[] visited = new boolean[n + 1];
        visited[root] = true;

        int[] que = new int[n];
        int left = 0, right = 0;
        que[right++] = root;

        while(left < right) {
            int cur = que[left++];

            for(int next : list[cur]) {
                if(visited[next]) continue;

                visited[next] = true;
                que[right++] = next;

                sb.append(cur).append(' ').append(next).append('\n');
            }
        }

        return sb.toString();
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