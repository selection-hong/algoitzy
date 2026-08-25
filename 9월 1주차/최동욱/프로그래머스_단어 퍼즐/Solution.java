class Solution {
    
    final static int INF = 20_005;
    
    public int solution(String[] strs, String t) {
        int n = t.length();
        int[] dp = new int[n + 1];
        for(int i = 0; i <= n; i++) dp[i] = INF;
        dp[0] = 0;
        
        for(int s = 0; s < n; s++) {
            if(dp[s] == INF) continue;
            
            for(String str : strs) {
                int next = str.length() + s;
                if(next <= n && check(str, t, s)) {
                    dp[next] = Math.min(dp[next], dp[s] + 1);
                }
            }
        }
        
        return dp[n] == INF ? -1 : dp[n];
    }
    
    private boolean check(String word, String str, int idx) {
        for(int i = 0; i < word.length(); i++) {
            if(word.charAt(i) != str.charAt(i + idx)) return false;
        }
        return true;
    }
}