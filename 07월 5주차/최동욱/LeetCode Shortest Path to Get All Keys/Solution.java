import java.util.*;

class Solution {
    public int shortestPathAllKeys(String[] grid) {
        int n = grid.length;
        int m = grid[0].length();

        int key = 0, y = -1, x = -1;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                char c = grid[i].charAt(j);
                if(c >= 'a' && c <= 'z') {
                    key++;
                } else if(c == '@') {
                    y = i;
                    x = j;
                }
            }
        }

        Queue<int[]> que = new ArrayDeque<>();
        que.add(new int[]{y, x, 0, 0});

        boolean[][][] visited = new boolean[n][m][1 << key];
        visited[y][x][0] = true;

        int[][] dist = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while(!que.isEmpty()) {
            int[] node = que.poll();
            int cy = node[0];
            int cx = node[1];
            int bit = node[2];
            int cnt = node[3];

            if(bit == (1 << key) - 1) {
                return cnt;
            }

            for(int[] d : dist) {
                int ny = cy + d[0];
                int nx = cx + d[1];
                
                if(!check(ny, nx, n, m) || grid[ny].charAt(nx) == '#') continue;
                
                int temp = grid[ny].charAt(nx);
                int nextBit = bit;
                if(temp >= 'A' && temp <= 'Z') {
                    temp -= 'A';
                    if((nextBit & (1 << temp)) == 0) continue;
                } else if(temp >= 'a' && temp <= 'z') {
                    temp -= 'a';
                    nextBit |= (1 << temp);
                }

                if(!visited[ny][nx][nextBit]) {
                    que.add(new int[]{ny, nx, nextBit, cnt + 1});
                    visited[ny][nx][nextBit] = true;
                }
            }
        }

        return -1;
    }

    private boolean check(int y, int x, int n, int m) {
        return y >= 0 && y < n && x >= 0 && x < m;
    }
}
