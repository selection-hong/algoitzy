#include <iostream>
#include <algorithm>


using namespace std;


/* 작업 정보 구조체 */
struct Work {
	int start;
	int end;
	int cost;

	// 빠른 작업 순 정렬 로직
	bool operator<(const Work& other) const {
		if (start == other.start) {
			return end < other.end;
		}

		return start < other.start;
	}
};


int n, m;
Work work[10002];
int max_cost[10002] = { 0, };


/* 작업 수익 갱신 */
void cost_calc(int end, int cost) {

	// 현재 작업의 수익이 기존 수익보다 큰 경우에만 갱신
	if (max_cost[end + 1] < cost) {
		for (int i = end + 1; i <= m + 1; i++) {
			max_cost[i] = max(max_cost[i], cost);
		}
	}
}


int main() {

	cin.tie(nullptr);
	ios::sync_with_stdio(false);

	int tc;
	cin >> tc;

	for (int t = 1; t <= tc; t++) {
		cin >> n >> m;

		// 작업 정보 입력
		for (int i = 0; i < n; i++) {
			cin >> work[i].start >> work[i].end >> work[i].cost;
		}

		// 작업 시작 시간 기준으로 정렬
		sort(work, work + n);

		// 수익 계산
		fill(max_cost, max_cost + m + 2, 0);
		for (int i = 0; i < n; i++) {			
			cost_calc(work[i].end, max_cost[work[i].start] + work[i].cost);
		}

		cout << "#" << t << " " << max_cost[m + 1] << "\n";
	}
}