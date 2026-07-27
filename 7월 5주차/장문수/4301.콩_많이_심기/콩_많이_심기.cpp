#include <iostream>
#include <vector>
#include <queue>


using namespace std;


int dx[4] = { 0, 2, 0, -2 };
int dy[4] = { 2, 0, -2, 0 };

void four_dir(int x, int y, vector<vector<bool>>& area) {
	for (int i = 0; i < 4; i++) {
		int nx = x + dx[i];
		int ny = y + dy[i];

		if (nx >= 0 && nx < area.size() && ny >= 0 && ny < area[0].size()) {
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
		int n, m;
		cin >> n >> m;

		int answer = 0;
		vector<vector<bool>> area(n, vector<bool>(m, true));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (area[i][j]) {
					answer++;
					four_dir(i, j, area);
				}

				area[i][j] = false;
			}
		}

		cout << "#" << t << " " << answer << "\n";
	}
}