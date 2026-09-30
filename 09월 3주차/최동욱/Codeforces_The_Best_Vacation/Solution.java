import java.io.*;

class Main {
    
    static int c;
    
    public static void main(String[] args) throws IOException {
        c = System.in.read();

        int n = readInt();
        long m = readLong();

        int[] arr = new int[n * 2];
        long[] hug = new long[n * 2];

        for(int i = 0; i < n; i++) {
            int d = readInt();
            long val = (long) d * (d + 1) / 2;

            arr[i] = arr[i + n] = d;
            hug[i] = hug[i + n] = val;
        }

        int right = 0, totalDate = 2 * n;
        long hugSum = 0, daySum = 0, res = 0;

        for(int left = 0; left < n; left++) {
            while(right < totalDate && daySum < m) {
                daySum += arr[right];
                hugSum += hug[right];
                right++;
            }

            if(daySum == m) {
                if(res < hugSum) {
                    res = hugSum;
                }
            } else {
                while(daySum - arr[left] >= m) {
                    daySum -= arr[left];
                    hugSum -= hug[left];
                    left++;
                }
                
                long temp = daySum - m;
                long tempVal = (long) temp * (temp + 1) / 2;

                if(res < hugSum - tempVal) {
                    res = hugSum - tempVal;
                }
            }

            daySum -= arr[left];
            hugSum -= hug[left];
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

    private static long readLong() throws IOException {
        while(c <= ' ') c = System.in.read();
        long n = 0;
        while(c >= '0' && c <= '9') {
            n = (n << 3) + (n << 1) + (c & 15);
            c = System.in.read();
        }
        return n;
    }
}