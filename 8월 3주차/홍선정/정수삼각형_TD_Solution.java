package 

8월 3주차.홍선정;

public class 정수삼각형_TD_Solution {

    /* 트리 같지만, 같은 칸에 경로가 합쳐지는 계층형 DAG
    최대 높이 50 -> Node 트리 만들거나 모든 경로 탐색 시 경우의 수 너무 커짐 
    -> [행][열] tri 노드 배열 그대로 활용하는 게 best 일수도..?
     */

 /* top-down 방식으로 푸는 경우 (재귀+memoization)
    꼭대기에서 -> 바닥까지 계산 후 최댓값 비교, 재탐색 
    & 각 행의 양 끝 테두리 예외 처리 필요..
    해당 문제 top-down 반복문만으로 구현 가능.
    재귀 설계부터 열받네요 
    재귀로 풀어보세요 여러분
     */
    public int solution(int[][] tri) {
        // 두번째 줄부터 시작
        for (int i = 1; i < tri.length; i++) {
            for (int j = 0; j <= i; j++) {

                if (j == 0) {
                    // 1. 왼쪽 가장자리 : 바로 윗줄의 값만 더하기 가능.
                    tri[i][j] += tri[i - 1][0];
                } else if (j == i) {
                    // 2. 오른쪽 가장자리: 왼쪽 윗 값만 더하기 가능
                    tri[i][j] += tri[i - 1][j - 1];
                } else {
                    // 3. 중간 부분: 왼쪽 위와 바로 위 중 큰 값 더하기.
                    tri[i][j] += Math.max(tri[i - 1][j - 1], tri[i - 1][j]);
                }

            }
        }
        // 바닥층 값 중 최댓값 출력.
        int ans = 0;
        int lastR = tri.length - 1;
        for (int i = 0; i < tri[lastR].length; i++) {
            ans = Math.max(ans, tri[lastR][i]);
        }

        return ans;
    }
}
