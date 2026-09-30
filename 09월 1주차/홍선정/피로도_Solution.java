package 

9월 1주차.홍선정;

class 피로도_Solution {

    //dfs+백트래킹 or 순열?
    int maxC = 0;

    public int solution(int k, int[][] dungeons) {
        boolean[] v = new boolean[dungeons.length];

        dfs(k, dungeons, v, 0);

        return maxC;
    }

    public void dfs(int f, int[][] d, boolean[] v, int c) {
        maxC = Math.max(maxC, c);

        for (int i = 0; i < d.length; i++) {
            //방문하지 않고, 피로도가 최소피로도보다 크거나 같으면
            //이상인지 초과인 지 확인!!
            if (!v[i] && f >= d[i][0]) {
                v[i] = true;
                dfs(f - d[i][1], d, v, c + 1);
                v[i] = false;
            }
        }

    }

}


class Solution {
    int dfs(int k, int[][] dungeons) {
        int cnt = 0;
        for(int[] d : dungeons) {
            int a = d[0], b = d[1];
            if(a <= k) {
                d[0] = 9999;
                cnt = Math.max(1 + dfs(k - b, dungeons), cnt);
                d[0] = a;
            }
        }
        return cnt;
    }
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
        return dfs(k, dungeons);
    }
}

