/*
* union-find 개념과 구현으로 이루어진 문제
* area에서 각 그룹을 만들고, 특수한 계산식을 통해 맞닿은 그룹 끼리 값을 합친 값을 구하는 문제
* 단 십자 모양을 좌측 90도, 그 외 모양들을 우측 90도로 돌리는 행위 후 위 계산 값을 다시 구하길 3번 반복하여
* 전체 합을 구하는 문제
*
*
* --- 참고 사항 ---
* 1. 계산식: (a그룹의 칸 수 + b그룹의 칸 수)
*            * a그룹을 이루는 숫자 * b그룹을 이루는 숫자
*            * a그룹과 b그룹이 맞닿은 변의 개수
*
*
* --- 풀이 ---
* 1. 현재 번호와 부모의 번호, 그룹 내 칸 수를 저장할 구조체 Data 선언
* 2. Data 구조체를 사용하는 29 사이즈 2차원 배열 선언
* 3. 탐색한 그룹 쌍을 저장하기 위한 Pair 구조체 및 배열 선언
* 4. 탐색한 그룹 쌍의 변의 개수를 기록하기 위한 unordered_map 선언
*    key: a_parent * n * n + b_parent, value: 그룹간 맞닿은 변의 개수
* 5. 배열 내 Data 구조체의 parent 초기화
* 6. area를 탐색하며 그룹 구성 시작
* 6 - 1. 현재 칸에 부모가 없을 경우 본인을 부모로 설정 후 bfs 탐색
* 6 - 2. bfs 탐색 중 부모가 없고, 숫자가 동일할 경우 부모 설정
* 7. area 재탐색 시작
* 7 - 1. 현재 칸의 부모와 상하좌우 칸의 부모가 다를 경우
* 7 - 2. unordered_map에 해당 그룹 쌍이 존재하는지 확인
* 7 - 3. 존재하지 않을 경우, 해당 그룹 쌍을 unordered_map 및 Pair 구조체 배열에 추가하고, 변의 개수를 1로 설정
* 7 - 4. 존재할 경우, 변의 개수를 1 증가
* 8. Pair 구조체 배열을 탐색하며 계산식에 따라 값을 계산 후 answer에 합산
* 9. area의 십자 부분을 좌측 90도 회전 후, 나머지 부분을 우측 90도 회전
* 10. 6~9번 과정을 3번 반복
* 11. answer 출력
*
*/

#include <iostream>
#include <algorithm>
#include <unordered_map>

using namespace std;

struct Data {
	// num: 현재 번호, parent: 부모 번호, count: 그룹 내 칸 수
	long long num, parent, count;
	bool is_visit;
};

Data area1[29][29];
Data area2[29][29];

long long pairs_idx = 0;
long long group_pairs[2000];
unordered_map<int, int> group_edges;

long long n;

/* 입출력 최적화 */
void optimize_io() {
	cin.tie(nullptr);
	ios::sync_with_stdio(false);
	cout.tie(nullptr);
}


/* area 초기화 */
void reset_area(Data area[29][29]) {
	for (long long i = 0; i < n; i++) {
		for (long long j = 0; j < n; j++) {
			area[i][j].parent = i * n + j;
			area[i][j].is_visit = false;
		}
	}
}


/* 배열 값 입력 */
void input() {
	cin >> n;
	for (long long i = 0; i < n; i++) {
		for (long long j = 0; j < n; j++) {
			cin >> area1[i][j].num;
		}
	}
}

long long front, rear;
long long que[1000];

long long dx[4] = { 0, 0, -1, 1 };
long long dy[4] = { -1, 1, 0, 0 };


/* 그룹 생성 */
void make_group(Data area[29][29], long long parent) {
	while (front < rear) {
		long long cur = que[front++];
		long long y = cur / n;
		long long x = cur % n;

		for (long long i = 0; i < 4; i++) {
			long long ny = y + dy[i];
			long long nx = x + dx[i];

			// 범위 확인
			if (nx < 0 || nx >= n || ny < 0 || ny >= n)
				continue;

			// 숫자가 다를 경우
			if (area[ny][nx].num != area[y][x].num)
				continue;

			// 이미 부모가 존재할 경우
			if (area[ny][nx].parent != ny * n + nx)
				continue;

			// 부모 업데이트 및 그룹 내 칸 수 증가
			area[ny][nx].parent = parent;
			area[parent / n][parent % n].count++;

			// 큐에 추가
			que[rear++] = ny * n + nx;
		}
	}
}


/* area 그룹 탐색 */
void find_groups(Data area[29][29]) {
	for (long long i = 0; i < n; i++) {
		for (long long j = 0; j < n; j++) {
			if (area[i][j].parent == i * n + j) {
				// 큐 초기화 및 부모 설정
				front = rear = 0;
				que[rear++] = i * n + j;

				// 부모 표시
				area[i][j].parent = -1;
				area[i][j].count = 1;

				// 그룹 생성
				make_group(area, i * n + j);
			}
		}
	}
}


/* 그룹 쌍의 변의 개수 탐색 */
void find_group_edges(Data area[29][29], long long parent) {
	while (front < rear) {
		long long cur = que[front++];
		long long y = cur / n;
		long long x = cur % n;

		for (long long i = 0; i < 4; i++) {
			long long ny = y + dy[i];
			long long nx = x + dx[i];

			// 범위 확인
			if (nx < 0 || nx >= n || ny < 0 || ny >= n)
				continue;

			// 이미 확인한 칸 및 그룹이라면 continue
			if (area[ny][nx].is_visit)
				continue;

			// 부모가 다를 경우
			long long neighbor_parent = (area[ny][nx].parent == -1 ? ny * n + nx : area[ny][nx].parent);
			if (parent != neighbor_parent) {
				long long pair_num = min(parent, neighbor_parent) * n * n + max(parent, neighbor_parent);

				// 그룹 쌍이 존재할 경우
				if (group_edges[pair_num]) {
					group_edges[pair_num]++;
				}
				else {
					group_edges[pair_num] = 1;
					group_pairs[pairs_idx++] = pair_num;
				}
			}
			else if (area[ny][nx].num == area[y][x].num) {
				area[ny][nx].is_visit = true;
				que[rear++] = ny * n + nx;
			}
		}
	}
}


/* 그룹 쌍 탐색 */
void find_group_pairs(Data area[29][29]) {
	pairs_idx = 0;
	group_edges.clear();
	for (long long i = 0; i < n; i++) {
		for (long long j = 0; j < n; j++) {
			if (area[i][j].parent == -1) {
				area[i][j].is_visit = true;

				front = rear = 0;
				que[rear++] = i * n + j;
				find_group_edges(area, i * n + j);
			}
		}
	}
}


/* 예술 점수 계산 */
long long calc_score(Data area[29][29]) {
	long long result = 0;
	for (long long i = 0; i < pairs_idx; i++) {
		long long pair_num = group_pairs[i];

		long long a = pair_num / (n * n);
		long long b = pair_num % (n * n);
		auto [a_num, a_parent, a_count, a_is_visit] = area[a / n][a % n];
		auto [b_num, b_parent, b_count, b_is_visit] = area[b / n][b % n];

		result += (a_count + b_count) * a_num * b_num * group_edges[group_pairs[i]];
	}

	return result;
}


/* 십자 회전 (좌측 90도) */
void cross_rotate_area(Data pre_area[29][29], Data next_area[29][29], long long mid) {
	for (long long i = 0; i < n; i++) {
		next_area[mid][i] = pre_area[i][mid];
	}

	for (long long i = 0; i < n; i++) {
		next_area[n - 1 - i][mid] = pre_area[mid][i];
	}
}


/* 남은 배열 회전 (우측 90도) */
void rotate_area(Data pre_area[29][29], Data next_area[29][29], long long mid) {
	// 좌 상단 회전
	for (long long i = 0; i < mid; i++) {
		for (long long j = 0; j < mid; j++) {
			next_area[j][mid - 1 - i] = pre_area[i][j];
		}
	}

	// 우 상단 회전
	for (long long i = 0; i < mid; i++) {
		for (long long j = mid + 1; j < n; j++) {
			next_area[j - mid - 1][n - 1 - i] = pre_area[i][j];
		}
	}

	// 좌 하단 회전
	for (long long i = mid + 1; i < n; i++) {
		for (long long j = 0; j < mid; j++) {
			next_area[n - mid + j][n - 1 - i] = pre_area[i][j];
		}
	}

	// 우 하단 회전
	for (long long i = mid + 1; i < n; i++) {
		for (long long j = mid + 1; j < n; j++) {
			next_area[j][n + mid - i] = pre_area[i][j];
		}
	}
}



int main() {

	optimize_io(); // 입출력 최적화
	input(); // 배열 값 입력

	long long answer = calc_score(area1);
	for (long long i = 1; i <= 4; i++) {

		reset_area((i % 2 ? area1 : area2)); // area 초기화
		find_groups((i % 2 ? area1 : area2)); // 그룹 탐색
		find_group_pairs((i % 2 ? area1 : area2)); // 그룹 쌍 탐색

		answer += calc_score((i % 2 ? area1 : area2));

		// 마지막 회전 후에는 더 이상 회전하지 않음
		if (i < 4) {
			// area 회전 및 next_area에 저장
			cross_rotate_area((i % 2 ? area1 : area2), (i % 2 ? area2 : area1), n / 2);
			rotate_area((i % 2 ? area1 : area2), (i % 2 ? area2 : area1), n / 2);
		}
	}

	cout << answer << "\n"; // 예술 점수 출력
}
