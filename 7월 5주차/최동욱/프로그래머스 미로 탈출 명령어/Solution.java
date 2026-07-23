class Solution {
        
    private class Direction {
        int opposite, cnt = 0;
        char d;
        
        Direction(int opposite, char d) {
            this.opposite = opposite;
            this.d = d;
        }
    }
    
    public String solution(int n, int m, int y, int x, int c, int r, int k) {
        StringBuilder sb = new StringBuilder();
        
        // 사전순: d, l, r, u
        int[][] d = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
        
        int w = Math.abs(r - x);
        int h = Math.abs(c - y);
        int dist = w + h;
        int remain = k - dist;
        
        // 최소 거리보다 짧거나, 남는 이동 횟수가 홀수이면 도달할 수 없다.
        if(dist > k || ((remain & 1) == 1)) return "impossible";
        
        Direction[] dir = new Direction[4];
        dir[0] = new Direction(3, 'd');
        dir[1] = new Direction(2, 'l');
        dir[2] = new Direction(1, 'r');
        dir[3] = new Direction(0, 'u');
        
        // 목적지까지 반드시 필요한 방향별 이동 횟수
        dir[(c > y) ? 0 : 3].cnt = h;
        dir[(r < x) ? 1 : 2].cnt = w;
        
        for(int step = 1; step <= k; step++) {
            for(int i = 0; i < 4; i++) {
                int ny = y + d[i][0];
                int nx = x + d[i][1];
                
                if(!check(ny, nx, n, m)) continue;
                
                if(dir[i].cnt > 0) {
                    // 목적지까지 필요한 이동을 수행한다.
                    dir[i].cnt--;
                }
                else if(remain > 0) {
                    // 추가 이동을 수행하고, 반대 방향 이동을 예약한다.
                    int op = dir[i].opposite;
                    dir[op].cnt++;
                    remain -= 2;
                } else {
                    continue;
                }
                
                // 장애물이 없으므로 선택한 경로를 즉시 확정한다.
                sb.append(dir[i].d);
                y = ny;
                x = nx;
                break;
            }
        }
        
        return sb.toString();
    }
    
    private static boolean check(int y, int x, int n, int m) {
        return y > 0 && y <= n && x > 0 && x <= m;
    }
}