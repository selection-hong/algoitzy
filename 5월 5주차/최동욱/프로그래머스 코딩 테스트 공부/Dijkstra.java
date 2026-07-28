import java.util.*;

class Solution {
    
    private static class Problem implements Comparable<Problem> {
        int alp, cop, time;
        
        public Problem(int alp, int cop, int time) {
            this.alp = alp;
            this.cop = cop;
            this.time = time;
        }
        
        @Override
        public int compareTo(Problem p) {
            return this.time - p.time;
        }
    }
    
    static final int MAX_SIZE = 150;
    static final int INF = 300;
    
    public int solution(int alp, int cop, int[][] problems) {
        int[][] dp = new int[MAX_SIZE + 1][MAX_SIZE + 1];
        init(dp, MAX_SIZE);
        
        int targetA = 0, targetC = 0;
        for(int[] problem : problems) {
            if(targetA < problem[0]) targetA = problem[0];
            if(targetC < problem[1]) targetC = problem[1];
        }
        
        PriorityQueue<Problem> pq = new PriorityQueue<>();
        pq.add(new Problem(alp, cop, 0));
        dp[alp][cop] = 0;
        
        while(!pq.isEmpty()) {
            Problem prob = pq.poll();
            int cAlp = prob.alp;
            int cCop = prob.cop;
            int cTime = prob.time;            
            
            if(cAlp >= targetA && cCop >= targetC) return cTime;
            if(cTime > dp[cAlp][cCop]) continue;
            
            int temp = cAlp + 1 > MAX_SIZE ? MAX_SIZE : cAlp + 1;
            if(dp[temp][cCop] > cTime + 1) {
                pq.add(new Problem(temp, cCop, cTime + 1));
                dp[temp][cCop] = cTime + 1;
            }
            
            temp = cCop + 1 > MAX_SIZE ? MAX_SIZE : cCop + 1;
            if(dp[cAlp][temp] > cTime + 1) {
                pq.add(new Problem(cAlp, temp, cTime + 1));
                dp[cAlp][temp] = cTime + 1;
            }
            
            for(int[] problem : problems) {
                if(cAlp < problem[0] || cCop < problem[1]) continue;
                int nextAlp = cAlp + problem[2] > MAX_SIZE ? MAX_SIZE : cAlp + problem[2];
                int nextCop = cCop + problem[3] > MAX_SIZE ? MAX_SIZE : cCop + problem[3];
                int nextTime = cTime + problem[4];
                
                if(dp[nextAlp][nextCop] > nextTime) {
                    pq.add(new Problem(nextAlp, nextCop, nextTime));
                    dp[nextAlp][nextCop] = nextTime;
                }
            }
        }
        
        return INF;
    }
    
    private static void init(int[][] dp, int size) {
        for(int i = 0; i <= size; i++) {
            for(int j = 0; j <= size; j++) {
                dp[i][j] = INF + 5;
            }
        }
    }
}