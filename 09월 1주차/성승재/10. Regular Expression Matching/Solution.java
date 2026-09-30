class Solution {
    public boolean isMatch(String s, String p) {

        int m = s.length();
        int n = p.length();

        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;

        // 빈 문자열과 매칭 가능한 패턴 처리
        // a*, a*b*, a*b*c* 등
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);

                // 일반 문자 또는 .
                if (pc == sc || pc == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                }

                // *
                else if (pc == '*') {

                    // 앞 문자 + * 를 0번 사용
                    dp[i][j] = dp[i][j - 2];

                    char prev = p.charAt(j - 2);

                    // *가 현재 문자를 하나 먹을 수 있음
                    if (prev == sc || prev == '.') {
                        dp[i][j] |= dp[i - 1][j];
                    }
                }
            }
        }

        return dp[m][n];
    }
}
