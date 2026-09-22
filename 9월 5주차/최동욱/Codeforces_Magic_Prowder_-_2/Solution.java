import java.io.*;

class Main {

    final static int INF = 2_000_000_000;
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        int n = readInt();
        long k = readInt();

        long[][] arr = new long[n][2];
        inputArray(arr, n, 0);
        inputArray(arr, n, 1);

        System.out.println(binarySearch(arr, n, k));
    }

    private static void inputArray(long[][] arr, int n, int idx) throws IOException {
        for(int i = 0; i < n; i++) {
            arr[i][idx] = readInt();
        }
    }

    private static long binarySearch(long[][] arr, int n, long k) {
        long left = 0, right = INF;
        
        while(left <= right) {
            long mid = (left + right) >> 1;
            long cost = 0;
            for(int i = 0; i < n; i++) {
                long need = arr[i][0] * mid - arr[i][1];
                if(need > 0) {
                    cost += need;
                    if(cost > k) break;
                }
            }

            if(cost <= k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
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