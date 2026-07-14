import java.util.*;

class Solution {
    static final int INF = 1_000_000_000;

    public int solution(int alp, int cop, int[][] problems) {
        int targetAlp = 0;
        int targetCop = 0;

        for (int[] p : problems) {
            targetAlp = Math.max(targetAlp, p[0]);
            targetCop = Math.max(targetCop, p[1]);
        }

        alp = Math.min(alp, targetAlp);
        cop = Math.min(cop, targetCop);

        int[][] dp = new int[targetAlp + 1][targetCop + 1];

        for (int i = 0; i <= targetAlp; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[alp][cop] = 0;

        for (int a = alp; a <= targetAlp; a++) {
            for (int c = cop; c <= targetCop; c++) {
                if (dp[a][c] == INF) continue;

                if (a + 1 <= targetAlp) {
                    dp[a + 1][c] = Math.min(dp[a + 1][c], dp[a][c] + 1);
                }

                if (c + 1 <= targetCop) {
                    dp[a][c + 1] = Math.min(dp[a][c + 1], dp[a][c] + 1);
                }

                for (int[] p : problems) {
                    int alpReq = p[0];
                    int copReq = p[1];
                    int alpRwd = p[2];
                    int copRwd = p[3];
                    int cost = p[4];

                    if (a >= alpReq && c >= copReq) {
                        int nextA = Math.min(targetAlp, a + alpRwd);
                        int nextC = Math.min(targetCop, c + copRwd);

                        dp[nextA][nextC] = Math.min(
                            dp[nextA][nextC],
                            dp[a][c] + cost
                        );
                    }
                }
            }
        }

        return dp[targetAlp][targetCop];
    }
}
