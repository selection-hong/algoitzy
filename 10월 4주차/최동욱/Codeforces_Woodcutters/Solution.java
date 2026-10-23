import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();
        int n = readInt();
        
        int[][] tree = new int[2][n];

        for(int i = 0; i < n; i++) {
            tree[0][i] = readInt();
            tree[1][i] = readInt();
        }

        int res = n == 1 ? 1 : 2;
        for(int i = 1; i < n - 1; i++) {
            int x = tree[0][i];
            int h = tree[1][i];

            if(x - h > tree[0][i - 1]) {
                res++;
            } else if(x + h < tree[0][i + 1]) {
                tree[0][i] = x + h;
                res++;
            }
        }
        
        System.out.println(res);
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