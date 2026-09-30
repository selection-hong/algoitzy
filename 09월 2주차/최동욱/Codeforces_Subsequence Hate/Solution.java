import java.io.*;

class Main {

    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();
        
        StringBuilder sb = new StringBuilder();

        int t = readInt();
        int[] runs = new int[1000];

        while(t-- > 0) {
            int num = '2';
            int top = -1;

            while(c <= ' ') c = System.in.read();
            while(c == '0' || c == '1') {
                if(num == c) runs[top]++;
                else {
                    runs[++top] = 1;
                    num = c;
                }

                c = System.in.read();
            }

            sb.append(solve(runs, top)).append('\n');
        }

        System.out.println(sb);
    }

    private static int solve(int[] arr, int n) {
        if(n <= 1) return 0;

        int even = 0, odd = 0;
        for(int i = 0; i <= n; i++) {
            if((i & 1) == 0) even += arr[i];
            else odd += arr[i];
        }

        int evenCnt = scanCost(arr, n, even, -1);
        int oddCnt = scanCost(arr, n, odd, 1);
        return evenCnt <= oddCnt ? evenCnt : oddCnt;
    }

    private static int scanCost(int[] arr, int n, int s, int diff) {
        int cnt = s;
        for(int i = 0; i <= n; i++) {
            s += arr[i] * diff;
            diff = -diff;
            if(cnt > s) cnt = s;
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