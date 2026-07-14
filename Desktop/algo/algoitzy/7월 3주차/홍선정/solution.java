class Solution {
    boolean[] v;  // 방문 체크

    public int solution(int n, int[][] c) {
        v = new boolean[n];
        int cnt = 0;

        for (int i = 0; i < n; i++) {
            if (!v[i]) {  
                dfs(i, n, c);
                cnt++;        // 네트워크 1개 발견
            }
        }
        return cnt;
    }

    void dfs(int i, int n, int[][] c) {
        v[i] = true;
        for (int j = 0; j < n; j++) {
            if (c[i][j] == 1 && !v[j]) { // 연결됐고 아직 안 갔으면
                dfs(j, n, c);
            }
        }
    }
}