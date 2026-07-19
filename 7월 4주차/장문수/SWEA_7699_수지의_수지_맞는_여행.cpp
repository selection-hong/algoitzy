#include <iostream>
#include <vector>
#include <algorithm>


using namespace std;

long long answer;
int dx[4] = {0, 0, -1, 1};
int dy[4] = {-1, 1, 0, 0};


/* 명물 위치 입력 */
void input (vector<vector<char>>& board, int r, int c) {
    for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
            cin >> board[i][j];
        }
    }
}


/* 명물 조회 여부 초기화 */
void reset_unique (vector<bool>& unique) {
    for (int i = 0; i < 26; i++) {
        unique[i] = false;
    }
}


/* 명물 위치 탐색 */
void dfs (vector<vector<char>>& board, vector<bool>& unique, int y, int x, long long cnt) {
    
    answer = max(answer, cnt);
    if (answer == 26) { // 모든 명물 탐색 시 종료
        return;
    }

    // 4방향 탐색
    for (int i = 0; i < 4; i++) {
        int ny = y + dy[i];
        int nx = x + dx[i];

        if (ny < 0 || ny >= board.size() || nx < 0 || nx >= board[0].size()) {
            continue;
        }
        
        // 방문처리
        if (!(unique[board[ny][nx] - 'A'])) {
            unique[board[ny][nx] - 'A'] = true;
            dfs(board, unique, ny, nx, cnt + 1);
            unique[board[ny][nx] - 'A'] = false;
        }
    }
}



int main() {

    cin.tie(nullptr);
    ios::sync_with_stdio(false);

    int tc;
    cin >> tc;

    for (int t = 1; t <= tc; t++) {
        int r, c;
        cin >> r >> c;

        // 명물 위치 입력
        vector<vector<char>> board(r, vector<char>(c));
        input(board, r, c);

        // 명물 조회 여부 초기화
        vector<bool> unique(26);
        reset_unique(unique);

        answer = 0;
        unique[board[0][0] - 'A'] = true;
        dfs(board, unique, 0, 0, 1);

        cout << "#" << t << " " << answer << "\n";
    }
}
