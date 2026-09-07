class Solution {
    
    int res = 10;
    
    public int solution(int n, int[] weak, int[] dist) {
        boolean[] visited = new boolean[dist.length];

        int wLen = weak.length;
        int[] arr = new int[wLen * 2];
        for(int i = 0; i < wLen; i++) {
            arr[i] = weak[i];
            arr[i + wLen] = weak[i] + n;
        }
        
        for(int i = 0; i < wLen; i++) {
            dfs(arr, dist, i, 0, wLen, visited);
        }
        
        return res == 10 ? -1 : res;
    }
    
    private void dfs(
        int[] weak,         // 취약점 위치 배열
        int[] dist,         // 친구의 이동 거리 배열
        int s,              // 외벽 시작 점
        int dCnt,           // 보낸 친구 수
        int wRemain,        // 남은 취약점
        boolean[] visited   // 보낸 친구 체크용
    ) {
        // 모든 취약점을 해결한 경우
        if(wRemain == 0) {
            if(res > dCnt) res = dCnt;
            return;
        }

        // 가지치기: 모든 친구를 보냈거나, 현재 구한 최소값보다 큰 경우
        if(dCnt == dist.length || res <= dCnt) {
            return;
        }
        
        for(int i = 0; i < dist.length; i++) {
            if(visited[i]) continue;
            
            int idx = s, d = dist[i], remain = wRemain - 1;
            while(
                idx < weak.length - 1 
                  && remain > 0 
                  && weak[idx + 1] - weak[idx] <= d
            ) {
                d -= weak[idx + 1] - weak[idx];
                idx++;
                remain--;
            }
            
            visited[i] = true;
            dfs(weak, dist, idx + 1, dCnt + 1, remain, visited);
            visited[i] = false;
        }
    }
}