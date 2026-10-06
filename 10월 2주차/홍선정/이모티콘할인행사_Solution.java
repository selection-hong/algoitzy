package 

10월 1주차.홍선정;

class 이모티콘할인행사_Solution {

    //프로그래머스 이모티콘 할인행사
    //이모티콘 개수 최대 7개
    //이모티콘 할인율 10, 20, 30, 40%
    //경우의 수 4^7 = 16384
    //->완탐가능 - dfs로 조합 만들고 조건에 따라 최적값 갱신

    //maxP : 최대 이모티콘 플러스 가입자 수
    //maxS : 최대 이모티콘 판매액
    int maxP = 0;
    int maxS = 0;
    int[] dis = {10, 20, 30, 40};


    //users[i][0] : 이모티콘을 구매하기 위해 필요한 최소 할인율
    //users[i][1] : 이모티콘 구매를 위해 지불할 수
    //emoticons[i] : 이모티콘 가격
    public int[] solution(int[][] users, int[] emoticons) {
        dfs(users, emoticons, 0, new int[emoticons.length]);
        return new int[]{maxP, maxS};

    }

    //dfs로 할인율 조합
    public void dfs(int[][] users, int[] emoticons, int idx, int[] dis) {
        if (idx == emoticons.length) {
            calculate(users, emoticons, dis);
            return;

        }
        for(int d : dis) {
            dis[idx] = d;
            dfs(users, emoticons, idx + 1, dis);
        }
    }

    private void calculate(int[][] users, int[] emoticons, int[] dis) {
        int p = 0;
        int s = 0;
        for(int[] user : users) {
            int sum = 0;
            //사용자가 구매할 이모티콘 가격 합계 계산
            //사용자가 원하는 할인율보다 크거나 같으면 구매
            for(int i = 0; i < emoticons.length; i++) {
                if(user[0] <= dis[i]) {
                    //할인율 적용 후 가격
                    sum += emoticons[i] * (100 - dis[i]) / 100;
                }
            }
            //사용자가 지불할 수 있는 금액보다 크거나 같으면 이모티콘 플러스 가입
            if(sum >= user[1]) {
                p++;
            } else {
                s += sum;
            }
        }
        //최대값 갱신
        //이모티콘 플러스 가입자 수가 최대인 경우
        //이모티콘 플러스 가입자 수가 같으면 이모티콘 판매액이 최대인 경우
        if(p > maxP) {
            maxP = p;
            maxS = s;
        } else if(p == maxP) {
            maxS = Math.max(maxS, s);
        }
    }
}
