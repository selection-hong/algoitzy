package 

8월 3주차.홍선정;


public class 정수삼각형_BU_Solution {

    /* 트리 같지만, 같은 칸에 경로가 합쳐지는 계층형 DAG
    최대 높이 50 -> Node 트리 만들거나 모든 경로 탐색 시 경우의 수 너무 커짐 
    -> [행][열] triangle 노드 배열 그대로 활용하는 게 best 일수도..?
     */

 /* bottom-up 방식으로 푸는 경우 (반복문+tabulation)
    ->행 : 바닥 윗줄부터 ---> 꼭대기로 올라가는 경로 방향
    ->열 : 방향 상관 x

     */
    public int solution(int[][] tri) {
        for (int i = tri.length - 2; i >= 0; i--) {
            //열 range = 각 행의 길이까지.
            for (int j = 0; j < tri[i].length; j++) {
                //누적으로 연산 1회만 하기
                tri[i][j] += Math.max(tri[i + 1][j], tri[i + 1][j + 1]);
            }

        }
        return tri[0][0];
    }
}
