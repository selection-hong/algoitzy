package 

9월 4주차.홍선정;

public class 양궁대회_Solution {

    int maxD = 0;
    int[] apeach;
    //ans 배열의 초깃값이 0이 아닌 -1이어야 하는 이유는, 
    // 문제에서 "만약 라이언이 우승할 수 없는 경우는 -1로 return 하세요"라는 조건 때문에
    int[] ans = {-1};

    public int[] solution(int n, int[] info) {

        apeach = info;
        dfs(0, n, new int[11]);
        return ans;
    }

    public void dfs(int idx, int n, int[] ryan) {
        if (idx == 11 || n == 0) {
            ryan[10] += n;
            int score = score(ryan);
            if (score > maxD) {
                maxD = score;
                ans = ryan.clone();
            } else if (score == maxD) {
                for (int i = 10; i >= 0; i--) {
                    if (ryan[i] > ans[i]) {
                        ans = ryan.clone();
                        break;
                    } else if (ryan[i] < ans[i]) {
                        break;
                    }
                }
            }
            ryan[10] -= n;
            return;
        }

        //어피치보다 1점 더 많이 맞추는 경우
        if (n > apeach[idx]) {
            ryan[idx] = apeach[idx] + 1;
            dfs(idx + 1, n - ryan[idx], ryan);
            ryan[idx] = 0;
        }

        //어피치보다 1점 더 많이 맞추지 않는 경우
        dfs(idx + 1, n, ryan);
    }

    //점수 계산
    public int score(int[] ryan) {
        int ryanScore = 0;
        int apeachScore = 0;
        for (int i = 0; i < 11; i++) {
            if (ryan[i] == 0 && apeach[i] == 0) {
                continue;
            }
            if (ryan[i] > apeach[i]) {
                ryanScore += 10 - i;
            } else {

                apeachScore += 10 - i;
            }
            //
            return ryanScore - apeachScore;
        }

    }
}
