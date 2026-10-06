import java.util.*;
import java.io.*;

/*
    약수가 3개다 = 1과 자기자신을 빼면 제곱했을때 자신이 되는 소수가 존재하는 수라는 뜻.
    반대로 얘기하면 소수의 제곱을 한 수가 T-Prime이라는 뜻

    어떻게 판정하나?
    각 숫자가 10^12니까 루트씌우면 10^6까지의 소수들을 구해놓고, 입력으로 들어오는 수를 루트 씌워서 계산하기
 */

public class Solution {

    static final int MAX = 1_000_000;
    static boolean[] isPrime = new boolean[MAX + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 에라토스테네스의 체
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        for (int i = 2; i * i <= MAX; i++) {
            if (!isPrime[i]) continue;

            for (int j = i * i; j <= MAX; j += i) {
                isPrime[j] = false;
            }
        }

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            long num = Long.parseLong(st.nextToken());

            long root = (long) Math.sqrt(num);

            if (root * root == num && isPrime[(int) root]) {
                sb.append("YES\n");
            } else {
                sb.append("NO\n");
            }
        }

        System.out.print(sb);
    }
}
