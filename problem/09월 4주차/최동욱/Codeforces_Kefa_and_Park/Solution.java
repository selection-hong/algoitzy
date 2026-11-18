import java.util.*;
import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        int n = readInt();
        int m = readInt();

        List<Integer>[] list = new ArrayList[n + 1];
        int[] arr = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
            arr[i] = readInt();
        }

        for(int i = 0; i < n - 1; i++) {
            int u = readInt();
            int v = readInt();

            list[u].add(v);
            list[v].add(u);
        }

        System.out.println(dfs(list, arr, m, 1, 0, 0));
    }

    private static int dfs(
        List<Integer>[] list,
        int[] arr, 
        int m, 
        int node, 
        int p,
        int cats
    ) {
        cats = arr[node] == 1 ? cats + 1 : 0;
        if(cats > m) return 0;
        
        int cnt = 0;
        boolean leaf = true;
        
        for(int next : list[node]) {
            if(next == p) continue;
            
            cnt += dfs(list, arr, m, next, node, cats);
            leaf = false;
        }
        return leaf ? 1 : cnt;
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