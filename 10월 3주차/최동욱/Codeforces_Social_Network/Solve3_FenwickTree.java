import java.util.*;
import java.io.*;

public class Main {

    static int c;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        c = System.in.read();

        int n = readInt();
        int m = readInt();

        int[] parents = new int[n + 1];
        int[] cnt = new int[n + 1];

        int[] countBIT = new int[n + 1];
        int[] sumBIT = new int[n + 1];

        for(int i = 1; i <= n; i++) {
            parents[i] = i;
            cnt[i] = 1;
        }

        update(countBIT, 1, n);
        update(sumBIT, 1, n);

        int conn = 1, group = n;

        while(m-- > 0) {
            int x = readInt();
            int y = readInt();

            int pX = find(parents, x);
            int pY = find(parents, y);

            if(pX != pY) {
                int sizeX = cnt[pX];
                int sizeY = cnt[pY];
                int size = sizeX + sizeY;

                update(countBIT, sizeX, -1);
                update(countBIT, sizeY, -1);
                update(countBIT, size, 1);

                update(sumBIT, sizeX, -sizeX);
                update(sumBIT, sizeY, -sizeY);
                update(sumBIT, size, size);

                parents[pX] = pY;
                cnt[pY] = size;

                group--;
            } else {
                conn++;
            }

            int target = Math.min(group, conn);

            sb.append(countConnect(countBIT, sumBIT, n, group, target))
              .append('\n');
        }

        System.out.println(sb);
    }

    private static int find(int[] parents, int p) {
        if(parents[p] == p) return p;
        else return parents[p] = find(parents, parents[p]);
    }

    private static int countConnect(
        int[] countBIT,
        int[] sumBIT,
        int n,
        int group,
        int target
    ) {
        int exclude = group - target;

        if(exclude == 0) return n - 1;

        int idx = lowerBound(countBIT, exclude);

        int beforeCount = query(countBIT, idx - 1);
        int beforeSum = query(sumBIT, idx - 1);

        int excludedSum = beforeSum + (exclude - beforeCount) * idx;

        return n - excludedSum - 1;
    }

    private static void update(int[] bit, int idx, int value) {
        while(idx < bit.length) {
            bit[idx] += value;
            idx += idx & -idx;
        }
    }

    private static int query(int[] bit, int idx) {
        int sum = 0;

        while(idx > 0) {
            sum += bit[idx];
            idx -= idx & -idx;
        }

        return sum;
    }

    private static int lowerBound(int[] bit, int target) {
        int n = bit.length - 1;
        int idx = 0;
        int step = Integer.highestOneBit(n);

        while(step > 0) {
            int next = idx + step;

            if(next <= n && bit[next] < target) {
                idx = next;
                target -= bit[next];
            }

            step >>= 1;
        }

        return idx + 1;
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
