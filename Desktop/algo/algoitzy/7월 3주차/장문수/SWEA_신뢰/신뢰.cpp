#include <iostream>
#include <queue>
#include <cmath>


using namespace std;


int main() {

    int tc;
    cin >> tc;

    for(int t = 1; t <= tc; t++) {
        int n;
        cin >> n;

        queue<pair<string, int>> que;
        for (int i = 0; i < n; i++) {
            string turn;
            int num;
            cin >> turn >> num;

            que.push({turn, num});
        }

        int o, b;
        o = b = 1;

        string last_robot = que.front().first;
        int mv_cnt = 0;

        int answer = 0;
        while(!que.empty()) {
            string robot = que.front().first;
            int goal = que.front().second;
            que.pop();

            int need_mv_cnt = abs(goal - (robot == "O" ? o : b)); // 필요 이동 횟수
            if (last_robot == robot) { // 동일한 로봇의 차례일 때
                // 사용 턴 수 업데이트
                answer += need_mv_cnt + 1;
                mv_cnt += need_mv_cnt + 1;
            } else { // 다른 로봇의 차례일 때
                last_robot = robot;
                if (need_mv_cnt > mv_cnt) { // 로봇이 추가적인 이동이 필요할 경우
                    answer += abs(need_mv_cnt - mv_cnt);
                    mv_cnt = abs(need_mv_cnt - mv_cnt);
                } else {
                    mv_cnt = 0;
                }
                
                // 버튼을 누르는 동안의 턴 수 업데이트
                mv_cnt += 1;
                answer += 1;
            }

            // 로봇 위치 업데이트
            if (robot == "O") {
                o = goal;
            } else {
                b = goal;
            }
        }

        cout << "#" << t << " " << answer << endl;
    }
}