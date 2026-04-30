#include <iostream>
#include <stdio.h>

using namespace std;

struct Point {
    int y, x, pre;
};

void inputPoint(Point* point, long long* dp, int* colors, int n, int k) {
    int idx = 0;
    for(int y = 0; y < n; y++) {
        for(int x = 0; x < n; x++) {
            int color;
            cin >> color;

            point[idx].y = y;
            point[idx].x = x;
            point[idx].pre = colors[color];
            dp[idx] = color < k ? 1e18 : 0;
            colors[color] = idx;
            idx++;
        }
    }
}

long long getDistance(int y, int x, int ny, int nx) {
    int dy = y > ny ? y - ny : ny - y;
    int dx = x > nx ? x - nx : nx - x;
    return dy + dx;
}

long long solve(Point* point, long long* dp, int* colors, int n, int k) {
    for(int i = 1; i <= k; i++) {
        if(colors[i] == -1) return -1;
    }
    
    for(int i = k - 1; i >= 1; i--) {
        int color = colors[i];

        while(color > -1) {
            int y = point[color].y;
            int x = point[color].x;
            
            int pre = colors[i + 1];
            while(pre > -1) {
                int ny = point[pre].y;
                int nx = point[pre].x;

                long long dist = getDistance(y, x, ny, nx);
                if(dp[color] > dist + dp[pre]) dp[color] = dist + dp[pre];
                pre = point[pre].pre;
            }
            color = point[color].pre;
        }
    }

    int color = colors[1];
    long long res = 1e18;
    while(color > -1) {
        if(res > dp[color]) res = dp[color];
        color = point[color].pre;
    }
    return res;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int T, n, k;
    cin >> T;
    for(int t = 1; t <= T; t++) {
        cin >> n >> k;

        Point* point = (Point*)malloc(sizeof(Point) * n * n);
        long long* dp = (long long*)malloc(sizeof(long long) * n * n);
        int* colors = (int*)malloc(sizeof(int) * (k + 1));

        // if(point == NULL || dp == NULL || colors == NULL) continue;
        
        for(int i = 1; i <= k; i++) colors[i] = -1;

        inputPoint(point, dp, colors, n, k);
        cout << '#' << t << ' ' << solve(point, dp, colors, n, k) << '\n';

        free(point);
        free(dp);
        free(colors);
    }
        
    return 0;
}