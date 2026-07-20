#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int dx[4] = {1, 0, -1, 0};
int dy[4] = {0, 1, 0, -1};

/* 치즈 덩어리 탐색 */
int cnt_block(vector<vector<int>> cheese, int n) {
    int result = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (cheese[i][j]) {
                result++;

                // 이어진 치즈 덩어리 초기화
                queue<pair<int, int>> que;
                que.push({i, j});
                cheese[i][j] = 0;

                while (!que.empty()) {
                    auto [x, y] = que.front();
                    que.pop();

                    for (int d = 0; d < 4; d++) {
                        int nx = x + dx[d];
                        int ny = y + dy[d];

                        if (nx < 0 || nx >= n || ny < 0 || ny >= n) {
                            continue;
                        }

                        if (cheese[nx][ny]) {
                            cheese[nx][ny] = 0;
                            que.push({nx, ny});
                        }
                    }
                }
            }
        }
    }

    return result;
}


int main()
{

    cin.tie(nullptr);
    ios::sync_with_stdio(false);

    int tc;
    cin >> tc;

    for (int t = 1; t <= tc; t++)
    {
        int n;
        cin >> n;

        // 치즈 입력
        priority_queue<pair<int, pair<int, int>>, vector<pair<int, pair<int, int>>>, greater<pair<int, pair<int, int>>>> que;
        vector<vector<int>> cheese(n, vector<int>(n));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cin >> cheese[i][j];
                que.push({cheese[i][j], {i, j}});
            }
        }

        // 치즈 섭취
        int answer = 1;
        for (int i = 1; i <= 100 && !que.empty(); i++) {   
            // i일차에 섭취할 치즈 제거
            while (!que.empty() && que.top().first == i) {
                auto [value, pos] = que.top();
                que.pop();

                cheese[pos.first][pos.second] = 0;
            }

            // 치즈 덩어리 탐색 및 개수 업데이트
            answer = max(answer, cnt_block(cheese, n));
        }

        cout << "#" << t << " " << answer << "\n";
    }
}
