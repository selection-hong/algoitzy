import java.util.*;

class Solution {
    
    private final int BOARD = 11;
    int[] answer;
    int maxScore = 0;
    
    public int[] solution(int n, int[] apeach) {
        answer = new int[BOARD];
        
        int[] ryan = new int[BOARD];
        backtracking(n, 0, ryan, apeach);
        
        return maxScore > 0 ? answer : new int[]{-1};
    }
    
    private void backtracking(int n, int idx, int[] ryan, int[] apeach) {
        if(n == 0) {
            checkScoreDifference(ryan, apeach);
            return;
        }
        for(int i = idx; i < BOARD; i++) {
            if(i == BOARD - 1) {
                ryan[i] = n;
                backtracking(0, i + 1, ryan, apeach);
                ryan[i] = 0;
            }
            else if(n - (apeach[i] + 1) >= 0) {
                int temp = n - (apeach[i] + 1);
                ryan[i] = apeach[i] + 1;
                backtracking(temp, i + 1, ryan, apeach);
                ryan[i] = 0;
            }
        }
    }
    
    private void checkScoreDifference(int[] ryan, int[] apeach) {
        int score = getScoreDifference(ryan, apeach);
        if(score > maxScore) {
            maxScore = score;
            answer = Arrays.copyOf(ryan, BOARD);
        }
        else if(score == maxScore && isPreferred(ryan, answer)) {
            answer = Arrays.copyOf(ryan, BOARD);
        }
    }
    
    private int getScoreDifference(int[] ryan, int[] apeach) {
        int ryanScore = 0;
        int apeachScore = 0;
        for(int i = 0; i < BOARD; i++) {
            if(ryan[i] == 0 && apeach[i] == 0) continue;
            int score = (BOARD - 1) - i;
            if(ryan[i] > apeach[i]) {
                ryanScore += score;
            }
            else {
                apeachScore += score;
            }
        }
        
        return ryanScore - apeachScore;
    }
    
    private boolean isPreferred(int[] ryan, int[] answer) {
        for(int i = BOARD - 1; i >= 0 ; i--) {
            if(ryan[i] > answer[i]) {
                return true;
            }
            else if(ryan[i] < answer[i]) return false;
        }
        return false;
    }
}