#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int dx[] = { 1, 0, -1, 0 };
int dy[] = { 0, 1, 0, -1 };

int main() {

    int t;
    cin >> t;

    for (int tc = 1; tc <= t; tc++) {
        int n;
        cin >> n;

        vector<vector<int>> map(n, vector<int>(n));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cin >> map[i][j];
            }
        }

        // 시작점과 도착점 입력
        int start_x, start_y, end_x, end_y;
        cin >> start_y >> start_x >> end_y >> end_x;

        queue<pair<pair<int, int>, int>> que; // (y, x), time
        vector<vector<bool>> visited(n, vector<bool>(n, true));

        que.push({ {start_y, start_x}, 0 });
        visited[start_y][start_x] = false;

        int answer = -1;
        while (!que.empty()) {
            int y = que.front().first.first;
            int x = que.front().first.second;
            int time = que.front().second;
            que.pop();

            if (x == end_x && y == end_y) {
                answer = time;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                // 범위 확인
                if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                    continue;
                }

                // 방문 확인
                if (!visited[ny][nx]) {
                    continue;
                }

                if (!map[ny][nx]) { // 0이면 이동 가능
                    que.push({ {ny, nx}, time + 1 });
                    visited[ny][nx] = false;
                }
                else if (map[ny][nx] == 2) { // 2일 때
                    if (!((time + 1) % 3)) { // 소용돌이가 사라지면 이동
                        que.push({ {ny, nx}, time + 1 });
                        visited[ny][nx] = false;
                    }
                    else { // 소용돌이가 있으면 대기
                        que.push({ {y, x}, time + 1 });
                    }
                }
            }
        }

        cout << "#" << tc << " " << answer << "\n";
    }
}