/**
 * [BOJ] 3144 - 자석
 * - 제출 날짜: 2026년 4월 22일
 * - 결과: 맞았습니다!!
 * - 메모리: 3976 KB
 * - 시간: 20 ms
 */

#include <iostream>

using namespace std;

static int arr[500000];

int inputArray(int n) {
    int cnt = 1, idx = 0;
    
    string val, next;
    cin >> val;
    
    for(int i = 1; i < n; i++) {
        cin >> next;
        if(val == next) {
            cnt++;
        } else {
            arr[idx++] = cnt;
            val = next;
            cnt = 1;
        }
    }

    arr[idx++] = cnt;
    return idx;
}

int solve(int n, int m) {
    int left = 0, right = 0, cnt = 0, res = n;
    while(left < n) {
        while(right < n && cnt < m) {
            cnt += arr[right++];
        }

        if(cnt == m && ((((right - left) & 1) == 1) || left == 0 || right == n)) {
            int val = (right - left) >> 1;
            if(res > val) res = val;
        }
        cnt -= arr[left++];
    }
    return res;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int n, m;
    cin >> n >> m;

    int arr_size = inputArray(n);
    cout << solve(arr_size, m);
    return 0;
}