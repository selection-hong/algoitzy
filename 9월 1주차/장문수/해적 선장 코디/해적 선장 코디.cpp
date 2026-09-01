#include <iostream>
#include <queue>
#include <unordered_map>

using namespace std;


// 선박 정보
struct Status {
    // id: 선박 번호, reload: 재장전 시간, power: 공격력
    int id, power, reload;

    // 마지막 공격 시간, 갱신 시간
    int last_attack_time, update_time;
};


// 공격 준비된 선박 정렬
struct attack_comp {
    bool operator()(const Status& a, const Status& b) {
        // 공격력이 동일하다면 
        if (a.power == b.power) {
            // id 기준 정렬
            return a.id > b.id;
        }

        // 공격력 기준 정렬
        return a.power < b.power;
    }
};


// 준비중인 선박 정렬
struct ready_comp {
    bool operator() (Status& a, Status& b) {
        return a.last_attack_time + a.reload > b.last_attack_time + b.reload;
    }
};


int ship_idx;
Status ships[40000];

int front, rear;
priority_queue<Status, vector<Status>, attack_comp> attack_pq;
priority_queue<Status, vector<Status>, ready_comp> ready_pq;

// {ship_id, ships_idx}
unordered_map<int, int> ship_map;


/* 입출력 최적화 */
void optimize_io() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
}


/* 함선 정보 입력 */
int n;
void ship_info() {
    cin >> n;
    for (int i = 0; i < n; i++) {
        int id, power, reload;
        cin >> id >> power >> reload;

        // 선박 정보 입력
        ships[ship_idx++] = { id, power, reload, 0, 0 };
        ship_map[id] = ship_idx - 1;
        attack_pq.push(ships[ship_map[id]]);
    }
}


/* 지원 선박 정보 추가 */
void assistant(int time) {
    int id, power, reload;
    cin >> id >> power >> reload;

    ships[ship_idx++] = { id, power, reload, 0, time };
    ship_map[id] = ship_idx - 1;
    attack_pq.push(ships[ship_map[id]]);
}


/* 함포(공격력) 업데이트 */
void update_power(int time) {
    int input_id, input_power;
    cin >> input_id >> input_power;

    auto [id, power, reload, last_attack_time, update_time] = ships[ship_map[input_id]];
    ships[ship_map[input_id]].power = input_power;
    ships[ship_map[input_id]].update_time = time;

    ready_pq.push(ships[ship_map[input_id]]);
}


/* 공격 준비된 선박 탐색 */
void find_ready_ship(int time) {
    while (!ready_pq.empty()) {
        auto [id, power, reload, last_attack_time, update_time] = ready_pq.top();
        ready_pq.pop();

        // 최근에 갱신된 선박이 아니라면 무시
        int resent_update_time = ships[ship_map[id]].update_time;
        if (resent_update_time > update_time) {
            continue;
        }

        // 공격 준비 완료 여부 확인
        if (last_attack_time + reload <= time) {
            attack_pq.push(ships[ship_map[id]]);
        }
        else {
            ready_pq.push(ships[ship_map[id]]);
            break;
        }
    }
}


int attack_ship_cnt;
int attack_ship[5];

/* 함포 공격 */
long long attack(int time) {
    long long total_power = 0;
    attack_ship_cnt = 0;
    while (attack_ship_cnt < 5 && !attack_pq.empty()) {
        auto [id, power, reload, last_attack_time, update_time] = attack_pq.top();
        attack_pq.pop();

        // 최근에 갱신된 선박이 아니라면 무시
        int resent_update_time = ships[ship_map[id]].update_time;
        if (resent_update_time > update_time) {
            continue;
        }

        // 공격 후 마지막 공격 시간 갱신
        ships[ship_map[id]].last_attack_time = time;

        // 공격 선박 정보 저장
        attack_ship[attack_ship_cnt++] = id;

        // 공격 후 선박 정보 갱신
        ready_pq.push(ships[ship_map[id]]);
        total_power += power;
    }

    return total_power;
}


/* 결과 출력 */
void output_result(long long total_power) {
    cout << total_power << " " << attack_ship_cnt << " ";
    for (int i = 0; i < attack_ship_cnt; i++) {
        cout << attack_ship[i] << " ";
    }
    cout << "\n";
}


int main() {
    optimize_io();

    int cmd_cnt;
    cin >> cmd_cnt;
    for (int time = 0; time < cmd_cnt; time++) {
        int cmd;
        cin >> cmd;

        if (cmd == 100) {
            ship_info();
        }
        else if (cmd == 200) {
            assistant(time);
        }
        else if (cmd == 300) {
            update_power(time);
        }
        else if (cmd == 400) {
            find_ready_ship(time);
            long long total_power = attack(time);
            output_result(total_power);
        }
    }
}