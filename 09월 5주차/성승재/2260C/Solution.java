import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();

        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            int sum = x + y;
            int maxX = 0;

            // sum의 서브마스크 중 x 이하인 최댓값을 구성합니다.
            for (int bit = 29; bit >= 0; bit--) {
                int value = 1 << bit;

                if ((sum & value) != 0 && maxX + value <= x) {
                    maxX += value;
                }
            }

            answer.append(sum)
                  .append(' ')
                  .append(x - maxX)
                  .append('\n');
        }

        System.out.print(answer);
    }
}
