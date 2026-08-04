#include <iostream>

using namespace std;

/* 위치 및 시간 데이터 */
struct Data {
    int y, x, time;
};

// 방향 정보
int dx[] = { 1, 0, -1, 0 };
int dy[] = { 0, 1, 0, -1 };

int n; // 바다 크기
int start_x, start_y, end_x, end_y; // 시작점과 도착점
int sea[15][15]; // 바다 정보
bool visited[15][15]; // 방문 여부

// que 배열
int front, rear;
Data que[10000];

int bfs() {
    int answer = -1;
    while (front < rear) {
        auto [y, x, time] = que[front++];

        if (x == end_x && y == end_y) {
            answer = time;
            break;
        }

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 범위 및 방문 확인
            if (nx < 0 || nx >= n || ny < 0 || ny >= n || !visited[ny][nx]) {
                continue;
            }

            if (!sea[ny][nx]) { // 0이면 이동 가능
                que[rear++] = { ny, nx, time + 1 };
                visited[ny][nx] = false;
            }
            else if (sea[ny][nx] == 2) { // 2일 때
                if (!((time + 1) % 3)) { // 소용돌이가 사라지면 이동
                    que[rear++] = { ny, nx, time + 1 };
                    visited[ny][nx] = false;
                }
                else { // 소용돌이가 있으면 대기
                    que[rear++] = { y, x, time + 1 };
                }
            }
        }
    }

    return answer;
}


int main() {

    int t;
    cin >> t;

    for (int tc = 1; tc <= t; tc++) {
        fill(&visited[0][0], &visited[0][0] + 15 * 15, true); // 방문 배열 초기화
        front = rear = 0; // 큐 초기화
        cin >> n;

        // 바다 정보 입력
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cin >> sea[i][j];
            }
        }

        // 시작점과 도착점 입력
        cin >> start_y >> start_x >> end_y >> end_x;

        // 시작점 큐에 넣기
        que[rear++] = { start_y, start_x, 0 };
        visited[start_y][start_x] = false;

        cout << "#" << tc << " " << bfs() << "\n";
    }
}

