// 메모리 5928 KB
// 시간 52 ms

#include <iostream>
#define SIZE 1000000
using namespace std;

static int arr[SIZE];

int solve(int n, int k) {
    int left = 0, right = 0, cnt = 0, min_len = SIZE + 5;
    while(left < n) {
        while(right < n && cnt < k) {
            cnt += arr[right++];
        }

        if(cnt == k && min_len > right - left) min_len = right - left;
        cnt -= arr[left++];
    }
    return min_len == SIZE + 5 ? -1 : min_len;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int n, k;
    cin >> n >> k;

    for(int i = 0; i < n; i++) {
        cin >> arr[i];
        arr[i] &= 1;
    }

    cout << solve(n, k);
    return 0;
}