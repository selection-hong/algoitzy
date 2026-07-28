#include <iostream>


using namespace std;

int n, m;
bool area[1000][1000];


/* 사용 배열 초기화 */
void reset_area() {
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			area[i][j] = true;
		}
	}
}


int dx[4] = { 0, 2, 0, -2 };
int dy[4] = { 2, 0, -2, 0 };

/* 4방향 처리 */
void four_dir(int x, int y) {
	for (int i = 0; i < 4; i++) {
		int nx = x + dx[i];
		int ny = y + dy[i];

		if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
			area[nx][ny] = false;
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

		// 배열 초기화
		reset_area();

		// 콩 심기
		int answer = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (area[i][j]) { // 콩을 심을 수 있을 경우
					answer++;
					area[i][j] = false; // 콩 심음 처리
					four_dir(i, j); // 4방향 처리
				}
			}
		}

		cout << "#" << t << " " << answer << "\n";
	}
}
