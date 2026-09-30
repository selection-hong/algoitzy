#include <iostream>
#include <queue>
#include <cmath>
#include <vector>
#include <cstring>
#include <unordered_map>

#define MAP_SIZE_MAX	350

using namespace std;

// 맵 정보 입력
int n, max_stamina;
int* map[MAP_SIZE_MAX];

// [gate_id] = gate 좌표 (y * n + x)
unordered_map<int, int> gate_map;

// 추가된 게이트 정보
int add_cnt;
int add_gate[200];

// 4방향 이동
int dx[] = { 1, 0, -1, 0 };
int dy[] = { 0, 1, 0, -1 };

// 게이트 간 이동 시간 정보 구조체
struct NODE_INFO {
	int gate_id, time;
};

// 게이트 간 이동 시간 정보
vector<NODE_INFO> gate_graph[202];
// 게이트간 연결 여부
bool gate_edge[202][202];

// bfs 용 게이트 방문 정보
NODE_INFO visit[MAP_SIZE_MAX][MAP_SIZE_MAX];

// bfs 용 큐 구조체
struct BFS_INFO {
	int y, x, time;
};

// bfs
int front, rear;
BFS_INFO que[MAP_SIZE_MAX * MAP_SIZE_MAX];

// dijkstra 용 우선순위 큐 비교 구조체 
struct Comp {
	bool operator() (NODE_INFO& a, NODE_INFO& b) {
		return a.time > b.time;
	}
};


// dijkstra 용 게이트 방문 정보 구조체
struct DATA {
	int call_cnt, time;
};

// dijkstra 용 게이트 방문 정보
DATA dijk_visit[202];

// dijkstra 용 방문 배열 재사용을 위한 호출 횟수
int call_cnt;


/* 초기화 함수 */
void init(int N, int mMaxStamina, int mMap[MAP_SIZE_MAX][MAP_SIZE_MAX])
{
	// n 및 최대 스태미나 설정
	n = N;
	max_stamina = mMaxStamina;

	// 맵 정보 초기화
	gate_map.clear();
	add_cnt = 0;
	call_cnt = 0;

	for (int i = 0; i < 202; ++i)
		gate_graph[i].clear();

	memset(gate_edge, 0, sizeof(gate_edge));
	memset(visit, 0, sizeof(visit));
	memset(dijk_visit, 0, sizeof(dijk_visit));

	// 맴 정보 복사
	for (int i = 0; i < N; ++i)
		map[i] = mMap[i];

	return;
}


/* 게이트 추가 함수 */
void addGate(int mGateID, int mRow, int mCol)
{
	// 벽이 1이기 때문에 게이트 번호를 2부터 시작
	mGateID += 1;

	// 맵에 게이트 추가
	map[mRow][mCol] = mGateID;
	gate_map[mGateID] = mRow * n + mCol;
	add_gate[add_cnt++] = mGateID;

	return;
}


/* 게이트 제거 함수 */
void removeGate(int mGateID)
{
	// 벽이 1이기 때문에 게이트 번호를 2부터 시작
	mGateID += 1;

	// 맵에서 게이트 제거
	int cur = gate_map[mGateID];
	map[cur / n][cur % n] = 0;
	gate_map[mGateID] = 0;

	return;
}




/* 생성된 게이트 간 최소 이동 시간 계산 */
void bfs() {
	for (int i = 0; i < add_cnt; i++) {
		int gate_id = add_gate[i];
		//cout << "add gate id: " << gate_id << "\n";
		// 추가된 게이트가 활성화 되어 있다면
		if (gate_map[gate_id]) {
			//cout << "this active gate\n";
			// 해당 게이트의 좌표를 가져온다
			int y = gate_map[gate_id] / n;
			int x = gate_map[gate_id] % n;

			// 큐 초기화 및 방문 기록 초기화
			front = rear = 0;
			que[rear++] = { y, x, 0 };
			visit[y][x] = { gate_id, 0 };

			// BFS 알고리즘 수행
			while (front < rear) {
				BFS_INFO cur = que[front++];
				//cout << "cur: " << cur.y << ", " << cur.x << ", " << cur.time << "\n";
				//cout << "visit map: " << map[cur.y][cur.x] << "\n";
				// 최대 이동 가능 거리를 넘었다면 continue
				if (cur.time > max_stamina) {
					continue;
				}

				// 다른 게이트를 만났다면, 최소 이동 시간 기록 및 continue
				if (map[cur.y][cur.x] > 1 && map[cur.y][cur.x] != gate_id) {
					visit[cur.y][cur.x] = { gate_id, cur.time };
					if (!gate_edge[gate_id][map[cur.y][cur.x]]) {
						gate_edge[gate_id][map[cur.y][cur.x]] = true;
						gate_edge[map[cur.y][cur.x]][gate_id] = true;

						gate_graph[gate_id].push_back({ map[cur.y][cur.x], cur.time });
						gate_graph[map[cur.y][cur.x]].push_back({ gate_id, cur.time });
					}

					//cout << "now graph status\n";
					//for (int i = 0; i < 202; i++) {
					//	if (gate_map[i]) {
					//		cout << "gate_id: " << i << "\n";
					//		for (NODE_INFO next : gate_graph[i]) {
					//			cout << "(" << next.gate_id << ", " << next.time << ") ";
					//		}
					//		cout << "\n";
					//	}
					//}
				}

				// 4방향 탐색
				for (int d = 0; d < 4; d++) {
					int ny = cur.y + dy[d];
					int nx = cur.x + dx[d];

					// 방문한 적 없고, 벽이 아니라면 방문 기록 및 큐에 추가
					if (visit[ny][nx].gate_id != gate_id && map[ny][nx] != 1) {
						visit[ny][nx] = { gate_id, cur.time + 1 };
						que[rear++] = { ny, nx, cur.time + 1 };
					}
					// 방문한 적이 있고, 현재 시간보다 더 빠른 시간으로 도착했다면 방문 기록 갱신 및 큐에 추가
					else if (visit[ny][nx].gate_id == gate_id) {
						if (visit[ny][nx].time > cur.time + 1) {
							visit[ny][nx].time = cur.time + 1;
							que[rear++] = { ny, nx, cur.time + 1 };
						}
					}
				}
			}
		}
	}
	add_cnt = 0;
}


/* 최소 이동 시간 계산 함수 */
int getMinTime(int mStartGateID, int mEndGateID)
{
	call_cnt++;
	// 생성된 게이트 간 최소 이동 시간 계산
	bfs();
	//cout << "now graph status\n";
	//for (int i = 0; i < 202; i++) {
	//	if (gate_map[i]) {
	//		cout << "gate_id: " << i - 1<< "\n";
	//		for (NODE_INFO next : gate_graph[i]) {
	//			cout << "(" << next.gate_id - 1 << ", " << next.time << ") ";
	//		}
	//		cout << "\n";
	//	}
	//}

	// 벽이 1이기 때문에 게이트 번호를 2부터 시작
	mStartGateID += 1;
	mEndGateID += 1;

	//cout << "start gate id: " << mStartGateID - 1 << ", end gate id: " << mEndGateID - 1 << "\n";
	priority_queue<NODE_INFO, vector<NODE_INFO>, Comp> pq;
	dijk_visit[mStartGateID] = { call_cnt, 0 };
	pq.push({ mStartGateID, 0 });
	while (!pq.empty()) {
		NODE_INFO cur = pq.top();
		pq.pop();

		//cout << "now gate id: " << cur.gate_id - 1 << ", time: " << cur.time << "\n";

		// 목적지점에 도착했다면 최소 이동 시간 반환
		if (cur.gate_id == mEndGateID) {
			return cur.time;
		}

		// 현재 게이트에서 연결된 게이트들을 탐색
		for (NODE_INFO next : gate_graph[cur.gate_id]) {
			//cout << "next gate id: " << next.gate_id - 1 << ", status: " << gate_map[next.gate_id] << "\n";
			// 활성화된 게이트라면
			if (gate_map[next.gate_id]) {
				// 현재 게이트에서 연결된 게이트로 이동했을 때, 최소 이동 시간 갱신 및 우선순위 큐에 추가
				if (dijk_visit[next.gate_id].call_cnt < call_cnt) {
					dijk_visit[next.gate_id] = { call_cnt, cur.time + next.time };
					pq.push({ next.gate_id, cur.time + next.time });
				}
				else if (dijk_visit[next.gate_id].time > cur.time + next.time) {
					dijk_visit[next.gate_id].time = cur.time + next.time;
					pq.push({ next.gate_id, cur.time + next.time });
				}
			}
		}
	}

	//cout << "out of que\n";
	return -1;
}


#ifndef _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#endif



#define MAX_MAP_SIZE 350

/////////////////////////////////////////////////////////////////////////

#define CMD_INIT			0
#define CMD_ADD_GATE		1
#define CMD_REMOVE_GATE		2
#define CMD_GET_MIN_TIME	3

static int gMap[MAX_MAP_SIZE][MAX_MAP_SIZE];

static bool run()
{
	int cmd, ans, ret;
	int N, maxStamina, gateID1, gateID2, row, col;
	int Q = 0;
	bool okay = false;

	cin >> Q;

	for (int q = 0; q < Q; ++q)
	{
		cin >> cmd;

		switch (cmd)
		{
		case CMD_INIT:
			cin >> N >> maxStamina;
			for (int i = 0; i <= N - 1; i++) {
				for (int j = 0; j <= N - 1; j++) {
					cin >> gMap[i][j];
				}
			}
			init(N, maxStamina, gMap);
			okay = true;
			break;

		case CMD_ADD_GATE:
			cin >> gateID1 >> row >> col;
			addGate(gateID1, row, col);
			break;

		case CMD_REMOVE_GATE:
			cin >> gateID1;
			removeGate(gateID1);
			break;

		case CMD_GET_MIN_TIME:
			cin >> gateID1 >> gateID2;
			ret = getMinTime(gateID1, gateID2);
			cin >> ans;
			//cout << "-------------------------------------------------\n";
			//cout << "ret: " << ret << ", ans: " << ans << "\n";
			if (ret != ans) {
				//cout << "fail\n";
				okay = false;
			}
			break;

		default:
			okay = false;
		}
	}

	return okay;
}

int main()
{
	//freopen("sample_input.txt", "r", stdin);

	int T, MARK;
	cin >> T >> MARK;

	for (int tc = 1; tc <= T; tc++)
	{
		int score = run() ? MARK : 0;
		cout << "#" << tc << " " << score << endl;
	}

	return 0;
}