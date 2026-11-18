/*
* 정사각형 격자판, 1칸짜리 덫
* 직사각형의 범위를 가진 기사
* 3개의 값이 주어진다
*
* 총 Q번의 명령이 주어졌을 때, 살아남은 기사들의 체력 감소량의 합을 구하라
*
*
* // 문제 풀이
* 1. L, N, Q를 입력 받는다
*  - L: 격자판의 한 변의 길이
*  - N: 기사들의 수
*  - Q: 명령의 수
* 2. 격자 정보를 입력 받는다
*  - 0: 빈 칸
*  - 1: 덫
*  - 2: 벽
* 3. 기사들의 정보를 입력 받는다
*  - r, c: 기사의 좌측 상단 위치
*  - h: 세로 길이
*  - w: 가로 길이
*  - k: 체력
* 4. 명령을 입력 받는다
*  - i: 기사의 번호
*  - d: 방향 (0: 상, 1: 우, 2: 하, 3: 좌)
* 5. 명령을 수행한다
*   1). 이동할 기사의 위치를 찾는다
*   2). 이동할 위치가 격자 범위 안이고, 벽이 아닌지 확인한다
*       2 - 1). 격자 범위 안이라면 stack에 이동할 기사 정보를 저장한다
*           2 - 1 - 1). 이동할 위치에 다른 기사가 있는지 확인한다
*           2 - 1 - 2). 있다면 1) ~ 2)를 반복한다
*           2 - 1 - 3). 없다면 stack에 0을 저장한다
*   3). stack을 확인해 마지막 저장된 기사 번호가 양수일 경우 명령을 종료한다
*   4). stack이 빌 때 까지 기사 번호를 꺼낸다
*   5). 기사 번호 기반, 기사의 위치 조회
*   6). 격자 에서 기사 정보 제거
*   7). 기사 위치 업데이트
*   8). 격자에 기사 정보 추가
*       8 - 1). 이동한 위치에 덫이 있는지 확인
*       8 - 2). 있다면 체력 감소
*   9) 기사의 체력이 0 이하라면 사망으로 처리
*/
#include <iostream>
#include <set>

#define GRID_MAX 40
#define KNIGHT_MAX 30

using namespace std;

struct POS {
    int y, x;
};

struct GRID {
    bool trap;
    bool wall;
    int knight;
};

struct KNIGHT {
    POS pos;
    int h, w, k;
    int origin_k;
    bool alive = true;
};

GRID grid[GRID_MAX + 1][GRID_MAX + 1];
KNIGHT knight[KNIGHT_MAX + 1];

int l, n, q;

int dy[] = { -1, 0, 1, 0 };
int dx[] = { 0, 1, 0, -1 };

int knight_cnt;
int move_knight_list[KNIGHT_MAX + 1];
bool visited[KNIGHT_MAX + 1];


/* 입출력 최적화 */
void fast_io() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
}


/* 격자 정보 입력 */
void input_grid() {
    for (int y = 0; y < l; y++) {
        for (int x = 0; x < l; x++) {
            int input;
            cin >> input;

            grid[y][x] = { input == 1, input == 2, 0 };
        }
    }
}


/* 격자에 기사 배치: 피해 계산은 별도로 수행 */
void set_knight(const KNIGHT& cur, int id) {
    for (int y = cur.pos.y; y < cur.pos.y + cur.h; y++) {
        for (int x = cur.pos.x; x < cur.pos.x + cur.w; x++) {
            grid[y][x].knight = id;
        }
    }
}


/* 기사 정보 입력 */
void input_knight() {
    for (int id = 1; id <= n; id++) {
        int r, c, h, w, k;
        cin >> r >> c >> h >> w >> k;

        knight[id] = { {r - 1, c - 1}, h, w, k, k, true };
        set_knight(knight[id], id);
    }
}


/* 해당 칸으로 이동할 수 있는지 확인 */
bool can_move(int y, int x) {
    if (y < 0 || y >= l || x < 0 || x >= l) {
        return false;
    }

    return !grid[y][x].wall;
}


/*
 * 이동할 기사 수집 + 전체 이동 가능 여부 확인
 * 이 단계에서는 위치, 격자, 체력을 변경하지 않습니다.
 */
bool knight_move(int id, int dir) {
    // 같은 기사는 명령 하나에서 한 번만 확인
    if (visited[id]) {
        return true;
    }

    visited[id] = true;

    // 최초 명령받은 기사도 목록에 포함
    move_knight_list[knight_cnt++] = id;

    const KNIGHT& cur = knight[id];

    int ny = cur.pos.y + dy[dir];
    int nx = cur.pos.x + dx[dir];

    set<int> push_knight;

    // 이동 후 차지할 직사각형 전체 확인
    for (int y = ny; y < ny + cur.h; y++) {
        for (int x = nx; x < nx + cur.w; x++) {
            // 한 칸이라도 벽이나 격자 밖이면 전체 이동 불가
            if (!can_move(y, x)) {
                return false;
            }

            int next_id = grid[y][x].knight;

            // 현재 위치와 겹치는 자신의 칸은 제외
            if (next_id != 0 && next_id != id) {
                push_knight.insert(next_id);
            }
        }
    }

    // 밀려나는 기사들도 같은 방향으로 이동 가능한지 확인
    for (int next_id : push_knight) {
        if (!knight_move(next_id, dir)) {
            return false;
        }
    }

    return true;
}


/* 격자에서 기사 제거 */
void remove_knight(const KNIGHT& cur) {
    for (int y = cur.pos.y; y < cur.pos.y + cur.h; y++) {
        for (int x = cur.pos.x; x < cur.pos.x + cur.w; x++) {
            grid[y][x].knight = 0;
        }
    }
}


/* 기사 위치 업데이트 */
void update_pos(KNIGHT& cur, int dir) {
    cur.pos.y += dy[dir];
    cur.pos.x += dx[dir];
}


/* 이동 후 영역 전체의 덫 피해 계산 */
void apply_damage(KNIGHT& cur) {
    int damage = 0;

    for (int y = cur.pos.y; y < cur.pos.y + cur.h; y++) {
        for (int x = cur.pos.x; x < cur.pos.x + cur.w; x++) {
            if (grid[y][x].trap) {
                damage++;
            }
        }
    }

    cur.k -= damage;
    cur.alive = cur.k > 0;
}


/* 명령어 입력 및 수행 */
void input_command() {
    for (int c = 0; c < q; c++) {
        int id, dir;
        cin >> id >> dir;

        // 죽은 기사에게 내려진 명령은 무시
        if (!knight[id].alive) {
            continue;
        }

        knight_cnt = 0;

        for (int i = 1; i <= n; i++) {
            visited[i] = false;
        }

        // 하나라도 이동 불가능하면 명령 취소
        if (!knight_move(id, dir)) {
            continue;
        }

        // 이동할 기사 전부를 기존 격자에서 제거
        for (int i = 0; i < knight_cnt; i++) {
            int move_id = move_knight_list[i];
            remove_knight(knight[move_id]);
        }

        // 이동할 기사 전부의 좌표 변경
        for (int i = 0; i < knight_cnt; i++) {
            int move_id = move_knight_list[i];
            update_pos(knight[move_id], dir);
        }

        // 밀려난 기사들의 피해 계산
        for (int i = 0; i < knight_cnt; i++) {
            int move_id = move_knight_list[i];

            // 직접 명령받은 기사는 피해를 받지 않음
            if (move_id != id) {
                apply_damage(knight[move_id]);
            }
        }

        // 살아 있는 기사만 격자에 다시 배치
        for (int i = 0; i < knight_cnt; i++) {
            int move_id = move_knight_list[i];

            if (knight[move_id].alive) {
                set_knight(knight[move_id], move_id);
            }
        }
    }
}


/* 생존한 기사들이 받은 피해의 합 출력 */
void output() {
    int answer = 0;

    for (int id = 1; id <= n; id++) {
        if (knight[id].alive) {
            answer += knight[id].origin_k - knight[id].k;
        }
    }

    cout << answer << '\n';
}


int main() {
    fast_io();

    cin >> l >> n >> q;

    input_grid();
    input_knight();
    input_command();
    output();

    return 0;
}