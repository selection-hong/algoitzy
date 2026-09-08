#include <vector>
#include <queue>
#include <climits>
#include <unordered_map>

#define MAX_CITY 300
#define MAX_COST 2000

using namespace std;

// 간선 정보
struct EDGE_INFO {
    int id;          // 간선 ID
    int dist, dest;  // 거리, 도착 도시
};

vector<EDGE_INFO> graph[MAX_CITY];       // graph[i]: i번 도시에서 출발하는 간선
unordered_map<int, bool> remove_id_map;  // 삭제된 간선 ID
int node_cost[MAX_CITY];                 // 도시별 이동 단가

// 우선순위 큐에 저장할 이동 상태
struct MV_INFO {
    int node;        // 현재 도시
    int min_cost;    // 지금까지 만난 최소 단가
    int total_cost;  // 지금까지 사용한 총비용
};

// 총비용이 작은 상태가 먼저 나오도록 설정
struct DIJK_COMP {
    bool operator()(const MV_INFO& p1, const MV_INFO& p2) {
        return p1.total_cost > p2.total_cost;
    }
};

// visit 배열 재사용을 위한 정보
struct REUSE_VISIT {
    int call_cnt;    // cost() 호출 번호
    int total_cost;  // 해당 상태까지의 최소 총비용
};

int n;
int call_cnt = 0;

// visit[현재 도시][현재 최소 단가]
REUSE_VISIT visit[MAX_CITY][MAX_COST];

// 기존 그래프와 삭제 정보 초기화
void reset(int n) {
    for (int i = 0; i < MAX_CITY; i++) graph[i].clear();
    remove_id_map.clear();
}

// 초기 도시 및 간선 정보 설정
void init(int N, int mCost[], int K, int mId[], int sCity[], int eCity[], int mDistance[]) {
    reset(N);
    n = N;

    for (int i = 0; i < N; i++) node_cost[i] = mCost[i];
    for (int i = 0; i < K; i++) graph[sCity[i]].push_back({ mId[i], mDistance[i], eCity[i] });
}

// 새로운 단방향 간선 추가
void add(int mId, int sCity, int eCity, int mDistance) {
    graph[sCity].push_back({ mId, mDistance, eCity });
}

// 간선을 삭제 상태로 변경
void remove(int mId) {
    remove_id_map[mId] = true;
}

int cost(int sCity, int eCity) {
    call_cnt++;

    priority_queue<MV_INFO, vector<MV_INFO>, DIJK_COMP> pq;
    pq.push({ sCity, node_cost[sCity], 0 });

    int result = -1;

    while (!pq.empty()) {
        int node = pq.top().node;
        int min_cost = min(pq.top().min_cost, node_cost[node]);  // 현재 도시의 단가까지 반영
        int total_cost = pq.top().total_cost;
        pq.pop();

        // 현재 상태의 비용 기록
        visit[node][min_cost].total_cost = total_cost;
        visit[node][min_cost].call_cnt = call_cnt;

        // 목적지가 가장 먼저 나오면 최소비용 확정
        if (node == eCity) {
            result = total_cost;
            break;
        }

        // 현재 도시에서 출발하는 간선 탐색
        for (auto& next_node : graph[node]) {
            int id = next_node.id;
            int dist = next_node.dist;
            int dest = next_node.dest;

            // 삭제된 간선 제외
            if (remove_id_map[id]) continue;

            // 현재 최소 단가로 다음 간선을 이동
            int next_cost = total_cost + dist * min_cost;

            // 이번 cost()에서 처음 만들어진 상태
            if (call_cnt != visit[dest][min_cost].call_cnt) {
                visit[dest][min_cost] = { call_cnt, next_cost };
                pq.push({ dest, min_cost, next_cost });
            }
            // 기존 상태보다 총비용이 작으면 갱신
            else if (visit[dest][min_cost].total_cost > next_cost) {
                visit[dest][min_cost] = { call_cnt, next_cost };
                pq.push({ dest, min_cost, next_cost });
            }
        }
    }

    return result;
}