import java.util.*;
import java.io.*;

class Main {

    static int res = -1;
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        int n = readInt();
        
        List<Integer>[] list = new ArrayList[n + 1];
        for(int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }

        for(int i = 0; i < n - 1; i++) {
            int u = readInt();
            int v = readInt();

            list[u].add(v);
            list[v].add(u);
        }

        if((n & 1) == 0) {
            dfs(list, 1, 0);   
        }
        System.out.println(res);
    }

    private static int dfs(List<Integer>[] list, int node, int p) {
        int cnt = 1;

        for(int child : list[node]) {
            if(child == p) continue;
            cnt += dfs(list, child, node);
        }

        if((cnt & 1) == 0) {
            res++;
            cnt = 0;
        }

        return cnt;
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